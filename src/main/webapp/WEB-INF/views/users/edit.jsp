<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><c:choose>
		<c:when test="${task == 'edit'}">Edit User</c:when>
		<c:otherwise>New User</c:otherwise>
	</c:choose></title>

<base href="${pageContext.servletContext.contextPath}/admin1337/">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
<link
	href="https://cdn.jsdelivr.net/npm/choices.js/public/assets/styles/choices.min.css"
	rel="stylesheet">

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
							<c:when test="${task == 'edit'}">Edit User</c:when>
							<c:otherwise>New User</c:otherwise>
						</c:choose>
					</h2>
				</div>
				<div class="col-auto d-none d-sm-block">
					<img class="page-icon" src="${pageContext.request.contextPath}/resources/images/page.svg"
						width="120px" alt="Page Icon">
				</div>
			</div>
			<form id="userForm"
				action="user/save.htm"
				method="POST">
				<input type="hidden" id="task" name="task" value="${task}">

				<c:if test="${task != 'new'}">
					<input type="hidden" id="id" name="id" value="${user.id}">
				</c:if>

				<div class="card">
					<div class="card-body">
						<h6 class="small text-muted">GENERAL INFORMATION</h6>

						<div class="form-floating mt-3">
							<input class="form-control" id="fullname" name="fullname"
								value="${user.fullname}" required> <label
								class="form-label" for="fullname">Name <span
								class="text-danger">*</span></label>
						</div>

						<div class="form-floating mt-3">
							<input class="form-control" id="email" name="email"
								value="${user.email}" required> <label
								class="form-label" for="email">Email <span
								class="text-danger">*</span></label>
						</div>

						<div class="form-floating mt-3">
							<input class="form-control" id="phone" name="phone"
								value="${user.phone}"> <label class="form-label"
								for="phone">Phone</label>
						</div>
						<div class="form-floating mt-3">
							<select class="form-control" id="gender" name="gender" required>
								<option value="1" ${user.gender == 1 ? 'selected' : ''}>Male</option>
								<option value="2" ${user.gender == 2 ? 'selected' : ''}>Female</option>
								<option value="3" ${user.gender == 3 ? 'selected' : ''}>Other</option>
							</select> <label class="form-label" for="gender">Gender <span
								class="text-danger">*</span></label>
						</div>
						<div class="form-floating mt-3">
							<div class="ms-2">Roles</div>
							<select class="w-100 selectpicker" id="roles" name="roleIds"
								multiple data-coreui-search="true" data-live-search="true">
								<c:forEach var="role" items="${roles}">
									<option value="${role.id}"
										${user.roles != null && user.roles.contains(role) ? 'selected' : ''}>
										${role.name}
									</option>
								</c:forEach>
							</select>


						</div>


					</div>
				</div>

				<div class="mt-3">
					<button class="btn btn-primary btn-save" type="submit">Save</button>
					<a href="<c:url value='/admin1337/users.htm' />"
						class="btn btn-light btn-cancel">Cancel</a>
				</div>
			</form>
		</div>

	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/choices.js/public/assets/scripts/choices.min.js"></script>

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
	<script>
		document.addEventListener('DOMContentLoaded', function() {
			var element = document.getElementById('roles');
			var choices = new Choices(element, {
				removeItemButton : true,
				searchEnabled : true,
				searchChoices : true
			});
		});
	</script>

</body>
</html>