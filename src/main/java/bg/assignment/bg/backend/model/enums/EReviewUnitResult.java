package bg.assignment.bg.backend.model.enums;

public enum EReviewUnitResult
{
	//validation
	FAILED__IN__SCORE_INVALID,

	//database
	FAILED__DB__ALREADY_REVIWED,
	FAILED__DB__INTERNAL_ERROR,
	
	SUCCESS__DB__CREATED;
}
