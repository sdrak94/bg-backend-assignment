package bg.assignment.bg.backend.rest.model.requests;

import java.io.Serializable;

public abstract class AUnitRequest implements Serializable
{
	private static final long serialVersionUID = 8342491177066854343L;
	
	private String _unitUUID = "";
	
	public String getUnitUUID() 
	{
		return _unitUUID;
	}

	public void setUnitUUID(String unitUUID)
	{
		_unitUUID = unitUUID;
	}

	
}
