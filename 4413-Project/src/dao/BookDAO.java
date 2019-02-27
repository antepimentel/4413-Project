package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import bean.BookBean;

public class BookDAO {

	private DataSource ds;

	public BookDAO() throws NamingException {
		this.ds = (DataSource) (new InitialContext()).lookup(DBSchema.DB_URL);
	}

	public Map<String, BookBean> retrieve(String title, String price, String author, String category) throws SQLException {
		
		if(isNullOrEmpty(title)) {
			title = "%%";
		} 
		
		if(isNullOrEmpty(price)) {
			price = "%%";
		} 
		
		// TODO: Add an author column to Book
//		if(isNullOrEmpty(author)) {
//			author = "%%";
//		} 
		
		if(isNullOrEmpty(category)) {
			category = "%%";
		} 
		
		String query = "select * from " + DBSchema.TABLE_BK + " where "
				+ DBSchema.COL_BK_TITLE + " like ? and "
				+ DBSchema.COL_BK_PRICE + " like ? and "
				//+ DBSchema.COL_BK_AUTHOR + " like ? and "
				+ DBSchema.COL_BK_CATEGORY + " like ?";

		Connection conn = this.ds.getConnection();
		PreparedStatement stmtObj = conn.prepareStatement(query);
		stmtObj.setString(1, title);
		stmtObj.setString(2, price);
		//stmtObj.setString(3, title);
		stmtObj.setString(3, category);
		ResultSet rs = stmtObj.executeQuery();

		Map<String, BookBean> result = new HashMap<String, BookBean>();

		while (rs.next()) {
			String currentBid = rs.getString(DBSchema.COL_BK_BID);
			String currentTitle = rs.getString(DBSchema.COL_BK_TITLE);
			int currentPrice = rs.getInt(DBSchema.COL_BK_PRICE);
			String currentCategory = rs.getString(DBSchema.COL_BK_CATEGORY);

			BookBean book = new BookBean(currentBid, currentTitle, currentPrice, currentCategory);
			result.put(currentBid, book);
		}

		return result;
	}

	public Map<String, BookBean> retrieveAllBooks() throws SQLException {

		String query = "select * from " + DBSchema.TABLE_BK;

		Connection conn = this.ds.getConnection();
		PreparedStatement stmtObj = conn.prepareStatement(query);
		ResultSet rs = stmtObj.executeQuery();

		Map<String, BookBean> result = new HashMap<String, BookBean>();

		while (rs.next()) {
			String bid = rs.getString(DBSchema.COL_BK_BID);
			String title = rs.getString(DBSchema.COL_BK_TITLE);
			int price = rs.getInt(DBSchema.COL_BK_PRICE);
			String category = rs.getString(DBSchema.COL_BK_CATEGORY);

			BookBean book = new BookBean(bid, title, price, category);
			result.put(bid, book);
		}

		return result;
	}
	
	/**
	 * Helper method to avoid database error
	 * @param str
	 * @return
	 */
	private static boolean isNullOrEmpty(String str) {
		if(str != null && !str.trim().isEmpty())
            return false;
        return true;
	}
}
