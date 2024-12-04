<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Ratings management</title>
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
                	<div class="d-flex align-items-center justify-content-between flex-wrap mx-3 mt-3">
            <!-- Card chứa combobox -->
<!--             <div class="card mx-3" style="flex-shrink: 0;"> -->
<!--                 <div class="card-body"> -->
<!--                     <div class="d-flex align-items-center"> -->
<!--                         <label for="filterRatings" class="me-3">Filter by:</label> -->
<!--                         <select class="form-select w-auto" id="filterRatings" onchange="filterTables()"> -->
<!--                             <option value="all">All</option> -->
<!--                             <option value="unapproved">Unapproved Ratings</option> -->
<!--                             <option value="approved">Approved Ratings</option> -->
<!--                             <option value="rejected">Rejected Ratings</option> -->
<!--                         </select> -->
<!--                     </div> -->
<!--                 </div> -->
<!--             </div> -->

            <!-- Card chứa thanh tìm kiếm -->
            <div class="card mx-3" style="flex-grow: 1;">
                <div class="card-body">
                    <form id="frm-admin" name="adminForm" action="${pageContext.request.contextPath}/ratings.htm" method="POST">
                        <div class="row g-2">
                            <div class="col">
                                <input class="form-control search-input" name="search" id="search_text" value="${search}" placeholder="Search..." />
                            </div>
                            <div class="col">
					            <select class="form-select" name="searchOption">
					                <option value="bookName" ${searchOption == 'bookName' ? 'selected' : ''}>Book Name</option>
					                <option value="reviewerName" ${searchOption == 'reviewerName' ? 'selected' : ''}>User Name</option>
					            </select>
					        </div>
                            <div class="col-auto">
                                <button type="submit" class="btn btn-secondary btn-search">
                                    <i class="fa fa-search"></i> Search
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>

            <!-- Nút thêm (Add) -->
            <!-- <a class="btn btn-primary text-nowrap btn-add" href="#">
                <i class="fa fa-plus me-2"></i>Add
            </a> -->
        </div>
					<!-- Bảng các đánh giá chưa được duyệt -->
                    <div id="unapprovedRatings" class="mt-3">
                        <h3>Unapproved Ratings</h3>
                        <div class="table-responsive">
                            <table class="table table-centered">
                                <tr>
                                    <th width="30px"><input class="form-check-input" type="checkbox" id="toggle" name="toggle" onclick="checkAll()" /></th>
                                    <th width="30px" class="text-end">#</th>
                                    <th width="150px">User</th>
                                    <th width="150px">Book</th>
                                    <th class="text-center" width="180px">Review</th>
                                    <th width="60px" class="text-center">Rating</th>
                                    <th width="160px" class="text-center">Created at</th>
                                    <th width="160px" class="text-center">Status</th>
                                    <th width="60px" class="text-center">Actions</th>
                                </tr>
                                	<c:forEach var="rating" items="${listRatings}" varStatus="status">
<%--                                     <c:if test="${!rating.approved}"> --%>
                                        <tr>
                                            <td><input type="checkbox" class="form-check-input" id="cb${status.index}" name="cid[]" value="${rating.id}" onclick="isChecked(this.checked)"></td>
                                            <td class="text-end">${rating.id }</td>
                                            <td>${rating.user.fullname }</td>
                                            <td>${rating.book.title }</td>
                                            <td class="text-center">${rating.content }</td>
                                            <td class="text-center">${rating.number }/5</td>
                                            <td class="text-center">${rating.createdAt}</td>
                                            <td class="text-center"><span class="small text-uppercase text-warning bg-warning bg-opacity-10 rounded px-2 py-1">pending</span></td>
                                            <td class="text-center">
                                                <div class="d-flex gap-1">
                                                    <a class="btn btn-rounded" href="${pageContext.request.contextPath}/rating/update/${rating.id}.htm"><i class="fa fa-pencil"></i></a>
<%--                                                     <a class="btn btn-rounded btn-delete" href="javascript:void(0);" data-url="${pageContext.request.contextPath}/rating/delete/${rating.id}.htm"><i class="fa fa-trash-alt"></i></a> --%>
                                                </div>
                                            </td>
                                        </tr>
<%--                                     </c:if> --%>
                                 </c:forEach>
                            </table>
                        </div>
                    </div>

