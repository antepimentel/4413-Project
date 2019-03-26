package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import bean.BookReviewBean;
import bean.ShoppingCartBean;


public class ShoppingCartDAO {
	private DataSource ds;

	public ShoppingCartDAO() throws NamingException {
		this.ds = (DataSource) (new InitialContext()).lookup(DBSchema.DB_URL);
	}
	
	public void addToCart(String cid, String bid, int quantity) throws SQLException {
		PreparedStatement statement;
		Connection conn = this.ds.getConnection();

		if (checkShoppingCartContains_BID_CID_Pair(cid, bid)) {
			String query = "UPDATE " + DBSchema.TABLE_SC + " SET "
					+ DBSchema.COL_SC_QUANTITY + " = ? WHERE ["
					+ DBSchema.COL_SC_CID + " = ? AND " 
					+ DBSchema.COL_SC_BID + " = ?]" 
					;
			statement = conn.prepareStatement(query);
			statement.setInt(1, quantity);
			statement.setString(2, cid);
			statement.setString(3, bid);

		} else {
			String query = "INSERT INTO " + DBSchema.TABLE_SC + " (username, bid, quantity) VALUES "
					+ "(?" +", ?" + ", ?)"
				;
			statement = conn.prepareStatement(query);
			statement.setString(1, cid);
			statement.setString(2, bid);
			statement.setInt(3, quantity);
		}



		System.out.println("SQL: " + statement.toString());
		ResultSet rs = statement.executeQuery();

		ArrayList<ShoppingCartBean> result = new ArrayList<ShoppingCartBean>();

		while (rs.next()) {
			String bookID = rs.getString(DBSchema.COL_REVIEW_BID);
			String cusID = rs.getString(DBSchema.COL_REVIEW_CID);
			int rating = rs.getInt(DBSchema.COL_REVIEW_RATING);
			String review = rs.getString(DBSchema.COL_REVIEW_TEXT);

			ShoppingCartBean reviewBean = new ShoppingCartBean(cid, bid, quantity);
			result.add(reviewBean);
		}
		rs.close();
		statement.close();
		conn.close();
		return;
	}
	
	public boolean checkShoppingCartContains_BID_CID_Pair(String cid, String bid) throws SQLException{
		
		String query = "SELECT * FROM " + DBSchema.TABLE_SC + " WHERE "
				+ DBSchema.COL_SC_CID + " = ? AND " 
				+ DBSchema.COL_SC_BID + " = ?";
		
		Connection conn = this.ds.getConnection();
		PreparedStatement statement = conn.prepareStatement(query);
		statement.setString(1,  cid);
		statement.setString(2,  bid);
		
		System.out.println("SQL check for item with query :" + statement.toString());
		
		ResultSet rs = statement.executeQuery();

		while(rs.next()) {
			return true;
		}
		return false;
	}
	
	public ArrayList<ShoppingCartBean> getShoppingCartContents(String cid) throws SQLException{

		ArrayList<ShoppingCartBean> result = new ArrayList<ShoppingCartBean>();
		
		PreparedStatement statement;
		Connection conn = this.ds.getConnection();
		
		String query = "SELECT * FROM  " + DBSchema.TABLE_SC + " WHERE "
				+ DBSchema.COL_SC_CID + " = ?";
		statement = conn.prepareStatement(query);
		statement.setString(1, cid);
		
		System.out.println("SQL: " + statement.toString());
		ResultSet rs = statement.executeQuery();

		while (rs.next()) {
			String bid = rs.getString(DBSchema.COL_SC_BID);
			int quantity = rs.getInt(DBSchema.COL_SC_QUANTITY);
			ShoppingCartBean cartBean = new ShoppingCartBean(cid, bid, quantity);
			result.add(cartBean);
		}
		
		rs.close();
		statement.close();
		conn.close();
		return result;
	}
	
}
