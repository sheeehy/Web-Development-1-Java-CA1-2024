import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoyaltyServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     
    	// get session and retrieve username and points from session attributes
        // using session to store data across pages
    	
    	HttpSession session = request.getSession();
               
        String username = (String) session.getAttribute("username");
        Integer currentPoints = (Integer) session.getAttribute("points");

        // get the action 
        String action = request.getParameter("action");

        Connection connection = null;
        PreparedStatement updatePoints = null;

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            // get connection to the database
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/loyalty?serverTimezone=UTC", "root", "root");

            if ("check".equals(action)) {
               
                out.println("<html><head><title>Your Balance</title></head><body>");
                out.println("<h1>Loyalty Card Application</h1>");
                out.println("<h2>Welcome, " + username + "</h2>");
                out.println("<p>Your current balance is: " + currentPoints + " points</p>");
            } else {
                // only parse points when adding or spending (not when checking)
                String pointsParam = request.getParameter("points");
                int points = Integer.parseInt(pointsParam);

                if ("add".equals(action)) {
                    // adding points
                    currentPoints += points;

                    // update the points in the database
                    String sql = "UPDATE users SET points = ? WHERE username = ?";
                    updatePoints = connection.prepareStatement(sql);
                    updatePoints.setInt(1, currentPoints);
                    updatePoints.setString(2, username);
                    updatePoints.executeUpdate();

                    // update points in the session for cross pages
                    session.setAttribute("points", currentPoints);

                    out.println("<html><head><title>Points Added</title></head><body>");
                    out.println("<h1>Your points have been added successfully.</h1>");
                    out.println("<p>Your new balance is: " + currentPoints + " points</p>");
                } else if ("spend".equals(action)) {
                    // spending points
                    if (currentPoints - points < 0) {
                        out.println("<html><head><title>Error</title></head><body>");
                        out.println("<h1>Insufficient points. Your balance cannot be below 0.</h1>");
                    } else {
                        currentPoints -= points;

                        // update the points in the database
                        String sql = "UPDATE users SET points = ? WHERE username = ?";
                        updatePoints = connection.prepareStatement(sql);
                        updatePoints.setInt(1, currentPoints);
                        updatePoints.setString(2, username);
                        updatePoints.executeUpdate();

                        // update points in the session
                        session.setAttribute("points", currentPoints);

                        out.println("<html><head><title>Points Spent</title></head><body>");
                        out.println("<h1>Your points have been spent successfully.</h1>");
                        out.println("<p>Your new balance is: " + currentPoints + " points</p>");
                    }
                }
            }

            out.println("<a href='balance.html'>Go back</a>");
            out.println("</body></html>");

        } catch (SQLException e) { // if error is sql then show db error
            e.printStackTrace();
            out.println("<html><head><title>Error</title></head><body>");
            out.println("<h1>Database error occurred. Please try again later.</h1>");
            out.println("</body></html>");
        } catch (NumberFormatException e) { // error
            out.println("<html><head><title>Error</title></head><body>");
            out.println("<h1>Invalid input for points. Please enter a valid number.</h1>");
            out.println("<a href='balance.html'>Go back</a>");
            out.println("</body></html>");
        } 
    }
}
