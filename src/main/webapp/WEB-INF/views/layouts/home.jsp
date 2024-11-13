<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="header.jsp"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Trang Chủ</title>
<base href="${pageContext.servletContext.contextPath}/">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="path/to/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<style>
body {
    display: flex;
    height: 100vh;
    margin: 0;
    flex-direction: column;
}

.main-content {
    display: flex;
    flex: 1;
}

.sidebar {
    background-color: #343a40;
    padding-left: 10px;
}

.sidebar a {
    color: white;
    padding: 15px;
    text-decoration: none;
    display: block;
}

.sidebar a:hover {
    background-color: #007bff;
}

.container {
    flex: 1;
}

.logo img {
    width: 90px;
    height: auto;
}

.logo-name {
    font-size: 1.5rem;
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
    <!-- Main Content Section -->
    <div class="main-content">
        <%@ include file="navigation.jsp"%>
        <!-- Gọi tệp navigation.jsp -->
        <div class="container-fluid d-flex flex-column" style="padding-left: 0px; padding-right: 0px;">
            <div class="page-nav p-2">
                <h1 class="page-title">Welcome back, Book Store!</h1>
            </div>
            <div class="container-fluid" style="margin-left: 0px; margin-right: 0px;">
                ... <!-- Nội dung chính ở đây -->
            </div>
        </div>

        <!-- Bootstrap JS and dependencies -->
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.7/dist/umd/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
    </div>
</body>
</html>
