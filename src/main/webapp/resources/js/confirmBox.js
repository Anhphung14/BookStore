$(document).ready(function() {
    // Bắt sự kiện click của các nút xóa
    $(".btn-delete").click(function(e) {
        e.preventDefault();
        
        // Lấy URL từ thuộc tính data-url
        var deleteUrl = $(this).data('url');

        // Tạo hộp thoại xác nhận với SweetAlert2
        Swal.fire({
            title: 'Delete item',
            text: 'Are you sure you want to delete this item?',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonText: 'Yes, delete it!',
            cancelButtonText: 'No, cancel!',
            reverseButtons: true
        }).then((result) => {
            if (result.isConfirmed) {
                // Nếu người dùng xác nhận xóa, gửi yêu cầu xóa đến URL từ data-url
                window.location.href = deleteUrl; // Chuyển hướng đến URL xóa
            } else if (result.dismiss === Swal.DismissReason.cancel) {
                Swal.fire('Cancelled', 'The item is safe!', 'error');
            }
        });
    });
});
