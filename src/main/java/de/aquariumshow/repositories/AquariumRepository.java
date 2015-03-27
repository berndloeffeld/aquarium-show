package de.aquariumshow.repositories;

import org.springframework.data.repository.CrudRepository;

import de.aquariumshow.model.Aquarium;

public interface AquariumRepository extends CrudRepository<Aquarium, Long> {

}
