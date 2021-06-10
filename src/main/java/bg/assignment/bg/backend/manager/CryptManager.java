package bg.assignment.bg.backend.manager;

import java.security.MessageDigest;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;


@Service
@Scope("singleton")
public class CryptManager
{
	@Autowired
	@Qualifier("md5digest")
	private MessageDigest messageDigest;

	public String md5digest(final String text) throws Exception
	{
		return Base64.getEncoder().encodeToString(messageDigest.digest(text.getBytes("UTF-8")));
	}
}
