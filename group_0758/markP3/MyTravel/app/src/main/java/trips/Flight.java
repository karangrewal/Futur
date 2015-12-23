package trips;

import java.util.Date;

/**
 * The Flight class extends Trip and adds new fields to each instance. Flights
 * are used to build itineraries.
 */
public class Flight extends Trip {

	/** A flight number Integer for each Flight object. */
	private Integer flightNumber;

	/** An airline name string for each Flight object */
	private String airline;

	/** An integer of the number of seats available */
	private Integer numSeats;

	/**
	 * Initializes a new flight object with the given fields.
	 *
	 * @param origin The origin of the flight
	 * @param destination The destination of the flight
	 * @param departureDateTime The departure date and time of the flight
	 * @param arrivalDateTime The arrival date and time of the flight
	 * @param flightNumber The flight number
	 * @param cost Cost of the flight
	 */
	public Flight(Integer flightNumber, String departureDateTime,
				  String arrivalDateTime, String airline, String origin,
				  String destination, Double cost, Integer numSeats) {
		this.flightNumber = flightNumber;
		this.departureDate = Flight.convertStringToDate(departureDateTime);
		this.arrivalDate = Flight.convertStringToDate(arrivalDateTime);
		this.airline = airline;
		this.origin = origin;
		this.destination = destination;
		this.cost = cost;
		this.numSeats = numSeats;
		this.travelTime = Trip.calculateTimeDifference(departureDate, arrivalDate);
	}

	/**
	 * Returns the number of remaining available seats on this Flight.
	 *
	 * @return the Integer representation of the remaining unbooked seats on this
	 * Flight.
	 */
	public Integer getNumSeats() {
		return numSeats;
	}

	/**
	 * Returns the flight number of a flight object.
	 *
	 * @return Integer the flight number
	 */
	public Integer getFlightNumber() {
		return this.flightNumber;
	}

	/**
	 * Updates the flight number of the flight object.
	 *
	 * @param newFlightNumber the new flight number Integer for the Flight
	 * object
	 */
	public void setFlightNumber(Integer newFlightNumber) {
		this.flightNumber = newFlightNumber;
	}

	/**
	 * Returns the string containing the airline name for a particular Flight.
	 *
	 * @return string the string containing the airline name
	 */
	public String getAirline() {
		return this.airline;
	}

	/**
	 * Set the airline to a given airline string.
	 *
	 * @param airline the string containing the airline name
	 */
	public void setAirline (String airline) {
		this.airline = airline;
	}

	/**
	 * Set a new departure time and update the travel time.
	 *
	 * @param newDate the new departure time
	 */
	public void setDepartureDateTime (Date newDate) {
		this.departureDate = newDate;
		this.travelTime = Trip.calculateTimeDifference(departureDate, arrivalDate);

	}

	/**
	 * Set the new arrival time and update the travel time.
	 *
	 * @param newDate the new arrival time
	 */
	public void setArrivalDateTime (Date newDate) {
		this.arrivalDate = newDate;
		this.travelTime = Trip.calculateTimeDifference(departureDate, arrivalDate);

	}

	/**
	 * Updates the origin of the flight object.
	 *
	 * @param newOrigin the new origin string for the Flight object
	 */
	public void setOrigin(String newOrigin) {
		this.origin = newOrigin;
	}

	/**
	 * Updates the destination of the Flight object.
	 *
	 * @param newDestination the new destination string for the Flight object
	 */
	public void setDestination(String newDestination) {
		this.destination = newDestination;
	}

	/**
	 * Updates the cost of the Flight object.
	 *
	 * @param newCost the new cost Double for the Flight object
	 */
	public void setCost(Double newCost) {
		this.cost = newCost;
	}

	/**
	 * Returns a single line string with the CSV String representation of the
	 * Flight object, formatted: Number,DepartureDateTime,ArrivalDateTime,
	 * Airline,Origin,Destination,Price,NumSeats
	 *
	 * @return the CSV-formatted single line string of the Flight object
	 */
	public String csvString() {
		return "" + this.getFlightNumber() + ","
				+ Trip.convertDateToString(this.getDepartureDate())
				+ "," + Trip.convertDateToString(this.getArrivalDate()) + ","
				+ this.getAirline() + "," + this.getOrigin() + ","
				+ this.getDestination() + ","
				+ String.format("%.2f", this.getCost()) + "," + this.getNumSeats();
	}

	/**
	 * Returns a single line string with the CSV String representation of the
	 * Flight object, formatted: Number,DepartureDateTime,ArrivalDateTime,
	 * Airline,Origin,Destination,Price. Leaves out numSeats.
	 *
	 * @return the CSV-formatted string of the Flight object, without numSeats.
	 */
	public String toString() {
		return "" + this.getFlightNumber() + ","
				+ Trip.convertDateToString(this.getDepartureDate())
				+ "," + Trip.convertDateToString(this.getArrivalDate()) + ","
				+ this.getAirline() + "," + this.getOrigin() + ","
				+ this.getDestination() + ","
				+ String.format("%.2f", this.getCost());
	}

	/**
	 * Returns a readable string with the Number, Departure Time, Arrival Time,
	 * Airline, Origin, Destination, and Cost. Leaves out numSeats.
	 *
	 * @return a readable string of the Flight object, without numSeats.
	 */
	public String displayString() {
		return String.format("Flight Number %s\nDeparture: %s\nArrival: %s\n" +
						"Airline: %s\nOrigin: %s\nDestination: %s\nCost: $%.2f"
						+ "\nTravel Time: %s",
				this.getFlightNumber(), Trip.convertDateToString(
						this.getDepartureDate()), Trip.convertDateToString(
						this.getArrivalDate()), this.getAirline(),
				this.getOrigin(), this.getDestination(), this.getCost(),
				this.displayTravelTime());
	}
	/**
	 * Returns a String representation of a Flight with only information 
	 * relevant to an Itinerary. A part of the Itinerary class's toString
	 * method.
	 *
	 * @return a String representation of this Flight in the format: 
	 * Number,DepartureDateTime,ArrivalDateTime,Airline,Origin,Destination
	 */
	protected String itineraryString() {
		return "" + this.getFlightNumber() + ","
				+ Trip.convertDateToString(this.getDepartureDate())
				+ "," + Trip.convertDateToString(this.getArrivalDate()) + ","
				+ this.getAirline() + "," + this.getOrigin() + ","
				+ this.getDestination();
	}
}
