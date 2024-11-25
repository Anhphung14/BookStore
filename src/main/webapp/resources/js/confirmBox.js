$(".btn-delete").click(function(e) {
    e.preventDefault();

    var deleteUrl = $(this).data('url');
    console.log("Delete URL:", deleteUrl);

    if (!deleteUrl || deleteUrl === "undefined") {
        alert("Error: Invalid URL for deletion.");
        return;
    }

    Swal.fire({
        title: "Confirm Deletion",
        text: "Are you sure you want to delete this role?",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Yes, delete it!"
    }).then((result) => {
        if (result.isConfirmed) {
            window.location.href = deleteUrl;
        }
    });
});
