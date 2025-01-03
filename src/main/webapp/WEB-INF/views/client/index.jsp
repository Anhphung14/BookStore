<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="f"%>

<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Book Library</title>
<base href="${pageContext.servletContext.contextPath}/">
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- Apple touch icon -->
<link rel="apple-touch-icon"
	href="${pageContext.servletContext.contextPath}/resources/images/client/apple-touch-icon.png">

<!-- External CSS Files -->
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
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/css/toastr.min.css"
	rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/js/toastr.min.js"></script>
<!-- Modernizr JS -->
<script
	src="resources/assets/js/client/vendor/modernizr-2.8.3-respond-1.4.2.min.js"></script>

<script src="https://kit.fontawesome.com/e70d1e2fed.js"
	crossorigin="anonymous"></script>

<!-- Modernizr JS -->
<script
	src="resources/assets/js/client/vendor/modernizr-2.8.3-respond-1.4.2.min.js"></script>
</head>
<style>
.owl-nav {
	display: flex;
	justify-content: center;
	align-items: center;
	width: 100%;
}

.tg-clientservices:hover {
	transform: scale(1.05);
}

.chat-window {
	position: fixed;
	top: 70px;
	right: 20px;
	width: 400px;
	height: 500px;
	background-color: white;
	border: 1px solid #ccc;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
	display: none;
	z-index: 10000;
}

iframe {
	width: 100%;
	height: 100%;
	border: none;
}

