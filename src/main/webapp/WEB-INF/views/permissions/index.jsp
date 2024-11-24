<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Permission management</title>
<base href="${pageContext.servletContext.contextPath}/">
<script src="${pageContext.request.contextPath}/resources/js/app.js"
	defer></script>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">

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
						<h2 class="h3">Permission management</h2>
						<p>Here you can view all permissions, see the roles assigned to each permission</p>
					</div>
					<div class="col-auto d-none d-sm-block">
						<img class="page-icon" src="resources/images/page.svg"
							width="120px" alt="Page Icon">
					</div>
				</div>
			</div>
			<form id="frm-admin" name="adminForm" action="" method="POST">
				<input type="hidden" id="task" name="task" value="${param.task}">
				<input type="hidden" id="sortby" name="sortby"
					value="${param.sortby != null ? param.sortby : 'updated_at'}" /> <input
					type="hidden" id="orderby" name="orderby"
					value="${param.orderby != null ? param.orderby : 'desc'}" /> <input
					type="hidden" id="boxchecked" name="boxchecked" value="0" />

				<div class="card mx-3">
					<div class="card-body">
						<div class="d-flex gap-3">
							<div class="input-group">
								<input class="form-control search-input" name="search_text"
									id="search_text" value="${param.search_text}"
									placeholder="Search ...">
								<button type="button" class="btn btn-secondary btn-search">
									<i class="fa fa-search"></i>
								</button>
							</div>
							<a class="btn btn-primary text-nowrap btn-add"
								href="${pageContext.request.contextPath}/permission/new"> <i
								class="fa fa-plus me-2"></i>Add
							</a>
						</div>

						<div class="mt-3">
							<div
								class="table-actionbar bg-primary bg-opacity-10 p-2 ps-3 d-none">
								<div class="d-flex justify-content-between gap-3">
									<div class="selected-count align-self-center"></div>
									<div class="d-flex gap-1">
										<a class="btn btn-rounded"> <i class="fa fa-eye"></i></a> <a
											class="btn btn-rounded"> <i class="fa fa-eye-slash"></i>
										</a> <a class="btn btn-rounded"> <i class="fa fa-trash-alt"></i>
										</a>
									</div>
								</div>
							</div>

							<div class="table-responsive">
								<table class="table table-centered">
									<tr>
										<th width="30px"><input class="form-check-input"
											type="checkbox" id="toggle" name="toggle"
											onclick="checkAll()" /></th>
										<th width="30px" class="text-end">#</th>
										<th>Name</th>
										<th width="60px">Roles</th>
										<th width="60px" class="text-end">Status</th>
										<th width="160px" class="text-center">Updated</th>
										<th width="60px">Actions</th>
									</tr>
									<%--          <c:forEach var="user" items="${users}" varStatus="status"> --%>
									<tr>
										<td><input type="checkbox" class="form-check-input"
											id="cb1" name="cid[]" value="1"
											onclick="isChecked(this.checked)"></td>
										<td class="text-end">1</td>
										<td>
										<span class="text-decoration-underline">Edit Product</span>
										<div class="text-muted small">Permission edit product</div>
										</td>
										<td class="text-center align-middle"><span
											class="small text-uppercase text-success bg-success bg-opacity-10 rounded px-2 py-1">admin</span>
										</td>
										<td class="text-end align-middle"><span
											class="small text-uppercase text-success bg-opacity-10 rounded px-2 py-1">Active</span>
										</td>
										<td class="text-end align-middle">22:00 14/11/2024</td>
										<td class="text-end">
											<div class="d-flex gap-1">
												<a class="btn btn-rounded" href="/user/edit/1"> <i
													class="fa fa-pencil"></i>
												</a> <a class="btn btn-rounded"> <i class="fa fa-eye-slash"></i>
												</a> <a class="btn btn-rounded"> <i class="fa fa-arrow-up"></i>
												</a> <a class="btn btn-rounded"> <i class="fa fa-arrow-down"></i>
												</a> <a class="btn btn-rounded"> <i class="fa fa-trash-alt"></i>
												</a>
											</div>
										</td>
									</tr>
									<%--        </c:forEach> --%>
								</table>
							</div>
						</div>
						<div>${users.links}</div>
					</div>
				</div>
			</form>

		</div>

	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
