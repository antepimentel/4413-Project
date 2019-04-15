package analytics;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionBindingEvent;

import bean.POBean;

/**
 * Application Lifecycle Listener implementation class POListener
 *
 */

@WebListener
public class POListener implements ServletContextAttributeListener {
	List<POBean> poList = new ArrayList<POBean>();
	private static int totalOrders =0;
	private static int totalBooks=0;


    /**
     * Default constructor. 
     */
    public POListener() {
        // TODO Auto-generated constructor stub
    
    }

	/**
     * @see ServletContextAttributeListener#attributeAdded(ServletContextAttributeEvent)
     */
    public void attributeAdded(ServletContextAttributeEvent arg0)  { 
         // TODO Auto-generated method stub
    	handleEvent(arg0);
    }

	/**
     * @see ServletContextAttributeListener#attributeRemoved(ServletContextAttributeEvent)
     */
    public void attributeRemoved(ServletContextAttributeEvent arg0)  { 
         // TODO Auto-generated method stub
    	handleEvent(arg0);

    }

	/**
     * @see ServletContextAttributeListener#attributeReplaced(ServletContextAttributeEvent)
     */
    public void attributeReplaced(ServletContextAttributeEvent arg0)  { 
         // TODO Auto-generated method stub
    	handleEvent(arg0);

    }
    
    void handleEvent(ServletContextAttributeEvent arg0) {
    	
    if (arg0.getName().equals("totalOrders")) {//Get name of the attribute that was changed
    	totalOrders++;
    	
    }
    
    if (arg0.getName().equals("po")) {
    	POBean po = (POBean)arg0.getServletContext().getAttribute("po");
    	
    	poList.add(po);
    	totalBooks = totalBooks + poList.size();
    	
    	
    
    	
    	
    }
    
    	
    }
}
    
