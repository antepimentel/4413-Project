package bean;

public class BookBean {

	private final static String[] categories = {"Science", "Fiction", "Engineering"};
	
	private String bid;
	private String title;
	private int price;
	private String category;
	private double averageRating;
	
	public BookBean(String bid, String title, int price, String category) {
		this.bid = bid;
		this.title = title;
		this.price = price;
		this.category = category;
		
		
		// TODO: Validate 
	}

	public static String[] getCategories() {
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

	public double getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(double averageRating) {
		this.averageRating = averageRating;
	}

	
}
