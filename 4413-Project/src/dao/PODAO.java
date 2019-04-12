package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import bean.POBean;

public class PODAO {

private DataSource ds;
	
	public PODAO() throws NamingException {
		this.ds = (DataSource) (new InitialContext()).lookup(DBSchema.DB_URL);
	}
	
	/**
	 * Returns a list of blank POBeans
	 * 
	 * @param username
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<POBean> getPOsForCustomer(String username) throws SQLException {
		String query = "select * from " + DBSchema.TABLE_PO 
				+ " where " + DBSchema.COL_ADD_CID + " =?";
		
		Connection conn = this.ds.getConnection();
		PreparedStatement stmtObj = conn.prepareStatement(query);
		stmtObj.setString(1, username);

		System.out.println("SQL: " + stmtObj.toString());
		ResultSet rs = stmtObj.executeQuery();
		
		ArrayList<POBean> pos = new ArrayList<POBean>();
		
		while(rs.next()) {
			int id = rs.getInt(DBSchema.COL_PO_ID);
			String cid = rs.getString(DBSchema.COL_PO_CID);
			String status = rs.getString(DBSchema.COL_PO_STATUS);
			
			pos.add(new POBean(id, cid, status));
		}
		
		stmtObj.close();
		conn.close();
		
		return pos;
	}
	
	/**
	 * Adds a new PO to the database
	 * 
	 * @param po
	 * @throws SQLException
	 */
	public long createPO(POBean po) throws SQLException {
		String query = "insert into " + DBSchema.TABLE_PO + " ("
				+ DBSchema.COL_PO_CID + ","
				+ DBSchema.COL_PO_STATUS
				+ ") values(?,?)";
		
		Connection conn = this.ds.getConnection();
		PreparedStatement stmtObj = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		stmtObj.setString(1, po.getCid());
		stmtObj.setString(2, po.getStatus());
		
		System.out.println("SQL: " + stmtObj.toString());
		stmtObj.executeUpdate();
		
		ResultSet generatedKeys = stmtObj.getGeneratedKeys();
		generatedKeys.next();
		
		long key = generatedKeys.getLong(1);
		
		stmtObj.close();
		conn.close();
		
		System.out.println("SQL: returning key of inserted record: " + key);
		return key;
	}
	
}
