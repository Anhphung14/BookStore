<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <div class="modal-header">
    <h3 class="modal-title" id="orderDetailsModalLabel">Order #${order.id} Details</h3>
</div>
<div class="modal-body">
	<p><strong>Status:</strong>
				<c:choose>
				    <c:when test="${order.orderStatus == 'Hoàn thành'}">
				        <span class="badge badge-success" style="background-color: #28a745;">Delivered</span>
				    </c:when>
				    <c:when test="${order.orderStatus == 'Chờ xác nhận'}">
				        <span class="badge badge-warning" style="background-color: #ffc107;">Pending</span>
				    </c:when>
				    <c:when test="${order.orderStatus == 'Hủy Đơn Hàng'}">
				        <span class="badge badge-danger" style="background-color: #dc3545;">Canceled</span>
				    </c:when>
				    <c:when test="${order.orderStatus == 'Xác nhận đơn hàng'}">
				        <span class="badge badge-primary" style="background-color: #007bff;">Confirmed</span>
				    </c:when>
				    <c:when test="${order.orderStatus == 'Đang giao hàng'}">
				        <span class="badge badge-info" style="background-color: #17a2b8;">Shipping</span>
				    </c:when>
				    <c:otherwise>
				        <span class="badge badge-danger" style="background-color: #dc3545;">Canceled</span>
				    </c:otherwise>
				</c:choose>
		
      </p>
    <table>
        <thead>
            <tr>
                <th>Book</th>
                <th>Price</th>
                <th>Quantity</th>
                <th>Subtotal</th>
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

    <h3>Payment Information</h3>
	<p><strong>Order ID:</strong> ${order.id}</p>
	<p><strong>Customer:</strong> ${order.user.fullname}</p>
	<p><strong>Phone Number:</strong> ${order.user.phone}</p>
	<p><strong>Order Date:</strong> ${order.createdAt}</p>
	<p><strong>Shipping Address:</strong> ${order.shippingAddress}</p>
	<p><strong>Payment Method:</strong> ${order.paymentMethod}</p>
    
    <table>
	    <tr>
	        <td>Order Total</td>
	        <c:set var="orderTotal" value="0" />
			<c:forEach var="orderDetail" items="${orderDetails}">
			    <c:set var="orderTotal" value="${orderTotal + (orderDetail.book.price * orderDetail.quantity)}" />
			</c:forEach>
	        <td>
	                ${orderTotal} VND 
	        </td>
	    </tr>
	    
	    <tr>
	        <td>Discounts</td>
	        <td>
	            <c:if test="${not empty orderDiscounts}">
	                <c:forEach var="orderDiscount" items="${orderDiscounts}">
	                    <p>${orderDiscount.discount_id.code}: -${orderDiscount.discount_id.discountValue} VND</p>
	                </c:forEach>
	            </c:if>
	        </td>
	    </tr>
	    <tr class="total">
	        <td>Total Payment</td>
	        <td>${order.totalPrice} VND</td>
	    </tr>
	</table>

</div>
