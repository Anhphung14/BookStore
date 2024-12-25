<%@page import="java.util.ArrayList"%>
<%@page import="bookstore.Entity.PermissionsEntity"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Permission management</title>
<base href="${pageContext.servletContext.contextPath}/admin1337/">
<script src="${pageContext.request.contextPath}/resources/js/app.js"
	defer></script>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
<link href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/css/toastr.min.css" rel="stylesheet">
<script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/js/toastr.min.js"></script>
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

.permission-badge {
        display: inline-block;
        margin: 0.2rem;
        padding: 0.3rem 0.6rem;
        border-radius: 0.3rem;
/*         background-color: #343a40; */
        background-color: #107896;
        color: #ffffff;
        font-size: 12px;
}

.permission-badge2 {
        display: inline-block; 
        margin: 0.2rem;     
        padding: 0.3rem 0.6rem;
        border-radius: 0.3rem;
        background-color: #343a40;
        color: #ffffff;
        font-size: 12px;
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
						<img class="page-icon" src="${pageContext.servletContext.contextPath}/resources/images/page.svg"
							width="120px" alt="Page Icon">
					</div>
				</div>
			</div>
			
			<div class="card mx-3 mb-3">
				<div class="table-responsive">
					<table class="table table-centered">
						<tr>
							<th>Supplier</th>
							<th>Category</th>
							<th>Product</th>
							<th>Discount</th>
							<th width="150px">Order</th>
							<th width="260px">Inventory</th>
							<th width="260px">Rating</th>
						</tr>
						
						<tr>
							<td>
					            <c:forEach var="permission" items="${supplierPermissions}">
					                <span class="permission-badge2">
					                    ${permission.name}
					                </span>
					            </c:forEach>
					        </td>
							<td>
					            <c:forEach var="permission" items="${categoryPermissions}">
					                <span class="permission-badge2">
					                    ${permission.name}
					                </span>
					            </c:forEach>
					        </td>
							<td>
					            <c:forEach var="permission" items="${productyPermissions}">
					                <span class="permission-badge2">
					                    ${permission.name}
					                </span>
					            </c:forEach>
					        </td>
							<td>
					            <c:forEach var="permission" items="${discountPermissions}">
					               <span class="permission-badge2">
					                    ${permission.name}
					                </span>
					            </c:forEach>
					        </td>
							<td>
					            <c:forEach var="permission" items="${orderPermissions}">
					               <span class="permission-badge2">
					                    ${permission.name}
					                </span>
					            </c:forEach>
					        </td>
							<td>
					            <c:forEach var="permission" items="${inventoryPermissions}">
					                <span class="permission-badge2">
					                    ${permission.name}
					                </span>
					            </c:forEach>
					        </td>
					        <td>
					            <c:forEach var="rating" items="${ratingPermissions}">
					                <span class="permission-badge2">
					                    ${rating.name}
					                </span>
					            </c:forEach>
					        </td>
						</tr>
						
					</table>
				</div>
			</div>
			
			<div class="card mx-3 mb-5">
				<div class="table-responsive">
					<table class="table table-centered">
						<tr>
							<th>User</th>
							<th>Role</th>
							<th>Permission</th>
						</tr>
						
						<tr>
							<td>
					            <c:forEach var="permission" items="${userPermissions}">
					                <span class="permission-badge2">
					                    ${permission.name}
					                </span>
					            </c:forEach>
					        </td>
							<td>
					            <c:forEach var="permission" items="${rolePermissions}">
					                <span class="permission-badge2">
					                    ${permission.name}
					                </span>
					            </c:forEach>
					        </td>
							<td>
					            <c:forEach var="permission" items="${permissionPermissions}">
					                <span class="permission-badge2">
					                    ${permission.name}
					                </span>
					            </c:forEach>
					        </td>
						</tr>
						
					</table>
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
<!-- 							<a class="btn btn-primary text-nowrap btn-add" -->
<!-- 								href="permission/new"> <i -->
								
<!-- 								class="fa fa-plus me-2"></i>Add -->
<!-- 							</a> -->
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
<!-- 										<th width="30px"><input class="form-check-input" -->
<!-- 											type="checkbox" id="toggle" name="toggle" -->
<!-- 											onclick="checkAll()" /></th> -->
										<th width="30px" class="text-start">#</th>
										<th width="150px" class="text-start">Roles</th>
										<th width="600px" class="text-start">Permissions</th>
<!-- 										<th width="60px" class="text-center">Status</th> -->
<!-- 										<th width="180px" class="text-center">Updated</th> -->
										<th width="60px" class="text-center">Actions</th>
									</tr>
								<c:forEach var="role" items="${roles}" varStatus="status">
									<tr>
<!-- 										<td><input type="checkbox" class="form-check-input" -->
<!-- 											id="cb1" name="cid[]" value="1" -->
<!-- 											onclick="isChecked(this.checked)"> -->
<!-- 										</td> -->
										<td class="text-start">${role.id}</td>
										<td>
											<span class="bg-danger text-white" style="font-size: 12px; padding: 0.2rem; font-weight: bold;">${role.name}</span>
											<div class="text-muted small">${role.description}</div>
										</td>
										
										<td>
								            <c:forEach var="permission" items="${role.permissions}">
								                <span class="permission-badge">
								                    ${permission.name}
								                </span>
								            </c:forEach>
								        </td>
										<td class="text-end">
											<div class="d-flex justify-content-center align-items-center gap-1">
												<a class="btn btn-rounded" href="permission/edit/${role.id}.htm"> <i
													class="fa fa-pencil"></i>
<!-- 												</a> <a class="btn btn-rounded"> <i class="fa fa-eye-slash"></i> -->
<!-- 												</a> <a class="btn btn-rounded"> <i class="fa fa-arrow-up"></i> -->
<!-- 												</a> <a class="btn btn-rounded"> <i class="fa fa-arrow-down"></i> -->
<!-- 												</a> <a class="btn btn-rounded"> <i class="fa fa-trash-alt"></i> -->
												</a>
											</div>
										</td>
									</tr>
								</c:forEach>
								</table>
							</div>
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
			    "closeButton": true, // Cho phép nút đóng
			    "debug": false,
			    "newestOnTop": true,
			    "progressBar": true, // Hiển thị thanh tiến trình
			    "positionClass": "toast-top-right", // Vị trí hiển thị thông báo
			    "preventDuplicates": true,
			    "onclick": null,
			    "showDuration": "200", // Thời gian hiển thị
			    "hideDuration": "1000",
			    "timeOut": "5000", // Thời gian thông báo sẽ tự động ẩn
			    "extendedTimeOut": "1000", // Thời gian mở rộng nếu hover vào
			    "showEasing": "swing",
			    "hideEasing": "linear",
			    "showMethod": "fadeIn",
			    "hideMethod": "fadeOut"
			};
		if (alertMessage) {
		    // Kiểm tra kiểu thông báo
		    if (alertType === "success") {
		        toastr.success(alertMessage, "Success");
		    } else if (alertType === "error") {
		        toastr.error(alertMessage, "Error");
		    }
		}
	</script>
</body>
</html>
