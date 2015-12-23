package trips;
import java.util.ArrayList;
import java.util.List;

/**
 * The Itinerary class is a subclass of Trip, and represents a series of
 * Flight objects that lead to a destination.
 */
public class Itinerary extends Trip{
	// The flights contained within the itinerary, stored sequentially
	private List<Flight> flights = new ArrayList<Flight>();

	/**
	 * Constructs a new Itinerary with a cost of zero dollars and zero cents.
	 */
	public Itinerary() {
		this.cost = 0.0;
		this.travelTime = new Integer[]{0, 0, 0};
	}

	/**
	 * Adds a given flight to the flights list.
	 *
	 * @param flight Flight to be added to the itinerary.
	 */
	public void addFlight(Flight flight) {
		// If no flights already, define the origin and departure date/time for
		// itinerary.
		if (this.flights.size() == 0) {
			this.origin = flight.getOrigin();
			this.departureDate = flight.getDepartureDate();
		}
		// Add flight, update destination, arrival dateTime, overall cost, and
		// overall Travel Time.
		this.flights.add(flight);
		this.destination = flight.getDestination();
		this.arrivalDate = flight.getArrivalDate();
		this.cost += flight.getCost();
		this.travelTime = Trip.calculateTimeDifference(departureDate,
				arrivalDate);
	}

	/**
	 * Returns the length of the list of flights in the itinerary.
	 */
	public int getLength(){
		return this.flights.size();
	}

	/**
	 * Get the flights in an itinerary.
	 *
	 * @return a list of flights in an itinerary
	 */
	public List<Flight> getFlights() {
		return this.flights;
	}

	/**
	 * Return if a location was already visited in an itinerary.
	 *
	 * @param location a location's name
	 * @return if the location was already visited
	 */
	public boolean alreadyVisited(String location) {
		// Populate a list with all destinations visited during the flights
		ArrayList<String> locationsVisited = new ArrayList<String>();
		// Add the origin of the itinerary, since locations are otherwise
		// added based on the destination of each flight.
		locationsVisited.add(this.origin);
		// Add every destination of every flight on the list. With the original
		// origin, this will account for every location visited.
		for (Flight flight: this.flights) {
			locationsVisited.add(flight.getDestination());
		}
		// Return if the location was visited or not.
		if (locationsVisited.contains(location)) {
			return true;
		}
		return false;
	}

	/**
	 * Returns a copy of an itinerary.
	 *
	 * @return an itinerary identical to the instantiated one
	 */
	public Itinerary copyItinerary() {
		// Create a new itinerary, populate it with all flights, return it.
		Itinerary newItinerary = new Itinerary();
		for (Flight flight: this.flights) {
			newItinerary.addFlight(flight);
		}
		return newItinerary;
	}

	/**
	 * Return if a newFlight would be a valid lay-over after the last flight of 
	 * the itinerary. It must be after the last itinerary flight, within six 
	 * hours.
	 *
	 * @param newFlight a potential new flight
	 * @return if the new flight would be considered a lay-over
	 */
	public boolean validLayover(Flight newFlight) {
		Flight lastFlight = this.getLastFlight();

		// Find the time difference between the last flight and potential 
		// new flight.
		Integer[] timeDifference = calculateTimeDifference(
				lastFlight.arrivalDate, newFlight.departureDate);
		// Check if the time difference is less than six hours.
		boolean lessThanSixHours = false;
		if (timeDifference[0] == 0) {
			if (timeDifference[1] < 6) {
				lessThanSixHours = true;
			}
			if ((timeDifference[1] == 6) && (timeDifference[2] == 0)) {
				lessThanSixHours = true;
			}
		}

		// The last flight's destination must fit the new flight's origin, the 
		// last flight must arrive before the new flight departs, and the time
		// difference must be less than 360 minutes (6 hours). Return true if
		// these are met.
		if ((lastFlight.getDestination().equals(newFlight.getOrigin()))
				&& (lastFlight.getArrivalDate().before(
				newFlight.getDepartureDate()))
				&& (lessThanSixHours)) {
			return true;
		}
		return false;
	}

	/**
	 * Return if a new Flight is a valid next flight, being a valid lay-over as
	 * well as whatever criteria constrain the itinerary (in this case, having 
	 * not visited the location before).
	 *
	 * @param nextFlight a potential new flight
	 * @return if the new flight meets all criteria to be added to the 
	 * itinerary
	 */
	public boolean validNextFlight(Flight nextFlight) {
		// If the flight is a valid lay-over, has not been visited, 
		// and has available seats, return true.
		// NOTE: Useful to separate from lay-over, in case more rules for 
		// adding new flights must be added, unrelated to being a lay-over.
		if ((!alreadyVisited(nextFlight.getDestination()))
				&& this.validLayover(nextFlight)
				&& nextFlight.getNumSeats() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * Return the last flight of the Itinerary.
	 *
	 * @return the last flight
	 */
	public Flight getLastFlight() {
		return this.flights.get(this.getLength() - 1);
	}

	/**
	 * Return the flights of an itinerary in a csv format.
	 *
	 * @return a csv string representation of flights in an itinerary
	 */
	public String flightsCSVString() {
		String toReturn = "";
		for(Flight f : this.getFlights()) {
			toReturn += f.getFlightNumber().toString() + "-";
		}
		return toReturn;//.substring(0, toReturn.length() - 1);
	}

	/**
	 * Returns a multi-line String representation of this Itinerary including
	 * its cost and total travel time.
	 *
	 * @return a String representation of this Itinerary, one line per flight,
	 * in the format: Number,DepartureDateTime,ArrivalDateTime,Airline,Origin,
	 * Destination followed by total price (on its own line, exactly two 
	 * decimal places), and total duration (on its own line, in format HH:MM)
	 */
	public String toString() {
		String result = "";
		// Add each flight to the string, one per line.
		for (Flight flight: this.flights)
		{
			result += flight.itineraryString() + "\n";
		}
		// Return the price and then the total travel time in hours and 
		// minutes, padded by 0s if necessary.
		String hours = String.valueOf((this.getTravelTime()[0] * 24) + this.getTravelTime()[1]);
		while (hours.length() < 2) {hours = "0" + hours;}
		result += String.format("%.2f", this.getCost())
				+ "\n" + hours + ":";
		String minutes = String.valueOf((this.getTravelTime()[2]));
		while (minutes.length() < 2) {minutes = "0" + minutes;}
		result += minutes;

		return result;
	}

	/**
	 * Return a string that displays a reader-friendly version of an itinerary, for use
	 * in the app.
	 *
	 * @return a string representing the itinerary
	 */
	@Override
	public String displayString() {
		String displayString = "Itinerary:\n";
		displayString += "Initial Origin: " + this.getOrigin() + "\n";
		for (Flight flight: this.flights) {
			displayString += "Flight #" + flight.getFlightNumber() + " : " +
					flight.getOrigin() + " -> "
					+ flight.getDestination() + "\n-Departure: "
					+ Trip.convertDateToString(flight.getDepartureDate())
					+ "\n-Arrival: " + Trip.convertDateToString(flight.getArrivalDate())
					+ "\n-Airline: " + flight.getAirline() + "\n";
		}
		displayString += "Final Destination: " + this.getDestination()
				+ String.format("\nTotal cost: $%.2f",this.getCost())
				+ "\nTotal travel Time: " + displayTravelTime();
		return displayString;
	}
}