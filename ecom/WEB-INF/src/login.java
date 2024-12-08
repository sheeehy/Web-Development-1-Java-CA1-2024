import com.opensymphony.xwork2.ActionContext;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;

public class login {

    private String username;
    private String password;

    public String loginUser() {
        try {
            Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/ecom?serverTimezone=UTC", "root", "root");

            PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM users WHERE username = ? AND password = ?");
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                // store only username in session, not password as its client side
                Map<String, Object> session = ActionContext.getContext().getSession();
                session.put("username", username);  
                System.out.println("Username added to session: " + username); // print statement just to double check
                return "success";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }

    // Getters and Setters
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
