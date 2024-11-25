window.checkAll = function() {
    var f = document.adminForm;
    var c = f.toggle.checked; 
    var checkboxes = f.elements['cid[]']; 

    var n = 0; 

    for (var i = 0; i < checkboxes.length; i++) {
        var cb = checkboxes[i];
        if (cb) {
            cb.checked = c; // Toggle checkbox
            if (cb.checked) {
                n++; // Increment count for selected checkboxes
            }
        }
    }

    document.adminForm.boxchecked.value = n; // Update the selected count
    toggleActionBar(); // Show or hide action bar based on the count
}

function toggleActionBar() {
    var form = document.adminForm;
    var selectedCount = form.boxchecked.value; // Get the selected count
    var actionBar = document.querySelector('.table-actionbar'); 

    if (selectedCount > 0) {
        actionBar.classList.remove('d-none'); 
        document.querySelector('.selected-count').textContent = `${selectedCount} selected`;
    } else {
        actionBar.classList.add('d-none');
    }
}

function isChecked(checked) {
    var checkboxes = document.adminForm.elements['cid[]'];
    var n = 0;
    for (var i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i].checked) {
            n++; // Increment count for selected checkboxes
        }
    }
    document.adminForm.boxchecked.value = n; // Update the selected count
    toggleActionBar(); // Show or hide action bar based on the count
}