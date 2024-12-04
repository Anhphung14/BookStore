<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Danh Sách Đánh Giá</title>
    <!-- Link Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/assets/css/client/main.css" />
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
<div>
    <h3>Đánh giá cho sản phẩm ID: ${bookId}</h3>
    <c:if test="${empty reviews}">
        <p>Chưa có đánh giá nào cho sản phẩm này.</p>
    </c:if>
    <c:forEach var="review" items="${reviews}">
        <div class="review">
            <div class="product-info">
                <p style="font-size: 1.4rem;"><strong style="color: white;">Mã đơn hàng:</strong> ${review.order.id}</p> <!-- Mã đơn hàng -->
                <p style="font-size: 1.4rem;"><strong style="color: white;">Sách:</strong> 
                    <a>${review.book.title}</a>
                </p>
                <p style="font-size: 1.4rem;"><strong style="color: white;">Thời gian tạo:</strong> 
                    <fmt:formatDate value="${review.createdAt}" pattern="dd/MM/yyyy HH:mm:ss"/> <!-- Thời gian tạo -->
                </p>
            </div>
            <div class="rating">
                <!-- Hiển thị số sao -->
                <c:forEach begin="1" end="${review.number}">
                    ⭐
                </c:forEach>
                <c:forEach begin="${review.number + 1}" end="5">
                    ☆
                </c:forEach>
            </div>
            <p style="font-size: 1.4rem;">${review.content}</p> <!-- Nội dung đánh giá -->
            <c:if test="${not empty review.response}">
                <div class="admin-response">
                    <h4>Phản hồi từ Admin:</h4>
                    <p style="font-size: 1.4rem;">${review.response}</p>
                </div>
            </c:if>
        </div>
        <hr />
    </c:forEach>
</div>

    <!-- Link Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
