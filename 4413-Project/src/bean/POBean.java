package bean;

public class POBean {

		private String[] statuses = {"ORDERED", "PROCESSED", "DENIED"};
	
		private int id;
		private String cid;
		private String status;
		
		public POBean(int id, String lname, String fname, String status, int address) {
			this.id = id;
			this.cid = lname;
			this.status = status;
		}

		public int getId() {
			return id;
		}

		public String getCid() {
			return cid;
		}

		public String getStatus() {
			return status;
		}

		public void setId(int id) {
			this.id = id;
		}

		public void setCid(String cid) {
			this.cid = cid;
		}

		public void setStatus(String status) {
			this.status = status;
		}
}
