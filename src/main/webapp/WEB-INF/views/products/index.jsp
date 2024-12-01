<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="f"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Products Management</title>
<base href="${pageContext.servletContext.contextPath}/">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
	<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
	<script src="${pageContext.request.contextPath}/resources/js/app.js"
	defer></script>
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

.table-centered tbody tr:hover { background-color: #f0f0f0;}

.ellipsis {
    white-space: nowrap;          /* Không xuống dòng */
    overflow: hidden;             /* Ẩn phần văn bản vượt quá vùng chứa */
    text-overflow: ellipsis;      /* Thêm dấu "..." khi văn bản bị cắt */
    max-width: 200px;             /* Đặt chiều rộng tối đa của vùng chứa */
    display: block;               /* Đảm bảo nó là một khối để áp dụng được */
}
</style>
</head>
<body>

	<jsp:include page="/WEB-INF/views/layouts/header.jsp" />

	<div class="main-content">
		<jsp:include page="/WEB-INF/views/layouts/navigation.jsp" />
		<div class="container-fluid d-flex flex-column"
			style="padding-left: 0px; padding-right: 0px;">
			<div class="container-fluid"
				style="margin-left: 0px; margin-right: 0px;">
				<div class="row g-3 mt-3">
					<div class="col">
						<h2 class="h3">Products management</h2>
						<p>Manage users, roles, permissions, and profile.</p>
					</div>
					<div class="col-auto d-none d-sm-block">
						<img class="page-icon" src="resources/images/page.svg"
							width="120px" alt="Page Icon">
					</div>
				</div>
			</div>
			<form id="frm-admin" name="adminForm" action="" method="POST">
				<input type="hidden" id="task" name="task" value="${param.task}">
				<input type="hidden" id="sortby" name="sortby"
					value="${param.sortby != null ? param.sortby : 'updated_at'}" /> <input
					type="hidden" id="orderby" name="orderby"
					value="${param.orderby != null ? param.orderby : 'desc'}" /> <input
					type="hidden" id="boxchecked" name="boxchecked" value="0" />

				<div class="card mx-3">
					<div class="card-body">
						<div class="d-flex gap-3">
							<div class="input-group">
								<input class="form-control search-input me-2" name="search"
									id="search_text" value="${search}"
									placeholder="Search...">
								<button type="submit" class="btn btn-secondary btn-search">
									<i class="fa fa-search"></i>
								</button>
							</div>
							<a class="btn btn-primary text-nowrap btn-add"
								href="${pageContext.request.contextPath}/product/new.htm"> <i
								class="fa fa-plus me-2"></i>Add
							</a>

						</div>

						<div class="mt-3">
							<div
								class="table-actionbar bg-primary bg-opacity-10 p-2 ps-3 d-none">
								<div class="d-flex justify-content-between gap-3">
									<div class="selected-count align-self-center"></div>
									<div class="d-flex gap-1">
										<a class="btn btn-rounded"> <i class="fa fa-eye"></i></a> <a
											class="btn btn-rounded"> <i class="fa fa-eye-slash"></i>
										</a> <a class="btn btn-rounded"> <i class="fa fa-trash-alt"></i>
										</a>
									</div>
								</div>
							</div>

							<div class="table-responsive">
								<table class="table table-centered table-hover">
									<tr>
										<th width="15px"><input class="text-end	form-check-input"
											type="checkbox" id="toggle" name="toggle"
											onclick="checkAll()" /></th>
										<th width="30px" class="text-start">#</th>
										<th width="200x" class="text-center">Product</th>
										<th width="30px" class="text-center">Status</th>
										<th width="100px" class="text-center">Total quantity</th>
										<!-- <th width="50px" class="text-center">Inventory</th> -->
										<th width="160px" class="text-center">Category</th>
										<th width="50px" class="text-center">Price</th>
										<th width="80px" class="text-center">Update at</th>
										<th width="60px" class="text-center">Action</th>
									</tr>
									<c:forEach var="book" items="${listBooks}" varStatus="status">
										<tr>
											<td class="align-middle">
												<input type="checkbox" class="form-check-input" id="cb${status.index}" name="cid[]" value="${book.id}" onclick="isChecked(this.checked)">
											</td>
<%-- 											<td class="text-end">${(users.page - 1) * users.pageSize + status.index + 1}</td> --%>
											<td class="text-start align-middle">${book.id}</td>
											<td>
    <a class="d-flex flex-nowrap align-items-center" style="text-decoration: none;" href="javascript:void(0);" 
       data-id="${book.id}"
       data-title="${book.title}"
       data-author="${book.author}"
       data-category="${book.subcategoriesEntity.categoriesEntity.name}"
       data-subcategory="${book.subcategoriesEntity.name}"
       data-supplier="${book.supplier.name}"
       data-publicationYear="${book.publication_year}"
       data-pageCount="${book.page_count} pages"
       data-language="${book.language}"
       data-status="${book.status == 0 ? 'Disable' : 'Enable'}"
       data-createdAt="<fmt:formatDate value='${book.updatedAt}' pattern='dd-MM-yyyy HH:mm' />"
       data-updatedAt="<fmt:formatDate value='${book.createdAt}' pattern='dd-MM-yyyy HH:mm' />"
       data-price=<fmt:formatNumber value="${book.price}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
       data-description="${fn:escapeXml(book.description)}" 
       data-thumbnail="${book.thumbnail}"
       data-images="${book.images}"
       data-quantity="${book.quantity} in stock"
       onclick="showProductDetails(this)">
        <div>
            <img alt="Product Thumbnail" src="${book.thumbnail}" class="bg-white border border-3 border-white" width="50px">
        </div>
        <div class="ms-3">
            <div class="fw-semibold custom-text ellipsis">${book.title}</div>
        </div>
    </a>
</td>
											<c:choose>
												<c:when test="${book.status == 1}">
													<td class="text-center align-middle"><span class="small text-uppercase text-success bg-success bg-opacity-10 rounded px-2 py-1">active</span></td>
												</c:when>
												<c:otherwise>
													<td class="text-center align-middle"><span class="small text-uppercase text-danger bg-danger bg-opacity-10 rounded px-2 py-1">disable</span></td>
												</c:otherwise>
											</c:choose></title>
<!-- 											<td class="text-center align-middle"><span class="small text-uppercase text-success bg-success bg-opacity-10 rounded px-2 py-1">active</span></td> -->
<%-- 											<td class="text-end align-middle"><span class="small text-uppercase ${user.isActive ? 'text-success' : 'text-danger'} bg-opacity-10 rounded px-2 py-1">${user.isActive ? 'Active' : 'Inactive'}</span></td> --%>
											
											<!-- <td class="text-center align-middle">${book.quantity}</td> -->
											
<%-- 											<c:choose> --%>
<%-- 												<c:when test="${book.quantity > 10}"> --%>
<%-- 													<td class="text-center align-middle"><span class="small text-success bg-opacity-10 rounded px-2 py-1">${book.quantity} in stock</span></td>												 --%>
<%-- 												</c:when> --%>
<%-- 												<c:otherwise> --%>
<%-- 													<td class="text-center align-middle"><span class="small text-danger bg-opacity-10 rounded px-2 py-1">${book.quantity} in stock</span></td> --%>
<%-- 												</c:otherwise> --%>
<%-- 											</c:choose> --%>
											
											<td class="text-center align-middle">${book.quantity}</td>
											<td class="text-center align-middle">${book.subcategoriesEntity.name}</td>
											<td class="text-center align-middle">
												
												<fmt:formatNumber value="${book.price}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
											</td>
											<td class="text-center align-middle"><fmt:formatDate value='${book.updatedAt}' pattern='dd-MM-yyyy HH:mm' /></td>
											<td class="text-center">
												<div class="d-flex justify-content-center align-items-center gap-1">
													<a class="btn btn-rounded" href="product/edit/${book.id}.htm"><i class="fa fa-pencil"></i></a>
<%-- 													<a class="btn btn-rounded"><i class="fa ${user.isActive ? 'fa-eye-slash' : 'fa-eye'}"></i></a> --%>
													<!-- <a class="btn btn-rounded"><i class="fa fa-eye-slash"></i></a> -->
													<!-- <a class="btn btn-rounded"><i class="fa fa-search"></i></a> -->
													<a class="btn btn-rounded" data-bs-toggle="modal" data-bs-target="#exampleModal">
													    <i class="fa fa-plus-circle" aria-hidden="true"></i>
													</a>
													<a class="btn btn-rounded" data-bs-toggle="modal" data-bs-target="#deleteModal" onclick="prepareDelete(${book.id}, '${book.title}')">
														<i class="fa fa-trash-alt"></i>
													</a>
												</div>
											</td>
										</tr>
										
										
									</c:forEach>
								</table>
							</div>
							<c:if test="${totalPages > 1}">
								<nav aria-label="Page navigation example" class="mt-2">
									<ul class="pagination justify-content-center">
										<!-- Liên kết đến trang trước -->
										<li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
											<a class="page-link"
											href="${pageContext.request.contextPath}/products.htm?page=${currentPage > 1 ? currentPage - 1 : 1}&size=${size}"
											aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
										</a>
										</li>

										<!-- Liên kết đến từng trang -->
										<c:forEach var="i" begin="1" end="${totalPages}">
											<li class="page-item ${i == currentPage ? 'active' : ''}">
												<a class="page-link"
												href="${pageContext.request.contextPath}/products.htm?page=${i}&size=${size}">${i}</a>
											</li>
										</c:forEach>

										<!-- Liên kết đến trang tiếp theo -->
										<li
											class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
											<a class="page-link"
											href="${pageContext.request.contextPath}/products.htm?page=${currentPage < totalPages ? currentPage + 1 : totalPages}&size=${size}"
											aria-label="Next"> <span aria-hidden="true">&raquo;</span>
										</a>
										</li>
									</ul>
								</nav>
							</c:if>
						</div>
					</div>
				</div>
			</form>

			<div class="modal fade" id="deleteModal" tabindex="-1" aria-labelledby="deleteModalLabel" aria-hidden="true">
			    <div class="modal-dialog">
			        <div class="modal-content">
			            <div class="modal-header">
			                <h5 class="modal-title" id="deleteModalLabel">Confirm Book Deletion</h5>
			                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			            </div>
			            <div class="modal-body">
			                <!-- Form used to send POST request -->
			                <form id="deleteForm" method="POST" action="product/delete.htm">
			                    <p>Are you sure you want to delete this book?</p>
			                    <p><strong>Book Id:</strong> <span id="bookIdToDelete"></span></p>
			                    <p><strong>Book title:</strong> <span id="bookTitleToDelete"></span></p>
			                    <!-- Hidden input to send bookId -->
			                    <input type="hidden" name="bookId" id="bookIdInput">
			                </form>
			            </div>
			            <div class="modal-footer">
			                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
			                <!-- Submit button to send form -->
			                <button type="submit" class="btn btn-danger" form="deleteForm">Delete</button>
			            </div>
			        </div>
			    </div>
			</div>
			
			
			<div class="modal fade" id="productDetailModal" tabindex="-1" aria-labelledby="productDetailModalLabel" aria-hidden="true">
			    <div class="modal-dialog">
			        <div class="modal-content">
			            <div class="modal-header">
			                <h5 class="modal-title" id="productDetailModalLabel">Product Details</h5>
			                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			            </div>
			            <div class="modal-body">
			            	<div class="d-flex">
			                    <div class="mb-3 me-3" style="flex: 1;">
				                    <label for="productTitle" class="form-label">Title</label>
				                    <input type="text" class="form-control" id="productTitle" disabled>
			                	</div>
			                    <div class="mb-3" style="flex: 1;">
			                        <label for="productAuthor" class="form-label">Author</label>
			                        <input type="text" class="form-control" id="productAuthor" disabled>
			                    </div>
			                </div>
			                
			                <div class="mb-3">
			                    <label for="productDescription" class="form-label">Description</label>
			                    <textarea class="form-control" id="productDescription" rows="3" disabled></textarea>
			                </div>
			                
			                <div class="d-flex">
			                    <div class="mb-3 me-3" style="flex: 1;">
			                        <label for="productPrice" class="form-label">Category</label>
			                        <input type="text" class="form-control" id="productCategory" disabled>
			                    </div>
			                    <div class="mb-3" style="flex: 1;">
			                        <label for="productSubcategory" class="form-label">Subcategory</label>
			                        <input type="text" class="form-control" id="productSubcategory" disabled>
			                    </div>
			                </div>
			                
			                <div class="d-flex">
			                    <div class="mb-3 me-3" style="flex: 1;">
			                        <label for="productPrice" class="form-label">Supplier</label>
			                        <input type="text" class="form-control" id="productSupplier" disabled>
			                    </div>
			                    <div class="mb-3" style="flex: 1;">
			                        <label for="productPublicationYear" class="form-label">Publication year</label>
			                        <input type="text" class="form-control" id="productPublicationYear" disabled>
			                    </div>
			                </div>
			                
			                <div class="d-flex">
							    <div class="mb-3 me-3" style="flex: 1;">
							        <label for="productPrice" class="form-label">Price</label>
							        <input type="text" class="form-control" id="productPrice" disabled>
							    </div>
							    <div class="mb-3 me-3" style="flex: 1;">
							        <label for="productQuantity" class="form-label">Total quantity</label>
							        <input type="text" class="form-control" id="productQuantity" disabled>
							    </div>
							    <div class="mb-3" style="flex: 1;">
							        <label for="productCategory" class="form-label">Page count</label>
							        <input type="text" class="form-control" id="productPageCount" disabled>
							    </div>
							</div>
							
							<div class="d-flex">
			                    <div class="mb-3 me-3" style="flex: 1;">
			                        <label for="productPrice" class="form-label">Created at</label>
			                        <input type="text" class="form-control" id="productCreatedAt" disabled>
			                    </div>
			                    <div class="mb-3" style="flex: 1;">
			                        <label for="productUpdatedAt" class="form-label">Updated at</label>
			                        <input type="text" class="form-control" id="productUpdatedAt" disabled>
			                    </div>
			                </div>
			                
			                <div class="d-flex">
			                    <div class="mb-3 me-3" style="flex: 1;">
			                        <label for="productPrice" class="form-label">Language</label>
			                        <input type="text" class="form-control" id="productLanguage" disabled>
			                    </div>
			                    <div class="mb-3" style="flex: 1;">
			                        <label for="productStatus" class="form-label">Status</label>
			                        <input type="text" class="form-control" id="productStatus" disabled>
			                    </div>
			                </div>
			                
			                <div class="d-flex">
			                    <div class="mb-3 me-3" style="flex: 1;">
			                        <label for="productPrice" class="form-label">Thumbnail</label>
			                        <img id="productThumbnail" src="" alt="Product Image" class="img-fluid" style="max-width: 100px; max-height: 100px; display: block;">
			                    </div>
			                    <div class="mb-3" style="flex: 1;">
        <label for="productImages" class="form-label">Images</label>
        <div id="productImages" class="d-flex flex-wrap"></div>
    </div>
			                </div>
			                
			                <!-- Add more fields if needed -->
			            </div>
			            <div class="modal-footer">
			                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
			            </div>
			        </div>
			    </div>
			</div>



		</div>

	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js">
	</script>
	<script type="text/javascript">
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
		
		function prepareDelete(bookId, bookTitle) {
		    // Hiển thị ID của sách trong modal
		    document.getElementById("bookIdToDelete").textContent = bookId;
		    document.getElementById("bookTitleToDelete").textContent = bookTitle;
		    document.getElementById("bookIdInput").value = bookId;

		}
		
		function showProductDetails(link) {
		    // Lấy thông tin từ thuộc tính data của link
		    var productId = link.getAttribute('data-id');
		    var productTitle = link.getAttribute('data-title');
		    var productAuthor = link.getAttribute('data-author');
		    var productDescription = link.getAttribute('data-description');
		    var productCategory = link.getAttribute('data-category');
		    var productSubcategory = link.getAttribute('data-subcategory');
		    var productSupplier = link.getAttribute('data-supplier');
		    var productPublicationYear = link.getAttribute('data-publicationYear');
		    var productPageCount = link.getAttribute('data-pageCount');
		    var productPrice = link.getAttribute('data-price');
		    var productLanguage = link.getAttribute('data-language');
		    var productStatus = link.getAttribute('data-status');
		    var productCreatedAt = link.getAttribute('data-createdAt');
		    var productUpdatedAt = link.getAttribute('data-updatedAt');
		    var productThumbnail = link.getAttribute('data-thumbnail');
		    var productImages = link.getAttribute('data-images');
		    var productQuantity = link.getAttribute('data-quantity');
			
		    
		    
		    // Điền thông tin vào modal
		    document.getElementById('productTitle').value = productTitle;
		    document.getElementById('productAuthor').value = productAuthor;
		    document.getElementById('productDescription').value = productDescription;
		    document.getElementById('productPrice').value = productPrice;
		    document.getElementById('productCategory').value = productCategory;
		    document.getElementById('productSubcategory').value = productSubcategory;
		    document.getElementById('productSupplier').value = productSupplier;
		    document.getElementById('productPublicationYear').value = productPublicationYear;
		    document.getElementById('productPageCount').value = productPageCount;
		    document.getElementById('productLanguage').value = productLanguage;
		    document.getElementById('productStatus').value = productStatus;
		    document.getElementById('productCreatedAt').value = productCreatedAt;
		    document.getElementById('productUpdatedAt').value = productUpdatedAt;
		    document.getElementById('productQuantity').value = productQuantity;
		    
		    document.getElementById('productThumbnail').src = productThumbnail;
		    
		    var images = productImages.split(';'); // Tách chuỗi URL bằng dấu ";"
		    var imagesContainer = document.getElementById('productImages');
		    imagesContainer.innerHTML = ''; // Xóa hết các thẻ img cũ

		    images.forEach(function(imageUrl) {
		        var imgElement = document.createElement('img');
		        imgElement.src = imageUrl.trim(); // Loại bỏ khoảng trắng
		        imgElement.classList.add('img-fluid');
		        imgElement.style.maxWidth = '100px';
		        imgElement.style.maxHeight = '100px';
		        imagesContainer.appendChild(imgElement);
		    });

		    // Mở modal
		    var modal = new bootstrap.Modal(document.getElementById('productDetailModal'));
		    modal.show();
		}

	</script>
</body>
</html>