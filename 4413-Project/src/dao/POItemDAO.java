package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import bean.POItem;

public class POItemDAO {

private DataSource ds;
	
	public POItemDAO() throws NamingException {
		this.ds = (DataSource) (new InitialContext()).lookup(DBSchema.DB_URL);
	}
	
	public void insertPOItem(POItem poItem) throws SQLException {
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
}
