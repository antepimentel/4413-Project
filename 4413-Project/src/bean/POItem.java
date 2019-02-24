package bean;

public class POItem {

		private int id;
		private String bid;
		private int price;
		
		public POItem(int id, String bid, int price) {
			this.id = id;
			this.bid = bid;
			this.price = price;
		}

		public int getId() {
			return id;
		}

		public String getBid() {
			return bid;
		}

		public int getPrice() {
			return price;
		}

		public void setId(int id) {
			this.id = id;
		}

		public void setBid(String bid) {
			this.bid = bid;
		}

		public void setPrice(int price) {
			this.price = price;
		}
		
}
