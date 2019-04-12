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
	
	// Servlet Entry Points, for redirects
	public static final String SERVLET_MAIN = "/Main";
	public static final String SERVLET_LOGIN = "/login";
	public static final String SERVLET_CART = "/ShoppingCartServlet";
	public static final String SERVLET_REGISTER = "/register";
	public static final String SERVLET_PAYMENT = "/checkout";
	public static final String SERVLET_PROFILE = "/profile";
	public static final String SERVLET_ADMIN = "/admin";

}
