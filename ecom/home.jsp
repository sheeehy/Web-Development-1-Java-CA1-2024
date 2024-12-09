<%@page contentType="text/html" pageEncoding="UTF-8"%> <%@ taglib uri="/struts-tags" prefix="s" %>

<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Home</title>
  </head>
  <body>
    <h1>Jack's ecom site</h1>

    Logged in as: <s:property value="#session.username" />

    <ul>
      <li><a href="logoff.action">Log Off</a></li>
      <li><a href="viewMyProfile.action">View My Profile</a></li>
      <li><a href="viewallusers.action">View All Users</a></li>
      <li><a href="additem.action">Add Item for Sale</a></li>
      <li><a href="viewallitems.action">View All Items for Sale</a></li>
      <li><a href="makebid.jsp">Make a Bid</a></li>
      <li><a href="viewbids.jsp">View All Bids on an Item</a></li>
    </ul>
  </body>
</html>
