package de.aquariumshow.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.aquariumshow.model.ASUser;
import de.aquariumshow.model.UserRole;
import de.aquariumshow.repositories.UserRepository;

@Service
@Qualifier("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);

	private UserRepository userRepository;
	
	@Inject
	public CustomUserDetailsService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Transactional(readOnly = true)
	@Override
	public UserDetails loadUserByUsername(final String id)
			throws UsernameNotFoundException {
		// Identifier is either the generated social id or the email.

		if (null == id) {
			throw new UsernameNotFoundException("ID must not be null");
		}
		
		log.debug("Try to find user by Identifier {}", id);
		
		ASUser user;
		List<GrantedAuthority> authorities = null;

		user = userRepository.findOneByGeneratedSocialUserId(id);
		if (null == user) {
			log.debug("User not found by generated social identifier {}. Try by email", id);
			user = userRepository.findOneByEmail(id);
		}

		if (null != user) {
			authorities = buildUserAuthority(user.getRoles());
		} else {
			throw new UsernameNotFoundException("No user found for ID " + id);
		}

		return buildUserForAuthentication(user, authorities);

	}

	private User buildUserForAuthentication(ASUser user,
			List<GrantedAuthority> authorities) {
		if (null != user.getGeneratedSocialUserId()) {
			return new User(user.getGeneratedSocialUserId(),
					user.getGeneratedSocialUserId(), authorities);
		} else {
			return new User(user.getUsername(), user.getPassword(), authorities);
		}
	}

	private List<GrantedAuthority> buildUserAuthority(Set<UserRole> userRoles) {

		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

		// Build user's authorities
		for (UserRole userRole : userRoles) {
			setAuths.add(new SimpleGrantedAuthority(userRole.getRoleName()));
		}

		return new ArrayList<GrantedAuthority>(setAuths);
	}
}