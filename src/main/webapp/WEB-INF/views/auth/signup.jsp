<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Sign Up</title>

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
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

<!-- Custom CSS -->
<style>
.mt-5 {
	margin-top: 7rem !important;
}

.auth-logo img {
	max-width: 150px;
}

.no-bullet {
	list-style-type: none;
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
										<form id="signupform" class="needs-validation" novalidate
											action="${pageContext.servletContext.contextPath}/saveSignup"
											method="POST">
											<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
											<div class="mb-3">
												<label for="fullname" class="form-label">Họ tên</label> <input
													type="text" class="form-control" name="fullname"
													id="fullname" placeholder="Vui lòng nhập Họ tên" required>
												<div class="invalid-feedback">Vui lòng nhập Họ tên.</div>
											</div>
											<!-- Email Input -->
											<div class="mb-3">
												<label for="email" class="form-label">Email</label> <input
													type="email" class="form-control" name="email" id="email"
													placeholder="Vui lòng nhập email" required>
												<div class="invalid-feedback">Vui lòng nhập email.</div>
											</div>

											<!-- PhoneNumber Input -->
											<div class="mb-3">
												<label for="email" class="form-label">Số điện thoại</label>
												<input type="text" class="form-control" name="phone"
													id="phone" placeholder="Vui lòng nhập số điện thoại"
													required>
												<div class="invalid-feedback">Vui lòng nhập số điện
													thoại.</div>
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
												<div class="form-group password-strength"
													id="password-strength" style="display: none;">
													<ul class="no-bullet">
														<li id="minLength"><i
															class="fas fa-times text-danger"></i> Tối thiểu 12 ký tự</li>
														<li id="uppercase"><i
															class="fas fa-times text-danger"></i> Ít nhất một chữ cái
															viết hoa</li>
														<li id="lowercase"><i
															class="fas fa-times text-danger"></i> Ít nhất một chữ cái
															viết thường</li>
														<li id="symbol"><i class="fas fa-times text-danger"></i>
															Ít nhất một ký tự đặc biệt (@$!%*?&)</li>
														<li id="number"><i class="fas fa-times text-danger"></i>
															Ít nhất một chữ số</li>
													</ul>
												</div>

											</div>

											<!-- Confirm Password Input -->
											<div class="mb-3">
												<label for="confirm-password" class="form-label">Xác
													nhận mật khẩu</label>
												<div class="input-group input-password">
													<input type="password" class="form-control"
														name="confirm-password" id="confirm-password"
														placeholder="Xác nhận mật khẩu" required>
													<button type="button" class="btn btn-light border"
														id="confirm-password-toggle">
														<i class="fa fa-eye fa-fw"></i>
													</button>
													<div class="invalid-feedback">Mật khẩu không trùng
														khớp</div>
												</div>
											</div>

											<button type="submit" class="btn btn-primary w-100 submit">Đăng
												ký</button>
											<div class="mt-3 row">
												<div class="text-center col-12">
													<p class="text-muted">
														Bạn đã có tài khoản? <a
															class="text-primary fw-medium ms-1"
															href="signin"> Đăng nhập</a>
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

	<!-- jQuery and Bootstrap JS -->
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

		function handleValidationForm() {
		    const fullnameInput = $("#fullname");
		    const emailInput = $("#email");
		    const phoneInput = $("#phone");
		    const passwordInput = $("#password");
		    const confirmPasswordInput = $("#confirm-password");

		    function validateField(input, isValid, errorMessage) {
		        if (!isValid) {
		            input.addClass("is-invalid");
		            input.next(".invalid-feedback").text(errorMessage).show();
		        } else {
		            input.removeClass("is-invalid");
		            input.next(".invalid-feedback").hide();
		        }
		    }
		    
		    fullnameInput.on("input blur", function(){
				const value = $(this).val().trim()
				if(value === ""){
					validateField($(this),false, "Vui lòng nhập Họ tên");
				}else{
		            validateField($(this), true, "");
				}
		    });

		    emailInput.on("input blur", function () {
		        const value = $(this).val().trim();
		        if (value === "") {
		            validateField($(this), false, "Vui lòng nhập địa chỉ Email.");
		        } else if (!validateEmail(value)) {
		            validateField($(this), false, "Địa chỉ email không hợp lệ.");
		        } else {
		            validateField($(this), true, "");
		        }
		    });

		    phoneInput.on("input blur", function () {
		        const value = $(this).val().trim();
		        if (value === "") {
		            validateField($(this), false, "Vui lòng nhập số điện thoại.");
		        } else if (!validatePhone(value)) {
		            validateField($(this), false, "Số điện thoại không hợp lệ.");
		        } else {
		            validateField($(this), true, "");
		        }
		    });

		    passwordInput.on("input blur", function () {
		        const value = $(this).val().trim(); // Lấy giá trị và xóa khoảng trắng

		        // Kiểm tra nếu mật khẩu trống
		        if (value === "") {
		            validateField($(this), false, "Mật khẩu không được để trống.");
		        }
		        // Kiểm tra mật khẩu có đủ mạnh không
		        else if (!validatePassword(value)) {
		            validateField($(this), false, "Mật khẩu phải có ít nhất 12 ký tự, bao gồm chữ hoa, chữ thường, số và ký tự đặc biệt.");
		        }
		        // Mật khẩu hợp lệ
		        else {
		            validateField($(this), true, "");
		        }
		    });

		    confirmPasswordInput.on("input blur", function () {
		        const passwordValue = passwordInput.val().trim();
		        const confirmPasswordValue = $(this).val().trim();
		        if (confirmPasswordValue !== passwordValue) {
		            validateField($(this), false, "Mật khẩu xác nhận không khớp.");
		        } else {
		            validateField($(this), true, "");
		        }
		    });

		    $("#signupform").on("submit", function (e) {
		        let isValid = true;

		        const fullnameValue = fullnameInput.val().trim();
		        const emailValue = emailInput.val().trim();
		        const phoneValue = phoneInput.val().trim();
		        const passwordValue = passwordInput.val().trim();
		        const confirmPasswordValue = confirmPasswordInput.val().trim();

		        if (fullnameValue === "") {
		            validateField(fullnameInput, false, "Vui lòng nhập họ tên.");
		            isValid = false;
		        }

		        if (emailValue === "") {
		            validateField(emailInput, false, "Vui lòng nhập địa chỉ Email.");
		            isValid = false;
		        } else if (!validateEmail(emailValue)) {
		            validateField(emailInput, false, "Địa chỉ email không hợp lệ.");
		            isValid = false;
		        }

		        if (phoneValue === "") {
		            validateField(phoneInput, false, "Vui lòng nhập số điện thoại.");
		            isValid = false;
		        } else if (!validatePhone(phoneValue)) {
		            validateField(phoneInput, false, "Số điện thoại không hợp lệ.");
		            isValid = false;
		        }

		        if (passwordValue === "") {
		            validateField(passwordInput, false, "Mật khẩu không được để trống.");
		            isValid = false;
		        } else if (!validatePassword(passwordValue)) {
		            validateField(passwordInput, false, "Mật khẩu không đủ mạnh.");
		            isValid = false;
		        }

		        if (confirmPasswordValue !== passwordValue) {
		            validateField(confirmPasswordInput, false, "Mật khẩu xác nhận không khớp.");
		            isValid = false;
		        }

		        if (!isValid) {
		            e.preventDefault(); 
		        }
		    });
		}


		function validateEmail(email) {
			const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
			return emailRegex.test(email);
		}
		
		function validatePhone(phone) {
			const regexPhoneNumber = /(84|0[3|5|7|8|9])+([0-9]{8})\b/g;
			return regexPhoneNumber.test(phone);
		}
		
    const alertMessage = "${alertMessage}";
    const alertType = "${alertType}";
    
    if (alertMessage) {
        Swal.fire({
            icon: alertType, 
            title: alertType === "success" ? "Success" : alertType === "error" ? "Error" : "Warning",
            text: alertMessage,
            confirmButtonText: "OK"
        }).then((result) => {
            if (result.isConfirmed && alertType === "success") {
                window.location.href = '${pageContext.servletContext.contextPath}/signin';
            } else {
	        	window.location.href = '${pageContext.servletContext.contextPath}/signin';
	        }
        });
    }
    
	function showPasswordStrength() {
		var passwordInput = document.getElementById('password');
		var strengthDiv = document.getElementById('password-strength');

		if (passwordInput.value.length > 0) {
			strengthDiv.style.display = 'block';
		} else {
			strengthDiv.style.display = 'none';
		}
	}

	document.getElementById('password').addEventListener('input',
			showPasswordStrength);

	function validatePassword(password) {
        let isValid = true;

        const minLengthValid = password.length >= 12;
        document.getElementById('minLength').innerHTML = minLengthValid 
            ? '<i class="fas fa-check text-success"></i> Tối thiểu 12 ký tự'
            : '<i class="fas fa-times text-danger"></i> Tối thiểu 12 ký tự';
        isValid = isValid && minLengthValid;

        const uppercaseValid = /[A-Z]/.test(password);
        document.getElementById('uppercase').innerHTML = uppercaseValid 
            ? '<i class="fas fa-check text-success"></i> Ít nhất một chữ cái viết hoa'
            : '<i class="fas fa-times text-danger"></i> Ít nhất một chữ cái viết thường';
        isValid = isValid && uppercaseValid;

        const lowercaseValid = /[a-z]/.test(password);
        document.getElementById('lowercase').innerHTML = lowercaseValid 
            ? '<i class="fas fa-check text-success"></i> Ít nhất một chữ cái viết thường'
            : '<i class="fas fa-times text-danger"></i> Ít nhất một chữ cái viết thường';
        isValid = isValid && lowercaseValid;

        const symbolValid = /[@$!%*?&]/.test(password);
        document.getElementById('symbol').innerHTML = symbolValid 
            ? '<i class="fas fa-check text-success"></i> Ít nhất một ký tự đặc biệt (@$!%*?&)'
            : '<i class="fas fa-times text-danger"></i> Ít nhất một ký tự đặc biệt (@$!%*?&)';
        isValid = isValid && symbolValid;

        const numberValid = /\d/.test(password);
        document.getElementById('number').innerHTML = numberValid 
            ? '<i class="fas fa-check text-success"></i> Ít nhất một chữ số'
            : '<i class="fas fa-times text-danger"></i> Ít nhất một chữ số';
        isValid = isValid && numberValid;

        return isValid; 
    }

</script>
</body>
</html>
