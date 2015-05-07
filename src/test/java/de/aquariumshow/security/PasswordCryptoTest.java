package de.aquariumshow.security;

import org.junit.Test;

public class PasswordCryptoTest {

	public void testCypt() {
		PasswordCrypto.getInstance().encrypt("rums");
	}
}
