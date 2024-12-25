<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <base href="${pageContext.servletContext.contextPath}/">
    <!--  This file has been downloaded from bootdey.com @bootdey on twitter -->
    <!--  All snippets are MIT license http://bootdey.com/license -->
    <title>Đánh giá của bạn</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@4.1.1/dist/css/bootstrap.min.css" rel="stylesheet">
    
<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/assets/css/client/styleAccount.css">

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
	<script src="resources/assets/js/client/vendor/modernizr-2.8.3-respond-1.4.2.min.js"></script>
	
	<style>
        .product-divider {
            border-top: 3px solid #007bff;  /* Đổi màu sắc và độ dày của đường phân cách */
            margin: 20px 0;  /* Thêm khoảng cách trên và dưới đường phân cách */
        }
        .review {
            border: 1px solid #444;
            border-radius: 5px;
            padding: 15px;
            margin-bottom: 20px;
            background: #4b4b4b;
        }
        .review h3 {
            margin: 0 0 10px 0;
            color: #ffd700; /* Màu vàng cho tên người dùng */
        }
        .review p {
            color: #e0e0e0;
        }
        .rating {
            color: #ffd700;
            margin-bottom: 10px;
        }
        .admin-response {
            margin-top: 10px;
            padding: 10px;
            background: #5a5a5a;
            border-left: 4px solid #ffd700;
        }
        .admin-response h4 {
            margin: 0 0 5px 0;
            color: #ffd700;
        }
    </style>
</head>
<body>
<%@ include file="../layouts/header.jsp"%>
<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet">
<div class="container mb-4">
    <div class="row">
        <div class="col-lg-4 pb-5">
            <!-- Account Sidebar-->
            <div class="author-card pb-3">
                <div class="author-card-cover" style="background-image: url(https://bootdey.com/img/Content/flores-amarillas-wallpaper.jpeg);"></div>
                <div class="author-card-profile">
                    <div class="author-card-avatar"><img src="${user.avatar}" alt="${user.fullname}">
                    </div>
                    <div class="author-card-details">
                        <h5 class="author-card-name text-lg">${user.fullname}</h5><span class="author-card-position">Tham gia ${user.created_at}</span>
                    </div>
                </div>
            </div>
            <div class="wizard">
                <nav class="list-group list-group-flush">
                    <a class="list-group-item" href="account/account_orders">
                        <div class="d-flex justify-content-between align-items-center">
                                <div class="d-inline-block font-weight-medium text-uppercase">Danh sách đơn hàng</div>
                        </div>
                    </a>
                    <a class="list-group-item" href="account/profile_settings">Chỉnh sửa thông tin</a>
                  	<a class="list-group-item" href="account/my_ratings">Đánh giá của bạn</a>
                </nav>
            </div>
        </div>
        <!-- Reviews -->
        <div class="col-lg-8">
            <h2 class="mb-4">ĐÁNH GIÁ CỦA BẠN</h2>
			<c:forEach var="review" items="${reviews}">
		        <div class="review">
		            <div class="product-info">
		            	<p>Mã đơn hàng: ${review.order.uuid}</p> <!-- Hiển thị mã đơn hàng -->
		                <p>Book: <a href="productdetail/${review.book.id}" target="_blank">${review.book.title}</a></p>
		                <p>Thời gian tạo: 
		                    <fmt:formatDate value="${review.createdAt}" pattern="dd/MM/yyyy HH:mm:ss"/> <!-- Hiển thị thời gian -->
		                </p>
		            </div>
		            <div class="rating">
		                <c:forEach begin="1" end="${review.number}">
		                    ⭐
		                </c:forEach>
		                <c:forEach begin="${review.number + 1}" end="5">
		                    ☆
		                </c:forEach>
		            </div>
		            <p>${review.content}</p>
		            <c:if test="${not empty review.response}">
		                <div class="admin-response">
		                    <h4>Phản hồi từ Admin:</h4>
		                    <p>${review.response}</p>
		                </div>
		            </c:if>
		            
		        	<div class="review-status">
            <strong style="color: white;">Trạng thái: </strong>
            <c:choose>
                <c:when test="${review.status == 0}">
                    <span class="badge bg-secondary" >Chưa duyệt</span> <!-- Status 0 -->
                </c:when>
                <c:when test="${review.status == 1}">
                    <span class="badge bg-success">Đã duyệt</span> <!-- Status 1 -->
                </c:when>
                <c:when test="${review.status == 2}">
                    <span class="badge bg-danger">Bị từ chối</span> <!-- Status 3 -->
                </c:when>
                <c:otherwise>
                    <span class="badge bg-secondary">Trạng thái không xác định</span> <!-- For unexpected status values -->
                </c:otherwise>
            </c:choose>
        </div>
		        
		        </div>
		    </c:forEach>
            
        <!-- End Reviews -->
        
    </div>
<%@ include file="../layouts/footer.jsp"%>
</div>

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
	
<script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.1.1/dist/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript">
	
</script>
<script>
    document.addEventListener("DOMContentLoaded", function () {
        // Lấy tất cả các liên kết trong danh sách
        const navLinks = document.querySelectorAll(".list-group-item");
    
        // Lấy đường dẫn hiện tại (chỉ phần tên file)
        const currentPath = window.location.pathname.split("/").pop();
    
        // Lặp qua các liên kết để kích hoạt "active"
        navLinks.forEach(function (link) {
            // Kiểm tra nếu href của link khớp với URL hiện tại
            if (link.getAttribute("href").includes(currentPath)) {
                link.classList.add("active");
            } else {
                link.classList.remove("active");
            }
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