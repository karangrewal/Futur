package users;

import java.io.IOException;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import info.NoSuchClientException;
import trips.Flight;
import trips.Itinerary;

/**
 * A subclass of User, represents a Client capable of having personal and 
 * billing informational contents.
 */
public class Client extends User implements Serializable{

	/** Personal and billing information for a client */
	private String lastName;
	private String firstName;
	private String email;
	private String address;
	private String creditCardNum;
	private String expiryDate;

	/** An ArrayList to keep track of booked itineraries for a client */
	private List<Itinerary> bookedItineraries;

	/**
	 * Initializes a new Client with the given fields.
	 *
	 * @param lastName the last name of this client
	 * @param firstName the first name of this client
	 * @param email the email address of this client
	 * @param address the address of this client
	 * @param creditCardNum the credit card number of this client
	 * @param expiryDate the expiry date of this client's credit card
	 */
	public Client(String lastName, String firstName, String email,
				  String address, String creditCardNum, String expiryDate) {
		this.lastName = lastName;
		this.firstName = firstName;
		this.email = email;
		this.address = address;
		this.creditCardNum = creditCardNum;
		this.expiryDate = expiryDate;

		this.bookedItineraries = new ArrayList<Itinerary>();
	}

	/**
	 * Returns the last name of this client.
	 *
	 * @return a string of last name of this client
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Set the last name of this client.
	 *
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Returns the first name of this client.
	 *
	 * @return a string of first name of this client
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Set the first name of this client.
	 *
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Returns the email address of this client.
	 *
	 * @return a string of email of this client
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Set the email of this client.
	 *
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Returns the address of this client.
	 *
	 * @return string a string of address of this client
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Set the address of this client
	 *
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Returns the credit card number of this client
	 *
	 * @return a stirng of credit card number of this client
	 */
	public String getCreditCardNum() {
		return creditCardNum;
	}

	/**
	 * Set the credit card number of this client
	 *
	 * @param creditCardNum the creditCardNum to set
	 */
	public void setCreditCardNum(String creditCardNum) {
		this.creditCardNum = creditCardNum;
	}

	/**
	 * Returns the expiry date of this client.
	 *
	 * @return a string of expiry date of this client
	 */
	public String getExpiryDate() {
		return expiryDate;
	}

	/**
	 * Set expiry date of this client.
	 *
	 * @param expiryDate the expiryDate to set
	 */
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	/**
	 * Return an array of booked itineraries.
	 *
	 * @return booked itineraries for this client
	 */
	public List<Itinerary> getBookedItineraries() {
		return this.bookedItineraries;
	}

	/**
	 * Returns a multi-line String representation of this Client's first and 
	 * last name followed by their email address.
	 *
	 * @return the String representation of this Client's first name, last name
	 * and email address, each item on its own line.
	 */
	public String viewPersonalInfo() {
		return String.format("First Name: %s\nLast Name: %s\nEmail Address: "
						+ "%s\nStreet Address: %s", this.getFirstName(),
				this.getLastName(), this.getEmail(), this.getAddress());
	}

	/**
	 * Returns a multi-line String representation of this Client's credit card
	 * number and said credit card's expiration date.
	 *
	 * @return the String representation of this Client's credit number, and 
	 * credit card expiration date, each on its own line.
	 */
	public String viewBillingInfo() {
		return String.format("Credit Card Number: %s\nExpires: %s\n",
				this.getCreditCardNum(), this.getExpiryDate());
	}

	/**
	 * Sets booked itineraries for the client.
	 *
	 * @param newItineraries sets as booked
	 */
	public void setBookedItineraries(List<Itinerary> newItineraries) {
		this.bookedItineraries = newItineraries;
	}

	/**
	 * Adds the selectedItinerary to this clients' booked itineraries if
	 * selectedItinerary is not null. Resets selectedItinerary.
	 */
	public void bookItinerary() {
		if (this.selectedItinerary != null) {
			this.bookedItineraries.add(this.selectedItinerary);
			this.selectedItinerary = null;
		}
	}

	/**
	 * Adds a given itinerary to this clients booked itineraries.
	 *
	 * @param itinerary the new itinerary to be booked by the client
	 */
	public void bookItinerary(Itinerary itinerary) {
		this.bookedItineraries.add(itinerary);
	}

	/**
	 * Displays the booked itineraries.
	 *
	 * @return a string of all of the booked itineraries
	 */
	public String displayBookedItineraries() {
		String returnString = "";
		for (Itinerary itinerary: this.bookedItineraries) {
			returnString += itinerary.toString() + "\n";
		}
		return returnString;
	}

	/**
	 * Returns a csv representation of an itinerary.
	 *
	 * @return a csv string of an itinerary
	 */
	public String itinerariesCSVString() {
		String toReturn = this.getEmail() + ",";
		for(Itinerary i : this.getBookedItineraries()) {
			toReturn += i.flightsCSVString() + ":";
		}
		return toReturn;
	}

	/**
	 * Returns the information of this client in string format.
	 *
	 * @return a string of the information of this client.
	 */
	public String toString(){
		String result = new String();
		result += lastName + "," + firstName + "," + email + "," + address +
				"," + creditCardNum + "," + expiryDate;
		return result;
	}
}	
