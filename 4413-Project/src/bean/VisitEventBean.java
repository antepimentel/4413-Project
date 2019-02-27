package bean;

public class VisitEventBean {

	private String[] eventTypes = {"VIEW", "CART", "PURCHASE"};
	private String day;
	private String bid;
	private String eventType;
	
	public VisitEventBean(String day, String bid, String eventType) {
		this.day = day;
		this.bid = bid;
		this.eventType = eventType;
	}
	
	public String getDay() {
		return day;
	}
	
	public String getBid() {
		return bid;
	}
	
	public String getEventType() {
		return eventType;
	}
	
	public void setDay(String day) {
		this.day = day;
	}
	
	public void setBid(String bid) {
		this.bid = bid;
	}
	
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
}
