import org.jsoup.nodes.*;
import org.jsoup.select.*;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.*;

/*
 * Subclass of Parse which is suited for parsing data from www.dinebombaygarden.com.
 */
public class FirstParser extends Parser {
	public FirstParser(String url) {
		this.url = url;
	}

	public void getInfo(String rest_id) {
		Document doc = getDocument(url + "contacts.php");
		Elements elementList = doc.select("p.spl");

		if (elementList.size() > 0) {
			for (int i = 0; i < elementList.size(); i++) {
				String rawString = elementList.get(i).text();
				String phone = rawString.substring(0,
						rawString.indexOf("(") - 1);
				String address = rawString.substring(rawString.indexOf("("),
						rawString.length());
				addInfo(rest_id, phone, address);

			}
		}
	}

	public void getMenu(String rest_id) {
		Document doc = getDocument(url + "menus.html");
		Elements elementList = null;
		// store different category links for iteration:
		ArrayList<String> categoryLinks = new ArrayList<String>();
		if (doc != null) {
			elementList = doc.select("div#content-center div.element h3");
		}
		for (Element ele : elementList) {
			String tmp = ele.select("a").get(0).attr("href");
			categoryLinks.add(tmp);
		}
		for (int i = 0; i < categoryLinks.size(); i++) {
			Document menuDoc = getDocument(url + categoryLinks.get(i));
			getMenuHelper1(rest_id, menuDoc);
		}
	}

	private void getMenuHelper1(String rest_id, Document doc) {
		Elements elementList = doc.select("div.left-data h3");
		if (elementList.size() > 0) {
			for (int i = 0; i < elementList.size(); i++) {
				Element ele = elementList.get(i);
				String name = "";
				if (ele.text().indexOf(".") > 0) {
					name = ele.text().substring(0, ele.text().indexOf("."));
				} else {
					name = ele.text();
				}
				addMenuItem(rest_id, name);
			}
		}
	}

	public static void main(String[] args) {
		FirstParser test = new FirstParser("http://www.dinebombaygarden.com/");
		test.openDBConnection();
		test.getMenu("1");
		test.getInfo("1");
	}
}
