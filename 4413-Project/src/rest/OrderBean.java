package rest;

public class OrderBean {
	private int title; 
	public int getTitle() {
		return title;
	}
	public void setTitle(int title) {
		this.title = title;
	}
	public int getAuthor() {
		return author;
	}
	public void setAuthor(int author) {
		this.author = author;
	}
	public int getBid() {
		return bid;
	}
	public void setBid(int bid) {
		this.bid = bid;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public OrderBean(int title, int author, int bid, int quantity, String email, String street, String country,
			String province, String city, String status) {
		super();
		this.title = title;
		this.author = author;
		this.bid = bid;
		this.quantity = quantity;
		this.email = email;
		this.street = street;
		this.country = country;
		this.province = province;
		this.city = city;
		this.status = status;
	}
	private int author; 
	private int bid; 
	private int quantity; 
	private String email;
	private String street;
	private String country; 
	private String province; 
	private String city; 
	private String status;
	
}
