package bean;

public class CustomerBean {

	private int id;
	private String username;
	private String password;
	private String fname;
	private String lname;
	private int address;
	
	public CustomerBean(String username, String password, String fname, String lname, int address) {
		this.username = username;
		this.password = password;
		this.fname = fname;
		this.lname = lname;
		this.address = address;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @return the fname
	 */
	public String getFname() {
		return fname;
	}
	/**
	 * @return the lname
	 */
	public String getLname() {
		return lname;
	}
	/**
	 * @return the address
	 */
	public int getAddress() {
		return address;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @param fname the fname to set
	 */
	public void setFname(String fname) {
		this.fname = fname;
	}
	/**
	 * @param lname the lname to set
	 */
	public void setLname(String lname) {
		this.lname = lname;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(int address) {
		this.address = address;
	}
	
	

}
