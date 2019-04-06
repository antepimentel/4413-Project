package rest;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "OrderList")
public class NewOrders {
	
	@XmlAttribute(name="BID")
	private static int bid;
	@XmlElement(name="orderList")
	private List<OrderBean> orders;
	
	public NewOrders(int bid, List<OrderBean> orders) {
		super();
		this.bid = bid;
		this.orders = orders;
	}
	public List<OrderBean> getOrders(){
		return this.orders;
	}
	public NewOrders() {
		
	}
}
