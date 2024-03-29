package ctrl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.AddressBean;
import bean.POBean;
import bean.POItemBean;
import bean.CustomerBean;
import bean.PaymentInfoBean;
import bean.ShoppingCartBean;
import bean.VisitEventBean;
import model.Model;

/**
 * Servlet implementation class PaymentServlet
 * 
 * This class is to walk the user through confirming an order.
 * The steps are: Confirm Address -> Confirm Method of Payment -> Review and Confirm
 * 
 * GET requests moves the user to the next step
 * POST requests indicate the user has returned info to be handled
 */
@WebServlet({"/checkout", "/checkout/*"})
public class PaymentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// JSP files related to this servlet
	private static final String JSP_CONF_ADDR = "/ConfirmAddress.jspx";
	private static final String JSP_CONF_PAY = "/ConfirmPayment.jspx";
	private static final String JSP_REVIEW = "/ReviewOrder.jspx";
	
	// URL paths to walk the user through confirming an order
	private static final String CHECKOUT_TAG = "checkout";
	private static final String CONF_ADDR_TAG = CHECKOUT_TAG + "/confaddress";
	private static final String CONF_PAY_TAG = CHECKOUT_TAG + "/confpayment";
	private static final String REVIEW_ORDER_TAG = CHECKOUT_TAG + "/review";
	
	// JSP field ids
	private static final String ADDR_LIST = "addressList";
	private static final String ADDR_RADIO = "address-radio";
	
	private static final String PAY_CARDNUM = "cardnumber";
	private static final String PAY_CARDHOLDER = "cardholder";
	private static final String PAY_EXP_DATE = "expdate";
	private static final String PAY_SEC_CODE = "securitycode";
	
	// Saved user selections
	private static final String SAVED_ADDR = "savedAddress";
	private static final String SAVED_PAY_INFO = "savedPaymentInfo";
	
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
		Model model = (Model)application.getAttribute(Tags.SESSION_MODEL);

		String reqString = request.getRequestURI();
		
		if(session.getAttribute(Tags.SESSION_USER) == null) {
			session.setAttribute(Tags.ERROR, "An account is required to login, please register");
			session.setAttribute(Tags.VISITOR_IS_CHECKING_OUT, true);
			response.sendRedirect(this.getServletContext().getContextPath() + Tags.SERVLET_REGISTER);
			
		} else if(reqString.endsWith(CHECKOUT_TAG)) {
			// This is a forward from another servlet
			System.out.println("BEEP");
			request.getRequestDispatcher(CONF_ADDR_TAG).forward(request, response);
		
		} else if(reqString.endsWith(CONF_ADDR_TAG)) { 
			// User wants to confirm address
			String target = "";
			String msg = "";
			try {
				String username = ((CustomerBean)session.getAttribute(Tags.SESSION_USER)).getUsername();
				ArrayList<AddressBean> addressList = model.getAddressListForCustomer(username);
				
				request.setAttribute(ADDR_LIST, addressList);
				
				target = JSP_CONF_ADDR;
				msg = "none";
			} catch (SQLException e) {
				target = JSP_CONF_ADDR;
				msg = "Error confirming address";
			}
			request.setAttribute(Tags.ERROR, msg);
			request.getRequestDispatcher(target).forward(request, response);
		
		} else if(reqString.endsWith(CONF_PAY_TAG)) {
			// User wants to confirm payment
			request.getRequestDispatcher(JSP_CONF_PAY).forward(request, response);
		
		} else if(reqString.endsWith(REVIEW_ORDER_TAG)) {
			// User wants to review the order
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
		Model model = (Model)application.getAttribute(Tags.SESSION_MODEL);
		
		String cid = ((CustomerBean)session.getAttribute(Tags.SESSION_USER)).getUsername();
		String reqString = request.getRequestURI();
		
		if(reqString.endsWith(CONF_ADDR_TAG)) { 
			// User has confirmed the address
			System.out.println("User has confirmed the address");
			
			String target = JSP_CONF_PAY;
			String msg = "";
			try {
				String username = ((CustomerBean)session.getAttribute(Tags.SESSION_USER)).getUsername();
				model.getAddressListForCustomer(username);
				
				// Get the selected Address ID and save it
				String selectedAddr = (String) request.getParameter(ADDR_RADIO);
				System.out.println("User has selected addres with ID = " + selectedAddr);
				
				AddressBean bean = model.getAddressByID(selectedAddr);
				session.setAttribute(SAVED_ADDR, bean);
				
				target = JSP_CONF_PAY;
				msg = "none";
			} catch (SQLException e) {
				target = JSP_CONF_ADDR;
				msg = "Error confirming address";
			}
			request.setAttribute(Tags.ERROR, msg);
			request.getRequestDispatcher(target).forward(request, response);
		
		} else if(reqString.endsWith(CONF_PAY_TAG)) {
			// User has confirmed payment 
			System.out.println("User has confirmed the payment method");

			String target = JSP_CONF_PAY;
			String msg = "";
			try {
				String username = ((CustomerBean)session.getAttribute(Tags.SESSION_USER)).getUsername();
				model.getAddressListForCustomer(username);
				
				// Get the field info and save it to session
				String cardnumber = (String) request.getParameter(PAY_CARDNUM);
				String cardholder = (String) request.getParameter(PAY_CARDHOLDER);
				String expdate = (String) request.getParameter(PAY_EXP_DATE);
				String secCode = (String) request.getParameter(PAY_SEC_CODE);
				
				PaymentInfoBean bean = new PaymentInfoBean(cardnumber, cardholder, expdate, secCode);
				session.setAttribute(SAVED_PAY_INFO, bean);
				
				// Also need to grab and save the cart items for the final confirmation
				ArrayList<ShoppingCartBean> cartItems = model.getCompleteCart(cid);
				request.setAttribute("cartItems", cartItems);
				
				int cartTotal = model.getCartTotal(cartItems);
				request.setAttribute("cartTotal", cartTotal);
				
				System.out.println(cartTotal);
				target = JSP_REVIEW;
				msg = "none";
			} catch (SQLException e) {
				target = JSP_CONF_PAY;
				msg = "Error confirming address";
			}
			request.setAttribute(Tags.ERROR, msg);
			request.getRequestDispatcher(target).forward(request, response);
			//request.getRequestDispatcher(JSP_REVIEW).forward(request, response);
		
		} else if(reqString.endsWith(REVIEW_ORDER_TAG)) {
			// User has reviewed and confirmed the order
			System.out.println("User has confirmed the order, going back to main page");
			
			String target = JSP_CONF_PAY;
			String msg = "";
			try {
		
				// Need to check number of orders made, decline every third order
				int numOrders = (int)application.getAttribute(Tags.TOTAL_ORDERS);
				
				String status = "";
				if(numOrders%3 == 0) {
					status = "DENIED";
					target = JSP_REVIEW;
					msg = "Credit Card Authorization Failed";
				} else {
					status = "ORDERED";
					target = JSP_REVIEW;
					msg = "Order Confirmed Successfully";
				}
				// Create a PO
				POBean po = new POBean(-1, cid, status);
				long poID = model.createPO(po);
				
				// Add items to PO
				ArrayList<ShoppingCartBean> cartItems = model.getCompleteCart(cid);
				
				for(ShoppingCartBean cartItem: cartItems) {
					POItemBean poItem = convertCartItemToPOItem(poID, cartItem);
					model.addItemToPO(poItem);
					
					// Log the event
					Date date = new Date();
					VisitEventBean event = new VisitEventBean(date, poItem.getBid(), Tags.VISIT_PURCHASE);
					model.addVisitEvent(event);
				}
				
				application.setAttribute(Tags.TOTAL_ORDERS, numOrders+1);
				application.setAttribute("po", po);
				
				session.setAttribute(Tags.ERROR, msg);
				response.sendRedirect(this.getServletContext().getContextPath() + Tags.SERVLET_MAIN);
			} catch (SQLException e) {
				e.printStackTrace();
				target = JSP_REVIEW;
				msg = "Error placing your order";
				request.getRequestDispatcher(target).forward(request, response);
			}
		}
	}
	
	private POItemBean convertCartItemToPOItem(long id, ShoppingCartBean cartItem) {
		return new POItemBean(id, cartItem.getBid(), cartItem.getPrice(), cartItem.getQuantity());
	}

}
