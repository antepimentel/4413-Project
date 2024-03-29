package model;

import javax.naming.NamingException;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

import javax.xml.transform.stream.StreamResult;

import analytics.BookStatBean;
import analytics.ListWrapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import dao.*;
import bean.*;
import ctrl.Tags;

public class Model {

	private VisitEventDAO visitDAO;
	private BookDAO bookDAO;
	private AddressDAO addressDAO;
	private PODAO purchaseOrderDAO;
	private POItemDAO poItemDAO;
	private BookReviewDAO bookReviewDAO;
	private CustomerDAO customerDAO;
	private ShoppingCartDAO shoppingCartDAO;
	
	public Model() throws NamingException {
		this.addressDAO = new AddressDAO();
		this.bookDAO = new BookDAO();
		this.visitDAO = new VisitEventDAO();
		this.purchaseOrderDAO = new PODAO();
		this.poItemDAO = new POItemDAO();
		this.bookReviewDAO = new BookReviewDAO();
		this.customerDAO = new CustomerDAO();
		this.shoppingCartDAO = new ShoppingCartDAO();
	}

	// ===========================
	// BOOK METHODS
	// ===========================
	public Map<String, BookBean> getBooks(String title, String price, String author, String category) throws SQLException {
		return bookDAO.retrieve(title, price, author, category);
	}

