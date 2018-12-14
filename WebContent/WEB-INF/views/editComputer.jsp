<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href='<spring:url value="/resources/css/bootstrap.min.css"/>' rel="stylesheet"
	media="screen">
<link href='<spring:url value="/resources/css/font-awesome.css"/>' rel="stylesheet"
	media="screen">
<link href='<spring:url value="/resources/css/main.css"/>' rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="/computer-database/"> Application -
				Computer Database </a>
		</div>
	</header>
	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<div class="label label-default pull-right">id:
						${param["id"]}</div>
					<h1>Edit Computer</h1>

					<form id="computerFormular" name="computerFormular"
						action="/computer-database/editComputer/${computer.id}" method="POST">
						<input type="hidden" value="${computer.id}" id="id" />
						<fieldset>
							<div class="form-group">
								<label for="computerName"><spring:message
									code="label.column.name" /></label> <input
									type="text" class="form-control" id="computerName"
									name="computerName" placeholder="Computer name"
									value="${computer.name}">
							</div>
							<div class="form-group">
								<label for="introduced"><spring:message
									code="label.column.introduced" /></label> <input
									type="date" class="form-control" id="introduced"
									name="introduced" placeholder="Introduced date"
									value="${introduced}">
							</div>
							<div class="form-group">
								<label for="discontinued"><spring:message
									code="label.column.discontinued" /></label> <input
									type="date" class="form-control" id="discontinued"
									name="discontinued" placeholder="Discontinued date"
									value="${discontinued}">
							</div>
							<div class="form-group">
								<label for="companyId"><spring:message
									code="label.column.company.number" /></label> <select
									class="form-control" id="companyId" name="companyId">
									<option value="">--</option>
									<c:forEach items="${companiesIds}" var="companyId">
										<c:choose>
											<c:when test="${companyId == company.id}">
												<option value="${companyId}" selected="selected">${companyId}</option>
											</c:when>
											<c:otherwise>
												<option value="${companyId}">${companyId}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="Edit" class="btn btn-primary">
							or <a href="/computer-database/"
								class="btn btn-default">Cancel</a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</section>

	<script src='<spring:url value="/resources/js/jquery.min.js"/>'></script>
	<script src='<spring:url value="/resources/js/jquery.validate.min.js"/>'></script>
	<script src='<spring:url value="/resources/js/validation.js"/>'></script>
</body>
</html>