package bg.assignment.bg.backend.model.enums;

public enum ECreateUnitResult
{
	//validation
	FAILED__IN__TITLE_LESS_3CHAR,
	FAILED__IN__DESC_LESS_3CHAR,

	FAILED__IN__POLICYID_INVALID,
	FAILED__IN__REGIONID_UNKN,
	
	FAILED__IN__PRICE_INVALID,
	
	FAILED__IN__IMAGE_NOT_PROVIDED,

	//database
	FAILED__DB__INTERNAL_ERROR,
	
	SUCCESS__DB__CREATED;
}
