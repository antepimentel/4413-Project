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
import bean.POBean;
import model.Model;

/**
 * Servlet implementation class ProfileServlet
 */
@WebServlet({"/profile", "/profile/*"})
public class ProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// JSP Files
	private static final String JSP_PROFILE = "/Profile.jspx";
	private static final String JSP_NEWADDRESS = "/NewAddress.jspx";
	
	// JSP variable tags
	private static final String TAG_ADDRESS_LIST = "addressList";
	private static final String TAG_PO_LIST = "poList";
	
	// URL tags
	private static final String URL_NEWADDRESS = "newaddress";
	
	// Form names
	private static final String ADDRESS = "address";
	private static final String CITY = "city";
	private static final String COUNTRY = "country";
	private static final String PROV = "province";
	private static final String POSTAL = "postal";
	private static final String PHONE = "phone";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProfileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("GET : PROFILE : URL -> " + request.getRequestURL());
		
		ServletContext application = getServletContext();
		HttpSession session = request.getSession(true);
		Model model = (Model)application.getAttribute(Tags.SESSION_MODEL);
		CustomerBean user = (CustomerBean) session.getAttribute(Tags.SESSION_USER);
		
		String target = "";
		String msg = "";
		
		if(request.getRequestURI().endsWith(URL_NEWADDRESS)) {
			target = JSP_NEWADDRESS;
			msg = "none";
			
		} else {
			try {
				// Get user addresses and POs
				ArrayList<AddressBean> addressList = model.getAddressListForCustomer(user.getUsername());
				ArrayList<POBean> pos = model.getPOsForCustomer(user.getUsername());
				
				// Save to request for page to use
				request.setAttribute(TAG_ADDRESS_LIST, addressList);
				request.setAttribute(TAG_PO_LIST, pos);
				
				// Set targets
				target = JSP_PROFILE;
				msg = "none";
				
			} catch (SQLException e) {
				target = JSP_PROFILE;
				msg = "Database error";
			}
		}
		
		// Go to set target
		request.setAttribute(Tags.ERROR, msg);
		request.getRequestDispatcher(target).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("POST : PROFILE : URL -> " + request.getRequestURL());

		ServletContext application = getServletContext();
		HttpSession session = request.getSession(true);
		Model model = (Model)application.getAttribute(Tags.SESSION_MODEL);
		CustomerBean user = (CustomerBean) session.getAttribute(Tags.SESSION_USER);
		
		String street = request.getParameter(ADDRESS);
		String city = request.getParameter(CITY);
		String country = request.getParameter(COUNTRY);
		String province = request.getParameter(PROV);
		String postal = request.getParameter(POSTAL);
		String phone = request.getParameter(PHONE);
		
		// Enter -1 as the ID value, this won't be used anyway, the DB will make its own ID value upon insertion
		AddressBean address = new AddressBean(-1, user.getUsername(), street, city, province, country, postal, phone); 
		
		String responseMsg = "";
		String target = "";
		
		// Pass info to model 
		try {
			model.addAddress(address);
			target = JSP_PROFILE;
			responseMsg = "Address Added";
			
			request.setAttribute(Tags.ERROR, responseMsg);
			response.sendRedirect(this.getServletContext().getContextPath() + Tags.SERVLET_PROFILE);
		} catch (SQLException e) {
			e.printStackTrace();
			target = JSP_NEWADDRESS;
			responseMsg = "Database error";
			pushInfoToSession(request, address);
			request.setAttribute(Tags.ERROR, responseMsg);
			request.getRequestDispatcher(target).forward(request, response);
		} 

		
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
	private static void pushInfoToSession(HttpServletRequest request, AddressBean address) {
		
		HttpSession session = request.getSession(true);
		
		session.setAttribute(ADDRESS, address.getStreet());
		session.setAttribute(CITY, address.getCity());
		session.setAttribute(COUNTRY, address.getCountry());
		session.setAttribute(PROV, address.getProvince());
		session.setAttribute(POSTAL, address.getZip());
		session.setAttribute(PHONE, address.getPhone());
	}

}
