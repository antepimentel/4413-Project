package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import bean.VisitEventBean;

public class VisitEventDAO {
	
	private SimpleDateFormat df = new SimpleDateFormat("ddMMYYYY");
	private DataSource ds;
	
	public VisitEventDAO() throws NamingException {
		this.ds = (DataSource) (new InitialContext()).lookup(DBSchema.DB_URL);
	}
	
	public ArrayList<VisitEventBean> retrieve() throws SQLException, ParseException{
		
		String query = "select * from "+DBSchema.TABLE_VE;
		Connection conn = ds.getConnection();
		
		PreparedStatement stmtObj = conn.prepareStatement(query); 
		System.out.println("SQL: " + stmtObj.toString());
		ResultSet rs = stmtObj.executeQuery();
		
		ArrayList<VisitEventBean> result = new ArrayList<VisitEventBean>();
		
		while(rs.next()) {
			String day = rs.getString(DBSchema.COL_VE_DAY);
			String bid = rs.getString(DBSchema.COL_VE_BID);
			String type = rs.getString(DBSchema.COL_VE_TYPE);
			
			Date date = df.parse(day);
			
			VisitEventBean bean = new VisitEventBean(date, bid, type);
			
			result.add(bean);
		}
		
		rs.close();
		stmtObj.close();
		conn.close();
		
		return result;
	}
	
	public void insert(VisitEventBean bean) throws SQLException {
		
		String query = "insert into "+DBSchema.TABLE_VE+" values(?,?,?)";
		Connection conn = ds.getConnection();
		PreparedStatement stmtObj = conn.prepareStatement(query);
		
		stmtObj.setString(1, df.format(bean.getDay()));
		stmtObj.setString(2, bean.getBid());
		stmtObj.setString(3, bean.getEventType());
		
		System.out.println("SQL: " + stmtObj.toString());
		stmtObj.executeUpdate();
		
		stmtObj.close();
		conn.close();
	}
}
