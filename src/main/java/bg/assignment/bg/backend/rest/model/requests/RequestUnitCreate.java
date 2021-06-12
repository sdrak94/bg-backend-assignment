package bg.assignment.bg.backend.rest.model.requests;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

import bg.assignment.bg.backend.model.BgUnit;

public class RequestUnitCreate implements Serializable
{
	private static final long serialVersionUID = 8342491177066854342L;
	
	private MultipartFile image = null;
	
	private String title = "";
	
	private int regionId = 0;
	
	private String desc = "";
	
	private int cancelpolicyId = 0;

	private long price = 0L;
	
	private BgUnit _newUnit;
	
	public void setNewUnit(final BgUnit newUnit)
	{
		_newUnit = newUnit;
	}
	
	public BgUnit getNewUnit()
	{
		return _newUnit;
	}
	
	public MultipartFile getImage()
	{
		return image;
	}
	
	public void setImage(final MultipartFile image)
	{
		this.image = image;
	}
	
	public String getTitle() 
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public int getRegionId()
	{
		return regionId;
	}

	public void setRegion(int regionId)
	{
		this.regionId = regionId;
	}

	public String getDesc()
	{
		return desc;
	}

	public void setDesc(String desc) 
	{
		this.desc = desc;
	}

	public int getCancelpolicyId()
	{
		return cancelpolicyId;
	}

	public void setCancelpolicy(int cancelpolicyId)
	{
		this.cancelpolicyId = cancelpolicyId;
	}

	public long getPrice()
	{
		return price;
	}

	public void setPrice(long price) 
	{
		this.price = price;
	}
}
