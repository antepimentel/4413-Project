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
		} catch (NamingException e) {
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
				responseMsg = "There was an issue handling your request";
			} 
			response.getWriter().append(responseMsg);
			
		} else {
			System.out.println("NORMAL Request");
			response.sendRedirect(this.getServletContext().getContextPath() + "/MainPage.jspx");
		}	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Recieved POST request");
		doGet(request, response);
	}
	
	private String getHTMLResponse(Map<String, BookBean> books) {
			
			String result = "Displaying " + books.size() + " items.";
			
			if(books.size() > 0) {
				result = result
						+"<TABLE border=1>"
						+"<TR>"
						+"<TD>Title</TD>"
						+"<TD>Price</TD>"
						+"<TD>Category</TD>"
						+"</TR>";
				
				for(BookBean bean: books.values()) {
					result = result + "<TR>"
							+"<TD>"+bean.getTitle()+"</TD>"
							+"<TD>"+bean.getPrice()+"</TD>"
							+"<TD>"+bean.getCategory()+"</TD>"
							+"</TR>";
				}
				result = result + "</TABLE>";
			}		
			return result;
		}
}
