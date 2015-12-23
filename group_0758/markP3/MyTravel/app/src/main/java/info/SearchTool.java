package info;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import trips.Flight;
import trips.Itinerary;
import trips.Trip;

/**
 * SearchTool is a class which contains methods other Classes utilize to 
 * achieve application functionality, namely operations related to the sorting
 * of Trip subclasses and the acquisition of search results based on criteria.
 */
public class SearchTool implements Serializable {
	
	/* contains the most recently acquired list of Trips 
	 * (Flights or Itineraries) of this SearchTool.*/
	private List<Trip> lastSearchedTrips;
	
	/**
	 * Constructs a new SearchTool Object with an empty recent search 
	 * List<Trip>.
	 */
	public SearchTool() {
		this.lastSearchedTrips = new ArrayList<Trip>();
	}
	
	/**
	 * Returns a List<Trip> containing the most recent search results found by
	 * this SearchTool.
	 * 
	 * @return List<Trip> a List<Trip> containing the results of the most 
	 * recent search.
	 */
	public List<Trip> getRecentSearch() {
		return this.lastSearchedTrips;
	}

	/**
	 * Populates this SearchTool's search results List with all possible 
	 * itineraries, given all registered flights, a departure date, an origin,
	 *  and a destination.
	 *  
	 * @param fm a given flight manager to search through
	 * @param departureDate a string representing the departure date 
	 * (YYYY-MM-DD)
	 * @param origin a string representing the origin of the itinerary journey
	 * @param destination a string representing the destination of the 
	 * itinerary journey
	 * @return all possible itineraries given the criteria
	 */
	public void generatePossibleItineraries(FlightManager fm, String 
			departureDate, String origin, String destination) {
		// Will populate and ultimately return this list
		ArrayList<Itinerary> possibleItineraries = new ArrayList<Itinerary>();
		
		// For all flights that have the right origin for the itinerary, 
		// departure date, and available seats... (note: the flight's 
		// departure dateTime must be substringed since it also contains 
		// hours and minutes, unlike the given departure date)
		for (Flight flight:(fm.getAllFlights().values())) {
			if ((flight.getOrigin().equals(origin)) 
					&& (Trip.convertDateToString(flight.getDepartureDate()).
							substring(0, 10).equals(departureDate))
					&& (flight.getNumSeats() > 0)) {
				// ... create a new itinerary and begin iterating through all 
				// combinations of flights.
				Itinerary itin = new Itinerary();
				this.iterateFlights(fm, flight, destination, itin, 
						possibleItineraries);
			}
		}
		// After all possible itineraries are added after the flights are 
		// iterated through, store the final array of itineraries as a field.
		List<Trip> resultTrips = new ArrayList<Trip>();
		resultTrips.addAll(possibleItineraries);
		this.lastSearchedTrips.clear();
		this.lastSearchedTrips = resultTrips;
	}
		
	/**
	 * A helper function for generatePossibleItineraries. Return all possible
	 * itineraries, given a flight to add (assumed already to be valid), a 
	 * final destination, an incomplete itinerary, and a list of all possible 
	 * itineraries to add to. Will recursively iterate through all registered
	 * flights.
	 *
	 * @param fm a given flight manager to search through
	 * @param addedFlight a flight to be added (assumed to be valid)
	 * @param finalDestination the location that the final flight will lead to
	 * @param itinerary the itinerary, as it is constructed so far
	 * @param possibleItineraries all itineraries that have reached the 
	 * finalDestination
	 * @return the list of possibleItineraries, as it is being added to
	 */
	private ArrayList<Itinerary> iterateFlights(FlightManager fm, 
			Flight addedFlight, String finalDestination, Itinerary itinerary, 
			ArrayList<Itinerary> possibleItineraries) {
		// Add the addedFlight, assuming it is valid to be added
		itinerary.addFlight(addedFlight);
		/* If the itinerary has reached the final destination, add the 
		 * itinerary to possible itineraries and return them.*/
		if (itinerary.getDestination().equals(finalDestination)) {
			possibleItineraries.add(itinerary);
			return possibleItineraries;
		}
		/* If the goal is not reached yet, check all valid flights that can be 
		 * added to the incomplete itinerary. Recursively add and iterate 
		 * through all valid flights by calling the function on each valid 
		 * flight.*/
		else {
			for (Flight nextFlight:(fm.getAllFlights().values())) {
				if (itinerary.validNextFlight(nextFlight)) {
					/*NOTE: A copy of the itinerary must be generated to keep
					 * each recursive addition to the itinerary separate.*/
					this.iterateFlights(fm, nextFlight, finalDestination, 
							itinerary.copyItinerary(), possibleItineraries);
				}		
			}
		}
		/* If the goal has not been reached (eg. a dead-end) and all flights 
		 * have been iterated through, return the possible itineraries without
		 *  adding to them.*/
		return possibleItineraries;
	}
	
	/**
	 * Returns a sorted List<Trip> based on a given List<Trip> and desired
	 * method of sorting, "cost" or "time", "time" by default.
	 * 
	 * @param possibleTrips List<Trip> the List of Trips we wish to be sorted.
	 * @param desiredSort String the string representation of our sorting 
	 * desires.
	 * @return
	 */
	public Trip[] sortTrips(List<Trip> possibleTrips, String desiredSort){
		
		Trip[] tripsToSort = new Trip[possibleTrips.size()];
		for (int i = 0; i < possibleTrips.size(); i++) {
			tripsToSort[i] = possibleTrips.get(i);
		}
		
		if (desiredSort == "cost") {
			return mergeSortTrips(tripsToSort, desiredSort);
		} 
		else return mergeSortTrips(tripsToSort, "time");
	}
	
