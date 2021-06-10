package bg.assignment.bg.backend.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

import bg.assignment.bg.backend.manager.DatabaseManager;
import bg.assignment.bg.backend.rest.model.ValidRegistration;

public class BgUser extends AUserInfo
{
	private static final String UPDATE_USER = "UPDATE bg_users SET verified=?, lastaccess_ts=? WHERE user_uuid=?";
	
	private boolean _verified;
	
	private boolean _update;
	
	private Instant lastaccessInstant;
	
	public BgUser(final String colonistId, final String mail, final UUID colonistUUID, final Instant registrationInstant)
	{
		super(colonistId, mail, colonistUUID, Timestamp.from(registrationInstant), Timestamp.from(registrationInstant));
	}
	
	public BgUser(final ValidRegistration validRegistration, final boolean verified)
	{
		super(validRegistration);
		_verified = verified;
	}
	
	public void onAccess()
	{
		setLastAccess();
	}
	
	public void setVerified()
	{
		_verified = true;
		_update = true;
	}
	
	public void setLastAccess()
	{
		lastaccessInstant = Instant.now();
		_update = true;
	}
	
	public boolean isVerified()
	{
		return _verified;
	}
	
	public boolean needsUpdate()
	{
		return _update;
	}
	
	public void store(final DatabaseManager databaseManager)
	{
		try (final Connection con = databaseManager.getConnection();
			 final PreparedStatement pst = con.prepareStatement(UPDATE_USER))
		{
			pst.setBoolean(1, _verified);
			pst.setTimestamp(2, lastaccessInstant == null ? null : Timestamp.from(lastaccessInstant));
			
			pst.setString(3, getColonistUUID().toString());
			
			pst.executeUpdate();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
