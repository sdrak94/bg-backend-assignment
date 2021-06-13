package bg.assignment.bg.backend.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import bg.assignment.bg.backend.model.BgCancelPolicy;
import bg.assignment.bg.backend.model.BgRegion;
import bg.assignment.bg.backend.model.BgUnit;
import bg.assignment.bg.backend.model.BgUser;
import bg.assignment.bg.backend.model.RetrieveOffset;
import bg.assignment.bg.backend.model.ReviewScore;
import bg.assignment.bg.backend.model.BgUnitFilter;
import bg.assignment.bg.backend.model.enums.ECreateUnitResult;
import bg.assignment.bg.backend.model.enums.EReviewUnitResult;
import bg.assignment.bg.backend.rest.model.requests.RequestUnitCreate;
import bg.assignment.bg.backend.rest.model.requests.RequestUnitList;
import bg.assignment.bg.backend.rest.model.requests.RequestUnitReview;


@Service
@Scope("singleton")
public class UnitManager
{
	private static final String GET_ALL_UNITS = "SELECT * FROM bg_units";
	private static final String GET_UNIT = "SELECT * FROM bg_units WHERE unit_uuid=?";
	
	private static final String INSERT_UNIT = "INSERT INTO bg_units (unit_uuid, title, `desc`, cancelpolicy_id, region_id, price) VALUES (?, ?, ?, ?, ?, ?)";
	private static final String INSERT_REVIEW = "INSERT INTO bg_reviews (unit_id, user_id, score, `desc`) VALUES (?, ?, ?, ?)";
	
	private static final String GET_REVIEW_SCORES = "SELECT score FROM bg_reviews WHERE unit_id=?";
//	private static final String GET_REVIEWS = "SELECT * FROM bg_reviews WHERE unit_id=?";
	
//	@Autowired
//	private CacheManager igniteManager;
	
	@Autowired
	private RegionManager regionManager;

	@Autowired
	private CancelpolicyManager cancelpolicyManager;

	@Autowired
	private DatabaseManager databaseManager;

	@Autowired
	private OffsetManager offsetManager;
	
