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
                <h1 class="text-center">Thông Tin Thanh Toán</h1>
                <div class="content-wrapper row">
                    <!-- Thông Tin Nhận Hàng -->
                    <form method="POST" action="${pageContext.servletContext.contextPath}/payment/pay.htm">
                    <div class="info-section col-md-6">
                        <h2>Thông Tin Nhận Hàng</h2>
                            <input type="hidden" name="userId" value="${user.id}">
                            <c:forEach var="cartItem" items="${cartItems}">
                                <input type="hidden" name="selectedItems" value="${cartItem.id}">
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
                            
                            <div class="row mt-3">
							    <!-- Tỉnh Thành -->
								<div class="col-md-3">
							        <div class="form-floating">
							            <select class="form-control" id="province" name="province" required>
							                <option value="">Chọn Tỉnh Thành</option>
							                
							            </select>
							            <label class="form-label" for="province">Tỉnh Thành <span class="text-danger">*</span></label>
							        </div>
							    </div>

								
								<!-- Quận Huyện -->
								<div class="col-md-3">
								    <div class="form-floating">
								        <select class="form-control" id="district" name="district" required>
								            <option value="">Chọn Quận Huyện</option>
								            <!-- Các quận huyện sẽ được cập nhật khi chọn Tỉnh Thành -->
								        </select>
								        <label class="form-label" for="district">Quận Huyện <span class="text-danger">*</span></label>
								    </div>
								</div>
								
								<!-- Phường Xã -->
								<div class="col-md-3">
								    <div class="form-floating">
								        <select class="form-control" id="ward" name="ward" required>
								            <option value="">Chọn Phường Xã</option>
								            <!-- Các phường xã sẽ được cập nhật khi chọn Quận Huyện -->
								        </select>
								        <label class="form-label" for="ward">Phường Xã <span class="text-danger">*</span></label>
								    </div>
								</div>

							
							    <!-- Số nhà/Đường -->
							    <div class="col-md-3">
							        <div class="form-floating" style="height: 100%;">
							            <input class="form-control" id="street" name="street" type="text" value="" required style="height: 100%;">
							            <label class="form-label" for="street">Số nhà/Đường <span class="text-danger">*</span></label>
							        </div>
							    </div>
							</div>
<!--                             <div class="field">
                                <label for="note" class="field__label">Ghi chú</label>
                                <textarea id="note" name="note" class="field__input"></textarea>
                            </div> -->
                    </div>
                    
                    <!-- Tóm Tắt Đơn Hàng -->
                    <div class="summary-section col-md-6">
                        <h2>Tóm Tắt Đơn Hàng</h2>
                        <table class="order-summary table table-bordered">
                            <thead>
                                <tr>
                                    <th colspan="2">Sản phẩm</th>
                                    <th>Số lượng</th>
                                    <th>Đơn Giá</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="item" items="${cartItems}">
                                    <tr>
                                    	<td><img src="${item.book.thumbnail}" width="50px"; height="50px"></td>
                                        <td>${item.book.title}</td>
                                        <td>${item.quantity}</td>
                                        <td> <fmt:formatNumber value="${item.price}" type="currency" currencySymbol="đ" /></td>
                                    </tr>	
                                </c:forEach>
                            </tbody>
                            <tfoot>
                                <tr>
                                    <th colspan="2">Tổng cộng</th>
                                    <td colspan="2"> <fmt:formatNumber value="${totalPrice}" type="currency" currencySymbol="đ" /></td>
                                </tr>
                              	<tr>
								    <th colspan="2">Mã giảm giá có thể sử dụng
								    	<div class="list-group">
								            <!-- Lặp qua các mã giảm giá có sẵn -->
								            <c:forEach var="discount" items="${listDiscountsAvailable}">
											    <button type="button" class="list-group-item list-group-item-action" 
											            onclick="applyDiscount('${discount.code}', '${discount.discountValue}')">
											        <span>
											            <strong>${discount.code}</strong> - Giảm giá 
											            <c:choose>
											                <c:when test="${discount.discountType == 'percentage'}">
											                    <fmt:formatNumber value="${discount.discountValue}" type="currency" currencySymbol="%" />
											                </c:when>
											                <c:otherwise>
											                    <fmt:formatNumber value="${discount.discountValue}" type="currency" currencySymbol="đ" />
											                </c:otherwise>
											            </c:choose>
											            từ đơn   <fmt:formatNumber value="${discount.minOrderValue}" type="currency" currencySymbol="đ" />
											        </span>
											    </button>
											</c:forEach>
								        </div>
								    </th>
								  <td colspan="2" class="center-input">
								        <input type="text" name="discountCode" id="discount" class="field__input" placeholder="Nhập mã giảm giá" oninput="convertToUpperCase(this);">
								    </td>
                            </tfoot>
                        </table>
                        
                        <h3>Phương Thức Thanh Toán</h3>
                        <div class="radio-group">
							<label><input type="radio" name="paymentMethod" value="PayPal" required> Thanh toán bằng Paypal</label>
								<div id="paypal-button-container"></div>
                            <label><input type="radio" name="paymentMethod" value="VnPay" required> Thanh toán qua VNPAY-QR</label>
                            <label><input type="radio" name="paymentMethod" value="COD" required> Thanh toán khi nhận hàng</label>
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

