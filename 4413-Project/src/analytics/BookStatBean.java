package analytics;

import javax.xml.bind.annotation.*;

@XmlRootElement(name="book")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder= {"bid", "title", "amount"})
public class BookStatBean {

	@XmlElement
	private String title;
	@XmlElement
	private String bid;
	@XmlElement
	private int amount;
	
	public BookStatBean() {
		
	}
	
	/**
	 * @param title
	 * @param bid
	 * @param amount
	 */
	public BookStatBean(String title, String bid, int amount) {
		this.title = title;
		this.bid = bid;
		this.amount = amount;
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
