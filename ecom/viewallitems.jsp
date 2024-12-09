<%@page contentType="text/html" pageEncoding="UTF-8"%> <%@ taglib uri="/struts-tags" prefix="s" %>

<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>All Items for Sale</title>
  </head>
  <body>
    <h1>All Items for Sale</h1>

    <ul>
      <%-- using an iterator to loop through the list --%>

      <s:iterator value="itemsForSale">
        <li><s:property /></li>
      </s:iterator>
    </ul>

    <a href="home.jsp">Back to Home</a>
  </body>
</html>
