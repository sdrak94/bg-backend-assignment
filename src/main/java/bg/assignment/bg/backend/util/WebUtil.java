package bg.assignment.bg.backend.util;

import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import bg.assignment.bg.backend.manager.MailManager;
import bg.assignment.bg.backend.model.GResponse;
import bg.assignment.bg.backend.model.enums.ERegisterResult;
import bg.assignment.bg.backend.rest.model.requests.RequestUserRegister;

public class WebUtil
{
	private static final String GOOGLE_RECAPTCHA_ENDPOINT = "https://www.google.com/recaptcha/api/siteverify";

	public static GResponse validateCaptcha(final String recaptchaSecret, final String captchaResponse)
	{
		final RestTemplate restTemplate = new RestTemplate();

		final MultiValueMap<String, String> requestMap = new LinkedMultiValueMap<>();
		requestMap.add("secret", recaptchaSecret);
		requestMap.add("response", captchaResponse);

		return restTemplate.postForObject(GOOGLE_RECAPTCHA_ENDPOINT, requestMap, GResponse.class);
	}
	
	public static String processRegister(final Model model, final RequestUserRegister login, final ERegisterResult result, final MailManager mailController)
	{
		if (result == ERegisterResult.SUCCESS__DB__CREATED)
		{
			mailController.sendVerificationEmail(login);
		}
		
		
		return "";
	}
}
