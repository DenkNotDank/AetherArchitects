new DataTable('#membersTable');

function memberPasswordChange() {
    const checkboxes = document.querySelectorAll('#membersTable tbody input[type="checkbox"]');
    const changePasswordButton = document.getElementById('memberPasswordSubmit');
    const passwordResetForm = document.getElementById('passwordResetForm');
    const selectedMemberEmailInput = document.getElementById('selectedMemberEmail');

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
                selectedMemberEmailInput.value = selected[0].getAttribute('data-email');
                $(passwordResetForm).collapse('show');
            } else {
                changePasswordButton.disabled = true;
                selectedMemberEmailInput.value = "";
                $(passwordResetForm).collapse('hide'); // Hide form if no checkbox is selected
            }
        });
    });

    // Hide the form when the page loads
    $(passwordResetForm).collapse('hide');
}

// Function to hide success and error messages after 3 seconds
function hideAlerts() {
    setTimeout(() => {
        $('.alert').fadeOut();
    }, 3000);
}

window.onload = () => {
    memberPasswordChange();
    hideAlerts(); // Call hideAlerts to set up the alert hiding
};
