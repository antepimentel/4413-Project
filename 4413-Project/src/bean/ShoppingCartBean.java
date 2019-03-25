package bean;

public class ShoppingCartBean {

		public ShoppingCartBean(String title, int quantity, int price, String bookCover) {
		super();
		this.title = title;
		this.quantity = quantity;
		this.price = price;
		this.bookCover = bookCover;
	}
		public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getBookCover() {
		return bookCover;
	}
	public void setBookCover(String bookCover) {
		this.bookCover = bookCover;
	}
		private String title; 
		private int quantity;
		private int price;
		private String bookCover;
}
