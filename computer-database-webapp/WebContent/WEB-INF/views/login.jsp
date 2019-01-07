<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href='<spring:url value="/resources/css/bootstrap.min.css"/>'
	rel="stylesheet" media="screen">
<link href='<spring:url value="/resources/css/font-awesome.css"/>'
	rel="stylesheet" media="screen">
<link href='<spring:url value="/resources/css/main.css"/>'
	rel="stylesheet" media="screen">
</head>

<body>
	<form action="login" method="post">
		<div>
			<input type="text" name="username" placeholder="Login" />
		</div>
		<div>
			<input type="password" name="password" placeholder="Password" />
		</div>
		<div>
			<input type="submit" value="Connection" />
		</div>
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</form>
</body>

</html>