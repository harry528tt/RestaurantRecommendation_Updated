import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;

/*
 * This class is the middle ware between MySQL DB and application logic,
 * it handles all the database operation, to separate the parsing and recommendation
 * logic from SQL statements.
 */
public class DatabaseOperation {
	private String dbURL = "jdbc:mysql://localhost/restaurant";
	private String username = "root";
	private String password = "";

	private Connection conn = null;

	public DatabaseOperation(String dbURL, String username, String password) {
		this.dbURL = dbURL;
		this.username = username;
		this.password = password;
	}

	public DatabaseOperation() {
		// do nothing;
	}

	public void openConnection() {
		try {
			// Setup the connection with the DB
			conn = DriverManager.getConnection(dbURL, username, password);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public void addRestaurant(String id, String name) {
		// PreparedStatements can use variables and are more efficient
		PreparedStatement preparedStatement;
		try {
			System.out.println(id+name);
			preparedStatement = conn
					.prepareStatement("insert into restaurant (id,name) values (?,?)");
			
			preparedStatement.setString(1, id);
			preparedStatement.setString(2, name);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void addMenuItem(String rest_id, String item){
		PreparedStatement preparedStatement;
		try {
			preparedStatement = conn
					.prepareStatement("insert into menu (item,rest_id) values (?,?)");
			preparedStatement.setString(1, item);
			preparedStatement.setString(2, rest_id);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addInfo(String rest_id, String phone, String address){
		PreparedStatement preparedStatement;
		try {
			preparedStatement = conn
					.prepareStatement("insert into info (phone,address,rest_id) values (?,?,?)");
			preparedStatement.setString(1, phone);
			preparedStatement.setString(2, address);
			preparedStatement.setString(3, rest_id);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ArrayList<String[]> recommend(String word){
		PreparedStatement preparedStatement;
		ArrayList<String[]> resultList = new ArrayList<String[]>();
		String[] result = new String[2];
		try {
			preparedStatement = conn
					.prepareStatement("select item,name from restaurant join menu on restaurant.id = menu.rest_id where menu.item LIKE ?");
			preparedStatement.setString(1, "%"+word+"%");
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				String item = rs.getString("item");
				String name = rs.getString("name");
				resultList.add(new String[]{item, name});
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultList;
	}
	
	public void close(Statement stmt) {
		try {

			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//for testing:
	public static void main(String[] args){
	}
}