/* // Gọi hàm loadProvinces khi trang được tải
document.addEventListener('DOMContentLoaded', loadProvinces);
 */
/* paypal.Buttons({
    createOrder: function(data, actions) {
        return actions.order.create({
            purchase_units: [{
                amount: {
                    value: ${totalPrice}  // Số tiền cần thanh toán (ví dụ 100 USD)
                }
            }]
        });
    },
    onApprove: function(data, actions) {
        return actions.order.capture().then(function(details) {
            alert('Thanh toán thành công! ' + details.payer.name.given_name);
            // Sau khi thanh toán thành công, bạn có thể thực hiện các thao tác khác như chuyển hướng trang hoặc lưu thông tin thanh toán
        });
    },
    onCancel: function(data) {
        alert('Thanh toán đã bị hủy');
    }
}).render('#paypal-button-container');  // Hiển thị nút thanh toán PayPal
 */


var locationData = ${locationData};  // Thông qua modelMap.addAttribute("locationData", locationDataJson);

// Lấy các phần tử select trong form
var provinceSelect = document.getElementById("province");
var districtSelect = document.getElementById("district");
var wardSelect = document.getElementById("ward");


// Hàm để đổ các tỉnh thành vào dropdown
function populateProvinces() {
    // Đổ tất cả tỉnh thành vào ô chọn
    Object.keys(locationData).forEach(function(provinceName) {
        var option = document.createElement("option");
        option.value = provinceName;
        option.textContent = provinceName;
        provinceSelect.appendChild(option);

        // Kiểm tra nếu tỉnh thành khớp với giá trị trong shippingAddress
        if (provinceName === province) {
            option.selected = true; // Chọn tỉnh thành
            populateDistricts(provinceName); // Cập nhật quận huyện tương ứng
        }
    });
}

// Hàm để đổ quận huyện vào dropdown
function populateDistricts(selectedProvince) {
    // Xóa tất cả quận huyện đã có
    districtSelect.innerHTML = "<option value=''>Chọn Quận Huyện</option>";

    // Đổ các quận huyện vào dropdown dựa trên tỉnh thành đã chọn
    var districts = locationData[selectedProvince];
    for (var districtName in districts) {
        var option = document.createElement("option");
        option.value = districtName;
        option.textContent = districtName;
        districtSelect.appendChild(option);

        // Kiểm tra xem quận huyện có giống với giá trị trong shippingAddress không
        if (districtName === district) {
            option.selected = true; // Chọn quận huyện
            populateWards(selectedProvince, districtName); // Cập nhật phường xã tương ứng
        }
    }
}

// Hàm để đổ phường xã vào dropdown
function populateWards(selectedProvince, selectedDistrict) {
    // Xóa tất cả phường xã đã có
    wardSelect.innerHTML = "<option value=''>Chọn Phường Xã</option>";

    // Đổ các phường xã vào dropdown dựa trên quận huyện đã chọn
    var wards = locationData[selectedProvince][selectedDistrict];
    wards.forEach(function(wardName) {
        var option = document.createElement("option");
        option.value = wardName;
        option.textContent = wardName;
        wardSelect.appendChild(option);

        // Kiểm tra xem phường xã có giống với giá trị trong shippingAddress không
        if (wardName === ward) {
            option.selected = true; // Chọn phường xã
        }
    });
}

// Gọi hàm để đổ tỉnh thành, quận huyện, phường xã
populateProvinces();

// Xử lý sự kiện khi người dùng thay đổi tỉnh thành
provinceSelect.addEventListener("change", function() {
    var selectedProvince = provinceSelect.value;
    if (selectedProvince) {
        populateDistricts(selectedProvince);
    }
});

// Xử lý sự kiện khi người dùng thay đổi quận huyện
districtSelect.addEventListener("change", function() {
    var selectedProvince = provinceSelect.value;
    var selectedDistrict = districtSelect.value;
    if (selectedProvince && selectedDistrict) {
        populateWards(selectedProvince, selectedDistrict);
    }
});

function applyDiscount(discountCode) {
    // Lấy phần tử input và gán mã giảm giá vào giá trị của ô input
    document.getElementById('discount').value = discountCode;
}

function convertToUpperCase(inputElement) {
    // Đổi giá trị của input thành chữ in hoa
    inputElement.value = inputElement.value.toUpperCase();
}


</script>
    <!-- JS Files -->
    <script src="${pageContext.servletContext.contextPath}/resources/assets/js/client/vendor/jquery-library.js"></script>
    <script src="${pageContext.servletContext.contextPath}/resources/assets/js/client/vendor/bootstrap.min.js"></script>
    <script src="${pageContext.servletContext.contextPath}/resources/assets/js/client/checkout.js">
</script>
    
</body>
</html>
