<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Shopping Cart</title>
<base href="${pageContext.servletContext.contextPath}/">
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- CSS Files -->
<link rel="stylesheet"
	href="${pageContext.servletContext.contextPath}/resources/assets/css/client/bootstrap.min.css" />
<link rel="stylesheet"
	href="${pageContext.servletContext.contextPath}/resources/assets/css/client/font-awesome.min.css" />
<link rel="stylesheet"
	href="${pageContext.servletContext.contextPath}/resources/assets/css/client/main.css" />
<%--     <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/assets/css/client/cart.css" /> --%>

<link rel='stylesheet'
	href='https://cdn.jsdelivr.net/npm/sweetalert2@7.12.15/dist/sweetalert2.min.css'></link>

<script src="https://kit.fontawesome.com/e70d1e2fed.js"
	crossorigin="anonymous"></script>
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/css/toastr.min.css"
	rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/js/toastr.min.js"></script>
</head>
<body>
	<div id="tg-wrapper" class="tg-wrapper tg-haslayout">
		<!-- Header -->
		<%@ include file="../client/layouts/header.jsp"%>

		<!-- Main Content -->
		<section id="tg-wrapper" class="tg-sectionspace tg-haslayout">
			<div class="container mt-4" style="margin-bottom: 24px">
				<h1 class="cart-title mb-4" style="text-align: center;">Giỏ hàng</h1>
				<c:choose>
					<c:when test="${empty cartItems}">
						<div class="alert alert-warning text-center" role="alert">
							<h4 class="alert-heading">Không có sản phẩm trong giỏ hàng!</h4>
							<p>Giỏ hàng của bạn hiện đang trống. Hãy thêm sản phẩm để
								tiếp tục mua sắm.</p>
							<hr>
						</div>
					</c:when>
					<c:otherwise>
						<form action="/bookstore/payment/checkout.htm" method="POST"
							style="width: 1170px">
							<table class="cart-table table table-hover table-bordered">
								<thead class="table-light">
									<tr>
										<th><input type="checkbox" id="select-all"
											class="form-check-input"></th>
										<th colspan="2">Sản phẩm</th>
										<th>Giá sản phẩm</th>
										<th>Số lượng</th>
										<th>Tổng cộng</th>
										<th></th>
									</tr>
								</thead>
								<tbody>

									<c:forEach var="item" items="${cartItems}">
										<tr>
											<td><input type="checkbox" name="selectedItems"
												value="${item.id}" class="form-check-input"></td>
											<td><img src="${item.book.thumbnail}"
												alt="${item.book.title}" class="img-fluid"
												style="width: 50px; height: auto; margin-right: 10px;"></td>
											<td>
												<div
													class="d-flex justify-content-center align-items-center">
													<span class="ms-2"><strong>${item.book.title}</strong></span>
												</div>
											</td>
											<td><fmt:formatNumber value="${item.book.price}"
													type="currency" maxFractionDigits="0" currencySymbol="₫" /></td>
											<td>
												<!-- <input type="number" name="quantity[1]" value="1" min="1" class="form-control quantity-input" style="width: 80px;"> -->
												<input type="number" name="quantity[${item.id}]"
												value="${item.quantity}" min="1"
												max="${item.book.inventoryEntity.stock_quantity }"
												oninput="checkMaxMin(this)"
												oninvalid="this.setCustomValidity('The minimum quantity is 1, and the available stock quantity is ' + this.max + '.')"
												class="form-control quantity-input numeric-input"
												style="width: 80px;"
												onchange="updateQuantityLink(${item.id}, this.value)"
												data-toggle="tooltip"
												title="The minimum quantity is 1, and the available stock quantity is ${item.book.inventoryEntity.stock_quantity}">

											</td>
											<td><fmt:formatNumber
													value="${item.quantity * item.book.price}" type="currency"
													maxFractionDigits="0" currencySymbol="₫" /></td>
											<td>
												<%--  <button type="button" class="btn btn-warning btn-sm" onclick="updateQuantityLink(${item.id}, this.previousElementSibling.value)">Update</button> --%>
												<a
												href="/bookstore/cart/view.htm?book_id=${item.book.id}&quantity=${item.quantity}"
												class="btn btn-warning" id="update-link-${item.id}"
												style="font-size: 11px;">Update</a>
												<button type="button" class="btn btn-danger btn-sm"
													onclick="location.href='/bookstore/cart/remove.htm?cartItemId=${item.id}'">Delete</button>
											</td>
										</tr>
									</c:forEach>
									<tr>
										<td colspan="3"><strong>Tổng tiền</strong></td>
										<td colspan="4"><fmt:formatNumber value="${totalPrice }"
												type="currency" maxFractionDigits="0" currencySymbol="₫" /></td>
									</tr>
								</tbody>
							</table>

							<!-- Tổng cộng giỏ hàng -->
							<div class="row mt-3">
								<div class="col-md-4 d-flex justify-content-center" style="left: 130px">
									<button type="button" class="btn btn-light btn-lg w-100"
										style="padding: 7px 31px; font-size: 16px; border-radius: 13px; background-color: #6C63FF; color: #404040; border: none; transition: background-color 0.3s ease;"
										onclick="window.location.href='/bookstore/index.htm'">
										Trang chủ</button>
								</div>

								<div
									class="col-md-4 d-flex justify-content-center align-items-center">
									<!-- <button type="submit" class="btn btn-secondary btn-lg w-100" style="padding: 7px 31px; font-size: 16px; border-radius: 13px;">Middle Button</button> -->
								</div>

								<div class="col-md-4 d-flex justify-content-center"
									style="left: 130px">
									<button type="submit" class="btn btn-primary btn-lg w-100"
										style="padding: 7px 31px; font-size: 16px; border-radius: 13px; background-color: #28a745; color: #404040; border: none; transition: background-color 0.3s ease;">
										Thanh toán</button>
								</div>
							</div>
						</form>
					</c:otherwise>
				</c:choose>
			</div>

		</section>
		<!-- Footer -->
		<%@ include file="../client/layouts/footer.jsp"%>
	</div>

	<!-- JS Files -->
	<script
		src="${pageContext.servletContext.contextPath}/resources/assets/js/client/vendor/jquery-library.js"></script>
	<script
		src="${pageContext.servletContext.contextPath}/resources/assets/js/client/vendor/bootstrap.min.js"></script>
	<script>
        // Select All Checkboxes
       document.addEventListener('DOMContentLoaded', function() {
    // Chức năng Select All
    document.getElementById('select-all').addEventListener('change', function () {
        const checkboxes = document.querySelectorAll('input[name="selectedItems"]');
        checkboxes.forEach(checkbox => checkbox.checked = this.checked);
    });

    // Chức năng tính tổng tiền khi thay đổi số lượng
    document.querySelectorAll('.product-quantity').forEach(function(select) {
        select.addEventListener('change', function() {
            var price = parseFloat(select.getAttribute('data-price'));
            var quantity = parseInt(select.value);
            var itemTotal = price * quantity;

            // Cập nhật tổng tiền cho sản phẩm này
            select.closest('tr').querySelector('.item-total').textContent = itemTotal.toFixed(2);

            // Tính lại tổng giỏ hàng
            var total = 0;
            document.querySelectorAll('.item-total').forEach(function(itemTotalElem) {
                total += parseFloat(itemTotalElem.textContent);
            });

            // Cập nhật tổng tiền giỏ hàng
            document.getElementById('totalPrice').textContent = total.toFixed(2);
        });
    });
});
        function updateRowTotal(inputElement) {
            var quantity = inputElement.value;
            var price = inputElement.getAttribute('data-price');
            var rowTotal = quantity * price;

            // Cập nhật giá trị tổng
            var rowTotalElement = inputElement.closest('tr').querySelector('.row-total');
            rowTotalElement.innerText = rowTotal.toFixed(2);
        }
        
        document.addEventListener('DOMContentLoaded', function() {
            var errorMessage = "${errorMessage}";
            var successMessage = "${successMessage}";

            if (errorMessage) {
                Swal.fire({
                    title: 'Error!',
                    text: errorMessage,
                    icon: 'error',
                    confirmButtonText: 'OK'
                });
            } else if (successMessage) {
                Swal.fire({
                    title: 'Success!',
                    text: successMessage,
                    icon: 'success',
                    confirmButtonText: 'OK'
                });
            }
        });
        
       
      
        //cập nhật số lượng sp trong giỏ hàng
        function updateQuantityLink(cartItemId, newQuantity) {
            var updateLink = document.getElementById('update-link-' + cartItemId);
            var newHref = '/bookstore/cart/update_quantity.htm?cartItemId=' + cartItemId + '&quantity=' + newQuantity;
            updateLink.href = newHref;
        }
        
        function checkMaxMin(input) {
            var max = parseInt(input.max);
            var min = parseInt(input.min);

            if (input.value > max) {
                input.value = max; // Nếu giá trị nhập vào lớn hơn max, đặt lại thành giá trị max
                input.setCustomValidity(`Value cannot be greater than ${max}`); // Thiết lập thông báo lỗi
            } else if (input.value < min) {
                input.value = min; // Nếu giá trị nhập vào nhỏ hơn min, đặt lại thành giá trị min
                input.setCustomValidity(`Value cannot be less than ${min}`); // Thiết lập thông báo lỗi
            } else {
                input.setCustomValidity(''); // Xóa thông báo lỗi nếu giá trị hợp lệ
            }
        }
	
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
