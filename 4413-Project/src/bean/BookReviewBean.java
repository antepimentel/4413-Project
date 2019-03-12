package bean;

public class BookReviewBean {

	private String bid;
	private int cid;
	private int rating;
	private String review_text;
	/**
	 * @param bid
	 * @param cid
	 * @param rating
	 * @param review_text
	 */
	public BookReviewBean(String bid, int cid, int rating, String review_text) {
		super();
		this.bid = bid;
		this.cid = cid;
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
	public int getCid() {
		return cid;
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
	public void setCid(int cid) {
		this.cid = cid;
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
