import org.jsoup.nodes.*;
import org.jsoup.select.*;
import org.jsoup.Jsoup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.*;
import java.util.regex.*;

/*
 * This is the second customized parser that can crawl data from 
 * www.allspicerestaurent.com
 */
public class SecondParser extends Parser {
	public SecondParser(String url) {
		this.url = url;
	}

	public void getInfo(String rest_id) {
		Document doc = getDocument(url + "contact.html");
		Elements elementList = doc.select("div.footer div.address");

		if (elementList.size() > 0) {
			for (int i = 0; i < elementList.size(); i++) {
				String rawString = elementList.get(i).text();
				String phone = rawString.substring(0,
						rawString.indexOf("|") - 1);
				String address = rawString.substring(rawString.indexOf("("),
						rawString.length());
				addInfo(rest_id, phone, address);
			}
		}
	}

	public void getMenu(String rest_id) {
		// content is generated by AJAX calls, extra library to handle
		// javascript would be better, but here I use regular expression:
		String response = null;
		try {
			// Create a URL for the desired page
			URL url = new URL(
					"https://widget.locu.com/widget2/locu.widget.v2.0.js?id=be24678d58686e86ef05&medium=web");

			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			// Read all the text returned by the server
			BufferedReader in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String str;
			while ((str = in.readLine()) != null) {
				// str is one line of text; readLine() strips the newline
				// character(s)
				response += str;
			}
			in.close();
		} catch (MalformedURLException e) {
		} catch (IOException e) {
		}

		Pattern pattern = Pattern.compile("locu-menu-item-name");
		Matcher matcher = pattern.matcher(response);
		while (matcher.find()) {
			try {
				String item = response.substring(matcher.end() + 3,
						response.indexOf("</div>", matcher.end()));
				item.replaceAll("&quot", "'");
				addMenuItem(rest_id, item);
			} catch (Exception e) {
				break;
			}
		}
	}

	// for testing:
	public static void main(String[] args) {
		SecondParser test = new SecondParser(
				"http://www.allspicerestaurant.com/");
		test.openDBConnection();
		test.getMenu("2");
		// test.getInfo("2");
	}
}
