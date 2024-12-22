<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Thanh toán</title>
    <base href="${pageContext.servletContext.contextPath}/">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- CSS Files -->
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/assets/css/client/bootstrap.min.css" />
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/assets/css/client/font-awesome.min.css" />
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/assets/css/client/cart.css" />
    <!-- Custom CSS -->
   
    
    <link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/sweetalert2@7.12.15/dist/sweetalert2.min.css'></link>  
    <script src="https://kit.fontawesome.com/e70d1e2fed.js" crossorigin="anonymous"></script>
    <!--boostrap for paypal -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://www.paypal.com/sdk/js?client-id=AZzuNyaP3AzHAUUzLAOkhRz0imoN1dmXQHN0FEczevPzFJK-c6tQwI-VD79x6dZ7eOuMR2kn4eBjPUnB&currency=USD"></script>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/assets/css/client/main.css" />
         <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/assets/css/checkout.css" />
</head>
<body>
    <div id="tg-wrapper" class="tg-wrapper tg-haslayout">
        <!-- Header -->
        <%@ include file="../client/layouts/header.jsp" %>

        <!-- Main Content -->
        <section class="tg-sectionspace tg-haslayout" style="margin-bottom: 24px;">
		    <div class="container">
		        <h1 class="text-center mb-4">Review Payment</h1>
		        <form id="paymentForm" method="POST" action="${pageContext.servletContext.contextPath}/payment/pay.htm">
		            <div class="row g-4">
		                <!-- Thông Tin Nhận Hàng -->
		                <div class="info-section col-lg-6">
		                    <div class="card shadow-sm">
		                        <div class="card-header bg-primary text-white">
		                            <h5 class="card-title mb-0">Thông Tin Nhận Hàng</h5>
		                        </div>
		                        <div class="card-body">
		                            <div class="mb-3">
		                                <label for="name" class="form-label">Họ và tên</label>
		                                <input id="name" name="name" type="text" class="form-control" value="${shippingAddress.recipientName}" required>
		                            </div>
		                            <div class="mb-3">
		                                <label for="phone" class="form-label">Số điện thoại <span class="text-danger">*</span></label>
		                                <input id="phone" name="phone" type="tel" class="form-control" value="${user.phone}" required pattern="^0(3|5|7|8|9)[0-9]{8}$">
		                            </div>
		                            <div class="mb-3">
		                                <label for="email" class="form-label">Email <span class="text-danger">*</span></label>
		                                <input id="email" name="email" type="email" class="form-control" value="${user.email}" required pattern="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$">
		                            </div>
		                            <div class="row g-3">
		                                <div class="col-md-3">
		                                    <label for="province" class="form-label">Tỉnh Thành</label>
		                                    <input class="form-control" id="province" name="province" type="text" value="${shippingAddress.state}" readonly>
		                                </div>
		                                <div class="col-md-3">
		                                    <label for="district" class="form-label">Quận Huyện</label>
		                                    <input class="form-control" id="district" name="district" type="text" value="${shippingAddress.city}" readonly>
		                                </div>
		                                <div class="col-md-3">
		                                    <label for="ward" class="form-label">Phường Xã</label>
		                                    <input class="form-control" id="ward" name="ward" type="text" value="${shippingAddress.line2}" readonly>
		                                </div>
		                                <div class="col-md-3">
		                                    <label for="street" class="form-label">Số nhà/Đường</label>
		                                    <input class="form-control" id="street" name="street" type="text" value="${shippingAddress.line1}" readonly>
		                                </div>
		                            </div>
		                        </div>
		                    </div>
		                </div>
		
		                <!-- Tóm Tắt Đơn Hàng -->
		                <div class="summary-section col-lg-6">
		                    <div class="card shadow-sm">
		                        <div class="card-header bg-primary text-white">
		                            <h5 class="card-title mb-0">Tóm Tắt Đơn Hàng</h5>
		                        </div>
		                        <div class="card-body">
		                            <table class="table table-bordered">
		                                <thead class="table-light">
		                                    <tr>
		                                        <th>Sản phẩm</th>
		                                        <th>Số lượng</th>
		                                        <th>Thuế</th>
		                                        <th>Đơn Giá (USD)</th>
		                                        <th>Đơn Giá (VND)</th>
		                                    </tr>
		                                </thead>
		                                <tbody>
		                                    <c:forEach var="item" items="${transaction.itemList.items}">
		                                        <tr>
		                                            <input type="hidden" name="selectedItems" value="${item.sku}">
		                                            <td>${item.name}</td>
		                                            <td>${item.quantity}</td>
		                                            <td>${item.tax}</td>
            										<td>${item.price} $</td> 
            										<%-- <td>${item.price / 0.000039} VND</td> --%>
            										<td><fmt:formatNumber
													value="${item.price / 0.000039}" type="currency"
													maxFractionDigits="0" currencySymbol="₫" /></td>
		                                        </tr>
		                                    </c:forEach>
		                                </tbody>
		                                <tfoot>
		                                    <tr>
		                                        <th colspan="2">Tổng cộng</th>
		                                        <td colspan="2">${transaction.amount.total} $</td>
		                                        <%-- <td>${transaction.amount.total  / 0.000039} VND</td> --%> 
		                                        <td><fmt:formatNumber
													value="${transaction.amount.total  / 0.000039}" type="currency"
													maxFractionDigits="0" currencySymbol="₫" /></td>
		                                    </tr>
		                                </tfoot>
		                            </table>
		                            <h5 class="mt-3">Thông tin thẻ</h5>
		                            <ul class="list-group mb-3">
		                                <li class="list-group-item"><strong>First Name:</strong> ${payer.firstName}</li>
		                                <li class="list-group-item"><strong>Last Name:</strong> ${payer.lastName}</li>
		                                <li class="list-group-item"><strong>Email:</strong> ${payer.email}</li>
		                            </ul>
		                            <input type="hidden" id="paymentMethod" name="paymentMethod" value="PayPal">
		                            <a href="/bookstore/cart/view.htm" class="btn btn-secondary">Quay về giỏ hàng</a>
		                            <input name="totalPrice" type="hidden" value="${totalPrice}">
		                            <button type="submit" class="btn btn-primary" onclick="submitPayment()">Thanh toán</button>
		                        </div>
		                    </div>
		                </div>
		            </div>
		        </form>
		    </div>
		</section>

        <!-- Footer -->
        <%@ include file="../client/layouts/footer.jsp" %>
    </div>

<script>

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



</script>
    <!-- JS Files -->
    <script src="${pageContext.servletContext.contextPath}/resources/assets/js/client/vendor/jquery-library.js"></script>
    <script src="${pageContext.servletContext.contextPath}/resources/assets/js/client/vendor/bootstrap.min.js"></script>
    <script src="${pageContext.servletContext.contextPath}/resources/assets/js/client/checkout.js">
</script>
    
</body>
</html>
