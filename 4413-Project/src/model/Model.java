package model;

import javax.naming.NamingException;

import java.sql.SQLException;
import java.util.*;
import dao.*;
import bean.*;

public class Model {

	private VisitEventDAO visitDAO;
	private BookDAO bookDAO;
	private AddressDAO addressDAO;
	private PODAO projectOrderDAO; 
	private POItemDAO poItemDAO;
	private BookReviewDAO bookReviewDAO;
	private CustomerDAO customerDAO;
	private ShoppingCartDAO shoppingCartDAO;
	
	public Model() throws NamingException {
		this.addressDAO = new AddressDAO();
		this.bookDAO = new BookDAO();
		this.visitDAO = new VisitEventDAO();
		this.projectOrderDAO = new PODAO();
		this.poItemDAO = new POItemDAO();
		this.bookReviewDAO = new BookReviewDAO();
		this.customerDAO = new CustomerDAO();
		this.shoppingCartDAO = new ShoppingCartDAO();
	}
	
	//===========================
	// BOOK METHODS
	//===========================
	public Map<String, BookBean> getBooks(String title, String price, String author, String category) throws SQLException{
		return bookDAO.retrieve(title, price, author, category);
	}
	
	public Map<String, BookBean> getAllBooks() throws SQLException{
		return bookDAO.retrieveAllBooks();
	}
	
	public ArrayList<BookReviewBean> getReviewsDB(String bid) {
		ArrayList<BookReviewBean> result = null;
		try {
			result = bookReviewDAO.retrieveByBook(bid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public void addBookReview(String bid, String cid, int numberRating, String textReview) {
		try {
			System.out.println("check");
			bookReviewDAO.addReview(bid, cid, numberRating, textReview);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//===========================
	// CUSTOMER METHODS
	//===========================
	
	public CustomerBean getCustoemrLogin(String user, String pass) throws SQLException {
		return customerDAO.retrieveByLogin(user, pass);
	}
	

	public void registerCustomer(CustomerBean customer, String conf_password, AddressBean address)
			throws SQLException, CustomException {

		int FIELD_LENGTH_LIMIT = 20;

		// Server side validation
		if (customerDAO.retrieveByUsername(customer.getUsername()) == true) {
			throw new CustomException("Username is taken");

		} else if (checkPasswordFormat(customer.getPassword()) == false) {
			throw new CustomException("Password must be of this format...");

		} else if (!customer.getPassword().equals(conf_password)) {
			throw new CustomException("Passwords must match");

		} else if (address.getStreet().length() > FIELD_LENGTH_LIMIT) {
			throw new CustomException("Address field is too long");

		} else if (address.getCountry().length() > FIELD_LENGTH_LIMIT) {
			throw new CustomException("Country field is too long");

		} else if (address.getCountry().length() > FIELD_LENGTH_LIMIT) {
			throw new CustomException("Province/State field is too long");

		} else if (address.getZip().length() > FIELD_LENGTH_LIMIT) {
			throw new CustomException("Postal/Zip Code field is too long");

		} else if (checkPhoneFormat(address.getPhone()) == false) {
			throw new CustomException("Phone field is not the proper format");

		} 

		// Add info into DB
		customerDAO.insertCustomer(customer);
		addressDAO.insertAddress(address);

	}

	/**
	 * Checks format of the password
	 * 
	 * @param password
	 * @return true if format is good
	 */
	private boolean checkPasswordFormat(String password) {
		// TODO
		boolean result = true;

		if (password.length() > 20) {
			result = false;
		}

		return result;
	}

	/**
	 * Checks the format of the phone number
	 * 
	 * @param phone
	 * @return
	 */
	private boolean checkPhoneFormat(String phone) {
		// TODO
		boolean result = true;

		if (phone.length() > 20) {
			result = false;
		}

		return result;
	}
	
	//===========================
	// SHOPPING CART METHODS
	//===========================
	
	public ArrayList<ShoppingCartBean> getCart(String cid){
		try {
			return this.shoppingCartDAO.getShoppingCartContents(cid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<ShoppingCartBean> getCompleteCart(String cid, ArrayList<ShoppingCartBean> cart)
	{
		Map<String, BookBean> books = null;
		try {
			books = getAllBooks();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//For every cartBean item add the BookBean corresponding to the bid so the CartBean has all the bid's book info
		for (ShoppingCartBean cartBean: cart) {
			if (books.containsKey(cartBean.getBid())) {
				cartBean.setBook(books.get(cartBean.getBid()));
			}
		}
		
		return cart;
		
	}
	
}
