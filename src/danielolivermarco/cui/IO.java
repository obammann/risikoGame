package danielolivermarco.cui;

import java.io.*;
/**
 * Regelt ein und Ausgaben in der CUI Version
 * @author Marco
 *
 */
public class IO {

	public static BufferedReader input
          = new BufferedReader(new InputStreamReader(System.in));
	public static String eingabe = "";
	/**
	 * Liest einen Char ein
	 * @return gibt den eingegeben Char zurueck
	 */
	public static char readChar() {
		try {
			eingabe = input.readLine();
			return eingabe.charAt(0);
		}
		catch(Exception e) {
			return '\0';
		}
	}
	/**
	 * Liest einen Short ein
	 * @return gibt den eingegeben Short zurueck
	 */
	public static short readShort() {
		try {
			eingabe = input.readLine();
			Integer string_to_short = new Integer(eingabe);
			return (short)string_to_short.intValue();
		}
		catch (Exception e) {
			return 0;
		}
	}	
	/**
	 * Liest einen Integer ein
	 * @return gibt den eingegeben Integer zurueck
	 */
	public static int readInt() {
		try {
			eingabe = input.readLine();
			Integer string_to_int = new Integer(eingabe);
			return string_to_int.intValue();
		}
		catch (Exception e) {
		  return 0;
		}
	}
	/**
	 * Liest einen Long ein
	 * @return gibt den eingegeben Long zurueck
	 */
	public static long readLong() {
		try {
			eingabe = input.readLine();
			Long string_to_long = new Long(eingabe);
			return string_to_long.longValue();
		}
		catch (Exception e) {
			return 0L;
		}
	}
	/**
	 * Liest einen String ein
	 * @return gibt den eingegeben String zurueck
	 */
	public static String readString() {
		try {
			return input.readLine();
		}
		catch (Exception e) {
			return "";
		}
	}
}