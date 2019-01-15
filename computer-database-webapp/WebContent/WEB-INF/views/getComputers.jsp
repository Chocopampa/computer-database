<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
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
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="/computer-database-webapp/">
				Application - Computer Database </a>
				|
			<a class="navbar-brand" href="/computer-database-webapp/logout">
				Log out </a>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<h1 id="homeTitle">
				<c:out value="${result_size}" />
				<spring:message code="label.count.computers" />
			</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="#" method="GET" class="form-inline">

						<input type="search" id="searchbox" name="search"
							class="form-control"
							placeholder="<spring:message
									code="label.search.field" />" />
						<input type="submit" id="searchsubmit"
							value="<spring:message
									code="label.search.button" />"
							class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer"
						href="/computer-database-webapp/addComputer"><spring:message
							code="label.add.computer" /></a> <a class="btn btn-default"
						id="editComputer" href="#" onclick="$.fn.toggleEditMode();"><spring:message
							code="label.delete.button" /></a>
				</div>
			</div>
		</div>

		<form id="deleteForm" action="/computer-database-webapp/"
			method="POST">
			<input type="hidden" name="selection" value="">
			<div class="container" style="margin-top: 10px;">
				<table id="computerTable" class="table table-striped table-bordered">
					<thead>
						<tr>
							<!-- Variable declarations for passing labels as parameters -->
							<!-- Table header for Computer Name -->

							<th class="editMode" style="width: 60px; height: 22px;"><input
								type="checkbox" id="selectall" /> <span
								style="vertical-align: top;"> - <a href="#"
									id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
										class="fa fa-trash-o fa-lg"></i>
								</a>
							</span></th>
							<th id="tableHeadComputerName"><spring:message
									code="label.column.name" /></th>
							<th id="tableHeadComputerIntroduced"><spring:message
									code="label.column.introduced" /></th>
							<!-- Table header for Discontinued Date -->
							<th id="tableHeadComputerDiscontinued"><spring:message
									code="label.column.discontinued" /></th>
							<!-- Table header for Company -->
							<th id="tableHeadComputerCompany"><spring:message
									code="label.column.company.name" /></th>

						</tr>
					</thead>
					<!-- Browse attribute computers -->
					<tbody id="results">
						<c:forEach items="${computers}" var="item">
							<tr>
								<td class="editMode"><input type="checkbox"
									name="computerChecked" class="cb" value="${item.id}"></td>
								<td id="computerName"><a
									href="/computer-database-webapp/editComputer/${item.id}"
									onclick=""><c:out value="${item.name}" /></a></td>
								<td id="computerIntroduced"><c:out
										value="${item.introduced}" /></td>
								<td id="computerDiscontinued"><c:out
										value="${item.discontinued}" /></td>
								<td id="computerCompany"><c:out value="${item.companyName}" /></td>
							</tr>
						</c:forEach>

					</tbody>
				</table>
			</div>
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
		</form>
	</section>

	<footer class="navbar-fixed-bottom">
		<div class="container text-center">
			<ul class="pagination">
				<li><a
					href='?nbItem=${param["nbItem"] != null ? param["nbItem"] : result_size}&order=${param["order"]}&change=false&numPage=${(param["numPage"]-1) < 0 ? 0 : param["numPage"]-1}'
					aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
				</a></li>
				<li><a
					href='?nbItem=${param["nbItem"] != null ? param["nbItem"] : result_size}&order=${param["order"]}&change=false&numPage=0'>1</a></li>
				<li><a
					href='?nbItem=${param["nbItem"] != null ? param["nbItem"] : result_size}&order=${param["order"]}&change=false&numPage=1'>2</a></li>
				<li><a
					href='?nbItem=${param["nbItem"] != null ? param["nbItem"] : result_size}&order=${param["order"]}&change=false&numPage=2'>3</a></li>
				<li><a
					href='?nbItem=${param["nbItem"] != null ? param["nbItem"] : result_size}&order=${param["order"]}&change=false&numPage=3'>4</a></li>
				<li><a
					href='?nbItem=${param["nbItem"] != null ? param["nbItem"] : result_size}&order=${param["order"]}&change=false&numPage=4'>5</a></li>
				<li><a
					href='?nbItem=${param["nbItem"] != null ? param["nbItem"] : result_size}&order=${param["order"]}&change=false&numPage=${result_size != param["nbItem"] ? param["numPage"] : param["numPage"]+1}'
					aria-label="Next"> <span aria-hidden="true">&raquo;</span>
				</a></li>
			</ul>

			<div class="btn-group btn-group-sm pull-right" role="group">
				<button type="button" onclick="location.href='?nbItem=10';"
					class="btn btn-default">10</button>
				<button type="button" onclick="location.href='?nbItem=50';"
					class="btn btn-default">50</button>
				<button type="button" onclick="location.href='?nbItem=100';"
					class="btn btn-default">100</button>
			</div>

			<div class="btn-group btn-group-sm pull-right" role="group">
				<button type="button" onclick="location.href='?lang=en';"
					class="btn btn-default">en</button>
				<button type="button" onclick="location.href='?lang=fr';"
					class="btn btn-default">fr</button>
			</div>

		</div>
	</footer>

	<script src='<spring:url value="/resources/js/jquery.min.js"/>'></script>
	<script src='<spring:url value="/resources/js/bootstrap.min.js"/>'></script>
	<script src='<spring:url value="/resources/js/dashboard.js"/>'></script>
	<script src='<spring:url value="/resources/js/jquery.tablesorter.js"/>'></script>
	<script src='<spring:url value="/resources/js/orderBy.js"/>'></script>
</body>
</html>