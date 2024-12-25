<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Profile</title>
<base href="${pageContext.servletContext.contextPath}/admin1337/">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
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

.no-bullet {
	list-style-type: none;
}
</style>
</head>
<body>
	<jsp:include page="/WEB-INF/views/layouts/header.jsp" />

	<div class="main-content">
		<jsp:include page="/WEB-INF/views/layouts/navigation.jsp" />

		<div class="container-fluid d-flex flex-column"
			style="padding-left: 10px; padding-right: 10px;">
			<form id="frm-admin" name="adminForm"
				action="profile/save.htm"
				method="POST" enctype="multipart/form-data">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				<div class="bg-light p-3 mt-3">
					<div class="d-flex align-items-center">
						<div class="flex-shrink-0 avatar avatar-xl me-3">
							<c:choose>
								<c:when test="${not empty sessionScope.user.avatar}">
									<img id="avatar_placeholder" class="rounded"
										src="${sessionScope.user.avatar}"
										alt="${sessionScope.user.fullname}"
										style="width: 120px; height: 120px; object-fit: cover;">
								</c:when>
								<c:otherwise>
									<img id="avatar_placeholder" class="rounded"
										alt="${sessionScope.user.fullname}"
										style="width: 120px; height: 120px; object-fit: cover;">
								</c:otherwise>
							</c:choose>

						</div>
						<div class="flex-grow-1">
							<label for="avatar" class="btn btn-secondary btn-sm mb-2">
								<i class="fas fa-sync-alt fa-fw me-2"></i>Change Avatar
							</label> <input type="file" class="form-control-file" id="avatar"
								name="avatarFile" style="display: none;" accept="image/*"
								onchange="previewFileUpload(event, 'avatar_placeholder')">
							<small class="form-text text-muted">Upload JPG, GIF, or
								PNG image. 300 x 300 pixels is recommended.</small>
						</div>
					</div>
				</div>

				<div class="card mt-3">
					<div class="card-body">
						<h6 class="small text-muted">GENERAL INFORMATION</h6>
						<div class="row g-3 mt-2">
							<div class="col-md-6">
								<div class="form-floating">
									<input type="email" class="form-control" id="email"
										name="email" value="${sessionScope.user.email}" disabled>
									<label class="form-label" for="email">Email address</label>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-floating">
									<input type="text" class="form-control" id="phone" name="phone"
										value="${sessionScope.user.phone}" pattern="^\d{10}$" required>
									<label class="form-label" for="phone">Phone number</label>
									<div class="invalid-feedback">Please enter a valid phone number (exactly 10 digits).</div>
								</div>


							</div>
							<div class="col-md-6">
								<div class="form-floating">
									<input type="text" class="form-control" id="fullname"
										name="fullname" value="${sessionScope.user.fullname}" required>
									<label class="form-label" for="fullname">Full name <span
										class="text-danger">*</span></label>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-floating">
									<select class="form-control form-select" id="select_menu"
										name="select_menu">
										<option value="1"
											${sessionScope.user.gender == 1 ? 'selected' : ''}>Male</option>
										<option value="2"
											${sessionScope.user.gender == 2 ? 'selected' : ''}>Female</option>
										<option value="0"
											${sessionScope.user.gender == 0 ? 'selected' : ''}>Other</option>
									</select> <label class="form-label" for="select_menu">Gender</label>
								</div>
							</div>
							<div class="col-md-6">
								<div class="input-group input-password">
									<div class="form-floating">
										<input type="password" class="form-control" name="password"
											id="password" oninput="validatePassword(this.value)">
										<label class="form-label" for="password">New password</label>
									</div>
									<button type="button" class="btn btn-light border">
										<i class="far fa-eye fa-fw"></i>
									</button>
								</div>
								<div class="form-group password-strength" id="password-strength"
									style="display: none;">
									<ul class="no-bullet">
										<li id="minLength"><i class="fas fa-times text-danger"></i>
											Minimum 12 characters</li>
										<li id="uppercase"><i class="fas fa-times text-danger"></i>
											At least one uppercase letter</li>
										<li id="lowercase"><i class="fas fa-times text-danger"></i>
											At least one lowercase letter</li>
										<li id="symbol"><i class="fas fa-times text-danger"></i>
											At least one symbol (@$!%*?&)</li>
									</ul>
								</div>
							</div>

							<div class="col-md-6">
								<div class="input-group input-password">
									<div class="form-floating">
										<input type="password" class="form-control"
											name="password_confirmation" id="password_confirmation">
										<label class="form-label" for="password_confirmation">Confirm
											new password</label>
									</div>
									<button type="button" class="btn btn-light border">
										<i class="far fa-eye fa-fw"></i>
									</button>
								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="mt-3">
					<button class="btn btn-primary btn-save" type="submit">Save</button>
				</div>
			</form>
		</div>
	</div>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
	<script>
	<script>
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
			const strongPasswordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;

			document.getElementById('minLength').innerHTML = password.length >= 12 ? '<i class="fas fa-check text-success"></i> Minimum 12 characters'
					: '<i class="fas fa-times text-danger"></i> Minimum 12 characters';
			document.getElementById('uppercase').innerHTML = /[A-Z]/
					.test(password) ? '<i class="fas fa-check text-success"></i> At least one uppercase letter'
					: '<i class="fas fa-times text-danger"></i> At least one uppercase letter';
			document.getElementById('lowercase').innerHTML = /[a-z]/
					.test(password) ? '<i class="fas fa-check text-success"></i> At least one lowercase letter'
					: '<i class="fas fa-times text-danger"></i> At least one lowercase letter';
			document.getElementById('symbol').innerHTML = /[@$!%*?&]/
					.test(password) ? '<i class="fas fa-check text-success"></i> At least one symbol (@$!%*?&)'
					: '<i class="fas fa-times text-danger"></i> At least one symbol (@$!%*?&)';

			const errorMessage = document.getElementById('errorMessage');
			if (strongPasswordRegex.test(password)) {
				errorMessage.textContent = 'Strong Password';
				errorMessage.classList.remove('text-danger');
				errorMessage.classList.add('text-success');
			} else {
				errorMessage.textContent = 'Weak Password';
				errorMessage.classList.remove('text-success');
				errorMessage.classList.add('text-danger');
			}
		}

		$(document)
				.ready(
						function() {
							$("#frm-admin")
									.on(
											"submit",
											function(e) {
												var password = $("#password")
														.val();
												var passwordConfirmation = $(
														"#password_confirmation")
														.val();

												if (password !== passwordConfirmation) {
													e.preventDefault();
													Swal
															.fire({
																icon : "error",
																title : "Oops...",
																text : "Password and confirmation do not match."
															});
												}
											});

							showPassword();

							// Kiểm tra nếu có thông báo thành công từ phía server
							<%if (request.getAttribute("success") != null) {%>
				            Swal.fire({
				                icon: 'success',
				                title: 'Updated Successfully',
				                text: '<%=request.getAttribute("success")%>',
				                toast: true,
				                position: 'top-end',
				                showConfirmButton: false,
				                timer: 3000,
				                timerProgressBar: true
				            });
				        <%}%>

				        // Kiểm tra nếu có thông báo lỗi từ FlashAttribute
				        <%if (request.getAttribute("error") != null) {%>
				            Swal.fire({
				                icon: 'error',
				                title: 'Oops...',
				                text: '<%=request.getAttribute("error")%>
		',
				toast : true,
				position : 'top-end',
				showConfirmButton : false,
				timer : 3000,
				timerProgressBar : true
			});
	<%}%>
		});

		document.getElementById('phone').addEventListener('input', function() {
		    const phoneInput = document.getElementById('phone');
		    const phoneValue = phoneInput.value;

		    const phoneRegex = /^\d{10}$/;

		    if (!phoneRegex.test(phoneValue)) {
		        phoneInput.classList.add('is-invalid');
		        phoneInput.classList.remove('is-valid');
		    } else {
		        phoneInput.classList.add('is-valid');
		        phoneInput.classList.remove('is-invalid');
		    }
		});

		document.getElementById('frm-admin').addEventListener('submit', function(e) {
		    const phoneInput = document.getElementById('phone');
		    const phoneValue = phoneInput.value;
		    const phoneRegex = /^\d{10}$/;

		    if (!phoneRegex.test(phoneValue)) {
		        e.preventDefault();  
		        phoneInput.classList.add('is-invalid');
		        phoneInput.classList.remove('is-valid');
		    }
		});



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
	</script>
</body>
</html>
