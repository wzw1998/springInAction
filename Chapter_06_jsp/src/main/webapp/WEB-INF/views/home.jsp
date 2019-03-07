<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ page session="false" %>
<%@ page isELIgnored="false"%>

<html>
  <head>
    <title>Spitter</title>
    <link rel="stylesheet" 
          type="text/css"
          href="../resources/style.css">
  </head>
  <body>
    <h1><s:message code="spittr.welcome" /> </h1>

      <s:url value="/spitter/register" var="registerUrl" scope="page" />

      <s:url value="/spittles" var="spittlesUrl">
        <s:param name="max" value="60"/>
        <s:param name="count" value="20" />
      </s:url>

    <s:url value="/spittles"  htmlEscape="true">
      <s:param name="max" value="60"/>
      <s:param name="count" value="20" />
    </s:url>

    <s:url value="/spittles" var="jsUrl" javaScriptEscape="true">
      <s:param name="max" value="60"/>
      <s:param name="count" value="20" />
    </s:url>

    <script>
      var spittlesUrl = ${jsUrl};
    </script>

    <a href="${spittlesUrl}">Spittles</a>
    <a href="${registerUrl}">register</a>

  </body>
</html>
