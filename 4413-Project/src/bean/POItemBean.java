package bean;

public class POItemBean {

		private long id;
		private String bid;
		private int price;
		private int quantity;
		/**
		 * @param id
		 * @param bid
		 * @param price
		 * @param quantity
		 */
		public POItemBean(long id, String bid, int price, int quantity) {
			this.id = id;
			this.bid = bid;
			this.price = price;
			this.quantity = quantity;
		}
		/**
		 * @return the id
		 */
		public long getId() {
			return id;
		}
		/**
		 * @return the bid
		 */
		public String getBid() {
			return bid;
		}
		/**
		 * @return the price
		 */
		public int getPrice() {
			return price;
		}
		/**
		 * @return the quantity
		 */
		public int getQuantity() {
			return quantity;
		}
		/**
		 * @param id the id to set
		 */
		public void setId(int id) {
			this.id = id;
		}
		/**
		 * @param bid the bid to set
		 */
		public void setBid(String bid) {
			this.bid = bid;
		}
		/**
		 * @param price the price to set
		 */
		public void setPrice(int price) {
			this.price = price;
		}
		/**
		 * @param quantity the quantity to set
		 */
		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}
		
		
		
}
