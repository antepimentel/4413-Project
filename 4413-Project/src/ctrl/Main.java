package ctrl;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.BookBean;
import bean.CustomerBean;
import bean.ShoppingCartBean;
import bean.BookReviewBean;
import bean.VisitEventBean;
import model.Model;

/**
 * Servlet implementation class 
 */
@WebServlet({"/Main", "/Main/*"})
public class Main extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	// Page element tags
	private static final String BID = "bid";
	private static final String CATEGORY_NAME = "categoryName";
	private static final String PRICE = "price";
	private static final String TITLE = "title";
	private static final String BOOK_TITLE="bookTitle";
	private static final String NUMBER_RATING = "numberRating";
	private static final String REVIEW_TEXT = "reviewText";
	private static final String USERNAME_ID = "uid";
	private static final String REVIEW_LIST = "reviewList";
	private static final String BOOK_LIST = "bookList";
	
	// Page names/filename
	private static final String JSP_MAIN = "/MainPage.jspx";
	private static final String JSP_VIEWBOOK = "/ViewBook.jspx";
	
	// URL Tags
	private static final String SEARCH_TAG = "/Main/search";
	private static final String VIEW_TAG = "/Main/view";
	private static final String CART_TAG = "/Main/Cart";
	
	// Banner Button Tags
	private static final String BUTTON_NAME = "banner";
	private static final String BUTTON_LOGOUT = "Logout";
	private static final String BUTTON_CART = "Cart";
	private static final String BUTTON_PROFILE = "Profile";
	private static final String BUTTON_ADMIN = "Admin";
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Main() {
        super();
    }
    
    /**
     * 
     * @param config
     * @throws ServletException 
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
    	super.init(config);
    	
    	ServletContext application = getServletContext();
    	
    	// Initialize the model and store in the application context
    	try {
			Model model = new Model();
			application.setAttribute(Tags.SESSION_MODEL, model);
			System.out.println("Model initialized");
			
			application.setAttribute(Tags.TOTAL_ORDERS, 3);
		} catch (NamingException e) {
			//e.printStackTrace();
			System.out.println("There was an error an error initializing the model.");
		}
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		System.out.println("GET : MAIN : URL -> " + request.getRequestURL());
		
		//Check if user clicked viewcart button
		if(request.getRequestURI().endsWith(CART_TAG)) {
			String username = ((CustomerBean)request.getSession().getAttribute(Tags.SESSION_USER)).getUsername();
			request.getRequestDispatcher("/ShoppingCartServlet/?cid=" + username).forward(request, response);
			return;
		}
		
		ServletContext application = getServletContext();
		HttpSession session = request.getSession(true);
		Model model = (Model)application.getAttribute(Tags.SESSION_MODEL);
		
		// Just to make sure the visitor flag gets set, avoiding null pointers
		if(session.getAttribute(Tags.IS_VISITOR) == null) {
			session.setAttribute(Tags.IS_VISITOR, false);
		}
		
		if (request.getRequestURI().endsWith("visitor")) {
			
			session.setAttribute(Tags.IS_VISITOR, true);
			session.setAttribute(Tags.VISITOR_CART, new ArrayList<ShoppingCartBean>());
			
			request.setAttribute(Tags.ERROR, "Logged is as a visitor");
			request.getRequestDispatcher(JSP_MAIN).forward(request, response);
			
		
		// Check if not logged and is not a visitor
		} else if(session.getAttribute(Tags.SESSION_USER) == null && !(boolean)session.getAttribute(Tags.IS_VISITOR)) {
			session.setAttribute(Tags.ERROR, "You must login first!");
			//request.getRequestDispatcher("/Login.jspx").forward(request, response);
			response.sendRedirect(this.getServletContext().getContextPath() + Tags.SERVLET_LOGIN);
			
		// User has pressed a banner button
		} else if(request.getRequestURI().endsWith("bannerButton")) {
			System.out.print("BANNER BUTTON: ");
			
			String target = "";
			String msg = "";
			
			// Which button was pressed
			String button = request.getParameter(BUTTON_NAME);
			if(button.equals(BUTTON_CART)) {
				System.out.println(BUTTON_CART);
				
				target = Tags.SERVLET_CART;
				msg = "none";
				
			} else if(button.equals(BUTTON_PROFILE)) {
				System.out.println(BUTTON_PROFILE);
				
				target = Tags.SERVLET_PROFILE;
				msg = "none";
				
			} else if(button.equals(BUTTON_LOGOUT)) {
				System.out.println(BUTTON_LOGOUT);
				
				session.setAttribute(Tags.SESSION_USER, null);
				
				target = Tags.SERVLET_LOGIN;
				msg = "Logged out";
			
			} else if(button.equals(BUTTON_ADMIN)) {
				System.out.println(BUTTON_ADMIN);
				
				target = Tags.SERVLET_ADMIN;
				msg = "none";
			}
			session.setAttribute(Tags.ERROR, msg);
			response.sendRedirect(this.getServletContext().getContextPath() + target);
			//response.sendRedirect(this.getServletContext().getContextPath() + LOGIN_SERVLET);
		
		// This is a first time visit to the site
		} else if (request.getQueryString() == null) { 
			System.out.println("GETL Request: fresh visit");
			//response.sendRedirect(this.getServletContext().getContextPath() + "/Main" + JSP_MAIN);
			request.getRequestDispatcher(JSP_MAIN).forward(request, response);
		
		// This is a search request
		} else if (request.getRequestURI().endsWith(SEARCH_TAG)) {
			System.out.println("GET Request: SEARCH");
			
			Map<String, BookBean> books;
			String responseMsg = "";
			String target = "";
			try {
				String title = request.getParameter("title");
				String category = request.getParameter("category");
				
				books = model.getBooks(title, "", "", category);
				
				request.setAttribute(BOOK_LIST, books.values());
				
				target = JSP_MAIN;
				responseMsg = "none";
				
			} catch (SQLException e) {
				//e.printStackTrace();
				responseMsg = "There was an issue handling your request";
				target = JSP_MAIN;
			} 
			request.setAttribute(Tags.ERROR, responseMsg);
			request.getRequestDispatcher(target).forward(request, response);
			
		// This is a view request
		} else if (request.getRequestURI().endsWith(VIEW_TAG)){
			System.out.println("GET Request: VIEW");
			
			//If statement for when a book title hyperlink is clicked
			String bookTitle = request.getParameter(BOOK_TITLE);
			String categoryName = request.getParameter(CATEGORY_NAME);
			String bid = request.getParameter(BID);
			
			String responseMsg = "";
			String target = "";
			try {
				Map<String, BookBean> books = model.getBooks(bookTitle, "", bid, categoryName);
				ArrayList<BookReviewBean> reviews = model.getReviewsDB(bid);
				
				BookBean book = (BookBean)books.values().toArray()[0];
				request.setAttribute(BID, book.getBid());
				request.setAttribute(CATEGORY_NAME, book.getCategory());
				request.setAttribute(PRICE, book.getPrice());
				request.setAttribute(TITLE, book.getTitle());
				request.setAttribute(REVIEW_LIST, reviews);
				
				// Log the event
				Date date = new Date();
				VisitEventBean event = new VisitEventBean(date, bid, Tags.VISIT_VIEW);
				model.addVisitEvent(event);
				
				responseMsg = "none";
				target = JSP_VIEWBOOK;

			} catch (SQLException e) {
				responseMsg = "There was an issue handling your request";
				target = JSP_VIEWBOOK;
			}
			request.setAttribute(Tags.ERROR, responseMsg);
			request.getRequestDispatcher(target).forward(request, response);
		} 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("POST : MAIN : URL -> " + request.getRequestURL());
		
		String UID = request.getParameter(USERNAME_ID), 
				reviewText = request.getParameter(REVIEW_TEXT), 
				numberRating = request.getParameter(NUMBER_RATING),
				bid = request.getParameter(BID);
		
		Model model = (Model) request.getServletContext().getAttribute(Tags.SESSION_MODEL);

		//If statement for the submission of a review 
		if (UID != null && reviewText != null && numberRating != null && bid != null) {
			System.out.println("BID " + bid + "\n"
					+ "USERID " + UID 
					+"\n rated " + Integer.parseInt(numberRating.trim()) 
					+ "\n text: " + reviewText);
			
			model.addBookReview(bid, UID.trim(), Integer.parseInt(numberRating.trim()), reviewText);
			response.sendRedirect(this.getServletContext().getContextPath() 
					+ VIEW_TAG +"?bookTitle=" + request.getParameter(BOOK_TITLE) + "&bid=" + request.getParameter(BID));
		} else {
			doGet(request, response);
		}
	}
	
}
