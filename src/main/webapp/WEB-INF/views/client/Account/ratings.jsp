<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
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
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="resources/assets/js/client/vendor/modernizr-2.8.3-respond-1.4.2.min.js"></script>
    
    
    
</head>
<body>

	<div id="tg-wrapper" class="tg-wrapper tg-haslayout">
		<!--************************************
				Header Start
		*************************************-->
		<%@ include file="../layouts/header.jsp"%>
		<!--************************************
				Header End
		*************************************-->
		<!--************************************
				Inner Banner Start
		*************************************-->
		<div class="tg-innerbanner tg-haslayout tg-parallax tg-bginnerbanner" data-z-index="-100" data-appear-top-offset="600" data-parallax="scroll" data-image-src="${pageContext.servletContext.contextPath}/resources/images/client//parallax/bgparallax-07.jpg">
			<div class="container">
				<div class="row">
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
						<div class="tg-innerbannercontent">
							<h1>Đánh giá</h1>
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
		<div class="container my-5">
        <h1 class="text-center mb-4">Đánh Giá Đơn Hàng #${orderId }</h1>

        <form id="ratingForm" action="account/submitRatings.htm" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <!-- Sản phẩm 1 -->
            <c:forEach var="orderDetail" items="${orderDetails}">
			    <div class="border mb-4 p-3">
			        <h4><a href="client/productdetail/${orderDetail.book.id}.htm" target="_blank" class="text-decoration-none" style="font-size: 2.2rem;">${orderDetail.book.title}</a></h4>
			        <div class="row">
			            <div class="col-md-2">
			                <img src="${orderDetail.book.thumbnail }" alt="${orderDetail.book.title}" class="img-fluid">
			            </div>
			            <div class="col-md-10">
			                <p><strong style="font-size: 1.5rem;">Số lượng: ${orderDetail.quantity}</strong></p>
			                <div class="mb-2">
			                    <strong style="font-size: 1.5rem;">Tên sản phẩm:</strong>
			                    <input type="text" readonly class="form-control" value="${orderDetail.book.title}" id="productName${orderDetail.book.id}">
			                </div>
			                <div class="my-2">
							    <strong style="font-size: 1.5rem;">Đánh giá:</strong>
							    <div class="btn-group" role="group" aria-label="Rating group" style="font-size: 1.5rem;">
							        <input type="radio" class="btn-check" name="rating${orderDetail.order.id}_${orderDetail.book.id}" id="rating1_${orderDetail.order.id}_${orderDetail.book.id}" value="1" required>
							        <label class="btn btn-outline-dark" for="rating1_${orderDetail.order.id}_${orderDetail.book.id}" style="font-size: 1.5rem;">1 ⭐</label>
							
							        <input type="radio" class="btn-check" name="rating${orderDetail.order.id}_${orderDetail.book.id}" id="rating2_${orderDetail.order.id}_${orderDetail.book.id}" value="2">
							        <label class="btn btn-outline-dark" for="rating2_${orderDetail.order.id}_${orderDetail.book.id}" style="font-size: 1.5rem;">2 ⭐</label>
							
							        <input type="radio" class="btn-check" name="rating${orderDetail.order.id}_${orderDetail.book.id}" id="rating3_${orderDetail.order.id}_${orderDetail.book.id}" value="3">
							        <label class="btn btn-outline-dark" for="rating3_${orderDetail.order.id}_${orderDetail.book.id}" style="font-size: 1.5rem;">3 ⭐</label>
							
							        <input type="radio" class="btn-check" name="rating${orderDetail.order.id}_${orderDetail.book.id}" id="rating4_${orderDetail.order.id}_${orderDetail.book.id}" value="4">
							        <label class="btn btn-outline-dark" for="rating4_${orderDetail.order.id}_${orderDetail.book.id}" style="font-size: 1.5rem;">4 ⭐</label>
							
							        <input type="radio" class="btn-check" name="rating${orderDetail.order.id}_${orderDetail.book.id}" id="rating5_${orderDetail.order.id}_${orderDetail.book.id}" value="5">
							        <label class="btn btn-outline-dark" for="rating5_${orderDetail.order.id}_${orderDetail.book.id}" style="font-size: 1.5rem;">5 ⭐</label>
							    </div>
							</div>
			                <div class="my-2">
			                    <strong style="font-size: 1.5rem;">Đánh giá:</strong>
			                    <textarea class="form-control" name="review${orderDetail.order.id}_${orderDetail.book.id}" rows="3" placeholder="Nhập đánh giá của bạn..." style="font-size: 1.5rem;" required="required"></textarea>
			                </div>
			            </div>
			        </div>
			    </div>
			    <hr>
			</c:forEach>
			<c:if test="${not empty errorRating}">
				<div class="alert alert-danger">${errorRating}</div>
			</c:if>
            <!-- Nút Submit -->
            <div class="text-center">
                <button type="submit" class="btn btn-primary" style="font-size: 1.5rem;">Gửi Đánh Giá</button>
            </div>
        </form>
    </div>

    <!-- Thêm link đến Bootstrap JS và Popper.js -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.min.js"></script>
		<!-- 
				Main End
		 *************************************-->
		<!--************************************
				Footer Start
		*************************************-->
		<%@ include file="../layouts/footer.jsp"%>
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