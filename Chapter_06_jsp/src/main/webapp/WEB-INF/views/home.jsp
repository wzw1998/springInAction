<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ page session="false" %>
<%@ page isELIgnored="false"%>

<html>
  <head>
    <title>Spitter</title>
    <link rel="stylesheet" 
          type="text/css" 
          href="<c:url value="/WEB-INF/resources/style.css" />" >
  </head>
  <body>
    <h1><s:message code="spittr.welcome" /> </h1>

      <s:url value="/spitter/register" var="registerUrl" scope="page">
            <s:param name="max" value="60"/>
            <s:param name="count" value="20" />
      </s:url>

    <a href="<s:url value="/spittles" />">Spittles</a> | 
    <a href="${registerUrl}">register</a>

  </body>
</html>
