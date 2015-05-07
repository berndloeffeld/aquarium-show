package de.aquariumshow.api.controller;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import de.aquariumshow.application.Application;
import de.aquariumshow.model.Aquarium;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class AquariumControllerTest {
	private MockMvc mvc;

	@Autowired
	AquariumController aquariumController;

	@Autowired
	private WebApplicationContext wac;

	@Before
	public void setUp() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(wac).build();

		Aquarium a1 = new Aquarium(1, "Steinwüste");
		Aquarium a2 = new Aquarium(2, "Amazonas Sumpf");

		aquariumController.add(a1);
		aquariumController.add(a2);

	}

	@Test
	public void getAquarium() throws Exception {
		mvc.perform(
				MockMvcRequestBuilders.get("/api/v1/aquarium/1").accept(
						MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().string(containsString("Steinwüste")))
				.andExpect(content().string(not(containsString("Sumpf"))));

		mvc.perform(
				MockMvcRequestBuilders.get("/api/v1/aquarium/999").accept(
						MediaType.APPLICATION_JSON)).andExpect(
				status().isNotFound());

	}
}
