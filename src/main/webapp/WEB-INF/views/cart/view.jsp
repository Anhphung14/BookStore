<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Shopping Cart</title>
    <base href="${pageContext.servletContext.contextPath}/">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- CSS Files -->
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/assets/css/client/bootstrap.min.css" />
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/assets/css/client/font-awesome.min.css" />
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/assets/css/client/main.css" />
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/assets/css/client/cart.css" />
    
    <link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/sweetalert2@7.12.15/dist/sweetalert2.min.css'></link>  

    <script src="https://kit.fontawesome.com/e70d1e2fed.js" crossorigin="anonymous"></script>
</head>
<body>
    <div id="tg-wrapper" class="tg-wrapper tg-haslayout">
        <!-- Header -->
        <%@ include file="../client/layouts/header.jsp" %>

        <!-- Main Content -->
        <section class="tg-sectionspace tg-haslayout">
            <div class="container">
                <h1 class="cart-title">Shopping Cart</h1>
                <form action="/bookstore/payment/checkout.htm" method="POST">
                    <table class="cart-table table table-hover table-bordered">
                        <thead>
                            <tr>
                                <th><input type="checkbox" id="select-all"></th>
                                <th>Product</th>
                                <th>Price</th>
                                <th>Quantity</th>
                                <th>Total</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="item" items="${cartItems}">
                                <tr>
                                    <td>
                                        <!-- Checkbox để chọn sản phẩm -->
                                        <input type="checkbox" name="selectedItems" value="${item.id}">
                                    </td>
                                    <td>
                                        <div class="product-info">
                                            <img src="${item.book.thumbnail}" 
                                                 alt="${item.book.title}" style="width: 50px; height: auto;">
                                            <span>${item.book.title}</span>
                                        </div>
                                    </td>
                                    <td>${item.book.price}</td>
                                    <td>
									    <input type="number" name="quantity[${item.id}]" value="${item.quantity}" min="1" class="form-control quantity-input numeric-input" style="width: 80px;" onchange="updateQuantityLink(${item.id}, this.value)">
									</td>
                                    <td class="row-total">${item.quantity * item.book.price}</td>
                                    <td>
                                        <button type="button" class="btn btn-danger btn-sm">Xóa</button>
                                    </td>
                                    <%-- <td>
				                        <a href="/bookstore/cart/view.htm?book_id=${item.book.id}&quantity=${item.quantity}" class="btn btn-primary" id="update-link-${item.id}">Cập nhật</a>
				                    </td> --%>
				                    <td>
									    <a href="#" class="btn btn-primary" id="update-link-${item.id}" onclick="updateQuantityLink(${item.id}, this.previousElementSibling.value)">Cập nhật</a>
									</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                    <div class="cart-actions">
                        <!-- Nút cập nhật giỏ hàng -->
                        <!-- <button type="submit" class="btn btn-secondary">Cập nhật giỏ hàng</button> -->
                        <button type="button" class="btn btn-light" id="back-to-home-btn" onclick="window.location.href='/bookstore/index.htm'">Trở lại trang chủ</button>
                        <!-- Nút thanh toán -->
                        <button type="submit" class="btn btn-primary">Tiến hành thanh toán</button>
                    </div>
                </form>
            </div>
        </section>
        <!-- Footer -->
        <%@ include file="../client/layouts/footer.jsp" %>
    </div>

    <!-- JS Files -->
    <script src="${pageContext.servletContext.contextPath}/resources/assets/js/client/vendor/jquery-library.js"></script>
    <script src="${pageContext.servletContext.contextPath}/resources/assets/js/client/vendor/bootstrap.min.js"></script>
    <script>
        // Select All Checkboxes
        document.getElementById('select-all').addEventListener('change', function () {
            const checkboxes = document.querySelectorAll('input[name="selectedItems"]');
            checkboxes.forEach(checkbox => checkbox.checked = this.checked);
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

        window.onload = function() {
		    formatNumberInputsOnLoad();
	
		    let numericInputs = document.querySelectorAll('.numeric-input');
		    numericInputs.forEach(input => {
		        input.addEventListener('focus', resetInitialInputFlag);
		    });
		};
    </script>
</body>
</html>
