package bg.assignment.bg.backend.rest.model.requests;

import java.io.Serializable;

public class RequestRegister extends AUserRequest implements Serializable
{
	private String _mail = "";
	
	private String _pass = "";
	
	private String _cpass = "";
	
	private String _captchaResponse;
	
	private static final long serialVersionUID = 1L;

	public String getMail()
	{
		return _mail;
	}

	public void setMail(String mail)
	{
		_mail = mail;
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
