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

import bean.VisitedEventBean;

public class VisitedEventDAO {
	
	// DB table definition
	private static final String TABLE_NAME = "VisitedEvent";
	private static final String COL_DAY = "day";
	private static final String COL_BID = "bid";
	private static final String COL_TYPE = "eventtype";

	private DataSource ds;
	
	public VisitedEventDAO() {
		try {
			this.ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Map<String, VisitedEventBean> retrieve() throws SQLException{
		
		String query = "select * from "+TABLE_NAME;
		Connection conn = ds.getConnection();
		
		PreparedStatement stmtObj = conn.prepareStatement(query);
		ResultSet rs = stmtObj.executeQuery();
		
		Map<String, VisitedEventBean> result = new HashMap<String, VisitedEventBean>();
		
		while(rs.next()) {
			String day = rs.getString(COL_DAY);
			String bid = rs.getString(COL_BID);
			String type = rs.getString(COL_TYPE);
			
			VisitedEventBean bean = new VisitedEventBean(day, bid, type);
			
			result.put(bid, bean);
		}
		
		rs.close();
		stmtObj.close();
		conn.close();
		
		return result;
	}
	
	public void insert(VisitedEventBean bean) throws SQLException {
		
		String query = "insert into "+TABLE_NAME+" values(?,?,?)";
		Connection conn = ds.getConnection();
		PreparedStatement stmtObj = conn.prepareStatement(query);
		
		stmtObj.setString(1, bean.getDay());
		stmtObj.setString(2, bean.getBid());
		stmtObj.setString(3, bean.getEventType());
		
		stmtObj.executeUpdate();
		conn.commit();
		conn.rollback(); 
		
		stmtObj.close();
		conn.close();
	}
}
