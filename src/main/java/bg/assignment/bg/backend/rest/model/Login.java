package bg.assignment.bg.backend.rest.model;

import java.io.Serializable;

public class Login implements Serializable
{
	private String _colonistId = "";

	private String _pass = "";
	
	private static final long serialVersionUID = 1L;

	public String getColonistId()
	{
		return _colonistId;
	}

	public void setName(String colonistId)
	{
		_colonistId = colonistId;
	}

	public String getPass()
	{
		return _pass;
	}

	public void setPass(String pass)
	{
		_pass = pass;
	}
}
