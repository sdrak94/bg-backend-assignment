package bg.assignment.bg.backend.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import bg.assignment.bg.backend.manager.LoginManager;
import bg.assignment.bg.backend.manager.MailManager;
import bg.assignment.bg.backend.manager.RegionManager;
import bg.assignment.bg.backend.model.BgRegion;
import bg.assignment.bg.backend.model.GResponse;
import bg.assignment.bg.backend.rest.model.Login;
import bg.assignment.bg.backend.rest.model.Register;
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
    
    @PostMapping("/login")
    public void login(final Model model, @ModelAttribute final Login login)
    {
    	
    }
    
    
	@PostMapping("/register") // not a requirement
	public void register(final Model model, @ModelAttribute final Register register)
	{
		final BgRegion region = regionManager.getRegionById(1);
		
		final GResponse gResponse = recaptchaEnabled ? WebUtil.validateCaptcha(recaptchaSecretKey, register.getCaptchaResponse()) : GResponse.STATIC_RESPONSE_SUCCESS;
		if (gResponse != null && gResponse.isSuccess())
		{
			final var res = loginManager.processRegister(register);

			WebUtil.processRegister(model, register, res, mailManager);
		}
		
	}
}
