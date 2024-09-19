jQuery(document).ready(function() {
    jQuery('.tabs .tab-links a').on('click', function(e) {
        var currentAttrValue = jQuery(this).attr('href');

        // Show/Hide Tabs
        jQuery('.tabs ' + currentAttrValue).show().siblings().hide();

        // Change/remove current tab to active
        jQuery(this).parent('li').addClass('active').siblings().removeClass('active');

        e.preventDefault();
    });
});

window.onload = function() {
    const myButton = document.getElementById("myButton");
    if (myButton) {
        myButton.onclick = function () {
            document.getElementById("newPassword").style.display = "inline";
            document.getElementById("confirmPassword").style.display = "inline";
            document.getElementById("pwSubmit").style.display = "inline";
            document.getElementById("lb1").style.display = "inline";
            document.getElementById("lb2").style.display = "inline";
            myButton.style.display = "none";
        };
    }

    const hiddenForm = document.getElementById("hiddenForm");
    if (hiddenForm) {
        hiddenForm.onsubmit = function(e) {
            var newPassword = document.getElementById("newPassword").value.trim();
            var confirmPassword = document.getElementById("confirmPassword").value.trim();
            var newPasswordError = document.getElementById("newPasswordError");
            var confirmPasswordError = document.getElementById("confirmPasswordError");

            newPasswordError.textContent = "";
            confirmPasswordError.textContent = ""; // clearing previous error messages

            var passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[\W_]).{8,}$/;
            var hasError = false;
            if (!passwordPattern.test(newPassword)) {
                newPasswordError.textContent = "Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, one digit, and one special character.";
                hasError = true;
            }
            if (newPassword !== confirmPassword) {
                confirmPasswordError.textContent = "Passwords do not match.";
                hasError = true;
            }

            if (hasError) {
                e.preventDefault(); //preventing the form submission if there is an error
            }

        };
    }
};
