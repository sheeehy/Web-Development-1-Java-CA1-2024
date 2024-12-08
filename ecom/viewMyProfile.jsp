<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>My Profile</title>
  </head>
  <body>
    <h1>My Profile</h1>

    <h2>Username: <s:property value="username" /></h2>

    <h3>My Items for Sale</h3>
    <ul>
      <s:iterator value="myItems">
        <li><s:property /></li>
      </s:iterator>
    </ul>

    <h3>My Bids</h3>
    <ul>
      <s:iterator value="myBids">
        <li><s:property /></li>
      </s:iterator>
    </ul>

    <a href="home.jsp">Back</a>
  </body>
</html>
