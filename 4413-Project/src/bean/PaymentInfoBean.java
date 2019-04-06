package bean;

public class PaymentInfoBean {

	private String cardNum;
	private String cardholder;
	private String expDate;
	private String secCode;
	private String scrubbedCardNumber;
	/**
	 * @param cardNum
	 * @param cardholder
	 * @param expDate
	 * @param secCode
	 */
	public PaymentInfoBean(String cardNum, String cardholder, String expDate, String secCode) {
		this.cardNum = cardNum;
		this.cardholder = cardholder;
		this.expDate = expDate;
		this.secCode = secCode;
		this.scrubbedCardNumber = scrubCardNumber();
	}
	/**
	 * @return the scrubbedCardNumber
	 */
	public String getScrubbedCardNumber() {
		return scrubbedCardNumber;
	}
	/**
	 * @param scrubbedCardNumber the scrubbedCardNumber to set
	 */
	public void setScrubbedCardNumber(String scrubbedCardNumber) {
		this.scrubbedCardNumber = scrubbedCardNumber;
	}
	/**
	 * @return the cardNum
	 */
	public String getCardNum() {
		return cardNum;
	}
	/**
	 * @return the cardholder
	 */
	public String getCardholder() {
		return cardholder;
	}
	/**
	 * @return the expDate
	 */
	public String getExpDate() {
		return expDate;
	}
	/**
	 * @return the secCode
	 */
	public String getSecCode() {
		return secCode;
	}
	/**
	 * @param cardNum the cardNum to set
	 */
	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}
	/**
	 * @param cardholder the cardholder to set
	 */
	public void setCardholder(String cardholder) {
		this.cardholder = cardholder;
	}
	/**
	 * @param expDate the expDate to set
	 */
	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}
	/**
	 * @param secCode the secCode to set
	 */
	public void setSecCode(String secCode) {
		this.secCode = secCode;
	}
	/**
	 * Creates a printable version of the card number for security
	 * 
	 * @return 
	 */
	private String scrubCardNumber() {
		String result = "Card ending in: ";
		result = result + this.cardNum.substring(this.cardNum.length()-4);
		
		return result;
	}

}
