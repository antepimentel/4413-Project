package bean;

public class AddressBean {

		private int id;
		private String street;
		private String province;
		private String country;
		private String zip;
		private String phone;
		
		public AddressBean(int id, String street, String province, String country, String zip, String phone) {
			this.id = id;
			this.street = street;
			this.province = province;
			this.country = country;
			this.zip = zip;
			this.phone = phone;
		}

		public int getId() {
			return id;
		}

		public String getStreet() {
			return street;
		}

		public String getProvince() {
			return province;
		}

		public String getCountry() {
			return country;
		}

		public String getZip() {
			return zip;
		}

		public String getPhone() {
			return phone;
		}

		public void setId(int id) {
			this.id = id;
		}

		public void setStreet(String street) {
			this.street = street;
		}

		public void setProvince(String province) {
			this.province = province;
		}

		public void setCountry(String country) {
			this.country = country;
		}

		public void setZip(String zip) {
			this.zip = zip;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}
}
