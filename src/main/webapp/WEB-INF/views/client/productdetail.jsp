<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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


<style type="text/css">
/* Thay đổi màu nền cho toastr success */
.toast-success {
    background-color: #28a745 !important; /* Màu xanh lá */
    color: #fff !important; /* Màu chữ trắng */
}

/* Thay đổi màu nền cho toastr error */
.toast-error {
    background-color: #dc3545 !important; /* Màu đỏ */
    color: #fff !important; /* Màu chữ trắng */
}

/* Thay đổi màu nền cho toastr warning */
.toast-warning {
    background-color: #ffc107 !important; /* Màu vàng */
    color: #000 !important; /* Màu chữ đen */
}

/* Thay đổi màu nền cho toastr info */
.toast-info {
    background-color: #17a2b8 !important; /* Màu xanh lam */
    color: #fff !important; /* Màu chữ trắng */
}

/* Tùy chỉnh chung cho toastr */
.toast {
    border-radius: 5px; /* Bo góc */
    font-size: 14px; /* Kích thước chữ */
}

</style>
<script src="https://kit.fontawesome.com/e70d1e2fed.js"
	crossorigin="anonymous"></script>

<!-- Modernizr JS -->
<script
	src="resources/assets/js/client/vendor/modernizr-2.8.3-respond-1.4.2.min.js"></script>
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/css/toastr.min.css"
	rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/js/toastr.min.js"></script>
