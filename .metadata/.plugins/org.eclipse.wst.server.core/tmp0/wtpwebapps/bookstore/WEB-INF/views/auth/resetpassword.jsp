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
<div class="d-flex flex-wrap justify-content-between vh-100">
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
                                    <p class="text-muted mt-3">Enter your new password to access the account</p>
                                </div>
                                <div class="mt-4">
                                    <form action="..." method="POST" id="resetpasswordform" class="needs-validation" novalidate>
                                        <input type="hidden" name="token" id="token" value="{{ $token }}">
                                        <div class="mb-3">
                                            <label for="password" class="form-label">New password</label>
                                            <div class="input-group input-password">
                                                <input type="password" class="form-control validation" name="password" id="password" placeholder="Enter your password" required>
                                                <button type="button" class="btn btn-light border" id="togglePassword"><i class="fa fa-eye fa-fw"></i></button>
                                                <div class="invalid-feedback">
                                                    Please enter a new password.
                                                </div>
                                            </div>
                                        </div>
                                        <div class="mb-3">
                                            <label for="password_confirmation" class="form-label">Confirm new password</label>
                                            <div class="input-group input-password">
                                                <input type="password" class="form-control validation" name="password_confirmation" id="password_confirmation" placeholder="Re-enter your password" required>
                                                <button type="button" class="btn btn-light border"><i class="fa fa-eye fa-fw"></i></button>
                                                <div class="invalid-feedback">
                                                    Please confirm your password.
                                                </div>
                                            </div>
                                        </div>
                                        <button type="submit" class="btn btn-primary w-100 submit">Reset Password</button>
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
 <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
  
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

    <script>
        $(document).ready(function () {
            showPassword();
        });

        function showPassword() {
            $(".input-password").each(function () {
                var grp = $(this);
                var btn = grp.find("button");
                btn.on("click", function () {
                    var input = grp.find("input");
                    var icon = btn.find("i");
                    if (input.attr("type") == "password") {
                        input.attr("type", "text");
                        icon.removeClass("fa-eye");
                        icon.addClass("fa-eye-slash");
                    } else {
                        input.attr("type", "password");
                        icon.removeClass("fa-eye-slash");
                        icon.addClass("fa-eye");
                    }
                });
            });
        }
    </script>
</body>
</html>