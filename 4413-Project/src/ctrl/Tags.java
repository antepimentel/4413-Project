package ctrl;

/**
 * Class to hold static attributes like servlet names, session variable names...
 * 
 * @author antep
 *
 */
public class Tags {

	// Session Variables
	public static final String SESSION_USER = "user";
	public static final String SESSION_MODEL = "model";
	public static final String ERROR = "error";
	public static final String IS_VISITOR = "isVisitor";
	public static final String VISITOR_CART = "visitorCart"; // Visitor carts must be stored in the session
	public static final String VISITOR_IS_CHECKING_OUT = "visitorCheckout";
	
	// Servlet Entry Points, for redirects
	public static final String SERVLET_MAIN = "/Main";
	public static final String SERVLET_LOGIN = "/login";
	public static final String SERVLET_CART = "/ShoppingCartServlet";
	public static final String SERVLET_REGISTER = "/register";
	public static final String SERVLET_PAYMENT = "/checkout";
	public static final String SERVLET_PROFILE = "/profile";
	public static final String SERVLET_ADMIN = "/admin";
	
	// Visit event types
	public static final String VISIT_VIEW = "VIEW";
	public static final String VISIT_CART = "CART";
	public static final String VISIT_PURCHASE = "PURCHASE";
	
	// Application variables
	public static final String TOTAL_ORDERS = "totalOrders"; // Tracks total number of orders, so we can decline every third order

}
