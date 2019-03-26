package model;

public class CustomException extends Exception {

	private final String msg;
	
	public CustomException(String msg) {
		super();
		this.msg = msg;
	}
	
	public String getMsg() {
		return this.msg;
	}

}
