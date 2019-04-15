package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
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

	/**
	 * Retrieves all books from Book similar to or matching the given inputs
	 * 
	 * @param title
	 * @param price
	 * @param bid
	 * @param category
	 * @return
	 * @throws SQLException
	 */
	public Map<String, BookBean> retrieve(String title, String price, String bid, String category) throws SQLException {
		
		title = formatInput(title);
		price = formatInput(price);
		bid = formatInput(bid);
		category = formatInput(category);
		
		String query = "select * from " + DBSchema.TABLE_BK + " where "
				+ DBSchema.COL_BK_TITLE + " like ? and "
				+ DBSchema.COL_BK_BID + " like ? and "
				//+ DBSchema.COL_BK_AUTHOR + " like ? and "
				+ DBSchema.COL_BK_CATEGORY + " like ?";

		Connection conn = this.ds.getConnection();
		PreparedStatement stmtObj = conn.prepareStatement(query);
		stmtObj.setString(1, title);
		//stmtObj.setString(2, price);
		stmtObj.setString(2, bid);
		stmtObj.setString(3, category);
		

		
		System.out.println("SQL: " + stmtObj.toString());
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
		
		rs.close();
		stmtObj.close();
		conn.close();

		return result;
	}

	/**
	 * Retrieves all books from table Book
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Map<String, BookBean> retrieveAllBooks() throws SQLException {

		String query = "select * from " + DBSchema.TABLE_BK;

		Connection conn = this.ds.getConnection();
		PreparedStatement stmtObj = conn.prepareStatement(query);
		
		System.out.println("SQL: " + stmtObj.toString());
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
		rs.close();
		stmtObj.close();
		conn.close();

		return result;
	}
	
	public BookBean retrieveBook(String bid) throws SQLException{
		BookBean result = null;
		String query = "select * from " + DBSchema.TABLE_BK + " where "+ DBSchema.COL_BK_BID + " = ?";
		Connection conn = this.ds.getConnection();
		PreparedStatement stmtObj = conn.prepareStatement(query);
		stmtObj.setString(1, bid);

		ResultSet rs = stmtObj.executeQuery();

		while (rs.next()) {
			bid = rs.getString(DBSchema.COL_BK_BID);
			String title = rs.getString(DBSchema.COL_BK_TITLE);
			int price = rs.getInt(DBSchema.COL_BK_PRICE);
			String category = rs.getString(DBSchema.COL_BK_CATEGORY);

			result = new BookBean(bid, title, price, category);
		}
		
		return result;
	}
	
	/**
	 * Helper method to avoid database errors
	 * 
	 * @param str
	 * @return
	 */
	private static boolean isNullOrEmpty(String str) {
		if(str != null && !str.trim().isEmpty())
            return false;
        return true;
	}
	
	/**
	 * Helper method for more robust queries.
	 * Allows even partial titles to be searched
	 * 
	 * @param str
	 * @return
	 */
	private static String formatInput(String str) {
		if(isNullOrEmpty(str)) {
			return "%";
		} else {
			return "%" + str + "%";
		}
	}
	
	
	public ArrayList<String> retrieveAllCategories() {

//		String query = "SELECT DISTINCT " + DBSchema.COL_BK_CATEGORY + "  FROM " + DBSchema.TABLE_BK 
//				+ " ORDER BY " + DBSchema.COL_BK_CATEGORY + " DESC";
//
//		Connection conn = this.ds.getConnection();
//		PreparedStatement stmtObj = conn.prepareStatement(query);
//		
//		System.out.println("SQL: " + stmtObj.toString());
//		ResultSet rs = stmtObj.executeQuery();
//
//		ArrayList<String> result = new ArrayList<String>();
//
//		while (rs.next()) {
//			String category = rs.getString(DBSchema.COL_BK_CATEGORY);
//			result.add(category);
//		}
//		rs.close();
//		stmtObj.close();
//		conn.close();
		
		String[] categories = BookBean.getCategories();
		return new ArrayList<String>(Arrays.asList(categories));
	}
 
}
