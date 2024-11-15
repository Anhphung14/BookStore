<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><c:choose>
    <c:when test="${task == 'edit'}">Edit User</c:when>
    <c:otherwise>New User</c:otherwise>
</c:choose></title>

<base href="${pageContext.servletContext.contextPath}/">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

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

    <div class="container-fluid" style="margin-left: 0px; margin-right: 0px;">

        <div class="row g-3 mt-3">
            <div class="col">
                <h2 class="h3">
                    <c:choose>
                        <c:when test="${task == 'edit'}">Edit User</c:when>
                        <c:otherwise>New User</c:otherwise>
                    </c:choose>
                </h2>
                <p>Manage users, roles, permissions, and profile.</p>
            </div>
            <div class="col-auto d-none d-sm-block">
                <img class="page-icon" src="resources/images/page.svg" width="120px" alt="Page Icon">
            </div>
        </div>

        <form id="frm-admin" name="adminForm" action="<c:url value='/users/save' />" method="POST">
            <input type="hidden" id="task" name="task" value="${task}">
            <div class="card mt-3">
                <div class="card-body">
                    <h6 class="small text-muted">GENERAL INFORMATION</h6>

                    <div class="form-floating mt-3">
                        <input class="form-control" id="fullname" name="fullname" value="${user.fullname}" required> 
                        <label class="form-label" for="fullname">Name <span class="text-danger">*</span></label>
                    </div>

                    <div class="form-floating mt-3">
                        <input class="form-control" id="email" name="email" value="${user.email}" required> 
                        <label class="form-label" for="email">Email <span class="text-danger">*</span></label>
                    </div>

                    <div class="form-floating mt-3">
                        <input class="form-control" id="phone" name="phone" value="${user.phone}"> 
                        <label class="form-label" for="phone">Phone</label>
                    </div>
                </div>
            </div>

            <div class="mt-3">
                <button class="btn btn-primary btn-save" type="submit">
                    <c:choose>
                        <c:when test="${task == 'edit'}">Save Changes</c:when>
                        <c:otherwise>Save</c:otherwise>
                    </c:choose>
                </button>
                <a href="<c:url value='/users' />" class="btn btn-light btn-cancel">Cancel</a>
            </div>
        </form>

    </div>

</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
