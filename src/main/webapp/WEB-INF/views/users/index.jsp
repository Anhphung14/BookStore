<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Users management</title>
<base href="${pageContext.servletContext.contextPath}/admin1337/">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
<script src="${pageContext.request.contextPath}/resources/js/app.js"
	defer></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/css/toastr.min.css"
	rel="stylesheet">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/js/toastr.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/confirmBox.js"></script>
<link rel="stylesheet"
	href="https://cdn.datatables.net/1.11.5/css/jquery.dataTables.min.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script
	src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>
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
		<div class="container-fluid d-flex flex-column"
			style="padding-left: 0px; padding-right: 0px;">
			<div class="container-fluid"
				style="margin-left: 0px; margin-right: 0px;">
				<div class="row g-3 mt-3">
					<div class="col">
						<h2 class="h3">Users management</h2>
						<p>Manage users, roles, permissions, and profile.</p>
					</div>
					<div class="col-auto d-none d-sm-block">
						<img class="page-icon"
							src="${pageContext.servletContext.contextPath}/resources/images/page.svg"
							width="120px" alt="Page Icon">
					</div>
				</div>
			</div>
			<form id="frm-admin" name="adminForm" action="" method="POST">
				<input type="hidden" id="task" name="task" value="${param.task}">
				<input type="hidden" id="boxchecked" name="boxchecked" value="0" />

				<div class="card mx-3">
					<div class="card-body">
						<div class="d-flex gap-3">
							<div class="input-group">
								<input class="form-control search-input" name="search"
									id="search_text" value="${search != null ? search : ''}"
									placeholder="Search...">
								<button type="submit" class="btn btn-secondary btn-search">
									<i class="fa fa-search"></i>
								</button>
							</div>

							<div class="input-group">
								<select name="enabled" class="form-select">
									<option value="">All Status</option>
									<option value="1" ${param.enabled == '1' ? 'selected' : ''}>Active</option>
									<option value="0" ${param.enabled == '0' ? 'selected' : ''}>Inactive</option>
								</select> <select class="form-select" id="roles" name="role">
									<option value="">All Roles</option>
									<c:forEach var="role" items="${roles}">
										<option value="${role.name}"
											${role.name == selectedRole ? 'selected' : ''}>
											${role.name}</option>
									</c:forEach>
								</select> <input class="form-control" type="date" name="fromDate"
									id="fromDate" onchange="checkDates()" value="${fromDate}"
									placeholder="From Date"> <span class="input-group-text">to</span>
								<input class="form-control" type="date" name="toDate"
									id="toDate" onchange="checkDates()" value="${toDate}"
									placeholder="To Date">
								<button type="submit" class="btn btn-secondary">
									<i class="fa fa-filter"></i>
								</button>
							</div>

							<div>
								<button type="button" class="btn btn-search"
									style="background-color: #B197FC" onclick="refreshPage()">
									<i class="fa-solid fa-arrows-rotate" style="color: #ffffff;"></i>
								</button>
							</div>
							<a class="btn btn-primary text-nowrap btn-add"
								href="user/new.htm"> <i class="fa fa-plus me-2"></i>Add
							</a>
						</div>
						<div class="d-flex gap-3"></div>
						<div class="mt-3">
							<div class="table-responsive">
								<table class="table table-centered">
									<thead>
										<tr>
											<th width="30px" class="text-end">#</th>
											<th onclick="sortTable(1)">Name</th>
											<th onclick="sortTable(2)">Email</th>
											<th width="60px" class="text-center">Roles</th>
											<th width="60px" class="text-center">Status</th>
											<th width="160px" class="text-center">Updated</th>
											<th width="60px" class="text-center">Actions</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="user" items="${users}" varStatus="status">
											<tr>
												<td class="text-end">${user.id}</td>
												<td><a class="d-flex flex-nowrap align-items-center"
													href="user/edit/${user.id}.htm">
														<div>
															<img alt="User Avatar" src="${user.avatar}"
																class="rounded-circle bg-white border border-3 border-white"
																width="30px">
														</div>
														<div class="ms-3">
															<div class="fw-semibold custom-text">${user.fullname}</div>
														</div>
												</a></td>
												<td><div class="custom-text">${user.email}</div></td>
												<td class="text-center align-middle"><c:forEach
														var="role" items="${user.roles}">
														<span
															class="small text-uppercase text-success bg-opacity-10 rounded px-2 py-1">${role.name}</span>
													</c:forEach></td>
												<td class="text-end align-middle"><span
													class="small text-uppercase ${user.enabled == 1 ? 'text-success bg-opacity-10' : 'text-danger bg-opacity-10'} rounded px-2 py-1">
														${user.enabled == 1 ? 'Active' : 'Inactive'} </span></td>
												<td class="text-center align-middle"><fmt:formatDate
														value="${user.updated_at}" pattern="dd-MM-yyyy" /></td>

												<td class="text-end">
													<div class="d-flex gap-1">
														<a class="btn btn-rounded" href="user/edit/${user.id}.htm"><i
															class="fa fa-pencil"></i></a>
													</div>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>

							<c:if test="${totalPages > 1}">
								<nav aria-label="Page navigation example" class="mt-2">
									<ul class="pagination justify-content-center">
										<li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
											<a class="page-link"
											href="users.htm?page=${currentPage > 1 ? currentPage - 1 : 1}&size=${size}"
											aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
										</a>
										</li>
										<c:forEach var="i" begin="1" end="${totalPages}">
											<li class="page-item ${i == currentPage ? 'active' : ''}">
												<a class="page-link" href="users.htm?page=${i}&size=${size}">${i}</a>
											</li>
										</c:forEach>
										<li
											class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
											<a class="page-link"
											href="users.htm?page=${currentPage < totalPages ? currentPage + 1 : totalPages}&size=${size}"
											aria-label="Next"> <span aria-hidden="true">&raquo;</span>
										</a>
										</li>
									</ul>
								</nav>
							</c:if>
						</div>
					</div>
				</div>
			</form>

		</div>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
	<script>
		function refreshPage() {
			window.location.href = window.location.origin
					+ window.location.pathname;
		}

		function checkDates() {
			var fromDate = document.getElementById('fromDate');
			var toDate = document.getElementById('toDate');

			if (fromDate != null && toDate != null) {
				query.setParameter("fromDate", fromDate);
				query.setParameter("toDate", toDate);
			} else if (fromDate != null) {
				query.setParameter("fromDate", fromDate);
				query.setParameter("toDate", LocalDate.now());
			} else if (toDate != null) {
				query.setParameter("fromDate", LocalDate.of(1900, 1, 1));
				query.setParameter("toDate", toDate);
			}
		}
	</script>

</body>
</html>
