import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class viewallusers {

    private List<Map<String, Object>> users = new ArrayList<>(); // list for all users

    public String execute() {
        try {
          
        	Connection connection = DriverManager.getConnection(
            		      		
                "jdbc:mysql://localhost:3306/ecom?serverTimezone=UTC", "root", "root");

            PreparedStatement userStatement = connection.prepareStatement(     // get all users
                "SELECT id, username FROM users");
            ResultSet userResult = userStatement.executeQuery();

            while (userResult.next()) {
                int userId = userResult.getInt("id");
                String username = userResult.getString("username");

                Map<String, Object> userDetails = new HashMap<>();
                userDetails.put("username", username);

                PreparedStatement itemsStatement = connection.prepareStatement(  // get items for sale by the user

                    "SELECT title, description, starting_price FROM items WHERE user_id = ?");
                itemsStatement.setInt(1, userId);
                ResultSet itemsResult = itemsStatement.executeQuery();

                List<String> itemsForSale = new ArrayList<>(); 
                while (itemsResult.next()) {
                    String item = "Title: " + itemsResult.getString("title") +
                                  ", Description: " + itemsResult.getString("description") +
                                  ", Starting Price: €" + itemsResult.getDouble("starting_price");
                    itemsForSale.add(item);
                }
                userDetails.put("itemsForSale", itemsForSale);

                PreparedStatement bidsStatement = connection.prepareStatement(  // get bids made by the user

                    "SELECT b.bid_amount, i.title FROM bids b JOIN items i ON b.item_id = i.id WHERE b.user_id = ?");
                bidsStatement.setInt(1, userId);
                ResultSet bidsResult = bidsStatement.executeQuery();

                List<String> userBids = new ArrayList<>();
                while (bidsResult.next()) {
                    String bid = "Bid Amount: €" + bidsResult.getDouble("bid_amount") +
                                 ", On Item: " + bidsResult.getString("title");
                    userBids.add(bid);
                }
                userDetails.put("userBids", userBids);

                users.add(userDetails); // add the user details to the list

            }

            return "success";

        } catch (Exception e) { // error handling
            e.printStackTrace();
            return "error";
        }
    }

    // one getter just for users
    public List<Map<String, Object>> getUsers() {
        return users;
    }
}
