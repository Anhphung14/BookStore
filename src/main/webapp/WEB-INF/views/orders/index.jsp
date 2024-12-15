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
<title>Orders Management</title>
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
						<h2 class="h3">Orders management</h2>
						<p>Manage orders....</p>
					</div>
					<div class="col-auto d-none d-sm-block">
						<img class="page-icon" src="${pageContext.request.contextPath}/resources/images/page.svg"
							width="120px" alt="Page Icon">
					</div>
				</div>
			</div>
			<form id="frm-admin" name="adminForm" action="orders.htm" method="GET">
				<!-- Tìm kiếm theo tên khách hàng -->
				<div class="card mx-3">
					<div class="card-body">
						<div class="d-flex gap-3">
							 <div class="input-group">
			                    <input class="form-control" name="customerName" id="customer_name" 
			                           value="${customerName}" placeholder="Customer Name">
			                </div>

                <!-- Tìm kiếm theo ngày tạo -->
                <div class="input-group">
                    <input class="form-control" type="date" name="fromDate" id="fromDate" onchange="checkDates()"
                           value="${fromDate}" placeholder="From Date">
                     <span class="input-group-text">to</span>
                     <input class="form-control" type="date" name="toDate" id="toDate" onchange="checkDates()"
                           value="${toDate}" placeholder="To Date">
                </div>

                <!-- Tìm kiếm theo khoảng giá -->
                <div class="input-group">
                    <input class="form-control" type="number" name="minPrice" id="min_price" 
                           value="${param.min_price}" placeholder="Min Price">
                    <span class="input-group-text">to</span>
                    <input class="form-control" type="number" name="maxPrice" id="max_price" 
                           value="${minPrice}" placeholder="Max Price">
                </div>

                <!-- Tìm kiếm theo trạng thái đơn hàng -->
                <div class="input-group">
                    <select class="form-control" name="paymentStatus" id="order_status">
                        <option value="">Payment Status</option>
                        <option value="Chưa thanh toán" ${paymentStatus == 'Chưa thanh toán' ? 'selected' : ''}>Chưa thanh toán</option>
                        <option value="Đã thanh toán" ${paymentStatus == 'Đã thanh toán' ? 'selected' : ''}>Đã thanh toán</option>
                    </select>
                </div>
                
                 <div class="input-group">
                    <select class="form-control" name="orderStatus" id="order_status">
                        <option value="">Order Status</option>
                        <option value="Chờ xác nhận" ${orderStatus == 'Chờ xác nhận' ? 'selected' : ''}>Chờ xác nhận</option>
                        <option value="Xác nhận đơn hàng" ${orderStatus == 'Xác nhận đơn hàng' ? 'selected' : ''}>Xác nhận đơn hàng</option>
                        <option value="Đang giao hàng" ${orderStatus == 'Đang giao hàng' ? 'selected' : ''}>Đang giao hàng</option>
                        <option value="Đã hoàn thành" ${orderStatus == 'Đã hoàn thành' ? 'selected' : ''}>Đã hoàn thành</option>
                        <option value="Huỷ đơn hàng" ${orderStatus == 'Huỷ đơn hàng' ? 'selected' : ''}>Huỷ đơn hàng</option>
                    </select>
                </div>
                

                <!-- Nút tìm kiếm -->
                <button type="submit" class="btn btn-info btn-search" style="background-color: #74C0FC">
                    <i class="fa fa-search" style="color: #ffffff;"></i> 
                </button>
                <button type="button" class="btn btn-search" style="background-color: #B197FC" onclick="refreshPage()">
                    <i class="fa-solid fa-arrows-rotate" style="color: #ffffff;"></i>
                </button>

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
										<th width="10%" class="text-center">Created At</th>
										<th width="64px" class="text-center">Customer name</th>
										<th width="90px" class="text-center">Total Price</th>
										<th width="100px" class="text-center">Payment method</th>
										<th width="100px" class="text-center">Payment status</th>
										<th width="50px" class="text-center">Order status</th>
										<th width="60px" class="text-center">Update status</th>
										<th width="60px" class="text-center"></th>
									</tr>
									<c:forEach var="order" items="${listOrders}" varStatus="status">
										<tr>
											<td class="align-middle">
												<input type="checkbox" class="form-check-input" id="cb${status.index}" name="cid[]" value="${order.id}" onclick="isChecked(this.checked)">
											</td>
