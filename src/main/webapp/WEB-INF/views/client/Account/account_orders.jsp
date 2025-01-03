<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<base href="${pageContext.servletContext.contextPath}/">

<title>Account Orders</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.1.1/dist/css/bootstrap.min.css"
	rel="stylesheet">

<link rel="stylesheet"
	href="${pageContext.servletContext.contextPath}/resources/assets/css/client/styleAccount.css">

<link rel="stylesheet"
	href="${pageContext.servletContext.contextPath}/resources/assets/css/client/bootstrap.min.css" />
<link rel="stylesheet"
	href="${pageContext.servletContext.contextPath}/resources/assets/css/client/normalize.css" />
<link rel="stylesheet"
	href="${pageContext.servletContext.contextPath}/resources/assets/css/client/font-awesome.min.css" />
<link rel="stylesheet"
	href="${pageContext.servletContext.contextPath}/resources/assets/css/client/icomoon.css" />
<link rel="stylesheet"
	href="${pageContext.servletContext.contextPath}/resources/assets/css/client/jquery-ui.css" />
<link rel="stylesheet"
	href="${pageContext.servletContext.contextPath}/resources/assets/css/client/owl.carousel.css" />
<link rel="stylesheet"
	href="${pageContext.servletContext.contextPath}/resources/assets/css/client/transitions.css" />
<link rel="stylesheet"
	href="${pageContext.servletContext.contextPath}/resources/assets/css/client/main.css" />
<link rel="stylesheet"
	href="${pageContext.servletContext.contextPath}/resources/assets/css/client/color.css" />
<link rel="stylesheet"
	href="${pageContext.servletContext.contextPath}/resources/assets/css/client/responsive.css" />

<script src="https://kit.fontawesome.com/e70d1e2fed.js"
	crossorigin="anonymous"></script>
<script
	src="resources/assets/js/client/vendor/modernizr-2.8.3-respond-1.4.2.min.js"></script>
