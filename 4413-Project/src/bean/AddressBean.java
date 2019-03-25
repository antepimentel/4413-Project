package bean;

public class AddressBean {

		private String cid;
		private String street;
		private String province;
		private String country;
		private String zip;
		private String phone;
		
		public AddressBean(String cid, String street, String province, String country, String zip, String phone) {
			this.cid = cid;
			this.street = street;
			this.province = province;
			this.country = country;
			this.zip = zip;
			this.phone = phone;
		}

		public String getCid() {
			return cid;
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

		public void setCid(String cid) {
			this.cid = cid;
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
