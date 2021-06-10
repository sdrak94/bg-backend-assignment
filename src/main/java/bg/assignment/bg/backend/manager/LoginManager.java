package bg.assignment.bg.backend.manager;

import java.sql.Connection;
import java.sql.ResultSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import bg.assignment.bg.backend.model.enums.ELoginResult;
import bg.assignment.bg.backend.model.enums.ERegisterResult;
import bg.assignment.bg.backend.rest.model.ValidRegistration;
import bg.assignment.bg.backend.rest.model.requests.RequestLogin;
import bg.assignment.bg.backend.rest.model.requests.RequestRegister;

@Service
@Scope("singleton")
public class LoginManager
{
	@Autowired
	private CryptManager cryptoController; //hash the password !

	private static final String GET_USER = "SELECT * FROM bg_users WHERE user_id=?";
	
	private static final String GET_ACCOUNT = "SELECT user_id FROM bg_users WHERE user_id=?";

	private static final String GET_MAIL = "SELECT mail FROM bg_users WHERE mail=?";
	
	private static final String INSERT_COLONIST = "INSERT INTO bg_users (user_id, pass_md5, mail, verified, user_uuid, registration_ts, lastaccess_ts) VALUES (?, ?, ?, ?, ?, ?, ?)";
	
	@Autowired
	private DatabaseManager databaseManager;
	
	public ERegisterResult validateRegisterInput(final RequestRegister registerRequest)
	{
		if (registerRequest.getColonistId().length() < 4)
			return ERegisterResult.FAILED__IN__USERNAME_LESS_3CHAR;

		if (registerRequest.getPass().length() < 4)
			return ERegisterResult.FAILED__IN__PASSWORD_LESS_3CHAR;
		
		if (registerRequest.getMail().length() < 4)
			return ERegisterResult.FAILED__IN__MAIL_LESS_3CHAR;

		if (!registerRequest.getPass().equals(registerRequest.getCpass()))
			return ERegisterResult.FAILED__IN__PASSWORD_NOT_CPASSWORD;
		
		return ERegisterResult.SUCCESS_IN__VALIDATED;
	}
	
	public ERegisterResult processRegister(final RequestRegister registerRequest)
	{
		final var validationRes = validateRegisterInput(registerRequest);
		if (validationRes == ERegisterResult.SUCCESS_IN__VALIDATED)
			return tryRegister(registerRequest);
		return validationRes;
	}
	
	private synchronized ERegisterResult tryRegister(final RequestRegister registerRequest)
	{
		try (final Connection con = databaseManager.getConnection();
			 final var pst = con.prepareStatement(INSERT_COLONIST))
		{
			final ValidRegistration validRegistration = new ValidRegistration(cryptoController, registerRequest);
			
			final var existResult = checkExisting(con, registerRequest.getColonistId(), registerRequest.getMail());
			if (existResult != ERegisterResult.SUCCESS__DB__READY)
				return existResult;

			validRegistration.insert(pst);
			
			pst.execute();
			
			return ERegisterResult.SUCCESS__DB__CREATED; // create success
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return ERegisterResult.FAILED__DB__INTERNAL_ERROR;
		}
	}
	
	private ERegisterResult checkExisting(final Connection con, final String username, final String mail)
	{
		final String oldLogin = getUsername(con, username);
		if (oldLogin != null)
			return ERegisterResult.FAILED__DB__USERNAME_EXISTS;
		
		final String oldMail = getMail(con, mail);
		if (oldMail != null)
			return ERegisterResult.FAILED__DB__MAIL_EXISTS;
		
		return ERegisterResult.SUCCESS__DB__READY;
	}

	public synchronized String getUsername(final Connection con, final String userId)
	{
		try (final var pst = con.prepareStatement(GET_ACCOUNT))
		{
			pst.setString(1, userId);
			try (final ResultSet rs = pst.executeQuery())
			{
				if (rs.next())
					return rs.getString("user_id");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public synchronized String getMail(final Connection con, final String mail)
	{
		try (final var pst = con.prepareStatement(GET_MAIL))
		{
			pst.setString(1, mail);
			try (final ResultSet rs = pst.executeQuery())
			{
				if (rs.next())
					return rs.getString("mail");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public ELoginResult validateLoginInput(final RequestLogin loginRequest)
	{
		if (loginRequest.getColonistId().length() < 4)
			return ELoginResult.FAILED__IN__USERNAME_LESS_3CHAR;

		if (loginRequest.getPass().length() < 4)
			return ELoginResult.FAILED__IN__PASSWORD_LESS_3CHAR;

		return ELoginResult.SUCCESS_IN__VALIDATED;
	}
	
	public ELoginResult processLogin(final RequestLogin loginRequest)
	{
		final var validationRes = validateLoginInput(loginRequest);
		if (validationRes == ELoginResult.SUCCESS_IN__VALIDATED)
			return tryLogin(loginRequest);
		return validationRes;
	}
	
	private synchronized ELoginResult tryLogin(final RequestLogin loginRequest)
	{
		try (final Connection con = databaseManager.getConnection();
			 final var pst = con.prepareStatement(GET_USER))
		{
			pst.setString(1, loginRequest.getColonistId());
			
			try (final ResultSet rs = pst.executeQuery())
			{
				if (rs.next())
				{
					final ValidRegistration validRegistration = new ValidRegistration(rs);
					if (validRegistration.checkPassword(cryptoController, loginRequest.getPass()))
					{
						loginRequest.authenticate(validRegistration);
						return ELoginResult.SUCCESS__DB__AUTHENTICATED;
					}
					else
						return ELoginResult.FAILED__DB__AUTHENTICATION_DENIED;
				}
			}
			
			return ELoginResult.FAILED__DB__USERNAME_NOT_EXISTS;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return ELoginResult.FAILED__DB__INTERNAL_ERROR;
		}
	}
	
	
	
	
	
	
	
	
}
