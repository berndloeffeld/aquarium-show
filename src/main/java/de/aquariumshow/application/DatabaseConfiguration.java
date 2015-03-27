package de.aquariumshow.application;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.aquariumshow.application.DatabaseConfiguration.DatabaseProperties;


@Configuration
@EnableConfigurationProperties(DatabaseProperties.class)
public class DatabaseConfiguration {

	@Autowired
	private DatabaseProperties databaseProperties;
	
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
			username = databaseProperties.getUsername();
			password = databaseProperties.getPassword();
			dbUrl = databaseProperties.getUrl();
		}

		BasicDataSource basicDataSource = new BasicDataSource();
		basicDataSource.setUrl(dbUrl);
		basicDataSource.setUsername(username);
		basicDataSource.setPassword(password);

		return basicDataSource;
	}
	
	@ConfigurationProperties("de.aquariumshow.db")
	public static class DatabaseProperties {
		private String username;
		private String password;
		private String url;
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		
		
	}
}
