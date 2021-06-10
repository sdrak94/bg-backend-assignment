package bg.assignment.bg.backend.model;

public class BgCancelPolicy 
{
	private int _cancelpolicyId;
	
	private String _desc;
	
	public int getCancelPolicyId()
	{
		return _cancelpolicyId;
	}
	
	public void setCancelpolicyId(final int cancelpolicyId)
	{
		_cancelpolicyId = cancelpolicyId;
	}
	
	public String getDesc()
	{
		return _desc;
	}
	
	public void setDesc(final String desc)
	{
		_desc = desc;
	}
	
	public String toString()
	{
		return String.format("[%d][%s]", _cancelpolicyId, _desc);
	}
}
