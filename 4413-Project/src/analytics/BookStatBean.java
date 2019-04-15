package analytics;

import javax.xml.bind.annotation.*;

@XmlRootElement(name="book")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder= {"bid", "title", "amount", "day"})
public class BookStatBean {

	@XmlElement
	private String title;
	@XmlElement
	private String bid;
	@XmlElement
	private int amount;
	@XmlElement
	private String day;
	
	public BookStatBean() {
		
	}
	
	/**
	 * @param title
	 * @param bid
	 * @param amount
	 */
	public BookStatBean(String title, String bid, int amount, String day) {
		this.title = title;
		this.bid = bid;
		this.amount = amount;
		this.day = day;
	}

	/**
	 * @return the day
	 */
	public String getDay() {
		return day;
	}

	/**
	 * @param day the day to set
	 */
	public void setDay(String day) {
		this.day = day;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the bid
	 */
	public String getBid() {
		return bid;
	}

	/**
	 * @return the amount
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @param bid the bid to set
	 */
	public void setBid(String bid) {
		this.bid = bid;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}

}
