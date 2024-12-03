<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <div class="modal-header">
    <h3 class="modal-title" id="orderDetailsModalLabel">Order #${order.id} Details</h3>
</div>
<div class="modal-body">
	<p><strong>Status:</strong>
		<c:choose>
                    <c:when test="${order.status == 1}">
                        <span class="badge badge-success" style="background-color: #28a745;">Delivered</span>
                    </c:when>
                    <c:when test="${order.status == 2}">
                        <span class="badge badge-warning" style="background-color: #ffc107;">Pending</span>
                    </c:when>
                    <c:when test="${order.status == 3}">
                        <span class="badge badge-danger" style="background-color: #dc3545;">Canceled</span>
                    </c:when>
                </c:choose>
      </p>
    <table>
        <thead>
            <tr>
                <th>Book</th>
                <th>Price</th>
                <th>Quantity</th>
                <th>Thành tiền</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="orderDetail" items="${orderDetails}">
                <tr>
                    <td>${orderDetail.book.title}</td>
                    <td>${orderDetail.book.price} VND</td>
                    <td>${orderDetail.quantity}</td>
                    <td>${orderDetail.book.price * orderDetail.quantity} VND</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <h3>Thông tin thanh toán</h3>
    <p><strong>Mã đơn hàng:</strong> ${order.id}</p>
    <p><strong>Khách hàng:</strong> ${order.user.fullname}</p>
    <p><strong>Số điện thoại:</strong> ${order.user.phone}</p>
    <p><strong>Thời gian đặt hàng:</strong> ${order.createdAt}</p>
    <p><strong>Địa chỉ giao hàng:</strong> ${order.shippingAddress}</p>

    <table>
        <tr>
            <td>Tổng tiền đơn hàng</td>
            <td>
                <c:forEach var="orderDetail" items="${orderDetails}">
                    ${orderDetail.book.price * orderDetail.quantity} VND
                </c:forEach>
            </td>
        </tr>
        <tr>
            <td>Phí giao hàng</td>
            <td>30,000 VND</td>
        </tr>
        <tr class="total">
            <td>Tổng thanh toán</td>
            <td>${order.totalPrice} VND</td>
        </tr>
    </table>
</div>
