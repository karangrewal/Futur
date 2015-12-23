package info;

import java.util.Map;
import java.util.Scanner;

import trips.Flight;
import trips.Itinerary;
import users.Client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * A subclass of Information, capable of obtaining and writing stored Client
 * objects to a file.
 */
public class ClientManager extends Manager{

	/**
	 * A map to store all clients by their email address
	 */
	private Map<String, Client> clients;

	/**
	 * A path to the file containing client information
	 */
	private String clientInfoFile;

	/**
	 * A path to the file containing client emails and passwords
	 */
	private String passwordsFile = "passwords.txt";

	/**
	 * A path to the file containing every client's booked itineraries
	 */
	private String bookedTrips;

	/**
	 * Initializes a new ClientInformation Object with an empty HashMap of
	 * clients.
	 */
	public ClientManager() {
		this.clients = new HashMap<>();
		this.clientInfoFile = new String();
	}

	/**
	 * Returns the path to the client information file
	 * @return a string of the path to the client information file
	 */
	public String getClientInfoPath() {
		return clientInfoFile;
	}

	/**
	 * Returns the path to the client passwords file
	 * @return a string of the path to the client passwords file
	 */
	public String getPasswordsPath() {
		return passwordsFile;
	}

	/**
	 * Sets a new path to a client information file.
	 * @param filePath the new path to the client information file
	 */
	public void setClientInfoPath(String filePath) {
		this.clientInfoFile = filePath;
	}

	/**
	 * Sets a new path to a client passwords file.
	 * @param filePath the new path to the client passwords file
	 */
	public void setPasswordsPath(String filePath) {
		this.passwordsFile = filePath;
	}

	/**
	 * Returns a HashMap of all clients stored in the object.
	 * @return a HashMap of all different clients
	 */
	public Map<String, Client> getClients() {
		return this.clients;
	}

	/**
	 * Reads client information from a given file and adds it to the HashMap.
	 *
	 * @param filePath a string of the path to the file
	 * @throws FileNotFoundException
	 */
	public void readFromClientFile(String filePath)
			throws FileNotFoundException {
		Scanner scanner = new Scanner(new FileInputStream(filePath));
		String record[];
		Client client;

		while (scanner.hasNextLine()) {
			record = scanner.nextLine().split(",");
			client = new Client(record[0], record[1],
					record[2], record[3], record[4], record[5]);

			this.add(client);
		}
		scanner.close();
	}

	/**
	 * Writes all client itineraries to the file at filePath, else throws an
	 * IOException if the filePath is invalid.
	 * @param filePath the location of the file to write to
	 * @throws IOException
	 */
	public void writeClientItineraries(String filePath) throws IOException {
		// format: email,itin1:itin2:...
		// where itin1 = f1-f2-...
		String toWrite = "";

		FileWriter fw = new FileWriter(filePath);
		BufferedWriter bw = new BufferedWriter(fw);

		for (Client c : this.getClients().values()) {
			toWrite += c.itinerariesCSVString() + "\n";
		}
		bw.write(toWrite);

		bw.close();
	}

	/**
	 * Reads each client's booked itineraries from a given file and adds the
	 * information to the HashMap.
	 *
	 * @param filePath a string of the path to the file
	 * @throws FileNotFoundException
	 * @throws NoSuchClientException
	 */
	public void readFromTripsFile(String filePath)
			throws FileNotFoundException, NoSuchClientException {

		// FILE FORMAT: email,flightNum1-...-flightNumx-:...:\n
		Scanner scanner = new Scanner(new FileInputStream(filePath));
		String record[];

		while (scanner.hasNext()) {
			record = scanner.nextLine().split(",");
			for (Object i : record) {
				System.out.println(i);
			}

			if (record.length > 1) {
				if (this.getClients().containsKey(record[0])) {
					Client c = (Client) this.getClients().get(record[0]);
					List<Itinerary> itineraries = this.buildItineraries(record[1], c);
					c.setBookedItineraries(itineraries);
				} else {
					throw new NoSuchClientException();
				}
			}
		}
		scanner.close();

	}

