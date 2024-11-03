import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    		// get username and password from the request
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Connection connection = null;
        PreparedStatement selectUser = null;
        ResultSet resultSet = null;

        try {
            // get connection to the database
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/loyalty?serverTimezone=UTC", "root", "root");

            // create sql query to verify the username and password
            String sql = "SELECT points FROM users WHERE username = ? AND password = ?";
            selectUser = connection.prepareStatement(sql);
            selectUser.setString(1, username);
            selectUser.setString(2, password);

            resultSet = selectUser.executeQuery();

            if (resultSet.next()) {
                // if login successful retrieve points
                int points = resultSet.getInt("points");

                // store the user info in session for later use
                // needed to access data across pages, i.e - balance page
                HttpSession session = request.getSession();
                session.setAttribute("username", username);
                session.setAttribute("points", points);

                // redirect to the balance page 
                response.sendRedirect("balance.html");

            } else {
                // if login failed display user not found
                response.setContentType("text/html");
                response.getWriter().println("<html><head><title>Login Failed</title></head><body>");
                response.getWriter().println("<h1>Invalid username or password. Please try again.</h1>");
                response.getWriter().println("</body></html>");
            }

        } catch (SQLException e) { // if sql exception display db error
            e.printStackTrace();
            response.setContentType("text/html");
            response.getWriter().println("<html><head><title>Error</title></head><body>");
            response.getWriter().println("<h1>Database error occurred. Please try again later.</h1>");
            response.getWriter().println("</body></html>");
        
        }
    }
}
