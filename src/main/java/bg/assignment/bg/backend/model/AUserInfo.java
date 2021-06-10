package bg.assignment.bg.backend.model;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

public abstract class AUserInfo
{
	private final String _colonistId;
	private final String _mail;
	private final UUID _colonistUUID;
	private final Instant _registrationInstant;
	private final Instant _lastaccessInstant;

	public AUserInfo(final String colonistId, final String mail, final UUID colonistUUID, final Timestamp registrationInstant, final Timestamp lastaccessInstant)
	{
		_colonistId = colonistId;
		_mail = mail;
		_colonistUUID = colonistUUID;
		_registrationInstant = registrationInstant == null ? null : registrationInstant.toInstant();
		_lastaccessInstant = lastaccessInstant == null ? null : lastaccessInstant.toInstant();
	}
	
	public AUserInfo(final AUserInfo copyOf)
	{
		this(copyOf.getColonistId(), copyOf.getMail(), copyOf.getColonistUUID(), copyOf.getRegistrationTimestamp(), copyOf.getLastaccessTimestamp());
	}
	
	public String getColonistId()
	{
		return _colonistId;
	}

	public String getMail() 
	{
		return _mail;
	}

	public UUID getColonistUUID()
	{
		return _colonistUUID;
	}

	public Instant getRegistrationTemporal()
	{
		return _registrationInstant;
	}
	
	public Timestamp getRegistrationTimestamp()
	{
		return Timestamp.from(_registrationInstant);
	}

	public Instant getLastaccessTemporal()
	{
		return _lastaccessInstant;
	}
	
	public Timestamp getLastaccessTimestamp()
	{
		return _lastaccessInstant == null ? null : Timestamp.from(_lastaccessInstant);
	}
}