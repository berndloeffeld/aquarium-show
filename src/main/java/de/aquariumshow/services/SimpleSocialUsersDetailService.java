package de.aquariumshow.services;

import javax.inject.Inject;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class SimpleSocialUsersDetailService implements SocialUserDetailsService {

    private UserDetailsService userDetailsService;
	
	@Inject
	public SimpleSocialUsersDetailService(final UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException, DataAccessException {
        UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
        if (null == userDetails) {
        	throw new UsernameNotFoundException("No User with ID " + userId + " found");
        }
        return new SocialUser(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
    }

}