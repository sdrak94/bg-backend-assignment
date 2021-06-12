package bg.assignment.bg.backend.rest.model.requests;

import java.io.Serializable;

import bg.assignment.bg.backend.model.ReviewScore;

public class RequestUnitReview implements Serializable
{
	private static final long serialVersionUID = 8342491177066854343L;
	
	private String _unitUUID = "";
	
	private float _newScore = -1f; // no score
	
	private String _desc = "";
	
	private ReviewScore _resScore;

	public String getUnitUUID() 
	{
		return _unitUUID;
	}

	public void setUnitUUID(String unitUUID)
	{
		_unitUUID = unitUUID;
	}

	public float getNewScore()
	{
		return _newScore;
	}

	public void setNewScore(float newScore) 
	{
		_newScore = newScore;
	}

	public String getDesc()
	{
		return _desc;
	}

	public void setDesc(String desc) 
	{
		_desc = desc;
	}
	
	public ReviewScore getReviewScore()
	{
		return _resScore;
	}

	public void setResultScore(final ReviewScore resScore) 
	{
		_resScore = resScore;
	}
	
	
	
}
