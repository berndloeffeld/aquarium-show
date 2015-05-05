package de.aquariumshow.application;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
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

@Configuration
@EnableSocial
public class SocialConfiguration implements SocialConfigurer {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private AccountConnectionSignUpService accountConnectionSignUpService;

	@Override
	public void addConnectionFactories(
			ConnectionFactoryConfigurer connectionFactoryConfigurer,
			Environment environment) {
		
		//TODO config issues
		String key = "959426730747977";
//				environment.getProperty("twitter.consumerKey");
		String secret = "d410dc30bbe22be2e714c202d75216ea";
//			environment.getProperty("twitter.consumerSecret");
		connectionFactoryConfigurer
				.addConnectionFactory(new FacebookConnectionFactory(key, secret));
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
}