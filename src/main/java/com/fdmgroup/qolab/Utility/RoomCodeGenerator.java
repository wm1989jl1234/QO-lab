package com.fdmgroup.qolab.Utility;

import java.util.Random;

/**
 * Utility class used to generate the random id for the room. 
 * Used to Room -> roomId
 *  
 * @author Birat
 * 
 * @since 2020-10-23
 *
 */
public class RoomCodeGenerator {
	/**
	 * Generates 6 character long uppercase unique alphanumeric room id.  
	 * 
	 * @return roomId
	 */
	public static String roomIdGenerator() {
		int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 6;
        Random random = new Random();
        
        String generatedString = random.ints(leftLimit, rightLimit + 1)
        													.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
        													.limit(targetStringLength)
												            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
												            .toString();
        
		return generatedString.toUpperCase();
	}
}
