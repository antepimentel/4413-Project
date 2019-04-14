package bean;

import java.text.SimpleDateFormat;
import java.util.Date;

public class VisitEventBean {

	private SimpleDateFormat df = new SimpleDateFormat("dd/MM/YY");
	private String[] eventTypes = {"VIEW", "CART", "PURCHASE"};
	private Date date;
	private String bid;
	private String eventType;
	
	public VisitEventBean(Date day, String bid, String eventType) {
		this.date = day;
		this.bid = bid;
		this.eventType = eventType;
	}
	
	
	
	public Date getDay() {
		return date;
	}
	
	public String getBid() {
		return bid;
	}
	
	public String getEventType() {
		return eventType;
	}
	
	public void setDay(Date day) {
		this.date = day;
	}
	
	public void setBid(String bid) {
		this.bid = bid;
	}
	
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
}
