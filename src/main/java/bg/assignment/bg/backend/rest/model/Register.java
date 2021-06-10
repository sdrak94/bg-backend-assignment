package bg.assignment.bg.backend.rest.model;

import java.io.Serializable;

public class Register implements Serializable
{
	private String _colonistId = "";

	private String _email = "";
	
	private String _pass = "";
	
	private String _cpass = "";
	
	private String _captchaResponse;
	
	private static final long serialVersionUID = 1L;

	public String getColonistId()
	{
		return _colonistId;
	}

	public void setName(String colonistId)
	{
		_colonistId = colonistId;
	}

	public String getEmail()
	{
		return _email;
	}

	public void setEmail(String email)
	{
		_email = email;
	}

	public String getPass()
	{
		return _pass;
	}

	public void setPass(String pass)
	{
		_pass = pass;
	}

	public String getCpass()
	{
		return _cpass;
	}

	public void setCpass(String cpass)
	{
		_cpass = cpass;
	}

	public void setCaptchaResponse(final String captchaResponse)
	{
		_captchaResponse = captchaResponse;
	}
	
	public String getCaptchaResponse()
	{
		return _captchaResponse;
	}
}
