package de.aquariumshow.application;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfiguration {

	@Bean
	public BasicDataSource dataSource() throws URISyntaxException {

		String herokuEnv = System.getenv("HEROKU_POSTGRESQL_AQUA_URL");

		String username;
		String password;
		String dbUrl;

		if (null != herokuEnv) {
			URI dbUri = new URI(System.getenv("DATABASE_URL"));

			username = dbUri.getUserInfo().split(":")[0];
			password = dbUri.getUserInfo().split(":")[1];
			dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':'
					+ dbUri.getPort() + dbUri.getPath();
		} else {
			username = "postgres";
			password = "postgres";
			dbUrl = "jdbc:postgresql://localhost:5432/aqua";
		}

		BasicDataSource basicDataSource = new BasicDataSource();
		basicDataSource.setUrl(dbUrl);
		basicDataSource.setUsername(username);
		basicDataSource.setPassword(password);

		return basicDataSource;
	}
}
