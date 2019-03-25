package ctrl;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String JSP_REGISTER = "/Register.jspx";
	
	private static final String USERNAME = "username";
	private static final String EMAIL = "email";
	private static final String PASSWORD = "password";
	private static final String CONF_PASSWORD = "conf-password";
	private static final String FNAME = "fname";
	private static final String LNAME = "lname";
	private static final String ADDRESS = "address";
	private static final String COUNTRY = "country";
	private static final String PROV = "province";
	private static final String POSTAL = "postal";
	private static final String PHONE = "phone";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() { 
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Recieved GET request: URL -> " + request.getRequestURL());
		response.sendRedirect(this.getServletContext().getContextPath() + JSP_REGISTER);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Recieved POST request: URL -> " + request.getRequestURL());
		
		// Get all the form values
		String username = request.getParameter(USERNAME);
		String email = request.getParameter(EMAIL);
		String password = request.getParameter(PASSWORD);
		String conf_password = request.getParameter(CONF_PASSWORD);
		String fname = request.getParameter(FNAME);
		String lname = request.getParameter(LNAME);
		String address = request.getParameter(ADDRESS);
		String country = request.getParameter(COUNTRY);
		String province = request.getParameter(PROV);
		String postal = request.getParameter(POSTAL);
		String phone = request.getParameter(PHONE);
		
		// Pass info to model 
		// TODO
		
		// Go to Search page
		// TODO
	}

}
