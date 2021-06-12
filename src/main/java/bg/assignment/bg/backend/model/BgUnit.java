package bg.assignment.bg.backend.model;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import bg.assignment.bg.backend.manager.CancelpolicyManager;
import bg.assignment.bg.backend.manager.RegionManager;

public class BgUnit
{
	private final UUID _unitUUID;
	
	private final String _imageUrl;

	private final String _title;
	
	private final String _desc;
	
	private final BgRegion _region;
	
	private final BgCancelPolicy _cancelPolicy;
	
	private final BigDecimal _monthlyPrice;
	
	private float _score;
	
	int calls;
	
	public void inc()
	{
		calls++;
	}
	
	public BgUnit(final MultipartFile image, final String title, final String desc, final BgRegion region, final BgCancelPolicy cancelPolicy, final long monthlyPrice)
	{
		_unitUUID = UUID.randomUUID();
		
		_title = title;
		_desc = desc;
		
		_region = region;
		_cancelPolicy = cancelPolicy;
		
		_monthlyPrice = BigDecimal.valueOf(monthlyPrice);
		
		Path imagePath = null;
		
		final String folderUrl = String.format("images/%s", _unitUUID.toString());
	
		final Path folderPath = Paths.get(folderUrl);
		try
		{
			Files.createDirectories(folderPath);
			imagePath = folderPath.resolve(image.getOriginalFilename());
			
			image.transferTo(imagePath);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		imagePath.toAbsolutePath();
		
		_imageUrl = imagePath.toString();
	}
	
	public BgUnit(final RegionManager regionManager, final CancelpolicyManager cancelpolicyManager, final ResultSet rs) throws SQLException
	{
		_unitUUID = UUID.fromString(rs.getString("unit_uuid"));
		
		_title = rs.getString("title");
		_desc = rs.getString("desc");
		
		final int regionId = rs.getInt("region_id");
		final int cancelpolicyId = rs.getInt("cancelpolicy_id");
		
		_region = regionManager.getRegionById(regionId);
		_cancelPolicy = cancelpolicyManager.getCancelpolicyById(cancelpolicyId);
		
		_monthlyPrice = rs.getBigDecimal("price");
		
		_imageUrl = String.format("images/%s/%s", _unitUUID.toString(), "main.jpg");
	}
	
	public void store(final PreparedStatement pst) throws SQLException
	{
		pst.setString(1, _unitUUID.toString());
		pst.setString(2, _title);
		pst.setString(3, _desc);
		pst.setInt(4, _cancelPolicy.getCancelPolicyId());
		pst.setInt(5, _region.getRegionId());
		
		pst.setBigDecimal(6, _monthlyPrice);
	}
	
	public float getScore() 
	{
		return _score;
	}

	public UUID getUnitUUID()
	{
		return _unitUUID;
	}

	public String getImageUrl()
	{
		return _imageUrl;
	}

	public String getTitle()
	{
		return _title;
	}

	public String getDesc()
{
		return _desc;
	}

	public BgRegion getRegion()
	{
		return _region;
	}

	public BgCancelPolicy getCancelPolicy() 
	{
		return _cancelPolicy;
	}

	public BigDecimal getPrice()
	{
		return _monthlyPrice;
	}
	
	public int getRegionId()
	{
		return _region.getRegionId();
	}
	
	public int getCancelPolicyId()
	{
		return _cancelPolicy.getCancelPolicyId();
	}
	
	public String toString()
	{
		return hashCode() + " " + getUnitUUID() + calls++;
	}
}
