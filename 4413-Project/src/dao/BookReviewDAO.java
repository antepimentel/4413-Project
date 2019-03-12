package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import bean.BookBean;
import bean.BookReviewBean;

public class BookReviewDAO {

	private DataSource ds;
	
	public BookReviewDAO() throws NamingException {
		this.ds = (DataSource) (new InitialContext()).lookup(DBSchema.DB_URL);
	}
	
	public void addReview(String bid, int cid, int rating, String text) throws SQLException {
		String query = "insert into " + DBSchema.TABLE_REVIEW + "("
				+ DBSchema.COL_REVIEW_BID + ","
				+ DBSchema.COL_REVIEW_CID + ","
				+ DBSchema.COL_REVIEW_RATING + ","
				+ DBSchema.COL_REVIEW_TEXT 
				+ ") values(?,?,?,?)";

		Connection conn = this.ds.getConnection();
		PreparedStatement stmtObj = conn.prepareStatement(query);
		stmtObj.setString(1, bid);
		stmtObj.setInt(2, cid);
		stmtObj.setInt(3, rating);
		stmtObj.setString(4, text);
		
		System.out.println("SQL: " + stmtObj.toString());
		stmtObj.executeUpdate();

		stmtObj.close();
		conn.close();
	}
	
	/**
	 * Retrieves a list of book reviews for a given book
	 * 
	 * @param bid
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<BookReviewBean> retrieveByBook(String bid) throws SQLException {
		
		String query = "select * from " + DBSchema.TABLE_REVIEW + " where "
				+ DBSchema.COL_REVIEW_BID + " = ?";

		Connection conn = this.ds.getConnection();
		PreparedStatement stmtObj = conn.prepareStatement(query);
		stmtObj.setString(1, bid);
		
		System.out.println("SQL: " + stmtObj.toString());
		ResultSet rs = stmtObj.executeQuery();

		ArrayList<BookReviewBean> result = new ArrayList<BookReviewBean>();

		while (rs.next()) {
			String bookID = rs.getString(DBSchema.COL_REVIEW_BID);
			int cusID = rs.getInt(DBSchema.COL_REVIEW_CID);
			int rating = rs.getInt(DBSchema.COL_REVIEW_RATING);
			String review = rs.getString(DBSchema.COL_REVIEW_TEXT);

			BookReviewBean reviewBean = new BookReviewBean(bookID, cusID, rating, review);
			result.add(reviewBean);
		}
		
		rs.close();
		stmtObj.close();
		conn.close();
		return result;
	}
}
