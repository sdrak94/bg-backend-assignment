package bg.assignment.bg.backend.rest.model.answers;

import bg.assignment.bg.backend.model.ReviewScore;

public class AnswerUnitReview 
{
	private final float _newScore;
	
	public AnswerUnitReview(final ReviewScore newScore)
	{
		_newScore = newScore.getAverage();
	}
	
	public float getNewScore()
	{
		return _newScore;
	}
}
