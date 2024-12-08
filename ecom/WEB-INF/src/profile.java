import com.opensymphony.xwork2.ActionContext;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class profile {

    private String username;
    private List<String> myItems = new ArrayList<>(); // arrays for items + bids
    private List<String> myBids = new ArrayList<>();

    public String execute() { //default method
        try {
            Map<String, Object> session = ActionContext.getContext().getSession(); //get current session
            username = (String) session.get("username");

            if (username == null) {
                return "error"; // go to login if no user is logged in
            }


            Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/ecom?serverTimezone=UTC", "root", "root");

            // get the userid
            PreparedStatement userStatement = connection.prepareStatement(
                "SELECT id FROM users WHERE username = ?");
            userStatement.setString(1, username);
            ResultSet userResult = userStatement.executeQuery();
            int userId = 0;
            if (userResult.next()) {
                userId = userResult.getInt("id"); // update userid
            }

           
            PreparedStatement itemsStatement = connection.prepareStatement(
                "SELECT title, description, starting_price FROM items WHERE user_id = ?");
            itemsStatement.setInt(1, userId);
            ResultSet itemsResult = itemsStatement.executeQuery();
            while (itemsResult.next()) {
                myItems.add("Title: " + itemsResult.getString("title") +
                            ", Description: " + itemsResult.getString("description") +
                            ", Starting Price: €" + itemsResult.getDouble("starting_price"));
            }

            PreparedStatement bidsStatement = connection.prepareStatement( // get bids
                "SELECT b.bid_amount, i.title FROM bids b INNER JOIN items i ON b.item_id = i.id WHERE b.user_id = ?");
            bidsStatement.setInt(1, userId);
            ResultSet bidsResult = bidsStatement.executeQuery();
            while (bidsResult.next()) {
                myBids.add("Bid Amount: €" + bidsResult.getDouble("bid_amount") +
                           ", On Item: " + bidsResult.getString("title"));
            }

            return "success";

        } catch (Exception e) { // error handling 
            e.printStackTrace();
            return "error";
        }
    }

    // getters and setters
    public String getUsername() {
        return username;
    }

    public List<String> getMyItems() {
        return myItems;
    }

    public List<String> getMyBids() {
        return myBids;
    }
}
