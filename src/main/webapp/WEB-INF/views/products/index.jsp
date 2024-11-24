<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Products Management</title>
<base href="${pageContext.servletContext.contextPath}/">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
	<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
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

.table-centered tbody tr:hover { background-color: #f0f0f0;}
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
						<h2 class="h3">Products management</h2>
						<p>Manage users, roles, permissions, and profile.</p>
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
								href="${pageContext.request.contextPath}/product/new.htm"> <i
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
								<table class="table table-centered table-hover">
									<tr>
										<th width="15px"><input class="text-end	form-check-input"
											type="checkbox" id="toggle" name="toggle"
											onclick="checkAll()" /></th>
										<th width="30px" class="text-start">#</th>
										<th width="200x" class="text-center">Product</th>
										<th width="30px" class="text-center">Status</th>
										<th width="50px" class="text-center">Inventory</th>
										<th width="160px" class="text-center">Category</th>
										<th width="80px" class="text-center">Update at</th>
										<th width="60px" class="text-center">Action</th>
									</tr>
									<c:forEach var="book" items="${listBooks}" varStatus="status">
										<tr>
											<td class="align-middle">
												<input type="checkbox" class="form-check-input" id="cb${status.index}" name="cid[]" value="${book.id}" onclick="isChecked(this.checked)">
											</td>
<%-- 											<td class="text-end">${(users.page - 1) * users.pageSize + status.index + 1}</td> --%>
											<td class="text-start align-middle">${book.id}</td>
											<td><a class="d-flex flex-nowrap align-items-center"
												style="text-decoration: none;"
												href="product/edit/${book.id}.htm">
													<div>
														<img alt="Product Thumbnail" src="${book.thumbnail}" class="rounded-circle bg-white border border-3 border-white" width="50px">
													</div>
													<div class="ms-3">
														<div class="fw-semibold custom-text">${book.title}</div>
<%-- 														<div class="small custom-text">${user.email}</div> --%>
													</div>
											</a></td>
											<td class="text-center align-middle"><span class="small text-uppercase text-success bg-success bg-opacity-10 rounded px-2 py-1">active</span></td>
<%-- 											<td class="text-end align-middle"><span class="small text-uppercase ${user.isActive ? 'text-success' : 'text-danger'} bg-opacity-10 rounded px-2 py-1">${user.isActive ? 'Active' : 'Inactive'}</span></td> --%>
											<c:choose>
												<c:when test="${quantities[status.index] > 10}">
													<td class="text-center align-middle"><span class="small text-success bg-opacity-10 rounded px-2 py-1">${quantities[status.index]} in stock</span></td>												
												</c:when>
												<c:otherwise>
													<td class="text-center align-middle"><span class="small text-danger bg-opacity-10 rounded px-2 py-1">${quantities[status.index]} in stock</span></td>
												</c:otherwise>
											</c:choose>
											
											
											<td class="text-center align-middle">${book.category.name}</td>
											<td class="text-center align-middle"><fmt:formatDate value='${book.updated_at}' pattern='dd-MM-yyyy HH:mm' /></td>
											<td class="text-center">
												<div class="d-flex justify-content-center align-items-center gap-1">
													<a class="btn btn-rounded" href="product/edit/${book.id}.htm"><i class="fa fa-pencil"></i></a>
<%-- 													<a class="btn btn-rounded"><i class="fa ${user.isActive ? 'fa-eye-slash' : 'fa-eye'}"></i></a> --%>
													<a class="btn btn-rounded"><i class="fa fa-eye-slash"></i></a>
													<a class="btn btn-rounded"><i class="fa fa-search"></i></a>
													<a class="btn btn-rounded"><i class="fa fa-trash-alt"></i></a>
												</div>
											</td>
										</tr>
										
										
									</c:forEach>
								</table>
							</div>
						</div>
<%-- 						<div>${users.links}</div> --%>
					</div>
				</div>
			</form>

		</div>

	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js">
	</script>
	<script type="text/javascript">
		const alertMessage = "${alertMessage}";
		const alertType = "${alertType}";
		
		if (alertMessage) {
		    Swal.fire({
		        icon: alertType, 
		        title: alertType === "success" ? "Success" : "Error",
		        text: alertMessage,
		        confirmButtonText: "OK"
		    }).then((result) => {
		        if (result.isConfirmed && alertType === "success") {
		            window.location.href = '${pageContext.servletContext.contextPath}/products.htm';
		        }
		    });
		}
	</script>
</body>
</html>