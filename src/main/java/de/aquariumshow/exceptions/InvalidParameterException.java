package de.aquariumshow.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Invalid Parameter")
public class InvalidParameterException extends Exception {
	/**
	 * UID
	 */
	private static final long serialVersionUID = 1L;

	Logger log = LoggerFactory.getLogger(InvalidParameterException.class);

	public InvalidParameterException(String message) {
		super(message);
		log.error(message);
	}
	

}
