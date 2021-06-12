package bg.assignment.bg.backend.rest.model.answers;

public class AnswerUserLogin 
{
	private final String _jwtToken;
	private final String _reply;
	
	public AnswerUserLogin(final String jwtToken, final String reply)
	{
		_jwtToken = jwtToken;
		_reply = reply;
	}
	
	public String getJwtToken()
	{
		return _jwtToken;
	}
	
	public String getReply()
	{
		return _reply;
	}
}
