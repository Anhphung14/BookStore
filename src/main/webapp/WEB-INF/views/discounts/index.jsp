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
<base href="${pageContext.servletContext.contextPath}/admin1337/">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
	<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
	
	<link href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/css/toastr.min.css" rel="stylesheet">
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/js/toastr.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/app.js"defer></script>
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
						<h2 class="h3">Discounts management</h2>
						<p>Manage discount code, type, value,....</p>
					</div>
					<div class="col-auto d-none d-sm-block">
						<img class="page-icon" src="${pageContext.servletContext.contextPath}/resources/images/page.svg"
							width="120px" alt="Page Icon">
					</div>
				</div>
			</div>
			<form id="frm-admin" name="adminForm" action="discounts.htm" method="GET">

				<div class="card mx-3">
					<div class="card-body">
						<div class="d-flex gap-3">
						
						
							    <div class="input-group mb-3">
							        <input class="form-control" type="text" name="discountCode" id="discountCode" 
							               value="${discountCode}" placeholder="Discount Code" oninput="convertToUpperCase(this);">
							    </div>

							    
							    <div class="input-group mb-3">
							        <select class="form-control" name="discountType" id=""discountType">
							            <option value="">Discount Type</option>
							            <option value="percentage" ${discountType == 'percentage' ? 'selected' : ''}>Percentage</option>
							            <option value="amount" ${discountType == 'amount' ? 'selected' : ''}>Amount</option>
							        </select>
							    </div>
							
							    <!-- Tìm kiếm theo khoảng giá trị-->
							    <div class="input-group mb-3">
								    <input class="form-control" type="text" name="minValue" id="minValue" 
								           value="${minValue}" placeholder="Min Value" 
								           pattern="^\d+(\.\d+)?%?$" 
								           oninvalid="this.setCustomValidity('Discount value must be a number, and can optionally include a percentage sign (e.g., 10 or 10%).')">
								    <span class="input-group-text">to</span>
								    <input class="form-control" type="text" name="maxValue" id="maxValue" 
								           value="${maxValue}" placeholder="Max Value" 
								           pattern="^\d+(\.\d+)?%?$" 
								           oninvalid="this.setCustomValidity('Discount value must be a number, and can optionally include a percentage sign (e.g., 10 or 10%).')">
								</div>
							
								<div class="input-group mb-3">
				                    <input class="form-control" type="date" name="fromDate" id="fromDate" onchange="checkDates()"
				                           value="${fromDate}" placeholder="From Date">
				                     <span class="input-group-text">to</span>
				                     <input class="form-control" type="date" name="toDate" id="toDate" onchange="checkDates()"
				                           value="${toDate}" placeholder="To Date">
				                </div>
							    
							    <div class="input-group mb-3">
							        <select class="form-control" name="discountStatus" id="discountStatus">
							            <option value="">Discount Status</option>
							            <option value="active" ${discountStatus == 'active' ? 'selected' : ''}>Active</option>
							            <option value="inactive" ${discountStatus == 'inactive' ? 'selected' : ''}>Inactive</option>
							            <option value="expired" ${discountStatus == 'expired' ? 'selected' : ''}>Expired</option>
							        </select>
							    </div>
							
							    <!-- Nút tìm kiếm -->
							    <div class="d-flex justify-content-end mb-3">
							        <button type="submit" class="btn btn-primary">
							            <i class="fa fa-search"></i>
							        </button>
							    </div>
							    <div class="d-flex justify-content-end mb-3">
							        
							        <button type="button" class="btn btn-refresh" style="background-color: #B197FC" onclick="refreshPage()">
					                    <i class="fa-solid fa-arrows-rotate" style="color: #ffffff;"></i>
					                </button>
							    </div>
							    
							   
								
								<div class="d-flex justify-content-end mb-3">
							        <a class="btn btn-primary text-nowrap btn-add"
										href="discount/create.htm"> <i
										class="fa fa-plus me-2"></i>Add
									</a>
							    </div>

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
											<%-- <td><a class="d-flex flex-nowrap align-items-center"style="text-decoration: none; justify-content: center; align-items: center;" onclick="showDiscountDetails(this)">
													<div class="ms-3 ">
														<div class="fw-semibold custom-text ellipsis">${discount.code}</div>
													</div>
											</a></td> --%>	
											
											<td>
											    <a class="d-flex flex-nowrap align-items-center" style="text-decoration: none;" href="javascript:void(0);" 
											       data-id="${discount.id}"
											       data-code="${discount.code}"
											       data-discountType="${discount.discountType}"
											       data-discountValue="${discount.discountValue}"
											       data-applyTo="${discount.applyTo}"
											       data-startDate="<fmt:formatDate value='${discount.startDate}' pattern='yyyy-MM-dd\'T\'HH:mm' />"
											       data-endDate="<fmt:formatDate value='${discount.endDate}' pattern='yyyy-MM-dd\'T\'HH:mm' />"
											       data-minOrderValue=<fmt:formatNumber value="${discount.minOrderValue}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
											       data-maxUses="${discount.maxUses}"
											       data-used="${discount.getUsed() }"
											       data-category="${discount.getCategoryName() }"
											       data-subcategories="${discount.getSubcategoriesName() }"
											       data-status="${discount.status}"
											       data-updatedAt="<fmt:formatDate value='${discount.updatedAt}' pattern='yyyy-MM-dd\'T\'HH:mm' />"
											       data-createdAt="<fmt:formatDate value='${discount.createdAt}' pattern='yyyy-MM-dd\'T\'HH:mm' />"
											       onclick="showDiscountDetails(this)">
											    	<div class="ms-3 ">
														<div class="fw-semibold custom-text ellipsis">${discount.code}</div>
													</div>
													</a>
											    </td>						
											
											
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
<!-- 													<a class="btn btn-rounded" data-bs-toggle="modal" data-bs-target="#exampleModal"> -->
<!-- 													    <i class="fa fa-plus-circle" aria-hidden="true"></i> -->
<!-- 													</a> -->
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
					</div>
				</div>
			</form>

			<div class="modal fade" id="deleteModal" tabindex="-1" aria-labelledby="deleteModalLabel" aria-hidden="true">
			    <div class="modal-dialog">
			        <div class="modal-content">
			            <div class="modal-header">
			                <h5 class="modal-title" id="deleteModalLabel">Confirm Discount Deletion</h5>
			                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			            </div>
			            <div class="modal-body">
			                <!-- Form used to send POST request -->
			                <form id="deleteForm" method="POST" action="discount/delete.htm">
			                    <p>Are you sure you want to delete this Discount?</p>
			                    <p><strong>Discount Id:</strong> <span id="discountIdToDelete"></span></p>
			                    <p><strong>Discount code:</strong> <span id="discountTitleToDelete"></span></p>
			                    <input type="hidden" name="discount_id" id="discountIdInput">
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
			
			<div class="modal fade" id="discountDetailModal" tabindex="-1" aria-labelledby="discountDetailModalLabel" aria-hidden="true">
			    <div class="modal-dialog">
			        <div class="modal-content">
			            <div class="modal-header">
			                <h5 class="modal-title" id="discountDetailModalLabel">Discount Details</h5>
			                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			            </div>
			            <div class="modal-body">
			            	<div class="d-flex">
			                    <div class="mb-3 me-3" style="flex: 1;">
				                    <label for="code" class="form-label">Code</label>
				                    <input type="text" class="form-control" id="discountCode" readonly="readonly">
			                	</div>
			                    <div class="mb-3" style="flex: 1;">
			                        <label for="discountType" class="form-label">Discount Type</label>
			                        <input type="text" class="form-control" id="discountType" readonly="readonly">
			                    </div>
			                </div>
			                

			                <div class="d-flex">
				                <div class="mb-3 me-3">
				                    <label for="discountValue" class="form-label">Discount Value</label>
				                    <input type="text" class="form-control" id="discountValue" readonly="readonly">
				                </div>
			                    <div class="mb-3" style="flex: 1;">
			                        <label for="applyTo" class="form-label">Apply To</label>
			                        <input type="text" class="form-control" id="applyTo" readonly="readonly">
			                    </div>
			                </div>
			                
			                <div class="d-flex">
			                
			                    <div class="mb-3 me-2" style="flex: 1;">
			                        <label for="startDate" class="form-label">Start Date</label>
			                        <input type="datetime-local" class="form-control" id="startDate" readonly="readonly">
			                    </div>
			                    
			                    <div class="mb-3" style="flex: 1;">
			                        <label for="endDate" class="form-label">End Date</label>
			                        <input type="datetime-local" class="form-control" id="endDate" readonly="readonly">
			                    </div>
			                </div>
			                
			                <div class="d-flex">
			                	
			                    <div id="minOrderValueModal" class="mb-3 me-3" style="flex: 1;">
			                        <label for="minOrderValue" class="form-label">Min Order Value</label>
			                        <input type="text" class="form-control" id="minOrderValue" readonly="readonly">
			                    </div>
			                    
							    <div id="maxUsesModal" class="mb-3 me-3" style="flex: 1;">
							        <label for="maxUses" class="form-label">Max Uses</label>
							        <input type="text" class="form-control" id="maxUses" readonly="readonly">
							    </div>
							    
							    <div id="usedModal" class="mb-3" style="flex: 1;">
							        <label for="used" class="form-label">Used</label>
							        <input type="text" class="form-control" id="used" readonly="readonly">
							    </div>
							    
							    <div id="categoryModal" class="mb-3 me-3" style="flex: 1;">
							        <label for="category" class="form-label">Category</label>
							        <input type="text" class="form-control" id="category" readonly="readonly">
							    </div>
							    
							    <div id="subcategoriesModal" class="mb-3" style="flex: 1;">
							        <label for="subcategories" class="form-label">Subcategories</label>
							        <input type="text" class="form-control" id="subcategories" readonly="readonly">
							    </div>
							</div>
							
							    <div class="mb-3 me-3" style="flex: 1;">
							        <label for="status" class="form-label">Status</label>
							        <input type="text" class="form-control" id="status" readonly="readonly">
							    </div>
							
							<div class="d-flex">
			                    <div class="mb-3 me-2" style="flex: 1;">
			                        <label for="discountCreatedAt" class="form-label">Created at</label>
			                        <input type="datetime-local" class="form-control" id="discountCreatedAt" readonly="readonly">
			                    </div>
			                    <div class="mb-3" style="flex: 1;">
			                        <label for="discountUpdatedAt" class="form-label">Updated at</label>
			                        <input type="datetime-local" class="form-control" id="discountUpdatedAt" readonly="readonly">
			                    </div>
			                </div>
			                
			            </div>
			            <div class="modal-footer">
			                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
			            </div>
			        </div>
			    </div>
			</div>



		</div>

	</div>
	
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js">
	</script>
	
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


	function prepareDelete(bookId, bookTitle) {
	    // Hiển thị ID của sách trong modal
	    document.getElementById("discountIdToDelete").textContent = bookId;
	    document.getElementById("discountTitleToDelete").textContent = bookTitle;
	    document.getElementById("discountIdInput").value = bookId;

	}
	
	function showDiscountDetails(link) {
	    // Lấy thông tin từ thuộc tính data của link
	    var discountId = link.getAttribute('data-id');
		var discountCode = link.getAttribute('data-code');
        var discountType = link.getAttribute('data-discountType');
        var discountValue = link.getAttribute('data-discountValue');
        var applyTo = link.getAttribute('data-applyTo');
        var startDate = link.getAttribute('data-startDate');
        var endDate = link.getAttribute('data-endDate');
        var minOrderValue = link.getAttribute('data-minOrderValue');
        var maxUses = link.getAttribute('data-maxUses');
        var category = link.getAttribute('data-category');
        var subcategories = link.getAttribute('data-subcategories');
        console.log(subcategories.slice(1, -1));
        var used = link.getAttribute('data-used');
        var status = link.getAttribute('data-status');
        var updatedAt = link.getAttribute('data-updatedAt');
        var createdAt = link.getAttribute('data-createdAt');
	    
        if (applyTo === "categories") {
            // Ẩn các trường không cần thiết
            document.getElementById("minOrderValueModal").style.display = "none";
            document.getElementById("maxUsesModal").style.display = "none";
            document.getElementById("usedModal").style.display = "none";
            
            // Hiển thị category và subcategory
            document.getElementById("categoryModal").style.display = "block";
            document.getElementById("subcategoriesModal").style.display = "block";

            // Cập nhật giá trị category và subcategory vào các trường
            document.getElementById("category").value = category;
            document.getElementById("subcategories").value = subcategories.slice(1, -1);
        } else {
            // Nếu không phải categories, hiển thị lại các trường ban đầu
            document.getElementById("minOrderValueModal").style.display = "block";
            document.getElementById("maxUsesModal").style.display = "block";
            document.getElementById("usedModal").style.display = "block";
            
            // Ẩn các trường category và subcategory
           	document.getElementById("categoryModal").style.display = "none";
           	document.getElementById("subcategoriesModal").style.display = "none";
        }
        
        document.getElementById("discountCode").value = discountCode;
        document.getElementById("discountType").value = discountType;
        document.getElementById("discountValue").value = discountValue;
        document.getElementById("applyTo").value = applyTo;
        document.getElementById("startDate").value = startDate;
        document.getElementById("endDate").value = endDate;
        document.getElementById("minOrderValue").value = minOrderValue;
        document.getElementById("status").value = status;
        document.getElementById("maxUses").value = maxUses;
        document.getElementById("used").value = used;
        document.getElementById("discountCreatedAt").value = createdAt;
        document.getElementById("discountUpdatedAt").value = updatedAt;
	    // Điền thông tin vào modal
	    // Mở modal
	    var modal = new bootstrap.Modal(document.getElementById('discountDetailModal'));
	    modal.show();
	}
	
	function refreshPage() {
	    // Sử dụng window.location để reload lại trang mà không tham số
	    window.location.href = window.location.origin + window.location.pathname;
	}
	
	function convertToUpperCase(inputElement) {
	    // Đổi giá trị của input thành chữ in hoa
	    inputElement.value = inputElement.value.toUpperCase();
	}
	
	function checkDates() {
	    var fromDate = document.getElementById('fromDate');
	    var toDate = document.getElementById('toDate');

	    // Kiểm tra và set "required" nếu chỉ có một ô có giá trị
	    if (fromDate.value && !toDate.value) {
	        toDate.setAttribute('required', true);
	        toDate.setAttribute('min', fromDate.value);  // Set min cho toDate là fromDate
	    } else if (!fromDate.value && toDate.value) {
	        fromDate.setAttribute('required', true);
	        fromDate.setAttribute('max', toDate.value);  // Set max cho fromDate là toDate
	    } else {
	        fromDate.removeAttribute('required');
	        toDate.removeAttribute('required');
	        toDate.setAttribute('min', fromDate.value);
	        fromDate.setAttribute('max', toDate.value);
	    }
	}


	

	</script>
</body>
</html>