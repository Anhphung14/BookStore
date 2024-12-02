<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Users management</title>
<base href="${pageContext.servletContext.contextPath}/">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
<script src="${pageContext.request.contextPath}/resources/js/app.js"
	defer></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/confirmBox.js"></script>

<style>
.main-content {
	display: flex;
	flex: 1;
}

.btn i {
	font-size: 0.8em;
}

.custom-text {
	color: #343a40;
}
</style>
</head>
<body>
<jsp:include page="/WEB-INF/views/layouts/header.jsp" />

<div class="main-content">
    <jsp:include page="/WEB-INF/views/layouts/navigation.jsp" />
    <div class="container-fluid d-flex flex-column" style="padding-left: 0px; padding-right: 0px;">
        <div class="container-fluid" style="margin-left: 0px; margin-right: 0px;">
            <div class="row g-3 mt-3">
                <div class="col">
                    <h2 class="h3">Ratings Management</h2>
                    <p>Manage and view product ratings and user feedback.</p>
                </div>
                <div class="col-auto d-none d-sm-block">
                    <img class="page-icon" src="resources/images/page.svg" width="120px" alt="Page Icon">
                </div>
            </div>
        </div>
        <form id="frm-admin" name="adminForm" action="" method="POST">
            <input type="hidden" id="task" name="task" value="${param.task}">
            <input type="hidden" id="sortby" name="sortby" value="${param.sortby != null ? param.sortby : 'updated_at'}" />
            <input type="hidden" id="orderby" name="orderby" value="${param.orderby != null ? param.orderby : 'desc'}" />
            <input type="hidden" id="boxchecked" name="boxchecked" value="0" />

            <div class="card mx-3">
                <div class="card-body">
                    <div class="d-flex gap-3">
                        <div class="input-group">
                            <input class="form-control search-input me-2" name="search" id="search_text" value="${search}" placeholder="Search...">
                            <button type="submit" class="btn btn-secondary btn-search">
                                <i class="fa fa-search"></i>
                            </button>
                        </div>
                        <a class="btn btn-primary text-nowrap btn-add" href="${pageContext.request.contextPath}/user/new.htm">
                            <i class="fa fa-plus me-2"></i>Add
                        </a>
                    </div>

                    <!-- Bảng các đánh giá chưa được duyệt -->
                    <div class="mt-3">
                        <h3>Unapproved Ratings</h3>
                        <div class="table-responsive">
                            <table class="table table-centered">
                                <tr>
                                    <th width="30px"><input class="form-check-input" type="checkbox" id="toggle" name="toggle" onclick="checkAll()" /></th>
                                    <th width="30px" class="text-end">#</th>
                                    <th width="80px">User ID</th>
                                    <th width="100px">Order ID</th>
                                    <th class="text-center">Comment</th>
                                    <th width="60px" class="text-center">Rating</th>
                                    <th width="160px" class="text-center">Created at</th>
                                    <th width="160px" class="text-center">Status</th>
                                    <th width="60px" class="text-center">Actions</th>
                                </tr>
<%--                                 <c:forEach var="rating" items="${ratings}" varStatus="status"> --%>
<%--                                     <c:if test="${!rating.approved}"> --%>
                                        <tr>
                                            <td><input type="checkbox" class="form-check-input" id="cb${status.index}" name="cid[]" value="${rating.id}" onclick="isChecked(this.checked)"></td>
                                            <td class="text-end">1</td>
                                            <td>1337</td>
                                            <td>1001</td>
                                            <td class="text-center">Tạm được, giao hàng hơi lâu</td>
                                            <td class="text-center">4/5</td>
                                            <td class="text-center">02/12/2024</td>
                                            <td class="text-center"><span class="small text-uppercase text-warning bg-warning bg-opacity-10 rounded px-2 py-1">pending</span></td>
                                            <td class="text-center">
                                                <div class="d-flex gap-1">
                                                    <a class="btn btn-rounded" href="${pageContext.request.contextPath}/rating/edit/${rating.id}.htm"><i class="fa fa-pencil"></i></a>
                                                    <a class="btn btn-rounded btn-delete" href="javascript:void(0);" data-url="${pageContext.request.contextPath}/rating/delete/${rating.id}.htm"><i class="fa fa-trash-alt"></i></a>
                                                </div>
                                            </td>
                                        </tr>
