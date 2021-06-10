package bg.assignment.bg.backend.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import bg.assignment.bg.backend.manager.DatabaseManager;
import bg.assignment.bg.backend.manager.LoginManager;
import bg.assignment.bg.backend.manager.MailManager;
import bg.assignment.bg.backend.manager.RegionManager;
import bg.assignment.bg.backend.manager.UserManager;
import bg.assignment.bg.backend.model.BgUser;
import bg.assignment.bg.backend.model.GResponse;
import bg.assignment.bg.backend.model.enums.ELoginResult;
import bg.assignment.bg.backend.rest.model.ValidRegistration;
import bg.assignment.bg.backend.rest.model.answer.AnswerLogin;
import bg.assignment.bg.backend.rest.model.requests.RequestLogin;
import bg.assignment.bg.backend.rest.model.requests.RequestRegister;
import bg.assignment.bg.backend.rest.model.requests.RequestVerify;
import bg.assignment.bg.backend.security.jwt.JWTUtil;
import bg.assignment.bg.backend.util.WebUtil;

@RestController
public class UserController
{
	@Autowired
	private RegionManager regionManager;
	
	@Autowired
	private boolean recaptchaEnabled;
	
	@Autowired
	private String recaptchaSecretKey;
	
	@Autowired
	private LoginManager loginManager;

    @Autowired
    private MailManager mailManager;

    @Autowired
    private UserManager userManager;

    @Autowired
    private DatabaseManager databaseManager;
    
	@Autowired
	private JWTUtil jwtUtil;
    
    @PostMapping("/login")
    public ResponseEntity<AnswerLogin> login(final Model model, @ModelAttribute final RequestLogin loginRequest)
    {
    	final ELoginResult loginResult = loginManager.processLogin(loginRequest);
    	
    	if (loginResult == ELoginResult.SUCCESS__DB__AUTHENTICATED)
    	{
    		final ValidRegistration authenticatedRegistration = loginRequest.getAuthenticatedRegistration();
    		
    		final String token = jwtUtil.generateToken(authenticatedRegistration.getColonistId());
    		
    		return ResponseEntity.ok(new AnswerLogin(token, "Token generated successfully!"));
    	}
    	
    	return ResponseEntity.ok(new AnswerLogin(loginResult.toString(), "Authentication Denied"));
    }
    
	@PostMapping("/verify") // not a requirement
	public void verify(final Model model, @ModelAttribute final RequestVerify verify)
	{
		final String uuid = verify.getToken();
		final BgUser bgUser = userManager.getBgUserByUUID(uuid);
		
		if (!bgUser.isVerified())
		{
			bgUser.setVerified();
			bgUser.store(databaseManager);
		}
		else
			System.out.println(bgUser + " is already verified!");
	}
    
	@PostMapping("/register") // not a requirement
	public void register(final Model model, @ModelAttribute final RequestRegister register)
	{
		final GResponse gResponse = recaptchaEnabled ? WebUtil.validateCaptcha(recaptchaSecretKey, register.getCaptchaResponse()) : GResponse.STATIC_RESPONSE_SUCCESS;
		if (gResponse != null && gResponse.isSuccess())
		{
			final var res = loginManager.processRegister(register);
			System.out.println(res);
			WebUtil.processRegister(model, register, res, mailManager);
		}
		
	}
}
