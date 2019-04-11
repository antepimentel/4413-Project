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
import bean.ShoppingCartBean;
import dao.DBSchema;
import model.Model;

/**
 * Servlet implementation class ShoppingCartServlet
 */
@WebServlet({"/ShoppingCartServlet", "/ShoppingCartServlet/*", "/MainCartLink"})
public class ShoppingCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//private static final String MODEL_TAG = "model";
	//private static final String ERROR = "error";
	private static final String JSP_CART = "/ShoppingCart.jspx";
	private static final String JSP_MAIN = "/MainPage.jspx";
	//private static final String JSP_CHECKOUT = "/Checkout.jspx";
	
	private static final String CHECKOUT_TAG = "checkout";
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShoppingCartServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("GET : CART : URL -> " + request.getRequestURL());
		
		request.setAttribute("baseURL", request.getRequestURL());
		ServletContext application = getServletContext();
		HttpSession session = request.getSession(true);
		Model model = (Model)application.getAttribute(Tags.SESSION_MODEL);
		
		String cid = ((CustomerBean)session.getAttribute(Tags.SESSION_USER)).getUsername();
		
		String target = "";
		String responseMsg = "";
		
		if(request.getRequestURI().endsWith(CHECKOUT_TAG)) {
			target = Tags.SERVLET_PAYMENT;
			responseMsg = "none";
			response.sendRedirect("/4413-Project" + target);
			return;
		} else {
			try {
				ArrayList<ShoppingCartBean> cartItems = model.getCompleteCart(cid);
				request.setAttribute("cartItems", cartItems);
				
				int cartTotal = model.getCartTotal(cartItems);
				request.setAttribute("cartTotal", cartTotal);
				
				target = JSP_CART;
				responseMsg = "none";
				request.setAttribute(Tags.ERROR, responseMsg);
			
			} catch (SQLException e) {
				e.printStackTrace();
				target = JSP_MAIN;
				responseMsg = "there was an error handling your request";
				request.setAttribute(Tags.ERROR, responseMsg);
			}
			
			request.getRequestDispatcher(target).forward(request, response);
			return;
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("POST : CART : URL -> " + request.getRequestURL());
		
		ServletContext application = getServletContext();
		HttpSession session = request.getSession(true);
		Model model = (Model)application.getAttribute(Tags.SESSION_MODEL);
		
		String cid = request.getParameter(DBSchema.COL_SC_CID);
		String bid = request.getParameter(DBSchema.COL_SC_BID);
		
		int price = 1; // Placeholder

		String responseMsg = "";
		
		if (request.getParameter("update") != null) {
			
			try {
				int quantity = Integer.parseInt(request.getParameter(DBSchema.COL_SC_QUANTITY));
				model.insertOrUpdateShoppingCart(cid, bid, quantity, price);
				response.sendRedirect("/4413-Project/ShoppingCartServlet/username=" + cid);
			} catch (SQLException e) {
				e.printStackTrace();
				responseMsg = "there was an error handling your request";
				request.setAttribute(Tags.ERROR, responseMsg);
				request.getRequestDispatcher(JSP_MAIN).forward(request, response);
			}
			
			return;
		}
		
		//If add to cart is pressed 
		if (request.getParameter("addToCart") != null) {
			
			try {
				int quantity = Integer.parseInt(request.getParameter(DBSchema.COL_SC_QUANTITY));
				model.addToCart(cid, bid, price);
				response.sendRedirect("/4413-Project/ShoppingCartServlet/username=" + cid);
				
			} catch (SQLException e) {
				e.printStackTrace();
				responseMsg = "there was an error handling your request";
				request.setAttribute(Tags.ERROR, responseMsg);
				request.getRequestDispatcher(JSP_MAIN).forward(request, response);
			}
			
			return;
		}
	}

}
