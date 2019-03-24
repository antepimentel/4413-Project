package bean;

public class BookReviewBean {

	private String bid;
	private String uid;
	private int rating;
	private String review_text;
	/**
	 * @param bid
	 * @param usernameID
	 * @param rating
	 * @param review_text
	 */
	public BookReviewBean(String bid, String usernameID, int rating, String review_text) {
		super();
		this.bid = bid;
		this.uid = usernameID;
		this.rating = rating;
		this.review_text = review_text;
	}
	/**
	 * @return the bid
	 */
	public String getBid() {
		return bid;
	}
	/**
	 * @return the cid
	 */
	public String getUid() {
		return uid;
	}
	/**
	 * @return the rating
	 */
	public int getRating() {
		return rating;
	}
	/**
	 * @return the review_text
	 */
	public String getReview_text() {
		return review_text;
	}
	/**
	 * @param bid the bid to set
	 */
	public void setBid(String bid) {
		this.bid = bid;
	}
	/**
	 * @param cid the cid to set
	 */
	public void setUID(String uid) {
		this.uid = uid;
	}
	/**
	 * @param rating the rating to set
	 */
	public void setRating(int rating) {
		this.rating = rating;
	}
	/**
	 * @param review_text the review_text to set
	 */
	public void setReview_text(String review_text) {
		this.review_text = review_text;
	}
	
	
	
}
