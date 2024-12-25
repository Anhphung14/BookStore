<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Activate Account</title>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>

<body>

</body>

<script>
const alertMessage = "${alertMessage}";
const alertType = "${alertType}";

if (alertMessage) {
    Swal.fire({
        icon: alertType,
        title: alertType === "success" ? "Success" : alertType === "error" ? "Error" : "Warning",
        text: alertMessage,
        showCancelButton: alertMessage === "The link has expired!", // Chỉ hiện nút "Resend link" nếu alertMessage là "The link has expired!"
        cancelButtonText: "Resend link",
        confirmButtonText: "OK"
    }).then((result) => {
        if (result.isConfirmed && alertType === "success") {
            window.location.href = '${pageContext.servletContext.contextPath}/signin';
        } else if (result.dismiss === Swal.DismissReason.cancel) {
            // Thực hiện yêu cầu POST khi nhấn nút "Resend link"
            Swal.fire({
                title: 'Link Resent!',
                text: 'Đang gửi tới email của bạn...........',
                icon: 'warning',
                showConfirmButton: false,
//                 confirmButtonText: 'OK'
            });

            fetch('resend-link', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: new URLSearchParams({
                    action: 'resend',
                    email: '${email}'
                })
            }).then(response => response.text())
            .then(data => {
                console.log("Resend link action sent:", data);
                Swal.fire({
                    title: 'Link Resent!',
                    text: 'A new activation link has been sent to your email.',
                    icon: 'success',
                    confirmButtonText: 'OK'
                }).then((result) => {
                    if (result.isConfirmed) {
                        // Chuyển hướng sau khi nhấn "OK"
                        window.location.href = '${pageContext.servletContext.contextPath}/signin';
                    }
                });
            }).catch(error => {
                console.error('Error:', error);
                Swal.fire({
                    title: 'Error',
                    text: 'There was an error resending the link. Please try again.',
                    icon: 'error',
                    confirmButtonText: 'OK'
                });
            });
        }
    });
}


</script>
</html>