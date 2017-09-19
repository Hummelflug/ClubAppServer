package de.hummelflug.clubapp.server.auth;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordHashHelper {

	private static final int iterations = 1000;
	private static final int hexBase = 16;
	private static final int keyLength = 256;
	private static final int saltLength = 32;
	
	public static String generatePasswordHash(String password) 
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		if (password == null || password.length() == 0) {
			throw new IllegalArgumentException("Password cannot be empty!");
		}
		
		byte[] salt = getSalt();
		byte[] hash = hash(password, salt);
        return (toHex(salt) + ":" + toHex(hash));
	}
	
	private static byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[saltLength];
        secureRandom.nextBytes(salt);
        return salt;
    }
	
	private static byte[] hash(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
		char[] chars = password.toCharArray();
		
		PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, keyLength);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();
		
        return hash;
	}
	
	private static String toHex(byte[] array) throws NoSuchAlgorithmException {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(hexBase);
        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        } else {
            return hex;
        }
    }
	
	public static boolean validatePassword(String password, String storedPassword)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		String[] parts = storedPassword.split(":");
        byte[] salt = fromHex(parts[0]);
        String passwordHash = toHex(hash(password, salt));
		return passwordHash.equals(parts[1]);
	}
	
	private static byte[] fromHex(String hex) throws NoSuchAlgorithmException {
		byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hex.substring((2 * i), (2 * i + 2)), hexBase);
        }
        return bytes;
	}
	
}
