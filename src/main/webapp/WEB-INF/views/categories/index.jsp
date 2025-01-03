<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Categories Management</title>
<base href="${pageContext.servletContext.contextPath}/admin1337/">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

<link
	href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/css/toastr.min.css"
	rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/js/toastr.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/app.js"
	defer></script>
<script
	src="${pageContext.request.contextPath}/resources/js/confirmBox.js"></script>

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
						<h2 class="h3">Categories Management</h2>
						<p>Manage categories for products or content.</p>
					</div>
					<div class="col-auto d-none d-sm-block">
						<img class="page-icon" src="${pageContext.servletContext.contextPath}/resources/images/page.svg"
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
								<input class="form-control search-input me-2" name="search"
									id="search_text" value="${search}"
									placeholder="Search categories...">
								<button type="submit" class="btn btn-secondary btn-search">
									<i class="fa fa-search"></i>
								</button>
							</div>
							<a class="btn btn-primary text-nowrap btn-add"
								href="category/new">
								<i class="fa fa-plus me-2"></i>Add
							</a>
						</div>

						<div class="mt-3">
							<div
								class="table-actionbar bg-primary bg-opacity-10 p-2 ps-3 d-none">
								<div class="d-flex justify-content-between gap-3">
									<div class="selected-count align-self-center"></div>
									<!-- <div class="d-flex gap-1">
										<a class="btn btn-rounded"> <i class="fa fa-trash-alt"></i>
										</a>
									</div> -->
								</div>
							</div>
							<div class="table-responsive">
								<table class="table table-centered">
									<tr>
										<!-- <th width="30px"><input class="form-check-input"
											type="checkbox" id="toggle" name="toggle"
											onclick="checkAll()" /></th> -->
										<th width="30px" class="text-end">#</th>
										<th>Name</th>
										<th width="160px" class="text-center">Updated</th>
										<th width="60px">Subcategories</th>
										<th width="60px" class="text-center">Actions</th>
									</tr>
									<c:forEach var="category" items="${categories}"
										varStatus="status">
										<tr>
										<%-- 	<td><input type="checkbox" class="form-check-input"
												id="cb${status.index}" name="cid[]" value="${category.id}"
												onclick="isChecked(this.checked)"></td> --%>
											<td class="text-end">${category.id}</td>
											<td>${category.name}</td>
											<td class="text-center align-middle">${category.updated_at}</td>
											<td class="text-center"><c:if
													test="${not empty category.subcategoriesEntity}">
													<div class="dropdown">
														<button class="btn btn-light dropdown-toggle"
															type="button" id="dropdownMenuButton"
															data-bs-toggle="dropdown" aria-expanded="false">
															<i class="fa fa-layer-group"></i>

														</button>
														<ul class="dropdown-menu p-3 shadow"
															aria-labelledby="dropdownMenuButton">
															<c:forEach var="subcategory"
																items="${category.subcategoriesEntity}">
																<li><a class="dropdown-item" href="#"></i>${subcategory.name}
																</a></li>
															</c:forEach>
														</ul>
													</div>
												</c:if></td>
											<td class="text-end">
												<div class="d-flex gap-1">
													<a class="btn btn-rounded"
														href="category/edit/${category.id}">
														<i class="fa fa-pencil"></i>
													</a> 
												<%-- 	<a class="btn btn-rounded btn-delete"
														href="javascript:void(0);"
														data-url="category/delete/${category.id}">
														<i class="fa fa-trash-alt"></i>
													</a> --%>
												</div>
											</td>
										</tr>
									</c:forEach>
								</table>
							</div>
							<c:if test="${totalPages > 1}">
								<nav aria-label="Page navigation example" class="mt-2">
									<ul class="pagination justify-content-center">
										<!-- Liên kết đến trang trước -->
										<li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
											<a class="page-link"
											href="categories?page=${currentPage > 1 ? currentPage - 1 : 1}&size=${size}"
											aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
										</a>
										</li>

										<!-- Liên kết đến từng trang -->
										<c:forEach var="i" begin="1" end="${totalPages}">
											<li class="page-item ${i == currentPage ? 'active' : ''}">
												<a class="page-link"
												href="categories?page=${i}&size=${size}">${i}</a>
											</li>
										</c:forEach>

										<!-- Liên kết đến trang tiếp theo -->
										<li
											class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
											<a class="page-link"
											href="categories?page=${currentPage < totalPages ? currentPage + 1 : totalPages}&size=${size}"
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
	<script type="text/javascript">
		const alertMessage = "${alertMessage}";
		const alertType = "${alertType}";

		toastr.options = {
			"closeButton" : true,
			"debug" : false,
			"newestOnTop" : true,
			"progressBar" : true,
			"positionClass" : "toast-top-right",
			"preventDuplicates" : true,
			"onclick" : null,
			"showDuration" : "200",
			"hideDuration" : "1000",
			"timeOut" : "5000",
			"extendedTimeOut" : "1000",
			"showEasing" : "swing",
			"hideEasing" : "linear",
			"showMethod" : "fadeIn",
			"hideMethod" : "fadeOut"
		};
		if (alertMessage) {
			if (alertType === "success") {
				toastr.success(alertMessage, "Success");
			} else if (alertType === "error") {
				toastr.error(alertMessage, "Error");
			}
		}
	</script>
</body>
</html>
