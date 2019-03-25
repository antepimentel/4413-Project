package dao;

/**
 * 
 * This is just a static class to hold the database definition
 * Allows for consistent queries and flexibility if the schema changes
 * 
 * @author antep
 *
 */
public class DBSchema {

	public static final String DB_URL = "java:/comp/env/jdbc/project_DB";
	
	// Address
	public static final String TABLE_ADD = "Address";
	public static final String COL_ADD_CID = "cid";
	public static final String COL_ADD_STREET = "street";
	public static final String COL_ADD_PROV = "province";
	public static final String COL_ADD_COUNTRY = "country";
	public static final String COL_ADD_ZIP = "zip";
	public static final String COL_ADD_PHONE = "phone";
	
	// Customer
	public static final String TABLE_CUS = "Customer";
	public static final String COL_CUS_USER = "username";
	public static final String COL_CUS_EMAIL = "email";
	public static final String COL_CUS_PASS = "password";
	public static final String COL_CUS_FNAME = "fname";
	public static final String COL_CUS_LNAME = "lname";
	public static final String COL_CUS_C_TYPE = "c_type";
	
	// Book
	public static final String TABLE_BK = "Book";
	public static final String COL_BK_BID = "bid";
	public static final String COL_BK_TITLE = "title";
	public static final String COL_BK_PRICE = "price";
	public static final String COL_BK_CATEGORY = "category";
	
	// BookReivew
	public static final String TABLE_REVIEW = "BookReview";
	public static final String COL_REVIEW_BID = "bid";
	public static final String COL_REVIEW_CID = "cid";
	public static final String COL_REVIEW_RATING = "rating";
	public static final String COL_REVIEW_TEXT = "review";
	
	// PO
	public static final String TABLE_PO = "PO";
	public static final String COL_PO_ID = "id";
	public static final String COL_PO_CID = "cid";
	public static final String COL_PO_STATUS = "status";
	
	// POItem
	public static final String TABLE_POI = "POItem";
	public static final String COL_POI_ID = "id";
	public static final String COL_POI_BID = "bid";
	public static final String COL_POI_PRICE = "price";
	
	// VisitEvent
	public static final String TABLE_VE = "visitevent";
	public static final String COL_VE_DAY = "day";
	public static final String COL_VE_BID = "bid";
	public static final String COL_VE_TYPE = "eventtype";
	
}

