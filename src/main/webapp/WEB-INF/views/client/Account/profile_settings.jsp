<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <base href="${pageContext.servletContext.contextPath}/">
    <title>Profile Settings</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@4.1.1/dist/css/bootstrap.min.css" rel="stylesheet">
    
<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/assets/css/client/styleAccount.css">

<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/assets/css/client/bootstrap.min.css" />
	<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/assets/css/client/normalize.css" />
	<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/assets/css/client/font-awesome.min.css" />
	<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/assets/css/client/icomoon.css" />
	<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/assets/css/client/jquery-ui.css" />
	<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/assets/css/client/owl.carousel.css" />
	<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/assets/css/client/transitions.css" />
	<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/assets/css/client/main.css" />
	<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/assets/css/client/color.css" />
	<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/assets/css/client/responsive.css" />
	
	<script src="https://kit.fontawesome.com/e70d1e2fed.js" crossorigin="anonymous"></script>
	<script src="resources/assets/js/client/vendor/modernizr-2.8.3-respond-1.4.2.min.js"></script>
	<!-- Toastr CSS -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/css/toastr.min.css">
	<!-- Toastr JS -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/js/toastr.min.js"></script>
	
</head>
<body>
<%@ include file="../layouts/header.jsp"%>
<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet">
<div class="container mt-5">
    <div class="row">
        <div class="col-lg-4 pb-5">
            <!-- Account Sidebar-->
            <div class="author-card pb-3">
                <div class="author-card-cover" style="background-image: url(https://bootdey.com/img/Content/flores-amarillas-wallpaper.jpeg);"></div>
                <div class="author-card-profile">
                    <div class="author-card-avatar"><img src="${user.getAvatar()}" alt="${user.getFullname()}">
                    </div>
                    <div class="author-card-details">
                        <h5 class="author-card-name text-lg">${user.fullname}</h5><span class="author-card-position">Tham gia ${user.created_at}</span>
                    </div>
                </div>
            </div>
            <div class="wizard">
                <nav class="list-group list-group-flush">
                    <a class="list-group-item" href="account/account_orders.htm">
                        <div class="d-flex justify-content-between align-items-center">
                                <div class="d-inline-block font-weight-medium text-uppercase">Danh sách đơn hàng</div>
                        </div>
                    </a><a class="list-group-item" href="account/profile_settings.htm">Chỉnh sửa thông tin</a>
                    <a class="list-group-item" href="account/my_ratings.htm">Đánh giá của bạn</a>
                </nav>
            </div>
        </div>
        <!-- Profile Settings-->

        <div class="col-lg-8 pb-5">
        	<h4 class="mb-3">Update Profile</h4>
        	<c:if test="${not empty successUpdate}">
				<div class="alert alert-success">${successUpdate}</div>
			</c:if>
			<c:if test="${not empty errorUpdate}">
				<div class="alert alert-danger">${errorUpdate}</div>
			</c:if>
            <form class="row" action="account/update_profile.htm" method="POST" enctype="multipart/form-data" style="border: 1px solid #ccc; padding: 20px; border-radius: 8px;">
                <div class="col-md-12">
                    <div class="form-group">
                        <label for="account-fn">Họ tên</label>
                        <input class="form-control" type="text" id="account-fn" name="fullname" value="${user.fullname }" required>
                        <c:if test="${not empty errorfn}">
                        	 <div class="text-danger">${errorfn}</div>
                        </c:if>
                    </div>
                </div>
                
                <div class="col-md-6">
                    <div class="form-group">
                        <label for="account-email">E-mail</label>
                        <input class="form-control" type="text" id="account-email" name="email" value="${user.email}" disabled>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="form-group">
                        <label for="account-phone">Số điện thoại</label>
                        <input class="form-control" type="text" id="account-phone" name="phone" value="${user.phone}" pattern="^0(3|5|7|8|9)[0-9]{8}$" required >
                        <c:if test="${not empty errorPhone}">
                        	 <div class="text-danger">${errorPhone}</div>
                        </c:if>
                    </div>
                </div>
                <div class="col-md-12">
                    <div class="form-group">
                        <label for="account-avatar">Ảnh đại diện</label>
                        <input class="form-control" type="file" id="account-avatar" name="avatar" accept="image/*">
                    </div>
                </div>
                
                <div class="col-12">
                    <hr class="mt-2 mb-3">
                    <div class="d-flex flex-wrap justify-content-between align-items-center">
                        <button class="btn btn-style-1 btn-primary" type="submit" data-toast="" data-toast-position="topRight" data-toast-type="success" data-toast-icon="fe-icon-check-circle" data-toast-title="Success!" data-toast-message="Your profile updated successfuly.">Cập nhật</button>
                    </div>
                </div>
            </form>
            <hr class="my-4" style="border-top: 2px dashed #ccc;">
            <!-- Change Password Form -->
            <h4 class="mb-3">Thay đổi mật khẩu</h4>
            <c:if test="${not empty successPassword}">
				<div class="alert alert-success">${successPassword}</div>
			</c:if>
			<c:if test="${not empty errorPassword}">
				<div class="alert alert-danger">${errorPassword}</div>
			</c:if>
            <form action="account/change_password.htm" method="POST" id="change-password-form" style="border: 1px solid #ccc; padding: 20px; border-radius: 8px;">
                <div class="form-group">
                    <label for="old-password">Mật khẩu hiện tại</label>
                    <input type="password" class="form-control" id="old-password" name="oldPassword" required>
                    <div id="error-old-password" class="text-danger" style="display: none;">Không được để trống ô Old Password.</div>
                    <c:if test="${not empty oldPassError}">
					    <div class="text-danger">${oldPassError}</div>
					</c:if>
                    
                </div>
                <div class="form-group">
                    <label for="new-password">Mật khẩu mới</label>
                    <input type="password" class="form-control" id="new-password" name="newPassword" required oninput="validatePassword(this.value)">
                    <div id="error-new-password" class="text-danger" style="display: none;">Không được để trống ô New Password.</div>
                </div>
                <div class="form-group password-strength" id="password-strength" style="display: none;">
					<ul class="no-bullet">
					<li id="minLength"><i class="fas fa-times text-danger"></i> Tối thiểu 8 ký tự</li>
					<li id="uppercase"><i class="fas fa-times text-danger"></i> Ít nhất một chữ cái viết hoa</li>
					<li id="lowercase"><i class="fas fa-times text-danger"></i> Ít nhất một chữ cái viết thường</li>
					<li id="symbol"><i class="fas fa-times text-danger"></i> Ít nhất một ký tự đặc biệt (@$!%*?&)</li>
					<li id="number"><i class="fas fa-times text-danger"></i> Ít nhất một chữ số</li>
					</ul>
				</div>
                <div class="form-group">
                    <label for="confirm-password">Xác nhận mật khẩu mới</label>
                    <input type="password" class="form-control" id="confirm-password" name="confirmPassword" required>
                    <div id="error-confirm-password" class="text-danger" style="display: none;">Không được để trống ô Confirm Password.</div>
                    <div id="error-password-match" class="text-danger" style="display: none;">New Password và Confirm Password chưa khớp.</div>
                </div>
                <button type="submit" class="btn btn-primary" id="save-password-btn">Lưu thay đổi</button>
            </form>
        </div>
    </div>
