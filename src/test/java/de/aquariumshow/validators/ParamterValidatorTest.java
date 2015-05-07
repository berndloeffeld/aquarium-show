package de.aquariumshow.validators;

import org.junit.Assert;
import org.junit.Test;

import de.aquariumshow.exceptions.InvalidParameterException;

public class ParamterValidatorTest {

	@Test(expected=InvalidParameterException.class)
	public void testNotLongParameterOne() throws Exception{
		ParameterValidators.getValidLong("e3", "TestParameter");
	}
	
	@Test(expected=InvalidParameterException.class)
	public void testNotLongParameterTwo() throws Exception{
		// Wrong syntax in this case
		ParameterValidators.getValidLong("12L", "TestParameter");
	}
	
	@Test(expected=InvalidParameterException.class)
	public void testNotLongParameterThree() throws Exception{
		ParameterValidators.getValidLong(null, "TestParameter");
	}

	@Test(expected=InvalidParameterException.class)
	public void testNotLongParameterFour() throws Exception{
		ParameterValidators.getValidLong("", "TestParameter");
	}

	@Test(expected=InvalidParameterException.class)
	public void testNotLongParameterFive() throws Exception{
		ParameterValidators.getValidLong("22.4", "TestParameter");
	}

	@Test
	public void testValidLong() throws Exception {
		Assert.assertEquals(new Long(123456789), ParameterValidators.getValidLong("123456789", "TestParameter"));
	}
	
	@Test(expected=InvalidParameterException.class)
	public void testInvalidPathOne() throws Exception{
		ParameterValidators.getValidPath("aquarium/2");
	}
	
	@Test(expected=InvalidParameterException.class)
	public void testInvalidPathTwo() throws Exception{
		ParameterValidators.getValidPath("");
	}
	
	@Test
	public void testValidPath() throws Exception {
		Assert.assertEquals("/aquarium/2", ParameterValidators.getValidPath("/aquarium/2"));
	}
}
