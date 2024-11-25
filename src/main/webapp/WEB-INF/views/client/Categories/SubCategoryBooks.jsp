<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
    <link rel="apple-touch-icon" href="${pageContext.servletContext.contextPath}/resources/images/client/apple-touch-icon.png">

    <!-- External CSS Files -->
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
    
    <!-- Modernizr JS -->
    <script src="resources/assets/js/client/vendor/modernizr-2.8.3-respond-1.4.2.min.js"></script>
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
		<div class="tg-innerbanner tg-haslayout tg-parallax tg-bginnerbanner" data-z-index="-100" data-appear-top-offset="600" data-parallax="scroll" data-image-src="images/parallax/bgparallax-07.jpg">
			<div class="container">
				<div class="row">
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
						<div class="tg-innerbannercontent">
							<h1>SÁCH THEO DANH MỤC</h1>
							<ol class="tg-breadcrumb">
								<li><a href="javascript:void(0);">home</a></li>
								<li class="tg-active">Categories</li>
								<li class="tg-active">${danhMuc[0] }</li>
								<li class="tg-active">${danhMuc[1]  }</li>
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
										<div class="tg-sectionhead">
											<h2><span>People’s Choice</span>Bestselling Books</h2>
										</div>
										<div class="tg-featurebook alert" role="alert">
											<button type="button" class="close" data-dismiss="alert" aria-label="Close">
												<span aria-hidden="true">&times;</span>
											</button>
											<div class="tg-featureditm">
												<div class="row">
													<div class="col-xs-12 col-sm-12 col-md-4 col-lg-4 hidden-sm hidden-xs">
														<figure><img src="${pageContext.servletContext.contextPath}/resources/images/client/img-04.png" alt="image description"></figure>
													</div>
													<div class="col-xs-12 col-sm-12 col-md-8 col-lg-8">
														<div class="tg-featureditmcontent">
															<div class="tg-themetagbox"><span class="tg-themetag">featured</span></div>
															<div class="tg-booktitle">
																<h3><a href="javascript:void(0);">Things To Know About Green Flat Design</a></h3>
															</div>
															<span class="tg-bookwriter">By: <a href="javascript:void(0);">Farrah Whisenhunt</a></span>
															<span class="tg-stars"><span></span></span>
															<div class="tg-priceandbtn">
																<span class="tg-bookprice">
																	<ins>$23.18</ins>
																	<del>$30.20</del>
																</span>
																<a class="tg-btn tg-btnstyletwo tg-active" href="javascript:void(0);">
																	<i class="fa fa-shopping-basket"></i>
																	<em>Add To Basket</em>
																</a>
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>
										<div class="tg-productgrid">
											<div class="tg-refinesearch">
												<span>showing 1 to 8 of 20 total</span>
												<form class="tg-formtheme tg-formsortshoitems">
													<fieldset>
														<div class="form-group">
															<label>sort by:</label>
															<span class="tg-select">
																<select>
																	<option>Mới nhất</option>
																	<option>Giá: Tăng dần</option>
																	<option>Giá: Giảm dần</option>
																	<option>Tên: A - Z</option>
																	<option>Tên: Z - A</option>
																	<option>Mới nhất</option>
																	<option>Cũ nhất</option>
																	<option>Bán chạy nhất</option>
																</select>
															</span>
														</div>
														<div class="form-group">
														    <label>Show:</label>
														    <span class="tg-select">
														        <select id="pageSizeSelect" name="pageSizeValue" onchange="handlePageSizeChange()">
														            <option value="1" ${pageSize == 1 ? 'selected="selected"' : ''}>1</option>
														            <option value="2" ${pageSize == 2 ? 'selected="selected"' : ''}>2</option>
														            <option value="3" ${pageSize == 3 ? 'selected="selected"' : ''}>3</option>
														        </select>
														    </span>
														</div>
													</fieldset>
												</form>
											</div>
											<c:forEach var="book" items="${bookList}">
												<div class="col-xs-6 col-sm-6 col-md-4 col-lg-3">
													<div class="tg-postbook">
														<figure class="tg-featureimg">
															<div class="tg-bookimg">
																<div class="tg-frontcover"><img src="${pageContext.servletContext.contextPath}/resources/images/client/books/${book.images}/front.jpg" alt="image description"></div>
																<div class="tg-backcover"><img src="${pageContext.servletContext.contextPath}/resources/images/client/books/${book.images}/front.jpg" alt="image description"></div>
															</div>
															<a class="tg-btnaddtowishlist" href="javascript:void(0);">
																<i class="fa-solid fa-heart"></i>
																<span>add to wishlist</span>
															</a>
														</figure>
														<div class="tg-postbookcontent">
																<ul class="tg-bookscategories">
																	<li><a href="javascript:void(0);">${book.subcategory.name }</a></li>
																</ul>
																<div class="tg-themetagbox"><span class="tg-themetag">sale</span></div>
																<div class="tg-booktitle">
																	<h3><a href="#" data-toggle="tooltip" title="${book.title}">${book.title}</a></h3>
																</div>
																<span class="tg-bookwriter">By: <a href="javascript:void(0);">${book.author }</a></span>
																<span class="tg-stars"><span></span></span>
																<span class="tg-bookprice">
																	<ins>chưa biết</ins>
																	<del><f:formatNumber value="${book.price}" type="currency"/></del>
																</span>
																<a class="tg-btn tg-btnstyletwo" href="javascript:void(0);">
																	<i class="fa fa-shopping-basket"></i>
																	<em>Thêm vào giỏ</em>
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
										                <li class="page-item">
										                    <a class="page-link" href="categories/${danhMuc[2]}/${danhMuc[3]}.htm?page=${currentPage - 1}" aria-label="Previous">
										                        <span aria-hidden="true">&laquo;</span>
										                    </a>
										                </li>
										            </c:when>
										            <c:otherwise>
										                <li class="page-item disabled">
										                    <a class="page-link"  aria-disabled="true" aria-label="Previous">
										                        <span aria-hidden="true">&laquo;</span>
										                    </a>
										                </li>
										            </c:otherwise>
										        </c:choose>
										
										        <!-- Các trang -->
										        <c:forEach var="page" begin="1" end="${totalPages}">
										            <li class="page-item ${page == currentPage ? 'active' : ''}">
										                <a class="page-link" href="categories/${danhMuc[2]}/${danhMuc[3]}.htm?page=${page}">${page}</a>
										            </li>
										        </c:forEach>
										
										        <!-- Nút Next -->
										        <c:choose>
										            <c:when test="${currentPage < totalPages}">
										                <li class="page-item">
										                    <a class="page-link" href="categories/${danhMuc[2]}/${danhMuc[3]}.htm?page=${currentPage + 1}" aria-label="Next">
										                        <span aria-hidden="true">&raquo;</span>
										                    </a>
										                </li>
										            </c:when>
										            <c:otherwise>
										                <li class="page-item disabled">
										                    <a class="page-link" aria-label="Next">
										                        <span aria-hidden="true">&raquo;</span>
										                    </a>
										                </li>
										            </c:otherwise>
										        </c:choose>
										    </ul>
										</nav>
									</div>
								</div>
							</div>
							
							<div class="col-xs-12 col-sm-4 col-md-4 col-lg-3 pull-left">
								<aside id="tg-sidebar" class="tg-sidebar">
									<div class="tg-widget tg-widgetsearch">
										<form class="tg-formtheme tg-formsearch">
											<div class="form-group">
												<button type="submit"><i class="fa-solid fa-magnifying-glass"></i></button>
												<input type="search" name="search" class="form-group" placeholder="Search by title, author, key...">
											</div>
										</form>
									</div>
									<div class="tg-widget tg-catagories">
										<div class="tg-widgettitle">
											<h3>Categories</h3>
										</div>
										<div class="tg-widgetcontent">
											<ul>
												<c:forEach var="item" items="${countBookEachCategory }">
													<li><a href="http://localhost:8080/bookstore/categories/${item[3]}.htm"><span>${item[0]}</span><em>${item[2] }</em></a></li>
												
												</c:forEach>
											</ul>
										</div>
									</div>
									<div class="tg-widget tg-widgettrending">
										<div class="tg-widgettitle">
											<h3>Trending Products</h3>
										</div>
										<div class="tg-widgetcontent">
											<ul>
												<li>
													<article class="tg-post">
														<figure><a href="javascript:void(0);"><img src="${pageContext.servletContext.contextPath}/resources/images/client/products/img-04.jpg" alt="image description"></a></figure>
														<div class="tg-postcontent">
															<div class="tg-posttitle">
																<h3><a href="javascript:void(0);">Where The Wild Things Are</a></h3>
															</div>
															<span class="tg-bookwriter">By: <a href="javascript:void(0);">Kathrine Culbertson</a></span>
														</div>
													</article>
												</li>
												<li>
													<article class="tg-post">
														<figure><a href="javascript:void(0);"><img src="${pageContext.servletContext.contextPath}/resources/images/client/products/img-05.jpg" alt="image description"></a></figure>
														<div class="tg-postcontent">
															<div class="tg-posttitle">
																<h3><a href="javascript:void(0);">Where The Wild Things Are</a></h3>
															</div>
															<span class="tg-bookwriter">By: <a href="javascript:void(0);">Kathrine Culbertson</a></span>
														</div>
													</article>
												</li>
												<li>
													<article class="tg-post">
														<figure><a href="javascript:void(0);"><img src="${pageContext.servletContext.contextPath}/resources/images/client/products/img-06.jpg" alt="image description"></a></figure>
														<div class="tg-postcontent">
															<div class="tg-posttitle">
																<h3><a href="javascript:void(0);">Where The Wild Things Are</a></h3>
															</div>
															<span class="tg-bookwriter">By: <a href="javascript:void(0);">Kathrine Culbertson</a></span>
														</div>
													</article>
												</li>
												<li>
													<article class="tg-post">
														<figure><a href="javascript:void(0);"><img src="${pageContext.servletContext.contextPath}/resources/images/client/products/img-07.jpg" alt="image description"></a></figure>
														<div class="tg-postcontent">
															<div class="tg-posttitle">
																<h3><a href="javascript:void(0);">Where The Wild Things Are</a></h3>
															</div>
															<span class="tg-bookwriter">By: <a href="javascript:void(0);">Kathrine Culbertson</a></span>
														</div>
													</article>
												</li>
											</ul>
										</div>
									</div>
									<div class="tg-widget tg-widgetinstagram">
										<div class="tg-widgettitle">
											<h3>Instagram</h3>
										</div>
										<div class="tg-widgetcontent">
											<ul>
												<li>
													<figure>
														<img src="${pageContext.servletContext.contextPath}/resources/images/client/instagram/img-01.jpg" alt="image description">
														<figcaption><a href="javascript:void(0);"><i class="fa-solid fa-heart"></i><em>50,134</em></a></figcaption>
													</figure>
												</li>
												<li>
													<figure>
														<img src="${pageContext.servletContext.contextPath}/resources/images/client/instagram/img-02.jpg" alt="image description">
														<figcaption><a href="javascript:void(0);"><i class="fa-solid fa-heart"></i><em>50,134</em></a></figcaption>
													</figure>
												</li>
												<li>
													<figure>
														<img src="${pageContext.servletContext.contextPath}/resources/images/client/instagram/img-03.jpg" alt="image description">
														<figcaption><a href="javascript:void(0);"><i class="fa-solid fa-heart"></i><em>50,134</em></a></figcaption>
													</figure>
												</li>
												<li>
													<figure>
														<img src="${pageContext.servletContext.contextPath}/resources/images/client/instagram/img-04.jpg" alt="image description">
														<figcaption><a href="javascript:void(0);"><i class="fa-solid fa-heart"></i><em>50,134</em></a></figcaption>
													</figure>
												</li>
												<li>
													<figure>
														<img src="${pageContext.servletContext.contextPath}/resources/images/client/instagram/img-05.jpg" alt="image description">
														<figcaption><a href="javascript:void(0);"><i class="fa-solid fa-heart"></i><em>50,134</em></a></figcaption>
													</figure>
												</li>
												<li>
													<figure>
														<img src="${pageContext.servletContext.contextPath}/resources/images/client/instagram/img-06.jpg" alt="image description">
														<figcaption><a href="javascript:void(0);"><i class="fa-solid fa-heart"></i><em>50,134</em></a></figcaption>
													</figure>
												</li>
												<li>
													<figure>
														<img src="${pageContext.servletContext.contextPath}/resources/images/client/instagram/img-07.jpg" alt="image description">
														<figcaption><a href="javascript:void(0);"><i class="fa-solid fa-heart"></i><em>50,134</em></a></figcaption>
													</figure>
												</li>
												<li>
													<figure>
														<img src="${pageContext.servletContext.contextPath}/resources/images/client/instagram/img-08.jpg" alt="image description">
														<figcaption><a href="javascript:void(0);"><i class="fa-solid fa-heart"></i><em>50,134</em></a></figcaption>
													</figure>
												</li>
												<li>
													<figure>
														<img src="${pageContext.servletContext.contextPath}/resources/images/client/instagram/img-09.jpg" alt="image description">
														<figcaption><a href="javascript:void(0);"><i class="fa-solid fa-heart"></i><em>50,134</em></a></figcaption>
													</figure>
												</li>
											</ul>
										</div>
									</div>
									<div class="tg-widget tg-widgetblogers">
										<div class="tg-widgettitle">
											<h3>Top Bloogers</h3>
										</div>
										<div class="tg-widgetcontent">
											<ul>
												<li>
													<div class="tg-author">
														<figure><a href="javascript:void(0);"><img src="${pageContext.servletContext.contextPath}/resources/images/client/author/imag-03.jpg" alt="image description"></a></figure>
														<div class="tg-authorcontent">
															<h2><a href="javascript:void(0);">Jude Morphew</a></h2>
															<span>21,658 Published Books</span>
														</div>
													</div>
												</li>
												<li>
													<div class="tg-author">
														<figure><a href="javascript:void(0);"><img src="${pageContext.servletContext.contextPath}/resources/images/client/author/imag-04.jpg" alt="image description"></a></figure>
														<div class="tg-authorcontent">
															<h2><a href="javascript:void(0);">Jude Morphew</a></h2>
															<span>21,658 Published Books</span>
														</div>
													</div>
												</li>
												<li>
													<div class="tg-author">
														<figure><a href="javascript:void(0);"><img src="${pageContext.servletContext.contextPath}/resources/images/client/author/imag-05.jpg" alt="image description"></a></figure>
														<div class="tg-authorcontent">
															<h2><a href="javascript:void(0);">Jude Morphew</a></h2>
															<span>21,658 Published Books</span>
														</div>
													</div>
												</li>
												<li>
													<div class="tg-author">
														<figure><a href="javascript:void(0);"><img src="${pageContext.servletContext.contextPath}/resources/images/client/author/imag-06.jpg" alt="image description"></a></figure>
														<div class="tg-authorcontent">
															<h2><a href="javascript:void(0);">Jude Morphew</a></h2>
															<span>21,658 Published Books</span>
														</div>
													</div>
												</li>
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
	        const danhMuc2 = pathParts[pathParts.length - 2] || ""; // Lấy danhMục[2]
	        const danhMuc3 = pathParts[pathParts.length - 1].split(".")[0] || ""; // Lấy danhMục[3] (loại bỏ phần `.htm`)
	
	        // Kiểm tra và tạo URL với giá trị pageSize
	        if (pageSizeValue && pageSizeValue.trim() !== "") {
	            const url = "http://localhost:8080/bookstore/categories/"
	                + danhMuc2 + "/"
	                + danhMuc3 + ".htm?" + "&page="
	                + currentPage + "&pageSize="
	                + pageSizeValue;
	            console.log("Navigating to URL: ", url);
	            window.location.href = url;
	        } else {
	            console.error("PageSize value is invalid:", pageSizeValue);
	        }
	    }
	</script>
	
</body>



</html>