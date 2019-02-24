package bean;

public class POBean {

		private int id;
		private String lname;
		private String fname;
		private String status;
		private int address;
		
		public POBean(int id, String lname, String fname, String status, int address) {
			this.id = id;
			this.lname = lname;
			this.fname = fname;
			this.status = status;
			this.address = address;
		}

		public int getId() {
			return id;
		}

		public String getLname() {
			return lname;
		}

		public String getFname() {
			return fname;
		}

		public String getStatus() {
			return status;
		}

		public int getAddress() {
			return address;
		}

		public void setId(int id) {
			this.id = id;
		}

		public void setLname(String lname) {
			this.lname = lname;
		}

		public void setFname(String fname) {
			this.fname = fname;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public void setAddress(int address) {
			this.address = address;
		}
}
