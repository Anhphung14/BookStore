<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<base href="${pageContext.servletContext.contextPath}/admin1337/">
<style>
.nav-link.dropdown-toggle {
	padding-right: 0;
}

.dropdown-item {
	padding: 12px 20px;
}

.dropdown-item:hover {
	background-color: #f8f9fa;
}

.dropdown-item i {
	margin-right: 10px;
}

.dropdown-toggle::after {
	display: none !important;
}

nav.navbar {
	height: 60px !important;
	padding-top: 5px;
	padding-bottom: 5px;
	border-bottom: 1px solid #dee2e6;
	background-color: #ffffff;
}

.auth-logo img {
	width: 70px;
}

.topbar-menu li .avatar img {
	width: 40px;
}

.toggle-btn {
	font-size: 1.5rem;
	cursor: pointer;
}

#sidebar.show {
	left: 30px;
}
</style>

<nav class="navbar navbar-topbar navbar-expand-lg sticky-top">
	<div
		class="container-fluid d-flex justify-content-between align-items-center">
		<ul
			class="list-unstyled topbar-menu mb-0 d-flex align-items-center justify-content-start">
			<li class="dropdown d-none d-sm-block">
				<div class="auth-logo d-flex align-items-center">
					<img alt="Logo"
						src="${pageContext.servletContext.contextPath}/resources/images/ALDPT.png"
						class="img-fluid"> <span class="logo-name ms-2"
						style="color: #343a40; font-size: 1.25rem; font-weight: 600; line-height: 4.5rem;">Book
						<span class="fw-light">Store</span>
					</span>
				</div>
			</li>
		</ul>
		<div class="toggle-btn p-5" onclick="toggleSidebar()">
			<i class="fa fa-bars"></i>
		</div>

		<div class="d-flex gap-3">
			<a href="${pageContext.servletContext.contextPath}/index.htm"
				class="btn btn-outline-dark d-flex align-items-center gap-2"> <i
				class="fa fa-globe fa-lg"></i> <span>Trang chủ BookStore</span>
			</a> <a href="https://dashboard.tawk.to/#/chat"
				class="btn btn-outline-dark d-flex align-items-center gap-2 px-2">
				<i class="fa fa-comments"></i> <span>Chat</span>
			</a>
		</div>



		<ul
			class="list-unstyled topbar-menu ms-auto mb-0 d-flex align-items-center justify-item-center fixed">
			<li></li>
			<li class="dropdown"><a class="nav-link dropdown-toggle"
				href="#" id="userDropdown" role="button" data-bs-toggle="dropdown"
				aria-expanded="false">
					<div class="avatar">
						<img alt="User Avatar" src="${sessionScope.user.avatar}"
							class="rounded-circle bg-white border border-3 border-white">
					</div>
			</a>

				<ul class="dropdown-menu dropdown-menu-end border-0 shadow"
					aria-labelledby="userDropdown">
					<li class="text-center p-3 border-bottom mb-3">
						<div class="avatar avatar-xxl">
							<img alt="User Avatar" src="${sessionScope.user.avatar}"
								class="rounded-circle bg-white border border-3 border-white">
						</div>
						<div class="mt-3">
							<h5>${sessionScope.user.fullname}</h5>
							<div class="text-muted">${sessionScope.user.email}</div>
						</div>
					</li>
					<li><a class="dropdown-item" href="profile.htm"><i
							class="fa fa-user"></i> Profile</a></li>
					<form id="signoutForm"
						action="${pageContext.servletContext.contextPath}/signout.htm"
						method="POST" style="display: none;">
						<input type="hidden" name="signout" value="true">
					</form>
					<li><a class="dropdown-item" href="javascript:void(0);"
						onclick="document.getElementById('signoutForm').submit();"><i
							class="fa fa-sign-out"></i> Sign Out</a></li>
				</ul></li>
		</ul>
	</div>
</nav>

<script>
	function toggleSidebar() {
		const sidebar = document.getElementById("sidebar");
		sidebar.classList.toggle("collapse");
	}
</script>
