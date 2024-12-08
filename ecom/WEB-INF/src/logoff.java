import com.opensymphony.xwork2.ActionContext;
import java.util.Map;

public class logoff {

    public String logoffUser() {
        try {
           
            Map<String, Object> session = ActionContext.getContext().getSession();  // get the current session

            // clear the session, removing any data
            session.clear();
            System.out.println("User logged off."); // print success message

            return "success";
        } catch (Exception e) { // error handling
            e.printStackTrace();
            return "error";
        }
    }
}
