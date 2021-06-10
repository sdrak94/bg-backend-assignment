package bg.assignment.bg.backend.model;

public class BgRegion
{
	private int _regionId;
	private String _name;
	private String _desc;
	
	public int getRegionId()
	{
		return _regionId;
	}
	
	public void setRegionId(int regionId)
	{
		_regionId = regionId;
	}
	
	public String getName()
	{
		return _name;
	}
	
	public void setName(String name)
	{
		_name = name;
	}
	
	public String getDesc()
	{
		return _desc;
	}
	
	public void setDesc(String desc)
	{
		_desc = desc;
	}
	
	@Override
	public String toString()
	{
		return String.format("[%d][%s]", _regionId, _name);
	}
}
