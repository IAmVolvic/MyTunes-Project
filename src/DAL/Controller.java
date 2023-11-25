package DAL;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;

public class Controller {
    private final SQLServerDataSource ds;
    Dotenv dotenv = Dotenv.load();

    public Controller() {
        ds = new SQLServerDataSource();

        ds.setDatabaseName(dotenv.get("DBDB"));
        ds.setUser(dotenv.get("DBUSER"));
        ds.setPassword(dotenv.get("DBPASSWORD"));
        ds.setServerName(dotenv.get("DBSERVERNAME"));

        ds.setTrustServerCertificate(true);
    }

    public Connection getConnection() throws SQLServerException {
        return ds.getConnection();
    }
}
