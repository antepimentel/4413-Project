package bean;

public class BookBean {

	private final String[] categories = {"Science", "Fiction", "Engineering"};
	
	private String pid;
	private String title;
	private int price;
	private String category;
	
	public BookBean(String pid, String title, int price, String category) {
		this.pid = pid;
		this.title = title;
		this.price = price;
		this.category = category;
		
		
		// TODO: Validate 
	}

	public String[] getCategories() {
		return categories;
	}

	public String getPid() {
		return pid;
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

	public void setPid(String pid) {
		this.pid = pid;
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
}
