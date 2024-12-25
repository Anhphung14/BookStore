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
<title>Inventories Management</title>
<base href="${pageContext.servletContext.contextPath}/admin1337/">
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

	<jsp:include page="/WEB-INF/views/layouts/header.jsp" />

	<div class="main-content">
		<jsp:include page="/WEB-INF/views/layouts/navigation.jsp" />
		<div class="container-fluid d-flex flex-column"
			style="padding-left: 0px; padding-right: 0px;">
			<div class="container-fluid"
				style="margin-left: 0px; margin-right: 0px;">
				<div class="row g-3 mt-3">
					<div class="col">
						<h2 class="h3">Inventories management</h2>
						<p>Manage users, roles, permissions, and profile.</p>
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
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

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
<%-- 								href="${pageContext.request.contextPath}/product/new"> <i --%>
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
								<table class="table table-centered table-hover">
									<tr>
										<th width="15px"><input class="text-end	form-check-input"
											type="checkbox" id="toggle" name="toggle"
											onclick="checkAll()" /></th>
										<th width="30px" class="text-start">#</th>
										<th width="30px" class="text-center">Product Id</th>
										<th width="200px" class="text-center">Product</th>
										<th width="100px" class="text-center">Total quantity</th>
										<th width="160px" class="text-center">Stock quantity</th>
										<th width="80px" class="text-center">Update at</th>
										<th width="60px" class="text-center">Action</th>
									</tr>
									<c:forEach var="inventory" items="${listInventories}" varStatus="status">
										<tr>
											<td class="align-middle">
												<input type="checkbox" class="form-check-input" id="cb${status.index}" name="cid[]" value="${inventory.id}" onclick="isChecked(this.checked)">
											</td>
<%-- 											<td class="text-end">${(users.page - 1) * users.pageSize + status.index + 1}</td> --%>
											<td class="text-start align-middle">${inventory.id}</td>
											<td class="text-center align-middle">${inventory.book.id}</td>
											<td>
											    <a class="d-flex flex-nowrap align-items-center" style="text-decoration: none;" href="javascript:void(0);" >
											        <div>
											            <img alt="Product Thumbnail" src="${inventory.book.thumbnail}" class="bg-white border border-3 border-white" width="50px">
											        </div>
											        <div class="ms-3">
											            <div class="fw-semibold custom-text ellipsis">${inventory.book.title}</div>
											        </div>
											    </a>
											</td>

											<td class="text-center align-middle">${inventory.book.quantity}</td>
											<td class="text-center align-middle">${inventory.stock_quantity}</td>
											<td class="text-center align-middle"><fmt:formatDate value='${inventory.updated_at}' pattern='dd-MM-yyyy HH:mm' /></td>
											<td class="text-center">
												<div class="d-flex justify-content-center align-items-center gap-1">
													<a class="btn btn-rounded" href="inventory/edit/${inventory.id}"><i class="fa fa-pencil"></i></a>
<%-- 													<a class="btn btn-rounded"><i class="fa ${user.isActive ? 'fa-eye-slash' : 'fa-eye'}"></i></a> --%>
<!-- 													<a class="btn btn-rounded"><i class="fa fa-eye-slash"></i></a> -->
<!-- 													<a class="btn btn-rounded"><i class="fa fa-search"></i></a> -->
													<a class="btn btn-rounded" id="openModalButton" data-bs-toggle="modal" data-bs-target="#addQuantityModal" onclick="prepareAddQuantity(${inventory.book.id}, ${inventory.book.quantity}, ${inventory.stock_quantity}, ${inventory.id})">
													    <i class="fa fa-plus-circle" aria-hidden="true"></i>
													</a>
<%-- 													<a class="btn btn-rounded" data-bs-toggle="modal" data-bs-target="#deleteModal" onclick="prepareAddQuantity(${inventory.book.id}, ${inventory.book.quantity}, ${inventory.stock_quantity})"> --%>
<!-- 														<i class="fa fa-trash-alt"></i> -->
<!-- 													</a> -->
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
			                <form id="deleteForm" method="POST" action="product/delete">
			                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
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
			
			
			<!-- Modal Add Quantity -->
