package dao;


import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import java.io.File;
import java.io.StringWriter;
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


public class OrderREST {
	
	private DataSource ds;
	public OrderREST() throws NamingException {
		this.ds = (DataSource) (new InitialContext()).lookup(DBSchema.DB_URL);
	}
	public Map<String, BookBean> newQuery(String query) throws SQLException{
		
		Map<String, BookBean> rv = new HashMap<String, BookBean>();
		
		Connection con;
		try {
			con = this.ds.getConnection();
			PreparedStatement ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			int count = 0;
			
			while(rs.next()) {
				int id = rs.getInt("id");
				String bid = rs.getString("bid");
				String title = rs.getString("TITLE");
				int price = rs.getInt("PRICE");
				String category = rs.getString("CATEGORY");
				//String author = rs.getString("AUTHOR");
				//String picture = rs.getString("PICTURE");
				BookBean bean = new BookBean (bid, title, price, category);
				
				String stringCounter = Integer.toString(count);
				rv.put(Integer.toString(id), bean);
				count++;
			}
			rs.close();
			ps.close();
			con.close();
			
			return rv;
		}
		catch(SQLException e) {
			System.out.println("Error in BookDAO");
			return null;
		}
	}
}


