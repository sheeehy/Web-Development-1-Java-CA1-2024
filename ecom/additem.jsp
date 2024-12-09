<%@page contentType="text/html" pageEncoding="UTF-8"%> <%@ taglib uri="/struts-tags" prefix="s" %><!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Add Item</title>
  </head>
  <body>
    <h1>Add Item for Sale</h1>

    <s:form action="submitAddItem">
      <s:textfield name="title" label="Title" />
      <s:textarea name="description" label="Description" />
      <s:textfield name="startingPrice" label="Starting Price (€)" />
      <s:submit value="Add Item" />
    </s:form>

    <a href="home.jsp">Back to Home</a>
  </body>
</html>
