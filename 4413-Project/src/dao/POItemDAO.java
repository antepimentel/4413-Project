package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import analytics.BookStatBean;
import bean.POBean;
import bean.POItemBean;

public class POItemDAO {

private DataSource ds;
	
	public POItemDAO() throws NamingException {
		this.ds = (DataSource) (new InitialContext()).lookup(DBSchema.DB_URL);
	}
	
	/**
	 * Adds a POItem to a PO in the DB
	 * 
	 * @param poItem
	 * @throws SQLException
	 */
	public void insertPOItem(POItemBean poItem) throws SQLException {
		String query = "insert into " + DBSchema.TABLE_POI + "("
				+ DBSchema.COL_POI_ID + ","
				+ DBSchema.COL_POI_BID + ","
				+ DBSchema.COL_POI_PRICE + ","
				+ DBSchema.COL_POI_QUANTITY 
				+ ") values(?,?,?,?)";

		Connection conn = this.ds.getConnection();
		PreparedStatement stmtObj = conn.prepareStatement(query);
		stmtObj.setLong(1, poItem.getId());
		stmtObj.setString(2, poItem.getBid());
		stmtObj.setInt(3, poItem.getPrice());
		stmtObj.setInt(4, poItem.getQuantity());
		
		System.out.println("SQL: " + stmtObj.toString());
		stmtObj.executeUpdate();

		stmtObj.close();
		conn.close();
	}
	
	/**
	 * Returns a list of completed POBeans, with added lists of items
	 * 
	 * @param pos
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<POBean> completePOBeans(ArrayList<POBean> pos) throws SQLException{
		
		Connection conn = this.ds.getConnection();
		
		// For each PO
		for(POBean po: pos) {
			String query = "select * from " + DBSchema.TABLE_POI
					+ " where " +DBSchema.COL_POI_ID + " =?";
			
			PreparedStatement stmtObj = conn.prepareStatement(query);
			stmtObj.setInt(1, po.getId());
			
			System.out.println("SQL: " + stmtObj.toString());
			ResultSet rs = stmtObj.executeQuery();
			
			ArrayList<POItemBean> items = new ArrayList<POItemBean>();
			
			// Loop over rows, create POItem beans, add to PO
			while(rs.next()) {
				int id = rs.getInt(DBSchema.COL_POI_ID);
				String bid = rs.getString(DBSchema.COL_POI_BID);
				int price = rs.getInt(DBSchema.COL_POI_PRICE);
				int quantity = rs.getInt(DBSchema.COL_POI_QUANTITY);
				
				items.add(new POItemBean(id, bid, price, quantity));
			}
			
			po.setItems(items);
			stmtObj.close();
		}
		conn.close();
		return pos;
	}
	
	public ArrayList<BookStatBean> getBookStats() throws SQLException{
		
		/* Sample query
 			select POItem.bid, title, SUM(quantity) as total from POItem 
				join Book on POItem.bid=Book.bid 
				join PO on PO.id=POItem.id
				where PO.status <> 'DENIED'
				group by POItem.bid;
		 */
		
		String COL_TOTAL = "total";
		String query = "select " + DBSchema.TABLE_POI +"."+DBSchema.COL_POI_BID + ", " + DBSchema.COL_BK_TITLE + ", " + "SUM("+DBSchema.COL_POI_QUANTITY+") as "+COL_TOTAL + " from " +DBSchema.TABLE_POI 
						+" join " + DBSchema.TABLE_BK + " on " + DBSchema.TABLE_POI +"."+DBSchema.COL_POI_BID + "=" + DBSchema.TABLE_BK +"."+DBSchema.COL_BK_BID 
						+" join " + DBSchema.TABLE_PO + " on " + DBSchema.TABLE_PO +"."+DBSchema.COL_POI_ID + "=" + DBSchema.TABLE_POI +"."+DBSchema.COL_POI_ID
						+ " where " + DBSchema.COL_PO_STATUS + " <> \'DENIED\'"
						+ " group by " + DBSchema.COL_BK_BID;

		Connection conn = this.ds.getConnection();
		PreparedStatement stmtObj = conn.prepareStatement(query);
		
		System.out.println("SQL: " + stmtObj.toString());
		ResultSet rs = stmtObj.executeQuery();

		ArrayList<BookStatBean> result = new ArrayList<BookStatBean>();
		while(rs.next()) {
			String bid = rs.getString(DBSchema.COL_BK_BID);
			String title = rs.getString(DBSchema.COL_BK_TITLE);
			int amount = rs.getInt(COL_TOTAL);
			
			result.add(new BookStatBean(title, bid, amount));
		}
		
		rs.close();
		stmtObj.close();
		conn.close();
		
		return result;
	}
}
