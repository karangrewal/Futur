package info;

public class NoSuchClientException extends Exception{
	
	public NoSuchClientException(){
		super();
	}
		
	public NoSuchClientException(String msg){
		super(msg);
	}
}

