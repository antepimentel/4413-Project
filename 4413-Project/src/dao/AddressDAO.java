package dao;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class AddressDAO {

private DataSource ds;
	
	public AddressDAO() {
		try {
			this.ds = (DataSource) (new InitialContext()).lookup(DBSchema.DB_URL);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
