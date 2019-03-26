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

	/**
	 * Retrieve a customer by their login information.
	 * Used when logging in.
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	public CustomerBean retrieveByLogin(String username, String password) throws SQLException {

		String query = "select * from " + DBSchema.TABLE_CUS + " where username=? and password=?";

		Connection conn = this.ds.getConnection();
		PreparedStatement stmtObj = conn.prepareStatement(query);
		stmtObj.setString(1, username);
		stmtObj.setString(2, password);

		System.out.println("SQL: " + stmtObj.toString());
		ResultSet rs = stmtObj.executeQuery();

		CustomerBean customer;

		if (rs.next() == false) {
			customer = null;
		} else {
			String user = rs.getString(DBSchema.COL_CUS_USER);
			String email = rs.getString(DBSchema.COL_CUS_EMAIL);
			String pass = rs.getString(DBSchema.COL_CUS_PASS);
			String fname = rs.getString(DBSchema.COL_CUS_FNAME);
			String lname = rs.getString(DBSchema.COL_CUS_LNAME);
			String c_type = rs.getString(DBSchema.COL_CUS_C_TYPE);

			customer = new CustomerBean(user, email, pass, fname, lname, c_type);
		}

		rs.close();
		stmtObj.close();
		conn.close();

		return customer;
	}

	/**
	 * Used to check availability of a username.
	 * 
	 * @param username
	 * @return 
	 * @throws SQLException
	 */
	public boolean retrieveByUsername(String username) throws SQLException {

		String query = "select * from " + DBSchema.TABLE_CUS + " where username=?";

		Connection conn = this.ds.getConnection();
		PreparedStatement stmtObj = conn.prepareStatement(query);
		stmtObj.setString(1, username);

		System.out.println("SQL: " + stmtObj.toString());
		ResultSet rs = stmtObj.executeQuery();

		boolean result = rs.next();

		rs.close();
		stmtObj.close();
		conn.close();

		return result;
	}
	
	public void insertCustomer(CustomerBean customer) throws SQLException {
		String query = "insert into " + DBSchema.TABLE_CUS + "("
				+ DBSchema.COL_CUS_USER + ","
				+ DBSchema.COL_CUS_EMAIL + ","
				+ DBSchema.COL_CUS_PASS + ","
				+ DBSchema.COL_CUS_FNAME + ","
				+ DBSchema.COL_CUS_LNAME + ","
				+ DBSchema.COL_CUS_C_TYPE 
				+ ") values(?,?,?,?,?,?)";

		Connection conn = this.ds.getConnection();
		PreparedStatement stmtObj = conn.prepareStatement(query);
		stmtObj.setString(1, customer.getUsername());
		stmtObj.setString(2, customer.getEmail());
		stmtObj.setString(3, customer.getPassword());
		stmtObj.setString(4, customer.getFname());
		stmtObj.setString(5, customer.getLname());
		stmtObj.setString(6, customer.getC_type());
		
		System.out.println("SQL: " + stmtObj.toString());
		stmtObj.executeUpdate();

		stmtObj.close();
		conn.close();
	}

}