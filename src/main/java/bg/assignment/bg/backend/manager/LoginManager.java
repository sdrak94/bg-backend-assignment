package bg.assignment.bg.backend.manager;

import java.sql.Connection;
import java.sql.ResultSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import bg.assignment.bg.backend.model.enums.ERegisterResult;
import bg.assignment.bg.backend.rest.model.Register;

@Service
@Scope("singleton")
public class LoginManager
{
	@Autowired
	private CryptManager cryptoController;

	private static final String GET_ACCOUNT = "SELECT login FROM accounts WHERE login = ?";

	private static final String GET_MAIL = "SELECT gmail FROM accounts WHERE gmail = ?";
	
	private static final String INSERT_COLONIST = "INSERT INTO bg_users (user_id, pass_md5, user_uuid, registration_ts, lastaccess_ts) VALUES (?, ?, ?, ?, ?)";
	
	@Autowired
	private DatabaseManager databaseManager;
	
	public ERegisterResult validateRegisterInput(final Register register)
	{
		if (register.getColonistId().length() < 4)
			return ERegisterResult.FAILED__IN__USERNAME_LESS_3CHAR;
		
		if (register.getPass().length() < 4)
			return ERegisterResult.FAILED__IN__PASSWORD_LESS_3CHAR;

		if (!register.getPass().equals(register.getCpass()))
			return ERegisterResult.FAILED__IN__PASSWORD_NOT_CPASSWORD;
		
		return ERegisterResult.SUCCESS_IN__VALIDATED;
	}
	
	public ERegisterResult processRegister(final Register login)
	{
		final var validationRes = validateRegisterInput(login);
		if (validationRes == ERegisterResult.SUCCESS_IN__VALIDATED)
			return tryRegister(login);
		return validationRes;
	}
	
	private synchronized ERegisterResult tryRegister(final Register register)
	{
		try (final Connection con = databaseManager.getConnection();
			 final var pst = con.prepareStatement(INSERT_COLONIST))
		{
			final var existResult = checkExisting(con, register.getColonistId(), register.getEmail());
			if (existResult != ERegisterResult.SUCCESS__DB__READY)
				return existResult;

			pst.setString(1, register.getColonistId());
			pst.setString(2, register.getPass());
			pst.setString(4, cryptoController.md5digest(register.getPass()));
			pst.setString(5, register.getPass());
			pst.setString(6, "");
			
			pst.execute();
			
			return ERegisterResult.SUCCESS__DB__CREATED; // create success
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return ERegisterResult.FAILED__DB__INTERNAL_ERROR;
		}
	}
	
	private ERegisterResult checkExisting(final Connection con, final String username, final String gmail)
	{
		final String oldLogin = getUsername(con, username);
		if (oldLogin != null)
			return ERegisterResult.FAILED__DB__USERNAME_EXISTS;
		
		final String oldMail = getMail(con, gmail);
		if (oldMail != null)
			return ERegisterResult.FAILED__DB__MAIL_EXISTS;
		
		return ERegisterResult.SUCCESS__DB__READY;
	}
	
	@CacheEvict(value="username", allEntries=true)
	public synchronized String getUsername(final Connection con, final String username)
	{
		try (final var pst = con.prepareStatement(GET_ACCOUNT))
		{
			pst.setString(1, username);
			try (final ResultSet rs = pst.executeQuery())
			{
				if (rs.next())
					return rs.getString("login");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	@CacheEvict(value="mail", allEntries=true)
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
}
