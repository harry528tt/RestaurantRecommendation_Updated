import java.util.ArrayList;

/*
 *  used for generating recommendation.
 */
public class Recommendation {
	private DatabaseOperation dbOp = new DatabaseOperation();

	/*
	 * get a string as input, process it, then call the dbOp object to query in
	 * the database
	 */
	public String recommend(String word) {

		ArrayList<String[]> res = dbOp.recommend(word.toLowerCase());
		StringBuffer sb = new StringBuffer();
		if (res.size() == 0) {
			return "Sorry, We can't find any recommendations for you about "
					+ word;
		}
		//arrange the result to form a single string.
		for (int i = 0; i < res.size(); i++) {
			String result = res.get(i)[0] + " from " + res.get(i)[1];
			sb.append(result + "\n");
		}

		return sb.toString();
	}
	
	/*
	 * Open dabase connection.
	 */
	public void openDBConnection() {
		dbOp.openConnection();
	}

	// for testing:
	public static void main(String[] args) {
		Recommendation test = new Recommendation();
		test.openDBConnection();
		System.out.println(test.recommend("pakora"));
	}
}
