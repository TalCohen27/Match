package utilsAndData;

import java.sql.DriverManager;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import snaq.db.ConnectionPool;

import com.mysql.jdbc.Driver;

/* This class is responsible to connect to the database 
 * and every other class which use the DB should get an handle
 * to the connection pool using this class.
 * IT HAS ONLY STATIC MEMEBRS 
 */
public class CopyOfDBConn {

	static ConnectionPool pool;
	private static final String USER_NAME = "root";
	private static final String PASSWORD = "";

	static {
		try {
			System.out.println("Loading the driver...");
			Class<?> c = Class.forName("com.mysql.jdbc.Driver");
			Driver driver = (Driver) c.newInstance();
			DriverManager.registerDriver(driver);
			System.out.println("Loading successed  ---------------");

			String jsonEnvVars = java.lang.System.getenv("VCAP_SERVICES");
			if (jsonEnvVars != null) {
				parseUrlFromEnvVarsAndConnect(jsonEnvVars);
			} else {
				// Runs locally - only for maintenance
				String url = "jdbc:mysql://localhost/my_db";
				System.out.println("Connected local host url=" + url);

				// Note: idleTimeout is specified in seconds.
				pool = new ConnectionPool("local", 10, 0, 3600, url, USER_NAME,
						PASSWORD);
				System.out.println("pool=" + pool);
				pool.registerShutdownHook();
			}

			System.out.println((new StringBuilder("pool successed. pool="))
					.append(pool).toString());
		} catch (ClassNotFoundException ex) {
			System.err.println((new StringBuilder("error loading:")).append(
					ex.getMessage()).toString());
		} catch (SQLException ex) {
			System.err.println((new StringBuilder("error loading:")).append(
					ex.getMessage()).toString());
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

	}

	private static void parseUrlFromEnvVarsAndConnect(String jsonEnvVars) {
		String url = "";
		try {
			JSONObject jsonObject = new JSONObject(jsonEnvVars);
			JSONArray jsonArray = jsonObject.getJSONArray("mysql-5.1");
			jsonObject = jsonArray.getJSONObject(0);
			jsonObject = jsonObject.getJSONObject("credentials");
			String host = jsonObject.getString("host");
			System.out.println("parseUrlFromEnvVarsAndConnect host=" + host);
			String port = jsonObject.getString("port");
			System.out.println("parseUrlFromEnvVarsAndConnect port=" + port);
			String dbName = jsonObject.getString("name");
			System.out
					.println("parseUrlFromEnvVarsAndConnect dbName=" + dbName);
			String username = jsonObject.getString("username");
			System.out.println("parseUrlFromEnvVarsAndConnect username="
					+ username);
			String password = jsonObject.getString("password");

			System.out.println("parseUrlFromEnvVarsAndConnect password="
					+ password);

			url = "jdbc:mysql://" + host + ":" + port + "/" + dbName;

			pool = new ConnectionPool("local", 10, 0, 3600, url, username,
					password);

			pool.registerShutdownHook();
		} catch (JSONException e) {
			System.err.println("Conn.connect: " + e.getMessage());
		}
	}

	public static ConnectionPool getPool() {
		return pool;
	}

}
