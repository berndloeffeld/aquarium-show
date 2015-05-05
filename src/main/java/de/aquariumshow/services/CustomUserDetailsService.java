package de.aquariumshow.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	private UserRepository userRepository;

	@Transactional(readOnly = true)
	@Override
	public UserDetails loadUserByUsername(final String id)
			throws UsernameNotFoundException {
		// Identifier is either the generated social id or the email.

		ASUser user;
		List<GrantedAuthority> authorities = null;

		user = userRepository.findOneByGeneratedSocialUserId(id);
		if (null == user) {
			user = userRepository.findOneByEmail(id);
		}

		if (null != user) {
			authorities = buildUserAuthority(user.getRoles());
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