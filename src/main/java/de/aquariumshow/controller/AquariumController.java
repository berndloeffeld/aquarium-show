package de.aquariumshow.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.aquariumshow.model.Aquarium;

@RestController
public class AquariumController {

	@RequestMapping(value="/aquarium/{id}")
	public Aquarium getAquarium(@PathVariable("id") String id){
		Aquarium result = new Aquarium();
		result.setId(id);
		result.setName("Name " + id);
		return result;
	}
}
