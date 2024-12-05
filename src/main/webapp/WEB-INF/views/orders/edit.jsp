<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>
		Edit Order
	</title>

	<base href="${pageContext.servletContext.contextPath}/admin1337/">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
	<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

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

.position-relative {
	position: relative;
}

.currency-symbol {
	position: absolute;
	right: 30px;
	top: 60%;
	transform: translateY(-50%);
	pointer-events: none;
	/* This makes sure the text is not clickable/interactable */
	color: #495057; /* Adjust color to match your design */
}

.form-floating {
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); /* Shadow nhẹ */
    border-radius: 8px; /* Bo góc mượt mà */
    background-color: #ffffff; /* Màu nền trắng */
    padding: 10px; /* Khoảng cách giữa nội dung và viền */
    margin-bottom: 15px; /* Khoảng cách giữa các div */
    transition: all 0.3s ease-in-out; /* Hiệu ứng hover */
}

.form-floating:hover {
    box-shadow: 0 8px 10px rgba(0, 0, 0, 0.15); /* Shadow đậm hơn khi hover */
    transform: translateY(-2px); /* Nâng nhẹ div khi hover */
}

.card {
    box-shadow: 0 10px 20px rgba(0, 0, 0, 0.08), 0 6px 6px rgba(0, 0, 0, 0.1);
    border-radius: 12px;
    transition: all 0.3s ease-in-out;
}

.btn-save, .btn-cancel {
    margin-bottom: 10px;
}

.btn-cancel {
    background-color: #e0e0e0;
    border: 1px solid #d0d0d0;
    color: #333;
    transition: all 0.3s ease;
}

.btn-cancel:hover {
    background-color: #c8c8c8;
    border-color: #b8b8b8;
    color: #000;
}

input[readonly] {
    background-color: #f0f0f0;  /* Màu nền khác biệt */
    border: 1px solid #ccc;     /* Đường viền nhạt hơn */
    cursor: not-allowed;        /* Thay đổi con trỏ khi di chuột */
}


