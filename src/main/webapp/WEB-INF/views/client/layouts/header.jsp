<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Book Library</title>
    <base href="${pageContext.servletContext.contextPath}/">
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>

<header id="tg-header" class="tg-header tg-haslayout">
			<div class="tg-topbar">
				<div class="container">
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
							<ul class="tg-addnav">
								<li>
									<a href="javascript:void(0);">
										<i class="fa-solid fa-envelope"></i>
										<em>Contact</em>
									</a>
								</li>
								<li>
									<a href="javascript:void(0);">
										<i class="fa-solid fa-circle-question"></i>
										<em>Help</em>
									</a>
								</li>
							</ul>
							<div class="dropdown tg-themedropdown tg-currencydropdown">
								<a href="javascript:void(0);" id="tg-currenty" class="tg-btnthemedropdown" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
									<i class="fa-solid fa-earth-americas"></i>
									<span>Currency</span>
								</a>
								<ul class="dropdown-menu tg-themedropdownmenu" aria-labelledby="tg-currenty">
									<li>
										<a href="javascript:void(0);">
											<i>£</i>
											<span>British Pound</span>
										</a>
									</li>
									<li>
										<a href="javascript:void(0);">
											<i>$</i>
											<span>Us Dollar</span>
										</a>
									</li>
									<li>
										<a href="javascript:void(0);">
											<i>€</i>
											<span>Euro</span>
										</a>
									</li>
								</ul>
							</div>
							<div class="tg-userlogin">
								<figure><a href="javascript:void(0);"><img src="${pageContext.servletContext.contextPath}/resources/images/client/users/img-01.jpg" alt="image description"></a></figure>
								<span>Hi, John</span>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="tg-middlecontainer">
				<div class="container">
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
							
							<strong class="tg-logo"><a href="index.htm"><img src="${pageContext.servletContext.contextPath}/resources/images/ALDPT.png" alt="BookStore"></a></strong>
							
							 <div class="tg-allcategories">
			                    <ul>
									<li class="menu-item-has-children menu-item-has-mega-menu" style="list-style: none; ">
									    <a href="javascript:void(0);" style="color: #404040;">
									    	<i class="fa-solid fa-book" style= "font-size: 20px; padding: 0 4px 0 0;"></i>
									    	<span style="font-weight: 600; line-height: 20px;">Danh mục</span>
								    	</a>
									    <div class="mega-menu">
									        <!-- Danh sách Tabs -->
									        <ul class="tg-themetabnav" role="tablist">
									            <c:forEach var="category" items="${Categories}">
									                <li role="presentation" class="<c:if test='${category.id == 1}'>active</c:if>">
									                    <a href="#category-${category.id}" 
									                       aria-controls="category-${category.id}" 
									                       role="tab" 
									                       data-toggle="tab">${category.name}</a>
									                </li>
									            </c:forEach>
									        </ul>
									        
									        <!-- Nội dung Tabs -->
									        <div class="tab-content tg-themetabcontent">
									            <c:forEach var="category" items="${Categories}">
									                <div role="tabpanel" class="tab-pane <c:if test='${category.id == 1}'>active</c:if>" id="category-${category.id}">
									                    <ul>
									                        <!-- Hiển thị các Subcategories -->
									                        <li>
									                            <div class="tg-linkstitle">
									                                <h2><a>${category.name}</a></h2>
									                            </div>
									                            <ul>
									                                <c:forEach var="subcategory" items="${SubCategories}">
									                                    <c:if test="${subcategory.categoriesEntity.id == category.id}">
									                                        <li style="list-style: none;"><a href="http://localhost:8080/bookstore/categories/${category.slug }/${subcategory.slug }.htm">${subcategory.name}</a></li>
									                                    </c:if>
									                                </c:forEach>
									                            </ul>
									                        </li>
									                    </ul>
									
									                    <!-- Phần bổ sung nội dung -->
									                    <ul>
									                        <li>
									                            <figure>
									                                <img src="${pageContext.servletContext.contextPath}/resources/images/client/img-01.png" alt="image description">
									                            </figure>
									                            <div class="tg-textbox">
									                                <h3>More Than<span>12,0657,53</span>Books Collection</h3>
									                                <div class="tg-description">
									                                    <p>Consectetur adipisicing elit sed doe eiusmod tempor incididunt laebore toloregna aliqua enim.</p>
									                                </div>
									                                <a class="tg-btn" href="products.html">view all</a>
									                            </div>
									                        </li>
									                    </ul>
									                </div>
									            </c:forEach>
									        </div>
									    </div>
									</li>
								</ul>
							</div>
							
							
							
							<div class="tg-wishlistandcart">
								<div class="dropdown tg-themedropdown tg-wishlistdropdown">
									<a href="javascript:void(0);" id="tg-wishlisst" class="tg-btnthemedropdown" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
										<span class="tg-themebadge">3</span>
										<i class="fa-solid fa-heart"></i>
										<span>Wishlist</span>
									</a>
									<div class="dropdown-menu tg-themedropdownmenu" aria-labelledby="tg-wishlisst">
										<div class="tg-description"><p>No products were added to the wishlist!</p></div>
									</div>
								</div>
								<div class="dropdown tg-themedropdown tg-minicartdropdown">
									<a href="javascript:void(0);" id="tg-minicart" class="tg-btnthemedropdown" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
										<span class="tg-themebadge">3</span>
										<i class="fa-solid fa-cart-shopping"></i>
										<span>$123.00</span>
									</a>
									<div class="dropdown-menu tg-themedropdownmenu" aria-labelledby="tg-minicart">
										<div class="tg-minicartbody">
											<div class="tg-minicarproduct">
												<figure>
													<img src="${pageContext.servletContext.contextPath}/resources/images/client/products/img-01.jpg" alt="image description">
													
												</figure>
												<div class="tg-minicarproductdata">
													<h5><a href="javascript:void(0);">Our State Fair Is A Great Function</a></h5>
													<h6><a href="javascript:void(0);">$ 12.15</a></h6>
												</div>
											</div>
											<div class="tg-minicarproduct">
												<figure>
													<img src="${pageContext.servletContext.contextPath}/resources/images/client/products/img-02.jpg" alt="image description">
													
												</figure>
												<div class="tg-minicarproductdata">
													<h5><a href="javascript:void(0);">Bring Me To Light</a></h5>
													<h6><a href="javascript:void(0);">$ 12.15</a></h6>
												</div>
											</div>
											<div class="tg-minicarproduct">
												<figure>
													<img src="${pageContext.servletContext.contextPath}/resources/images/client/products/img-03.jpg" alt="image description">
													
												</figure>
												<div class="tg-minicarproductdata">
													<h5><a href="javascript:void(0);">Have Faith In Your Soul</a></h5>
													<h6><a href="javascript:void(0);">$ 12.15</a></h6>
												</div>
											</div>
										</div>
										<div class="tg-minicartfoot">
											<a class="tg-btnemptycart" href="javascript:void(0);">
												<i class="fa fa-trash-o"></i>
												<span>Clear Your Cart</span>
											</a>
											<span class="tg-subtotal">Subtotal: <strong>35.78</strong></span>
											<div class="tg-btns">
												<a class="tg-btn tg-active" href="javascript:void(0);">View Cart</a>
												<a class="tg-btn" href="javascript:void(0);">Checkout</a>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="tg-searchbox">
							    <form class="tg-formtheme tg-formsearch" action="${pageContext.servletContext.contextPath}/search.htm" method="GET">
							        <fieldset>
							            <!-- Ô nhập liệu tìm kiếm -->
							            <input type="text" name="q" class="typeahead form-control" placeholder="Search by title, author, keyword, ISBN...">
							            <!-- Nút tìm kiếm -->
							            <button type="submit">
							                <i class="fa-solid fa-magnifying-glass"></i>
							            </button>
							        </fieldset>
							       
							    </form>
							</div>
						</div>
					</div>
				</div>
			</div>
			<%-- <div class="tg-navigationarea">
				<div class="container">
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
							<nav id="tg-nav" class="tg-nav">
								<div class="navbar-header">
									<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#tg-navigation" aria-expanded="false">
										<span class="sr-only">Toggle navigation</span>
										<span class="fa-solid fa-bars"></span>
										<span class="fa-solid fa-bars"></span>
										<span class="fa-solid fa-bars"></span>
									</button>
								</div>
								<div id="tg-navigation" class="collapse navbar-collapse tg-navigation">
									<ul>
										<li class="menu-item-has-children menu-item-has-mega-menu">
										    <a href="javascript:void(0);">All Categories</a>
										    <div class="mega-menu">
										        <!-- Danh sách Tabs -->
										        <ul class="tg-themetabnav" role="tablist">
										            <c:forEach var="category" items="${Categories}">
										                <li role="presentation" class="<c:if test='${category.id == 1}'>active</c:if>">
										                    <a href="#category-${category.id}" 
										                       aria-controls="category-${category.id}" 
										                       role="tab" 
										                       data-toggle="tab">${category.name}</a>
										                </li>
										            </c:forEach>
										        </ul>
										        
										        <!-- Nội dung Tabs -->
										        <div class="tab-content tg-themetabcontent">
										            <c:forEach var="category" items="${Categories}">
										                <div role="tabpanel" class="tab-pane <c:if test='${category.id == 1}'>active</c:if>" id="category-${category.id}">
										                    <ul>
										                        <!-- Hiển thị các Subcategories -->
										                        <li>
										                            <div class="tg-linkstitle">
										                                <h2><a>${category.name}</a></h2>
										                            </div>
										                            <ul>
										                                <c:forEach var="subcategory" items="${SubCategories}">
										                                    <c:if test="${subcategory.category.id == category.id}">
										                                        <li><a href="http://localhost:8080/bookstore/categories/${category.slug }/${subcategory.slug }.htm">${subcategory.name}</a></li>
										                                    </c:if>
										                                </c:forEach>
										                            </ul>
										                        </li>
										                    </ul>
										
										                    <!-- Phần bổ sung nội dung -->
										                    <ul>
										                        <li>
										                            <figure>
										                                <img src="${pageContext.servletContext.contextPath}/resources/images/client/img-01.png" alt="image description">
										                            </figure>
										                            <div class="tg-textbox">
										                                <h3>More Than<span>12,0657,53</span>Books Collection</h3>
										                                <div class="tg-description">
										                                    <p>Consectetur adipisicing elit sed doe eiusmod tempor incididunt laebore toloregna aliqua enim.</p>
										                                </div>
										                                <a class="tg-btn" href="products.html">view all</a>
										                            </div>
										                        </li>
										                    </ul>
										                </div>
										            </c:forEach>
										        </div>
										    </div>
										</li>

										<li class="menu-item-has-children current-menu-item">
											<a href="javascript:void(0);">Home</a>
											<ul class="sub-menu">
												<li class="current-menu-item"><a href="index-2.html">Home V one</a></li>
												<li><a href="indexv2.html">Home V two</a></li>
												<li><a href="indexv3.html">Home V three</a></li>
											</ul>
										</li>
										<li class="menu-item-has-children">
											<a href="javascript:void(0);">Authors</a>
											<ul class="sub-menu">
												<li><a href="authors.html">Authors</a></li>
												<li><a href="authordetail.html">Author Detail</a></li>
											</ul>
										</li>
										<li><a href="products.html">Best Selling</a></li>
										<li><a href="products.html">Weekly Sale</a></li>
										<li class="menu-item-has-children">
											<a href="javascript:void(0);">Latest News</a>
											<ul class="sub-menu">
												<li><a href="newslist.html">News List</a></li>
												<li><a href="newsgrid.html">News Grid</a></li>
												<li><a href="newsdetail.html">News Detail</a></li>
											</ul>
										</li>
										<li><a href="contactus.html">Contact</a></li>
										<li class="menu-item-has-children current-menu-item">
											<a href="javascript:void(0);"><i class="fa-solid fa-bars"></i></a>
											<ul class="sub-menu">
												<li class="menu-item-has-children">
													<a href="aboutus.html">Products</a>
													<ul class="sub-menu">
														<li><a href="products.html">Products</a></li>
														<li><a href="productdetail.html">Product Detail</a></li>
													</ul>
												</li>
												<li><a href="aboutus.html">About Us</a></li>
												<li><a href="404error.html">404 Error</a></li>
												<li><a href="comingsoon.html">Coming Soon</a></li>
											</ul>
										</li>
									</ul>
								</div>
							</nav>
						</div>
					</div>
				</div>
			</div> --%>
		</header>	
		
		
		<script type="text/javascript">
		
		document.addEventListener('DOMContentLoaded', function () {
		    const searchInput = document.querySelector('input[name="q"]');
		    const placeholderTexts = [
		        "Tìm kiếm theo tên sách",
		        "Tìm kiếm theo tác giả",
		        "Tìm kiếm theo khoảng giá (Ví dụ: 150000 - 250000)",
		        "Tìm kiếm theo mức giảm giá (Ví dụ: 10%)"
		    ]; // Các cụm từ Placeholder động
		    let textIndex = 0; // Vị trí cụm từ hiện tại
		    let charIndex = 0; // Vị trí ký tự trong cụm từ hiện tại
		    let isDeleting = false; // Xác định trạng thái xóa chữ

		    function typeEffect() {
		        const currentText = placeholderTexts[textIndex];
		        if (!isDeleting) {
		            // Gõ chữ
		            searchInput.setAttribute('placeholder', currentText.substring(0, charIndex + 1));
		            charIndex++;
		            if (charIndex === currentText.length) {
		                // Hoàn thành gõ 1 cụm từ
		                isDeleting = true;
		                setTimeout(typeEffect, 2000); // Tạm dừng trước khi bắt đầu xóa
		                return;
		            }
		        } else {
		            // Xóa chữ
		            searchInput.setAttribute('placeholder', currentText.substring(0, charIndex - 1));
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

		
		
		</script>	
</body>
</html>