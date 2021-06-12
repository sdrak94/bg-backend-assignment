package bg.assignment.bg.backend.model;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

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

	public BigDecimal getMonthlyPrice()
	{
		return _monthlyPrice;
	}
}
