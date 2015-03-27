package de.aquariumshow.controller;

import java.io.IOException;

import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.aquariumshow.exceptions.InvalidParameterException;
import de.aquariumshow.exceptions.NotFoundException;
import de.aquariumshow.model.Aquarium;
import de.aquariumshow.repositories.AquariumRepository;
import de.aquariumshow.validators.ParameterValidators;

@RestController
public class AquariumController {

	private Logger log = LoggerFactory.getLogger(AquariumController.class);
	
	@Autowired
	private AquariumRepository aquariumRepository;

	@RequestMapping(value = "/aquarium/{id}")
	public Aquarium getAquarium(@PathVariable("id") String id)
			throws ServletException, IOException, InvalidParameterException, NotFoundException {

		log.debug("Get Aquarium with ID: {}", id);
		Long idLong = ParameterValidators.getValidLong(id, "Aquarium ID");
		
		Aquarium result = aquariumRepository.findOne(idLong);
		
		if (null == result) {
			throw new NotFoundException("Aquarium with id " + idLong + " does not exist");
		}
		
		log.debug("Found Aquarium with ID {} and Name {}", result.getId(), result.getName());

		return result;
	}
}
