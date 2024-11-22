new DataTable('#membersTable');

function memberPasswordChange() {
    const checkboxes = document.querySelectorAll('#membersTable tbody input[type="checkbox"]');
    const changePasswordButton = document.getElementById('memberPasswordSubmit');
    const changePermissionButton = document.getElementById('changePermissionsSubmit');
    const changeSuspensionButton = document.getElementById('changeSuspensionSubmit');
    const passwordResetForm = document.getElementById('passwordResetForm');
    const changePermissionsForm = document.getElementById('changePermissionsForm');
    const changeSuspensionForm = document.getElementById('changeSuspensionForm');
    const selectedMemberEmailInput = document.getElementById('selectedMemberEmail');
    const selectedSuspensionEmailInput = document.getElementById('selectedSuspensionEmail');
    const selectedUserIdInput = document.getElementById('selectedUserId');


    checkboxes.forEach(checkbox => {
        checkbox.addEventListener('change', () => {
            checkboxes.forEach(cb => {
                if (cb !== checkbox) {
                    cb.checked = false;
                }
            });

            const selected = document.querySelectorAll('#membersTable tbody input[type="checkbox"]:checked');
            if (selected.length === 1) {
                changePasswordButton.disabled = false;
                changePermissionButton.disabled = false;
                changeSuspensionButton.disabled = false;
                selectedMemberEmailInput.value = selected[0].getAttribute('data-email');
                selectedSuspensionEmailInput.value = selected[0].getAttribute('data-email');
                selectedUserIdInput.value = selected[0].getAttribute('data-userId')
                //$(passwordResetForm).collapse('show');
            } else {
                changePasswordButton.disabled = true;
                changePermissionButton.disabled = true;
                changeSuspensionButton.disabled = true;
                selectedMemberEmailInput.value = "";
                selectedSuspensionEmailInput.value = "";
                selectedUserIdInput.value = "";
                $(passwordResetForm).collapse('hide'); // Hide form if no checkbox is selected
                $(changePermissionsForm).collapse('hide');
                $(changeSuspensionForm).collapse('hide');
            }
        });
    });

    // Hide the form when the page loads
    $(passwordResetForm).collapse('hide');
    $(changePermissionsForm).collapse('hide');
    $(changeSuspensionForm).collapse('hide');
}

// Function to hide success and error messages after 3 seconds
function hideAlerts() {
    setTimeout(() => {
        $('.alert').fadeOut();
    }, 3000);
}

function toggleRowHighlight(checkbox) {
    const row = checkbox.closest('tr');
    if (checkbox.checked) {
        row.style.backgroundColor = '#d1ecf1';
    } else {
        row.style.backgroundColor = '';
    }
}

window.onload = () => {
    memberPasswordChange();
    hideAlerts(); // Call hideAlerts to set up the alert hiding
};
