package bg.assignment.bg.backend.manager;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("singleton")
public class DatabaseManager
{
	@Autowired
	@Qualifier("h2ds")
	DataSource h2DataSource;

    public Connection getConnection() throws SQLException
    {
		return h2DataSource.getConnection();
    }

}
