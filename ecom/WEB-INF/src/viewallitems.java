import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class viewallitems {

    private List<String> itemsForSale = new ArrayList<>(); // items for sale list

    public String execute() {
        try {
        	
        	
            Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/ecom?serverTimezone=UTC", "root", "root");

            // get all items
            
            PreparedStatement statement = connection.prepareStatement(
                "SELECT i.title, i.description, i.starting_price, u.username FROM items i JOIN users u ON i.user_id = u.id");
            ResultSet resultSet = statement.executeQuery();

            // fill the list with item details
            while (resultSet.next()) {
                String item = "Title: " + resultSet.getString("title") +
                              ", Description: " + resultSet.getString("description") +
                              ", Starting Price: €" + resultSet.getDouble("starting_price") +
                              ", User: " + resultSet.getString("username");
                
                itemsForSale.add(item);
                System.out.println(" item for sale: " + item);
            }

            return "success";

        } catch (Exception e) { // error handling
            e.printStackTrace();
            return "error";
        }
    }

    // getter for itemsForSale
    public List<String> getItemsForSale() {
        return itemsForSale;
    }
}
