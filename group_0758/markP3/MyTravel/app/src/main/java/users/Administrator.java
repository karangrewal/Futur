package users;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;

import info.ClientManager;
import info.NoSuchClientException;
import info.NoSuchFlightException;
import trips.Flight;
import trips.Itinerary;

public class Administrator extends User implements Serializable{

	/** A ClientManager to maintain information about all clients */
	protected ClientManager cm = new ClientManager();

	/**
	 * Gets a flight given a unique flight number.
	 * @param flightNum a unique integer representing a flight
	 * @return the flight that the flightNum represents
	 * @throws NoSuchFlightException if no flight exists
	 */
	/**
	 * Returns a flight give its flight number, else throws a
	 * NoSuchFlightException if that Flight does not exist
	 * @param flightNum the flight number of a flight to retrieve
	 * @return the Flight object corresponding to flightNum
	 * @throws NoSuchFlightException
	 */
	public Flight getFlight(Integer flightNum) throws NoSuchFlightException{
		return fm.retrieveFlightInfo(flightNum);
	}

	/**
	 * Adds a given client to the client manager.
	 *
	 * @param client a given client
	 */
	public void addClient(Client client){
		cm.add(client);
	}

	/**
	 * Uploads Client objects via their individual data in a CSV file of Client
	 * information located at the specified file path, to be stored by this
	 * User's ClientInformation class
	 *
	 * @param filePath the String pathway to the location of the specified file
	 * @throws IOException thrown if their is an I/O related failure.
	 */
	public void uploadClientInfoFromFile(String filePath)
			throws IOException{
		cm.readFromClientFile(filePath);
	}

	/**
	 * Uploads clients' booked itinerary information from a given filePath.
	 *
	 * @param filePath indicating the file that stores the booking information
	 * @throws IOException if no such file exists
	 * @throws NoSuchClientException
	 */
	public void uploadClientItinerariesFromFile(String filePath) throws IOException, NoSuchClientException {
		cm.readFromTripsFile(filePath);
	}

	/**
	 * Uploads a specified Client object's data to the CSV file of Client
	 * information located at the specified file path.
	 *
	 * @param client the Client object to be uploaded
	 * @param filePath the location of the CSV file to be added to.
	 * @throws IOException thrown if their is an I/O related failure.
	 * @throws FileNotFoundException thrown if the file at the location cannot
	 * be found.
	 */
	public void uploadClientInfo(Client client, String filePath)
			throws FileNotFoundException, IOException{
		cm.readFromClientFile(filePath);
		cm.add(client);
		cm.setClientInfoPath(filePath);
		cm.writeClientInfoToFile();
	}

	/**
	 * Uploads a specified Flight object's data to the CSV file of Client
	 * information located at the specified file path.
	 *
	 * @param flight the Client object to be uploaded
	 * @param filePath the location of the CSV file to be added to.
	 * @throws IOException thrown if their is an I/O related failure.
	 * @throws FileNotFoundException thrown if the file at the location cannot
	 * be found.
	 */
	public void uploadFlightInfo(Flight flight, String filePath)
			throws IOException, FileNotFoundException{
		fm.readFromFile(filePath);
		fm.add(flight);
		fm.setFilePath(filePath);
		fm.writeFlightInfoToFile();
	}

	/**
	 * Add a given flight to the flight manager.
	 *
	 * @param flight a given flight
	 */
	public void addFlight(Flight flight) {
		fm.add(flight);
	}

	/**
	 * Return a client given a unique email identifying him or her.
	 *
	 * @param email a unique string representing the client
	 * @return a client, given his or her email
	 * @throws NoSuchClientException if no such email is uploaded
	 */
	public Client getClient(String email) throws NoSuchClientException {
		return cm.retrieveClientInfo(email);
	}

