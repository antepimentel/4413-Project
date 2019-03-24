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
import bean.CustomerBean;

public class CustomerDAO {

	private DataSource ds;

	public CustomerDAO() throws NamingException {
		this.ds = (DataSource) (new InitialContext()).lookup(DBSchema.DB_URL);
	}
	
	public CustomerBean retrieveByLogin(String username, String password) throws SQLException {
		
		String query = "select * from " + DBSchema.TABLE_CUS + " where username=? and password=?";

		Connection conn = this.ds.getConnection();
		PreparedStatement stmtObj = conn.prepareStatement(query);
		stmtObj.setString(1, username);
		stmtObj.setString(2, password);
		
		System.out.println("SQL: " + stmtObj.toString());
		ResultSet rs = stmtObj.executeQuery();

		CustomerBean customer;
		
		if(rs.next() == false) {
			customer = null;
		} else {
			String user = rs.getString(DBSchema.COL_CUS_USER);
			String pass = rs.getString(DBSchema.COL_CUS_PASS);
			String fname = rs.getString(DBSchema.COL_CUS_FNAME);
			String lname = rs.getString(DBSchema.COL_CUS_LNAME);
			int address = rs.getInt(DBSchema.COL_CUS_ADD);
			
			customer = new CustomerBean(user, pass, fname, lname, address);
		}
		
		rs.close();
		stmtObj.close();
		conn.close();

		return customer;
	}

}