</head>
<body>

	<div id="tg-wrapper" class="tg-wrapper tg-haslayout">
		<!--************************************
				Header Start
		*************************************-->
		<%@ include file="../client/layouts/header.jsp"%>
		<!--************************************
				Header End
		*************************************-->
		<!--************************************
				Inner Banner Start
		*************************************-->
		<div class="tg-innerbanner tg-haslayout tg-parallax tg-bginnerbanner"
			data-z-index="-100" data-appear-top-offset="600"
			data-parallax="scroll" style="height: 100px;">
			<div class="container">
				<div class="row">
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
						<div class="tg-innerbannercontent" style="padding-bottom: 20px;">
							<h1>Tất Cả Sản Phẩm</h1>
							<ol class="tg-breadcrumb">
								<li><a href="javascript:void(0);">Trang chủ</a></li>
								<li><a href="javascript:void(0);">Sản phẩm</a></li>
								<li class="tg-active">${book.title}</li>
							</ol>

						</div>
					</div>
				</div>
			</div>
		</div>
		<!--************************************
				Inner Banner End
		*************************************-->
		<!--************************************
				Main Start
		*************************************-->
		<main id="tg-main" class="tg-main tg-haslayout">
			<!--************************************
					News Grid Start
			*************************************-->
			<div class="tg-sectionspace tg-haslayout">
				<div class="container">
					<div class="row">
						<div id="tg-twocolumns" class="tg-twocolumns"
							style="padding-bottom: 40px;">
							<div class="col-xs-12 col-sm-8 col-md-8 col-lg-9 pull-right">
								<div id="tg-content" class="tg-content">
									<div class="tg-productdetail">
										<div class="row">
											<div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">
												<div class="tg-postbook">
													<img src="${book.thumbnail}" alt="image description">
													<div class="tg-postbookcontent">
														<c:choose>
															<c:when test="${discount > 0}">
																<!-- Hiển thị giá sau khi giảm giá -->
																<span class="tg-bookprice"> <ins>
																		<f:formatNumber
																			value="${book.price - book.price * discount / 100}"
																			type="currency" maxFractionDigits="0"
																			currencySymbol="₫" />
																	</ins> <del>
																		<f:formatNumber value="${book.price}" type="currency"
																			maxFractionDigits="0" currencySymbol="₫" />
																	</del>
																</span>
																<span class="tg-bookwriter">Bạn tiết kiệm được <ins>
																		<f:formatNumber value="${book.price * discount / 100}"
																			type="currency" maxFractionDigits="0"
																			currencySymbol="₫" />
																	</ins></span>

															</c:when>
															<c:otherwise>
																<!-- Hiển thị giá gốc khi không có giảm giá -->
																<span class="tg-bookprice"> <ins>
																		<f:formatNumber value="${book.price}" type="currency"
																			maxFractionDigits="0" currencySymbol="₫" />
																	</ins>
																</span>
															</c:otherwise>
														</c:choose>
														<ul class="tg-delevrystock">
															<li><i class="fa-solid fa-rocket"></i><span>Giao
																	hàng miễn phí toàn cầu</span></li>
															<li><i class="fa-solid fa-store"></i><span>Kho
																	hàng: <em>${book.inventoryEntity.stock_quantity}</em>
															</span></li>

														</ul>
														<div class="tg-quantityholder">
															<em class="minus">-</em> <input type="text"
																class="result" value="1" id="quantity1" name="quantity">
															<em class="plus">+</em>
														</div>
														<a class="tg-btn tg-active tg-btn-lg btn-add-to-cart"
															data-book-id="${book.id}" data-quantity="1"
															style="text-decoration: none;">Thêm vào giỏ hàng</a>

													</div>
												</div>
											</div>
											<div class="col-xs-12 col-sm-12 col-md-8 col-lg-8">
												<div class="tg-productcontent">
													<ul class="tg-bookscategories">
														<li><a style="text-decoration: none;"
															href="javascript:void(0);">${book.subcategoriesEntity.name }</a></li>
													</ul>
													<c:if test="${discount > 0}">
														<div class="tg-themetagbox">
															<span class="tg-themetag">Giảm giá <f:formatNumber
																	value="${discount}" type="number" pattern="##" />%
															</span>
														</div>

													</c:if>
													<div class="tg-booktitle">
														<h3>${book.title }</h3>
													</div>
													<span class="tg-bookwriter">Tác giả: <a
														style="text-decoration: none;" href="javascript:void(0);">${book.author }</a></span>

													<div class="tg-description"></div>
													<div class="tg-sectionhead">
														<h2>Product Details</h2>
													</div>
													<ul class="tg-productinfo">
														<li><span>Nhà cung cấp:</span><span>${book.supplier.name }</span></li>
														<li><span>Tác giả:</span><span>${book.author }</span></li>
														<li><span>Năm XB:</span><span>${book.publication_year }</span></li>
														<li><span>Số trang:</span><span>${book.page_count }</span></li>
														<li><span>Ngôn ngữ:</span><span>${book.language }</span></li>

													</ul>
												</div>
											</div>
											<div class="tg-productdescription">
												<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12"
													style="padding-top: 15px;">
													<div class="tg-sectionhead">
														<h2>Mô tả sản phẩm</h2>
													</div>
													<ul class="tg-themetabs" role="tablist">
														<li role="presentation" class="active"><a
															style="text-decoration: none;" href="#description"
															data-toggle="tab">Mô tả</a></li>
														<li role="presentation"><a
															style="text-decoration: none;" href="#review"
															data-toggle="tab">Đánh giá</a></li>
													</ul>
													<div class="tg-tab-content tab-content">
														<div role="tabpanel" class="tg-tab-pane tab-pane active"
															id="description">
															<div class="tg-description">
																<c:set var="description" value="${book.description}" />
																<c:choose>
																	<c:when
																		test="${description != null && fn:length(description) > 100}">
																		<p id="book-description" style="font-size: 1.4rem;">${description.substring(0, 100)}...</p>
																		<a id="more_less" href="javascript:void(0);"
																			onclick="showMore()" style="font-size: 1.4rem;">More</a>
																	</c:when>
																	<c:otherwise>
																		<p style="font-size: 1.4rem;">${description != null ? description : 'Không có mô tả'}</p>
																	</c:otherwise>
																</c:choose>
															</div>
														</div>
														<div role="tabpanel" class="tg-tab-pane tab-pane"
															id="review">
															<jsp:include page="/productdetail/${book.id}/reviews.htm" />
														</div>
													</div>
												</div>
											</div>

											<div class="tg-relatedproducts">
												<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
													<div class="tg-sectionhead">
														<h2>Sản phẩm bạn có thể thích</h2>
														<a style="text-decoration: none;" class="tg-btn"
															href="allProduct.htm">Xem tất cả</a>
													</div>
												</div>
												<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
													<div id="tg-relatedproductslider"
														class="tg-relatedproductslider tg-relatedbooks owl-carousel">
														<c:forEach var="book" items="${books_category}"
															varStatus="status">
															<div class="item">
																<div class="tg-postbook">
																	<div class="tg-bookimg">
																		<div class="tg-frontcover">
																			<img src="${book.thumbnail}" alt="${book.title}">
																		</div>
																		<div class="tg-backcover">
																			<img src="${book.thumbnail}" alt="${book.title}">
																		</div>
																	</div>

																	<div class="tg-postbookcontent">
																		<ul class="tg-bookscategories">
																			<li style="padding-top: 10px"><a style="text-decoration: none;"
																				href="javascript:void(0);">${book.subcategoriesEntity.name}</a></li>
																		</ul>
																		<c:if test="${discount > 0}">
																			<div class="tg-themetagbox">
																				<span class="tg-themetag">Giảm giá <f:formatNumber
																						value="${discount}" type="number" pattern="##" />%
																				</span>
																			</div>

																		</c:if>
																		<div class="tg-booktitle">
																			<h3>
																				<a style="text-decoration: none;"
																					href="/productdetail/${book.id}.htm">${book.title}</a>
																			</h3>
																		</div>
																		<span class="tg-bookwriter">Tác giả: <a
																			style="text-decoration: none;"
																			href="javascript:void(0);">${book.author}</a></span>
																		<c:choose>
																			<c:when test="${discounts[status.index] > 0}">
																				<span class="tg-bookprice"> <ins>
																						<f:formatNumber
																							value="${book.price - book.price * discounts[status.index] / 100}"
																							type="currency" />
																					</ins> <del>
																						<f:formatNumber value="${book.price}"
																							type="currency" />
																					</del>
																				</span>
																			</c:when>
																			<c:otherwise>
																				<span class="tg-bookprice"> <ins>
																						<f:formatNumber value="${book.price}"
																							type="currency" />
																					</ins>
																				</span>
																			</c:otherwise>
																		</c:choose>
																		<a class="tg-btn tg-btnstyletwo btn-add-to-cart"
																			href="javascript:void(0);"> <i
																			class="fa fa-shopping-basket"></i> <em>Thêm vào
																				giỏ hàng</em>
																		</a>
																	</div>
																</div>
															</div>
														</c:forEach>

													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="col-xs-12 col-sm-4 col-md-4 col-lg-3 pull-left">
								<aside id="tg-sidebar" class="tg-sidebar">
									<div class="tg-widget tg-catagories">
										<div class="tg-widget tg-catagories">
											<div class="tg-widgettitle">
												<h3>Danh mục</h3>
											</div>
											<div class="tg-widgetcontent">
												<ul>
													<c:forEach var="item" items="${countBookEachCategory }">
														<li><a
															href="http://localhost:8080/bookstore/categories/${item[3]}.htm"><span>${item[0]}</span><em>${item[2] }</em></a></li>

													</c:forEach>
												</ul>
											</div>
										</div>
									</div>
								</aside>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!--************************************
					News Grid End
			*************************************-->
		</main>
		<!--************************************
				Main End
		*************************************-->
		<!--************************************
				Footer Start
		*************************************-->
		<%@ include file="../client/layouts/footer.jsp"%>
		<!--************************************
				Footer End
		*************************************-->
	</div>
<!-- 	<!--************************************ -->
	<script>
	var isFullDescription = false;  // Biến để theo dõi trạng thái của mô tả

	
	document.querySelectorAll('.btn-add-to-cart').forEach(function(button) {
        button.addEventListener('click', function(event) {
        	console.log(event.currentTarget.getAttribute('data-book-id'));
        	console.log(event.currentTarget.getAttribute('data-book-id'));
        	 var bookId = event.currentTarget.getAttribute('data-book-id');
        	 
			         	
//              var quantity = event.currentTarget.getAttribute('data-quantity');
             var quantity = document.querySelector('#quantity1').value;
             console.log("bookId:", bookId);
             console.log("quantity:", quantity);
            // Gửi GET request để thêm sách vào giỏ hàng
            fetch('/bookstore/cart/add.htm?bookId=' + bookId + '&quantity=' + quantity, {
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
                	 fetch('/bookstore/index.htm');
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
<!-- 			Wrapper End -->
<!-- 	************************************* -->
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
	<script>

	
	
	</script>
</body>
</html>