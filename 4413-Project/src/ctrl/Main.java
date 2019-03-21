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
	
	// Page names/filename
	private static final String JSP_MAIN = "/MainPage.jspx";
	private static final String JSP_VIEWBOOK = "/ViewBook.jspx";
	private static final String SEARCH_TAG = "/Main/search";
	
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
		Model model = (Model)application.getAttribute(MODEL_TAG);
		
		String type = request.getParameter(COMM_TAG);
		
		if(type != null && type.equals("ajax")) {
			System.out.println("AJAX Request");
			
			Map<String, BookBean> books;
			String responseMsg;
			try {
				String title = request.getParameter("title");
				String category = request.getParameter("category");
				
				books = model.getBooks(title, "", "", category);
				responseMsg = getHTMLResponse(books);
			} catch (SQLException e) {
				e.printStackTrace();
				responseMsg = "There was an issue handling your request";
			} 
			response.getWriter().append(responseMsg);
			
			
		} else if (request.getQueryString() == null) {
			System.out.println("NORMAL Request: fresh visit");
			response.sendRedirect(this.getServletContext().getContextPath() + JSP_MAIN);
			
		} else if (request.getRequestURI().endsWith(SEARCH_TAG)){
			
			//If statement for when a book title hyperlink is clicked
			String bookTitle = request.getParameter(BOOK_TITLE);
			String categoryName = request.getParameter(CATEGORY_NAME);
			String bid = request.getParameter(BID);
			
			// I can add this into the main servlet and make the search look nicer in code, we dont need another servelet for searching
			// If a category name link is clicked forward to the category servlet 
			if (categoryName != null && bookTitle == null) {
				response.sendRedirect("http://localhost:8080/4413-Project/Category/?" + CATEGORY_NAME +"="+  categoryName);
				return;
			}
			
			
			//If a book title link has been clicked String bid will have its value as the clicked book title's corresponding bid

			if (bid != null) {
				
				String responseMsg;
				try {
					Map<String, BookBean> books = model.getBooks(bookTitle, "", bid, categoryName);
					ArrayList<BookReviewBean> reviews = model.getReviewsDB(bid);
					
					//responseMsg = getHTMLResponse(books);
					BookBean book = (BookBean)books.values().toArray()[0];
					request.setAttribute(BID, book.getBid());
					request.setAttribute(CATEGORY_NAME, book.getCategory());
					request.setAttribute(PRICE, book.getPrice());
					request.setAttribute(TITLE, book.getTitle());
					request.setAttribute(REVIEW_LIST, reviews);
					
					//response.getWriter().append(responseMsg);
					
					request.getRequestDispatcher(JSP_VIEWBOOK).forward(request, response);
					//System.out.println(responseMsg);				

				} catch (SQLException e) {
					responseMsg = "There was an issue handling your request";
				} 			
			}
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
					+ SEARCH_TAG +"?bookTitle=" + request.getParameter(BOOK_TITLE) + "&bid=" + request.getParameter(BID));
		} else {
			doGet(request, response);
			
		}
	}
	
	/**
	 * Creates an HTML formatted response for the ajax call used by the search button 
	 * 
	 * @param books
	 * @return
	 */
	private String getHTMLResponse(Map<String, BookBean> books) {
			
		String url = "http://localhost:8080/4413-Project" + SEARCH_TAG;
		
			String result = "Displaying " + books.size() + " items.";
			
			if(books.size() > 0) {
				result = result
						+"<TABLE border='1'>"
						+"<TR>"
						+"<TD>Title</TD>"
						+"<TD>Price</TD>"
						+"<TD>Category</TD>"
						+"<TD></TD>"
						+"</TR>";
				
				for(BookBean bean: books.values()) {
					String addToCartButton = "<A href='" + url + "?addTo=notNull&categoryName="+ bean.getCategory() + "&bookTitle=" + bean.getTitle() + "'>Add to cart button</A>";
					result = result + "<TR>"
							+"<TD><A href='" + url + "?bid="+ bean.getBid() +"&bookTitle=" +bean.getTitle()+"'> " + bean.getTitle() + "</A></TD>"
							+"<TD>"+bean.getPrice()+"</TD>"
							+"<TD><A href='http://localhost:8080/4413-Project/Category/?categoryName="+ bean.getCategory() +"'>" +bean.getCategory()+"</A></TD>"
							+"<TD>"+addToCartButton+"</TD>"
							+"</TR>";
				}
				result = result + "</TABLE>";
			}		
			return result;
		}
	
	/**
	 * Creates an HTML formatted response for the list of reviews
	 * 
	 * @param list
	 * @return
	 */
	private String createReviewsTable(ArrayList<BookReviewBean> list) {
		
		String result = "There are  " + list.size() + " review(s) for this item.";
		
		if(list.size() > 0) {
			result = result
					+"<TABLE border='1'>"
					+"<TR>"
					+"<TD>cusID</TD>"
					+"<TD>Number Rating</TD>"
					+"<TD>Review</TD>"
					+"</TR>";
			
			for(BookReviewBean bean: list) {
				result = result + "<TR>"
						+"<TD>" + bean.getCid() + "</TD>"
						+"<TD>" + bean.getRating() +"</TD>"
						+"<TD>" + bean.getReview_text() +"</TD>"
						+"</TR>";
			}
			result = result + "</TABLE>";
		}		
		return result;
				
	}
	
}
