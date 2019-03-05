<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ page session="false" %>
<html>
  <head>
    <title>Spitter</title>
    <link rel="stylesheet" 
          type="text/css" 
          href="<c:url value="/WEB-INF/resources/style.css" />" >
  </head>
  <body>
    <h1>Welcome to spitter!</h1>

      <s:url value="/spitter/register" var="registerUrl" />

    <a href="<s:url value="/spittles" />">Spittles</a> | 
    <a href=" <s:url value="/spitter/register" />">registe</a>

  </body>
</html>
