<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Computers</title>
</head>
<body>

<p><% out.println(request.getAttribute("computer")); %><p>


<c:out value="${computer.getName()}"/>

<%-- <table>
  <c:forEach items="${computers}" var="item">
    <tr>
      <td><c:out value="${item.name}" /></td>
    </tr>
  </c:forEach>
</table> --%>
</body>
</html>