	//querying the whole bg_users table is not suggested but for the scope of this assignment its fine
	//all Units should be cached on a map or a distributed cache like Apache Ignite and getAllUnits should not query the database directly
	public List<BgUnit> getAllUnits()
	{
		final List<BgUnit> allUnits = new ArrayList<>();
		
		try (final Connection con = databaseManager.getConnection();
			 final PreparedStatement pst = con.prepareStatement(GET_ALL_UNITS);
			 final ResultSet rs = pst.executeQuery())
		{
			while (rs.next())
			{
				final BgUnit bgUnit = new BgUnit(regionManager, cancelpolicyManager, rs);
				final String unitUUID = bgUnit.getUnitUUID().toString();
				final ReviewScore reviewScore = getTotalScoreByUnitId(unitUUID, con);//reuse con
				bgUnit.setReviewScore(reviewScore);
				allUnits.add(bgUnit);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return allUnits;
	}

	// this is an assignment requirement
	// has heavy performance impact due to the fact that getAllUnits queries the whole database
	// but provides an amazing seamless feel for user experience.
	// can be heavily optimized by using a local or a distributed cache like Apache Ignite (out of scope)
	public Stream<BgUnit> retrieveUnits(final RequestUnitList unitListRequest)
	{
		final RetrieveOffset retrieveOffset = offsetManager.calculateOffsets(unitListRequest);
		
		final Comparator<BgUnit> unitComparator = unitListRequest.getCombinedComparator();
		
		System.out.println(unitComparator);
		
		final List<BgUnit> allUnits = getAllUnits();
		
		// this can be cached locally to the BgUser instance for future use to boost performance significantly at the cost of some data consistency for the user (out of scope)
		return allUnits.stream()
				.sorted(unitComparator)
				.filter(new BgUnitFilter(unitListRequest))
				.skip(retrieveOffset.getOffset())
				.limit(retrieveOffset.getLimit());
	}
	
	//TODO caching
	public BgUnit getUnitById(final String unitUUID)
	{
		try (final Connection con = databaseManager.getConnection();
			 final PreparedStatement pst = con.prepareStatement(GET_UNIT))
		{
			pst.setString(1, unitUUID);
			try (final ResultSet rs = pst.executeQuery())
			{
				if (rs.next())
				{
					final BgUnit bgUnit = new BgUnit(regionManager, cancelpolicyManager, rs);
					final ReviewScore reviewScore = getTotalScoreByUnitId(unitUUID, con);//reuse con
					bgUnit.setReviewScore(reviewScore);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return null;//not found 
	}
	
	public ECreateUnitResult tryCreateUnit(final RequestUnitCreate createUnitRequest)
	{
		
		final String desc = createUnitRequest.getDesc();
		if (desc.length() < 3)
			return ECreateUnitResult.FAILED__IN__TITLE_LESS_3CHAR;
		
		final String title = createUnitRequest.getTitle();
		if (title.length() < 3)
			return ECreateUnitResult.FAILED__IN__DESC_LESS_3CHAR;
		
		final int cancelpolicyId = createUnitRequest.getCancelpolicyId();
		final BgCancelPolicy cancelPolicy = cancelpolicyManager.getCancelpolicyById(cancelpolicyId);
		if (cancelPolicy == null)
			return ECreateUnitResult.FAILED__IN__POLICYID_INVALID;
		
		final int regionId = createUnitRequest.getRegionId();
		final BgRegion region = regionManager.getRegionById(regionId);
		if (region == null)
			return ECreateUnitResult.FAILED__IN__REGIONID_UNKN;
		
		final long monthlyPrice = createUnitRequest.getPrice();
		if (monthlyPrice < 1)
			return ECreateUnitResult.FAILED__IN__PRICE_INVALID;
		
		final MultipartFile imageFile = createUnitRequest.getImage();
		if (imageFile == null)
			return ECreateUnitResult.FAILED__IN__IMAGE_NOT_PROVIDED; //TODO need further checks to see if this image is valid format
		
		final BgUnit bgUnit = new BgUnit(imageFile, title, desc, region, cancelPolicy, monthlyPrice);
		
		try (final Connection con = databaseManager.getConnection();
			 final PreparedStatement pst = con.prepareStatement(INSERT_UNIT))
		{
			bgUnit.store(pst);
			pst.executeUpdate();
			createUnitRequest.setNewUnit(bgUnit);
			
			return ECreateUnitResult.SUCCESS__DB__CREATED;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return ECreateUnitResult.FAILED__DB__INTERNAL_ERROR;
		}
	}
	
	public ReviewScore getTotalScoreByUnitId(final String unitId)
	{
		try (final Connection con = databaseManager.getConnection())
		{
			return getTotalScoreByUnitId(unitId, con);
		}
		catch (SQLException e)
		{
		}
		return null;
	}
	
	public ReviewScore getTotalScoreByUnitId(final String unitId, final Connection con)
	{
		final ReviewScore reviewScore = new ReviewScore();
		
		try(final PreparedStatement pst = con.prepareStatement(GET_REVIEW_SCORES))
		{
			pst.setString(1, unitId);
			
			try (final ResultSet rs = pst.executeQuery())
			{
				while (rs.next())
					reviewScore.addReviewScore(rs.getFloat("score"));
			}
		}
		catch (SQLException e)
		{
		}
		
		return reviewScore;
	}
	
	public EReviewUnitResult tryReviewUnit(final BgUser reviewerUser, final RequestUnitReview reviewUnitRequest)
	{
		
		final String unitId = reviewUnitRequest.getUnitUUID();
		
		final float reviewScore = reviewUnitRequest.getNewScore();
		if (reviewScore < 0f || reviewScore > 5f)
			return EReviewUnitResult.FAILED__IN__SCORE_INVALID;
		
		try (final Connection con = databaseManager.getConnection();
			 final PreparedStatement pst = con.prepareStatement(INSERT_REVIEW))
		{
			pst.setString(1, unitId);
			pst.setString(2, reviewerUser.getColonistUUID().toString());
			
			pst.setFloat(3, reviewScore);
			pst.setString(4, reviewUnitRequest.getDesc());
			
			pst.executeUpdate();
			
			final ReviewScore resScore = getTotalScoreByUnitId(unitId);
			reviewUnitRequest.setResultScore(resScore);
			
			return EReviewUnitResult.SUCCESS__DB__CREATED;
		}
		catch (SQLException e)
		{
			//check for error 23 ?
			return EReviewUnitResult.FAILED__DB__ALREADY_REVIWED;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return EReviewUnitResult.FAILED__DB__INTERNAL_ERROR;
		}
	}
	
}
