package gov.polisen.ainappen;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * Class used for hashing salt+password in AinAppen
 * @author Joakim
 *
 */
public class Hasher {
	/**
	 * Method used to hash salt+password for AinAppen
	 * returns the result in string format! Uses "ISO-8859-1" for 1-1 way formatting
	 * purposes!
	 * @param input
	 * @return
	 * @throws NoSuchAlgorithmException 
	 */
	public String getSHA256Hash(String input) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");

		md.update(input.getBytes(Charset.forName("ISO-8859-1")));
		StringBuffer hexString = new StringBuffer();
		byte[] bytes = md.digest();
		for (int i = 0; i < bytes.length; i++) {
			hexString.append(String.format("%02x", bytes[i] & 0xff));
		}
		return hexString.toString();
	}
}