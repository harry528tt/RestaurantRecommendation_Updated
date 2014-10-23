import java.util.*;

/*
 * This class is the interactive interface for user
 */
public class ConsoleApp {

	/*
	 * Crawl data from both of the websites.
	 */
	public void crawlData() {
		FirstParser test1 = new FirstParser("http://www.dinebombaygarden.com/");
		test1.openDBConnection();
		test1.addRestaurant("1", "Bombay Garden");
		test1.getMenu("1");
		test1.getInfo("1");
		SecondParser test2 = new SecondParser(
				"http://www.allspicerestaurant.com/");
		test2.openDBConnection();
		test2.addRestaurant("2", "All Spice Restaurant");
		test2.getMenu("2");
		test2.getInfo("2");
	}

	public static void main(String[] args) {
		ConsoleApp ca = new ConsoleApp();
		Recommendation rc = new Recommendation();
		rc.openDBConnection();
		System.out.println("Crawling data...");
		ca.crawlData();
		System.out.println("Data collection completed!\n"
				+ "Enter 1 for recommendation\n" + "Enter exit to exit.");
		Scanner sc = new Scanner(System.in);
		String command = sc.nextLine();
		if (command.equals("1")) {
			System.out.println("Please enter a word for recommendation:");
			while (sc.hasNextLine()) {
				String word = sc.nextLine();
				if (word.equals("exit")) {
					System.out.println("Bye.");
					System.exit(0);
				}
				System.out.println(rc.recommend(word));
			}
		} else if (command.equals("exit")) {
			System.out.println("Bye.");
			System.exit(0);
		} else {
			System.out.println("Wrong command! Exiting..");
		}

	}
}
