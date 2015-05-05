package de.aquariumshow.services;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Service;

import de.aquariumshow.model.ASUser;
import de.aquariumshow.model.UserRole;
import de.aquariumshow.repositories.UserRepository;
import de.aquariumshow.repositories.UserRoleRepository;

@Service
public class AccountConnectionSignUpService implements ConnectionSignUp {

    private final Logger log = LoggerFactory.getLogger(AccountConnectionSignUpService.class);
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;
    
    public String execute(Connection<?> connection) {
        org.springframework.social.connect.UserProfile profile = connection.fetchUserProfile();
        String userId = UUID.randomUUID().toString();
        
        log.debug("Created user-id: {}", userId);
        ASUser user = new ASUser();
        user.setGeneratedSocialUserId(userId);
        user.setEmail(profile.getEmail());
        userRepository.save(user);
        
        UserRole role = new UserRole();
        role.setRoleName("USER");
        role.setUser(user);
        role.setVersion(1L);
        userRoleRepository.save(role);

        log.debug("Saved user {} with role {}", role);
        return userId;
    }
}