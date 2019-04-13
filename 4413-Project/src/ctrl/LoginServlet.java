package ctrl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.CustomerBean;
import model.Model;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet({"/login", "/login/*"})
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CATEGORY_LIST = "categoryList";
	
	// Param Tags
	//private static final String MODEL_TAG = "model";
	
	// Servlets
	//private static final String SERVLET_MAIN = "/Main";
	//private static final String SERVLET_LOGIN = "/login";
	
	// Pages
	private static final String JSP_LOGIN = "/Login.jspx";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    
    @Override
    public void init(ServletConfig config) throws ServletException {
    	super.init(config);
    	
    	ServletContext application = getServletContext();
    	
    	// Initialize the model and store in the application context
    	try {
			Model model = new Model();
			application.setAttribute(Tags.SESSION_MODEL, model);
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
		
		System.out.println("GET : LOGIN : URL -> " + request.getRequestURL());
		
		HttpSession session = request.getSession(true);
		session.setAttribute(Tags.IS_VISITOR, false);
		
		response.sendRedirect(this.getServletContext().getContextPath() + JSP_LOGIN);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("POST : LOGIN : URL -> " + request.getRequestURL());
		
		ServletContext application = getServletContext();
		HttpSession session = request.getSession(true);
		
		Model model = (Model) application.getAttribute(Tags.SESSION_MODEL);
		
		//Retrieve and store all categories 
		ArrayList<String> categoryList = null;
		categoryList = model.getCategories();
		request.getServletContext().setAttribute(CATEGORY_LIST, categoryList);
			
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		// TESTING MODE ONLY
		String skip = request.getParameter("skip");
		if(skip != null && skip.equals("skip")) {
			username = "antep";
			password = "password";
		}
		
		String responseMsg;
		String target;
		try {
			CustomerBean customer = model.getCustoemrLogin(username, password);
			
			if(customer != null) {
				// persist to session?
				responseMsg = "Success! Signed in as " + customer.getFname() + " " + customer.getLname();
				target = Tags.SERVLET_MAIN;
				session.setAttribute(Tags.SESSION_USER, customer);
				//session.setAttribute("username", customer.getUsername());
			} else {
				responseMsg = "no matching username and password, try again";
				target = Tags.SERVLET_LOGIN;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			responseMsg = "SQL ERROR";
			target = Tags.SERVLET_LOGIN;
		}
		
		session.setAttribute("error", responseMsg);
		response.sendRedirect(this.getServletContext().getContextPath() + target);
		//request.getRequestDispatcher(target).forward(request, response);
		
	}

}