</style>
</head>
<body>

	<jsp:include page="/WEB-INF/views/layouts/header.jsp" />

	<div class="main-content">
		<jsp:include page="/WEB-INF/views/layouts/navigation.jsp" />

		<div class="container-fluid"
			style="margin-left: 0px; margin-right: 0px;">

			<div class="row g-3 mt-3">
				<div class="col">
					<h2 class="h3">Edit Order</h2>
					<c:if test="${task == 'edit'}">
					    <p class="text-muted small">OrderID: ${order.id}</p>
					</c:if>
					<p>Manage order,.....</p>
				</div>
				<div class="col-auto d-none d-sm-block">
					<img class="page-icon" src="${pageContext.servletContext.contextPath}/resources/images/page.svg"
						width="120px" alt="Page Icon">
				</div>
			</div>
			<form id="orderForm" action="order/edit.htm" method="POST">
				<input type="hidden" name="orderId" value="${order.id }">
				<div class="card mt-3">
					<div class="card-body">
						<h6 class="small text-muted">GENERAL INFORMATION</h6>

						<div class="row">
							<!-- Cột 1 -->
							<div class="col-md-6">
								<div class="form-floating mt-3">
									<input type="datetime-local" class="form-control" id="createdAt" name="createdAt"
										value="<fmt:formatDate value='${order.createdAt}' pattern='yyyy-MM-dd\'T\'HH:mm' />" readonly="readonly"> <label
										class="form-label" for="createdAt">Created At <span
										class="text-danger">*</span></label>
								</div>

								<div class="form-floating mt-3 position-relative">
									<input class="form-control pr-4 numeric-input" id="totalPrice" name="totalPrice" oninput="validateNumberInput(this)"
										type="text" value="${order.totalPrice}" required> <span class="currency-symbol">₫</span> <label
										class="form-label" for="totalPrice">Total Price <span class="text-danger">*</span>
									</label>
								</div>
									
								<div class="form-floating mt-3 position-relative">
								    <select class="form-control" id="paymentMethod" name="paymentMethod" required>
								        <option value="COD" ${order.paymentMethod == 'COD' ? 'selected' : ''}>COD</option>
								        <option value="PayPal" ${order.paymentMethod == 'PayPal' ? 'selected' : ''}>PayPal</option>
								    </select>
								    <label class="form-label" for="paymentMethod">Payment Method <span class="text-danger">*</span></label>
								</div>

								
								
								<div class="form-floating mt-3 position-relative">
								    <select class="form-control" id="orderStatus" name="orderStatus" required>
								        <option value="Chờ xác nhận" ${order.orderStatus == 'Chờ xác nhận' ? 'selected' : ''}>Chờ xác nhận</option>
								        <option value="Xác nhận đơn hàng" ${order.orderStatus == 'Xác nhận đơn hàng' ? 'selected' : ''}>Xác nhận đơn hàng</option>
								        <option value="Đang giao hàng" ${order.orderStatus == 'Đang giao hàng' ? 'selected' : ''}>Đang giao hàng</option>
								        <option value="Hoàn thành" ${order.orderStatus == 'Hoàn thành' ? 'selected' : ''}>Hoàn thành</option>
								        <option value="Huỷ đơn hàng" ${order.orderStatus == 'Huỷ đơn hàng' ? 'selected' : ''}>Huỷ đơn hàng</option>
								    </select>
								    <label class="form-label" for="orderStatus">Order Status <span class="text-danger">*</span></label>
								</div>
								
								<div class="form-floating mt-3 position-relative">
									<c:set var="totalPrice" value="0" />
										<!-- Tính tổng giá trị đơn hàng -->
										<c:forEach var="orderDetail" items="${order.orderDetails}">
										    <c:set var="itemTotal" value="${orderDetail.book.price * orderDetail.quantity}" />
										    <c:set var="totalPrice" value="${totalPrice + itemTotal}"/>
										</c:forEach>
										
										<!-- Kiểm tra xem có mã giảm giá không và tính giá trị giảm -->
										<c:set var="discountAmount" value="0" />
										<c:if test="${not empty order.orderDiscounts[0]}">
										    <c:choose>
										        <c:when test="${order.orderDiscounts[0].discount_id.discountType == 'percentage'}">
										            <c:set var="discountAmount" value="${totalPrice * order.orderDiscounts[0].discount_id.discountValue / 100}" />
										        </c:when>
										        <c:when test="${order.orderDiscounts[0].discount_id.discountType == 'amount'}">
										            <c:set var="discountAmount" value="${order.orderDiscounts[0].discount_id.discountValue}" />
										        </c:when>
										    </c:choose>
										</c:if>
										
										<!-- Tổng giá trị sau giảm giá -->
										<c:set var="totalPriceAfterDiscount" value="${totalPrice - discountAmount}" />
																			
								
								    <input class="form-control pr-4 numeric-input" id="orderDiscount" name="orderDiscount" type="text" value="${discountAmount}" readonly>
								    <label class="form-label" for="totalPrice">Order Discount <span class="text-danger">*</span></label>
								</div>
							</div>

							<!-- Cột 2 -->
						<div class="col-md-6">
							<div class="form-floating mt-3">
								<input class="form-control" id="fullname" name="fullname"
									value="${order.user.fullname}" required readonly="readonly"> <label
									class="form-label" for="fullname">Customer Name <span
									class="text-danger">*</span></label>
							</div>	
							
							<%-- <div class="form-floating mt-3">
								<input class="form-control" id="shippingAddress" name="shippingAddress"
									type="text" value="${order.shippingAddress}" required> <label
									class="form-label" for="shippingAddress">Shipping Addess <span class="text-danger">*</span>
								</label>
								
							</div> --%>
							
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
							        <div class="form-floating">
							            <input class="form-control" id="street" name="street" type="text" value="" required>
							            <label class="form-label" for="street">Số nhà/Đường <span class="text-danger">*</span></label>
							        </div>
							    </div>
							</div>
							

							<div class="form-floating mt-3">
							    <select class="form-control" id="paymentStatus" name="paymentStatus" required>
							        <option value="Đã thanh toán" ${order.paymentStatus == 'Đã thanh toán' ? 'selected' : ''}>Đã thanh toán</option>
							        <option value="Chưa thanh toán" ${order.paymentStatus == 'Chưa thanh toán' ? 'selected' : ''}>Chưa thanh toán</option>
							    </select>
							    <label class="form-label" for="paymentStatus">Payment Status <span class="text-danger">*</span></label>
							</div>

	
							<div class="form-floating mt-3">
								<input type="datetime-local" class="form-control" id="updatedAt" name="updatedAt"
									value="<fmt:formatDate value='${order.updatedAt}' pattern='yyyy-MM-dd\'T\'HH:mm' />" readonly="readonly"> <label
									class="form-label" for="updatedAt">Updated At <span
									class="text-danger">*</span></label>
							</div>
							
							<div class="form-floating mt-3 position-relative">
							    <input class="form-control pr-4 numeric-input" id="originalPrice" name="originalPrice" type="text" value="${order.totalPrice + discountAmount}" readonly> <span class="currency-symbol">₫</span>
							    <label class="form-label" for="totalPrice">Original Price<span class="text-danger">*</span></label>
							</div>
							</div>
						</div>
					</div>
				<div class="mt-3">
					<button class="btn btn-primary btn-save" type="submit" onclick="beforeSubmit()">Save</button>
					<a href="<c:url value='/admin1337/products.htm' />"
						class="btn btn-light btn-cancel">Cancel</a>
				</div>
			</form>
		</div>
		<div>
		<form id="orderItemsForm" action="order/updateOrderItems.htm" method="POST">
			    <input type="hidden" name="orderId" value="${order.id }">
			    <div class="card mt-3">
			        <div class="card-body">
			            <h6 class="small text-muted">ORDER ITEMS</h6>
						
			            <!-- Danh sách các sản phẩm -->
			            <c:forEach var="orderDetail" items="${order.orderDetails}" varStatus="status">
			            <%-- <input type="hidden" name="orderDetailsBookId[${status.index}].bookId" value="${orderDetail.book.id }"> --%>
			            <div class="row">
			                <!-- Sản phẩm 1 -->
			                <div class="col-md-12 mb-3">
			                    <div class="row align-items-center">
			                        <div class="col-md-2" style="width: 150px">
			                            <!-- Ảnh sản phẩm -->
			                            <img src="${orderDetail.book.thumbnail }" alt="${orderDetail.book.title }" class="img-fluid" style="height: 150px;">
			                        </div>
			                        <div class="col-md-4 form-floating" style="box-shadow: none;">
			                            <!-- Tên sản phẩm -->
			                            <input type="text" class="form-control" value="${orderDetail.book.title }" readonly>
			                            <label class="form-label" for="bookTitle">Book Title</label>
			                        </div>
			                        <div class="col-md-2 form-floating" style="box-shadow: none;">
			                            <!-- Giá tiền -->
			                            <input type="text" class="form-control numeric-input" value="${orderDetail.book.price }" readonly> <span class="currency-symbol">₫</span>
			                            <label class="form-label" for="price">Price</label>
			                        </div>
			                        <div class="col-md-2 form-floating" style="box-shadow: none;">
			                            <!-- Số lượng -->
			                            <input type="number" class="form-control " name="orderDetailsQuantity[${orderDetail.id}].quantity" value="${orderDetail.quantity }" min="1" max="${orderDetail.book.inventoryEntity.stock_quantity}" oninput="checkMaxMin(this)" 
			                            oninvalid="this.setCustomValidity('The minimum quantity is 1, and the available stock quantity is ' + this.max + '.')" 
           oninput="this.setCustomValidity('')" data-toggle="tooltip" title="The minimum quantity is 1, and the available stock quantity is ${orderDetail.book.inventoryEntity.stock_quantity}"  >
			                            <label class="form-label" for="quantity">Quantity</label>
			                        </div>
			                        <div class="col-md-2 form-floating" style="box-shadow: none;">
			                            <!-- Tổng tiền sản phẩm -->
			                            <input type="text" class="form-control numeric-input" value="${orderDetail.book.price * orderDetail.quantity}" readonly> <span class="currency-symbol">₫</span>
			                            <label class="form-label" for="totalPrice">Total Price</label>
			                        </div>
			                    </div>
			                </div>
			            </div>
			            </c:forEach>
			
			            <!-- Nút Submit -->
			            <div class="mt-4 text-center">
			                <button type="submit" class="btn btn-primary">Cập nhật số lượng</button>
			            </div>
			        </div>
			    </div>
			</form>
		</div>
			
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
	<script>

	let initialInput = true;

	function validateNumberInput(input) {
	    let value = input.value.replace(/[^0-9]/g, ''); // Xóa tất cả các ký tự không phải là số

	    if (value === "") {
	        input.value = "0"; // Nếu không có giá trị, thiết lập là 0
	        initialInput = true; // Reset lại flag
	        return;
	    }

	    if (initialInput) {
	        value = value.replace(/^0+/, ''); // Loại bỏ các số 0 đầu tiên
	        initialInput = false; // Đặt flag là false khi đã nhập lần đầu
	    }

	    // Thêm dấu phân cách hàng nghìn
	    value = value.replace(/\B(?=(\d{3})+(?!\d))/g, '.');

	    input.value = value; // Cập nhật giá trị của input
	}

	// Hàm reset lại flag khi người dùng thay đổi hoặc cần làm mới
	function resetInitialInputFlag(event) {
	    initialInput = true; // Đặt lại flag về true khi reset
	    let input = event.target;
	    let value = input.value.replace(/\./g, ''); // Loại bỏ dấu phân cách

	    // Chuyển giá trị thành số và gán lại vào input để người dùng nhập tiếp
	    input.value = parseInt(value, 10).toString();
	}

	function formatNumberInputsOnLoad() {
	    let numericInputs = document.querySelectorAll('.numeric-input');

	    numericInputs.forEach(input => {
	        let priceValue = input.value;

	        priceValue = priceValue.split('.')[0]; // Tách phần nguyên của giá trị

	        priceValue = parseInt(priceValue, 10).toString(); // Chuyển thành số nguyên

	        priceValue = priceValue.replace(/\B(?=(\d{3})+(?!\d))/g, '.'); // Thêm dấu phân cách hàng nghìn

	        input.value = priceValue; // Cập nhật lại giá trị cho input
	    });
	}
	
	function beforeSubmit() {
	    // Lấy giá trị của input `totalPrice`
	    let numericInput = document.getElementById('totalPrice');
	    
	    // Lấy giá trị đã được định dạng (có dấu chấm phân cách)
	    let rawValue = numericInput.value.replace(/\./g, ''); 
	    numericInput.value = rawValue; 
	}


	window.onload = function() {
	    formatNumberInputsOnLoad(); // Định dạng lại các input khi tải trang

	    let numericInputs = document.querySelectorAll('.numeric-input');
	    numericInputs.forEach(input => {
	        input.addEventListener('focus', function() {
	            resetInitialInputFlag({ target: input }); // Reset lại khi input lấy focus
	        });
	    });
	}

	
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
	            window.location.href = 'products.htm';
	        }
	    });
	}
	
	
	var locationData = ${locationData};  // Thông qua modelMap.addAttribute("locationData", locationDataJson);
    var orderShippingAddress = "${order.shippingAddress}"; 
    var addressParts = orderShippingAddress.split(",");
    
    var streetAddress = addressParts[0].trim(); // Số nhà/đường
    var ward = addressParts[1].trim(); // Phường xã
    var district = addressParts[2].trim(); // Quận huyện
    var province = addressParts[3].trim(); // Tỉnh thành

    // Lấy các phần tử select trong form
    var provinceSelect = document.getElementById("province");
    var districtSelect = document.getElementById("district");
    var wardSelect = document.getElementById("ward");

    // Điền giá trị Số nhà/đường vào ô input
    document.getElementById('street').value = streetAddress;

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
	</script>
</body>
</html>
