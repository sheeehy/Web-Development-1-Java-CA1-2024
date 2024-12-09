import com.opensymphony.xwork2.ActionContext;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;

public class makebid {

    private String title; 
    private double bidAmount;



    public String execute() {
        try {
            Map<String, Object> session = ActionContext.getContext().getSession();
            
            String username = (String) session.get("username");

            if (username == null) {
                System.out.println(" User not logged in.");
                return "error";
            }

            Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/ecom?serverTimezone=UTC", "root", "root");

            
            PreparedStatement itemStatement = connection.prepareStatement( // see if the item exists 
                "SELECT id FROM items WHERE title = ?");
            itemStatement.setString(1, title);
            ResultSet itemResult = itemStatement.executeQuery();
            int itemId = 0;
            if (itemResult.next()) {
                itemId = itemResult.getInt("id");
            } else {
                System.out.println("Error: Item with title '" + title + "' does not exist.");
                return "error";
            }

            PreparedStatement userStatement = connection.prepareStatement(  // get the userid

                "SELECT id FROM users WHERE username = ?");
            userStatement.setString(1, username);
            ResultSet userResult = userStatement.executeQuery();
            int userId = 0;
            if (userResult.next()) {
                userId = userResult.getInt("id");
            }

           
            PreparedStatement bidStatement = connection.prepareStatement(  // put the bid into the bids table
                "INSERT INTO bids (item_id, user_id, bid_amount) VALUES (?, ?, ?)");
            bidStatement.setInt(1, itemId);
            bidStatement.setInt(2, userId);
            bidStatement.setDouble(3, bidAmount);
            bidStatement.executeUpdate();

            System.out.println("Bid placed: Title '" + title + "', Amount €" + bidAmount);
            return "success";

        } catch (Exception e) { // error handling
            e.printStackTrace();
            return "error";
        }
    }

    // getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(double bidAmount) {
        this.bidAmount = bidAmount;
    }
}
