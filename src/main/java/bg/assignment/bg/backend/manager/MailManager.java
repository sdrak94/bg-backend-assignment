package bg.assignment.bg.backend.manager;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import bg.assignment.bg.backend.rest.model.requests.RequestUserRegister;

@Service
@Scope("singleton")
public class MailManager
{
	@Value("classpath:conf_mail.html")
	private String confMail;
	
//	@Autowired
//	private JavaMailSender mailSender;

	public void sendVerificationEmail(RequestUserRegister registerRequest)
	{

	}
}
