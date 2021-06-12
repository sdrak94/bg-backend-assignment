package bg.assignment.bg.backend.rest.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

import bg.assignment.bg.backend.manager.CryptManager;
import bg.assignment.bg.backend.model.AUserInfo;
import bg.assignment.bg.backend.rest.model.requests.RequestUserRegister;

public class ValidRegistration extends AUserInfo
{
	private final String _passwordHash;
	
	public ValidRegistration(final CryptManager cryptoController, final RequestUserRegister register) throws Exception
	{
		super(register.getColonistId(), register.getMail(), UUID.randomUUID(), Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));
		
		_passwordHash = cryptoController.md5digest(register.getPass());
	}
	
	public ValidRegistration(final ResultSet rs) throws SQLException
	{
		super(rs.getString("user_id"),
				rs.getString("mail"),
				UUID.fromString(rs.getString("user_uuid")),
				rs.getTimestamp("registration_ts"),
				rs.getTimestamp("lastaccess_ts"));
		
		_passwordHash = rs.getString("pass_md5"); // ??
	}
	
	//user_id, pass_md5, mail, verified, user_uuid, registration_ts, lastaccess_ts
	public void insert(final PreparedStatement pst) throws Exception
	{
		pst.setString(1, getColonistId());
		pst.setString(2, _passwordHash);
		pst.setString(3, getMail());
		pst.setInt(4, 0);
		pst.setString(5, getColonistUUID().toString());
		
		final Timestamp registrationTs = getRegistrationTimestamp();
		
		pst.setTimestamp(6, registrationTs);
		pst.setTimestamp(7, registrationTs);
	}

	public boolean checkPassword(final CryptManager cryptoController, final String password) throws Exception
	{
		return _passwordHash.equals(cryptoController.md5digest(password));
	}
	
	public String getPasswordHash()
	{
		return _passwordHash;
	}
	
}
