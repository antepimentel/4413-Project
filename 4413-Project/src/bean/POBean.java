package bean;

import java.util.ArrayList;

public class POBean {

		private String[] statuses = {"ORDERED", "PROCESSED", "DENIED"};
	
		private int id;
		private String cid;
		private String status;
		private ArrayList<POItemBean> items;
		/**
		 * @param statuses
		 * @param id
		 * @param cid
		 * @param status
		 */
		public POBean(int id, String cid, String status) {
			this.id = id;
			this.cid = cid;
			this.status = status;
		}
		/**
		 * @return the items
		 */
		public ArrayList<POItemBean> getItems() {
			return items;
		}
		/**
		 * @param items the items to set
		 */
		public void setItems(ArrayList<POItemBean> items) {
			this.items = items;
		}
		/**
		 * @return the id
		 */
		public int getId() {
			return id;
		}
		/**
		 * @return the cid
		 */
		public String getCid() {
			return cid;
		}
		/**
		 * @return the status
		 */
		public String getStatus() {
			return status;
		}
		/**
		 * @param id the id to set
		 */
		public void setId(int id) {
			this.id = id;
		}
		/**
		 * @param cid the cid to set
		 */
		public void setCid(String cid) {
			this.cid = cid;
		}
		/**
		 * @param status the status to set
		 */
		public void setStatus(String status) {
			this.status = status;
		}
	
		
}