<!-- Toastr JS -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/js/toastr.min.js"></script>
<style>
.modal-content {
	background-color: white;
	margin: 5% auto;
	padding: 20px;
	border-radius: 8px;
	width: 100%;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

.modal-header h2 {
	margin: 0;
}

table {
	width: 100%;
	border-collapse: collapse;
	margin: 20px 0;
}

table, th, td {
	border: 1px solid #ddd;
}

th, td {
	padding: 10px;
	text-align: left;
}

.total {
	font-size: 18px;
	color: red;
	font-weight: bold;
	text-align: right;
}

.btn-close {
	float: right;
	background-color: red;
}
</style>

</head>
<body>
	<%@ include file="../layouts/header.jsp"%>
	<link
		href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css"
		rel="stylesheet">
	<div class="container mb-4 main-container">
		<div class="row">
			<div class="col-lg-4 pb-5">
				<!-- Account Sidebar-->
				<div class="author-card pb-3">
					<div class="author-card-cover"
						style="background-image: url(https://bootdey.com/img/Content/flores-amarillas-wallpaper.jpeg);"></div>
					<div class="author-card-profile">
						<div class="author-card-avatar">
							<img src="${user.avatar}" alt="${user.fullname}">
						</div>
						<div class="author-card-details">
							<h5 class="author-card-name text-lg">${user.fullname}</h5>
							<span class="author-card-position">Joined
								${user.created_at}</span>
						</div>
					</div>
				</div>
				<div class="wizard">
					<nav class="list-group list-group-flush">
						<a class="list-group-item" href="account/account_orders">
							<div class="d-flex justify-content-between align-items-center">
								<div class="d-inline-block font-weight-medium text-uppercase">Danh
									sách đơn hàng</div>
							</div>
						</a> <a class="list-group-item" href="account/profile_settings">Chỉnh
							sửa thông tin</a> <a class="list-group-item"
							href="account/my_ratings">Đánh giá của bạn</a>
					</nav>
				</div>
			</div>
			<!-- Orders Table -->
			<div class="col-lg-8 pb-5">
				<div class="table-responsive">
					<table class="table table-striped table-bordered">
						<thead>
							<tr>
								<th scope="col">Mã đơn hàng</th>
								<th scope="col">Ngày</th>
								<th scope="col">Tổng</th>
								<th scope="col">Trạng thái</th>
								<th scope="col"></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="order" items="${orders}">
								<tr>
									<td>${order.uuid}</td>
									<td><fmt:formatDate value="${order.createdAt}"
											pattern=" HH:mm dd/MM/yyyy" /></td>
									<td><fmt:formatNumber value="${order.totalPrice}"
											type="currency" currencySymbol="đ" /></td>
									<td><c:choose>
											<c:when test="${order.orderStatus == 'Hoàn thành'}">
												<span class="badge badge-success"
													style="background-color: #28a745;">Hoàn thành</span>
											</c:when>
											<c:when test="${order.orderStatus == 'Chờ xác nhận'}">
												<span class="badge badge-warning"
													style="background-color: #ffc107;">Chờ xác nhận</span>
											</c:when>
											<c:when test="${order.orderStatus eq 'Hủy Đơn Hàng'}">
												<span class="badge badge-danger"
													style="background-color: #dc3545;">Hủy Đơn Hàng</span>
											</c:when>
											<c:when test="${order.orderStatus == 'Xác nhận đơn hàng'}">
												<span class="badge badge-primary"
													style="background-color: #007bff;">Xác nhận đơn hàng</span>
											</c:when>
											<c:when test="${order.orderStatus == 'Đang giao hàng'}">
												<span class="badge badge-info"
													style="background-color: #17a2b8;">Đang giao hàng</span>
											</c:when>
											<c:otherwise>
												<span class="badge badge-danger"
													style="background-color: #dc3545;">Huỷ</span>
											</c:otherwise>
										</c:choose> <%-- 				<c:out value = "${order.orderStatus }"/> --%></td>
									<td><a class="btn btn-info btn-sm link-detail"
										data-toggle="modal" data-target="#orderDetailsModal"
										data-bs-backdrop="false"
										href="${pageContext.servletContext.contextPath}/account/order_details/${order.uuid}">Xem</a>
										<c:if test="${order.orderStatus == 'Chờ xác nhận'}">
											<button class="btn btn-danger btn-sm" data-toggle="modal"
												data-target="#confirmCancelModal"
												onclick="setOrderIdToCancel(${order.id});">Huỷ</button>
										</c:if> <c:if test="${order.orderStatus == 'Hoàn thành'}">
											<!-- Check if the order ID is in the list of rated orders -->
											<c:set var="isReviewed" value="false" />
											<c:forEach var="reviewedOrderId" items="${order_reviewed}">
												<c:if
													test="${reviewedOrderId.toString() eq order.id.toString()}">
													<c:set var="isReviewed" value="true" />

												</c:if>
											</c:forEach>
											<c:if test="${isReviewed}">
												<button class="btn btn-success btn-sm" disabled>Đã
													đánh giá</button>
											</c:if>
											<c:if test="${!isReviewed}">
												<a class="btn btn-success btn-sm"
													href="${pageContext.servletContext.contextPath}/account/ratings/${order.uuid}">Đánh
													giá</a>
											</c:if>
										</c:if></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<%-- 	<c:if test="${not empty successMessage}">
						<div class="alert alert-success">${successMessage}</div>
					</c:if>
					<c:if test="${not empty errorMessage}">
						<div class="alert alert-danger">${errorMessage}</div>
					</c:if> --%>
				</div>
			</div>
		</div>
		<%@ include file="../layouts/footer.jsp"%>
	</div>
	<input type="hidden" name="${_csrf.parameterName}"
		value="${_csrf.token}" />
	<!-- Modal Order Details -->
	<div class="modal fade" id="orderDetailsModal" tabindex="-1"
		aria-labelledby="orderDetailsModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content"></div>
		</div>
	</div>

	<!-- Modal Confirm Cancel Order -->
	<div class="modal fade" id="confirmCancelModal" tabindex="-1"
		role="dialog" aria-labelledby="confirmCancelModalLabel"
		aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="confirmCancelModalLabel">Xác nhận
						huỷ</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">Bạn có chắc HUỶ đơn hàng?</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">No</button>
					<button type="button" class="btn btn-danger" id="confirmCancelBtn">Yes</button>
				</div>
			</div>
		</div>
	</div>


	<script src="resources/assets/js/client/vendor/jquery-library.js"></script>
	<script src="resources/assets/js/client/vendor/bootstrap.min.js"></script>
	<script
		src="https://maps.google.com/maps/api/js?key=AIzaSyCR-KEWAVCn52mSdeVeTqZjtqbmVJyfSus&amp;language=en"></script>
	<script src="resources/assets/js/client/owl.carousel.min.js"></script>
	<script src="resources/assets/js/client/jquery.vide.min.js"></script>
	<script src="resources/assets/js/client/countdown.js"></script>
	<script src="resources/assets/js/client/jquery-ui.js"></script>
	<script src="resources/assets/js/client/parallax.js"></script>
	<script src="resources/assets/js/client/countTo.js"></script>
	<script src="resources/assets/js/client/appear.js"></script>
	<script src="resources/assets/js/client/gmap3.js"></script>
	<script src="resources/assets/js/client/main.js"></script>

	<script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@4.1.1/dist/js/bootstrap.bundle.min.js"></script>

	<script type="text/javascript">
/* function loadOrderDetails(orderId) {
    // Gửi AJAX request để lấy thông tin chi tiết của đơn hàng
    $.ajax({
        url: '${pageContext.servletContext.contextPath}/client/account/order_details',
        method: 'GET',
        data: { orderId: orderId },
        success: function (data) {
            // Khi nhận được dữ liệu, chèn thông tin vào modal
            $('#orderDetailsContent')l(data);
        },
        error: function () {
            $('#orderDetailsContent')l('<p class="text-danger">Failed to load order details. Please try again.</p>');
        }
    });
}
 */
 	$(document).ready(function(){
 		$(".link-detail").on("click",function(e){
 			e.preventDefault();
 			console.log($(this).attr("href"));
 			$("#orderDetailsModal").modal("show").find(".modal-content").load($(this).attr("href"));
 		});
 	});
 
 </script>

	<script>
 // JavaScript để điều khiển modal
 const orderModal = document.getElementById('orderDetailsModal');

 // Đóng modal khi nhấp ra ngoài nội dung
 window.onclick = (event) => {
     if (event.target === orderModal) {
         orderModal.style.display = 'none';
     }
 };
</script>

	<script type="text/javascript">
	let orderIdToCancel = null;
	
	function setOrderIdToCancel(orderId) {
	    orderIdToCancel = orderId;
	}
	
	document.getElementById("confirmCancelBtn").addEventListener("click", function () {
	    if (orderIdToCancel !== null) {
	        // Gửi yêu cầu POST để xóa order
	        const form = document.createElement("form");
	        form.method = "POST";
	        form.action = "${pageContext.servletContext.contextPath}/account/cancel_order";
	
	        const hiddenInput = document.createElement("input");
	        hiddenInput.type = "hidden";
	        hiddenInput.name = "orderId";
	        hiddenInput.value = orderIdToCancel;
	        form.appendChild(hiddenInput);
	        
	     	// Lấy CSRF token từ thẻ input hidden trong trang HTML
	        const csrfToken = document.querySelector('input[name="${_csrf.parameterName}"]').value;

	        // Thêm input hidden cho CSRF token
	        const hiddenInputCsrf = document.createElement("input");
	        hiddenInputCsrf.type = "hidden";
	        hiddenInputCsrf.name = "${_csrf.parameterName}";
	        hiddenInputCsrf.value = csrfToken;
	        form.appendChild(hiddenInputCsrf);
			
	        document.body.appendChild(form);
	        form.submit();
	    }
	    // Đóng modal sau khi gửi yêu cầu
	    $('#confirmCancelModal').modal('hide');
	});

</script>
	<script>
    document.addEventListener("DOMContentLoaded", function () {
        // Lấy tất cả các liên kết trong danh sách
        const navLinks = document.querySelectorAll(".list-group-item");
    
        // Lấy đường dẫn hiện tại (chỉ phần tên file)
        const currentPath = window.location.pathname.split("/").pop();
    
        // Lặp qua các liên kết để kích hoạt "active"
        navLinks.forEach(function (link) {
            // Kiểm tra nếu href của link khớp với URL hiện tại
            if (link.getAttribute("href").includes(currentPath)) {
                link.classList.add("active");
            } else {
                link.classList.remove("active");
            }
        });
    });
    
    const alertMessage = "${messagePassword}";
    const alertType = "${alertType}";

    
	toastr.options = {
		    "closeButton": true, // Cho phép nút đóng
		    "debug": false,
		    "newestOnTop": true,
		    "progressBar": true, // Hiển thị thanh tiến trình
		    "positionClass": "toast-top-right", // Vị trí hiển thị thông báo
		    "preventDuplicates": true,
		    "onclick": null,
		    "showDuration": "200", // Thời gian hiển thị
		    "hideDuration": "1000",
		    "timeOut": "5000", // Thời gian thông báo sẽ tự động ẩn
		    "extendedTimeOut": "1000", // Thời gian mở rộng nếu hover vào
		    "showEasing": "swing",
		    "hideEasing": "linear",
		    "showMethod": "fadeIn",
		    "hideMethod": "fadeOut"
		};
	// Kiểm tra và hiển thị toastr nếu có thông báo
    if (alertMessage) {
        if (alertType === "success") {
            toastr.success(alertMessage);
        } else if (alertType === "error") {
            toastr.error(alertMessage);
        } else {
            toastr.info(alertMessage);  // Default to info if no type is specified
        }
    }
    
</script>
	<script type="text/javascript">
var Tawk_API=Tawk_API||{}, Tawk_LoadStart=new Date();
(function(){
var s1=document.createElement("script"),s0=document.getElementsByTagName("script")[0];
s1.async=true;
s1.src='https://embed.tawk.to/67685e74af5bfec1dbe01c1d/1ifnr1ubt';
s1.charset='UTF-8';
s1.setAttribute('crossorigin','*');
s0.parentNode.insertBefore(s1,s0);
})();
</script>

</body>
</html>