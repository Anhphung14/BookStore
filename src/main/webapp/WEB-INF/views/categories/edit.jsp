<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><c:choose>
		<c:when test="${task == 'edit'}">Edit Category</c:when>
		<c:otherwise>New Category</c:otherwise>
	</c:choose></title>
<base href="${pageContext.servletContext.contextPath}/admin1337/">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<script src="${pageContext.request.contextPath}/resources/js/app.js"
	defer></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/confirmBox.js"></script>
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
/* Style for the buttons to appear next to each other */
.action-buttons {
	display: none;
	margin-top: 10px;
}

.action-buttons button {
	margin-right: 10px;
}

.no-bullet {
	list-style-type: none;
}

.subcategory-item {
	display: flex;
	justify-content: space-between;
	align-items: center;
	padding: 0 0;
}

.subcategory-text {
	flex-grow: 1;
	padding-left: 3px;
	padding-bottom: 1px;
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
							<c:when test="${task == 'edit'}">Edit Category</c:when>
							<c:otherwise>New Category</c:otherwise>
						</c:choose>
					</h2>
				</div>
				<div class="col-auto d-none d-sm-block">
					<img class="page-icon" src="${pageContext.servletContext.contextPath}/resources/images/page.svg"
						width="120px" alt="Page Icon">
				</div>
			</div>
			<form id="categoryForm"
				action="category/save.htm"
				method="POST">
				<input type="hidden" id="task" name="task" value="${task}">
				<c:if test="${task != 'new'}">
					<input type="hidden" id="id" name="id" value="${category.id}">
				</c:if>
				<div class="card">
					<div class="card-body">
						<h6 class="small text-muted">GENERAL INFORMATION</h6>
						<div class="form-floating mt-3">
							<input class="form-control" id="name" name="name"
								value="${category.name}" required> <label
								class="form-label" for="name">Name <span
								class="text-danger">*</span></label>
						</div>
						<c:if test="${task == 'edit'}">
							<h6 class="small text-muted mt-4">Subcategories</h6>
							<div class="mt-3">
								<ul id="subcategoryList" class="no-bullet">
									<c:forEach var="subcategory"
										items="${category.subcategoriesEntity}">
										<li class="subcategory-item"><input type="checkbox"
											class="subcategoryCheckbox" name="subcategoryIdsToEdit"
											value="${subcategory.id}" data-name="${subcategory.name}"><span
											class="subcategory-text">${subcategory.name}</span> <a
											href="javascript:void(0);"
											class="btn btn-rounded edit-button"
											data-id="${subcategory.id}" data-name="${subcategory.name}">
												<i class="fa fa-pencil"></i>
										</a></li>
									</c:forEach>
								</ul>
								<div id="deleteButtons" class="action-buttons">
									<button id="deleteBtn" type="button" class="btn btn-danger"
										style="display: none;" onclick="confirmDelete()">Delete</button>
								</div>
							</div>
						</c:if>
						<div class="form-floating mt-3">
							<input class="form-control" id="subcategoryNames"
								name="subcategoryNames"> <label class="form-label"
								for="Subcategories">Subcategories<span
								class="text-danger">*</span></label>
						</div>
					</div>
				</div>
				<div class="mt-3">
					<button class="btn btn-primary btn-save" type="submit">Save</button>
					<a href="<c:url value='/admin1337/categories.htm' />"
						class="btn btn-light btn-cancel">Cancel</a>
				</div>
			</form>
			<div id="editSubcategoryFormContainer" style="display: none;"
				class="mt-3">
				<hr>
				<h6>Edit Subcategory</h6>
				<form id="editSubcategoryForm"
					action="category/saveSubcategory.htm"
					method="POST">
					<input type="hidden" id="editSubcategoryId" name="subcategoryId">
					<div class="form-floating mt-3">
						<input class="form-control" id="editSubcategoryName" name="name"
							required> <label for="editSubcategoryName">Subcategory
							Name</label>
					</div>
					<div class="mt-3">
						<button type="submit" class="btn btn-primary">Save</button>
						<button type="button" class="btn btn-secondary"
							id="cancelEditButton">Cancel</button>
					</div>
				</form>
			</div>


		</div>
	</div>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>

	<script>
	
    document.querySelectorAll('.subcategoryCheckbox').forEach(function(checkbox) {
        checkbox.addEventListener('change', function() {
            toggleActionButtons();
        });
    });
    function toggleActionButtons() {
        const selectedCheckboxes = document.querySelectorAll('.subcategoryCheckbox:checked');
        const actionButtons = document.getElementById('deleteButtons');
        const deleteBtn = document.getElementById("deleteBtn");
        if (selectedCheckboxes.length > 0) {
            actionButtons.style.display = 'block';
            deleteBtn.style.display = 'inline-block';
        } else {
            actionButtons.style.display = 'none';
        }
    }
    function confirmDelete() {
        const selectedIds = [];
        document.querySelectorAll('.subcategoryCheckbox:checked').forEach(function(checkbox) {
            selectedIds.push(checkbox.value);
        });
        if (selectedIds.length > 0) {
            Swal.fire({
                title: 'Delete Subcategories',
                text: "You are about to delete selected subcategories. This action cannot be undone.",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonText: 'Delete',
                cancelButtonText: 'Cancel',
                preConfirm: () => {
                    const form = document.getElementById("categoryForm");
                    const deleteInput = document.createElement("input");
                    deleteInput.setAttribute("type", "hidden");
                    deleteInput.setAttribute("name", "subcategoryIdsToDelete");
                    deleteInput.setAttribute("value", selectedIds.join(","));
                    form.appendChild(deleteInput);
                    form.submit();
                }
            });
        }
    }
    

    document.querySelectorAll('.edit-button').forEach(function(editButton) {
        editButton.addEventListener('click', function() {
            var subcategoryId = this.getAttribute('data-id');
            var subcategoryName = this.getAttribute('data-name');
            
            console.log(subcategoryId)
            console.log(subcategoryName)
            
            document.getElementById('editSubcategoryId').value = subcategoryId;
            document.getElementById('editSubcategoryName').value = subcategoryName;
            document.getElementById('editSubcategoryFormContainer').style.display = 'block';
        });
    });

    document.getElementById('cancelEditButton').addEventListener('click', function() {
        document.getElementById('editSubcategoryFormContainer').style.display = 'none';
    });
    const alertMessage = "${alertMessage}";
    const alertType = "${alertType}";
    toastr.options = {
        "closeButton": true, // Allow close button
        "debug": false,
        "newestOnTop": true,
        "progressBar": true, // Show progress bar
        "positionClass": "toast-top-right", // Position of the toast
        "preventDuplicates": true,
        "onclick": null,
        "showDuration": "200", // Duration to show
        "hideDuration": "1000",
        "timeOut": "5000", // Time before auto hiding
        "extendedTimeOut": "1000", // Time when hovering over toast
        "showEasing": "swing",
        "hideEasing": "linear",
        "showMethod": "fadeIn",
        "hideMethod": "fadeOut"
    };

    if (alertMessage) {
        // Check alert type and display the message accordingly
        if (alertType === "success") {
            toastr.success(alertMessage, "Success");
        } else if (alertType === "error") {
            toastr.error(alertMessage, "Error");
        }
    }
    </script>



</body>
</html>
