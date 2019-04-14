package analytics;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBException;

import org.xml.sax.SAXException;

import bean.CustomerBean;
import ctrl.Tags;
import model.Model;

/**
 * Servlet implementation class Analytics
 */
@WebServlet({"/admin", "/admin/bookstats"})
public class Admin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// JSP Files
	private static final String JSP_ADMIN = "/Admin.jspx";
	private static final String JSP_REPORT = "/DownloadReport.jspx";
	
	// Request Variables
	private static final String REQ_FILENAME = "xmlFilename";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Admin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("GET : ADMIN : URL -> " + request.getRequestURL());
		
		ServletContext application = getServletContext();
		HttpSession session = request.getSession(true);
		Model model = (Model)application.getAttribute(Tags.SESSION_MODEL);
		CustomerBean user = (CustomerBean) session.getAttribute(Tags.SESSION_USER);
		
		if(user.getC_type().equals("ADMIN")) {
			request.getRequestDispatcher(JSP_ADMIN).forward(request, response);
		} else {
			response.sendRedirect(this.getServletContext().getContextPath() + Tags.SERVLET_MAIN);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("POST : ADMIN : URL -> " + request.getRequestURL());
		
		ServletContext application = getServletContext();
		HttpSession session = request.getSession(true);
		Model model = (Model)application.getAttribute(Tags.SESSION_MODEL);
		
		String pathname = application.getRealPath("/export");
		String filename = request.getSession().getId()+".xml";
		
		String target = "";
		String msg = "";
		try {
			model.exportBookStats("Jan", pathname, filename);
			request.setAttribute(REQ_FILENAME, filename);
			target = JSP_REPORT;
		} catch (SQLException | JAXBException | SAXException e) {
			target = JSP_ADMIN;
			e.printStackTrace();
		}
		request.getRequestDispatcher(target).forward(request, response);
	}

}
