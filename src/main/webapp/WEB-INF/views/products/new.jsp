<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><c:choose>
		<c:when test="${task == 'edit'}">Edit Product</c:when>
		<c:otherwise>New Product</c:otherwise>
	</c:choose></title>

<base href="${pageContext.servletContext.contextPath}/">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
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
    transform: none;
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
    background-color: #e0e0e0; /* Màu nền xám đậm hơn */
    border: 1px solid #d0d0d0; /* Đường viền nhấn nhá */
    color: #333; /* Màu chữ tối hơn */
    transition: all 0.3s ease; /* Hiệu ứng mượt */
}

.btn-cancel:hover {
    background-color: #c8c8c8; /* Màu nền khi hover */
    border-color: #b8b8b8; /* Đường viền khi hover */
    color: #000; /* Màu chữ đậm hơn khi hover */
}

select.form-control {
    max-width: 100%; /* Không vượt quá kích thước container */
    width: 100%; /* Chiếm toàn bộ không gian của form-floating */
    transition: none; /* Loại bỏ hiệu ứng hover */
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
							<c:when test="${task == 'edit'}">Edit Product</c:when>
							<c:otherwise>New Product</c:otherwise>
						</c:choose>
					</h2>
					<p>Manage users, roles, permissions, and profile.</p>
				</div>
				<div class="col-auto d-none d-sm-block">
					<img class="page-icon" src="resources/images/page.svg"
						width="120px" alt="Page Icon">
				</div>
			</div>
			<form id="productForm"
				action="${pageContext.servletContext.contextPath}/product/add.htm"
				method="POST" enctype="multipart/form-data">
				<input type="hidden" id="task" name="task" value="${task}">

				<c:if test="${task != 'new'}">
					<input type="hidden" id="id" name="id" value="${book.id}">
				</c:if>

				<div class="card mt-3">
					<div class="card-body">
						<h6 class="small text-muted">GENERAL INFORMATION</h6>

						<div class="row">
							<!-- Cột 1 -->
							<div class="col-md-6">
								<div class="form-floating mt-3">
									<input class="form-control" id="title" name="title"
										value="" required> <label
										class="form-label" for="title">Title <span
										class="text-danger">*</span></label>
								</div>

								<div class="form-floating mt-3">
									<input class="form-control" id="author" name="author"
										value="" required> <label
										class="form-label" for="author">Author <span
										class="text-danger">*</span></label>
								</div>

								<div class="form-floating mt-3">
								    <textarea class="form-control" id="description" name="description" rows="5"></textarea>
								    <label class="form-label" for="description">Description</label>
								</div>
								
								<div class="form-floating mt-3 position-relative">
									<input class="form-control pr-4" id="publication_year" name="publication_year"
										type="text" value="0" max="2024" oninput="validateYearInput(this)" required> <label
										class="form-label" for="publication_year">Publication year <span class="text-danger">*</span>
									</label>
								</div>
								
								<div class="form-floating mt-3 position-relative">
									<input class="form-control pr-4 numeric-input" id="price" name="price" oninput="validateNumberInput(this)"
										type="text" value="0" required> <span class="currency-symbol">₫</span> <label
										class="form-label" for="price">Price <span class="text-danger">*</span>
									</label>
								</div>
								
								<div class="form-floating mt-3">
									<input class="form-control" id="language" name="language" value="" required> <label
										class="form-label" for="title">Language <span
										class="text-danger">*</span></label>
								</div>
								
								<div class="form-floating mt-3">
								    <input class="form-control" id="thumbnail" name="thumbnail" type="file" accept="image/*">
								    <label class="form-label" for="thumbnail">Thumbnail</label>
								</div>
	
							</div>

							<!-- Cột 2 -->
							<div class="col-md-6">
<!-- 								<div class="form-floating mt-3"> -->
<!-- 									<input class="form-control" id="category" name="category" -->
<%-- 										value="${book.category.name}"> <label --%>
<!-- 										class="form-label" for="category">Category <span class="text-danger">*</span></label> -->
<!-- 								</div> -->
<div class="form-floating mt-3">
    <select class="form-control" id="category" name="category" onchange="loadSubcategories(this.value)">
        <option value="" disabled selected>Select an option</option>
        <!-- Lặp qua danh sách danh mục từ server -->
        <c:forEach var="category" items="${listCategories}">
            <option value="${category.id}">
                ${category.name}
            </option>
        </c:forEach>
    </select>
    <label class="form-label" for="category">Category <span class="text-danger">*</span></label>
</div>

<div class="form-floating mt-3">
    <select class="form-control" id="subcategory" name="subcategory" onchange="loadCategory(this.value)">
        <option value="" disabled selected>Select an option</option>
        <!-- Lặp qua danh sách danh mục từ server -->
        <c:forEach var="subcategory" items="${listSubcategories}">
            <option value="${subcategory.id}">
                ${subcategory.name}
            </option>
        </c:forEach>
    </select>
    <label class="form-label" for="category">Subcategory <span class="text-danger">*</span></label>
</div>

<!-- 								<div class="form-floating mt-3"> -->
<!-- 									<input class="form-control" id="supplier" name="supplier" -->
<%-- 										value="${book.supplier.name}"> <label --%>
<!-- 										class="form-label" for="supplier">Supplier <span class="text-danger">*</span></label> -->
<!-- 								</div> -->

<div class="form-floating mt-3">
    <select class="form-control" id="supplier" name="supplier">
        <option value="" disabled selected>Select an option</option>
        <!-- Lặp qua danh sách danh mục từ server -->
        <c:forEach var="supplier" items="${listSuppliers}">
            <option value="${supplier.id}" ${supplier.id == book.supplier.id ? 'selected' : ''}>
                ${supplier.name}
            </option>
        </c:forEach>
    </select>
    <label class="form-label" for="category">Supplier <span class="text-danger">*</span></label>
</div>

								<div class="form-floating mt-3">
									<input class="form-control numeric-input" id="quantity" oninput="validateNumberInput(this)"
										name="quantity" value="0"> <label
										class="form-label" for="quantity">Stock quantity <span class="text-danger">*</span></label>
								</div>
								
								<div class="form-floating mt-3 position-relative">
									<input class="form-control pr-4 numeric-input" id="page_count" name="page_count" oninput="validateNumberInput(this)"
										type="text" value="0" required> <label
										class="form-label" for="price">Page count <span class="text-danger">*</span>
									</label>
								</div>
								
								<div class="form-floating mt-3">
								    <select class="form-control" id="status" name="status">
								        <option value="" disabled selected>Select an option</option>
								            <option value="0">Disable</option>
								            <option value="1">Enable</option>
								    </select>
								    <label class="form-label" for="category">Status <span class="text-danger">*</span></label>
								</div>
								
								
								<div class="form-floating mt-3">
								    <input class="form-control" id="images" name="images" type="file" accept="image/*" multiple>
								    <label class="form-label" for="images">Images</label>
								</div>
								
							</div>
						</div>
					</div>
				</div>

				<div class="mt-3">
					<button class="btn btn-primary btn-save" type="submit">Save</button>
					<a href="<c:url value='/products.htm' />"
						class="btn btn-light btn-cancel">Cancel</a>
				</div>
			</form>


		</div>

	</div>

	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
	
	<script>
		let initialInput = true;
	
		function validateNumberInput(input) {
		    // Loại bỏ tất cả ký tự không phải số
		    let value = input.value.replace(/[^0-9]/g, '');

		    // Nếu trống, đặt về "0"
		    if (value === "") {
		        input.value = "0"; // Đặt lại giá trị input thành "0"
		        return; // Dừng hàm
		    }

		    // Loại bỏ số 0 ở đầu nếu không phải chỉ là "0"
		    value = value.replace(/^0+/, '') || "0";

		    // Thêm định dạng phân cách hàng nghìn
		    value = value.replace(/\B(?=(\d{3})+(?!\d))/g, '.');

		    // Cập nhật lại giá trị input
		    input.value = value;
		}
	
		function resetInitialInputFlag(event) {
		    initialInput = true;
		}
	
		function formatNumberInputsOnLoad() {
	
		    let numericInputs = document.querySelectorAll('.numeric-input');
	
		    numericInputs.forEach(input => {
		        let priceValue = input.value;
	
		        priceValue = priceValue.split('.')[0]; 
	
		        priceValue = parseInt(priceValue, 10).toString();
	
		        priceValue = priceValue.replace(/\B(?=(\d{3})+(?!\d))/g, '.');
	
		        input.value = priceValue;
		    });
		}
	
		window.onload = function() {
		    formatNumberInputsOnLoad();
	
		    let numericInputs = document.querySelectorAll('.numeric-input');
		    numericInputs.forEach(input => {
		        input.addEventListener('focus', resetInitialInputFlag);
		    });
		};
	
		document.getElementById('productForm').onsubmit = function() {
		    let numericInputs = document.querySelectorAll('.numeric-input');
	
		    numericInputs.forEach(input => {
		        input.value = input.value.replace(/\./g, '');
		    });
		}
		
		function validateYearInput(input) {
		    let value = input.value.replace(/[^0-9]/g, '');

		    if (!value) {
		        input.value = "0";
		        return;
		    }

		    value = value.replace(/^0+/, '') || "0";

		    if (parseInt(value) > 2024) {
		        value = new Date().getFullYear().toString();
		    }
		    
		    input.value = value;
		}
		
		function loadSubcategories(categoryId) {
		    if (!categoryId) {
		        document.getElementById('subcategory').innerHTML = '<option value="" disabled selected>Select a subcategory</option>';
		        return;
		    }

		    // Gửi yêu cầu AJAX đến server
		    fetch('/bookstore/product/getSubcategories.htm?categoryId=' + categoryId)
		    .then(response => response.text())
		    .then(data => {
		        const subcategorySelect = document.getElementById('subcategory');
		        subcategorySelect.innerHTML = data; // Thêm trực tiếp HTML trả về vào dropdown
		    })
		    .catch(error => console.error('Error:', error));

		}
		
		function loadCategory(subcategoryId) {
		    if (!subcategoryId) return;

		    fetch(`/bookstore/product/getCategory.htm?subcategoryId=` + subcategoryId)
		        .then(response => response.text()) // Đọc response dạng text
		        .then(data => {
		            const parser = new DOMParser();
		            const doc = parser.parseFromString(data, 'text/html');
		            const categoryId = doc.querySelector('#categoryId')?.textContent; // Lấy ID từ HTML

		            if (categoryId) {
		                const categorySelect = document.getElementById('category');
		                const options = categorySelect.options;

		                for (let i = 0; i < options.length; i++) {
		                    if (options[i].value === categoryId) {
		                        options[i].selected = true; // Đặt trạng thái selected
		                        break;
		                    }
		                }
		            }
		        })
		        .catch(error => console.error('Error:', error));
		}

		
	</script>
</body>
</html>
