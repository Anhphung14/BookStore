<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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

<script src="https://kit.fontawesome.com/e70d1e2fed.js"
	crossorigin="anonymous"></script>

<!-- Modernizr JS -->
<script
	src="resources/assets/js/client/vendor/modernizr-2.8.3-respond-1.4.2.min.js"></script>
</head>

<body>

	<div id="tg-wrapper" class="tg-wrapper tg-haslayout">
		<!--************************************
				Header Start
		*************************************-->
		<%@ include file="../../client/layouts/header.jsp"%>
		<!--************************************
				Header End
		*************************************-->
		<!--************************************
				Inner Banner Start
		*************************************-->
		<div class="tg-innerbanner tg-haslayout tg-parallax tg-bginnerbanner"
			data-z-index="-100" data-appear-top-offset="600"
			data-parallax="scroll"
			data-image-src="images/parallax/bgparallax-07.jpg">
			<div class="container">
				<div class="row">
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
						<div class="tg-innerbannercontent">
							<h1>SÁCH THEO DANH MỤC</h1>
							<ol class="tg-breadcrumb">
								<li><a href="javascript:void(0);">home</a></li>
								<li class="tg-active">Categories</li>
								<li class="tg-active">${danhMuc.name }</li>
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
						<div id="tg-twocolumns" class="tg-twocolumns">
							<div class="col-xs-12 col-sm-8 col-md-8 col-lg-9 pull-right">
								<div id="tg-content" class="tg-content">
									<div class="tg-products">
										<div class="tg-productgrid">
											<div class="tg-refinesearch">
												<form class="tg-formtheme tg-formsortshoitems">
													<fieldset>
														<div class="form-group">
															<label>sort by:</label> <span class="tg-select"> <select
																name="sortby" onchange="handleSortChange()">
																	<option value="newest"
																		${sortBy.equals('newest') ? 'selected="selected"' : ''}>Mới
																		nhất</option>
																	<option value="oldest"
																		${sortBy.equals('oldest') ? 'selected="selected"' : ''}>Cũ
																		nhất</option>
																	<option value="priceAsc"
																		${sortBy.equals('priceAsc') ? 'selected="selected"' : ''}>Giá:
																		Tăng dần</option>
																	<option value="priceDesc"
																		${sortBy.equals('priceDesc') ? 'selected="selected"' : ''}>Giá:
																		Giảm dần</option>
																	<option value="nameAsc"
																		${sortBy.equals('nameAsc') ? 'selected="selected"' : ''}>Tên:
																		A - Z</option>
																	<option value="nameDesc"
																		${sortBy.equals('nameDesc') ? 'selected="selected"' : ''}>Tên:
																		Z - A</option>
															</select>

															</span>
														</div>
														<div class="form-group">
															<label>Show:</label> <span class="tg-select"> <select
																id="pageSizeSelect" name="pageSizeValue"
																onchange="handlePageSizeChange()">
																	<option value="16"
																		${pageSize == 16 ? 'selected="selected"' : ''}>16</option>
																	<option value="32"
																		${pageSize == 32 ? 'selected="selected"' : ''}>32</option>
																	<option value="48"
																		${pageSize == 48 ? 'selected="selected"' : ''}>48</option>
															</select>
															</span>
														</div>
													</fieldset>
												</form>
											</div>
											<c:forEach var="book" items="${bookList}">
												<div class="col-xs-6 col-sm-6 col-md-4 col-lg-3">
													<div class="tg-postbook">
														<div class="tg-bookimg">
															<div class="tg-frontcover">
																<img src="${book.thumbnail }" alt="image description">
															</div>
															<div class="tg-backcover">
																<img src="${book.thumbnail }" alt="image description">
															</div>
														</div>
														<div class="tg-postbookcontent">
															<ul class="tg-bookscategories">
																<li><a href="javascript:void(0);">${book.subcategoriesEntity.name }</a></li>
															</ul>
															<c:if
																test="${bookDiscounts[book.id] != 0.0 and bookDiscounts[book.id] != null}">
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
																<a href="productdetail/${book.id}.htm"> <c:choose>
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
																class="fa fa-shopping-basket"
																style="padding-left: 10px;"></i> <em>Thêm vào giỏ
																	hàng</em>
															</a>
														</div>
													</div>
												</div>
											</c:forEach>
										</div>

										<!-- Phân trang -->
										<nav aria-label="Page navigation example">
											<ul class="pagination">
												<!-- Nút Previous -->
												<c:choose>
													<c:when test="${currentPage > 1}">
														<li class="page-item"><a class="page-link"
															href="categories/${danhMuc.id}.htm?page=${currentPage - 1}"
															aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
														</a></li>
													</c:when>
													<c:otherwise>
														<li class="page-item disabled"><a class="page-link"
															aria-disabled="true" aria-label="Previous"> <span
																aria-hidden="true">&laquo;</span>
														</a></li>
													</c:otherwise>
												</c:choose>

												<!-- Các trang -->
												<c:forEach var="page" begin="1" end="${totalPages}">
													<li
														class="page-item ${page == currentPage ? 'active' : ''}">
														<a class="page-link"
														href="categories/${danhMuc.id}.htm?page=${page}">${page}</a>
													</li>
												</c:forEach>

												<!-- Nút Next -->
												<c:choose>
													<c:when test="${currentPage < totalPages}">
														<li class="page-item"><a class="page-link"
															href="categories/${danhMuc.id}.htm?page=${currentPage + 1}"
															aria-label="Next"> <span aria-hidden="true">&raquo;</span>
														</a></li>
													</c:when>
													<c:otherwise>
														<li class="page-item disabled"><a class="page-link"
															aria-label="Next"> <span aria-hidden="true">&raquo;</span>
														</a></li>
													</c:otherwise>
												</c:choose>
											</ul>
										</nav>
									</div>
								</div>
							</div>

							<div class="col-xs-12 col-sm-4 col-md-4 col-lg-3 pull-left">
								<aside id="tg-sidebar" class="tg-sidebar">
									<div class="tg-widget tg-catagories">
										<div class="tg-widgettitle">
											<h3>Categories</h3>
										</div>
										<div class="tg-widgetcontent">
											<ul>
												<li><a
													href="http://localhost:8080/bookstore/allProduct.htm"><span>Tất
															cả sách</span><em>${countAllBooks }</em></a></li>
												<c:forEach var="item" items="${countBookEachCategory }">
													<li><a
														href="http://localhost:8080/bookstore/categories/${item[3]}.htm"><span>${item[0]}</span><em>${item[2] }</em></a></li>

												</c:forEach>
											</ul>
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
		<%@ include file="../../client/layouts/footer.jsp"%>
		<!--************************************
				Footer End
		*************************************-->
	</div>
	<!--************************************
			Wrapper End
	*************************************-->
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
	    function handlePageSizeChange() {
	        const pageSizeSelect = document.getElementById("pageSizeSelect");
	        const pageSizeValue = pageSizeSelect.options[pageSizeSelect.selectedIndex].value; // Lấy giá trị chọn
	        console.log("pageSizeValue: ", pageSizeValue);
	
	        const currentUrl = new URL(window.location.href);
	        const searchQuery = currentUrl.searchParams.get("q") || ""; // Lấy giá trị `q`
	        const currentPage = 1; // Reset về trang đầu khi thay đổi pageSize
	
	        // Lấy thông tin `danhMuc` từ URL
	        const pathParts = window.location.pathname.split("/");
	        const danhMuc = pathParts[pathParts.length - 1].split(".")[0] || ""; // Lấy danhMục[4] (loại bỏ phần `.htm`)
	
	        // Kiểm tra và tạo URL với giá trị pageSize
	        if (pageSizeValue && pageSizeValue.trim() !== "") {
	            const url = "http://localhost:8080/bookstore/categories/"
	                + danhMuc + ".htm?" + "&page="
	                + currentPage + "&pageSize="
	                + pageSizeValue;
	            console.log("Navigating to URL: ", url);
	            window.location.href = url;
	        } else {
	            console.error("PageSize value is invalid:", pageSizeValue);
	        }
	    }
	    
	    function handleSortChange() {
	        const sortSelect = document.querySelector('select[name="sortby"]'); // Chọn select box sort
	        const sortByValue = sortSelect.options[sortSelect.selectedIndex].value; // Lấy giá trị lựa chọn

	        console.log("Sắp xếp theo: ", sortByValue);

	        const currentUrl = new URL(window.location.href); // Lấy URL hiện tại
	        const searchQuery = currentUrl.searchParams.get("q") || ""; // Lấy từ khóa tìm kiếm (nếu có)
	        const pageSize = currentUrl.searchParams.get("pageSize") || "10"; // Lấy số lượng sản phẩm mỗi trang (mặc định là 10)

	        const currentPage = currentUrl.searchParams.get("page") || "1"; // Lấy số trang hiện tại (mặc định là trang 1)

	        // Lấy thông tin danh mục từ URL (nếu có)
	        const pathParts = window.location.pathname.split("/");
	        const danhMuc2 = pathParts[pathParts.length - 2] || ""; // Lấy danh mục 2
	        const danhMuc3 = pathParts[pathParts.length - 1].split(".")[0] || ""; // Lấy danh mục 3 (loại bỏ phần mở rộng)

	        // Tạo URL mới với các tham số đã chọn
	        const newUrl = new URL(window.location.origin + window.location.pathname);
	        newUrl.searchParams.set("q", searchQuery);
	        newUrl.searchParams.set("sortBy", sortByValue); // Thêm giá trị sort vào URL
	        newUrl.searchParams.set("pageSize", pageSize);
	        newUrl.searchParams.set("page", currentPage); // Đảm bảo vẫn giữ số trang hiện tại

	        // Chuyển hướng đến URL mới để tải lại dữ liệu đã sắp xếp
	        window.location.href = newUrl.href;
	    }
	    
	    document.querySelectorAll('.btn-add-to-cart').forEach(function(button) {
	        button.addEventListener('click', function(event) {
	        	console.log(event.currentTarget.getAttribute('data-book-id'));
	        	console.log(event.currentTarget.getAttribute('data-book-id'));
	        	 var bookId = event.currentTarget.getAttribute('data-book-id');
	             var quantity = event.currentTarget.getAttribute('data-quantity');
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