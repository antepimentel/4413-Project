package ctrl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.AddressBean;
import bean.CustomerBean;
import bean.ShoppingCartBean;
import model.CustomException;
import model.Model;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String JSP_REGISTER = "/Register.jspx";
	private static final String JSP_MAIN = "/MainPage.jspx";
	
	//private static final String MODEL_TAG = "model";
	//private static final String ERROR = "error";

	// Form Names
	private static final String USERNAME = "username";
	private static final String EMAIL = "email";
	private static final String PASSWORD = "password";
	private static final String CONF_PASSWORD = "conf-password";
	private static final String FNAME = "fname";
	private static final String LNAME = "lname";
	private static final String ADDRESS = "address";
	private static final String CITY = "city";
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
		System.out.println("GET : REG : URL -> " + request.getRequestURL());
		response.sendRedirect(this.getServletContext().getContextPath() + JSP_REGISTER);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("POST : REG : URL -> " + request.getRequestURL());
		
		ServletContext application = getServletContext();
		HttpSession session = request.getSession(true);
		Model model = (Model)application.getAttribute(Tags.SESSION_MODEL);

		// Get all the form values
		String username = request.getParameter(USERNAME);
		String email = request.getParameter(EMAIL);
		String password = request.getParameter(PASSWORD);
		String conf_password = request.getParameter(CONF_PASSWORD);
		String fname = request.getParameter(FNAME);
		String lname = request.getParameter(LNAME);
		String street = request.getParameter(ADDRESS);
		String city = request.getParameter(CITY);
		String country = request.getParameter(COUNTRY);
		String province = request.getParameter(PROV);
		String postal = request.getParameter(POSTAL);
		String phone = request.getParameter(PHONE);
		
		CustomerBean customer = new CustomerBean(username, email, password, fname, lname, "CUSTOMER"); 
		
		// Enter -1 as the ID value, this won't be used anyway, the DB will make its own ID value upon insertion
		AddressBean address = new AddressBean(-1, username, street, city, province, country, postal, phone); 

		String responseMsg = "";
		String target = "";
		// Pass info to model 
		try {
			model.registerCustomer(customer, conf_password, address);
			target = JSP_MAIN;
			responseMsg = "Success! Signed in as " + customer.getFname() + " " + customer.getLname();
			session.setAttribute(Tags.SESSION_USER, customer);
			
			if(session.getAttribute(Tags.VISITOR_IS_CHECKING_OUT) != null) {
				session.setAttribute(Tags.VISITOR_IS_CHECKING_OUT, null);
				session.setAttribute(Tags.IS_VISITOR, false);
				
				// Saved the cart stored in the session to the DB
				ArrayList<ShoppingCartBean> cart = (ArrayList<ShoppingCartBean>)session.getAttribute(Tags.VISITOR_CART);
				model.convertVisitorCart(customer, cart);
				response.sendRedirect(this.getServletContext().getContextPath() + Tags.SERVLET_PAYMENT);
				return;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			target = JSP_REGISTER;
			responseMsg = "Database error";
			pushInfoToSession(request, customer, address);
		} catch (CustomException e) {
			//e.printStackTrace();
			target = JSP_REGISTER;
			responseMsg = e.getMsg();
			pushInfoToSession(request, customer, address);
		}

		request.setAttribute(Tags.ERROR, responseMsg);
		request.getRequestDispatcher(target).forward(request, response);
	}
	
	/**
	 * Saves entered fields into the session
	 * Saves time for users if there is an error
	 * in one of the fields
	 * 
	 * @param request
	 * @param customer
	 * @param address
	 */
	private static void pushInfoToSession(HttpServletRequest request, CustomerBean customer, AddressBean address) {
		
		HttpSession session = request.getSession(true);
		
		session.setAttribute(USERNAME, customer.getUsername());
		session.setAttribute(EMAIL, customer.getEmail());
		session.setAttribute(FNAME, customer.getFname());
		session.setAttribute(LNAME, customer.getLname());
		session.setAttribute(ADDRESS, address.getStreet());
		session.setAttribute(CITY, address.getCity());
		session.setAttribute(COUNTRY, address.getCountry());
		session.setAttribute(PROV, address.getProvince());
		session.setAttribute(POSTAL, address.getZip());
		session.setAttribute(PHONE, address.getPhone());
	}

}