package bg.assignment.bg.backend.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import bg.assignment.bg.backend.model.BgUser;
import bg.assignment.bg.backend.rest.model.ValidRegistration;


@Service
@Scope("singleton")
public class UserManager implements UserDetailsService
{
	private static final String SELECT_USER__BY_UUID = "SELECT * FROM bg_users WHERE user_uuid = ?";
	private static final String SELECT_USER__BY_USID = "SELECT * FROM bg_users WHERE user_id = ?";
	
	@Autowired
	private DatabaseManager databaseManager;

	@Cacheable(value="defaultCache", key="#uuid",unless="#result!=null")
	public BgUser getBgUserByUUID(final String uuid)
	{
		try (final Connection con = databaseManager.getConnection();
			 final PreparedStatement pst = con.prepareStatement(SELECT_USER__BY_UUID))
		{
			pst.setString(1, uuid);
			
			try (final ResultSet rs = pst.executeQuery())
			{
				if (rs.next())
				{
					final ValidRegistration validRegistration = new ValidRegistration(rs);
					final boolean verified = rs.getBoolean("verified");
					return new BgUser(validRegistration, verified);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	public BgUser getBgUserByColonistId(final String colonistId)
	{
		try (final Connection con = databaseManager.getConnection();
				 final PreparedStatement pst = con.prepareStatement(SELECT_USER__BY_USID))
			{
				pst.setString(1, colonistId);
				
				try (final ResultSet rs = pst.executeQuery())
				{
					if (rs.next())
					{
						final ValidRegistration validRegistration = new ValidRegistration(rs);
						final boolean verified = rs.getBoolean("verified");
						return new BgUser(validRegistration, verified);
					}
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			
			return null;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	{
		return getBgUserByColonistId(username);
	}
}