	/**
	 * Returns a multi-line String representation of a Client's first and
	 * last name followed by their email address, obtained by providing the
	 * email address of the Client in question.
	 *
	 * @param email the String email address of the specific Client.
	 * @return the String representation of the Client's first name, last name
	 * and email address, each item on its own line.
	 * @throws NoSuchClientException
	 */
	public String viewClientPersonalInfo(String email) throws NoSuchClientException {
		return this.getClient(email).viewPersonalInfo();
	}

	/**
	 * Returns a multi-line String representation of a Client's credit card
	 * number and said credit card's expiration date, obtained by providing the
	 * email address of the Client in question.
	 *
	 * @param email the String email address of the specific Client.
	 * @return the String representation of the Client's credit number, and
	 * credit card expiration date, each on its own line.
	 * @throws NoSuchClientException
	 */
	public String viewClientBillingInfo(String email)
			throws NoSuchClientException {
		return this.getClient(email).viewBillingInfo();
	}

	/**
	 * Edits a specific field of data of a Client object as specified by said
	 * Client's email address. Writes any changes made to file upon completion.
	 * @param email the String email address of the specific Client.
	 * @param sort String representation of the form of data, either
	 * "firstname", "lastname", "email", "address", "creditcardnum", or
	 * "expirydate".
	 * @param newData the String representation of the new data.
	 * @throws NoSuchClientException
	 */
	public void editClientInfo(String email, String sort, String newData)
			throws NoSuchClientException
	{
		cm.editClientInfo(email, sort, newData);
	}

	/**
	 * Edits a specified field of data for a Flight object, as specified by a
	 * unique given flight number. Updates the indicated sort of data with newData.
	 * @param flightNumber identifies the unique flight being edited
	 * @param sort indicates the sort of data being edited
	 * @param newData the new data to update the flight with
	 * @throws NoSuchFlightException
	 */
	public void editFlightInfo(Integer flightNumber, String sort, String newData)
			throws NoSuchFlightException {
		fm.editStoreFlightInfo(flightNumber, sort, newData);
	}
	
	/**
	 * Books a selected itinerary for client if client's selected itinerary
	 * is not null. Resets client's selected itinerary to null.
	 * @param client the client for whom to book his/her selected itinerary
	 */
	/**
	 * Book an admin's selected itinerary for a given client.
	 *
	 * @param client book for this given client
	 */
	public void bookItineraryForClient(Client client) {
		// If there is a selected itinerary, book and reset the selection.
		if (this.selectedItinerary != null) {
			client.bookItinerary(this.selectedItinerary);
			this.selectedItinerary = null;
		}
	}

	/**
	 * Book a given itinerary for a given client.
	 *
	 * @param client a given client
	 * @param itinerary a given itinerary to be booked
	 */
	public void bookItineraryForClient(Client client, Itinerary itinerary) {
		client.bookItinerary(itinerary);
	}

	/**
	 * Display a given client's booked itineraries.
	 *
	 * @param client a given client
	 * @return the given client's booked itineraries
	 */
	public String displayBookedItinerariesForClient(Client client) {
		return client.displayBookedItineraries();
	}

	/**
	 * Writes all Client and Flight data in the Application to files. Should be
	 * called after every change made relevant to Clients and Flights. Any
	 * errors regarding reading and writing to file should be notified to the
	 * User before this method is relied on.
	 */
	public void saveData() throws IOException {
		cm.writeClientInfoToFile();
		fm.writeFlightInfoToFile();
	}

	/**
	 * Writes all Client and Flight data in the Application to files located in
	 * the directory of the specified filepath. Should be called after every
	 * change made relevant to Clients and Flights. Any errors regarding
	 * reading and writing to file should be notified to the User before this
	 * method is relied on. It is assumed that the flight and client data
	 * files exist in the same directory.
	 */
	public  void saveData(String filePath) throws  IOException
	{
		cm.setClientInfoPath(filePath + "/clients.txt");
		fm.setFilePath(filePath + "/flights.txt");
		cm.writeClientItineraries(filePath + "/trips.txt");
		cm.writeClientInfoToFile();
		fm.writeFlightInfoToFile();
	}
}
