<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

	<base href="${pageContext.servletContext.contextPath}/admin1337/">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
	<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
	<!-- Thêm Toastr từ CDN -->
	<link href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/css/toastr.min.css" rel="stylesheet">
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/js/toastr.min.js"></script>


	<link href="https://cdn.jsdelivr.net/npm/select2@4.0.13/dist/css/select2.min.css" rel="stylesheet" >
	<script src="https://cdn.jsdelivr.net/npm/select2@4.0.13/dist/js/select2.min.js"></script>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
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
	top: 50%;
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

#subcategory-container .select2-container {
    width: 100% !important; 
}

#subcategory-container .select2-selection--multiple {
    height: 58px;
    text-align: center;
    display: inline-flex;
    justify-content: center;
    align-items: center;
    width: 100%;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); /* Shadow nhẹ */
    border-radius: 8px; /* Bo góc mượt mà */
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
					<h2 class="h3">
						<c:choose>
							<c:when test="${task == 'edit'}">Edit Discount</c:when>
							<c:otherwise>New Discounts</c:otherwise>
						</c:choose>
					</h2>
					<c:if test="${task == 'edit'}">
					    <p class="text-muted small">DiscountID: ${discount.id}</p>
					</c:if>
					<p>Manage discount code, type, value,....</p>
				</div>
				<div class="col-auto d-none d-sm-block">
					<img class="page-icon" src="${pageContext.servletContext.contextPath}/resources/images/page.svg"
						width="120px" alt="Page Icon">
				</div>
			</div>
			<form id="discountForm" action="discount/edit" method="POST" >
				<input type="hidden" value="${discount.id }" name="discount_id">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				<div class="card mt-3">
					<div class="card-body">
						<h6 class="small text-muted">GENERAL INFORMATION</h6>

						<div class="row">
							<!-- Cột 1 -->
							<div class="col-md-6">
								<div class="form-floating mt-3 position-relative">
									<input class="form-control" id="code" name="code"
										value="${discount.code}" required oninput="convertToUpperCase(this);"> <label
										class="form-label" for="code">Code <span
										class="text-danger">*</span></label>
								</div>

								<div class="form-floating mt-3 position-relative">
								    <input class="form-control numeric-input" id="discountValue" name="discountValue" oninput="validateNumberInput(this)" value="${discount.discountValue}" required>
								    <label class="form-label" for="discountValue">
								        Discount Value <span class="text-danger">*</span>
								    </label>
								
								    <span class="currency-symbol" id="currencySymbol"></span>
								</div>


								<div class="form-floating mt-3 position-relative">
								    <input class="form-control" id="startDate" name="startDate" type="datetime-local" 
								           value="<fmt:formatDate value='${discount.startDate}' pattern='yyyy-MM-dd\'T\'HH:mm'/>" 
								           required> 
								    <label class="form-label" for="startDate">Start Date <span class="text-danger">*</span></label>
								</div>

								
								<div class="form-floating mt-3 position-relative">
								    <input class="form-control  numeric-input" id="minOrderValue" name="minOrderValue"  oninput="validateNumberInput(this)"
										value="${discount.minOrderValue}" required> <span class="currency-symbol">₫</span> <label
										class="form-label" for="minOrderValue">Min Order Value <span
										class="text-danger">*</span></label>
								</div>

								<div class="form-floating mt-3 position-relative">
								    <select class="form-control" id="status" name="status" required>
								        <option value="active" ${discount.status == 'active' ? 'selected' : ''}>Active</option>
								        <option value="inactive" ${discount.status == 'inactive' ? 'selected' : ''}>Inactive</option>
								        <option value="expired" ${discount.status == 'expired' ? 'selected' : ''}>Expired</option>
								    </select>
								    <label class="form-label" for="status">Status<span class="text-danger">*</span></label>
								</div>

								
								<div class="form-floating mt-3 position-relative">
								    <input class="form-control" id="updatedAt" name="updatedAt" type="datetime-local" 
										value="<fmt:formatDate value='${discount.updatedAt}' pattern='yyyy-MM-dd\'T\'HH:mm' />" disabled="disabled"> <label
										class="form-label" for="updatedAt">Updated At</label>
								</div>
								
							</div>

							<!-- Cột 2 -->
							<div class="col-md-6">
							<div class="form-floating mt-3">
							    <select class="form-control" id="discountType" name="discountType" required>
							        <option value="percentage" ${discount.discountType == 'percentage' ? 'selected' : ''}>Percentage</option>
							        <option value="amount" ${discount.discountType == 'amount' ? 'selected' : ''}>Amount</option>
							        <option value="product-based" ${discount.discountType == 'product-based' ? 'selected' : ''}>Product-based</option>
							        <option value="fixed" ${discount.discountType == 'fixed' ? 'selected' : ''}>Fixed</option>
							    </select>
							    <label class="form-label" for="discountType">Discount Type <span class="text-danger">*</span></label>
							</div>

							
							<div class="form-floating mt-3">
							    <select class="form-control" id="applyTo" name="applyTo" required onchange="showSubcategorySelect()" disabled="disabled">
							        <option value="user" ${discount.applyTo == 'user' ? 'selected' : ''}>User</option>
							        <option value="products" ${discount.applyTo == 'products' ? 'selected' : ''}>Products</option>
							        <option value="categories" ${discount.applyTo == 'categories' ? 'selected' : ''}>Categories</option>
							    </select>
							    <input type="hidden" value="${discount.applyTo}" name="applyTo">
							    <label class="form-label" for="applyTo">Apply To <span class="text-danger">*</span></label>
							</div>

							<div class="form-floating mt-3" id="subcategory-container" style="display: none;">
							    <div class="row">
							        <!-- Category Select -->
							        <div class="col-6">
							            <div class="form-floating">
							                <select class="form-control" id="category" name="category" onchange="loadSubcategories()">
							                   <!--  <option value="" disabled selected>Select Category</option> -->
							                    <c:forEach var="category" items="${listCategories }">
							                        <option value="${category.id}" ${category.id == category_id ? 'selected' : '' }>${category.name}</option>
							                    </c:forEach>
							                </select>
							                <input type="hidden" value="" name="category">
							                <label class="form-label" for="category">Select Category</label>
							            </div>
							        </div>
							
							        <!-- Subcategory Select -->
							        <div class="col-6">
							            <div class="form-floating">
							               <select class="form-control" id="subcategory" name="subcategory[]" multiple="multiple">
											    <c:forEach var="subcategory" items="${listSubCategories}">
											        <option value="${subcategory.id}"
											            <c:if test="${fn:contains(subcategories_id, subcategory.id)}">
											                selected
											            </c:if>
											        >${subcategory.name}</option>
											    </c:forEach>
											</select>
							                <label class="form-label" for="subcategory">Select Subcategories</label>
							            </div>
							        </div>
							    </div>
							</div>

							<div class="form-floating mt-3">
								<input class="form-control" id="endDate" name="endDate" type="datetime-local" min=""
									value="<fmt:formatDate value='${discount.endDate}' pattern='yyyy-MM-dd\'T\'HH:mm' />" required> <label
									class="form-label" for="endDate">End Date <span
									class="text-danger">*</span></label>
							</div>
							
							<div class="form-floating mt-3 ">
								<input type="number" class="form-control numeric-input" id="maxUses" name="maxUses" oninput="validateNumberInput(this)"
									value="${discount.maxUses}" required> <label class="form-label"
									for="maxUses">Max Uses <span
									class="text-danger">*</span></label>
							</div>
							
							<div class="form-floating mt-3">
								<input class="form-control" id="createdAt" name="createdAt" type="datetime-local" 
									value="<fmt:formatDate value='${discount.createdAt}' pattern='yyyy-MM-dd\'T\'HH:mm' />" disabled="disabled"> <label class="form-label"
									for="createdAt">Created At</label>
							</div>
								
							</div>
						</div>
					</div>
					</div>
					<div class="mt-3">
						<button class="btn btn-primary btn-save" type="submit">Save</button>
						<a href="<c:url value='discounts' />"
							class="btn btn-light btn-cancel">Cancel</a>
					</div>
			</form>


		</div>

	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
	<script>

	let initialInput = true;

	function validateNumberInput(input) {
	    let value = input.value.replace(/[^0-9]/g, '');

	    if (value === "") {
	        input.value = "0";
	        initialInput = true;
	        return;
	    }

	    if (initialInput) {
	        value = value.replace(/^0+/, '');
	        initialInput = false;
	    }

	    value = value.replace(/\B(?=(\d{3})+(?!\d))/g, '.');

	    input.value = value;
	}

	function resetInitialInputFlag(event) {
	    initialInput = true;
	}

	function formatNumberInputsOnLoad() {
	    let numericInputs = document.querySelectorAll('.numeric-input');
	    numericInputs.forEach(input => {
	        let priceValue = input.value;
	
	        // Kiểm tra nếu giá trị không phải là null hoặc undefined và có chứa dấu chấm
	        if (priceValue && priceValue.indexOf('.') !== -1) {
	            priceValue = priceValue.split('.')[0]; // Tách phần thập phân
	        }

	        // Chuyển giá trị thành số nguyên nếu nó không phải là NaN
	        priceValue = parseInt(priceValue, 10).toString();

	        // Định dạng số theo dạng nhóm ba chữ số (nghìn)
	        priceValue = priceValue.replace(/\B(?=(\d{3})+(?!\d))/g, '.');

	        // Cập nhật giá trị input
	        input.value = priceValue;
	        console.log(input.id + ": " + priceValue);
	    });
	}
	
	window.onload = function() {}
		formatNumberInputsOnLoad();
	;


	document.getElementById('discountForm').onsubmit = function() {
	    let numericInputs = document.querySelectorAll('.numeric-input');

	    numericInputs.forEach(input => {
	        input.value = input.value.replace(/\./g, '');
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
	            window.location.href = '${pageContext.servletContext.contextPath}/products';
	        }
	    });
	}
	
	function setDateLimits() {
	    // Lấy giá trị của startDate
	    const startDateValue = document.getElementById("startDate").value;

	    // Set min cho EndDate là giá trị của StartDate (nếu có)
	    if (startDateValue) {
	    	document.getElementById("startDate").setAttribute("min", startDateValue);
	        document.getElementById("endDate").setAttribute("min", startDateValue);
	    }
	}

	function validateDateRange() {
	    const startDate = document.getElementById("startDate").value;
	    const endDate = document.getElementById("endDate").value;

	    // Nếu StartDate có giá trị và EndDate có giá trị, kiểm tra xem EndDate có nhỏ hơn StartDate không
	    if (startDate && endDate) {
	        if (new Date(endDate) < new Date(startDate)) {
	            // Cảnh báo và thông báo lỗi nếu EndDate nhỏ hơn StartDate
	            toastr.error("End Date must be greater than Start Date!");
	            document.getElementById("endDate").setCustomValidity("End Date must be greater than Start Date.");
	            event.preventDefault();
	        } else {
	            // Nếu hợp lệ, reset lại validation cho EndDate
	            document.getElementById("endDate").setCustomValidity("");
	        }
	    }
	}


	
	window.onload = function() {
		formatNumberInputsOnLoad();
		
	    let numericInputs = document.querySelectorAll('.numeric-input');
	    numericInputs.forEach(input => {
	        input.addEventListener('focus', resetInitialInputFlag);
	    });
	    setDateLimits();
	};
	// Cập nhật min của EndDate khi StartDate thay đổi
	document.getElementById("startDate").addEventListener("change", function() {
	    const startDate = document.getElementById("startDate").value;

	    // Cập nhật min cho EndDate mỗi khi StartDate thay đổi, EndDate không thể nhỏ hơn StartDate
	    document.getElementById("endDate").setAttribute("min", startDate);

	    // Kiểm tra lại nếu EndDate không hợp lệ
	    validateDateRange();
	});

	// Kiểm tra khi EndDate thay đổi
	document.getElementById("endDate").addEventListener("change", validateDateRange);
	
		
	
	function showSubcategorySelect() {
	    var applyTo = document.getElementById('applyTo').value;
	    var subcategoryContainer = document.getElementById('subcategory-container');
	    var categoryInput = document.getElementById('category');
	    var subcategoryInput = document.getElementById('subcategory');
	    if (applyTo === 'categories') {
	        subcategoryContainer.style.display = 'block';
	        subcategoryInput.setAttribute('required', 'true');
	        categoryInput.setAttribute('required', 'true');
	    } else {
	        subcategoryContainer.style.display = 'none';
	        categoryInput.removeAttribute('required');
	        subcategoryInput.removeAttribute('required');
	    }
	}

	// Gọi hàm này khi trang được load để kiểm tra giá trị hiện tại của Apply To
	window.onload = function() {
	    showSubcategorySelect();
	};
	
	
	$(document).ready(function() {
	    // Khởi tạo Select2 với cấu hình cho phép xóa lựa chọn và hiển thị placeholder
	    $('#subcategory').select2({
	        //placeholder: "Select an option",  // Đặt placeholder cho select2
	        //allowClear: true, // Cho phép xóa lựa chọn
	    }).on('select2:unselecting', function(e) {
	        // Đảm bảo chọn lại option mặc định khi người dùng xóa lựa chọn
	        $(this).val('').trigger('change');
	    });
	});
	
	
	// Lấy subcategory khi chọn category
	function loadSubcategories() {
    var categoryId = document.getElementById('category').value;

    // Nếu chưa chọn category, không làm gì
    if (!categoryId) {
        return;
    }

    // Sử dụng fetch để lấy subcategories từ server, nối chuỗi bằng toán tử "+"
    fetch("http://localhost:8080/bookstore/discount/getSubcategories?categoryId=" + categoryId)
        .then(response => {
            // Kiểm tra xem yêu cầu có thành công hay không
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.text(); // Nhận phản hồi dưới dạng text (HTML)
        })
        .then(data => {
            // Lấy select subcategory
            var subcategorySelect = document.getElementById('subcategory');
            
            for (var i = 0; i < subcategorySelect.options.length; i++) {
                subcategorySelect.options[i].selected = false;  // Bỏ chọn tất cả các options
            }
            
            // Xóa các option cũ
            subcategorySelect.innerHTML = data; // Đặt trực tiếp nội dung HTML vào select
        })
        .catch(error => {
            console.error('Error fetching subcategories:', error);
        });
	}

	document.addEventListener('DOMContentLoaded', function() {
	    var discountType = document.getElementById('discountType');
	    var currencySymbol = document.getElementById('currencySymbol'); // Thẻ hiển thị ký hiệu

	    // Kiểm tra nếu phần tử tồn tại trước khi thực thi
	    if (discountType && currencySymbol) {
	        // Lắng nghe sự kiện thay đổi trên discountType
	        discountType.addEventListener('change', function() {
	            var selectedValue = this.value;

	            // Kiểm tra giá trị của discountType để quyết định symbol
	            if (selectedValue === 'percentage') {
	                // Nếu chọn "percentage", hiển thị ký hiệu phần trăm (%)
	                currencySymbol.textContent = '%';
	            } else {
	                // Nếu chọn các loại khác, hiển thị đồng tiền (₫)
	                currencySymbol.textContent = '₫';
	            }
	        });

	        // Cập nhật lại ký hiệu khi trang tải lần đầu
	        // Kiểm tra giá trị ban đầu của discountType và cập nhật ký hiệu tương ứng
	        if (discountType.value === 'percentage') {
	            currencySymbol.textContent = '%';
	        } else {
	            currencySymbol.textContent = '₫';
	        }
	    } else {
	        console.error("Không tìm thấy phần tử #discountType hoặc #currencySymbol");
	    }
	});

	  function toggleFields() {
          var applyTo = document.getElementById('applyTo').value;  // Lấy giá trị của applyTo
          var minOrderValueField = document.getElementById('minOrderValue');  // Lấy phần tử minOrderValue
          var maxUsesField = document.getElementById('maxUses');  // Lấy phần tử maxUses

          // Nếu applyTo là 'categories', thì disable minOrderValue và maxUses
          if (applyTo === 'categories') {
              minOrderValueField.disabled = true;
              maxUsesField.disabled = true;
          } else {
              minOrderValueField.disabled = false;
              maxUsesField.disabled = false;
          }
      }

      // Khi trang tải xong, kiểm tra và áp dụng thuộc tính disabled nếu cần
      $(document).ready(function() {
          toggleFields();
          
          // Thêm sự kiện khi thay đổi giá trị của select applyTo
          $('#applyTo').change(function() {
              toggleFields();
          });
      });

      function convertToUpperCase(inputElement) {
  	    // Đổi giá trị của input thành chữ in hoa
  	    inputElement.value = inputElement.value.toUpperCase();
  	}
	</script>
</body>
</html>
