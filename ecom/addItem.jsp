<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
  <head>
    <title>Welcome Page</title>
  </head>
  <body>
    <h2>Welcome</h2>
    <p>Your username is: <s:property value="#session.currentUser" /></p>
    <p>Your color preference is: <s:property value="#session.colorPreference" /></p>
  </body>
</html>
