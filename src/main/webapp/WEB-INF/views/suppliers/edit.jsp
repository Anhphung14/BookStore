<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="f"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><c:choose>
		<c:when test="${task == 'edit'}">Edit Supplier</c:when>
		<c:otherwise>New Supplier</c:otherwise>
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
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/css/toastr.min.css"
	rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/js/toastr.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/app.js"
	defer></script>
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
							<c:when test="${task == 'edit'}">Edit Supplier</c:when>
							<c:otherwise>New Supplier</c:otherwise>
						</c:choose>
					</h2>
				</div>
				<div class="col-auto d-none d-sm-block">
					<img class="page-icon" src="${pageContext.servletContext.contextPath}/resources/images/page.svg"
						width="120px" alt="Page Icon">
				</div>
			</div>
			<form id="userForm"
				action="supplier/save.htm"
				method="POST">
				<input type="hidden" id="task" name="task" value="${task}">

				<c:if test="${task != 'new'}">
					<input type="hidden" id="id" name="id" value="${supplier.id}">
				</c:if>

				<div class="card">
					<div class="card-body">
						<h6 class="small text-muted">GENERAL INFORMATION</h6>

						<div class="form-floating mt-3">
							<input class="form-control" id="name" name="name"
								value="${supplier.name}" required> <label
								class="form-label" for="name">Name <span
								class="text-danger">*</span></label>
						</div>

						<div class="form-floating mt-3">
							<input class="form-control" id="contactPerson"
								name="contactPerson" value="${supplier.contactPerson}" required>
							<label class="form-label" for="contactPerson">Contact
								Person <span class="text-danger">*</span>
							</label>
						</div>

						<div class="form-floating mt-3">
							<input class="form-control" id="email" name="email"
								value="${supplier.email}" required pattern="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"> <label
								class="form-label" for="email">Email <span
								class="text-danger">*</span></label>
						</div>

						<div class="form-floating mt-3">
							<input class="form-control" id="phone" name="phone"
								value="${supplier.phone}" pattern="^0(3|5|7|8|9)[0-9]{8}$" required> <label class="form-label"
								for="phone">Phone <span class="text-danger">*</span></label>
						</div>

						<div class="form-floating mt-3">
							<input class="form-control" id="address" name="address"
								value="${supplier.address}"> <label class="form-label"
								for="address">Address <span class="text-danger">*</span></label>
						</div>
					</div>
				</div>

				<div class="mt-3">
					<button class="btn btn-primary btn-save" type="submit">Save</button>
					<a href="<c:url value='/admin1337/suppliers.htm' />"
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
