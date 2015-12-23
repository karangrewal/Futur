package info;

public class NoSuchFlightException extends Exception {

	public NoSuchFlightException() {
		super();
	}
	
	public NoSuchFlightException(String msg) {
		super(msg);
	}
}