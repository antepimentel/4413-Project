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
    
	private static final String MODEL_TAG = "model";
	private static final String COMM_TAG = "comm";
	private static final String BID = "bid";
	private static final String CATEGORY_NAME = "categoryName", BOOK_TITLE="bookTitle", NUMBER_RATING = "numberRating",
			REVIEW_TEXT = "reviewText", CID = "cid";
	
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
	
		System.out.println("Recieved GET request");
		
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
			response.sendRedirect(this.getServletContext().getContextPath() + "/MainPage.jspx");
			
		} else {
			//If statement for when a book title hyperlink is clicked
			String bookTitle = request.getParameter(BOOK_TITLE);
			String categoryName = request.getParameter(CATEGORY_NAME);

			String bid = request.getParameter(BID);
			
			// I can add this into the main servlet and make the search look nicer in code, we dont need another servelet for searching
			//If a category name link is clicked forward to the category servlet 
			if (categoryName != null && bookTitle == null) {
				response.sendRedirect("http://localhost:8080/4413-Project/Category/?" + CATEGORY_NAME +"="+  categoryName);
				return;
			}
			
			
			//If a book title link has been clicked String bid will have its value as the clicked book title's corresponding bid

			if (bid != null) {
				Map<String, BookBean> books;
				String responseMsg;
				try {
					books = model.getBooks(bookTitle, "", bid, categoryName);
					responseMsg = getHTMLResponse(books);
					request.setAttribute(BID, bid);
					response.getWriter().append(responseMsg);

					System.out.println(responseMsg);				

				} catch (SQLException e) {
					responseMsg = "There was an issue handling your request";
				} 			

				String allReviewsResponse = createReviewsTable(model.getReviewsDB(bid));
				System.out.println(allReviewsResponse);				
				response.getWriter().append(allReviewsResponse);
				
				String submitReviewForm  = "<form action='' method='POST'><table>" + 
						" <tr><td> Review text: <textarea name='reviewText' id='reviewText' cols='30' rows='10'></textarea></td></tr>" + 
						" <tr><td> Number rating out of 10 <input type='number' name='numberRating'></td></tr>" + 
						" <tr><td> Your customer id or username <input type='text' name='cid'><br><td></tr>" + 
						" <tr><td> The book id <input type='text' name='bid' value='" + request.getParameter(BID) + "' readonly><tr><td>" + 
						" <tr><td> The book title <input type='text' name='bookTitle' value='" + request.getParameter(BOOK_TITLE) + "' readonly><tr><td>" + 

						//The submit review button needs validation from JS or by checking for customer id's and purchase orders
						"<tr><td><input type='submit' name='reviewSubmit' value='Submit Review'></input><tr><td>" + 
						"<table></form>";
				
						
				response.getWriter().append(submitReviewForm);

				return;
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
					+ "/Main/?bookTitle=" + request.getParameter(BOOK_TITLE) + "&bid=" + request.getParameter(BID));
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
			
		String url = "http://localhost:8080/4413-Project/Main/";
		
			String result = "Displaying " + books.size() + " items.";
			
			if(books.size() > 0) {
				result = result
						+"<TABLE border='1'>"
						+"<TR>"
						+"<TD>Title</TD>"
						+"<TD>Price</TD>"
						+"<TD>Category</TD>"
						+"</TR>";
				
				for(BookBean bean: books.values()) {
					String addToCartButton = "<A href='" + url + "?addTo=notNull&categoryName="+ bean.getCategory() + "&bookTitle=" + bean.getTitle() + "'>Add to cart button</A>";
					result = result + "<TR>"
							+"<TD><A href='" + url + "?bid="+ bean.getBid() +"&bookTitle=" +bean.getTitle()+"'> " + bean.getTitle() + "</A>" + addToCartButton + "</TD>"
							+"<TD>"+bean.getPrice()+"</TD>"
							+"<TD><A href='http://localhost:8080/4413-Project/Category/?categoryName="+ bean.getCategory() +"'>" +bean.getCategory()+"</A></TD>"
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
