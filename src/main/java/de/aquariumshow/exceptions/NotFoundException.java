package de.aquariumshow.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Nothing found")
public class NotFoundException extends Exception {
	/**
	 * UID
	 */
	private static final long serialVersionUID = 1L;

	Logger log = LoggerFactory.getLogger(this.getClass());

	public NotFoundException(String message) {
		super(message);
		log.error(message);
	}
}
