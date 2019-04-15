package analytics;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import bean.POBean;

/**
 * Application Lifecycle Listener implementation class OrderListener
 *
 */
@WebListener
public class OrderListener implements HttpSessionAttributeListener {
int count =0;
int counti = 0;

List<POBean> poList = new ArrayList<POBean>();
    /**
     * Default constructor. 
     */
    public OrderListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see HttpSessionAttributeListener#attributeAdded(HttpSessionBindingEvent)
     */
    public void attributeAdded(HttpSessionBindingEvent arg0)  { 
         // TODO Auto-generated method stub
    	
    	handleEvent(arg0);
    	
    	
    }

	/**
     * @see HttpSessionAttributeListener#attributeRemoved(HttpSessionBindingEvent)
     */
    public void attributeRemoved(HttpSessionBindingEvent arg0)  { 
         // TODO Auto-generated method stub
    	
    	handleEvent(arg0);

    }

	/**
     * @see HttpSessionAttributeListener#attributeReplaced(HttpSessionBindingEvent)
     */
    public void attributeReplaced(HttpSessionBindingEvent arg0)  { 
         // TODO Auto-generated method stub
    	
    	handleEvent(arg0);

    }
    
    void handleEvent(HttpSessionBindingEvent arg0) {
    	
    	
    	if (arg0.getName().equals("totalOrders")) {//Get name of the attribute that was changed
	    	/*new order was placed*/
    		double p = (double)arg0.getSession().getAttribute("principal");
			//arg0.getSession().setAttribute("maxPrincipal", p);
    		
    		
    		if (count ==0) {

        		arg0.getSession().getServletContext().setAttribute("maxPrincipal", p);
        		count ++;


    		}// First principal entry, set max
        	
    	
    		else {
    			if (p> (double)arg0.getSession().getServletContext().getAttribute("maxPrincipal")) {
    		
    				arg0.getSession().getServletContext().setAttribute("maxPrincipal", p);


    			}
    		
    		}// Do comparison and then set max accordingly
    	
    	}
    	
    	
	if (arg0.getName().equals("interest")) {//Get name of the attribute that was changed
	    	
    		double p = (double)arg0.getSession().getAttribute("interest");
			//arg0.getSession().setAttribute("maxPrincipal", p);

    		
    		if (counti ==0) {

        		arg0.getSession().getServletContext().setAttribute("maxInterest", p);
        		counti ++;


    		}// First principal entry, set max
        	
    	
    		else {
    			if (p> (double)arg0.getSession().getServletContext().getAttribute("maxInterest")) {
    		
    				arg0.getSession().getServletContext().setAttribute("maxInterest", p);


    			}
    		
    		}// Do comparison and then set max accordingly
    	
    	}
    
    	
    }
    
	
}