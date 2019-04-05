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
import model.Model;

/**
 * Servlet implementation class PaymentServlet
 */
@WebServlet({"/checkout", "/checkout/*"})
public class PaymentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String JSP_CONF_ADDR = "/ConfirmAddress.jspx";
	private static final String JSP_CONF_PAY = "/ConfirmPayment.jspx";
	private static final String JSP_REVIEW = "/ReviewOrder.jspx";
	
	private static final String CHECKOUT_TAG = "checkout";
	private static final String CONF_ADDR_TAG = CHECKOUT_TAG + "/confaddress";
	private static final String CONF_PAY_TAG = CHECKOUT_TAG + "/confpayment";
	private static final String REVIEW_ORDER_TAG = CHECKOUT_TAG + "/review";
	
	private static final String MAINPAGE_TAG = "/Main";
      
	private static final String MODEL_TAG = "model";
	private static final String ERROR = "error";
	private static final String USERNAME = "username";
	private static final String ADDR_LIST = "addressList";
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PaymentServlet() {
        super();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("GET : PAY : URL -> " + request.getRequestURL());
		
		ServletContext application = getServletContext();
		HttpSession session = request.getSession(true);
		Model model = (Model)application.getAttribute(MODEL_TAG);
		
		String reqString = request.getRequestURI();
		
		if(reqString.endsWith(CHECKOUT_TAG)) {
			// This is a forware from another servlet
			System.out.println("BEEP");
			request.getRequestDispatcher(CONF_ADDR_TAG).forward(request, response);
		
		} else if(reqString.endsWith(CONF_ADDR_TAG)) { 
			
			String target = "";
			String msg = "";
			try {
				String username = (String)session.getAttribute(USERNAME);
				ArrayList<AddressBean> addressList = model.getAddressListForCustomer(username);
				
				request.setAttribute(ADDR_LIST, addressList);
				
				target = JSP_CONF_ADDR;
				msg = "none";
			} catch (SQLException e) {
				target = JSP_CONF_ADDR;
				msg = "Error confirming address";
			}
			request.setAttribute(ERROR, msg);
			request.getRequestDispatcher(target).forward(request, response);
		
		} else if(reqString.endsWith(CONF_PAY_TAG)) {
			request.getRequestDispatcher(JSP_CONF_PAY).forward(request, response);
		
		} else if(reqString.endsWith(REVIEW_ORDER_TAG)) {
			request.getRequestDispatcher(JSP_REVIEW).forward(request, response);
		
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("POST : PAY : URL -> " + request.getRequestURL());

		ServletContext application = getServletContext();
		HttpSession session = request.getSession(true);
		Model model = (Model)application.getAttribute(MODEL_TAG);
		
		String reqString = request.getRequestURI();
		
		if(reqString.endsWith(CONF_ADDR_TAG)) { 
			// User has confirmed the address
			System.out.println("User has confirmed the address");
			
			String target = JSP_CONF_PAY;
			String msg = "";
//			try {
//				String username = (String)session.getAttribute(USERNAME);
//				model.getAddressListForCustomer(username);
//				
//				target = JSP_CONF_PAY;
//				msg = "none";
//			} catch (SQLException e) {
//				target = JSP_CONF_ADDR;
//				msg = "Error confirming address";
//			}
			request.setAttribute(ERROR, msg);
			request.getRequestDispatcher(target).forward(request, response);
		
		} else if(reqString.endsWith(CONF_PAY_TAG)) {
			// User has confirmed payment 
			System.out.println("User has confirmed the payment method");
			request.getRequestDispatcher(JSP_REVIEW).forward(request, response);
		
		} else if(reqString.endsWith(REVIEW_ORDER_TAG)) {
			
			System.out.println("User has confirmed the order, going back to main page");
			
			request.setAttribute("error", "ORDER CONFIRMED");
			response.sendRedirect("/4413-Project/Main");
		
		}
	}

}
