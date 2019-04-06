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

import bean.AddressBean;
import bean.BookBean;

public class AddressDAO {

private DataSource ds;
	
	public AddressDAO() throws NamingException {
		this.ds = (DataSource) (new InitialContext()).lookup(DBSchema.DB_URL);
	}
	
	public void insertAddress(AddressBean address) throws SQLException {
		String query = "insert into " + DBSchema.TABLE_ADD + "("
				+ DBSchema.COL_ADD_CID + ","
				+ DBSchema.COL_ADD_STREET + ","
				+ DBSchema.COL_ADD_CITY + ","
				+ DBSchema.COL_ADD_COUNTRY + ","
				+ DBSchema.COL_ADD_PROV + ","
				+ DBSchema.COL_ADD_ZIP + ","
				+ DBSchema.COL_ADD_PHONE 
				+ ") values(?,?,?,?,?,?,?)";

		Connection conn = this.ds.getConnection();
		PreparedStatement stmtObj = conn.prepareStatement(query);
		stmtObj.setString(1, address.getCid());
		stmtObj.setString(2, address.getStreet());
		stmtObj.setString(3, address.getCity());
		stmtObj.setString(4, address.getCountry());
		stmtObj.setString(5, address.getProvince());
		stmtObj.setString(6, address.getZip());
		stmtObj.setString(7, address.getPhone());
		
		System.out.println("SQL: " + stmtObj.toString());
		stmtObj.executeUpdate();

		stmtObj.close();
		conn.close();
	}
	
	public ArrayList<AddressBean> getAddressListForCustomer(String username) throws SQLException{
		
		String query = "select * from " + DBSchema.TABLE_ADD + " where "
				+ DBSchema.COL_ADD_CID + " like ?";

		Connection conn = this.ds.getConnection();
		PreparedStatement stmtObj = conn.prepareStatement(query);
		stmtObj.setString(1, username);
	
		System.out.println("SQL: " + stmtObj.toString());
		ResultSet rs = stmtObj.executeQuery();

		ArrayList<AddressBean> result = new ArrayList<AddressBean>();

		while (rs.next()) {
			AddressBean bean = getBeanFromResult(rs);
			result.add(bean);
		}
		
		rs.close();
		stmtObj.close();
		conn.close();

		return result;
	}
	
	public AddressBean getAddressByID(int id) throws SQLException {
		String query = "select * from " + DBSchema.TABLE_ADD + " where "
				+ DBSchema.COL_ADD_ID + "=?";

		Connection conn = this.ds.getConnection();
		PreparedStatement stmtObj = conn.prepareStatement(query);
		stmtObj.setInt(1, id);
	
		System.out.println("SQL: " + stmtObj.toString());
		ResultSet rs = stmtObj.executeQuery();

		rs.next();
		AddressBean bean = getBeanFromResult(rs);

		rs.close();
		stmtObj.close();
		conn.close();

		return bean;
	}
	
	/**
	 * Helper method to convert SQL result to bean
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private static AddressBean getBeanFromResult(ResultSet rs) throws SQLException {
		int id = rs.getInt(DBSchema.COL_ADD_ID);
		String cid = rs.getString(DBSchema.COL_ADD_CID);
		String street = rs.getString(DBSchema.COL_ADD_STREET);
		String city = rs.getString(DBSchema.COL_ADD_CITY);
		String prov = rs.getString(DBSchema.COL_ADD_PROV);
		String country = rs.getString(DBSchema.COL_ADD_COUNTRY);
		String zip = rs.getString(DBSchema.COL_ADD_ZIP);
		String phone = rs.getString(DBSchema.COL_ADD_PHONE);
		
		return new AddressBean(id, cid, street, city, prov, country, zip, phone);
	}
}
