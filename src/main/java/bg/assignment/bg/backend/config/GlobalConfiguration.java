package bg.assignment.bg.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalConfiguration
{

    @Value("${bg.assignment.unit.retrieve.serverside}")
    private boolean retrieveServerSide;

    @Value("${bg.assignment.unit.retrieve.hardlimit}")
    private int clientsideHardLimit;

    @Value("${bg.assignment.unit.retrieve.server.limit}")
    private int serversideLimit;
	
	
	
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

	@Bean(name = "retrieveServerSide")
	public boolean retrieveServerSide()
	{
		return retrieveServerSide;
	}

	@Bean(name = "clientsideHardLimit")
	public int clientsideHardLimit()
	{
		return clientsideHardLimit;
	}

	@Bean(name = "serversideLimit")
	public int serversideLimit()
	{
		return serversideLimit;
	}
	
}