<%-- 											<td class="text-end">${(users.page - 1) * users.pageSize + status.index + 1}</td> --%>
											<td class="text-start align-middle">${order.id}</td>
											
											<td>
											    <a class="d-flex flex-nowrap align-items-center" style="text-decoration: none;" href="javascript:void(0);">
											    	<div class="ms-3 ">
														<div class="fw-semibold custom-text ellipsis"><fmt:formatDate value='${order.createdAt}' pattern='dd-MM-yyyy HH:mm' /></div>
													</div>
												</a>
										   </td>						
											
											
											<td class="text-center align-middle">${order.user.fullname}</td>

											<td class="text-center align-middle"><fmt:formatNumber value="${order.totalPrice}" type="currency" maxFractionDigits="0" currencySymbol="₫"/></td>
											<td class="text-center align-middle">${order.paymentMethod }</td>
											<td class="text-center align-middle" style="width: 100px">
											    <span class="small
											        <c:choose>
											            <c:when test="${order.paymentStatus == 'Chưa thanh toán'}">text-danger bg-danger</c:when>
											            <c:when test="${order.paymentStatus == 'Đã hoàn tiền'}">text-warning bg-warning</c:when>
											            <c:otherwise>text-success bg-success</c:otherwise>
											        </c:choose> 
											        bg-opacity-10 rounded px-2 py-1">
											        ${order.paymentStatus}
											    </span>
											</td>

											<td class="text-center align-middle" style="width: 100px">
											    <span class="small 
											        <c:choose>
											            <c:when test="${order.orderStatus == 'Chờ xác nhận'}">text-warning bg-warning</c:when>
											            <c:when test="${order.orderStatus == 'Huỷ đơn hàng'}">text-danger bg-danger</c:when>
											            <c:otherwise>text-success bg-success</c:otherwise>
											        </c:choose> 
											        bg-opacity-10 rounded px-2 py-1">
											        ${order.orderStatus}
											    </span>
											</td>

											
											
											<td class="text-center align-middle" style="width: 100px">
											
											        <!-- Nút sẽ thay đổi tùy thuộc vào trạng thái đơn hàng -->
											        <c:choose>
											            <c:when test="${order.orderStatus == 'Chờ xác nhận'}">
											                <button type="button" name="orderStatus"  class="small text-primary bg-primary bg-opacity-10 rounded px-2 py-1" 
											                onclick="updateOrderStatus(${order.id}, 'Xác nhận đơn hàng')">
											                    Xác nhận đơn hàng
											                </button>
											            </c:when>
											            <c:when test="${order.orderStatus == 'Xác nhận đơn hàng'}">
											                <button type="button" name="orderStatus" class="small text-warning bg-warning bg-opacity-10 rounded px-2 py-1"
											                onclick="updateOrderStatus(${order.id}, 'Đang giao hàng')">
											                    Đang giao hàng
											                </button>
											            </c:when>
											            <c:when test="${order.orderStatus == 'Đang giao hàng'}">
											                <button type="button" name="orderStatus"class="small text-info bg-info bg-opacity-10 rounded px-2 py-1" 
											                onclick="updateOrderStatus(${order.id}, 'Hoàn thành')">
											                    Hoàn thành
											                </button>
											            </c:when>
											            <c:when test="${order.orderStatus == 'Hoàn thành'}">
											                <button type="button" class="small text-secondary bg-secondary bg-opacity-10 rounded px-2 py-1" disabled>
											                    Đã hoàn thành
											                </button>
											            </c:when>
											            <c:when test="${order.orderStatus == 'Huỷ đơn hàng'}">
											                <button type="button" class="small text-secondary bg-secondary bg-opacity-10 rounded px-2 py-1" disabled>
											                    Đã huỷ đơn hàng
											                </button>
											            </c:when>
											        </c:choose>
											</td>
											
											<td class="text-center" style="width: 10%">
												<div class="d-flex justify-content-center align-items-center gap-1">
													<a class="btn btn-rounded" href="order/edit/${order.id}.htm"><i class="fa fa-pencil"></i></a>
													<a class="btn btn-rounded"
													 data-createdAt="<fmt:formatDate value='${order.createdAt}' pattern='dd-MM-yyyy HH:mm' />"
													 data-fullname="${order.user.fullname}"
													 data-totalPrice="${order.totalPrice}"
													 data-paymentMethod="${order.paymentMethod}"
													 data-paymentStatus="${order.paymentStatus}"
													 data-orderStatus="${order.orderStatus}"
													 data-shippingAddress="${order.shippingAddress }"
													 data-discountValue=${order.discountValue }
													 data-originalValue=${order.totalPrice + order.discountValue}
													 <c:forEach var="orderDetail" items="${order.orderDetails}" varStatus="status">
												       data-bookTitle${status.index}="${orderDetail.book.title}"
												       data-bookThumbnail${status.index}="${orderDetail.book.thumbnail}"
												       data-bookQuantity${status.index}="${orderDetail.quantity}"
												       data-bookPrice${status.index}="${orderDetail.book.price}"
												   </c:forEach>
												   data-orderDetailsLength="${fn:length(order.orderDetails)}"
													 onclick="showDiscountDetails(this)">
														<i class="fa-solid fa-eye"></i>
													</a> 
													<!-- <a class="btn btn-rounded"><i class="fa fa-eye-slash"></i></a> -->
													
													<c:if test="${order.orderStatus == 'Chờ xác nhận'}">
													    <a href="javascript:void(0);" class="btn btn-rounded" data-bs-toggle="modal" data-bs-target="#confirmCancelModal" data-order-id="${order.id}"
													     data-toggle="tooltip" title="Huỷ đơn hàng" >
													        <i class="fa-solid fa-ban"></i>
													    </a>
													</c:if>
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
			
			<form id="orderUpdateForm" action="orders/updateOrderStatus.htm" method="POST" style="display: none;">
			    <input type="hidden" name="orderId" id="orderId" />
			    <input type="hidden" name="orderStatus" id="orderStatus" />
			    <button type="submit" id="submitButton" style="display:none;">Submit</button> <!-- Nút ẩn -->
			</form>

			
			<!-- Modal xác nhận hủy đơn hàng -->
				<div class="modal fade" id="confirmCancelModal" tabindex="-1" aria-labelledby="confirmCancelModalLabel" aria-hidden="true">
				    <div class="modal-dialog">
				        <div class="modal-content">
				            <div class="modal-header">
				                <h5 class="modal-title" id="confirmCancelModalLabel">Xác nhận hủy đơn hàng</h5>
				                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				            </div>
				            <div class="modal-body">
				                Bạn có chắc chắn muốn hủy đơn hàng này?
				            </div>
				            <div class="modal-footer">
				                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
				                <button type="button" class="btn btn-danger" id="confirmCancelButton">Hủy đơn hàng</button>
				            </div>
				        </div>
				    </div>
				</div>
			
			
			
			
					<div class="modal fade" id="orderDetailModal" tabindex="-1" aria-labelledby="orderDetailModalLabel" aria-hidden="true">
					    <div class="modal-dialog modal-xl">
					        <div class="modal-content shadow-lg rounded-4">
					            <div class="modal-header bg-primary text-white rounded-top-4">
					                <h5 class="modal-title fw-bold" id="orderDetailModalLabel">Order Details</h5>
					                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
					            </div>
					            <div class="modal-body p-4">
					                <!-- Thông tin tổng quan của đơn hàng -->
					                <div class="order-summary mb-4">
					                    <h6 class="text-primary fw-bold mb-3">Order Summary</h6>
					                    <div class="row">
					                        <div class="col-md-4 mb-3">
					                            <label class="form-label">Customer Name:</label>
					                            <input type="text" class="form-control" id="customerName" readonly>
					                        </div>
					                        <div class="col-md-4 mb-3">
					                            <label class="form-label">Created At:</label>
					                            <input type="text" class="form-control" id="createdAt" readonly>
					                        </div>
					                        <div class="col-md-4 mb-3">
					                            <label class="form-label">Total Price:</label>
					                            <input type="text" class="form-control" id="totalPrice" readonly> 
					                        </div>
					                    </div>
					                    <div class="row">
					                        <div class="col-md-4 mb-3">
					                            <label class="form-label">Payment Method:</label>
					                            <input type="text" class="form-control" id="paymentMethod" readonly>
					                        </div>
					                        <div class="col-md-4 mb-3">
					                            <label class="form-label">Payment Status:</label>
					                            <input type="text" class="form-control" id="paymentStatus" readonly>
					                        </div>
					                        <div class="col-md-4 mb-3">
					                            <label class="form-label">Order Status:</label>
					                            <input type="text" class="form-control" id="orderStatuss" readonly>
					                        </div>
					                    </div>
					                    
					                    <div class="row">
					                        <div class="col-md-4 mb-3">
					                            <label class="form-label">Shipping Address:</label>
					                            <input type="text" class="form-control" id="shippingAddress" readonly>
					                        </div>
					                        <div class="col-md-4 mb-3">
					                            <label class="form-label">Discount Value:</label>
					                            <input type="text" class="form-control" id="discountValue" readonly>
					                        </div>
					                        <div class="col-md-4 mb-3">
					                            <label class="form-label">Original Price:</label>
					                            <input type="text" class="form-control" id="originalPriceee" readonly>
					                        </div>
					                    </div>
					                </div>
					
					                <!-- Chi tiết sản phẩm -->
					                <div class="order-items">
					                    <h6 class="text-primary fw-bold mb-3">Order Items</h6>
					                    <div class="table-responsive">
					                        <table id="orderItemsTable" class="table table-bordered table-hover ">
					                            <thead class="table-light">
					                                <tr>
					                                    <th>Product</th>
					                                    <th>Quantity</th>
					                                    <th>Price</th>
					                                    <th>Total</th>
					                                </tr>
					                            </thead>
					                            <tbody id="order-items-body">
					                                
					                            </tbody>
					                        </table>
					                    </div>
					                </div>
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
	

	function updateOrderStatus(orderId, orderStatus) {
	    // Cập nhật giá trị vào các input của form
	    document.getElementById("orderId").value = orderId;
	    document.getElementById("orderStatus").value = orderStatus;

	    // Submit form
	    document.getElementById("orderUpdateForm").submit();
	}
	
	document.addEventListener('DOMContentLoaded', function() {
	    // Lấy ID đơn hàng khi nút hủy được click
	    var cancelOrderId = null;

	    // Khi người dùng click vào nút hủy đơn hàng
	    var cancelButtons = document.querySelectorAll('.btn[data-bs-target="#confirmCancelModal"]');
	    cancelButtons.forEach(function(button) {
	        button.addEventListener('click', function() {
	            cancelOrderId = this.getAttribute('data-order-id');
	        });
	    });

	    // Khi người dùng xác nhận hủy đơn hàng
	    var confirmCancelButton = document.getElementById('confirmCancelButton');
	    confirmCancelButton.addEventListener('click', function() {
	        if (cancelOrderId) {
	            updateOrderStatus(cancelOrderId, 'Huỷ đơn hàng');
	        }
	        // Đóng modal sau khi xác nhận
	        var cancelModal = new bootstrap.Modal(document.getElementById('confirmCancelModal'));
	        cancelModal.hide();
	    });
	});

	
	function showDiscountDetails(link) {
		var customerName = link.getAttribute('data-fullName');
	    var createdAt = link.getAttribute('data-createdAt');
	    var totalPrice = link.getAttribute('data-totalPrice');
	    var paymentMethod = link.getAttribute('data-paymentMethod');
	    var paymentStatus = link.getAttribute('data-paymentStatus');
	    var orderStatus = link.getAttribute('data-orderStatus');
	    var shippingAddress = link.getAttribute('data-shippingAddress');
	    var discountValue = link.getAttribute('data-discountValue');
	    var originalPrice = link.getAttribute('data-originalPrice');
	    var productPrice = link.getAttribute('data-productPrice');
	    var orderDetailsLength = link.getAttribute('data-orderDetailsLength');
	    var products = [];
	    for (var i = 0; i < orderDetailsLength; i++) {
	        var bookTitle = link.getAttribute('data-bookTitle' + i);
	        var bookThumbnail = link.getAttribute('data-bookThumbnail' + i);
	        var bookQuantity = link.getAttribute('data-bookQuantity' + i);
	        var bookPrice = link.getAttribute('data-bookPrice' + i);
	        
	        products.push({
	            title: bookTitle,
	            thumbnail: bookThumbnail,
	            quantity: bookQuantity,
	            price: bookPrice
	        });
	    }
	    console.log(products);
	    
	    document.getElementById('customerName').value = customerName;
	    document.getElementById('createdAt').value = createdAt;
	    document.getElementById('totalPrice').value = totalPrice;
	    document.getElementById('paymentMethod').value = paymentMethod;
	    document.getElementById('paymentStatus').value = paymentStatus;
	    document.getElementById('orderStatuss').value = orderStatus;
	    document.getElementById('shippingAddress').value = shippingAddress;
	    document.getElementById('discountValue').value = discountValue;
	    document.getElementById('originalPriceee').value = originalPrice;
	    
	    var tbody = document.getElementById('order-items-body');
	    tbody.innerHTML = ""; // Xóa mọi nội dung cũ trong tbody trước khi thêm mới

	    products.forEach(function(product) {
	        var row = tbody.insertRow();

	        // Cột 1: Ảnh và tên sản phẩm
	        var cell1 = row.insertCell(0);
	        var thumbnail = product.thumbnail
	        cell1.innerHTML = '<img src="' + thumbnail + '" alt="' + product.title + '" class="img-fluid" style="width: 60px; height: 60px;"> ' + product.title;


	        // Cột 2: Số lượng
	        var cell2 = row.insertCell(1);
	        cell2.innerHTML = product.quantity;

	        // Cột 3: Giá
	        var cell3 = row.insertCell(2);
	        cell3.innerHTML = product.price; // Hiển thị giá với 2 chữ số sau dấu thập phân

	        // Cột 4: Tổng giá (Giá * Số lượng)
	        var cell4 = row.insertCell(3);
	        var totalPrice = (product.price * product.quantity); // Tính tổng giá
	        cell4.innerHTML = (product.price * product.quantity);
	    });

	    // Mở modal
	    var modal = new bootstrap.Modal(document.getElementById('orderDetailModal'));
	    modal.show();
	}
	
	function refreshPage() {
	    // Sử dụng window.location để reload lại trang mà không tham số
	    window.location.href = window.location.origin + window.location.pathname;
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