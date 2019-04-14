package analytics;

import java.util.List;

import javax.xml.bind.annotation.*;

@XmlRootElement(name="bookStatReport")
public class ListWrapper {

	@XmlAttribute
	private String month;
	@XmlElement
	private List<BookStatBean> book;
	
	public ListWrapper() {
		
	}

	/**
	 * @param month
	 * @param list
	 */
	public ListWrapper(String month, List<BookStatBean> list) {
		this.month = month;
		this.book = list;
	}


}
