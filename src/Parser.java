import org.jsoup.nodes.*;
import org.jsoup.select.*;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.ArrayList;

/*
 * an abstract class to reduce code redundancy and for scalability:
 */
public abstract class Parser {
	String url;//the base url used to form destination url
	private DatabaseOperation dbOp = new DatabaseOperation();

	/*
	 * get the document of the given url
	 */
	public Document getDocument(String url) {
		if (url == null || url.length() == 0) {
			System.out.println("Empty URL!");
			return null;
		}
		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			System.out.println("Internet Connection Error! "
					+ "Please check your internet connectivity.");
			System.exit(0);
		}
		return doc;
	}

	public void openDBConnection() {
		if (dbOp != null) {
			dbOp.openConnection();
		}
	}
	
	public void addRestaurant(String id, String name) {
		dbOp.addRestaurant(id, name);
	}

	public void addInfo(String rest_id, String phone, String address) {
		dbOp.addInfo(rest_id, phone, address);
	}

	public void addMenuItem(String rest_id, String item) {
		dbOp.addMenuItem(rest_id, item);
	}
	
	/*
	 * This method may vary for different websites, so make it abstract
	 */
	public abstract void getInfo(String rest_id);
	
	/*
	 * This method may vary for different websites, so make it abstract
	 */
	public abstract void getMenu(String rest_id);
}
