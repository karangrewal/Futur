package trips;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Trip is an abstract Class representing a journey with an origin location, a
 * destination location, a cost, a travel time, a departure date, and an 
 * arrival date.
 */
public abstract class Trip implements Serializable {

	// a String representing the Origin location
	protected String origin;
	// a String representing the Destination location
	protected String destination;
	// a Double representing the cost in dollars and cents
	protected Double cost;
	/* an Integer array, representing the travel time of the journey in the 
	 * form of {days, hours, minutes}*/
	protected Integer[] travelTime;
	// a Date object representing the date of departure
	protected Date departureDate;
	// a Date object representing the date of arrival
	protected Date arrivalDate;

	/**
	 * Returns the String representing the origin location of this Trip.
	 *
	 * @return a String representing the origin location.
	 */
	public String getOrigin() {
		return this.origin;
	}

	/**
	 * Returns the String representing the destination location of this Trip.
	 *
	 * @return a String representing the destination location.
	 */
	public String getDestination() {
		return this.destination;
	}

	/**
	 * Returns the Double representing the cost of this Trip.
	 *
	 * @return a Double representing the cost in dollars and cents.
	 */
	public Double getCost() {
		return this.cost;
	}

	/**
	 * Returns the Integer Array representing the travel time of this Trip.
	 *
	 * @return an Integer Array representing the travel time in {days, hours
	 * minutes}
	 */
	public Integer[] getTravelTime() {return this.travelTime;}

	/**
	 * Returns the Date object representing the departure date of this Trip.
	 *
	 * @return a Date object representing the departure date.
	 */
	public Date getDepartureDate() {
		return this.departureDate;
	}

	/**
	 * Returns the Date object representing the arrival date of this Trip.
	 * @return a Date object representing the arrival date.
	 */
	public Date getArrivalDate() {
		return this.arrivalDate;
	}

	/**
	 * Returns an Integer representation of the total amount of minutes this 
	 * Trip takes.
	 *
	 * @return an Integer representation of the total number of minutes of this
	 * Trip.
	 */
	public Integer getTravelTimeMinutes(){
		Integer days = this.travelTime[0];
		Integer hours = this.travelTime[1];
		Integer minutes = this.travelTime[2];

		// Returns an Integer result of the amount of time this Trip takes.
		return (days * 24 * 60) + (hours * 60) + minutes;
	}

	/**
	 * Returns a Date object given a String representation in the form
	 * "YYYY-MM-DD HH:MM". If hours or minutes are not included, they
	 * are not set. Assumes a valid string representation is given.
	 *
	 * @param dateString a string representation of the date
	 * @return a Date
	 */
	public static Date convertStringToDate(String dateString) {
		// Year, month, and day are assumed to be included
		Integer year = Integer.parseInt(dateString.substring(0, 4));
		Integer month = Integer.parseInt(dateString.substring(5, 7));
		Integer day = Integer.parseInt(dateString.substring(8, 10));
		// Date receives years in relation to 1900 and months begin at 0 (Jan).
		Date returnDate = new Date(year - 1900, month - 1, day);

		// Specification of hours and minutes are optional but possible.
		if (dateString.length() > 10) {
			Integer hour = Integer.parseInt(dateString.substring(11, 13));
			Integer minute = Integer.parseInt(dateString.substring(14, 16));
			returnDate.setHours(hour);
			returnDate.setMinutes(minute);
		}
		return returnDate;
	}

	/**
	 * Return a String representation of a given Date, with the form 
	 * "YYYY-MM-DD HH:MM". 
	 * @param date the Date
	 * @return a string representation of date
	 */
	public static String convertDateToString(Date date) {
		String returnString = "";
		//For each TimeUnit, pad with 0s to achieve YYYY-MM-DD HH:MM format
		// Years stored in date in relation to 1900.
		String year = String.valueOf(date.getYear() + 1900);
		while (year.length() < 4) {year = "0" + year;}
		// Months stored in date from 0-11 (eg. month 0 = January)
		String month = String.valueOf(date.getMonth() + 1);
		if (month.length() == 1) {month = "0" + month;}
		String day = String.valueOf(date.getDate());
		if (day.length() == 1) {day = "0" + day;}
		String hour = String.valueOf(date.getHours());
		if (hour.length() == 1) {hour = "0" + hour;}
		String minute = String.valueOf(date.getMinutes());
		if (minute.length() == 1) {minute = "0" + minute;}

		// Construct the string to return
		returnString = year + "-" + month + "-" + day + " " + hour + ":" + minute;
		return returnString;
	}

	/**
	 * Returns the time difference of two dates in days, hours, and minutes.
	 * Assumes that the start date occurs between the final date.
	 *
	 * @param startDate the initial date
	 * @param finalDate the later date
	 * @return the time difference in days, hours, and minutes
	 */
	/**
	 * Returns the time difference of two dates in days, hours, and minutes.
	 * Assumes that the start date occurs between the final date.
	 *
	 * @param startDate the initial date
	 * @param finalDate the later date
	 * @return the time difference in days, hours, and minutes
	 */
	public static Integer[] calculateTimeDifference(Date startDate,
													Date finalDate) {
		// Get the difference in milliseconds and convert to relevant timeunits
		Long msDifference = finalDate.getTime() - startDate.getTime();

		Long totalMinDifference = msDifference / (60 * 1000);
		Long totalHourDifference = msDifference/ (60 * 60 * 1000);
		Long totalDayDifference = msDifference / (24 * 60 * 60 * 1000);
		Integer diffInMinutes = totalMinDifference.intValue() % 60;
		Integer diffInHours = totalHourDifference.intValue() % 24;
		Integer diffInDays = totalDayDifference.intValue();

		return new Integer[]{diffInDays, diffInHours, diffInMinutes};
	}

	/**
	 * Display the travel time in a readable string, indicating travel time
	 * in hours and minutes.
	 *
	 * @return a readable string indicating travel time
	 */
	public String displayTravelTime() {

		return this.getTravelTime()[0] * 24 + this.getTravelTime()[1]
				+ " hours, " + this.getTravelTime()[2] + " minutes";
	}

	/**
	 * Returns a string suitable for displaying of a trip.
	 *
	 * @return a reader-friendly string to display
	 */
	public abstract String displayString();
}
