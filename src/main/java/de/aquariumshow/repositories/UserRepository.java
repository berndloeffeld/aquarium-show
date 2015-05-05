package de.aquariumshow.repositories;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import de.aquariumshow.model.ASUser;

@Repository
@Qualifier(value = "userRepository")
public interface UserRepository extends CrudRepository<ASUser, Long> {
    public ASUser findByUsername(String username);
    public ASUser findOneByEmail(String email);
    public ASUser findOneByGeneratedSocialUserId(String id);
}