package ctrl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.BookBean;
import model.Model;

/**
 * Servlet implementation class CategoryListServlet
 */
@WebServlet({"/CategoryListServlet", "/Category/", "/Category/*"})
public class CategoryListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final String MODEL = "model", CATEGORY_NAME = "categoryName";
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CategoryListServlet() {
        super();
    }
    
    @Override
    public void init(ServletConfig config) throws ServletException {
    	super.init(config);
    	
    	ServletContext application = getServletContext();
    	
    	// Initialize the model and store in the application context
    	try {
			Model model = new Model();
			application.setAttribute(MODEL, model);
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
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		Model model = (Model) getServletContext().getAttribute(MODEL);
		String categoryName = request.getParameter(CATEGORY_NAME);
		try {
			response.getWriter().append(getHTMLResponse(model.getBooks("", "", "", categoryName)));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private String getHTMLResponse(Map<String, BookBean> books) {
		
		String result = "Displaying " + books.size() + " items.";
		
		if(books.size() > 0) {
			result = result
					+"<TABLE border='1'>"
					+"<TR>"
					+"<TD>Title</TD>"
					+"<TD>Price</TD>"
					+"<TD>Category</TD>"
					+"</TR>";
			
			for(BookBean bean: books.values()) {
				String addToCartButton = "<A href='http://localhost:8080/4413-Project/Main/?addTo=notNull&categoryName="+ bean.getCategory() + "&bookTitle=" + bean.getTitle() + "'>Add to cart button</A>";
				result = result + "<TR>"
						+"<TD><A href='http://localhost:8080/4413-Project/Main/?bid="+ bean.getBid() +"&bookTitle=" +bean.getTitle()+"'> " + bean.getTitle() + "</A>" + addToCartButton + "</TD>"
						+"<TD>"+bean.getPrice()+"</TD>"
						+"<TD><A href='http://localhost:8080/4413-Project/Main/?categoryName="+ bean.getCategory() +"'>" +bean.getCategory()+"</A></TD>"
						+"</TR>";
			}
			result = result + "</TABLE>";
		}		
		return result;
	}

}
