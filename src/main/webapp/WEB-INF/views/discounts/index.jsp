<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="f"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Discounts Management</title>
<base href="${pageContext.servletContext.contextPath}/">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
	<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
	<script src="${pageContext.request.contextPath}/resources/js/app.js"
	defer></script>
	
	<link href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/css/toastr.min.css" rel="stylesheet">
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
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

.table-centered tbody tr:hover { background-color: #f0f0f0;}

.ellipsis {
    white-space: nowrap;          /* Không xuống dòng */
    overflow: hidden;             /* Ẩn phần văn bản vượt quá vùng chứa */
    text-overflow: ellipsis;      /* Thêm dấu "..." khi văn bản bị cắt */
    max-width: 200px;             /* Đặt chiều rộng tối đa của vùng chứa */
    display: block;               /* Đảm bảo nó là một khối để áp dụng được */
}
</style>
</head>
<body>
		<div class="main-content">
		<jsp:include page="/WEB-INF/views/layouts/navigation.jsp" />
		<div class="container-fluid d-flex flex-column"
			style="padding-left: 0px; padding-right: 0px;">
			<div class="container-fluid"
				style="margin-left: 0px; margin-right: 0px;">
				<div class="row g-3 mt-3">
					<div class="col">
						<h2 class="h3">Discounts management</h2>
						<p>Manage discount code, type, value,....</p>
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
								href="${pageContext.request.contextPath}/discount/create.htm"> <i
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
										<th width="100px" class="text-center">Code</th>
										<th width="64px" class="text-center">Type</th>
										<th width="90px" class="text-center">Value</th>
										<!-- <th width="50px" class="text-center">Inventory</th> -->
										<th width="100px" class="text-center">Start Date</th>
										<th width="100px" class="text-center">End Date</th>
										<th width="50px" class="text-center">Status</th>
										<th width="60px" class="text-center">Action</th>
									</tr>
									<c:forEach var="discount" items="${listDiscounts}" varStatus="status">
										<tr>
											<td class="align-middle">
												<input type="checkbox" class="form-check-input" id="cb${status.index}" name="cid[]" value="${discount.id}" onclick="isChecked(this.checked)">
											</td>
<%-- 											<td class="text-end">${(users.page - 1) * users.pageSize + status.index + 1}</td> --%>
											<td class="text-start align-middle">${discount.id}</td>
											<td><a class="d-flex flex-nowrap align-items-center"style="text-decoration: none; justify-content: center; align-items: center;"href="discount/edit/${discount.id}.htm">
													<div class="ms-3 ">
														<div class="fw-semibold custom-text ellipsis">${discount.code}</div>
													</div>
											</a></td>							
											
											
											<td class="text-center align-middle">${discount.discountType}</td>
											<td class="text-center align-middle">
											    <c:choose>
											        <%-- Kiểm tra nếu discountType là 'percentage' --%>
											        <c:when test="${discount.discountType == 'percentage'}">
											            <%-- Hiển thị giá trị theo phần trăm --%>
											            <fmt:formatNumber value="${discount.discountValue / 100}" type="percent" maxFractionDigits="0" />
											        </c:when>
											        
											        <%-- Kiểm tra nếu discountType là 'amount', 'product-based', hoặc 'fixed' --%>
											        <c:otherwise>
											            <%-- Hiển thị giá trị dưới dạng tiền tệ (₫) --%>
											            <fmt:formatNumber value="${discount.discountValue}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
											        </c:otherwise>
											    </c:choose>
											</td>

											<td class="text-center align-middle"><fmt:formatDate value='${discount.startDate}' pattern='dd-MM-yyyy HH:mm' /></td>
											<td class="text-center align-middle"><fmt:formatDate value='${discount.endDate}' pattern='dd-MM-yyyy HH:mm' /></td>
											
											<c:choose>
											    <c:when test="${discount.status == 'active'}">
											        <td class="text-center align-middle">
											            <span class="small text-uppercase text-success bg-success bg-opacity-10 rounded px-2 py-1">Active</span>
											        </td>
											    </c:when>
											
											    <c:when test="${discount.status == 'inactive'}">
											        <td class="text-center align-middle">
											            <span class="small text-uppercase text-warning bg-warning bg-opacity-10 rounded px-2 py-1">Inactive</span>
											        </td>
											    </c:when>
											
											    <c:when test="${discount.status == 'expired'}">
											        <td class="text-center align-middle">
											            <span class="small text-uppercase text-danger bg-danger bg-opacity-10 rounded px-2 py-1">Expired</span>
											        </td>
											    </c:when>
											
											    <c:otherwise>
											        <td class="text-center align-middle">
											            <span class="small text-uppercase text-muted bg-muted bg-opacity-10 rounded px-2 py-1">Unknown</span>
											        </td>
											    </c:otherwise>
											</c:choose>
											
											<td class="text-center">
												<div class="d-flex justify-content-center align-items-center gap-1">
													<a class="btn btn-rounded" href="discount/edit/${discount.id}.htm"><i class="fa fa-pencil"></i></a>
<%-- 													<a class="btn btn-rounded"><i class="fa ${user.isActive ? 'fa-eye-slash' : 'fa-eye'}"></i></a> --%>
													<!-- <a class="btn btn-rounded"><i class="fa fa-eye-slash"></i></a> -->
													<!-- <a class="btn btn-rounded"><i class="fa fa-search"></i></a> -->
													<a class="btn btn-rounded" data-bs-toggle="modal" data-bs-target="#exampleModal">
													    <i class="fa fa-plus-circle" aria-hidden="true"></i>
													</a>
													<a class="btn btn-rounded" data-bs-toggle="modal" data-bs-target="#deleteModal" onclick="prepareDelete(${discount.id}, '${discount.code}')">
														<i class="fa fa-trash-alt"></i>
													</a>
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

			<div class="modal fade" id="deleteModal" tabindex="-1" aria-labelledby="deleteModalLabel" aria-hidden="true">
			    <div class="modal-dialog">
			        <div class="modal-content">
			            <div class="modal-header">
			                <h5 class="modal-title" id="deleteModalLabel">Confirm Book Deletion</h5>
			                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			            </div>
			            <div class="modal-body">
			                <!-- Form used to send POST request -->
			                <form id="deleteForm" method="POST" action="/bookstore/discount/delete.htm">
			                    <p>Are you sure you want to delete this book?</p>
			                    <p><strong>Book Id:</strong> <span id="bookIdToDelete"></span></p>
			                    <p><strong>Book title:</strong> <span id="bookTitleToDelete"></span></p>
			                    <!-- Hidden input to send bookId -->
			                    <input type="hidden" name="bookId" id="bookIdInput">
			                </form>
			            </div>
			            <div class="modal-footer">
			                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
			                <!-- Submit button to send form -->
			                <button type="submit" class="btn btn-danger" form="deleteForm">Delete</button>
			            </div>
			        </div>
			    </div>
			</div>



		</div>

	</div>
	
	<script type="text/javascript">
	/* const alertMessage = "${alertMessage}";
	const alertType = "${alertType}";

	if (alertMessage) {
	    Swal.fire({
	        icon: alertType, 
	        title: alertType === "success" ? "Success" : "Error",
	        text: alertMessage,
	        confirmButtonText: "OK"
	    }).then((result) => {
	        if (result.isConfirmed) {
	            Swal.close();
	        }
	    });
	} */
	
	
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