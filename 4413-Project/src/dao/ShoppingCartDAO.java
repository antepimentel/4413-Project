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
	
	public void addToCart(String cid, String bid, int quantity, int price) throws SQLException {
		PreparedStatement statement;
		Connection conn = this.ds.getConnection();
		if (checkShoppingCartContains_BID_CID_Pair(cid, bid) && quantity == 0) {
			String query = "DELETE FROM "+ DBSchema.TABLE_SC + " WHERE "
					+ DBSchema.COL_SC_CID + " = ? AND " 
					+ DBSchema.COL_SC_BID + " = ?";
			statement = conn.prepareStatement(query);
			statement.setString(1, cid);
			statement.setString(2, bid);
		}
		else if (checkShoppingCartContains_BID_CID_Pair(cid, bid)) {
			String query = "UPDATE " + DBSchema.TABLE_SC + " SET "
					+ DBSchema.COL_SC_QUANTITY + " = ? WHERE "
					+ DBSchema.COL_SC_CID + " = ? AND " 
					+ DBSchema.COL_SC_BID + " = ?" 
					;
			statement = conn.prepareStatement(query);
			statement.setInt(1, quantity);
			statement.setString(2, cid);
			statement.setString(3, bid);

		} else {
			String query = "INSERT INTO " + DBSchema.TABLE_SC + " ("
					+ DBSchema.COL_SC_CID +","
					+ DBSchema.COL_SC_BID +","
					+ DBSchema.COL_SC_QUANTITY +","
					+ DBSchema.COL_SC_PRICE
					+ ") VALUES (?, ?, ?, ?)";
			statement = conn.prepareStatement(query);
			statement.setString(1, cid);
			statement.setString(2, bid);
			statement.setInt(3, quantity);
			statement.setInt(4, price);
		}

		System.out.println("SQL: " + statement.toString());
		statement.execute();

		ArrayList<ShoppingCartBean> result = new ArrayList<ShoppingCartBean>();

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
	
	public int checkBIDQuantity(String cid, String bid) throws SQLException{
		
		if(this.checkShoppingCartContains_BID_CID_Pair(cid, bid) == false) {
			return 0;
		}
		
		String query = "SELECT * FROM " + DBSchema.TABLE_SC + " WHERE "
				+ DBSchema.COL_SC_CID + " = ? AND " 
				+ DBSchema.COL_SC_BID + " = ?";
		
		Connection conn = this.ds.getConnection();
		PreparedStatement statement = conn.prepareStatement(query);
		statement.setString(1,  cid);
		statement.setString(2,  bid);

		System.out.println("SQL check for item with query :" + statement.toString());
		
		ResultSet rs = statement.executeQuery();
		rs.next();
		return rs.getInt(DBSchema.COL_SC_QUANTITY);
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