<div class="modal fade" id="addQuantityModal" tabindex="-1" aria-labelledby="addQuantityModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="addQuantityModalLabel">Add Quantity to Product</h5>
                <button type="button" id="closeModalButton" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <form id="addQuantityForm" method="POST" action="inventory/addQuantity">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <div class="modal-body">
                    <!-- Book ID (Read-Only) -->
                    <div class="mb-3">
                        <label for="bookId" class="form-label">Book ID</label>
                        <input type="text" class="form-control" id="bookId" name="bookId" readonly="readonly">
                    </div>

                    <!-- Total Quantity (Read-Only) -->
                    <div class="d-flex">
	                    <div class="mb-3 me-3" style="flex: 1;">
	                        <label for="totalQuantity" class="form-label">Total Quantity</label>
	                        <input type="text" class="form-control" id="totalQuantity" name="totalQuantity" readonly="readonly">
	                    </div>
	                    <div class="mb-3" style="flex: 1;">
	                        <label for="stockQuantity" class="form-label">Stock Quantity</label>
	                        <input type="text" min="0" class="form-control" id="stock_quantity" name="stock_quantity" readonly="readonly">
	                    </div>
	                </div>

                    <div class="mb-3">
                        <label for="bookId" class="form-label">Enter Quantity to add</label>
                        <input type="number" min="0" max="10000" class="form-control" id="addQuantity" name="addQuantity">
                    </div>
                    <c:if test="${not empty errorQuantity}">
                    	 <div class="text-danger">${errorQuantity}</div>
                    </c:if>
                    
                </div>
                <div class="modal-footer">
                    <button type="button" id="closeModalButton" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-primary">Save Quantity</button>
                </div>
                <input type="hidden" name="id" id="inventoryId">
            </form>
        </div>
    </div>
</div>



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
		            window.location.href = 'inventories';
		        }
		    });
		}
		
		function prepareAddQuantity(bookId, quantity, stock_quantity, inventoryId) {
			localStorage.setItem("bookId", bookId);
		    localStorage.setItem("totalQuantity", quantity);
		    localStorage.setItem("stock_quantity", stock_quantity);
		    localStorage.setItem("inventoryId", inventoryId);
			
			
		    document.getElementById("bookId").value = bookId;
		    document.getElementById("totalQuantity").value = quantity;
		    document.getElementById("stock_quantity").value = stock_quantity;
		    document.getElementById("inventoryId").value = inventoryId
		}
		
		document.querySelectorAll('#openModalButton').forEach(button => {
		    button.addEventListener('click', function() {
		        localStorage.setItem('isModalOpen', 'true');
		    });
		});

		// Khi đóng modal
		document.querySelectorAll('#closeModalButton').forEach(button => {
		    button.addEventListener('click', function() {
		        localStorage.setItem('isModalOpen', 'false');
		    });
		});

		// Kiểm tra trạng thái modal khi tải lại trang
		window.addEventListener('load', function() {
			const errorMess = "${errorQuantity}";
			
			if (!errorMess) {
				localStorage.setItem('isModalOpen', 'false');
			}
			
		    // Kiểm tra nếu modal đã được mở trước đó
		    const isModalOpen = localStorage.getItem('isModalOpen') === 'true';
		
		    if (isModalOpen) {
		        // Mở modal
		        var myModal = new bootstrap.Modal(document.getElementById('addQuantityModal'));
		        myModal.show();
		
		        // Binding lại giá trị vào các input trong modal
		        document.getElementById('bookId').value = localStorage.getItem('bookId');
		        document.getElementById('totalQuantity').value = localStorage.getItem('totalQuantity');
		        document.getElementById('stock_quantity').value = localStorage.getItem('stock_quantity');
		        document.getElementById('inventoryId').value = localStorage.getItem('inventoryId');
		    }
		});
		
	</script>
</body>
</html>