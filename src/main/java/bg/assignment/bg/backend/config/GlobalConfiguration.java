package bg.assignment.bg.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalConfiguration
{
    @Value("${recaptcha.enabled}")
    private boolean recaptchaEnabled; 
    
    @Value("${recaptcha.secret.key}")
    private String recaptchaSecretKey;

	@Bean(name = "recaptchaEnabled")
	public boolean isRecaptchaEnabled()
	{
		return recaptchaEnabled;
	}

	@Bean(name = "recaptchaSecretKey")
	public String getRecaptchaSecretKey()
	{
		return recaptchaSecretKey;
	}
}
