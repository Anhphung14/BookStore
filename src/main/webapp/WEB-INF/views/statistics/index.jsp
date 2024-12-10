<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>BookStore - Dashboard</title>
<base href="${pageContext.servletContext.contextPath}/admin1337/">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
<link
	href="https://fonts.googleapis.com/css2?family=Roboto:wght@500;700&display=swap"
	rel="stylesheet">

<!-- Custom CSS -->
<style>
.main-content {
	display: flex;
	flex: 1;
}

.card {
	margin-top: 20px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
	border-radius: 12px;
}

.card-header {
	font-size: 1.2rem;
	font-weight: bold;
}

.custom-text {
	color: #343a40;
}

.table-hover tbody tr:hover {
	background-color: rgba(0, 0, 0, 0.05);
}

.card-body h2 {
	font-size: 2.5rem;
}

.page-icon {
	max-width: 100px;
	margin: auto;
	display: block;
}

canvas {
	max-height: 400px;
}

.page-title {
	color: #343a40;
	font-size: 1rem;
	font-weight: 600;
}

.page-nav {
	position: sticky;
	top: 60px;
	background-color: #ffffff;
	border-bottom: 1px solid #dee2e6;
}
</style>

</head>
<body>
	<jsp:include page="/WEB-INF/views/layouts/header.jsp" />

	<div class="main-content">
		<jsp:include page="/WEB-INF/views/layouts/navigation.jsp" />
		<div class="container-fluid d-flex flex-column p-0">
			<div class="container-fluid"
				style="margin-left: 0px; margin-right: 0px;">
				<div class="page-nav p-2">
					<h1 class="page-title">Welcome back, Book Store!</h1>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="card text-white" style="background-color: #5f6368;">
							<div class="card-body">
								<h5 class="card-title">Tổng số người dùng</h5>
								<h2 class="card-text">${userCount}</h2>
							</div>
						</div>
					</div>

					<div class="col-md-4">
						<div class="card text-white" style="background-color: #66b3a6;">
							<div class="card-body">
								<h5 class="card-title">Tổng số đơn hàng</h5>
								<h2 class="card-text">${orderCount}</h2>
							</div>
						</div>
					</div>

					<div class="col-md-4">
						<div class="card text-white" style="background-color: #f0ad4e;">
							<div class="card-body">
								<h5 class="card-title">Doanh thu tháng này</h5>
								<h2 class="card-text">${totalAmountForStatus3}</h2>
							</div>
						</div>
					</div>
				</div>

				<div class="row mt-4">
					<div class="col-md-6">
						<div class="card" style="height: 400px;">
							<div class="card-header text-center">Biểu đồ doanh thu</div>
							<div class="card-body">
								<canvas id="revenueChart"></canvas>
							</div>
						</div>
					</div>

					<div class="col-md-6">
						<div class="card" style="height: 400px; overflow-y: auto;">
							<div class="card-header text-center">Thống kê đơn hàng</div>
							<div class="card-body">
								<table class="table table-striped table-hover text-center">
									<thead>
										<tr>
											<th>ID</th>
											<th>Tên khách hàng</th>
											<th>Ngày đặt hàng</th>
											<th>Tổng tiền</th>
											<th>Trạng thái</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="order" items="${orders}">
											<tr>
												<td>${order.id}</td>
												<td>${order.user.fullname}</td>
												<td><fmt:formatDate value="${order.createdAt}"
														pattern="dd/MM/yyyy HH:mm" /></td>
												<td><fmt:formatNumber value="${order.totalPrice}"
														type="currency" currencySymbol="đ" /></td>

												<td><c:choose>
														<c:when test="${order.orderStatus == 'Chờ xác nhận'}">
															<a class="btn btn-rounded" href="${pageContext.servletContext.contextPath}/admin1337/orders.htm"> <i class="fas fa-clock text-warning"></i> Chờ xác
																nhận
															</a>
														</c:when>
														<c:when test="${order.orderStatus == 'Xác nhận đơn hàng'}">
															<i class="fas fa-check-circle text-primary"></i> Đã xác nhận
        												</c:when>
														<c:when test="${order.orderStatus == 'Đang giao hàng'}">
															<i class="fas fa-truck text-info"></i> Vận chuyển
       													</c:when>
														<c:when test="${order.orderStatus == 'Hoàn thành'}">
															<i class="fas fa-check-circle text-success"></i> Hoàn thành
        												</c:when>
														<c:when test="${order.orderStatus == 'Huỷ đơn hàng'}">
															<i class="fas fa-times-circle text-danger"></i> Huỷ đơn hàng
														</c:when>
													</c:choose></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>

			</div>
		</div>
	</div>

	<!-- Bootstrap JS -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

	<!-- Chart.js -->
	<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
	<script>
		// Chuyển đổi dữ liệu từ JSP sang JSON
		const months = JSON.parse('${months}');
		const revenues = JSON.parse('${revenues}');

		const ctx = document.getElementById('revenueChart').getContext('2d');
		const revenueChart = new Chart(ctx, {
			type : 'line',
			data : {
				labels : months,
				datasets : [ {
					label : 'Doanh thu (VND)',
					data : revenues,
					borderColor : 'rgba(75, 192, 192, 1)',
					backgroundColor : 'rgba(75, 192, 192, 0.2)',
					borderWidth : 1
				} ]
			},
			options : {
				responsive : true,
				maintainAspectRatio : false,
				plugins : {
					legend : {
						position : 'top'
					}
				},
				layout : {
					padding : 10
				}
			}
		});
	</script>




</body>
</html>
