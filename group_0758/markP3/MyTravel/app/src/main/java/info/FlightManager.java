package info;

import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

import trips.Flight;
import trips.Trip;

import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class FlightManager extends Manager {
	// Stores flights using unique Flight Numbers as a Key to each
	// individually stored flight.
	private  Map<Integer, Flight> flights =  new HashMap<Integer, Flight>();
	private  String filePath;
	
	/**
	 * Initializes a new FlightInformation Object with an empty HashMap of
	 * flights.
	 */
	public FlightManager() {
		this.flights = new HashMap<Integer, Flight>(); 
	}

	/**
	 * Sets a string as the new filePath field.
	 * @param path the new file path.
	 */
	public void setFilePath(String path) {
		this.filePath = path;
	}
	
	/**
	 * Reads flight information from a given file and adds it to the HashMap.
	 * 
	 * @param filePath a string of the path to the file
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public void readFromFile(String filePath) 
			throws IOException, FileNotFoundException {
		Scanner scanner = new Scanner(new FileInputStream(filePath));
		String[] record;
		Flight flight;
		
		while(scanner.hasNextLine()){
			record = scanner.nextLine().split(",");
			flight = new Flight(Integer.parseInt(record[0]), record[1], 
					record[2], record[3], record[4], record[5], 
					Double.parseDouble(record[6]), Integer.parseInt(record[7]));
			// Create and add flight based on scanned information
			this.add(flight);
		}
		scanner.close();
	}
	
	/**
	 * Write all currently stored flights to the class's storage file.
	 *
	 * @throws IOException
	 */
	public void writeFlightInfoToFile() throws IOException {
		FileWriter fw = new FileWriter(this.filePath);
		BufferedWriter bw = new BufferedWriter(fw);
		
		bw.write(this.toString());
		bw.close();
	}
	
	/**
	 * Edits information for a specified flight.
	 * 
	 * @param flightNumber the unique flight number of the Flight being edited
	 * @param sort the information that the users want to change
	 * @param info the new information
	 * @throws NoSuchFlightException if the flightNumber is not stored under a
	 * flight
	 */
	public void editStoreFlightInfo(Integer flightNumber, String sort, 
			String info) throws NoSuchFlightException {
		// If flight is stored under the given flightNumberm, set the relevant
		// flight information
		
		if (flights.containsKey(flightNumber)) {
			Flight flight = this.retrieveFlightInfo(flightNumber);
			switch(sort) {
				case "departuredate":
					flight.setDepartureDateTime(Trip.convertStringToDate(info)); break;
				case "arrivaldate":
					flight.setArrivalDateTime(Trip.convertStringToDate(info)); break;
				case "origin":
					flight.setOrigin((String) info); break;
				case "destination":
					flight.setDestination((String) info); break;
				case "flightNumber":
					// If flightNumber, need to remove the Key and store the
					// flight under the new flightNumber.
					this.flights.remove(flight.getFlightNumber());
					flight.setFlightNumber(Integer.parseInt(info));
					this.add(flight); break;
				case "cost":
					try {flight.setCost(Double.parseDouble(info));}
					catch (NumberFormatException e) {}; break;
				case "airline":
					flight.setAirline(info);
			}
		}else{
			throw new NoSuchFlightException();
		}
	}
	
	/**
	 * Returns a flight by giving its unique flight number.
	 * 
	 * @param flightNumber the flight number of the flight to be retrieved
	 * @return the stored flight matching the unique flight number
	 * @throws NoSuchFlightException if no flight is found with the number
	 */
	public Flight retrieveFlightInfo(Integer flightNumber) 
			throws NoSuchFlightException{
		// Return the flight if the flightNumber is stored. If not, throw
		// an exception.
		if (flights.containsKey(flightNumber)) {
			return flights.get(flightNumber);
		} else {
			throw new NoSuchFlightException();
		}
	}
	
	/**
	 * Add a flight to the HashMap that stores current flights.
	 * 
	 * @param flight a flight which user wants to add to FlightManager
	 */
	public void add(Flight flight) {
		flights.put(flight.getFlightNumber(), flight);
	}
	
	/**
	 * Returns the HashMap of currently stored flights in string format, each
	 * flight on a newline.
	 * 
	 * @return a string of all flights currently stored
	 */
	public String toString() {
		// Add the string representation of each flight, one per new line, to
		// the result string and return it.
		String result = new String();
		for (Flight flight : flights.values()){
			result += flight.csvString() + "\n";
		}
		return result;
	}
	
	/**
	 * Returns the Map<Integer, Flight> of this FlightManager instance 
	 * containing all the Flight objects currently stored.
	 * 
	 * @return the Map<Integer, Flight> containing all Flight objects in the 
	 * system currently.
	 */
	public Map<Integer, Flight> getAllFlights() {
		Map<Integer, Flight> allFlights = new HashMap<Integer, Flight>();
		for (Integer i : flights.keySet())
		{
			allFlights.put(i, flights.get(i));
		}
		return allFlights;
	}
}
