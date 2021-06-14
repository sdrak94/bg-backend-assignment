package bg.assignment.bg.backend.rest.model.answers;

import bg.assignment.bg.backend.model.enums.ERegisterResult;

public class AnswerUserRegister 
{
	public static final AnswerUserRegister GSTATIC_ANSWER = new AnswerUserRegister(ERegisterResult.FAILED__IN__GCAPTCHA_VALIDATION);
	
	private final ERegisterResult _registerResult;
	
	public AnswerUserRegister(final ERegisterResult registerResult)
	{
		_registerResult = registerResult;
	}
	
	public ERegisterResult getRegisterResult()
	{
		return _registerResult;
	}
}
