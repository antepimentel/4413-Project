package dao;

import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import bean.BookBean;

public class BookDAO {

	private DataSource ds;
	
	public BookDAO() {
		try {
			this.ds = (DataSource) (new InitialContext()).lookup(DBSchema.DB_URL);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Map<String, BookBean> retrieve(String title, String price, String author, String category){
		String query = "select * from "+DBSchema.TABLE_BK;
		
		Map<String, BookBean> result = new HashMap<String, BookBean>();
		
		return result;
	}
}
