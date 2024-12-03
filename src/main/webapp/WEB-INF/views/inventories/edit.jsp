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
					<h2 class="h3">Edit Inventory</h2>
				    <p class="text-muted small">InventoryID: ${inventory.id}</p>
					<p>Update the stock quantity for this inventory item.</p>
				</div>
				<div class="col-auto d-none d-sm-block">
					<img class="page-icon" src="resources/images/page.svg"
						width="120px" alt="Page Icon">
				</div>
			</div>
			<form id="productForm" action="inventory/edit.htm" method="POST">
				<input type="hidden" id="task" name="task" value="${task}">
				
				<input type="hidden" id="hehe" name="hehe" value="123123">
				<c:if test="${task != 'new'}">
					<input type="hidden" id="id" name="id" value="${inventory.id}">
				</c:if>

				<div class="card mt-3">
					<div class="card-body">
						<h6 class="small text-muted">GENERAL INFORMATION</h6>

						<div class="form-floating mt-3">
							<input class="form-control numeric-input" id="stock_quantity" name="stock_quantity"
								value="${inventory.stock_quantity}" oninput="validateNumberInput(this)" required> <label
								class="form-label" for="stock_quantity">Stock quantity <span
								class="text-danger">*</span></label>
						</div>
						<c:if test="${not empty errorstock_quantity}">
                        	 <div class="text-danger">${errorstock_quantity}</div>
                        </c:if>
		                        
		                        
					</div>
				</div>

				<div class="mt-3">
					<button class="btn btn-primary btn-save" type="submit">Save</button>
					<a href="<c:url value='/inventories.htm' />"
						class="btn btn-light btn-cancel">Cancel</a>
				</div>
			</form>


		</div>

	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
	<script>

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
		            window.location.href = '${pageContext.servletContext.contextPath}/inventories.htm';
		        }
		    });
		}
		
		
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
	


	</script>
</body>
</html>