	/**
	 * A helper function for readFromTripsFile to build multiple itineraries
	 * for a given client.
	 *
	 * @param text   raw text from the trips file
	 * @param client the client whose information is being retrieved
	 * @return a List of client's itineraries
	 */
	public List<Itinerary> buildItineraries(String text, Client client) {

		List<Itinerary> itineraries = new ArrayList<Itinerary>();
		String[] itinerariesText = text.split(":");

		for (String i : itinerariesText) {
			Itinerary f = this.buildItinerary(i, client);
			itineraries.add(f);
		}

		return itineraries;
	}

	/**
	 * A helper function for buildItineraries to build an itinerary and
	 * get all flight information for a given client.
	 *
	 * @param text   raw text from the trips file
	 * @param client the client whose information is being retrieved
	 * @return an itinerary belonging to client
	 */
	public Itinerary buildItinerary(String text, Client client) {
		Itinerary itinerary = new Itinerary();
		String[] flightsText = text.split("-");

		for (String i : flightsText) {
			try {
				if (i != "\n") {
					itinerary.addFlight(client.getFlightManager().retrieveFlightInfo(Integer.parseInt(i)));
				}
			} catch (NumberFormatException | NoSuchFlightException e) {
			}
		}

		return itinerary;
	}

	/**
	 * Returns a client by giving his/her unique email address.
	 *
	 * @param email the email of the client that the user wants to retrieve
	 * @return the stored flight matching the unique email
	 * @throws NoSuchClientException if no client is found with the given email
	 */
	public Client retrieveClientInfo(String email) throws NoSuchClientException {
		// The email is unique for each client and is used as the key for
		// the client.
		if (clients.containsKey(email)) {
			return (Client) clients.get(email);
		}
		// If the given email is not stored
		throw new NoSuchClientException("No such client information");
	}

	/**
	 * Edit the personal information of a client by giving his or her
	 * email address and the information that he/she wants to change.
	 *
	 * @param email the email of this client
	 * @param sort  the information that the users want to change
	 * @param info  the new information
	 * @throws NoSuchClientException if the email is not stored under a client
	 */
	public void editClientInfo(String email, String sort, Object info)
			throws NoSuchClientException {
		// Retrieve the client and edit the information specified
		Client client = this.retrieveClientInfo(email);
		switch (sort) {
			case "firstname":
				client.setFirstName((String) info);
				break;
			case "lastname":
				client.setLastName((String) info);
				break;
			case "email":
				clients.remove(client.getEmail());
				client.setEmail((String) info);
				this.add(client);
				break;
			case "address":
				client.setAddress((String) info);
				break;
			case "creditcardnum":
				client.setCreditCardNum((String) info);
				break;
			case "expirydate":
				client.setExpiryDate((String) info);
				break;
		}
	}

	/**
	 * Write all client information to a specified file.
	 * @throws IOException
	 */
	public void writeClientInfoToFile() throws IOException {
		FileWriter fwClient = new FileWriter(this.clientInfoFile);
		BufferedWriter bwClient = new BufferedWriter(fwClient);

		bwClient.write(this.toString());
		bwClient.close();
	}

	/**
	 * Add a client to the HashMap that stores current clients.
	 * @param client a client which user wants to add to ClientInformation
	 */
	public void add(Client client) {
		clients.put(client.getEmail(), client);
	}

	/**
	 * Returns the HashMap of currently stored clients in string format, each
	 * client on a newline.
	 * @return a string of all clients currently stored
	 */
	public String toString() {
		// Add the string representation of each client, one per new line, to
		// the result string and return it.
		String result = new String();
		for (Client client : clients.values()) {
			result += client.toString() + "\n";
		}
		return result;
	}
}