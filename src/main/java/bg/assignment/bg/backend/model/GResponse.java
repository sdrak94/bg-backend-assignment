package bg.assignment.bg.backend.model;
public class GResponse
{
	public static GResponse STATIC_RESPONSE_SUCCESS = new GResponse(true);

	private boolean success;
	private String challenge_ts;
	private String hostname;
	
	public GResponse()
	{
		this(false);
	}
	
	public GResponse(final boolean success)
	{
		this.success = success;
	}

	public boolean isSuccess()
	{
		return success;
	}

	public void setSuccess(boolean success)
	{
		this.success = success;
	}

	public String getChallenge_ts()
	{
		return challenge_ts;
	}

	public void setChallenge_ts(String challenge_ts)
	{
		this.challenge_ts = challenge_ts;
	}

	public String getHostname()
	{
		return hostname;
	}

	public void setHostname(String hostname)
	{
		this.hostname = hostname;
	}
}