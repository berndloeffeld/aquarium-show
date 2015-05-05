package de.aquariumshow.api.controller;

import java.io.IOException;

import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import de.aquariumshow.exceptions.InvalidParameterException;
import de.aquariumshow.exceptions.NotFoundException;
import de.aquariumshow.model.Aquarium;
import de.aquariumshow.repositories.AquariumRepository;
import de.aquariumshow.validators.ParameterValidators;

@RestController
@RequestMapping("/api/v1/aquarium")
public class AquariumController {

	private Logger log = LoggerFactory.getLogger(AquariumController.class);
	
	@Autowired
	private AquariumRepository aquariumRepository;

	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<?> add(@RequestBody Aquarium input) throws InvalidParameterException {
		
		log.debug("Save new Aquarium {}", input);
		Aquarium result = aquariumRepository.save(new Aquarium(input.getId(), input.getName()));
		log.debug("Aquarium saved with ID {}", result.getId());
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(result.getId()).toUri());
		return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);
	}

	
	
	@RequestMapping(value = "/{id}")
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
