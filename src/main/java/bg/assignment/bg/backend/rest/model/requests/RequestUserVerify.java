package bg.assignment.bg.backend.rest.model.requests;

import java.io.Serializable;

public class RequestUserVerify extends AUserRequest implements Serializable
{
	private static final long serialVersionUID = 1L;

	private String _token; // its actually the user's uuid
	
	public String getToken()
	{
		return _token;
	}
	
	public void setToken(final String token)
	{
		_token = token;
	}
}
