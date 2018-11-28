<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Companies</title>
</head>
<body>
	<a href="/computer-database/">Return</a>

	<table>
		<c:forEach items="${companies}" var="item">
			<tr>
				<td><c:out value="${item}" /></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>