.chat-window.show {
	display: block;
}
</style>
<body class="tg-home tg-homeone">
	<div id="tg-wrapper" class="tg-wrapper tg-haslayout">
		<%@ include file="../client/layouts/header.jsp"%>
		<div class="banner-container"
			style="text-align: center; margin: 20px auto; width: 100%; position: relative;">
			<img
				src="https://res.cloudinary.com/dsqhfz3xt/image/upload/v1734896841/images/y9s5swwde8qowxxeseoi.gif"
				alt="Banner"
				style="width: 100%; max-width: 1500px; height: auto; display: inline-block; border-radius: 15px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2); transition: transform 0.3s ease;">
		</div>
		<section class="tg-sectionspace tg-haslayout">
			<div class="container">
				<div class="row">
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
						<div id="tg-bestsellingbooksslider" class="owl-carousel">
							<c:forEach var="book" items="${bookList}">
								<div class="item">
									<div class="tg-postbook">
										<div class="tg-bookimg">
											<div class="tg-frontcover">
												<a
													href="${pageContext.request.contextPath}/productdetail/${book.id}">
													<img src="${book.thumbnail}" alt="${book.thumbnail}" />
												</a>
											</div>
											<div class="tg-backcover">
												<img src="${book.thumbnail}" alt="${book.title}" />
											</div>
										</div>
										<div class="tg-postbookcontent">
											<ul class="tg-bookscategories" style="padding-top: 10px">
												<li><a href="javascript:void(0);">${book.subcategoriesEntity.name}</a></li>
											</ul>
											<c:if test="${bookDiscounts[book.id] != 0.0}">
												<div class="tg-themetagbox">
													<span class="tg-themetag"> Giảm giá <fmt:formatNumber
															value="${bookDiscounts[book.id]}" pattern="##" />%
													</span>
												</div>
											</c:if>
											<c:if test="${bookDiscounts[book.id] == 0.0}">
												<div class="tg-themetagbox">
													<span class=""></span>
												</div>
											</c:if>
											<h4>
												<a href="productdetail/${book.id}"> <c:choose>
														<c:when test="${fn:length(fn:split(book.title, ' ')) > 2}">
															<c:forEach var="word" begin="0" end="2"
																items="${fn:split(book.title, ' ')}">
											                    ${word}
											                </c:forEach>...
											            </c:when>
														<c:otherwise>
											                ${book.title}
											            </c:otherwise>
													</c:choose>
												</a>
											</h4>
											<span class="tg-bookwriter"> Tác giả: <a
												href="javascript:void(0);"> <c:choose>
														<c:when
															test="${fn:length(fn:split(book.author, ' ')) > 3}">
															<c:forEach var="word" begin="0" end="2"
																items="${fn:split(book.author, ' ')}">
											                        ${word} 
											                    </c:forEach>
											                    ...
											                </c:when>
														<c:otherwise>
											                    ${book.author}
											                </c:otherwise>
													</c:choose>
											</a>
											</span> <span class="tg-bookprice"> <ins>
													<fmt:formatNumber value="${book.price}" type="currency"
														currencySymbol="VND" />
												</ins>
											</span> <a class="tg-btn tg-btnstyletwo btn-add-to-cart"
												data-quantity="1" data-book-id="${book.id}"
												href="javascript:void(0);"
												style="padding-left: 0px; padding-right: 0px;"> <i
												class="fa fa-shopping-basket" style="padding-left: 10px;"></i>
												<em>Thêm vào giỏ hàng</em>
											</a>
										</div>
									</div>
								</div>
							</c:forEach>
						</div>
					</div>
				</div>
			</div>
		</section>

		<section class="tg-haslayout" style="padding-top: 2rem;">
			<div class="container">
				<div class="row">
					<c:if test="${not empty bestSellingBook}">
						<div class="tg-featureditm">
							<div
								class="col-xs-12 col-sm-12 col-md-4 col-lg-4 hidden-sm hidden-xs">
								<!-- Hiển thị ảnh của sách bán chạy nhất -->
								<div class="tg-featuredimg" style="text-align: center;">
									<img src="${bestSellingBook.thumbnail}"
										alt="${bestSellingBook.title}" style="height: 460px;" />
								</div>
							</div>
							<div class="col-xs-12 col-sm-12 col-md-8 col-lg-8">
								<div class="tg-featureditmcontent">
									<div class="tg-themetagbox">
										<span class="tg-themetag">Sách bán chạy</span>
									</div>
									<div class="tg-booktitle">
										<h3>
											<a href="productdetail/${book.id}"> <c:choose>
													<c:when
														test="${fn:length(fn:split(bestSellingBook.title, ' ')) > 2}">
														<c:forEach var="word" begin="0" end="2"
															items="${fn:split(bestSellingBook.title, ' ')}">
											                    ${word}
											                </c:forEach>...
											            </c:when>
													<c:otherwise>
											                ${bestSellingBook.title}
											            </c:otherwise>
												</c:choose>
											</a>
										</h3>
									</div>
									<span class="tg-bookwriter"> Tác giả: <a
										href="javascript:void(0);"> <c:choose>
												<c:when
													test="${fn:length(fn:split(bestSellingBook.author, ' ')) > 3}">
													<c:forEach var="word" begin="0" end="2"
														items="${fn:split(book.author, ' ')}">
											                        ${word} 
											                    </c:forEach>
											                    ...
											                </c:when>
												<c:otherwise>
											                    ${bestSellingBook.author}
											                </c:otherwise>
											</c:choose>
									</a>
									</span>
									<div class="tg-priceandbtn">
										<span class="tg-bookprice"> <ins>
												<fmt:formatNumber value="${bestSellingBook.price}"
													type="currency" currencySymbol="VND" />
											</ins>
										</span> <a class="tg-btn tg-btnstyletwo tg-active btn-add-to-cart"
											data-book-id="${bestSellingBook.id}" data-quantity="1"
											href="javascript:void(0);"> <i
											class="fa fa-shopping-basket"></i> <em>Thêm vào giỏ hàng</em>
										</a>
									</div>
								</div>
							</div>
						</div>
					</c:if>
				</div>
			</div>
		</section>
		<section class="tg-sectionspace tg-haslayout">
			<div class="container">
				<div class="row">
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
						<h2 class="text-center">Tác giả nổi tiếng</h2>
						<div class="tg-authorslist"
							style="display: flex; flex-wrap: wrap; justify-content: center; gap: 20px;">
							<!-- Tác giả 1 -->
							<div class="tg-author"
								style="display: flex; flex-direction: column; align-items: center; width: 200px;">
								<div class="tg-authorimg"
									style="width: 150px; height: 150px; margin: 0 auto;">
									<img
										src="https://res.cloudinary.com/dsqhfz3xt/image/upload/v1733335369/images/author/ebp7a9t6my0izsoa4c3v.jpg"
										alt="Nguyễn Nhật Ánh"
										style="width: 100%; height: 100%; object-fit: cover; border-radius: 50%;">
								</div>
								<div class="tg-authorcontent" style="text-align: center;">
									<h4>Nguyễn Nhật Ánh</h4>
									<p>Tác giả nổi tiếng với nhiều tác phẩm dành cho thiếu nhi.</p>
								</div>
							</div>

							<!-- Tác giả 2 -->
							<div class="tg-author"
								style="display: flex; flex-direction: column; align-items: center; width: 200px;">
								<div class="tg-authorimg"
									style="width: 150px; height: 150px; margin: 0 auto;">
									<img
										src="https://res.cloudinary.com/dsqhfz3xt/image/upload/v1733335369/images/author/wikczevryxpbsrqszmcr.jpg"
										alt="J.K. Rowling"
										style="width: 100%; height: 100%; object-fit: cover; border-radius: 50%;">
								</div>
								<div class="tg-authorcontent" style="text-align: center;">
									<h4>J.K. Rowling</h4>
									<p>Tác giả của loạt truyện Harry Potter nổi tiếng.</p>
								</div>
							</div>

							<!-- Tác giả 3 -->
							<div class="tg-author"
								style="display: flex; flex-direction: column; align-items: center; width: 200px;">
								<div class="tg-authorimg"
									style="width: 150px; height: 150px; margin: 0 auto;">
									<img
										src="https://res.cloudinary.com/dsqhfz3xt/image/upload/v1733335368/images/author/cyc9pza72ki8tokrymjx.jpg"
										alt="George Orwell"
										style="width: 100%; height: 100%; object-fit: cover; border-radius: 50%;">
								</div>
								<div class="tg-authorcontent" style="text-align: center;">
									<h4>George Orwell</h4>
									<p>Tác giả của các tác phẩm kinh điển như '1984' và 'Animal
										Farm'.</p>
								</div>
							</div>
							<!-- Tác giả 4 -->
							<div class="tg-author"
								style="display: flex; flex-direction: column; align-items: center; width: 200px;">
								<div class="tg-authorimg"
									style="width: 150px; height: 150px; margin: 0 auto;">
									<img
										src="https://res.cloudinary.com/dsqhfz3xt/image/upload/v1733412213/images/author/roh4tilaj2srebwxk8ju.jpg"
										alt="Fujiko.F.Fujio"
										style="width: 100%; height: 100%; object-fit: cover; border-radius: 50%;">
								</div>
								<div class="tg-authorcontent" style="text-align: center;">
									<h4>Fujiko F. Fujio</h4>
									<p>Tác giả của các tác phẩm kinh điển như 'Doraemon',
										'Ninja Hattori', và 'Perman'.</p>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>
		<section class="tg-sectionspace tg-haslayout">
			<div class="container">
				<div class="row">
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
						<div id="tg-discountbooksslider" class="owl-carousel">
							<!-- Kiểm tra xem có sách giảm giá -->
							<c:if test="${not empty bookDiscounts}">
								<c:forEach var="book" items="${bookList}">
									<!-- Chỉ hiển thị sách có giảm giá -->
									<c:if test="${bookDiscounts[book.id] != 0.0}">
										<h2 class="text-center">Sách Đang Giảm Giá</h2>
										<div class="item">
											<div class="tg-postbook">
												<div class="tg-bookimg">
													<div class="tg-frontcover">
														<a
															href="${pageContext.request.contextPath}/productdetail/${book.id}">
															<img src="${book.thumbnail}" alt="${book.title}" />
														</a>
													</div>
													<div class="tg-backcover">
														<img src="${book.thumbnail}" alt="${book.title}" />
													</div>
												</div>
												<div class="tg-postbookcontent">
													<ul class="tg-bookscategories" style="padding-top: 10px">
														<li><a href="javascript:void(0);">${book.subcategoriesEntity.name}</a></li>
													</ul>
													<div class="tg-themetagbox">
														<span class="tg-themetag"> Giảm giá <fmt:formatNumber
																value="${bookDiscounts[book.id]}" pattern="##" />%
														</span>
													</div>
													<div class="tg-booktitle">
														<h4>
															<a href="productdetail/${book.id}"> <c:choose>
																	<c:when
																		test="${fn:length(fn:split(book.title, ' ')) > 2}">
																		<c:forEach var="word" begin="0" end="2"
																			items="${fn:split(book.title, ' ')}">
                                                                    ${word}
                                                                </c:forEach>...
                                                            </c:when>
																	<c:otherwise>
                                                                ${book.title}
                                                            </c:otherwise>
																</c:choose>
															</a>
														</h4>
													</div>
													<span class="tg-bookwriter">Tác giả: <a
														href="javascript:void(0);"> <c:choose>
																<c:when
																	test="${fn:length(fn:split(book.author, ' ')) > 3}">
																	<c:forEach var="word" begin="0" end="2"
																		items="${fn:split(book.author, ' ')}">
                                                            ${word} 
                                                        </c:forEach>...
                                                    </c:when>
																<c:otherwise>
                                                        ${book.author}
                                                    </c:otherwise>
															</c:choose>
													</a></span> <span class="tg-bookprice"> <ins>
															<fmt:formatNumber value="${book.price}" type="currency"
																currencySymbol="VND" />
														</ins>
													</span> <a class="tg-btn tg-btnstyletwo btn-add-to-cart"
														data-book-id="${book.id}" data-quantity="1"
														href="javascript:void(0);"
														style="padding-left: 0px; padding-right: 0px;"> <i
														class="fa fa-shopping-basket" style="padding-left: 15px;"></i>
														<em>Thêm vào giỏ hàng</em>
													</a>
												</div>
											</div>
										</div>
									</c:if>
								</c:forEach>
							</c:if>
						</div>
					</div>
				</div>
			</div>
		</section>

		<section class="tg-sectionspace tg-haslayout"
			style="padding: 50px 50px;">
			<div class="container">
				<div class="row">
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12"
						style="padding-bottom: 20px;">
						<h2 class="text-center"
							style="font-size: 36px; color: #333; margin-bottom: 30px;">Cam
							Kết</h2>
						<div class="tg-authorslist"
							style="display: flex; flex-wrap: wrap; justify-content: center; gap: 40px;">

							<div class=tg-clientservices
								style="display: flex; flex-direction: column; align-items: center; width: 220px; background-color: #fff; padding: 20px; border-radius: 10px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); transition: transform 0.3s;">
								<div class="tg-clientservices"
									style="width: 150px; height: 150px;">
									<span class="tg-clientserviceicon"
										style="font-size: 50px; color: #ff4500;"><i
										class="fa-solid fa-rocket"></i></span>
								</div>
								<h3 style="font-size: 20px; color: #555;">Giao Hàng Nhanh</h3>
								<p style="color: #777;">Nhanh Chóng, Đúng Hẹn</p>
							</div>

							<div class="tg-clientservices"
								style="display: flex; flex-direction: column; align-items: center; width: 220px; background-color: #fff; padding: 20px; border-radius: 10px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); transition: transform 0.3s;">
								<div class="tg-clientservices"
									style="width: 150px; height: 150px;">
									<i class="fa-solid fa-tag"
										style="font-size: 50px; color: #ff6347;"></i>
								</div>
								<h3 style="font-size: 20px; color: #555;">Giảm Giá</h3>
								<p style="color: #777;">Ưu Đãi Mỗi Ngày</p>
							</div>

							<div class="tg-clientservices"
								style="display: flex; flex-direction: column; align-items: center; width: 220px; background-color: #fff; padding: 20px; border-radius: 10px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); transition: transform 0.3s;">
								<div class="tg-clientservices"
									style="width: 150px; height: 150px;">
									<i class="fa-solid fa-leaf"
										style="font-size: 50px; color: #32cd32;"></i>
								</div>
								<h3 style="font-size: 20px; color: #555;">Chất Lượng</h3>
								<p style="color: #777;">Chất Lượng Là Hàng Đầu</p>
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>

		<%@ include file="../client/layouts/footer.jsp"%>
	</div>

	<script type="text/javascript">
	document.querySelectorAll('.btn-add-to-cart').forEach(function(button) {
        button.addEventListener('click', function(event) {
        	console.log(event.currentTarget.getAttribute('data-book-id'));
        	console.log(event.currentTarget.getAttribute('data-book-id'));
        	 var bookId = event.currentTarget.getAttribute('data-book-id');
             var quantity = event.currentTarget.getAttribute('data-quantity');
             console.log("bookId:", bookId);
             console.log("quantity:", quantity);
            // Gửi GET request để thêm sách vào giỏ hàng
            fetch('/bookstore/cart/add?bookId=' + bookId + '&quantity=' + quantity, {
                method: 'GET', 
            })
            .then(response => response.text()) 
            .then(data => {
            	console.log("data: " + data);
                // Xử lý phản hồi sau khi thêm vào giỏ hàng
	              	 if (data == "Vui long dang nhap") {
	               		 toastr.error('Vui lòng đăng nhập để thêm sản phẩm!', 'Lỗi');
	              	 }
	              	 else if (data != "error" && data != "Vui long dang nhap") {
                	 var countBooksInCart = parseInt(data);  // Chuyển đổi dữ liệu trả về thành số
                	 fetch('/bookstore/index');
                     document.querySelector('#tg-minicart .tg-themebadge').textContent = countBooksInCart;
                	 toastr.success('Sản phẩm đã được thêm vào giỏ hàng!', 'Thành công');
                    
                } else {
                	toastr.error('Đã có lỗi xảy ra khi thêm sản phẩm vào giỏ!', 'Lỗi');
                }
            })
            .catch(error => {
                // Lỗi trong quá trình gửi request
                console.error('Có lỗi xảy ra:', error);
                toastr.error('Đã có lỗi xảy ra khi thêm sản phẩm vào giỏ!', 'Lỗi');
            });
        });
    });
	
	</script>
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
	<script type="text/javascript">
	$('#tg-discountbooksslider').owlCarousel({
	    loop: false,  // Không lặp lại
	    items: 4
	});

		const alertMessage = "${alertMessage}";
		const alertType = "${alertType}";
		toastr.options = {
			"closeButton" : true, // Cho phép nút đóng
			"debug" : false,
			"newestOnTop" : true,
			"progressBar" : true, // Hiển thị thanh tiến trình
			"positionClass" : "toast-top-right", // Vị trí hiển thị thông báo
			"preventDuplicates" : true,
			"onclick" : null,
			"showDuration" : "200", // Thời gian hiển thị
			"hideDuration" : "1000",
			"timeOut" : "5000", // Thời gian thông báo sẽ tự động ẩn
			"extendedTimeOut" : "1000", // Thời gian mở rộng nếu hover vào
			"showEasing" : "swing",
			"hideEasing" : "linear",
			"showMethod" : "fadeIn",
			"hideMethod" : "fadeOut"
		};
		if (alertMessage) {
			// Kiểm tra kiểu thông báo
			if (alertType === "success") {
				toastr.success(alertMessage, "Success");
			} else if (alertType === "error") {
				toastr.error(alertMessage, "Error");
			}
		}
		$(document).ready(function() {
		    $("#tg-discountbooksslider").owlCarousel({
		        loop: true,
		        margin: 20,
		        nav: true,  // Đảm bảo rằng navigation (prev/next) được bật
		        navText: [
		            '<i class="fa-solid fa-chevron-left"></i>', 
		            '<i class="fa-solid fa-chevron-right"></i>'
		        ],  // Thiết lập icon cho các nút Prev và Next
		        responsive: {
		            0: {
		                items: 1
		            },
		            600: {
		                items: 2
		            },
		            1000: {
		                items: 6
		            }
		        }
		    });
		});

	</script>
	<script>
        function toggleChatWindow() {
            var chatWindow = document.getElementById('chat-window');
            chatWindow.classList.toggle('show'); 
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