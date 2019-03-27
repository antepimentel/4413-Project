package ctrl;

import java.io.IOException;
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

import bean.ShoppingCartBean;
import dao.DBSchema;
import model.Model;

/**
 * Servlet implementation class ShoppingCartServlet
 */
@WebServlet({"/ShoppingCartServlet", "/ShoppingCartServlet/*", "/MainCartLink"})
public class ShoppingCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String MODEL_TAG = "model";
       
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
		// TODO Auto-generated method stub
				
		System.out.println(request.getRequestURL());
		request.setAttribute("baseURL", request.getRequestURL());
		ServletContext application = getServletContext();
		HttpSession session = request.getSession(true);
		Model model = (Model)application.getAttribute(MODEL_TAG);
		
		String cid = (String) request.getSession().getAttribute("username");
		ArrayList<ShoppingCartBean> cartItems = model.getCompleteCart(cid);
		request.setAttribute("cartItems", cartItems);
		
		int cartTotal = model.getCartTotal(cartItems);
		request.setAttribute("cartTotal", cartTotal);
		request.getRequestDispatcher("/ShoppingCart.jspx").forward(request, response);
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext application = getServletContext();
		HttpSession session = request.getSession(true);
		Model model = (Model)application.getAttribute(MODEL_TAG);
		
		String cid = request.getParameter(DBSchema.COL_SC_CID), bid = request.getParameter(DBSchema.COL_SC_BID);

		// TODO Auto-generated method stub
		if (request.getParameter("update") != null) {
			int quantity = Integer.parseInt(request.getParameter(DBSchema.COL_SC_QUANTITY));
			model.insertOrUpdateShoppingCart(cid, bid, quantity);
			response.sendRedirect("/4413-Project/ShoppingCartServlet/username=" + cid);
			return;
		}
		
		//If add to cart is pressed 
		if (request.getParameter("addToCart") != null) {
			int quantity = Integer.parseInt(request.getParameter(DBSchema.COL_SC_QUANTITY));
			model.addToCart(cid, bid);
			response.sendRedirect("/4413-Project/ShoppingCartServlet/username=" + cid);
			return;
		}
	}

}
