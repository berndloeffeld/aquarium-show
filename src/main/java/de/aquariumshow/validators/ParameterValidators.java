package de.aquariumshow.validators;

import de.aquariumshow.exceptions.InvalidParameterException;

/**
 * Validate parameters and throw Exceptions with HTTP Codes if invalid
 * 
 * @author bloeffeld
 *
 */
public abstract class ParameterValidators {

	/**
	 * 
	 * @param param
	 *            - the parameter to validate
	 * @return the param parsed into a long
	 * 
	 * @throws InvalidParameterException
	 *             - a 400 HTTP code Exception
	 */
	public static Long getValidLong(String param, String paramName) throws InvalidParameterException {
		Long result;
		try {
			result = Long.parseLong(param);
		} catch (Exception e) {
			throw new InvalidParameterException("Content of parameter " + paramName + ": " + param + " is not a valid Long");
		}
		return result;
	}

	public static String getValidPath(String param) throws InvalidParameterException {
		if(param.matches("^/{1}.+/{1}.+$")) {
			return param;
		} else {
			throw new InvalidParameterException("Parameter " + param + " is not a valid path");
		}
	}
}
