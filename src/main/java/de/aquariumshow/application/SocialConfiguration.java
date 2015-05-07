package de.aquariumshow.application;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.security.AuthenticationNameUserIdSource;

import de.aquariumshow.services.AccountConnectionSignUpService;

import de.aquariumshow.application.SocialConfiguration.SocialProperties;

@Configuration
@EnableSocial
@EnableConfigurationProperties(SocialProperties.class)
public class SocialConfiguration implements SocialConfigurer {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private SocialProperties socialProperties;
	
	@Autowired
	private AccountConnectionSignUpService accountConnectionSignUpService;

	@Override
	public void addConnectionFactories(
			ConnectionFactoryConfigurer connectionFactoryConfigurer,
			Environment environment) {

		FacebookConnectionFactory facebookConnectionFactory;

		// PG Config is always available in heroku prod
		String herokuEnv = System.getenv("HEROKU_POSTGRESQL_AQUA_URL");
		
		if (null != herokuEnv) {
			facebookConnectionFactory = new FacebookConnectionFactory(
					System.getenv("FACEBOOK_APP_ID"), System.getenv("FACEBOOK_APP_SECRET"));
		} else {
			facebookConnectionFactory = new FacebookConnectionFactory(
					socialProperties.getFacebookAppId(), socialProperties.getFacebookAppSecret());
		}

		connectionFactoryConfigurer
				.addConnectionFactory(facebookConnectionFactory);
	}

	@Override
	public UserIdSource getUserIdSource() {
		return new AuthenticationNameUserIdSource();
	}

	@Override
	public UsersConnectionRepository getUsersConnectionRepository(
			ConnectionFactoryLocator connectionFactoryLocator) {

		JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(
				dataSource, connectionFactoryLocator, Encryptors.noOpText());
		repository.setConnectionSignUp(accountConnectionSignUpService);
		return repository;
	}
	
	@ConfigurationProperties("de.aquariumshow.social")
	public static class SocialProperties {
		private String facebookAppId;
		private String facebookAppSecret;
		public String getFacebookAppId() {
			return facebookAppId;
		}
		public void setFacebookAppId(String facebookAppId) {
			this.facebookAppId = facebookAppId;
		}
		public String getFacebookAppSecret() {
			return facebookAppSecret;
		}
		public void setFacebookAppSecret(String facebookAppSecret) {
			this.facebookAppSecret = facebookAppSecret;
		}
	}
}