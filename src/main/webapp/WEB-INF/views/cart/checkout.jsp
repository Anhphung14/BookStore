<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/assets/css/client/main.css" />
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/assets/css/client/cart.css" />
    <!-- Custom CSS -->
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/assets/css/checkout.css" />
    
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
                <h1 class="page-title text-center">Thông Tin Thanh Toán</h1>
                <div class="content-wrapper row">
                    <!-- Thông Tin Nhận Hàng -->
                    <div class="info-section col-md-6">
                        <h2>Thông Tin Nhận Hàng</h2>
                        <form method="post" action="${pageContext.servletContext.contextPath}/payment/pay.htm">
                            <input type="hidden" name="userId" value="${user.id}">
                            <c:forEach var="id" items="${param.selectedItems}">
                                <input type="hidden" name="selectedItems" value="${id}">
                            </c:forEach>
                            <div class="field">
                                <label for="name" class="field__label">Họ và tên</label>
                                <input id="name" name="name" type="text" class="field__input" value="${user.fullname}" required>
                            </div>
                            <div class="field">
                                <label for="phone" class="field__label">Số điện thoại</label>
                                <input id="phone" name="phone" type="tel" class="field__input" value="${user.phone}" required>
                            </div>
                            <div class="field">
                                <label for="email" class="field__label">Email</label>
                                <input id="email" name="email" type="email" class="field__input" value="${user.email}" required>
                            </div>
                            <!-- <div class="field">
                                <label for="province" class="field__label">Tỉnh/Thành phố</label>
                                <select id="province" name="province" class="field__input">
                                    <option value="">Chọn tỉnh/thành phố</option>
                                    Thêm các tỉnh thành khác ở đây
                                </select>
                            </div>
                            <div class="field">
                                <label for="district" class="field__label">Quận/Huyện</label>
                                <select id="district" name="district" class="field__input">
                                    <option value="">Chọn quận/huyện</option>
                                    Các quận huyện theo tỉnh sẽ được tải sau khi chọn tỉnh
                                </select>
                            </div>
                            <div class="field">
                                <label for="ward" class="field__label">Phường/Xã</label>
                                <select id="ward" name="ward" class="field__input">
                                    <option value="">Chọn phường/xã</option>
                                    Các phường xã theo quận sẽ được tải sau khi chọn quận
                                </select>
                            </div> -->
                            <div class="field">
                                <label for="address" class="field__label"><!-- Số nhà --> Địa chỉ giao hàng</label>
                                <input id="address" name="address" type="text" class="field__input">
                            </div>
                            <div class="field">
                                <label for="note" class="field__label">Ghi chú</label>
                                <textarea id="note" name="note" class="field__input"></textarea>
                            </div>
                    </div>
                    
                    <!-- Tóm Tắt Đơn Hàng -->
                    <div class="summary-section col-md-6">
                        <h2>Tóm Tắt Đơn Hàng</h2>
                        <table class="order-summary table table-bordered">
                            <thead>
                                <tr>
                                    <th>Sản phẩm</th>
                                    <th>Số lượng</th>
                                    <th>Đơn Giá</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="item" items="${cartItems}">
                                    <tr>
                                        <td>${item.book.title}</td>
                                        <td>${item.quantity}</td>
                                        <td>${item.price} VND</td>
                                    </tr>	
                                </c:forEach>
                            </tbody>
                            <tfoot>
                                <tr>
                                    <th colspan="2">Tổng cộng</th>
                                    <td>${totalPrice} VND</td>
                                </tr>
                                <tr>
                                    <th colspan="2">Mã giảm giá có thể sử dụng</th>
                                    <td><input type="text" name="discount" id="discount" class="field__input" placeholder="Nhập mã giảm giá"></td>
                                </tr>
                            </tfoot>
                        </table>
                        <h3>Phương Thức Thanh Toán</h3>
                        <div class="radio-group">
                            <label><input type="radio" name="payment" value="paypal" required> Thanh toán bằng Paypal</label>
                            <label><input type="radio" name="payment" value="vnpay" required> Thanh toán qua VNPAY-QR</label>
                            <label><input type="radio" name="payment" value="cod" required> Thanh toán khi nhận hàng</label>
                        </div>
                        
                        <a href="/bookstore/cart/view.htm" class="btn btn-secondary">Quay về giỏ hàng</a>
                        <button type="submit" class="btn btn-primary">Đặt Hàng</button>
                    </div>

                    </form>
                </div>
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
//Load danh sách tỉnh
function loadProvinces() {
    fetch('/api/provinces')
        .then(response => response.json())
        .then(data => {
            const provinceSelect = document.getElementById('province');
            provinceSelect.innerHTML = '<option value="">Chọn tỉnh/thành phố</option>';
            data.forEach(province => {
                provinceSelect.innerHTML += `<option value="${province}">${province}</option>`;
            });
        });
}

// Load danh sách huyện theo tỉnh
function loadDistricts() {
    const province = document.getElementById('province').value;
    if (province) {
        fetch(`/api/districts?province=${province}`)
            .then(response => response.json())
            .then(data => {
                const districtSelect = document.getElementById('district');
                districtSelect.innerHTML = '<option value="">Chọn quận/huyện</option>';
                data.forEach(district => {
                    districtSelect.innerHTML += `<option value="${district}">${district}</option>`;
                });
            });
    } else {
        document.getElementById('district').innerHTML = '<option value="">Chọn quận/huyện</option>';
        document.getElementById('ward').innerHTML = '<option value="">Chọn phường/xã</option>';
    }
}

// Load danh sách xã theo huyện
function loadWards() {
    const province = document.getElementById('province').value;
    const district = document.getElementById('district').value;
    if (province && district) {
        fetch(`/api/wards?province=${province}&district=${district}`)
            .then(response => response.json())
            .then(data => {
                const wardSelect = document.getElementById('ward');
                wardSelect.innerHTML = '<option value="">Chọn phường/xã</option>';
                data.forEach(ward => {
                    wardSelect.innerHTML += `<option value="${ward}">${ward}</option>`;
                });
            });
    } else {
        document.getElementById('ward').innerHTML = '<option value="">Chọn phường/xã</option>';
    }
}

// Gọi hàm loadProvinces khi trang được tải
document.addEventListener('DOMContentLoaded', loadProvinces);
</script>
    <!-- JS Files -->
    <script src="${pageContext.servletContext.contextPath}/resources/assets/js/client/vendor/jquery-library.js"></script>
    <script src="${pageContext.servletContext.contextPath}/resources/assets/js/client/vendor/bootstrap.min.js"></script>
    <script src="${pageContext.servletContext.contextPath}/resources/assets/js/client/checkout.js"></script>
</body>
</html>
