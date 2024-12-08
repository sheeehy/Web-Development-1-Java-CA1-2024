<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/struts-tags" prefix="s" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Register</title>
  </head>
  <body>
    <h2>Register</h2>
    <s:form action="register">
      <s:textfield name="username" label="Username" />
      <s:password name="password" label="Password" />
      <s:submit value="register" />
    </s:form>
  </body>
</html>
