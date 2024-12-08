import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class register {
    
    private String username;
    private String password;

    public register() {
    }

    public String registerUser() {
        String result = "success";

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/ecom?serverTimezone=UTC", "root", "root");
        } catch (SQLException e) {
            e.printStackTrace();
            result = "error";
            return result;
        }
        	//PUT SOMETHING INTO THE DATABASE USING A PREPAREDSTATEMENT
      		//Use a preparedStatement (parameterised SQL statement) to add a word
        PreparedStatement createUser = null;
        
      //pass in the values as parameters
        try {
            createUser = connection.prepareStatement(
                "INSERT INTO users (username, password) VALUES (?, ?)");
            createUser.setString(1, username);
            createUser.setString(2, password);

            int rowsInserted = createUser.executeUpdate();
            if (rowsInserted <= 0) { // if no rows added is error
                result = "error";
            }
        } catch (SQLException e) { // if sql exception 
            e.printStackTrace();
            result = "error";
        } 

        return result;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
