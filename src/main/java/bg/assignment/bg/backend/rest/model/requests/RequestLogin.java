package bg.assignment.bg.backend.rest.model.requests;

import java.io.Serializable;

import bg.assignment.bg.backend.rest.model.ValidRegistration;

public class RequestLogin extends AUserRequest implements Serializable
{
	private String _pass = "";
	
	private ValidRegistration _authenticatedRegistration;
	
	private static final long serialVersionUID = 1L;

	public String getPass()
	{
		return _pass;
	}

	public void setPass(String pass)
	{
		_pass = pass;
	}
	
	public void authenticate(final ValidRegistration authenticatedRegistration)
	{
		_authenticatedRegistration = authenticatedRegistration;
	}
	
	public ValidRegistration getAuthenticatedRegistration()
	{
		return _authenticatedRegistration;
	}
	
	
}
