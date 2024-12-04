<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
		<%@ include file="../client/layouts/header.jsp"%>
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
							<h1>All Products</h1>
							<ol class="tg-breadcrumb">
								<li><a href="javascript:void(0);">home</a></li>
								<li><a href="javascript:void(0);">Products</a></li>
								<li class="tg-active">${book.title }</li>
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
									<div class="tg-productdetail">
										<div class="row">
											<div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">
												<div class="tg-postbook">
													<figure class="tg-featureimg"><img src="${book.thumbnail}" alt="image description"></figure>
													<div class="tg-postbookcontent">
														<c:choose>
														    <c:when test="${discount > 0}">
														        <!-- Hiển thị giá sau khi giảm giá -->
														        <span class="tg-bookprice">
														            <ins><f:formatNumber value="${book.price - book.price * discount / 100}" type="currency" maxFractionDigits="0" currencySymbol="₫"/> </ins>
														            <del><f:formatNumber value="${book.price}" type="currency" maxFractionDigits="0" currencySymbol="₫"/> </del>
														        </span>
														        <span class="tg-bookwriter">Bạn tiết kiệm được ${book.price * discount / 100}</span>
														    </c:when>
														    <c:otherwise>
														        <!-- Hiển thị giá gốc khi không có giảm giá -->
														        <span class="tg-bookprice">
														            <ins><f:formatNumber value="${book.price}" type="currency" maxFractionDigits="0" currencySymbol="₫"/> </ins>
														        </span>
														    </c:otherwise>
														</c:choose>
														<ul class="tg-delevrystock">
															<li><i class="fa-solid fa-rocket"></i><span>Free delivery worldwide</span></li>
															<li><i class="fa-regular fa-square-check"></i><span>Dispatch from the USA in 2 working days </span></li>
															<li><i class="fa-solid fa-store"></i><span>Status: <em>${stock_quantity}</em></span></li>
														</ul>
														<div class="tg-quantityholder">
															<em class="minus">-</em>
															<input type="text" class="result" value="0" id="quantity1" name="quantity">
															<em class="plus">+</em>
														</div>
														<a class="tg-btn tg-active tg-btn-lg" style="text-decoration: none;" href="javascript:void(0);">Add To Basket</a>
								
													</div>
												</div>
											</div>
											<div class="col-xs-12 col-sm-12 col-md-8 col-lg-8">
												<div class="tg-productcontent">
													<ul class="tg-bookscategories">
														<li><a style="text-decoration: none;" href="javascript:void(0);">${book.subcategoriesEntity.name }</a></li>
													</ul>
													<c:if test="${discount > 0}">
													    <div class="tg-themetagbox">
													        <span class="tg-themetag">sale</span>
													    </div>
													</c:if>
													<div class="tg-booktitle">
														<h3>${book.title }</h3>
													</div>
													<span class="tg-bookwriter">By: <a style="text-decoration: none;" href="javascript:void(0);">${book.author }</a></span>
													<span>
														<c:forEach begin="1" end="${ratingAVR}">
										                    ⭐
										                </c:forEach>
										                <c:forEach begin="${ratingAVR + 1}" end="5">
										                    ☆
										                </c:forEach>
													</span>
													
													<div class="tg-description">
														 
													</div>
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
												<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
													<div class="tg-sectionhead">
														<h2>Product Description</h2>
													</div>
													<ul class="tg-themetabs" role="tablist">
														<li role="presentation" class="active"><a style="text-decoration: none;" href="#description" data-toggle="tab">Description</a></li>
														<li role="presentation"><a style="text-decoration: none;" href="#review" data-toggle="tab">Reviews</a></li>
													</ul>
													<div class="tg-tab-content tab-content">
														<div role="tabpanel" class="tg-tab-pane tab-pane active" id="description">
															<div class="tg-description">
																<c:set var="description" value="${book.description}" />
																	<c:choose>
																	    <c:when test="${description != null && fn:length(description) > 100}">
																	        <p id="book-description" style="font-size: 1.4rem;">${description.substring(0, 100)}...</p>
																	        <a id="more_less" href="javascript:void(0);" onclick="showMore()" style="font-size: 1.4rem;">More</a>
																	    </c:when>
																	    <c:otherwise>
																	        <p style="font-size: 1.4rem;">${description != null ? description : 'Không có mô tả'}</p>
																	    </c:otherwise>
																	</c:choose>
															</div>
														</div>
														<div role="tabpanel" class="tg-tab-pane tab-pane" id="review">
															<jsp:include page="/productdetail/${book.id}/reviews.htm" />
														</div>
													</div>
												</div>
											</div>
											
											<div class="tg-relatedproducts">
												<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
													<div class="tg-sectionhead">
														<h2><span>Related Products</span>You May Also Like</h2>
														<a style="text-decoration: none;" class="tg-btn" href="javascript:void(0);">View All</a>
													</div>
												</div>
												<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
													<div id="tg-relatedproductslider" class="tg-relatedproductslider tg-relatedbooks owl-carousel">
														<c:forEach var="book" items="${books_category}" varStatus="status">
														    <div class="item">
														        <div class="tg-postbook">
														            <figure class="tg-featureimg">
														                <div class="tg-bookimg">
														                    <div class="tg-frontcover">
														                        <img src="${book.thumbnail}" alt="${book.title}">
														                    </div>
														                    <div class="tg-backcover">
														                        <img src="${book.thumbnail}" alt="${book.title}">
														                    </div>
														                </div>
														     
														            </figure>
														            <div class="tg-postbookcontent">
														                <ul class="tg-bookscategories">
														                        <li><a style="text-decoration: none;" href="javascript:void(0);">${book.subcategoriesEntity.name}</a></li>
														                </ul>
														                <c:if test="${discounts[status.index] > 0}">
																		    <div class="tg-themetagbox">
																		        <span class="tg-themetag">sale</span>
																		    </div>
																		</c:if>
														                <div class="tg-booktitle">
														                    <h3><a style="text-decoration: none;" href="/productdetail/${book.id}.htm">${book.title}</a></h3>
														                </div>
														                <span class="tg-bookwriter">By: <a style="text-decoration: none;" href="javascript:void(0);">${book.author}</a></span>
														                <c:choose>
																		    <c:when test="${discounts[status.index] > 0}">
																		        <!-- Hiển thị giá sau khi giảm giá -->
																		        <span class="tg-bookprice">
																		            <ins><f:formatNumber value="${book.price - book.price * discounts[status.index] / 100}" type="currency"/></ins>
																		            <del><f:formatNumber value="${book.price}" type="currency"/></del>
																		        </span>
																		    </c:when>
																		    <c:otherwise>
																		        <!-- Hiển thị giá gốc khi không có giảm giá -->
																		        <span class="tg-bookprice">
																		            <ins><f:formatNumber value="${book.price}" type="currency"/></ins>
																		        </span>
																		    </c:otherwise>
																		</c:choose>
														                <a class="tg-btn tg-btnstyletwo" href="javascript:void(0);">
														                    <i class="fa fa-shopping-basket"></i>
														                    <em>Add To Basket</em>
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
									<div class="tg-widget tg-widgetsearch">
										<form class="tg-formtheme tg-formsearch">
											<div class="form-group">
												<button type="submit"><i class="icon-magnifier"></i></button>
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
												<li><a href="javascript:void(0);"><span>Art &amp; Photography</span><em>28245</em></a></li>
												<li><a href="javascript:void(0);"><span>Biography</span><em>4856</em></a></li>
												<li><a href="javascript:void(0);"><span>Children’s Book</span><em>8654</em></a></li>
												<li><a href="javascript:void(0);"><span>Craft &amp; Hobbies</span><em>6247</em></a></li>
												<li><a href="javascript:void(0);"><span>Crime &amp; Thriller</span><em>888654</em></a></li>
												<li><a href="javascript:void(0);"><span>Fantasy &amp; Horror</span><em>873144</em></a></li>
												<li><a href="javascript:void(0);"><span>Fiction</span><em>18465</em></a></li>
												<li><a href="javascript:void(0);"><span>Fod &amp; Drink</span><em>3148</em></a></li>
												<li><a href="javascript:void(0);"><span>Graphic, Anime &amp; Manga</span><em>77531</em></a></li>
												<li><a href="javascript:void(0);"><span>Science Fiction</span><em>9247</em></a></li>
												<li><a href="javascript:void(0);"><span>View All</span></a></li>
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
														<figcaption><a href="javascript:void(0);"><i class="icon-heart"></i><em>50,134</em></a></figcaption>
													</figure>
												</li>
												<li>
													<figure>
														<img src="${pageContext.servletContext.contextPath}/resources/images/client/instagram/img-02.jpg" alt="image description">
														<figcaption><a href="javascript:void(0);"><i class="icon-heart"></i><em>50,134</em></a></figcaption>
													</figure>
												</li>
												<li>
													<figure>
														<img src="${pageContext.servletContext.contextPath}/resources/images/client/instagram/img-03.jpg" alt="image description">
														<figcaption><a href="javascript:void(0);"><i class="icon-heart"></i><em>50,134</em></a></figcaption>
													</figure>
												</li>
												<li>
													<figure>
														<img src="${pageContext.servletContext.contextPath}/resources/images/client/instagram/img-04.jpg" alt="image description">
														<figcaption><a href="javascript:void(0);"><i class="icon-heart"></i><em>50,134</em></a></figcaption>
													</figure>
												</li>
												<li>
													<figure>
														<img src="${pageContext.servletContext.contextPath}/resources/images/client/instagram/img-05.jpg" alt="image description">
														<figcaption><a href="javascript:void(0);"><i class="icon-heart"></i><em>50,134</em></a></figcaption>
													</figure>
												</li>
												<li>
													<figure>
														<img src="${pageContext.servletContext.contextPath}/resources/images/client/instagram/img-06.jpg" alt="image description">
														<figcaption><a href="javascript:void(0);"><i class="icon-heart"></i><em>50,134</em></a></figcaption>
													</figure>
												</li>
												<li>
													<figure>
														<img src="${pageContext.servletContext.contextPath}/resources/images/client/instagram/img-07.jpg" alt="image description">
														<figcaption><a href="javascript:void(0);"><i class="icon-heart"></i><em>50,134</em></a></figcaption>
													</figure>
												</li>
												<li>
													<figure>
														<img src="${pageContext.servletContext.contextPath}/resources/images/client/instagram/img-08.jpg" alt="image description">
														<figcaption><a href="javascript:void(0);"><i class="icon-heart"></i><em>50,134</em></a></figcaption>
													</figure>
												</li>
												<li>
													<figure>
														<img src="${pageContext.servletContext.contextPath}/resources/images/client/instagram/img-09.jpg" alt="image description">
														<figcaption><a href="javascript:void(0);"><i class="icon-heart"></i><em>50,134</em></a></figcaption>
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
		<%@ include file="../client/layouts/footer.jsp"%>
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
	<script>
	var isFullDescription = false;  // Biến để theo dõi trạng thái của mô tả

    function showMore() {
        var fullDescription = '${book.description}';
        var shortDescription = '${book.description.substring(0, 100)}...';
        var descriptionElement = document.getElementById('book-description');
        var linkElement = document.getElementById('more_less');

        if (isFullDescription) {
            descriptionElement.innerText = shortDescription;  // Thu gọn lại nội dung
            linkElement.innerText = "More";  // Đổi lại chữ thành "More"
        } else {
            descriptionElement.innerText = fullDescription;  // Hiển thị đầy đủ nội dung
            linkElement.innerText = "Less";  // Đổi lại chữ thành "Less"
        }

        isFullDescription = !isFullDescription;  // Đổi trạng thái
    }
	</script>
</body>
</html>