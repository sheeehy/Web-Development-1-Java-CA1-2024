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

public class RegisterServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");


        Connection connection = null;
        PreparedStatement insertUser = null;

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try { // check if password is valid
            if (!password.equals(confirmPassword)) {
                out.println("<html><head><title>Error</title></head><body>");
                out.println("<h1>Passwords do not match.</h1>");
                out.println("</body></html>");
                return;
            }

            // connect to the database
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/loyalty?serverTimezone=UTC", "root", "root");

            // make the sql query to insert the user with 100 points
            String sql = "INSERT INTO users (username, password, points) VALUES (?, ?, 100)";
            insertUser = connection.prepareStatement(sql);

            insertUser.setString(1, username); // set the values
            insertUser.setString(2, password);  

        
            int rowsAffected = insertUser.executeUpdate();

          
            if (rowsAffected > 0) { // if rows updated
                out.println("<html><head><title>Registration Successful</title></head><body>");
                out.println("<h1>Thank you for registering, " + username + "</h1>");
                out.println("<p><a href='login.html'>log in</a></p>"); // link to login page

                out.println("</body></html>");
            } else {
                out.println("<html><head><title>Error</title></head><body>");
                out.println("<h1>There was an error with the registration.</h1>");
                out.println("</body></html>");
            }

        } catch (SQLException e) { // if error is with sql, use database error message
            e.printStackTrace();
            out.println("<html><head><title>Error</title></head><body>");
            out.println("<h1>Database error - try restarting the server.</h1>");
            out.println("</body></html>");
        }
            
        
    }
}
