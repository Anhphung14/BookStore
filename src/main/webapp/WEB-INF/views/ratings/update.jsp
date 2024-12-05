<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Update Rating Status</title>

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
						Update Rating Status
					</h2>
				</div>
				<div class="col-auto d-none d-sm-block">
					<img class="page-icon" src="${pageContext.servletContext.contextPath}/resources/images/page.svg"
						width="120px" alt="Page Icon">
				</div>
			</div>
			<form id="userForm"
				action="rating/update.htm"
				method="POST">
				<input type="hidden" id="id" name="id" value="${rating.id}">

				<div class="card">
					<div class="card-body">
						<h6 class="small text-muted">GENERAL INFORMATION</h6>

						<div class="form-floating mt-3">
							<input class="form-control" id="user" name="user"
								value="${rating.user.fullname}" required disabled> <label
								class="form-label" for="user">User <span
								class="text-danger">*</span></label>
						</div>

						<div class="form-floating mt-3">
							<input class="form-control" id="orderid"
								name="orderid" value="${rating.order.id}" required disabled>
							<label class="form-label" for="orderid">Order ID
								Person <span class="text-danger">*</span>
							</label>
						</div>

						<div class="form-floating mt-3">
							<input class="form-control" id="book" name="book"
								value="${rating.book.title}" required disabled> <label
								class="form-label" for="book">Book <span
								class="text-danger">*</span></label>
						</div>

						<div class="form-floating mt-3">
							<input class="form-control" id="rateStar" name="rateStar"
								value="${rating.number}" disabled> <label class="form-label"
								for="rateStar">Rate <span
								class="text-danger">*</span></label>
						</div>

						<div class="form-floating mt-3">
							<input class="form-control" id="review" name="review"
								value="${rating.content}" disabled> <label class="form-label"
								for="review">Review </label>
						</div>
						
						<div class="form-floating mt-3">
			                <select class="form-control" id="status" name="status" required>
			                    <option value="0" ${rating.status == 0 ? 'selected' : ''}>Unapproved</option>
			                    <option value="1" ${rating.status == 1 ? 'selected' : ''}>Approved</option>
			                    <option value="2" ${rating.status == 2 ? 'selected' : ''}>Rejected</option>
			                </select>
			                <label class="form-label" for="status">Status <span class="text-danger">*</span></label>
			            </div>
						
					</div>
				</div>

				<div class="mt-3">
					<button class="btn btn-primary btn-save" type="submit">Save</button>
					<a href="<c:url value='/admin1337/ratings.htm' />"
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
