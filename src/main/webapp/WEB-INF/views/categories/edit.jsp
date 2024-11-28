<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><c:choose>
		<c:when test="${task == 'edit'}">Edit Category</c:when>
		<c:otherwise>New Category</c:otherwise>
	</c:choose></title>

<base href="${pageContext.servletContext.contextPath}/">
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
					<img class="page-icon" src="resources/images/page.svg"
						width="120px" alt="Page Icon">
				</div>
			</div>

			<form id="categoryForm"
				action="${pageContext.servletContext.contextPath}/category/save.htm"
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
								<ul id="subcategoryList">
									<c:forEach var="subcategory" items="${category.subcategories}">
										<li><input type="checkbox" class="subcategoryCheckbox"
											name="subcategoryIdsToEdit" value="${subcategory.id}"
											data-name="${subcategory.name}"> ${subcategory.name}</li>
									</c:forEach>
								</ul>

								<!-- Action Buttons: Edit & Delete -->
								<div id="editDeleteButtons" class="action-buttons">
									<button id="editBtn" type="button" class="btn btn-warning"
										style="display: none;" onclick="confirmEdit()">Edit</button>
									<button id="deleteBtn" type="button" class="btn btn-danger"
										style="display: none;" onclick="confirmDelete()">Delete</button>
								</div>
							</div>
						</c:if>

						<label for="subcategoryNames">Subcategories</label> <input
							type="text" id="subcategoryNames" name="subcategoryNames"
							class="form-control"
							placeholder="Enter subcategory names, separated by commas">
					</div>
				</div>

				<div class="mt-3">
					<button class="btn btn-primary btn-save" type="submit">Save</button>
					<a href="<c:url value='/categories.htm' />"
						class="btn btn-light btn-cancel">Cancel</a>
				</div>
			</form>
		</div>
	</div>

	<script>
    document.querySelectorAll('.subcategoryCheckbox').forEach(function(checkbox) {
        checkbox.addEventListener('change', function() {
            toggleActionButtons();
        });
    });

    function toggleActionButtons() {
        const selectedCheckboxes = document.querySelectorAll('.subcategoryCheckbox:checked');
        const actionButtons = document.getElementById('editDeleteButtons');
        const editBtn = document.getElementById("editBtn");
        const deleteBtn = document.getElementById("deleteBtn");

        if (selectedCheckboxes.length > 0) {
            actionButtons.style.display = 'block';
            editBtn.style.display = 'inline-block';
            deleteBtn.style.display = 'inline-block';
        } else {
            actionButtons.style.display = 'none';
        }
    }

    function confirmEdit() {
        const selectedIds = [];
        const selectedNames = [];

        // Lấy các ID và tên của các checkbox đã chọn
        document.querySelectorAll('.subcategoryCheckbox:checked').forEach(function(checkbox) {
            selectedIds.push(checkbox.value); 
            selectedNames.push(checkbox.getAttribute('data-name')); 
        });

        // Kiểm tra xem có bất kỳ subcategory nào được chọn không
        if (selectedIds.length > 0) {
            Swal.fire({
                title: 'Edit Subcategories',
                text: "You are about to edit selected subcategories.",
                input: 'text',
                inputValue: selectedNames.join(", "), // Hiển thị tên các subcategory đã chọn
                showCancelButton: true,
                confirmButtonText: 'Save Changes',
                cancelButtonText: 'Cancel',
                preConfirm: (newNames) => {
                    // Kiểm tra xem người dùng có nhập tên mới hay không
                    if (!newNames) {
                        Swal.showValidationMessage('You need to enter new names');
                    } else {
                        const form = document.getElementById("categoryForm");

                        // Thêm ID của các subcategory vào form
                        const editInput = document.createElement("input");
                        editInput.setAttribute("type", "hidden");
                        editInput.setAttribute("name", "subcategoryIdsToEdit");
                        editInput.setAttribute("value", selectedIds.join(","));
                        form.appendChild(editInput);
                        form.submit();
                    }
                }
            });
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

    </script>
</body>
</html>