	public Map<String, BookBean> getAllBooks() throws SQLException {
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
	
	public ArrayList<BookReviewBean> getBookInfo(String bid) {
		ArrayList<BookReviewBean> result = null;
		try {
			result = bookReviewDAO.retrieveByBook(bid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	// ===========================
	// SOAP METHODS
	// ===========================
	
	
	public String getProductInfo(String bid) {
		BookBean bean = null;
		try {
			bean = this.bookDAO.retrieveBook(bid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		ArrayList<BookReviewBean> reviews = null;
//		//get reviews
//		reviews = getBookInfo(bid);
//		double avgRating = 0.0;
//		
//		for(int i = 0; i < reviews.size(); i++) {
//			avgRating += reviews.get(i).getRating();
//		}
//		if(reviews != null) {
//			avgRating /= (1.0 + reviews.size());
//		} 
//		
//		result.setAverageRating(avgRating);
		String result = "ProductID: " + bean.getBid() + "/t Title:"+ bean.getTitle() + "/t Price: "+ bean.getPrice()
		+ "/t  Category: "+ bean.getCategory();
		return result;
		
	}

	// ===========================
	// CUSTOMER METHODS
	// ===========================

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

		} else if (address.getCity().length() > FIELD_LENGTH_LIMIT) {
			throw new CustomException("City field is too long");

		}else if (address.getCountry().length() > FIELD_LENGTH_LIMIT) {
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
	
	public ArrayList<AddressBean> getAddressListForCustomer(String username) throws SQLException {
		return addressDAO.getAddressListForCustomer(username);
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
	
	private ArrayList<ShoppingCartBean> getCart(String cid) throws SQLException{
		return this.shoppingCartDAO.getShoppingCartContents(cid);
	}
	
	public ArrayList<ShoppingCartBean> getCompleteCart(String cid) throws SQLException
	{
		Map<String, BookBean> books = null;
	
		books = getAllBooks();
		
		ArrayList<ShoppingCartBean> cart = getCart(cid);
		//For every cartBean item add the BookBean corresponding to the bid so the CartBean has all the bid's book info
		for (ShoppingCartBean cartBean: cart) {
			if (books.containsKey(cartBean.getBid())) {
				cartBean.setBook(books.get(cartBean.getBid()));
				cartBean.setPriceOfAllCopies();
			}
		}
		
		return cart;
	}
	
	/**
	 * To be called when user is a visitor
	 * 
	 * @param cart
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<ShoppingCartBean> getCompleteCart(ArrayList<ShoppingCartBean> cart) throws SQLException
	{
		Map<String, BookBean> books = null;
	
		books = getAllBooks();
		
		//For every cartBean item add the BookBean corresponding to the bid so the CartBean has all the bid's book info
		for (ShoppingCartBean cartBean: cart) {
			if (books.containsKey(cartBean.getBid())) {
				cartBean.setBook(books.get(cartBean.getBid()));
				cartBean.setPriceOfAllCopies();
			}
		}
		
		return cart;
	}
	
	/**
	 * For user when a visitor wants to checkout and has finished registering.
	 * The cart being stored in the session must be converted into the DB
	 * 
	 * @param user
	 * @param cart
	 * @throws SQLException
	 */
	public void convertVisitorCart(CustomerBean user, ArrayList<ShoppingCartBean> cart) throws SQLException {
		
		cart = getCompleteCart(cart);
		
		for(ShoppingCartBean item: cart) {
			insertOrUpdateShoppingCart(user.getUsername(), item.getBid(), item.getQuantity(), item.getPrice());
		}
		
		
	}
	
	public int getCartTotal(ArrayList<ShoppingCartBean> cart) {
		int total = 0;
		if (cart != null) {
			for(ShoppingCartBean cartBean: cart) {
				total += cartBean.getPriceOfAllCopies();
			}
		}
		return total;
	}

	public void insertOrUpdateShoppingCart(String cid, String bid, int quantity, int price) throws SQLException {
		this.shoppingCartDAO.addToCart(cid, bid, quantity, price);
		
	}
	
	public void addToCart(String cid, String bid, int price) throws SQLException {
		int quantity=0;

		quantity = this.shoppingCartDAO.checkBIDQuantity(cid, bid);
		
		//Increment current quantity to represent adding a copy of the bid item to the cart
		quantity++;
		this.insertOrUpdateShoppingCart(cid, bid, quantity, price);
	}
	
	public ArrayList<String> getCategories() {
		return this.bookDAO.retrieveAllCategories();
	}
	
	//===========================
	// ADDRESS METHODS
	//===========================
	
	public AddressBean getAddressByID(String id) throws SQLException {
		return this.addressDAO.getAddressByID(Integer.parseInt(id));
	}
	
	public void addAddress(AddressBean address) throws SQLException {
		this.addressDAO.insertAddress(address);
	}
	
	//===========================
	// PO METHODS
	//===========================
	
	public long createPO(POBean po) throws SQLException {
		return this.purchaseOrderDAO.createPO(po);
	}
	
	public void addItemToPO(POItemBean item) throws SQLException {
		this.poItemDAO.insertPOItem(item);
	}
	
	public ArrayList<POBean> getPOsForCustomer(String username) throws SQLException{
		ArrayList<POBean> pos = this.purchaseOrderDAO.getPOsForCustomer(username);
		return this.poItemDAO.completePOBeans(pos);
	}
	
	public ArrayList<BookStatBean> getBookStats() throws SQLException{
		return poItemDAO.getBookStats();
	}
	
	//===========================
	// ADMIN REPORT METHODS
	//===========================
	
	public void exportBookStats(int month, String pathname, String filename) throws SQLException, JAXBException, SAXException, IOException, CustomException, ParseException {
		
		if(month < 1 || month > 12) {
			throw new CustomException("Invalid Month");
		}
		
		String XML_SCHEMA = "/bookStatReport.xsd";
		// Get the actual path we need and grab the query result
		String absPath = pathname+"/"+filename;
		
		// Get book stats and sort out unwanted dates
		ArrayList<BookStatBean> stats = getBookStats();
		stats = sortBookStats(month, stats);
		ListWrapper lw = new ListWrapper(month, stats);
		
		// Setup the marshaller
		JAXBContext jc = JAXBContext.newInstance(lw.getClass());
		Marshaller marshaller = jc.createMarshaller();
		
		// Set the schema for the marshaller
		SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = sf.newSchema(new File(pathname+XML_SCHEMA));
		marshaller.setSchema (schema);
		
		// Configure the marshaller and setup the writer
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
		StringWriter sw = new StringWriter();
		sw.write("\n");
		marshaller.marshal(lw, new StreamResult(sw));

		// For debugging
		System.out.println("Writing to: "+absPath);
		System.out.println(sw.toString());
		
		// Finally, write to file 
		FileWriter fw = new FileWriter(absPath);
		fw.write(sw.toString());
		fw.close();
	}
	
	/**
	 * Return only the books that were sold this month
	 * 
	 * @param month
	 * @param books
	 * @return
	 * @throws ParseException
	 */
	private ArrayList<BookStatBean> sortBookStats(int month, ArrayList<BookStatBean> books) throws ParseException{
		System.out.println(month);
		month--;
		Date today = new Date();
		today.setMonth(month);
		
		SimpleDateFormat df = new SimpleDateFormat(Tags.DF_PATTERN);
		ArrayList<BookStatBean> result = new ArrayList<BookStatBean>();
		
		System.out.println(today.toString());
		for(BookStatBean book: books) {
			Date temp = df.parse(book.getDay());
			System.out.println(temp.toString());
			if(today.getMonth() == temp.getMonth() && today.getYear() == temp.getYear()) {
				result.add(book);
				System.out.println("BEEP");
			}
		}
		
		return result;
	}
	
	//===========================
	// VISIT EVENT METHODS
	//===========================
	
	public void addVisitEvent(VisitEventBean event) throws SQLException {
		this.visitDAO.insert(event);
	}
}
