package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import bean.AddressBean;

public class AddressDAO {

private DataSource ds;
	
	public AddressDAO() throws NamingException {
		this.ds = (DataSource) (new InitialContext()).lookup(DBSchema.DB_URL);
	}
	
	public void insertAddress(AddressBean address) throws SQLException {
		String query = "insert into " + DBSchema.TABLE_ADD + "("
				+ DBSchema.COL_ADD_CID + ","
				+ DBSchema.COL_ADD_STREET + ","
				+ DBSchema.COL_ADD_COUNTRY + ","
				+ DBSchema.COL_ADD_PROV + ","
				+ DBSchema.COL_ADD_ZIP + ","
				+ DBSchema.COL_ADD_PHONE 
				+ ") values(?,?,?,?,?,?)";

		Connection conn = this.ds.getConnection();
		PreparedStatement stmtObj = conn.prepareStatement(query);
		stmtObj.setString(1, address.getCid());
		stmtObj.setString(2, address.getStreet());
		stmtObj.setString(3, address.getCountry());
		stmtObj.setString(4, address.getProvince());
		stmtObj.setString(5, address.getZip());
		stmtObj.setString(6, address.getPhone());
		
		System.out.println("SQL: " + stmtObj.toString());
		stmtObj.executeUpdate();

		stmtObj.close();
		conn.close();
	}
}
