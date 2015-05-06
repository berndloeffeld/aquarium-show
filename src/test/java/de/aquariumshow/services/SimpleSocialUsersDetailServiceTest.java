package de.aquariumshow.services;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.HashSet;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;

@RunWith(MockitoJUnitRunner.class)
public class SimpleSocialUsersDetailServiceTest {

	private final class TestUserDetails implements UserDetails {
		private static final long serialVersionUID = 1L;

		@Override
		public boolean isEnabled() {
			return true;
		}

		@Override
		public boolean isCredentialsNonExpired() {
			return true;
		}

		@Override
		public boolean isAccountNonLocked() {
			return true;
		}

		@Override
		public boolean isAccountNonExpired() {
			return true;
		}

		@Override
		public String getUsername() {
			return "DiDumm";
		}

		@Override
		public String getPassword() {
			return "dddddeeeee";
		}

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			HashSet<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
			authorities.add(new SimpleGrantedAuthority("USER"));
			return authorities;
		}
	}

	@Mock
	private UserDetailsService userDetailsService;

	private SimpleSocialUsersDetailService simpleSocialUsersDetailService;

	private String userIdOne;

	@Before
	public void setup() {
		simpleSocialUsersDetailService = new SimpleSocialUsersDetailService(
				userDetailsService);
	}

	@Test(expected = UsernameNotFoundException.class)
	public void doNotFindAUser() throws Exception {
		when(userDetailsService.loadUserByUsername(any(String.class)))
				.thenReturn(null);
		simpleSocialUsersDetailService.loadUserByUserId("dkdkd");
	}

	@Test(expected = UsernameNotFoundException.class)
	public void findNullUser() {
		simpleSocialUsersDetailService.loadUserByUserId(null);
	}

	@Test
	public void findOneUser() {
		when(userDetailsService.loadUserByUsername(any(String.class)))
				.thenReturn(new TestUserDetails());
		SocialUserDetails loaded = simpleSocialUsersDetailService
				.loadUserByUserId(userIdOne);
		Assert.assertEquals("DiDumm", loaded.getUsername());
	}
}
