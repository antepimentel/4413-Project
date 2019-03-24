package bean;

import java.util.ArrayList;

public class BookBean {

	private final String[] categories = {"Science", "Fiction", "Engineering"};
	
	private String bid;
	private String title;
	private int price;
	private String category;
	private String rating;
	private ArrayList<BookReviewBean> reviews;
	
	public BookBean(String bid, String title, int price, String category) {
		this.bid = bid;
		this.title = title;
		this.price = price;
		this.category = category;
		
		
		// TODO: Validate 
	}
	
	
	public BookBean(String bid, String title, int price, String category, String rating) {
		this.bid = bid;
		this.title = title;
		this.price = price;
		this.category = category;
		this.setRating(rating);
		
		
		// TODO: Validate 
	}

	public String[] getCategories() {
		return categories;
	}

	public String getBid() {
		return bid;
	}

	public String getTitle() {
		return title;
	}

	public int getPrice() {
		return price;
	}

	public String getCategory() {
		return category;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public void setCategory(String category) {
		this.category = category;
	}


	public String getRating() {
		return rating;
	}


	public void setRating(String rate) {
		this.rating = rate;
	}


	public ArrayList<BookReviewBean> getReviews() {
		return reviews;
	}


	public void setReviews(ArrayList<BookReviewBean> reviews) {
		this.reviews = reviews;
	}
}
