package bg.assignment.bg.backend.model;

public class RetrieveOffset
{
	private long _offset;
	private final long _limit;
	
	public RetrieveOffset(final long limit)
	{
		_limit = limit;
	}

	public long getOffset()
	{
		return _offset;
	}

	public void setOffset(int offset)
	{
		_offset = offset;
	}

	public long getLimit()
	{
		return _limit;
	}
	
	//user for server side offseting
	public void incOffset(final long inc)
	{
		_offset += inc;
	}
}
