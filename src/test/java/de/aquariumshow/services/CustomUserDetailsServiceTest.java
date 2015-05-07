package de.aquariumshow.services;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import de.aquariumshow.model.ASUser;
import de.aquariumshow.model.UserRole;
import de.aquariumshow.repositories.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class CustomUserDetailsServiceTest {

	private CustomUserDetailsService customUserDetailsService;
	
	@Mock
	private UserRepository userRepository;
	
	@Before
	public void setup() {
		customUserDetailsService = new CustomUserDetailsService(userRepository);
	}
	
	@Test(expected=UsernameNotFoundException.class)
	public void testNullUser() {
		customUserDetailsService.loadUserByUsername(null);
	}
	
	@Test(expected=UsernameNotFoundException.class)	
	public void testWrongUser(){
		customUserDetailsService.loadUserByUsername("wrongId");
	}
	
	@Test
	public void findUserByEmail() {
		ASUser user = getTestUser("meine@email.com", null);
		when(userRepository.findOneByGeneratedSocialUserId(any(String.class))).thenReturn(null);
		when(userRepository.findOneByEmail("meine@email.com")).thenReturn(user);
		
		Assert.assertEquals("meine@email.com", customUserDetailsService.loadUserByUsername("meine@email.com").getUsername());
	}
	
	@Test
	public void findUserBySocialID() {
		ASUser user = getTestUser("meine@email.com", "1234");
		when(userRepository.findOneByGeneratedSocialUserId("1234")).thenReturn(user);
		when(userRepository.findOneByEmail(any(String.class))).thenReturn(null);
		
		Assert.assertEquals("1234", customUserDetailsService.loadUserByUsername("1234").getUsername());
	}

	private ASUser getTestUser(final String email, final String uuid) {
		ASUser user = new ASUser();
		user.setEmail(email);
		user.setUsername(email);
		user.setGeneratedSocialUserId(uuid);
		user.setPassword("Passwort");
		user.setId(222L);
		Set<UserRole> roles = new HashSet<UserRole>();
		UserRole role = new UserRole();
		role.setId(1L);
		role.setRoleName("USER");
		roles.add(role);
		user.setRoles(roles);
		return user;
	}
}