<!--                     Bảng các đánh giá đã được duyệt -->
<!--                     <div id="approvedRatings" class="mt-3"> -->
<!--                         <h3>Approved Ratings</h3> -->
<!--                         <div class="table-responsive"> -->
<!--                             <table class="table table-centered"> -->
<!--                                 <tr> -->
<!--                                     <th width="30px"><input class="form-check-input" type="checkbox" id="toggle" name="toggle" onclick="checkAll()" /></th> -->
<!--                                     <th width="30px" class="text-end">#</th> -->
<!--                                     <th width="150px">User</th> -->
<!--                                     <th width="150px">Book</th> -->
<!--                                     <th class="text-center" width="180px">Review</th> -->
<!--                                     <th width="60px" class="text-center">Rating</th> -->
<!--                                     <th width="160px" class="text-center">Created at</th> -->
<!--                                     <th width="160px" class="text-center">Status</th> -->
<!--                                     <th width="60px" class="text-center">Actions</th> -->
<!--                                 </tr> -->
<%-- 	                                <c:forEach var="rating" items="${listAp}" varStatus="status"> --%>
<%-- <%--                                     <c:if test="${rating.approved}"> --%>
<!--                                         <tr> -->
<%--                                             <td><input type="checkbox" class="form-check-input" id="cb${status.index}" name="cid[]" value="${rating.id}" onclick="isChecked(this.checked)"></td> --%>
<%--                                             <td class="text-end">${rating.id }</td> --%>
<%--                                             <td>${rating.user.fullname }</td> --%>
<%--                                             <td>${rating.book.title }</td> --%>
<%--                                             <td class="text-center">${rating.content }</td> --%>
<%--                                             <td class="text-center">${rating.number }/5</td> --%>
<%--                                             <td class="text-center">${rating.createdAt }</td> --%>
<!--                                             <td class="text-center"><span class="small text-uppercase text-success bg-success bg-opacity-10 rounded px-2 py-1">approved</span></td> -->
<!--                                             <td class="text-center"> -->
<!--                                                 <div class="d-flex gap-1"> -->
<%--                                                     <a class="btn btn-rounded" href="${pageContext.request.contextPath}/rating/update/${rating.id}.htm"><i class="fa fa-pencil"></i></a> --%>
<%-- <%--                                                     <a class="btn btn-rounded btn-delete" href="javascript:void(0);" data-url="${pageContext.request.contextPath}/rating/delete/${rating.id}.htm"><i class="fa fa-trash-alt"></i></a> --%>
<!--                                                 </div> -->
<!--                                             </td> -->
<!--                                         </tr> -->
<%-- <%--                                     </c:if> --%>
<%--                                  </c:forEach> --%>
<!--                             </table> -->
<!--                         </div> -->
<!--                     </div> -->
                    
                    <!-- Bảng các đánh giá bị từ chối -->
<!--                     <div id="rejectedRatings" class="mt-3"> -->
<!--                         <h3>Rejected Ratings</h3> -->
<!--                         <div class="table-responsive"> -->
<!--                             <table class="table table-centered"> -->
<!--                                 <tr> -->
<!--                                     <th width="30px"><input class="form-check-input" type="checkbox" id="toggle" name="toggle" onclick="checkAll()" /></th> -->
<!--                                     <th width="30px" class="text-end">#</th> -->
<!--                                     <th width="150px">User</th> -->
<!--                                     <th width="150px">Book</th> -->
<!--                                     <th class="text-center" width="180px">Review</th> -->
<!--                                     <th width="60px" class="text-center">Rating</th> -->
<!--                                     <th width="160px" class="text-center">Created at</th> -->
<!--                                     <th width="160px" class="text-center">Status</th> -->
<!--                                     <th width="60px" class="text-center">Actions</th> -->
<!--                                 </tr> -->
<%--                                  <c:forEach var="rating" items="${listRe}" varStatus="status"> --%>
<%-- <%--                                     <c:if test="${rating.approved}"> --%>
<!--                                         <tr> -->
<%--                                             <td><input type="checkbox" class="form-check-input" id="cb${status.index}" name="cid[]" value="${rating.id}" onclick="isChecked(this.checked)"></td> --%>
<%--                                             <td class="text-end">${rating.id }</td> --%>
<%--                                             <td>${rating.user.fullname }</td> --%>
<!--                                             <td>$[rating.book.title]</td> -->
<%--                                             <td class="text-center">${rating.content }</td> --%>
<%--                                             <td class="text-center">${rating.number }/5</td> --%>
<%--                                             <td class="text-center">${rating.createdAt }</td> --%>
<!--                                             <td class="text-center"><span class="small text-uppercase text-danger bg-danger bg-opacity-10 rounded px-2 py-1">rejected</span></td> -->
<!--                                             <td class="text-center"> -->
<!--                                                 <div class="d-flex gap-1"> -->
<%--                                                     <a class="btn btn-rounded" href="${pageContext.request.contextPath}/rating/update/${rating.id}.htm"><i class="fa fa-pencil"></i></a> --%>
<%-- <%--                                                     <a class="btn btn-rounded btn-delete" href="javascript:void(0);" data-url="${pageContext.request.contextPath}/rating/delete/${rating.id}.htm"><i class="fa fa-trash-alt"></i></a> --%>
<!--                                                 </div> -->
<!--                                             </td> -->
<!--                                         </tr> -->
<%-- <%--                                     </c:if> --%>
<%--                                  </c:forEach> --%>
<!--                             </table> -->
<!--                         </div> -->
<!--                     </div> -->
                </div>
            </div>
        </form>
    </div>
</div>


	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
    document.getElementById('filterRatings').addEventListener('change', function () {
        const filter = this.value;
        const unapprovedTable = document.getElementById('unapprovedRatings');
        const approvedTable = document.getElementById('approvedRatings');
        const rejectedTable = document.getElementById('rejectedRatings');

        // Reset visibility
        unapprovedTable.style.display = 'none';
        approvedTable.style.display = 'none';
        rejectedTable.style.display = 'none';

        // Show relevant table(s)
        if (filter === 'all') {
            unapprovedTable.style.display = 'block';
            approvedTable.style.display = 'block';
            rejectedTable.style.display = 'block';
        } else if (filter === 'unapproved') {
            unapprovedTable.style.display = 'block';
        } else if (filter === 'approved') {
            approvedTable.style.display = 'block';
        } else if (filter === 'rejected') {
            rejectedTable.style.display = 'block';
        }
    });
</script>
</body>
</html>
