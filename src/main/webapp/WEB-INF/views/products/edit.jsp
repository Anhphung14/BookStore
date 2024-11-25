<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>
		<c:choose>
			<c:when test="${task == 'edit'}">Edit Product</c:when>
			<c:otherwise>New Product</c:otherwise>
		</c:choose>
	</title>

	<base href="${pageContext.servletContext.contextPath}/">
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
					<c:if test="${task == 'edit'}">
					    <p class="text-muted small">BookID: ${book.id}</p>
					</c:if>
					<p>Manage users, roles, permissions, and profile.</p>
				</div>
				<div class="col-auto d-none d-sm-block">
					<img class="page-icon" src="resources/images/page.svg"
						width="120px" alt="Page Icon">
				</div>
			</div>
			<form id="productForm"
				action="${pageContext.servletContext.contextPath}/product/edit.htm"
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
										value="${book.title}" required> <label
										class="form-label" for="title">Title <span
										class="text-danger">*</span></label>
								</div>

								<div class="form-floating mt-3">
									<input class="form-control" id="author" name="author"
										value="${book.author}" required> <label
										class="form-label" for="author">Author <span
										class="text-danger">*</span></label>
								</div>

								<div class="form-floating mt-3">
    <textarea class="form-control" id="description" name="description" rows="5">${book.description}</textarea>
    <label class="form-label" for="description">Description</label>
</div>
								
								<div class="form-floating mt-3 position-relative">
									<input class="form-control pr-4" id="publication_year" name="publication_year"
										type="text" value="${book.publication_year}" max="2024" required> <label
										class="form-label" for="publication_year">Publication year <span class="text-danger">*</span>
									</label>
								</div>
								
								<div class="form-floating mt-3 position-relative">
									<input class="form-control pr-4 numeric-input" id="price" name="price" oninput="validateNumberInput(this)"
										type="text" value="${book.price}" required> <span class="currency-symbol">₫</span> <label
										class="form-label" for="price">Price <span class="text-danger">*</span>
									</label>
								</div>


								
								<div class="form-floating mt-3 position-relative">
									<input class="form-control pr-4 numeric-input" id="page_count" name="page_count" oninput="validateNumberInput(this)"
										type="text" value="${book.page_count}" required> <label
										class="form-label" for="price">Page count <span class="text-danger">*</span>
									</label>
								</div>
								
								<div class="form-floating mt-3">
								    <!-- Hiển thị ảnh thumbnail hiện tại nếu có -->
								    <c:if test="${book.thumbnail != null && !book.thumbnail.isEmpty()}">
								        <img src="${book.thumbnail}" alt="Thumbnail" 
								             class="img-thumbnail mb-3" style="max-width: 200px;">
								    </c:if>
								
								    <!-- Input file để chọn ảnh mới -->
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
    <select class="form-control" id="category" name="category">
        <option value="" disabled selected>Chọn danh mục</option>
        <!-- Lặp qua danh sách danh mục từ server -->
        <c:forEach var="category" items="${listCategories}">
            <option value="${category.id}" ${category.id == book.category.id ? 'selected' : ''}>
                ${category.name}
            </option>
        </c:forEach>
    </select>
    <label class="form-label" for="category">Category <span class="text-danger">*</span></label>
</div>

<!-- 								<div class="form-floating mt-3"> -->
<!-- 									<input class="form-control" id="supplier" name="supplier" -->
<%-- 										value="${book.supplier.name}"> <label --%>
<!-- 										class="form-label" for="supplier">Supplier <span class="text-danger">*</span></label> -->
<!-- 								</div> -->

<div class="form-floating mt-3">
    <select class="form-control" id="supplier" name="supplier">
        <option value="" disabled selected>Chọn danh mục</option>
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
										name="quantity" value="${quantity}"> <label
										class="form-label" for="quantity">Stock quantity <span class="text-danger">*</span></label>
								</div>
								
								<div class="form-floating mt-3">
									<input class="form-control" id="language" name="language"
										value="${book.language}" required> <label
										class="form-label" for="title">Language <span
										class="text-danger">*</span></label>
								</div>

								<div class="form-floating mt-3">
									<input class="form-control" id="create_at" name="create_at" 
										value="<fmt:formatDate value='${book.created_at}' pattern='dd-MM-yyyy HH:mm' />"
										disabled="disabled"> <label class="form-label"
										for="create_at">Create at</label>
								</div>
								
								<div class="form-floating mt-3">
									<input class="form-control" id="updated_at" name="updated_at" 
										value="<fmt:formatDate value='${book.updated_at}' pattern='dd-MM-yyyy HH:mm' />"
										disabled="disabled"> <label class="form-label"
										for="updated_at">Update at</label>
								</div>
								
								<div class="form-floating mt-3">
								    <!-- Hiển thị các ảnh hiện tại nếu có -->
								    <c:if test="${not empty book.images}">
								        <div class="mb-3">
								            <c:forEach var="image" items="${fn:split(book.images, ';')}">
								                <img src="${image}" alt="Image" 
								                     class="img-thumbnail mb-2" style="max-width: 100px;">
								            </c:forEach>
								        </div>
								    </c:if>
								
								    <!-- Input để chọn nhiều ảnh mới -->
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
	            window.location.href = '${pageContext.servletContext.contextPath}/products.htm';
	        }
	    });
	}
	</script>
</body>
</html>