	/**
	 * An implementation of merge sort that returns an array of Trips sorted by
	 * total "cost" or total "time" given a List<Trip> and String 
	 * representation of desired sorting method.
	 * 
	 * @param tripArray List<Trip> the Trips in question to obtain a
	 * sorted Trip array of.
	 * @param desiredSort String the string representation, "cost" or "time",
	 * of the desired sorting method.
	 * @return an array of Trips sorted by cost or time.
	 */
	private Trip[] mergeSortTrips(Trip[] tripArray, String desiredSort) {
		
		if (tripArray.length <= 1) {
			return tripArray;		// returning a single Trip Trip Array
		}
		
		// splitting the given array of Trips into two separate arrays of Trips
		int mid = tripArray.length / 2;
		Trip[] left = new Trip[mid];
		Trip[] right = new Trip[tripArray.length - mid];
		
		for (int i = 0; i < mid; i++) {
			left[i] = tripArray[i];
		}
		
		int j = 0;
		for (int i = mid; i < tripArray.length; i++) {
			right[j] = tripArray[i];
			j++;
		}
		
		/* recursively sorting and merging the two arrays of Trips according to
		 * search criteria */
		left = mergeSortTrips(left, desiredSort);
		right = mergeSortTrips(right, desiredSort);
		Trip[] result = new Trip[tripArray.length];
		result = merge(left, right, desiredSort);
		
		return result;
	}

	/**
	 * Returns a merged array of Trips sorted by the desired sorting method, 
	 * "cost" or "time" via the merge portion of an implementation of 
	 * merge sort.
	 * 
	 * @param left Trip[] the "left" portion to be merged and sorted.
	 * @param right Trip[] the "right" portion to be merged and sorted.
	 * @param desiredSort String the String representation of the requested 
	 * sorting method.
	 * @return an array of Trips sorted by cost or time as desired.
	 */
	private Trip[] merge(Trip[] left, Trip[] right, String desiredSort) {
		int resultLength = left.length + right.length;
		Trip[] result = new Trip[resultLength];
		int indexL = 0;
		int indexR = 0;
		int indexResult = 0;
		
		while( indexL < left.length || indexR < right.length) {
			if (indexL < left.length && indexR < right.length) {
				/* comparing the cost or time of the trips dependent on 
				 * user specification String desiredSort */
				if ( ((left[indexL].getCost() <= right[indexR].getCost()) 
						&& desiredSort == "cost") 
						|| ((left[indexL].getTravelTimeMinutes() 
								<= right[indexR].getTravelTimeMinutes())
						&& desiredSort == "time")) {
					result[indexResult] = left[indexL];
					indexL++;
					indexResult++;
					} 
				else {
					result[indexResult] = right[indexR];
					indexR++;
					indexResult++;
					}
				} 
			else if (indexL < left.length) {
				result[indexResult] = left[indexL];
				indexL++;
				indexResult++;
				} 
			else if (indexR < right.length) {
				result[indexResult] = right[indexR];
				indexR++;
				indexResult++;
				}
			}
		
		return result;
		}

	/**
	 * Returns an array of Trips containing the sorted results of the most
	 * recent search conducted by this SearchTool, by cost or by time.
	 * 
	 * @param sortCriteria the String representation of the desired sorting
	 * method, "cost" or "time".
	 * @return an array of Trips based on the most recent search by this 
	 * SearchTool as sorted by the desired sorting method.
	 */
	public Trip[] lastSearchSorted(String sortCriteria) {
		Trip[] sortedTripsArray = this.sortTrips(this.getRecentSearch(), sortCriteria);
		ArrayList<Trip> sortedTrips = new ArrayList<>();
		for (Trip trip:sortedTripsArray) {
			sortedTrips.add(trip);
		}
		lastSearchedTrips = sortedTrips;
		return sortedTripsArray;
	}
	
	/**
	 * Populates this SearchTool's List<Trip> of search results adding all
	 * Flight objects in the system departing at given departure time and from 
	 * given origin location, arriving at given destination location.
	 * @param fm The FlightManager object containing the Flights to be searched
	 * through.
	 * @param departure String the desired date of departure, in the format
	 * YYYY-MM-DD.
	 * @param origin a String of the desired departure location of the Flight.
	 * @param destination a String of the desired destination location of the 
	 * Flight.
	 */
	public void searchFlights(FlightManager fm, String departure, 
			String origin, String destination) {
		List<Flight> results = new ArrayList<Flight>();
		
		for (Flight flight : fm.getAllFlights().values()) {
			String departString = Trip.convertDateToString(flight.getDepartureDate()).substring(0, 10);
			if (departString.equals(departure)
					&& flight.getOrigin().equals(origin) 
					&& flight.getDestination().equals(destination)
					&& flight.getNumSeats() > 0) {
				results.add(flight);
			}
		}
		List<Trip> resultTrips = new ArrayList<Trip>();
		resultTrips.addAll(results);
		this.lastSearchedTrips.clear();
		this.lastSearchedTrips = resultTrips;
	}
}
