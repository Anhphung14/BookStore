<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
    <link href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/css/toastr.min.css" rel="stylesheet">
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/js/toastr.min.js"></script>
    <!-- Modernizr JS -->
    <script src="resources/assets/js/client/vendor/modernizr-2.8.3-respond-1.4.2.min.js"></script>
</head>

<body class="tg-home tg-homeone">

	<div id="tg-wrapper" class="tg-wrapper tg-haslayout">
		<!--************************************
				Header Start
		*************************************-->
		<%@ include file="../client/layouts/header.jsp"%>
		<!--************************************
				Header End
		*************************************-->

			<!--************************************
					Best Selling Start
			*************************************-->
			<section class="tg-sectionspace tg-haslayout">
				<div class="container">
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
							<div class="tg-sectionhead">
								<h2><span>People’s Choice</span>Best selling Books</h2>
								<a class="tg-btn" href="javascript:void(0);">View All</a>
							</div>
						</div>
						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
							<div id="tg-bestsellingbooksslider" class="tg-bestsellingbooksslider tg-bestsellingbooks owl-carousel">
								<div class="item">
									<div class="tg-postbook">
										<figure class="tg-featureimg">
											<div class="tg-bookimg">
												<div class="tg-frontcover"><img src="${pageContext.servletContext.contextPath}/resources/images/client/books/img-01.jpg" alt="image description"></div>
												<div class="tg-backcover"><img src="${pageContext.servletContext.contextPath}/resources/images/client/books/img-01.jpg" alt="image description"></div>
											</div>
											<a class="tg-btnaddtowishlist" href="javascript:void(0);">
												<i class="fa-solid fa-heart"></i>
												<span>add to wishlist</span>
											</a>
										</figure>
										<div class="tg-postbookcontent">
											<ul class="tg-bookscategories">
												<li><a href="javascript:void(0);">Adventure</a></li>
												<li><a href="javascript:void(0);">Fun</a></li>
											</ul>
											<div class="tg-themetagbox"><span class="tg-themetag">sale</span></div>
											<div class="tg-booktitle">
												<h3><a href="javascript:void(0);">Help Me Find My Stomach</a></h3>
											</div>
											<span class="tg-bookwriter">By: <a href="javascript:void(0);">Angela Gunning</a></span>
											<span class="tg-stars"><span></span></span>
											<span class="tg-bookprice">
												<ins>$25.18</ins>
												<del>$27.20</del>
											</span>
											<a class="tg-btn tg-btnstyletwo" href="javascript:void(0);">
												<i class="fa fa-shopping-basket"></i>
												<em>Add To Basket</em>
											</a>
										</div>
									</div>
								</div>
								<div class="item">
									<div class="tg-postbook">
										<figure class="tg-featureimg">
											<div class="tg-bookimg">
												<div class="tg-frontcover"><img src="${pageContext.servletContext.contextPath}/resources/images/client/books/img-02.jpg" alt="image description"></div>
												<div class="tg-backcover"><img src="${pageContext.servletContext.contextPath}/resources/images/client/books/img-02.jpg" alt="image description"></div>
											</div>
											<a class="tg-btnaddtowishlist" href="javascript:void(0);">
												<i class="fa-solid fa-heart"></i>
												<span>add to wishlist</span>
											</a>
										</figure>
										<div class="tg-postbookcontent">
											<ul class="tg-bookscategories">
												<li><a href="javascript:void(0);">Adventure</a></li>
												<li><a href="javascript:void(0);">Fun</a></li>
											</ul>
											<div class="tg-themetagbox"><span class="tg-themetag">sale</span></div>
											<div class="tg-booktitle">
												<h3><a href="javascript:void(0);">Drive Safely, No Bumping</a></h3>
											</div>
											<span class="tg-bookwriter">By: <a href="javascript:void(0);">Angela Gunning</a></span>
											<span class="tg-stars"><span></span></span>
											<span class="tg-bookprice">
												<ins>$25.18</ins>
												<del>$27.20</del>
											</span>
											<a class="tg-btn tg-btnstyletwo" href="javascript:void(0);">
												<i class="fa fa-shopping-basket"></i>
												<em>Add To Basket</em>
											</a>
										</div>
									</div>
								</div>
								<div class="item">
									<div class="tg-postbook tg-notag">
										<figure class="tg-featureimg">
											<div class="tg-bookimg">
												<div class="tg-frontcover"><img src="${pageContext.servletContext.contextPath}/resources/images/client/books/img-03.jpg" alt="image description"></div>
												<div class="tg-backcover"><img src="${pageContext.servletContext.contextPath}/resources/images/client/books/img-03.jpg" alt="image description"></div>
											</div>
											<a class="tg-btnaddtowishlist" href="javascript:void(0);">
												<i class="fa-solid fa-heart"></i>
												<span>add to wishlist</span>
											</a>
										</figure>
										<div class="tg-postbookcontent">
											<ul class="tg-bookscategories">
												<li><a href="javascript:void(0);">Adventure</a></li>
												<li><a href="javascript:void(0);">Fun</a></li>
											</ul>
											<div class="tg-booktitle">
												<h3><a href="javascript:void(0);">Let The Good Times Roll Up</a></h3>
											</div>
											<span class="tg-bookwriter">By: <a href="javascript:void(0);">Angela Gunning</a></span>
											<span class="tg-stars"><span></span></span>
											<span class="tg-bookprice">
												<ins>$25.18</ins>
												<del>$27.20</del>
											</span>
											<a class="tg-btn tg-btnstyletwo" href="javascript:void(0);">
												<i class="fa fa-shopping-basket"></i>
												<em>Add To Basket</em>
											</a>
										</div>
									</div>
								</div>
								<div class="item">
									<div class="tg-postbook">
										<figure class="tg-featureimg">
											<div class="tg-bookimg">
												<div class="tg-frontcover"><img src="${pageContext.servletContext.contextPath}/resources/images/client/books/img-04.jpg" alt="image description"></div>
												<div class="tg-backcover"><img src="${pageContext.servletContext.contextPath}/resources/images/client/books/img-04.jpg" alt="image description"></div>
											</div>
											<a class="tg-btnaddtowishlist" href="javascript:void(0);">
												<i class="fa-solid fa-heart"></i>
												<span>add to wishlist</span>
											</a>
										</figure>
										<div class="tg-postbookcontent">
											<ul class="tg-bookscategories">
												<li><a href="javascript:void(0);">Adventure</a></li>
												<li><a href="javascript:void(0);">Fun</a></li>
											</ul>
											<div class="tg-themetagbox"><span class="tg-themetag">sale</span></div>
											<div class="tg-booktitle">
												<h3><a href="javascript:void(0);">Our State Fair Is A Great State Fair</a></h3>
											</div>
											<span class="tg-bookwriter">By: <a href="javascript:void(0);">Angela Gunning</a></span>
											<span class="tg-stars"><span></span></span>
											<span class="tg-bookprice">
												<ins>$25.18</ins>
												<del>$27.20</del>
											</span>
											<a class="tg-btn tg-btnstyletwo" href="javascript:void(0);">
												<i class="fa fa-shopping-basket"></i>
												<em>Add To Basket</em>
											</a>
										</div>
									</div>
								</div>
								<div class="item">
									<div class="tg-postbook tg-notag">
										<figure class="tg-featureimg">
											<div class="tg-bookimg">
												<div class="tg-frontcover"><img src="${pageContext.servletContext.contextPath}/resources/images/client/books/img-05.jpg" alt="image description"></div>
												<div class="tg-backcover"><img src="${pageContext.servletContext.contextPath}/resources/images/client/books/img-05.jpg" alt="image description"></div>
											</div>
											<a class="tg-btnaddtowishlist" href="javascript:void(0);">
												<i class="fa-solid fa-heart"></i>
												<span>add to wishlist</span>
											</a>
										</figure>
										<div class="tg-postbookcontent">
											<ul class="tg-bookscategories">
												<li><a href="javascript:void(0);">Adventure</a></li>
												<li><a href="javascript:void(0);">Fun</a></li>
											</ul>
											<div class="tg-booktitle">
												<h3><a href="javascript:void(0);">Put The Petal To The Metal</a></h3>
											</div>
											<span class="tg-bookwriter">By: <a href="javascript:void(0);">Angela Gunning</a></span>
											<span class="tg-stars"><span></span></span>
											<span class="tg-bookprice">
												<ins>$25.18</ins>
												<del>$27.20</del>
											</span>
											<a class="tg-btn tg-btnstyletwo" href="javascript:void(0);">
												<i class="fa fa-shopping-basket"></i>
												<em>Add To Basket</em>
											</a>
										</div>
									</div>
								</div>
								<div class="item">
									<div class="tg-postbook">
										<figure class="tg-featureimg">
											<div class="tg-bookimg">
												<div class="tg-frontcover"><img src="${pageContext.servletContext.contextPath}/resources/images/client/books/img-06.jpg" alt="image description"></div>
												<div class="tg-backcover"><img src="${pageContext.servletContext.contextPath}/resources/images/client/books/img-06.jpg" alt="image description"></div>
											</div>
											<a class="tg-btnaddtowishlist" href="javascript:void(0);">
												<i class="fa-solid fa-heart"></i>
												<span>add to wishlist</span>
											</a>
										</figure>
										<div class="tg-postbookcontent">
											<ul class="tg-bookscategories">
												<li><a href="javascript:void(0);">Adventure</a></li>
												<li><a href="javascript:void(0);">Fun</a></li>
											</ul>
											<div class="tg-themetagbox"><span class="tg-themetag">sale</span></div>
											<div class="tg-booktitle">
												<h3><a href="javascript:void(0);">Help Me Find My Stomach</a></h3>
											</div>
											<span class="tg-bookwriter">By: <a href="javascript:void(0);">Angela Gunning</a></span>
											<span class="tg-stars"><span></span></span>
											<span class="tg-bookprice">
												<ins>$25.18</ins>
												<del>$27.20</del>
											</span>
											<a class="tg-btn tg-btnstyletwo" href="javascript:void(0);">
												<i class="fa fa-shopping-basket"></i>
												<em>Add To Basket</em>
											</a>
										</div>
									</div>
								</div>
								<div class="item">
									<div class="tg-postbook tg-notag">
										<figure class="tg-featureimg">
											<div class="tg-bookimg">
												<div class="tg-frontcover"><img src="${pageContext.servletContext.contextPath}/resources/images/client/books/img-03.jpg" alt="image description"></div>
												<div class="tg-backcover"><img src="${pageContext.servletContext.contextPath}/resources/images/client/books/img-03.jpg" alt="image description"></div>
											</div>
											<a class="tg-btnaddtowishlist" href="javascript:void(0);">
												<i class="fa-solid fa-heart"></i>
												<span>add to wishlist</span>
											</a>
										</figure>
										<div class="tg-postbookcontent">
											<ul class="tg-bookscategories">
												<li><a href="javascript:void(0);">Adventure</a></li>
												<li><a href="javascript:void(0);">Fun</a></li>
											</ul>
											<div class="tg-booktitle">
												<h3><a href="javascript:void(0);">Let The Good Times Roll Up</a></h3>
											</div>
											<span class="tg-bookwriter">By: <a href="javascript:void(0);">Angela Gunning</a></span>
											<span class="tg-stars"><span></span></span>
											<span class="tg-bookprice">
												<ins>$25.18</ins>
												<del>$27.20</del>
											</span>
											<a class="tg-btn tg-btnstyletwo" href="javascript:void(0);">
												<i class="fa fa-shopping-basket"></i>
												<em>Add To Basket</em>
											</a>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</section>
			<!--************************************
					Best Selling End
			*************************************-->

			<!--************************************
					Featured Item Start
			*************************************-->
			<section class="tg-bglight tg-haslayout">
				<div class="container">
					<div class="row">
						<div class="tg-featureditm">
							<div class="col-xs-12 col-sm-12 col-md-4 col-lg-4 hidden-sm hidden-xs">
								<figure><img src="${pageContext.servletContext.contextPath}/resources/images/client/img-02.png" alt="image description"></figure>
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
			</section>
			<!--************************************
					Featured Item End
			*************************************-->
			<!--************************************
					New Release Start
			*************************************-->
			<section class="tg-sectionspace tg-haslayout">
				<div class="container">
					<div class="row">
						<div class="tg-newrelease">
							<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
								<div class="tg-sectionhead">
									<h2><span>Taste The New Spice</span>New Release Books</h2>
								</div>
								<div class="tg-description">
									<p>Consectetur adipisicing elit sed do eiusmod tempor incididunt labore toloregna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamcoiars nisiuip commodo consequat aute irure dolor in aprehenderit aveli esseati cillum dolor fugiat nulla pariatur cepteur sint occaecat cupidatat.</p>
								</div>
								<div class="tg-btns">
									<a class="tg-btn tg-active" href="javascript:void(0);">View All</a>
									<a class="tg-btn" href="javascript:void(0);">Read More</a>
								</div>
							</div>
							<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
								<div class="row">
									<div class="tg-newreleasebooks">
										<div class="col-xs-4 col-sm-4 col-md-6 col-lg-4">
											<div class="tg-postbook">
												<figure class="tg-featureimg">
													<div class="tg-bookimg">
														<div class="tg-frontcover"><img src="${pageContext.servletContext.contextPath}/resources/images/client/books/img-07.jpg" alt="image description"></div>
														<div class="tg-backcover"><img src="${pageContext.servletContext.contextPath}/resources/images/client/books/img-07.jpg" alt="image description"></div>
													</div>
													<a class="tg-btnaddtowishlist" href="javascript:void(0);">
														<i class="fa-solid fa-heart"></i>
														<span>add to wishlist</span>
													</a>
												</figure>
												<div class="tg-postbookcontent">
													<ul class="tg-bookscategories">
														<li><a href="javascript:void(0);">Adventure</a></li>
														<li><a href="javascript:void(0);">Fun</a></li>
													</ul>
													<div class="tg-booktitle">
														<h3><a href="javascript:void(0);">Help Me Find My Stomach</a></h3>
													</div>
													<span class="tg-bookwriter">By: <a href="javascript:void(0);">Kathrine Culbertson</a></span>
													<span class="tg-stars"><span></span></span>
												</div>
											</div>
										</div>
										<div class="col-xs-4 col-sm-4 col-md-6 col-lg-4">
											<div class="tg-postbook">
												<figure class="tg-featureimg">
													<div class="tg-bookimg">
														<div class="tg-frontcover"><img src="${pageContext.servletContext.contextPath}/resources/images/client/books/img-08.jpg" alt="image description"></div>
														<div class="tg-backcover"><img src="${pageContext.servletContext.contextPath}/resources/images/client/books/img-08.jpg" alt="image description"></div>
													</div>
													<a class="tg-btnaddtowishlist" href="javascript:void(0);">
														<i class="fa-solid fa-heart"></i>
														<span>add to wishlist</span>
													</a>
												</figure>
												<div class="tg-postbookcontent">
													<ul class="tg-bookscategories">
														<li><a href="javascript:void(0);">Adventure</a></li>
														<li><a href="javascript:void(0);">Fun</a></li>
													</ul>
													<div class="tg-booktitle">
														<h3><a href="javascript:void(0);">Drive Safely, No Bumping</a></h3>
													</div>
													<span class="tg-bookwriter">By: <a href="javascript:void(0);">Sunshine Orlando</a></span>
													<span class="tg-stars"><span></span></span>
												</div>
											</div>
										</div>
										<div class="col-xs-4 col-sm-4 col-md-3 col-lg-4 hidden-md">
											<div class="tg-postbook">
												<figure class="tg-featureimg">
													<div class="tg-bookimg">
														<div class="tg-frontcover"><img src="${pageContext.servletContext.contextPath}/resources/images/client/books/img-09.jpg" alt="image description"></div>
														<div class="tg-backcover"><img src="${pageContext.servletContext.contextPath}/resources/images/client/books/img-09.jpg" alt="image description"></div>
													</div>
													<a class="tg-btnaddtowishlist" href="javascript:void(0);">
														<i class="fa-solid fa-heart"></i>
														<span>add to wishlist</span>
													</a>
												</figure>
												<div class="tg-postbookcontent">
													<ul class="tg-bookscategories">
														<li><a href="javascript:void(0);">Adventure</a></li>
														<li><a href="javascript:void(0);">Fun</a></li>
													</ul>
													<div class="tg-booktitle">
														<h3><a href="javascript:void(0);">Let The Good Times Roll Up</a></h3>
													</div>
													<span class="tg-bookwriter">By: <a href="javascript:void(0);">Elisabeth Ronning</a></span>
													<span class="tg-stars"><span></span></span>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</section>
			<!--************************************
					New Release End
			*************************************-->
			<!--************************************
					Collection Count Start
			*************************************-->
			<section class="tg-parallax tg-bgcollectioncount tg-haslayout" data-z-index="-100" data-appear-top-offset="600" data-parallax="scroll" data-image-src="${pageContext.servletContext.contextPath}/resources/images/client/parallax/bgparallax-04.jpg">
				<div class="tg-sectionspace tg-collectioncount tg-haslayout">
					<div class="container">
						<div class="row">
							<div id="tg-collectioncounters" class="tg-collectioncounters">
								<div class="tg-collectioncounter tg-drama">
									<div class="tg-collectioncountericon">
										<i class="fa-solid fa-masks-theater"></i>
									</div>
									<div class="tg-titlepluscounter">
										<h2>Drama</h2>
										<h3 data-from="0" data-to="6179213" data-speed="8000" data-refresh-interval="50">6,179,213</h3>
									</div>
								</div>
								<div class="tg-collectioncounter tg-horror">
									<div class="tg-collectioncountericon">
										<i class="fa-solid fa-heart-pulse"></i>
									</div>
									<div class="tg-titlepluscounter">
										<h2>Horror</h2>
										<h3 data-from="0" data-to="3121242" data-speed="8000" data-refresh-interval="50">3,121,242</h3>
									</div>
								</div>
								<div class="tg-collectioncounter tg-romance">
									<div class="tg-collectioncountericon">
										<i class="fa-solid fa-heart"></i>
									</div>
									<div class="tg-titlepluscounter">
										<h2>Romance</h2>
										<h3 data-from="0" data-to="2101012" data-speed="8000" data-refresh-interval="50">2,101,012</h3>
									</div>
								</div>
								<div class="tg-collectioncounter tg-fashion">
									<div class="tg-collectioncountericon">
										<i class="fa-solid fa-star"></i>
									</div>
									<div class="tg-titlepluscounter">
										<h2>Fashion</h2>
										<h3 data-from="0" data-to="1158245" data-speed="8000" data-refresh-interval="50">1,158,245</h3>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</section>
			<!--************************************
					Collection Count End
			*************************************-->
			<!--************************************
					Picked By Author Start
			*************************************-->
			<section class="tg-sectionspace tg-haslayout">
				<div class="container">
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
							<div class="tg-sectionhead">
								<h2><span>Some Great Books</span>Picked By Authors</h2>
								<a class="tg-btn" href="javascript:void(0);">View All</a>
							</div>
						</div>
						<div id="tg-pickedbyauthorslider" class="tg-pickedbyauthor tg-pickedbyauthorslider owl-carousel">
							<div class="item">
								<div class="tg-postbook">
									<figure class="tg-featureimg">
										<div class="tg-bookimg">
											<div class="tg-frontcover"><img src="${pageContext.servletContext.contextPath}/resources/images/client/books/img-10.jpg" alt="image description"></div>
										</div>
										<div class="tg-hovercontent">
											<div class="tg-description">
												<p>Consectetur adipisicing elit sed do eiusmod tempor incididunt labore toloregna aliqua enim adia minim veniam, quis nostrud.</p>
											</div>
											<strong class="tg-bookpage">Book Pages: 206</strong>
											<strong class="tg-bookcategory">Category: Adventure, Fun</strong>
											<strong class="tg-bookprice">Price: $23.18</strong>
											<div class="tg-ratingbox"><span class="tg-stars"><span></span></span></div>
										</div>
									</figure>
									<div class="tg-postbookcontent">
										<div class="tg-booktitle">
											<h3><a href="javascript:void(0);">Seven Minutes In Heaven</a></h3>
										</div>
										<span class="tg-bookwriter">By: <a href="javascript:void(0);">Sunshine Orlando</a></span>
										<a class="tg-btn tg-btnstyletwo" href="javascript:void(0);">
											<i class="fa fa-shopping-baowl-prev tg-btnround tg-btnprevsket"></i>
											<em>Add To Basket</em>
										</a>
									</div>
								</div>
							</div>
							<div class="item">
								<div class="tg-postbook">
									<figure class="tg-featureimg">
										<div class="tg-bookimg">
											<div class="tg-frontcover"><img src="${pageContext.servletContext.contextPath}/resources/images/client/books/img-11.jpg" alt="image description"></div>
										</div>
										<div class="tg-hovercontent">
											<div class="tg-description">
												<p>Consectetur adipisicing elit sed do eiusmod tempor incididunt labore toloregna aliqua enim adia minim veniam, quis nostrud.</p>
											</div>
											<strong class="tg-bookpage">Book Pages: 206</strong>
											<strong class="tg-bookcategory">Category: Adventure, Fun</strong>
											<strong class="tg-bookprice">Price: $23.18</strong>
											<div class="tg-ratingbox"><span class="tg-stars"><span></span></span></div>
										</div>
									</figure>
									<div class="tg-postbookcontent">
										<div class="tg-booktitle">
											<h3><a href="javascript:void(0);">Slow And Steady Wins The Race</a></h3>
										</div>
										<span class="tg-bookwriter">By: <a href="javascript:void(0);">Drusilla Glandon</a></span>
										<a class="tg-btn tg-btnstyletwo" href="javascript:void(0);">
											<i class="fa fa-shopping-basket"></i>
											<em>Add To Basket</em>
										</a>
									</div>
								</div>
							</div>
							<div class="item">
								<div class="tg-postbook">
									<figure class="tg-featureimg">
										<div class="tg-bookimg">
											<div class="tg-frontcover"><img src="${pageContext.servletContext.contextPath}/resources/images/client/books/img-12.jpg" alt="image description"></div>
										</div>
										<div class="tg-hovercontent">
											<div class="tg-description">
												<p>Consectetur adipisicing elit sed do eiusmod tempor incididunt labore toloregna aliqua enim adia minim veniam, quis nostrud.</p>
											</div>
											<strong class="tg-bookpage">Book Pages: 206</strong>
											<strong class="tg-bookcategory">Category: Adventure, Fun</strong>
											<strong class="tg-bookprice">Price: $23.18</strong>
											<div class="tg-ratingbox"><span class="tg-stars"><span></span></span></div>
										</div>
									</figure>
									<div class="tg-postbookcontent">
										<div class="tg-booktitle">
											<h3><a href="javascript:void(0);">There’s No Time Like The Present</a></h3>
										</div>
										<span class="tg-bookwriter">By: <a href="javascript:void(0);">Patrick Seller</a></span>
										<a class="tg-btn tg-btnstyletwo" href="javascript:void(0);">
											<i class="fa fa-shopping-basket"></i>
											<em>Add To Basket</em>
										</a>
									</div>
								</div>
							</div>
							<div class="item">
								<div class="tg-postbook">
									<figure class="tg-featureimg">
										<div class="tg-bookimg">
											<div class="tg-frontcover"><img src="${pageContext.servletContext.contextPath}/resources/images/client/books/img-10.jpg" alt="image description"></div>
										</div>
										<div class="tg-hovercontent">
											<div class="tg-description">
												<p>Consectetur adipisicing elit sed do eiusmod tempor incididunt labore toloregna aliqua enim adia minim veniam, quis nostrud.</p>
											</div>
											<strong class="tg-bookpage">Book Pages: 206</strong>
											<strong class="tg-bookcategory">Category: Adventure, Fun</strong>
											<strong class="tg-bookprice">Price: $23.18</strong>
											<div class="tg-ratingbox"><span class="tg-stars"><span></span></span></div>
										</div>
									</figure>
									<div class="tg-postbookcontent">
										<div class="tg-booktitle">
											<h3><a href="javascript:void(0);">Seven Minutes In Heaven</a></h3>
										</div>
										<span class="tg-bookwriter">By: <a href="javascript:void(0);">Sunshine Orlando</a></span>
										<a class="tg-btn tg-btnstyletwo" href="javascript:void(0);">
											<i class="fa fa-shopping-basket"></i>
											<em>Add To Basket</em>
										</a>
									</div>
								</div>
							</div>
							<div class="item">
								<div class="tg-postbook">
									<figure class="tg-featureimg">
										<div class="tg-bookimg">
											<div class="tg-frontcover"><img src="${pageContext.servletContext.contextPath}/resources/images/client/books/img-11.jpg" alt="image description"></div>
										</div>
										<div class="tg-hovercontent">
											<div class="tg-description">
												<p>Consectetur adipisicing elit sed do eiusmod tempor incididunt labore toloregna aliqua enim adia minim veniam, quis nostrud.</p>
											</div>
											<strong class="tg-bookpage">Book Pages: 206</strong>
											<strong class="tg-bookcategory">Category: Adventure, Fun</strong>
											<strong class="tg-bookprice">Price: $23.18</strong>
											<div class="tg-ratingbox"><span class="tg-stars"><span></span></span></div>
										</div>
									</figure>
									<div class="tg-postbookcontent">
										<div class="tg-booktitle">
											<h3><a href="javascript:void(0);">Slow And Steady Wins The Race</a></h3>
										</div>
										<span class="tg-bookwriter">By: <a href="javascript:void(0);">Drusilla Glandon</a></span>
										<a class="tg-btn tg-btnstyletwo" href="javascript:void(0);">
											<i class="fa fa-shopping-basket"></i>
											<em>Add To Basket</em>
										</a>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</section>
			<!--************************************
					Picked By Author End
			*************************************-->
			<!--************************************
					Testimonials Start
			*************************************-->
			<section class="tg-parallax tg-bgtestimonials tg-haslayout" data-z-index="-100" data-appear-top-offset="600" data-parallax="scroll" data-image-src="${pageContext.servletContext.contextPath}/resources/images/client/parallax/bgparallax-05.jpg">
				<div class="tg-sectionspace tg-haslayout">
					<div class="container">
						<div class="row">
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-8 col-lg-push-2">
								<div id="tg-testimonialsslider" class="tg-testimonialsslider tg-testimonials owl-carousel">
									<div class="item tg-testimonial">
										<figure><img src="${pageContext.servletContext.contextPath}/resources/images/client/author/imag-02.jpg" alt="image description"></figure>
										<blockquote><q>Consectetur adipisicing elit sed do eiusmod tempor incididunt ut labore tolore magna aliqua enim ad minim veniam, quis nostrud exercitation ullamcoiars nisi ut aliquip commodo.</q></blockquote>
										<div class="tg-testimonialauthor">
											<h3>Holli Fenstermacher</h3>
											<span>Manager @ CIFP</span>
										</div>
									</div>
									<div class="item tg-testimonial">
										<figure><img src="${pageContext.servletContext.contextPath}/resources/images/client/author/imag-02.jpg" alt="image description"></figure>
										<blockquote><q>Consectetur adipisicing elit sed do eiusmod tempor incididunt ut labore tolore magna aliqua enim ad minim veniam, quis nostrud exercitation ullamcoiars nisi ut aliquip commodo.</q></blockquote>
										<div class="tg-testimonialauthor">
											<h3>Holli Fenstermacher</h3>
											<span>Manager @ CIFP</span>
										</div>
									</div>
									<div class="item tg-testimonial">
										<figure><img src="${pageContext.servletContext.contextPath}/resources/images/client/author/imag-02.jpg" alt="image description"></figure>
										<blockquote><q>Consectetur adipisicing elit sed do eiusmod tempor incididunt ut labore tolore magna aliqua enim ad minim veniam, quis nostrud exercitation ullamcoiars nisi ut aliquip commodo.</q></blockquote>
										<div class="tg-testimonialauthor">
											<h3>Holli Fenstermacher</h3>
											<span>Manager @ CIFP</span>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</section>
			<!--************************************
					Testimonials End
			*************************************-->
			
			<!--************************************
					Call to Action Start
			*************************************-->
			<section class="tg-parallax tg-bgcalltoaction tg-haslayout" data-z-index="-100" data-appear-top-offset="600" data-parallax="scroll" data-image-src="${pageContext.servletContext.contextPath}/resources/images/client/parallax/bgparallax-06.jpg">
				<div class="tg-sectionspace tg-haslayout">
					<div class="container">
						<div class="row">
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
								<div class="tg-calltoaction">
									<h2>Open Discount For All</h2>
									<h3>Consectetur adipisicing elit sed do eiusmod tempor incididunt ut labore et dolore.</h3>
									<a class="tg-btn tg-active" href="javascript:void(0);">Read More</a>
								</div>
							</div>
						</div>
					</div>
				</div>
			</section>
			<!--************************************
					Call to Action End
			*************************************-->
			<!--************************************
					Latest News Start
			*************************************-->
			<section class="tg-sectionspace tg-haslayout">
				<div class="container">
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
							<div class="tg-sectionhead">
								<h2><span>Latest News &amp; Articles</span>What's Hot in The News</h2>
								<a class="tg-btn" href="javascript:void(0);">View All</a>
							</div>
						</div>
						<div id="tg-postslider" class="tg-postslider tg-blogpost owl-carousel">
							<article class="item tg-post">
								<figure><a href="javascript:void(0);"><img src="${pageContext.servletContext.contextPath}/resources/images/client/blog/img-01.jpg" alt="image description"></a></figure>
								<div class="tg-postcontent">
									<ul class="tg-bookscategories">
										<li><a href="javascript:void(0);">Adventure</a></li>
										<li><a href="javascript:void(0);">Fun</a></li>
									</ul>
									<div class="tg-themetagbox"><span class="tg-themetag">featured</span></div>
									<div class="tg-posttitle">
										<h3><a href="javascript:void(0);">Where The Wild Things Are</a></h3>
									</div>
									<span class="tg-bookwriter">By: <a href="javascript:void(0);">Kathrine Culbertson</a></span>
									<ul class="tg-postmetadata">
										<li><a href="javascript:void(0);"><i class="fa fa-comment-o"></i><i>21,415 Comments</i></a></li>
										<li><a href="javascript:void(0);"><i class="fa fa-eye"></i><i>24,565 Views</i></a></li>
									</ul>
								</div>
							</article>
							<article class="item tg-post">
								<figure><a href="javascript:void(0);"><img src="${pageContext.servletContext.contextPath}/resources/images/client/blog/img-02.jpg" alt="image description"></a></figure>
								<div class="tg-postcontent">
									<ul class="tg-bookscategories">
										<li><a href="javascript:void(0);">Adventure</a></li>
										<li><a href="javascript:void(0);">Fun</a></li>
									</ul>
									<div class="tg-themetagbox"><span class="tg-themetag">featured</span></div>
									<div class="tg-posttitle">
										<h3><a href="javascript:void(0);">All She Wants To Do Is Dance</a></h3>
									</div>
									<span class="tg-bookwriter">By: <a href="javascript:void(0);">Kathrine Culbertson</a></span>
									<ul class="tg-postmetadata">
										<li><a href="javascript:void(0);"><i class="fa fa-comment-o"></i><i>21,415 Comments</i></a></li>
										<li><a href="javascript:void(0);"><i class="fa fa-eye"></i><i>24,565 Views</i></a></li>
									</ul>
								</div>
							</article>
							<article class="item tg-post">
								<figure><a href="javascript:void(0);"><img src="${pageContext.servletContext.contextPath}/resources/images/client/blog/img-03.jpg" alt="image description"></a></figure>
								<div class="tg-postcontent">
									<ul class="tg-bookscategories">
										<li><a href="javascript:void(0);">Adventure</a></li>
										<li><a href="javascript:void(0);">Fun</a></li>
									</ul>
									<div class="tg-themetagbox"><span class="tg-themetag">featured</span></div>
									<div class="tg-posttitle">
										<h3><a href="javascript:void(0);">Why Walk When You Can Climb?</a></h3>
									</div>
									<span class="tg-bookwriter">By: <a href="javascript:void(0);">Kathrine Culbertson</a></span>
									<ul class="tg-postmetadata">
										<li><a href="javascript:void(0);"><i class="fa fa-comment-o"></i><i>21,415 Comments</i></a></li>
										<li><a href="javascript:void(0);"><i class="fa fa-eye"></i><i>24,565 Views</i></a></li>
									</ul>
								</div>
							</article>
							<article class="item tg-post">
								<figure><a href="javascript:void(0);"><img src="${pageContext.servletContext.contextPath}/resources/images/client/blog/img-04.jpg" alt="image description"></a></figure>
								<div class="tg-postcontent">
									<ul class="tg-bookscategories">
										<li><a href="javascript:void(0);">Adventure</a></li>
										<li><a href="javascript:void(0);">Fun</a></li>
									</ul>
									<div class="tg-themetagbox"><span class="tg-themetag">featured</span></div>
									<div class="tg-posttitle">
										<h3><a href="javascript:void(0);">Dance Like Nobody’s Watching</a></h3>
									</div>
									<span class="tg-bookwriter">By: <a href="javascript:void(0);">Kathrine Culbertson</a></span>
									<ul class="tg-postmetadata">
										<li><a href="javascript:void(0);"><i class="fa fa-comment-o"></i><i>21,415 Comments</i></a></li>
										<li><a href="javascript:void(0);"><i class="fa fa-eye"></i><i>24,565 Views</i></a></li>
									</ul>
								</div>
							</article>
							<article class="item tg-post">
								<figure><a href="javascript:void(0);"><img src="${pageContext.servletContext.contextPath}/resources/images/client/blog/img-02.jpg" alt="image description"></a></figure>
								<div class="tg-postcontent">
									<ul class="tg-bookscategories">
										<li><a href="javascript:void(0);">Adventure</a></li>
										<li><a href="javascript:void(0);">Fun</a></li>
									</ul>
									<div class="tg-themetagbox"><span class="tg-themetag">featured</span></div>
									<div class="tg-posttitle">
										<h3><a href="javascript:void(0);">All She Wants To Do Is Dance</a></h3>
									</div>
									<span class="tg-bookwriter">By: <a href="javascript:void(0);">Kathrine Culbertson</a></span>
									<ul class="tg-postmetadata">
										<li><a href="javascript:void(0);"><i class="fa fa-comment-o"></i><i>21,415 Comments</i></a></li>
										<li><a href="javascript:void(0);"><i class="fa fa-eye"></i><i>24,565 Views</i></a></li>
									</ul>
								</div>
							</article>
							<article class="item tg-post">
								<figure><a href="javascript:void(0);"><img src="${pageContext.servletContext.contextPath}/resources/images/client/blog/img-03.jpg" alt="image description"></a></figure>
								<div class="tg-postcontent">
									<ul class="tg-bookscategories">
										<li><a href="javascript:void(0);">Adventure</a></li>
										<li><a href="javascript:void(0);">Fun</a></li>
									</ul>
									<div class="tg-themetagbox"><span class="tg-themetag">featured</span></div>
									<div class="tg-posttitle">
										<h3><a href="javascript:void(0);">Why Walk When You Can Climb?</a></h3>
									</div>
									<span class="tg-bookwriter">By: <a href="javascript:void(0);">Kathrine Culbertson</a></span>
									<ul class="tg-postmetadata">
										<li><a href="javascript:void(0);"><i class="fa fa-comment-o"></i><i>21,415 Comments</i></a></li>
										<li><a href="javascript:void(0);"><i class="fa fa-eye"></i><i>24,565 Views</i></a></li>
									</ul>
								</div>
							</article>
						</div>
					</div>
				</div>
			</section>
			<!--************************************
					Latest News End
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
</body>

<script type="text/javascript">
	const alertMessage = "${alertMessage}";
	const alertType = "${alertType}";
	toastr.options = {
		    "closeButton": true, // Cho phép nút đóng
		    "debug": false,
		    "newestOnTop": true,
		    "progressBar": true, // Hiển thị thanh tiến trình
		    "positionClass": "toast-top-right", // Vị trí hiển thị thông báo
		    "preventDuplicates": true,
		    "onclick": null,
		    "showDuration": "200", // Thời gian hiển thị
		    "hideDuration": "1000",
		    "timeOut": "5000", // Thời gian thông báo sẽ tự động ẩn
		    "extendedTimeOut": "1000", // Thời gian mở rộng nếu hover vào
		    "showEasing": "swing",
		    "hideEasing": "linear",
		    "showMethod": "fadeIn",
		    "hideMethod": "fadeOut"
		};
	if (alertMessage) {
	    // Kiểm tra kiểu thông báo
	    if (alertType === "success") {
	        toastr.success(alertMessage, "Success");
	    } else if (alertType === "error") {
	        toastr.error(alertMessage, "Error");
	    }
	}
</script>

</html>