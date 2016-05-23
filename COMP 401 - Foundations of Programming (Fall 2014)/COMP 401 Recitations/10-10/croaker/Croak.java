package croaker;

import observer.Publisher;

/** Represents a message, including the sender and message text. */
public class Croak {
	private Publisher sender;
	private String text;
	
	public Croak(Publisher sender, String text) {
		this.sender = sender;
		this.text = text;
	}
	
	public Publisher getSender() {
		return sender;
	}
	
	public String getText() {
		return text;
	}
}
