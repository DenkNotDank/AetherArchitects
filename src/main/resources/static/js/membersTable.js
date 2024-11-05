new DataTable('#membersTable');

function memberMarkPaidChange() {
    const checkboxes = document.querySelectorAll('#membersTable tbody input[type="checkbox"]');
    const markPaidButton = document.getElementById('memberMarkPaid');
    const markPaidDiv = document.getElementById('markPaidDiv');
    const selectedMemberEmailInput = document.getElementById('selectedMemberEmail');
    const paidMemberList = document.getElementById('paidMemberList');

    const selectedEmails = []; // Array to store selected emails

    checkboxes.forEach(checkbox => {
        checkbox.addEventListener('change', () => {
            const email = checkbox.getAttribute('data-email');

            if (checkbox.checked) {
                if (!selectedEmails.includes(email)) {
                    selectedEmails.push(email); // Add email if not already in the array
                }
            } else {
                const index = selectedEmails.indexOf(email);
                if (index !== -1) {
                    selectedEmails.splice(index, 1); // Remove email if checkbox is unchecked
                }
            }

            if (selectedEmails.length > 0) {
                markPaidButton.disabled = false;
                selectedMemberEmailInput.value = selectedEmails[0]; // Set the first email for the hidden input
                paidMemberList.value = selectedEmails.join(', '); // Join emails with commas and update textarea
            } else {
                markPaidButton.disabled = true;
                selectedMemberEmailInput.value = "";
                paidMemberList.value = ""; // Clear the textarea
                $(markPaidDiv).collapse('hide'); // Hide form if no checkbox is selected
            }
        });
    });

    // Hide the form when the page loads
    $(markPaidDiv).collapse('hide');
}

function validateMarkPaidForm(event) {
    const paidToggleTrue = document.getElementById('paidToggleTrue');
    const paidToggleFalse = document.getElementById('paidToggleFalse');
    const tiers = document.getElementById('tiers');
    const paidMemberList = document.getElementById('paidMemberList');

    if (!paidMemberList.value) {
        alert('Please add at least 1 member.');
        event.preventDefault();
        return false;
    }

    if (!paidToggleTrue.checked && !paidToggleFalse.checked) {
        alert('Please select either "Yes" or "No" for payment status.');
        event.preventDefault();
        return false;
    }

    if (!tiers.value) {
        alert('Please select a Tier.');
        event.preventDefault();
        return false;
    }

    return true;
}

document.getElementById('markPaidForm').addEventListener('submit', validateMarkPaidForm);

function memberPasswordChange() {
    const checkboxes = document.querySelectorAll('#membersTable tbody input[type="checkbox"]');
    const changePasswordButton = document.getElementById('memberPasswordSubmit');
    const changePermissionButton = document.getElementById('changePermissionsSubmit');
    const passwordResetForm = document.getElementById('passwordResetForm');
    const changePermissionsForm = document.getElementById('changePermissionsForm');
    const selectedMemberEmailInput = document.getElementById('selectedMemberEmail');
    const selectedUserIdInput = document.getElementById('selectedUserId');


    checkboxes.forEach(checkbox => {
        checkbox.addEventListener('change', () => {

            const selected = document.querySelectorAll('#membersTable tbody input[type="checkbox"]:checked');
            if (selected.length === 1) {
                changePasswordButton.disabled = false;
                changePermissionButton.disabled = false;
                selectedMemberEmailInput.value = selected[0].getAttribute('data-email');
                selectedUserIdInput.value = selected[0].getAttribute('data-userId')                //$(passwordResetForm).collapse('show');
            } else {
                changePasswordButton.disabled = true;
                changePermissionButton.disabled = true;
                selectedMemberEmailInput.value = "";
                selectedUserIdInput.value = "";
                $(passwordResetForm).collapse('hide'); // Hide form if no checkbox is selected
            }
        });
    });

    // Hide the form when the page loads
    $(passwordResetForm).collapse('hide');
    $(changePermissionsForm).collapse('hide');
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
    memberMarkPaidChange();
    hideAlerts(); // Call hideAlerts to set up the alert hiding
};
