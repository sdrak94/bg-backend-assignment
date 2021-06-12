package bg.assignment.bg.backend.rest.model.answers;

public class AnswerUnitCreate 
{
	private final String _unitUUID;
	
	public AnswerUnitCreate(final String unitUUID)
	{
		_unitUUID = unitUUID;
	}
	
	public String getUnitUUID()
	{
		return _unitUUID;
	}
}
