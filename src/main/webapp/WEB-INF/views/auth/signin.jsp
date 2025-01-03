<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Book Store - ALDPT</title>

<base href="${pageContext.servletContext.contextPath}/">
<!-- Font Awesome CSS -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

<!-- Bootstrap CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	crossorigin="anonymous">

<script
	src="https://www.google.com/recaptcha/api.js?render=6LfGAJYqAAAAAB0X5ZPkvqjkKV0yhiA3zsSHhkeW&onload=onRecaptchaLoad"
	async defer></script>

<style>
.mt-5 {
	margin-top: 7rem !important;
}

.auth-logo img {
	max-width: 150px;
}

.google-btn img {
	max-width: 25px;
}
</style>
</head>
<body>
	<div class="d-flex flex-wrap justify-content-between vh-100">
		<div class="w-100">
			<section>
				<div class="container mt-5">
					<div class="justify-content-center row">
						<div class="col-xl-4 col-lg-6 col-md-8">
							<div class="card shadow-sm">
								<div class="card-body p-4">
									<div class="text-center w-75 mx-auto">
										<div class="auth-logo">
											<img alt="Logo" src="resources/images/ALDPT.png"
												class="img-fluid">
										</div>
									</div>
									<div class="mt-4">
										<form id="signinform" class="needs-validation" novalidate
											action="${pageContext.request.contextPath}/perform_login"
											method="POST">
											<input type="hidden" id="g-recaptcha-response"
												name="g-recaptcha-response" />
											
											<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

											<!-- Email Input -->
											<div class="mb-3">
												<label for="email" class="form-label">Email</label> <input
													type="email" class="form-control" name="email" id="email"
													placeholder="Vui lòng nhập email" required>
												<div class="invalid-feedback">Vui lòng nhập email hợp
													lệ.</div>
											</div>

											<!-- Password Input -->
											<div class="mb-3">
												<label for="password" class="form-label">Mật khẩu</label>
												<div class="input-group input-password">
													<input type="password" class="form-control" name="password"
														id="password" placeholder="Vui lòng nhập mật khẩu"
														required>
													<button type="button" class="btn btn-light border"
														id="password-toggle">
														<i class="fa fa-eye fa-fw"></i>
													</button>
													<div class="invalid-feedback">Vui lòng nhập mật khẩu.</div>
												</div>
											</div>
											<div
												class="form-check mb-3 d-flex justify-content-end align-items-end">
												<a class="text-primary fw-medium ms-1"
													href="forgotpassword">Quên mật khẩu</a>
											</div>
											<div class="d-flex flex-column align-items-center">
												<div class="g-recaptcha mb-3"
													data-sitekey="6LfGAJYqAAAAAB0X5ZPkvqjkKV0yhiA3zsSHhkeW"></div>
												<div id="recaptcha-error" class="invalid-feedback"
													style="display: none;">Vui lòng xác thực reCAPTCHA.</div>
											</div>
											<c:if test="${param.error != null}">
												<div class="alert alert-danger mt-3" role="alert">Đăng nhập thất bại! Vui lòng kiểm tra lại email hoặc mật
													khẩu của bạn.</div>
											</c:if>


											<button onclick="onClick(event)" type="submit"
												class="btn btn-primary w-100 submit">Đăng nhập</button>
											<div class="d-flex justify-content-center mx-3 my-1 py-2">
												<div class="mx-2">
													<a
														href="https://accounts.google.com/o/oauth2/v2/auth?scope=profile email&redirect_uri=http://localhost:8080/bookstore/login-google&response_type=code&client_id=644611492314-nck1gv86bbn2a8jc97ivgf3viuaged1p.apps.googleusercontent.com&approval_prompt=force"
														target="_blank" class="google-btn"> <img
														src="resources/images/search.png" alt="Google Login"
														style="max-width: 30px;">
													</a>
												</div>
												<div class="mx-2">
													<a
														href="https://github.com/login/oauth/authorize?scope=user&client_id=Ov23liaEWyhUvSt8WkUC"
														target="_blank" class="google-btn"> <img
														src="resources/images/github-logo.png" alt="GitHub Login"
														style="max-width: 30px;">
													</a>
												</div>
											</div>

											<div class="mt-3 row">
												<div class="text-center col-12">
													<p class="text-muted">
														Bạn chưa có tài khoản? <a
															class="text-primary fw-medium ms-1"
															href="${pageContext.request.contextPath}/signup">Đăng
															ký</a>
													</p>
												</div>
											</div>
										</form>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</section>
		</div>
	</div>

	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

	<script>
  $(document).ready(function() {
	    showPassword();
	    handleValidationForm();
	});

	// Toggle password visibility
	function showPassword() {
	    $(".input-password").each(function() {
	        var grp = $(this);
	        var btn = grp.find("button");
	        btn.on("click", function() {
	            var input = grp.find("input");
	            var icon = btn.find("i");
	            if (input.attr("type") == "password") {
	                input.attr("type", "text");
	                icon.removeClass("fa-eye");
	                icon.addClass("fa-eye-slash");
	            } else {
	                input.attr("type", "password");
	                icon.removeClass("fa-eye-slash");
	                icon.addClass("fa-eye");
	            }
	        });
	    });
	}

	// Form validation
	// Form validation
function handleValidationForm() {
    $("#signinform").on("submit", function(e) {
        let isValid = true;
        const emailInput = $("#email");
        const passwordInput = $("#password");
        const emailValue = emailInput.val().trim();
        const passwordValue = passwordInput.val().trim();
        const recaptchaResponse = grecaptcha.getResponse(); 
        const recaptchaContainer = $("#recaptcha-container");
        const recaptchaError = $("#recaptcha-error");

        // Validate email
        if (emailValue == "" || !validateEmail(emailValue)) {
            emailInput.addClass("is-invalid");
            isValid = false;
        } else {
            emailInput.removeClass("is-invalid");
        }

        // Validate password
        if (passwordValue == "") {
            passwordInput.addClass("is-invalid");
            isValid = false;
        } else {
            passwordInput.removeClass("is-invalid");
        }

        // Validate reCAPTCHA
        if (recaptchaResponse == "") {
            recaptchaError.show(); 
            isValid = false;
        } else {
            recaptchaError.hide(); 
        }

        if (isValid) {
            e.preventDefault();

            if (recaptchaReady) {
                grecaptcha.execute('6LfGAJYqAAAAAB0X5ZPkvqjkKV0yhiA3zsSHhkeW', { action: 'LOGIN' })
                    .then((token) => {
                        document.getElementById('g-recaptcha-response').value = token; 
                        document.getElementById('signinform').submit();
                    })
                    .catch((error) => {
                        console.error("Lỗi khi thực hiện reCAPTCHA:", error);
                    });
            } else {
                console.error("reCAPTCHA chưa được tải!");
            }
        } else {
            e.preventDefault();
        }
    });
}


	// Email validation regex
	function validateEmail(email) {
	    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
	    return emailRegex.test(email);
	}

	let recaptchaReady = false;

	// Hàm onRecaptchaLoad được gọi khi reCAPTCHA đã tải xong
	function onRecaptchaLoad() {
	    recaptchaReady = true;
	}

  </script>
</body>
</html>
