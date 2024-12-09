import com.opensymphony.xwork2.ActionContext;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
public class additem {

    private String title;
    private String description;
    private double startingPrice;

    public String renderForm() { // rendering the form BEFORE any logic
        						// issues with form submitting when opening my additem.jsp page
        return "input";
    }

    public String execute() {
        try {
            Map<String, Object> session = ActionContext.getContext().getSession(); // get session
            String username = (String) session.get("username");

            if (username == null) {
                System.out.println("User is not logged in.");
                return "error";
            }

          

            Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/ecom?serverTimezone=UTC", "root", "root");

            PreparedStatement userStatement = connection.prepareStatement( // get userid
                "SELECT id FROM users WHERE username = ?");
            userStatement.setString(1, username);
            ResultSet userResult = userStatement.executeQuery();
            int userId = 0;
            if (userResult.next()) {
                userId = userResult.getInt("id");
            }

            PreparedStatement itemStatement = connection.prepareStatement( // set item
                "INSERT INTO items (user_id, title, description, starting_price) VALUES (?, ?, ?, ?)");
            itemStatement.setInt(1, userId);
            itemStatement.setString(2, title);
            itemStatement.setString(3, description);
            itemStatement.setDouble(4, startingPrice);
            itemStatement.executeUpdate();

            System.out.println("Item added successfully: " + title);
            return "success";

        } catch (Exception e) { // error handling
            e.printStackTrace();
            return "error";
        }
    }

    public String getTitle() {     // getters and setters
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(double startingPrice) {
        this.startingPrice = startingPrice;
    }
}
