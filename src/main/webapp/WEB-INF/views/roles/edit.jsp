<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><c:choose>
		<c:when test="${task == 'edit'}">Edit Role</c:when>
		<c:otherwise>New Role</c:otherwise>
	</c:choose></title>

<base href="${pageContext.servletContext.contextPath}/admin1337/">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
<style>
.main-content {
	display: flex;
	flex: 1;
}

.btn i {
	font-size: 0.8em;
}

.custom-text {
	color: #343a40;
}
</style>
</head>
<body>

	<jsp:include page="/WEB-INF/views/layouts/header.jsp" />

	<div class="main-content">
		<jsp:include page="/WEB-INF/views/layouts/navigation.jsp" />

		<div class="container-fluid"
			style="margin-left: 0px; margin-right: 0px;">

			<div class="row g-3 mt-3">
				<div class="col">
					<h2 class="h3">
						<c:choose>
							<c:when test="${task == 'edit'}">Edit Role</c:when>
							<c:otherwise>New Role</c:otherwise>
						</c:choose>
					</h2>
				</div>
				<div class="col-auto d-none d-sm-block">
					<img class="page-icon" src="${pageContext.servletContext.contextPath}/resources/images/page.svg"
						width="120px" alt="Page Icon">
				</div>
			</div>
			<form id="userForm" action="role/save" method="POST">
				<input type="hidden" id="task" name="task" value="${task}">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				<c:if test="${task != 'new'}">
					<input type="hidden" id="id" name="id" value="${role.id}">
				</c:if>

				<div class="card">
					<div class="card-body">
						<h6 class="small text-muted">GENERAL INFORMATION</h6>

						<div class="form-floating mt-3">
							<input class="form-control" id="name" name="name"
								value="${role.name }" required> <label
								class="form-label" for="name">Name <span
								class="text-danger">*</span></label>
						</div>
						<div class="form-floating mt-3">
							<input class="form-control" id="description" name="description"
								value="${role.description}" required> <label
								class="form-label" for="description">Description<span
								class="text-danger">*</span></label>
						</div>
					</div>
				</div>

				<div class="mt-3">
					<button class="btn btn-primary btn-save" type="submit">Save</button>
					<a href="<c:url value='/admin1337/roles' />"
						class="btn btn-light btn-cancel">Cancel</a>
				</div>
			</form>


		</div>

	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
	<script>
		document.getElementById("userForm").addEventListener("submit",
				function(event) {
					const email = document.getElementById("email").value;
					const phone = document.getElementById("phone").value;

					const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
					if (!emailRegex.test(email)) {
						alert("Invalid email format.");
						event.preventDefault();
					}

					if (phone && !/^\d{10}$/.test(phone)) {
						alert("Phone number must be 10 digits.");
						event.preventDefault();
					}
				});
	</script>
</body>
</html>
