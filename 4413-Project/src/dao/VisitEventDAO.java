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

import bean.VisitEventBean;

public class VisitEventDAO {
	
	private DataSource ds;
	
	public VisitEventDAO() {
		try {
			this.ds = (DataSource) (new InitialContext()).lookup(DBSchema.DB_URL);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ArrayList<VisitEventBean> retrieve() throws SQLException{
		
		String query = "select * from "+DBSchema.TABLE_VE;
		Connection conn = ds.getConnection();
		
		PreparedStatement stmtObj = conn.prepareStatement(query); 
		ResultSet rs = stmtObj.executeQuery();
		
		ArrayList<VisitEventBean> result = new ArrayList<VisitEventBean>();
		
		while(rs.next()) {
			String day = rs.getString(DBSchema.COL_VE_DAY);
			String bid = rs.getString(DBSchema.COL_VE_BID);
			String type = rs.getString(DBSchema.COL_VE_TYPE);
			
			VisitEventBean bean = new VisitEventBean(day, bid, type);
			
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
