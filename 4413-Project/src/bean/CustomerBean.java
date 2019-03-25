package bean;

public class CustomerBean {

	private String[] types = {"CUSTOMER", "ADMIN", "PARTNER"};
	
	private String username;
	private String email;
	private String password;
	private String fname;
	private String lname;
	private String c_type;
	
	public CustomerBean(String username, String email, String password, String fname, String lname, String c_type) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.fname = fname;
		this.lname = lname;
		this.c_type = c_type;
	}
	
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return the c_type
	 */
	public String getC_type() {
		return c_type;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @param c_type the c_type to set
	 */
	public void setC_type(String c_type) {
		this.c_type = c_type;
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

}
