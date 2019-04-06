package rest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.io.StringWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.transform.stream.StreamResult;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import dao.DBSchema;
import rest.OrderBean;

@Path("/OrderByPart")
public class OrderListDAO {
	
	private DataSource ds; 
	
	public OrderListDAO() throws NamingException {
		this.ds = (DataSource) (new InitialContext()).lookup(DBSchema.DB_URL);
	} 
	
	
	public ArrayList<OrderBean> getOrdersByPartNumber(int bid) throws SQLException{
		String query = "select * from " + DBSchema.COL_CUS_EMAIL +  DBSchema.COL_BK_TITLE + DBSchema.COL_BK_AUTHOR + DBSchema.COL_BK_PRICE + DBSchema.COL_SC_QUANTITY + DBSchema.COL_PO_ID + 
				DBSchema.COL_PO_STATUS + DBSchema.COL_ADD_STREET + DBSchema.COL_ADD_COUNTRY;
	
	
	Connection con = this.ds.getConnection();
	PreparedStatement ps = con.prepareStatement(query);
	ResultSet rs = ps.executeQuery();
	
	ArrayList<OrderBean> order = new ArrayList<OrderBean>();
		while(rs.next()) {
			String email = rs.getString("email");
			//int bid = rs.getInt("bid");
			String title = rs.getString("title");
			String author = rs.getString("author");
			int quantity = rs.getInt("quantity");
			float price = rs.getFloat("price");
			String status = rs.getString("status");
			String street = rs.getString("street");
			String city = rs.getString("city");
			String province = rs.getString("province");
			String country = rs.getString("country");
			int pid = rs.getInt("pid");
			OrderBean bean = new OrderBean(title, author, bid, price, quantity, email, street, country, province, city, status, pid);
			order.add(bean);
		}
		rs.close();
		ps.close();
		con.close();
		return order;
	}
	
	
	/*@GET
	@Path("/product/{productID}")
	@Produces("app/xml")
	public String OrdersByPartNumber(@PathParam("productID") String id) throws Exception{
		int idInt = Integer.parseInt(id);
		
	}
	*/
	 
}
