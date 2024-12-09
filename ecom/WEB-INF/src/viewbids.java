import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class viewbids {

    private String title; // 
    private List<String> bids = new ArrayList<>(); // list to put bids

    public String execute() {
        try {
            Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/ecom?serverTimezone=UTC", "root", "root");

            PreparedStatement itemStmt = connection.prepareStatement(  // Get the itemid

                "SELECT id FROM items WHERE title = ?");
            itemStmt.setString(1, title);
            ResultSet itemResult = itemStmt.executeQuery();
            int itemId = 0;
            if (itemResult.next()) {
                itemId = itemResult.getInt("id");
            } else {
                System.out.println("Error: Item with title '" + title + "' does not exist.");
                return "error";
            }

            // get all the bids for the item
            PreparedStatement bidStmt = connection.prepareStatement(
                "SELECT b.bid_amount, u.username FROM bids b JOIN users u ON b.user_id = u.id WHERE b.item_id = ?");
            bidStmt.setInt(1, itemId);
            ResultSet bidResult = bidStmt.executeQuery();

            while (bidResult.next()) {   // add bid to the list

                String bid = "Bid Amount: €" + bidResult.getDouble("bid_amount") +
                             ", Bidder: " + bidResult.getString("username");
                bids.add(bid);
            }

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

    public List<String> getBids() {
        return bids;
    }
}
