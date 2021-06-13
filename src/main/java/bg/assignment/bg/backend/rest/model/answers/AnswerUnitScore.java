package bg.assignment.bg.backend.rest.model.answers;

import bg.assignment.bg.backend.model.ReviewScore;

public class AnswerUnitScore extends AnswerUnitUUID
{
	private final double _score;
	private final int _totalReviews;
	
	public AnswerUnitScore(final String unitUUID, final ReviewScore reviewScore)
	{
		super(unitUUID);
		_score = reviewScore.getAverageReview();
		_totalReviews = reviewScore.getTotalReviews();
	}
	
	public double getAverageScore()
	{
		return _score;
	}
	
	public int getTotalReviews()
	{
		return _totalReviews;
	}
}
