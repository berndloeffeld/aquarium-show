package de.aquariumshow.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.aquariumshow.exceptions.InvalidParameterException;
import de.aquariumshow.model.Aquarium;
import de.aquariumshow.validators.ParameterValidators;

@RestController
public class AquariumController {

	private Logger log = LoggerFactory.getLogger(AquariumController.class);

	@RequestMapping(value = "/aquarium/{id}")
	public Aquarium getAquarium(@PathVariable("id") String id)
			throws ServletException, IOException, InvalidParameterException {

		log.debug("Get Aquarium with ID: {}", id);
		Long idLong = ParameterValidators.getValidLong(id, "Aquarium ID");

		showDatabase();
		Aquarium result = new Aquarium();
		result.setId(idLong);
		result.setName("Name " + id);

		log.info("Aquarium {} is named {}", result.getId(), result.getName());
		return result;
	}

	private void showDatabase() throws ServletException, IOException {
		Connection connection = null;
		try {
			connection = getConnection();
		} catch (Exception e) {
			log.error("Error while getting database connection", e);
		} finally {
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException e) {
					log.error("Error while closing db", e);
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
