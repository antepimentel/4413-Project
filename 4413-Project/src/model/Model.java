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
	
	public Model() throws NamingException {
		this.addressDAO = new AddressDAO();
		this.bookDAO = new BookDAO();
		this.visitDAO = new VisitEventDAO();
		this.projectOrderDAO = new PODAO();
		this.poItemDAO = new POItemDAO();
		this.bookReviewDAO = new BookReviewDAO();
		this.customerDAO = new CustomerDAO();
	}
	
	public Map<String, BookBean> getBooks(String title, String price, String author, String category) throws SQLException{
		return bookDAO.retrieve(title, price, author, category);
	}
	
	public Map<String, BookBean> getAllBooks() throws SQLException{
		return bookDAO.retrieveAllBooks();
	}
	
	public CustomerBean getCustoemrLogin(String user, String pass) throws SQLException {
		return customerDAO.retrieveByLogin(user, pass);
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
	public void addBookReview(String bid, int cid, int numberRating, String textReview) {
		try {
			System.out.println("check");
			bookReviewDAO.addReview(bid, cid, numberRating, textReview);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
