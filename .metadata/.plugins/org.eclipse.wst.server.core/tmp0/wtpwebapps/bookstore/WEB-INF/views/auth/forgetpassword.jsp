<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Book Store - ALDPT</title>
    
    <base href="${pageContext.servletContext.contextPath}/">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body>
<div class="d-flex flex-wrap justify-content-beetween vh-100">
    <div class="w-100">
        <section>
            <div class="container mt-3">
                <div class="justify-content-center row">
                    <div class="col-xl-4 col-lg-6 col-md-8">
                        <div class="card shadow-sm">
                            <div class="card-body p-4">
                                <div class="text-center w-75 mx-auto">
                                    <div class="auth-logo">
										  <img alt="Logo" src="resources/images/ALDPT.png" class="img-fluid"> 
									</div>
                                    <p class="text-muted mt-3">Enter your email address and we'll send you an email with instructions to reset your password.</p>
                                </div>
                                <div class="mt-4">
                                    <form action="..." method="POST" class="needs-validation" novalidate id="forgetpasswordform">
                                        <div class="mb-3">
                                            <label for="email" class="form-label">Email</label>
                                            <input type="email" class="form-control validation" name="email" id="email" placeholder="Enter your email">
                                            <div class="invalid-feedback">
                                                Please enter a valid email.
                                            </div>
                                        </div>

                                        <button type="submit" class="btn btn-primary w-100 submit">Send Email</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="mt-3 row">
                    <div class="text-center col-12">
                        <p class="text-muted">Back to <a class="text-primary fw-medium ms-1" href="...">Sign In</a></p>
                    </div>
                </div>
            </div>
        </section>
    </div>
</div>
</body>
</html>