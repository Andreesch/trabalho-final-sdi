package sdi;

import java.io.Serializable;
import java.util.Date;

public class MessageData implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String message;
	private Date date;
	
	public MessageData(String message) {
		this.message = message;
		this.date = new Date();
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
}
