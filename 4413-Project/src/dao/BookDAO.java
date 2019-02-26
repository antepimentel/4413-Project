package dao;

import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import bean.BookBean;

public class BookDAO {

	// DB Definition
	private static final String TABLE_NAME = "Book";
	private static final String COL_BID = "bid";
	private static final String COL_TITLE = "title";
	private static final String COL_PRICE = "price";
	private static final String COL_CATEGORY = "category";
	
	private DataSource ds;
	
	public BookDAO() {
		try {
			this.ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Map<String, BookBean> retrieve(String title, String price, String author, String category){
		String query = "select * from "+TABLE_NAME;
		
		Map<String, BookBean> result = new HashMap<String, BookBean>();
		
		return result;
	}
}
