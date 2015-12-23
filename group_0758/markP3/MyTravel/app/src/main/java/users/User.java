package users;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import trips.Flight;
import trips.Itinerary;
import info.ClientManager;
import info.FlightManager;
import info.NoSuchClientException;
import info.NoSuchFlightException;
import info.SearchTool;
import trips.Trip;

/**
 * User is a class representing a User of the application, capable of obtaining
 * Client information stored in the app, uploading Client information to the 
 * application, and possesses the ability to search across all available 
 * Flights for both individual Flight objects and Itineraries.
 */
public class User implements Serializable {

	/** Each user has it's own SearchTool. */
	protected SearchTool ST = new SearchTool();

	protected Itinerary selectedItinerary;

	/** A FlightManager for any User to keep track of flights */
	protected FlightManager fm = new FlightManager();


	/**
	 * Reads the file with path filePath and
	 * 1) adds flight information to the Map containing all flight information
	 * 2) adds flight information to the file containing all flight information
	 * If no such path exists, throws FileNotFoundException or IO Exception.
	 *
	 * @param filePath the path of the file with flight info to read from
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void uploadFlightInfoFromFile(String filePath)
			throws FileNotFoundException, IOException {
		fm.readFromFile(filePath);
	}

	/**
	 * Return a flight given its unique flight number.
	 *
	 * @param flightNumber the unique flight number of the flight
	 * @return Flight the flight object with flight number flightNumber
	 * @throws NoSuchFlightException
	 */
	public Flight viewFlightInfo(Integer flightNumber)
			throws NoSuchFlightException {
		return fm.retrieveFlightInfo(flightNumber);
	}

	/**
	 * Updates this User's SearchTool object as it performs a search to obtain
	 * all Flight objects in the system departing at given departure time and
	 * from given origin location, arriving at given destination location.
	 * Results can later be viewed in a sorted manner.
	 *
	 * @param departure a String of the desired date of departure, in the
	 * format YYYY-MM-DD.
	 * @param origin a String of the desired departure location of the Flight.
	 * @param destination a String of the desired destination location of the
	 * Flight.
	 */
	public void searchAvailableFlights(String departure, String origin,
									   String destination) {
		this.ST.searchFlights(fm, departure, origin, destination);
	}

	/**
	 * Updates this User's SearchTool object as it performs a search to obtain
	 * all possible Itinerary objects departing at given departure time and
	 * from given origin location, arriving at given destination location.
	 * These results can later be viewed in a sorted manner.
	 *
	 * @param departure
	 * @param origin
	 * @param destination
	 */
	public void searchPossibleItineraries(String departure, String origin,
										  String destination) {
		this.ST.generatePossibleItineraries(fm, departure, origin,
				destination);
	}

	/**
	 * Sorts last searched itineraries according to a given criteria, time or cost.
	 *
	 * @param criteria a string indicating to sort by cost or time.
	 */
	public void sortPossibleItineraries(String criteria) {
		this.ST.lastSearchSorted(criteria);
	}

	/**
	 * Returns a String representation of the most recent search conducted
	 * by a given SearchTool as ordered by cost or time, in the format of a
	 * String representation of each Trip object as a single line.
	 *
	 * @param sortCriteria a String representation of the sorting view, either
	 * by "cost" or "time" and time by default.
	 * @return a String representation of the most recent search conducted
	 * by the given SearchTool as ordered by cost or time.
	 */
	public String searchResultString(String sortCriteria)
	{
		// obtaining a sorted list of Flights or Itineraries
		trips.Trip[] sortedTrips = this.ST.lastSearchSorted(sortCriteria);
		String result = "";
		for (int i = 0; i < sortedTrips.length; i++) {
			//adding each element to String in order of sort criteria
			result += sortedTrips[i].toString() + "\n";
		}
		return result;
	}

	/**
	 * Returns the selected itinerary from a conducted search.
	 *
	 * @return the selected itinerary
	 */
	public Itinerary getSelectedItinerary() {
		return this.selectedItinerary;
	}

	/**
	 * Returns a recently searched itinerary given an index. Also updates
	 * selected itinerary.
	 *
	 * @param index indicates the itinerary to select
	 * @return the selected itinerary
	 */
	public Itinerary selectSearchedItinerary(int index) {
		this.selectedItinerary = (Itinerary) this.ST.getRecentSearch().get(index);
		return this.selectedItinerary;
	}

	/**
	 * Returns the most recent search conducted.
	 *
	 * @return the most recent search of itineraries
	 */
	public List<Trip> getRecentlySearchedTrips() {
		return ST.getRecentSearch();
	}

	/**
	 * Returns the flightmanager being used by the admin.
	 *
	 * @return the flight manager
	 */
	public FlightManager getFlightManager() {
		return fm;
	}
}
