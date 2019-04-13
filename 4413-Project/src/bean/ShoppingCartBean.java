package bean;

public class ShoppingCartBean {
	
	private String cid; 
	private int quantity;
	private String bid;
//	private String bookCover;
	private int priceOfAllCopies;
	private BookBean book;
	
	public BookBean getBook() {
		return book;
	}

	public void setBook(BookBean book) {
		this.book = book;
	}

	public int getPrice() {
		return book.getPrice();
	}

	public ShoppingCartBean(String cid, String bid, int quantity) {
		super();
		this.cid = cid;
		this.quantity = quantity;
		this.bid = bid;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}

	public int getPriceOfAllCopies() {
		return priceOfAllCopies;
	}

	public void setPriceOfAllCopies() {
		this.priceOfAllCopies = book.getPrice() * quantity;
	}
	
	
}
