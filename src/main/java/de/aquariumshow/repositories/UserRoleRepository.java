package de.aquariumshow.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import de.aquariumshow.model.UserRole;

@Repository
public interface UserRoleRepository extends CrudRepository<UserRole, Long> {

}
