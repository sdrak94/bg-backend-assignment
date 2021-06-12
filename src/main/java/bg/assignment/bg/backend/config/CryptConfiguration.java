package bg.assignment.bg.backend.config;

import java.security.MessageDigest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class CryptConfiguration
{
	@Bean(name = "md5digest")
	public MessageDigest getCryptMd5()
	{
		try
		{
			return MessageDigest.getInstance("SHA");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	@Bean
	public BCryptPasswordEncoder encodePassword() 
	{
		return new BCryptPasswordEncoder();
	}
}