<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Book Library</title>
<base href="${pageContext.servletContext.contextPath}/">
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/css/toastr.min.css"
	rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/js/toastr.min.js"></script>
</head>
<style>
.signin-container {
	padding: 25px 0px;
	text-align: center;
}

.btn-signin {
	text-decoration: underline;
	font-weight: 600;
	color: #404040;
	background:;
}

.btn-signin:hover {
	color: #000;
}
</style>
<body>

	<header id="tg-header" class="tg-header tg-haslayout">
		<div class="tg-middlecontainer">
			<div class="container">
				<div class="row">
					<div class="col-xs-10 col-sm-10 col-md-10 col-lg-10">

						<strong class="tg-logo"><a href="index"><img
								src="${pageContext.servletContext.contextPath}/resources/images/ALDPT.png"
								alt="BookStore"></a></strong>

						<div class="tg-allcategories">
							<ul>
								<li class="menu-item-has-children menu-item-has-mega-menu"
									style="list-style: none;"><a href="javascript:void(0);"
									style="color: #404040;"> <i class="fa-solid fa-book"
										style="font-size: 20px; padding: 0 4px 0 0;"></i> <span
										style="font-weight: 600; line-height: 20px;">Danh mục</span>
								</a>
									<div class="mega-menu">
										<!-- Danh sách Tabs -->
										<ul class="tg-themetabnav" role="tablist">
											<c:forEach var="category" items="${Categories}">
												<li role="presentation"
													class="<c:if test='${category.id == 1}'>active</c:if>">
													<a href="#category-${category.id}"
													aria-controls="category-${category.id}" role="tab"
													data-toggle="tab">${category.name}</a>
												</li>
											</c:forEach>
										</ul>

										<!-- Nội dung Tabs -->
										<div class="tab-content tg-themetabcontent">
											<c:forEach var="category" items="${Categories}">
												<div role="tabpanel"
													class="tab-pane <c:if test='${category.id == 1}'>active</c:if>"
													id="category-${category.id}">
													<ul>
														<!-- Hiển thị các Subcategories -->
														<li>
															<div class="tg-linkstitle">
																<h2>
																	<a>${category.name}</a>
																</h2>
															</div>
															<ul>
																<c:forEach var="subcategory" items="${SubCategories}">
																	<c:if
																		test="${subcategory.categoriesEntity.id == category.id}">
																		<li style="list-style: none;"><a
																			href="http://localhost:8080/bookstore/categories/${category.slug }/${subcategory.slug }">${subcategory.name}</a></li>
																	</c:if>
																</c:forEach>
															</ul>
														</li>
													</ul>

												</div>
											</c:forEach>
										</div>
									</div></li>
							</ul>
						</div>

						<div class="tg-wishlistandcart">
							<div class="dropdown tg-themedropdown tg-minicartdropdown">
								<a
									href="${pageContext.servletContext.contextPath}/cart/view"
									id="tg-minicart" class="tg-btnthemedropdown"
									aria-haspopup="true" aria-expanded="false"> <span
									class="tg-themebadge">${sessionScope.countBooksInCart}</span> <i
									class="fa-solid fa-cart-shopping"></i>
								</a>
							</div>
						</div>

						<div class="tg-searchbox">
							<form class="tg-formtheme tg-formsearch"
								action="${pageContext.servletContext.contextPath}/search"
								method="GET" onsubmit="return validateSearch();"
								style="width: 550px">
								<fieldset>
									<!-- Ô nhập liệu tìm kiếm -->
									<input id="searchInput" type="text" name="q"
										class="typeahead form-control" placeholder="">
									<!-- Nút tìm kiếm -->
									<button type="submit">
										<i class="fa-solid fa-magnifying-glass"></i>
									</button>
								</fieldset>

							</form>
						</div>
						<div class="action">
							<c:choose>
								<c:when test="${not empty user}">
									<div class="profile" onclick="menuToggle();">
										<img src="${user.avatar}" style="width: 60px; height: 60px" />
									</div>
									<div class="menu">
										<h3>${user.fullname}</h3>
										<ul>
											<c:if test="${sessionScope.role == 'ROLE_ADMIN' || sessionScope.role == 'ROLE_STAFF'}">
												<li><i class="fa-solid fa-tachometer-alt"></i>&nbsp;&nbsp;
													<a
													href="${pageContext.servletContext.contextPath}/admin1337/home">Trang
														quản lý</a></li>
											</c:if>

											<li><i class="fa-solid fa-user"></i>&nbsp;&nbsp;<a
												href="${pageContext.servletContext.contextPath}/account/profile_settings">Trang
													cá nhân</a></li>
											<li><i class="fa-solid fa-shop"></i>&nbsp;&nbsp;<a
												href="${pageContext.servletContext.contextPath}/account/account_orders">Đơn
													hàng của bạn</a></li>
													
											<form id="signoutForm"
												action="${pageContext.servletContext.contextPath}/signout"
												method="POST" style="display: none;">
												<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
												<input type="hidden" name="signout" value="true">
											</form>
											<li><i class="fa-solid fa-right-from-bracket"></i>&nbsp;&nbsp;<a
												onclick="document.getElementById('signoutForm').submit();">Đăng
													xuất</a></li>
										</ul>
									</div>
								</c:when>
								<c:otherwise>
									<div class="signin-container">
										<a href="${pageContext.servletContext.contextPath}/signin"
											class="btn-signin"> <span
											style="font-weight: 600; line-height: 20px; color: #404040;">Đăng
												nhập</span>
										</a>
									</div>

								</c:otherwise>
							</c:choose>
						</div>

					</div>
				</div>
			</div>
		</div>

	</header>


	<script type="text/javascript">
		document.addEventListener('DOMContentLoaded', function() {
			const searchInput = document.querySelector('input[name="q"]');
			const placeholderTexts = [ "Tìm kiếm theo tên sách",
					"Tìm kiếm theo tác giả",
					"Tìm kiếm theo khoảng giá (Ví dụ: 150000 - 250000)",
					"Tìm kiếm theo mức giảm giá (Ví dụ: 10%)" ]; // Các cụm từ Placeholder động
			let textIndex = 0; // Vị trí cụm từ hiện tại
			let charIndex = 0; // Vị trí ký tự trong cụm từ hiện tại
			let isDeleting = false; // Xác định trạng thái xóa chữ

			function typeEffect() {
				const currentText = placeholderTexts[textIndex];
				if (!isDeleting) {
					// Gõ chữ
					searchInput.setAttribute('placeholder', currentText
							.substring(0, charIndex + 1));
					charIndex++;
					if (charIndex === currentText.length) {
						// Hoàn thành gõ 1 cụm từ
						isDeleting = true;
						setTimeout(typeEffect, 2000); // Tạm dừng trước khi bắt đầu xóa
						return;
					}
				} else {
					// Xóa chữ
					searchInput.setAttribute('placeholder', currentText
							.substring(0, charIndex - 1));
					charIndex--;
					if (charIndex === 0) {
						// Hoàn thành xóa, chuyển sang cụm từ tiếp theo
						isDeleting = false;
						textIndex = (textIndex + 1) % placeholderTexts.length; // Chuyển sang cụm từ tiếp theo
					}
				}
				setTimeout(typeEffect, isDeleting ? 100 : 150); // Tốc độ xóa/gõ
			}

			typeEffect(); // Bắt đầu hiệu ứng
		});

		function menuToggle() {
			const toggleMenu = document.querySelector(".menu");
			toggleMenu.classList.toggle("active");
		}

		function validateSearch() {
			const searchInput = document.getElementById("searchInput").value
					.trim();
			if (searchInput === "") {
				return false; // Ngăn form gửi request
			}
			return true; // Cho phép gửi request
		}
	</script>
</body>
</html>