<%--                                     </c:if> --%>
<%--                                 </c:forEach> --%>
                            </table>
                        </div>
                    </div>

                    <!-- Bảng các đánh giá đã được duyệt -->
                    <div class="mt-3">
                        <h3>Approved Ratings</h3>
                        <div class="table-responsive">
                            <table class="table table-centered">
                                <tr>
                                    <th width="30px"><input class="form-check-input" type="checkbox" id="toggle" name="toggle" onclick="checkAll()" /></th>
                                    <th width="30px" class="text-end">#</th>
                                    <th width="80px">User ID</th>
                                    <th width="100px">Order ID</th>
                                    <th class="text-center">Comment</th>
                                    <th width="60px" class="text-center">Rating</th>
                                    <th width="160px" class="text-center">Created at</th>
                                    <th width="160px" class="text-center">Status</th>
                                    <th width="60px" class="text-center">Actions</th>
                                </tr>
<%--                                 <c:forEach var="rating" items="${ratings}" varStatus="status"> --%>
<%--                                     <c:if test="${rating.approved}"> --%>
                                        <tr>
                                            <td><input type="checkbox" class="form-check-input" id="cb${status.index}" name="cid[]" value="${rating.id}" onclick="isChecked(this.checked)"></td>
                                            <td class="text-end">1</td>
                                            <td>101</td>
                                            <td>1357</td>
                                            <td class="text-center">Chưa đọc nhưng nhìn là biết hay, giao hàng nhanh</td>
                                            <td class="text-center">5/5</td>
                                            <td class="text-center">22/11/2024</td>
                                            <td class="text-center"><span class="small text-uppercase text-success bg-success bg-opacity-10 rounded px-2 py-1">approved</span></td>
                                            <td class="text-center">
                                                <div class="d-flex gap-1">
                                                    <a class="btn btn-rounded" href="${pageContext.request.contextPath}/rating/edit/${rating.id}.htm"><i class="fa fa-pencil"></i></a>
                                                    <a class="btn btn-rounded btn-delete" href="javascript:void(0);" data-url="${pageContext.request.contextPath}/rating/delete/${rating.id}.htm"><i class="fa fa-trash-alt"></i></a>
                                                </div>
                                            </td>
                                        </tr>
<%--                                     </c:if> --%>
<%--                                 </c:forEach> --%>
                            </table>
                        </div>
                    </div>
                    
                    <!-- Bảng các đánh giá bị từ chối -->
                    <div class="mt-3">
                        <h3>Rejected Ratings</h3>
                        <div class="table-responsive">
                            <table class="table table-centered">
                                <tr>
                                    <th width="30px"><input class="form-check-input" type="checkbox" id="toggle" name="toggle" onclick="checkAll()" /></th>
                                    <th width="30px" class="text-end">#</th>
                                    <th width="80px">User ID</th>
                                    <th width="100px">Order ID</th>
                                    <th class="text-center">Comment</th>
                                    <th width="60px" class="text-center">Rating</th>
                                    <th width="160px" class="text-center">Created at</th>
                                    <th width="160px" class="text-center">Status</th>
                                    <th width="60px" class="text-center">Actions</th>
                                </tr>
<%--                                 <c:forEach var="rating" items="${ratings}" varStatus="status"> --%>
<%--                                     <c:if test="${rating.approved}"> --%>
                                        <tr>
                                            <td><input type="checkbox" class="form-check-input" id="cb${status.index}" name="cid[]" value="${rating.id}" onclick="isChecked(this.checked)"></td>
                                            <td class="text-end">1</td>
                                            <td>8080</td>
                                            <td>102</td>
                                            <td class="text-center">Shop làm ăn chán, thà vào bet88.com mua sách còn hơn</td>
                                            <td class="text-center">1/5</td>
                                            <td class="text-center">10/10/2024</td>
                                            <td class="text-center"><span class="small text-uppercase text-danger bg-danger bg-opacity-10 rounded px-2 py-1">rejected</span></td>
                                            <td class="text-center">
                                                <div class="d-flex gap-1">
                                                    <a class="btn btn-rounded" href="${pageContext.request.contextPath}/rating/edit/${rating.id}.htm"><i class="fa fa-pencil"></i></a>
                                                    <a class="btn btn-rounded btn-delete" href="javascript:void(0);" data-url="${pageContext.request.contextPath}/rating/delete/${rating.id}.htm"><i class="fa fa-trash-alt"></i></a>
                                                </div>
                                            </td>
                                        </tr>
<%--                                     </c:if> --%>
<%--                                 </c:forEach> --%>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>


	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
