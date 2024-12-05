<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="modal-header">
	<h3 class="modal-title" id="orderDetailsModalLabel">Chi tiết đơn hàng</h3>
</div>
<div class="modal-body">
	<p>
		<strong>Status:</strong>
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
				<th>Sản phẩm</th>
				<th>Giá</th>
				<th>Số lượng</th>
				<th>Tổng cộng</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="orderDetail" items="${orderDetails}">
				<tr>
					<td>${orderDetail.book.title}</td>
					<td><fmt:formatNumber value="${orderDetail.book.price}" type="currency"
														currencySymbol="VND" /></td>
					<td>${orderDetail.quantity}</td>
					<td><fmt:formatNumber value="${orderDetail.book.price * orderDetail.quantity}" type="currency"
														currencySymbol="VND" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<h3>Thông tin thanh toán</h3>
	<p>
		<strong>Mã đơn hàng:</strong> ${order.id}
	</p>
	<p>
		<strong>Khách hàng:</strong> ${order.customerName}
	</p>
	<p>
		<strong>Số điện thoại:</strong> ${order.customerPhone}
	</p>
	<p>
		<strong>Ngày đặt hàng:</strong> ${order.createdAt}
	</p>
	<p>
		<strong>Địa chỉ giao hàng:</strong> ${order.shippingAddress}
	</p>
	<p>
		<strong>Phương thức thanh toán:</strong> ${order.paymentMethod}
	</p>


	<table>
		<tr>
			<td>Tổng tiền</td>
			<c:set var="orderTotal" value="0" />
			<c:forEach var="orderDetail" items="${orderDetails}">
				<c:set var="orderTotal"
					value="${orderTotal + (orderDetail.book.price * orderDetail.quantity)}" />
			</c:forEach>
			<td><fmt:formatNumber value="${orderTotal}" type="currency"
														currencySymbol="VND" /></td>
		</tr>

		<tr>
			<td>Giảm giá</td>
			<td><c:if test="${not empty orderDiscounts}">
					<c:forEach var="orderDiscount" items="${orderDiscounts}">
						<p>${orderDiscount.discount_id.code}:
							-${orderDiscount.discount_id.discountValue} VND</p>
					</c:forEach>
				</c:if></td>
		</tr>
		<tr class="total">
			<td>Tổng thanh toán</td>
			<td><fmt:formatNumber value="${order.totalPrice}" type="currency"
														currencySymbol="VND" /></td>
		</tr>
	</table>

</div>
