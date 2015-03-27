package de.aquariumshow.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.aquariumshow.model.Aquarium;

@RestController
public class AquariumController {

	@RequestMapping(value = "/aquarium/{id}")
	public Aquarium getAquarium(@PathVariable("id") String id)
			throws ServletException, IOException {
		showDatabase();
		Aquarium result = new Aquarium();
		result.setId(id);
		result.setName("Name " + id);
		return result;
	}

	private void showDatabase() throws ServletException, IOException {
		Connection connection = null;
		try {
			connection = getConnection();

			Statement stmt = connection.createStatement();
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)");
			stmt.executeUpdate("INSERT INTO ticks VALUES (now())");
			ResultSet rs = stmt.executeQuery("SELECT tick FROM ticks");

			String out = "Hello!\n";
			while (rs.next()) {
				out += "Read from Database: " + rs.getTimestamp("tick") + "\n";
			}
			System.out.println(out);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException e) {
				}
		}
	}

	private Connection getConnection() throws URISyntaxException, SQLException {

		String herokuEnv = System.getenv("HEROKU_POSTGRESQL_AQUA_URL");

		String username;
		String password;
		String dbUrl;

		if (null != herokuEnv) {

			URI dbUri = new URI(herokuEnv);

			username = dbUri.getUserInfo().split(":")[0];
			password = dbUri.getUserInfo().split(":")[1];
			int port = dbUri.getPort();

			dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ":" + port
					+ dbUri.getPath();

		} else {

			username = "postgres";
			password = "postgres";

			dbUrl = "jdbc:postgresql://localhost:5432/aqua";
		}
		return DriverManager.getConnection(dbUrl, username, password);
	}
}
