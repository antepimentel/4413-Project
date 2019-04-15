package analytics;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Application Lifecycle Listener implementation class SessionListener
 *
 */
@WebListener
public class SessionListener implements HttpSessionListener {

	private static int activeSessions;
	
	public static int getTotalActiveSessions() {
		return activeSessions;
	}
    /**
     * Default constructor. 
     */


	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent se)  { 
         // TODO Auto-generated method stub
    	activeSessions++;
    	HttpSession sess = se.getSession();
    	sess.setAttribute("startTime", (long)System.currentTimeMillis());
    
    	
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent se)  { 
         // TODO Auto-generated method stub
    	activeSessions--;
    }
	
}
