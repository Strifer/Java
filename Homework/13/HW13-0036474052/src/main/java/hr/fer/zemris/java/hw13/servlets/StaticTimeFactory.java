package hr.fer.zemris.java.hw13.servlets;

import java.util.concurrent.TimeUnit;

/**
 * Creates a new <code>String</code> representation of time provided in
 * miliseconds.
 * 
 * @author Filip Džidić
 *
 */
public class StaticTimeFactory {
	/**
	 * Creates a new format of time provided in milliseconds.
	 * <p>
	 * The format is as follows:<br>
	 * <code>Days : Hours : Minutes : Seconds : Milliseconds</code>
	 * 
	 * @param milliseconds
	 *            an interval of milliseconds representing the time we wish to
	 *            parse
	 * @return the time in the set format
	 */
	public static String getDurationBreakdown(long milliseconds) {
		long millis = milliseconds;
		if (millis < 0) {
			throw new IllegalArgumentException(
					"Duration must be greater than zero!");
		}

		long days = TimeUnit.MILLISECONDS.toDays(millis);
		millis -= TimeUnit.DAYS.toMillis(days);
		long hours = TimeUnit.MILLISECONDS.toHours(millis);
		millis -= TimeUnit.HOURS.toMillis(hours);
		long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
		millis -= TimeUnit.MINUTES.toMillis(minutes);
		long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

		StringBuilder sb = new StringBuilder(64);
		sb.append(days);
		sb.append(" Days ");
		sb.append(hours);
		sb.append(" Hours ");
		sb.append(minutes);
		sb.append(" Minutes ");
		sb.append(seconds);
		sb.append(" Seconds ");
		sb.append(millis % 1000);
		sb.append(" Milliseconds");

		return (sb.toString());
	}
}
