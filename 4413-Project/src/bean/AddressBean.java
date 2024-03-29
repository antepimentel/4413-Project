package bean;

public class AddressBean {

		private int id;
		private String cid;
		private String street;
		private String city;
		private String province;
		private String country;
		private String zip;
		private String phone;
		
		public AddressBean(int id, String cid, String street, String city, String province, String country, String zip, String phone) {
			this.id = id;
			this.cid = cid;
			this.street = street;
			this.city = city;
			this.province = province;
			this.country = country;
			this.zip = zip;
			this.phone = phone;
		}

		/**
		 * @return the id
		 */
		public int getId() {
			return id;
		}

		/**
		 * @param id the id to set
		 */
		public void setId(int id) {
			this.id = id;
		}

		/**
		 * @return the city
		 */
		public String getCity() {
			return city;
		}

		/**
		 * @param city the city to set
		 */
		public void setCity(String city) {
			this.city = city;
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