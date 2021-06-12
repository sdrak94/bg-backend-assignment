package bg.assignment.bg.backend.model;
public class ReviewScore
{
	private int _totalReviews;
	
	private float _reviewScore;
	
	public void addReviewScore(final float score)
	{
		_totalReviews++;
		_reviewScore += score;
	}
	
	public float getAverage()
	{
		return _reviewScore / _totalReviews;
	}
	
	public int getTotalReviews()
	{
		return _totalReviews;
	}
}