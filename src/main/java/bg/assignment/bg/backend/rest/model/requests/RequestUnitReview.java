package bg.assignment.bg.backend.rest.model.requests;

import java.io.Serializable;

import bg.assignment.bg.backend.model.ReviewScore;

public class RequestUnitReview extends AUnitRequest implements Serializable
{
	private static final long serialVersionUID = 8342491177066854343L;
	
	private float _newScore = -1f; // no score
	
	private String _desc = "";
	
	private ReviewScore _resScore;

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
