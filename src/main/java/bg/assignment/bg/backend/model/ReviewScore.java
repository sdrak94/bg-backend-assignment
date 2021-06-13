package bg.assignment.bg.backend.model;
public class ReviewScore
{
	private int _totalReviews;
	
	private float _reviewScore;
	
	private double _averageReview = -1;
	
	public void addReviewScore(final float score)
	{
		_totalReviews++;
		_reviewScore += score;
		
		_averageReview = (_reviewScore / _totalReviews);
	}
	
	public double getAverageReview()
	{
		return _averageReview;
	}
	
	public int getTotalReviews()
	{
		return _totalReviews;
	}
}