<%@ include file="../layouts/footer.jsp"%>
</div>



<script src="resources/assets/js/client/vendor/jquery-library.js"></script>
	<script src="resources/assets/js/client/vendor/bootstrap.min.js"></script>
	<script src="https://maps.google.com/maps/api/js?key=AIzaSyCR-KEWAVCn52mSdeVeTqZjtqbmVJyfSus&amp;language=en"></script>
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
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.1.1/dist/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript">
	function validatePassword(password) {
	    document.getElementById('minLength').innerHTML = password.length >= 8 
	        ? '<i class="fas fa-check text-success"></i> Tối thiểu 8 ký tự'
	        : '<i class="fas fa-times text-danger"></i> Tối thiểu 8 ký tự';
	
	    document.getElementById('uppercase').innerHTML = /[A-Z]/.test(password) 
	        ? '<i class="fas fa-check text-success"></i> Ít nhất một chữ cái viết hoa'
	        : '<i class="fas fa-times text-danger"></i> Ít nhất một chữ cái viết hoa';
	
	    document.getElementById('lowercase').innerHTML = /[a-z]/.test(password) 
	        ? '<i class="fas fa-check text-success"></i> Ít nhất một chữ cái viết thường'
	        : '<i class="fas fa-times text-danger"></i> Ít nhất một chữ cái viết thường';
	
	    document.getElementById('symbol').innerHTML = /[@$!%*?&]/.test(password) 
	        ? '<i class="fas fa-check text-success"></i> Ít nhất một ký tự đặc biệt (@$!%*?&)'
	        : '<i class="fas fa-times text-danger"></i> Ít nhất một ký tự đặc biệt (@$!%*?&)';
	
	    document.getElementById('number').innerHTML = /\d/.test(password) 
	        ? '<i class="fas fa-check text-success"></i> Ít nhất một chữ số'
	        : '<i class="fas fa-times text-danger"></i> Ít nhất một chữ số';
	}
	
    document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("change-password-form");
    const oldPasswordField = document.getElementById("old-password");
    const newPasswordField = document.getElementById("new-password");
    const confirmPasswordField = document.getElementById("confirm-password");
    
    newPasswordField.addEventListener("input", function () {
        const password = newPasswordField.value;
        validatePassword(password);
        document.getElementById("password-strength").style.display = password ? "block" : "none";
    });

    const errorOldPassword = document.getElementById("error-old-password");
    const errorNewPassword = document.getElementById("error-new-password");
    const errorConfirmPassword = document.getElementById("error-confirm-password");
    const errorPasswordMatch = document.getElementById("error-password-match");

    form.addEventListener("submit", function (event) {
        let isValid = true;

        // Reset lỗi
        errorOldPassword.style.display = "none";
        errorNewPassword.style.display = "none";
        errorConfirmPassword.style.display = "none";
        errorPasswordMatch.style.display = "none";

        // Kiểm tra từng trường
        if (oldPasswordField.value.trim() === "") {
            errorOldPassword.style.display = "block";
            isValid = false;
        }
        if (newPasswordField.value.trim() === "") {
            errorNewPassword.style.display = "block";
            isValid = false;
        } else {
            // Kiểm tra điều kiện mật khẩu mới
            const password = newPasswordField.value;
            if (password.length < 8 ||                // Tối thiểu 8 ký tự
                !/[A-Z]/.test(password) ||           // Ít nhất một chữ hoa
                !/[a-z]/.test(password) ||           // Ít nhất một chữ thường
                !/\d/.test(password) ||              // Ít nhất một chữ số
                !/[@$!%*?&]/.test(password)) {       // Ít nhất một ký tự đặc biệt
                errorNewPassword.textContent = "Mật khẩu mới không thỏa mãn các điều kiện.";
                errorNewPassword.style.display = "block";
                isValid = false;
            }
        }
        if (confirmPasswordField.value.trim() === "") {
            errorConfirmPassword.style.display = "block";
            isValid = false;
        }
        if (newPasswordField.value !== confirmPasswordField.value) {
            errorPasswordMatch.style.display = "block";
            isValid = false;
        }

        // Ngăn không cho gửi form nếu không hợp lệ
        if (!isValid) {
            event.preventDefault();
        }
    });

});
    </script>
<script type="text/javascript">
    document.addEventListener("DOMContentLoaded", function () {
        const navLinks = document.querySelectorAll(".list-group-item");
        const currentPath = window.location.pathname.split("/").pop();

        navLinks.forEach(function (link) {
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


</body>
</html>

