package de.aquariumshow.api.controller;

import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import de.aquariumshow.exceptions.InvalidParameterException;
import de.aquariumshow.model.Comment;
import de.aquariumshow.repositories.CommentRepository;

@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {
	private Logger log = LoggerFactory.getLogger(CommentController.class);

	@Autowired
	private CommentRepository commentRepository;

	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<?> add(@RequestBody Comment input) throws InvalidParameterException {
		
		log.debug("Save new Comment {}", input);
		Comment result = commentRepository.save(new Comment(input.getAuthor(), input.getText()));
		log.debug("Comment saved with ID {}", result.getId());
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(result.getId()).toUri());
		return new ResponseEntity<>(result, httpHeaders, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public Collection<Comment> getAllComments() {
		log.debug("Return ALL comments");
		Collection<Comment> result = new ArrayList<Comment>();
		
		Iterable<Comment> all = commentRepository.findAll();
		for (Comment comment : all) {
			result.add(comment);
		}
		
		return result;
	}
}
