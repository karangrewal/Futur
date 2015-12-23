package info;


import java.io.Serializable;

/**
 * An abstract class representing an object with the ability to write specified
 * contents of the object to a file.
 */
public abstract class Manager implements Serializable{
	
	/**
	 * Writes all the informational content of this Information object to a 
	 * file.
	 */
	public void writeInfoToFile() {}
	
	/**
	 * Makes changes to a data structure containing the informational content
	 * of this Information object, given an identifying String to obtain the
	 * correct Object from the data structure, a String to identify the 
	 * specific sort of stored information of the target Object, and the new
	 * changes themselves in the form of a String.
	 * @param key String identity to be used to obtain the correct Object from
	 * the data structure
	 * @param sort String to identify the specific sort of stored information
	 *  of the target Object
	 * @param info String format of the new informational content
	 */
	public void editStoreInfo(String key, String sort, String info) {}
	
	/**
	 * Retrieves a form of a specific piece of informational content via a 
	 * reference to a String that will be used to obtain the specific content.
	 * @param key String that will be used to obtain the specific content.
	 */
	public void retrieveInfo(String key) {}
}
