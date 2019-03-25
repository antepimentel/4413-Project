package ctrl;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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
import bean.BookReviewBean;
import bean.VisitEventBean;
import dao.VisitEventDAO;
import model.Model;

/**
 * Servlet implementation class 
 */
@WebServlet({"/Main", "/Main/*"})
public class Main extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	// Attribute tags
	private static final String MODEL_TAG = "model";
	private static final String COMM_TAG = "comm";
	
	// Page element tags
	private static final String BID = "bid";
	private static final String CATEGORY_NAME = "categoryName";
	private static final String PRICE = "price";
	private static final String TITLE = "title";
	private static final String BOOK_TITLE="bookTitle";
	private static final String NUMBER_RATING = "numberRating";
	private static final String REVIEW_TEXT = "reviewText";
	private static final String CID = "cid";
	private static final String REVIEW_LIST = "reviewList";
	private static final String BOOK_LIST = "bookList";
	private static final String ERROR = "error";
	
	// Page names/filename
	private static final String JSP_MAIN = "/MainPage.jspx";
	private static final String JSP_VIEWBOOK = "/ViewBook.jspx";
	
	// URL Tags
	private static final String SEARCH_TAG = "/Main/search";
	private static final String VIEW_TAG = "/Main/view";
	
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
			application.setAttribute(MODEL_TAG, model);
			System.out.println("Model initialized");
		} catch (NamingException e) {
			//e.printStackTrace();
			System.out.println("There was an error an error initializing the model.");
		}
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		System.out.println("Recieved GET request: URL -> " + request.getRequestURL());
		
		ServletContext application = getServletContext();
		HttpSession session = request.getSession(true);
		Model model = (Model)application.getAttribute(MODEL_TAG);
		
		
		
		//Register
		if(request.getParameter("register") != null) {
			request.getRequestDispatcher("/Register.jspx").forward(request, response);
		}
		
		// Check if logged in
		if(session.getAttribute("userID") == null) {
			request.setAttribute(ERROR, "You must login first!");
			request.getRequestDispatcher("/Login.jspx").forward(request, response);
		}

		// This is a first time visit to the site
		if (request.getQueryString() == null) {
			System.out.println("GETL Request: fresh visit");
			response.sendRedirect(this.getServletContext().getContextPath() + JSP_MAIN);
		
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
			request.setAttribute(ERROR, responseMsg);
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
				
				responseMsg = "none";
				target = JSP_VIEWBOOK;

			} catch (SQLException e) {
				responseMsg = "There was an issue handling your request";
				target = JSP_VIEWBOOK;
			}
			request.setAttribute(ERROR, responseMsg);
			request.getRequestDispatcher(target).forward(request, response);
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Recieved POST request");
		
		String cid = request.getParameter(CID), 
				reviewText = request.getParameter(REVIEW_TEXT), 
				numberRating = request.getParameter(NUMBER_RATING),
				bid = request.getParameter(BID);
		
		Model model = (Model) request.getServletContext().getAttribute(MODEL_TAG);

		//If statement for the submission of a review 
		if (cid != null && reviewText != null && numberRating != null && bid != null) {
			System.out.println("BID " + bid + "\n"
					+ "cid " + Integer.parseInt(cid.trim()) 
					+"\n rated " + Integer.parseInt(numberRating.trim()) 
					+ "\n text: " + reviewText);
			
			model.addBookReview(bid, Integer.parseInt(cid.trim()), Integer.parseInt(numberRating.trim()), reviewText);
			response.sendRedirect(this.getServletContext().getContextPath() 
					+ VIEW_TAG +"?bookTitle=" + request.getParameter(BOOK_TITLE) + "&bid=" + request.getParameter(BID));
		} else {
			doGet(request, response);
			
		}
	}
	
}
