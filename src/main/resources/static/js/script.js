/**************** Login Page ****************/
window.addEventListener(
    'load',
    () => {
        var elements = document.getElementsByClassName('verify');
        for (var i = 0; i < elements.length; i++) {
            elements.item(i).addEventListener('change', verify);
        }
    });

function verify() {
    document.getElementById("registerButton").disabled = false
    document.getElementById("signupError").innerHTML = ""
    var pass1 = document.forms['form']['password'].value;
    var pass2 = document.forms['form']['vpassword'].value;
    var email = document.forms['form']['email'].value;
    var firstName = document.forms['form']['firstName'].value;
    var lastName = document.forms['form']['lastName'].value;
    var phone = document.forms['form']['phone'].value;
    var province = document.forms['form']['province'].value;
    var city = document.forms['form']['city'].value;


    if (firstName == null || lastName == null || phone == null || province == null || city == null
    ) {
        document.getElementById("signupError").innerHTML += "Please fill in all required fields." + "<br/>";
        document.getElementById("registerButton").disabled = true
    }

    if (firstName == "" || lastName == "" || phone == "" || province == "" || city == ""
    ) {
        document.getElementById("signupError").innerHTML += "Please fill in all required fields." + "<br/>";
        document.getElementById("registerButton").disabled = true
    }

    if (!passwordStrength(pass1)) {
        document.getElementById("registerButton").disabled = true
        document.getElementById("signupError").innerHTML += "Password must contain:" + "<br/>" + "" +
            "<nb></nb> Minimum 8 Characters" + "<br/>"
            + "<nb></nb> A number (0-9) <br/>"
            + "<nb></nb> A lowercase letter (a-z) <br/>"
            + "<nb></nb> An upercase letter (A-Z) <br/>"
            + "<nb></nb> A special character <br/>";
    }


    if (pass1 = null || pass1 == "" || pass1 != pass2) {
        document.getElementById("signupError").innerHTML += "Please check your passwords are matching." + "<br/>";
        document.getElementById("registerButton").disabled = true
    }
    if (!validateEmail(email)) {
        document.getElementById("signupError").innerHTML += "Invalid email format." + "<br/>";
        document.getElementById("registerButton").disabled = true
    }

}


/**************** Account Information Change ****************/
window.addEventListener('load', () => {
    var elements = document.getElementsByClassName('verify-changes');
    for (var i = 0; i < elements.length; i++) {
        elements.item(i).addEventListener('change', verifyAccountChanges);
    }
});

function verifyAccountChanges() {
    document.getElementById("saveChangesButton").disabled = false;
    document.getElementById("editError").innerHTML = "";

    console.log(document.getElementById("editSuccess"));

    if (document.getElementById("editSuccess") != null) {
        document.getElementById("editSuccess").style.display = 'none'
    }

    var firstName = document.forms['form']['firstName'].value;
    var lastName = document.forms['form']['lastName'].value;
    var phone = document.forms['form']['phone'].value;
    var province = document.forms['form']['province'].value;
    var city = document.forms['form']['city'].value;

    let valid = true; // Flag to check if everything is valid

    // Ensure required fields are filled
    if (firstName === "" || lastName === "" || phone === "" || province === "" || city === "") {
        document.getElementById("editError").innerHTML += "Please fill in all required fields." + "<br/>";
        valid = false;
    }

    // Validate phone number format (assuming a 10-digit number)
    if (!validatePhone(phone)) {
        document.getElementById("editError").innerHTML += "Phone number must be exactly 10 digits." + "<br/>";
        valid = false;
    }

    // Disable button if invalid
    document.getElementById("saveChangesButton").disabled = !valid;

    return valid; // Return true if all is valid, false otherwise
}


/****************  Email Change ****************/
window.addEventListener('load', () => {
    document.getElementById("newEmail").addEventListener('input', verifyEmailChange);
});

function verifyEmailChange() {
    var currentEmail = document.getElementById("currentEmail").value;
    var newEmail = document.getElementById("newEmail").value;

    // Clears any previous error message
    document.getElementById("emailError").innerHTML = "";

    // Disables the submit button
    document.getElementById("emailSubmit").disabled = false;

    // Clears message if form is empty
    if (newEmail === "") {
        document.getElementById("emailError").innerHTML = "";
        document.getElementById("emailSubmit").disabled = true;
        return;
    }

    // Checks if new email matches current one
    else if (newEmail === currentEmail) {
        document.getElementById("emailError").innerHTML = "You cannot change to the same email.";
        document.getElementById("emailSubmit").disabled = true;
    } else if (!validateEmail(newEmail)) {
        document.getElementById("emailError").innerHTML = "Invalid email format.";
        document.getElementById("emailSubmit").disabled = true;
    }
}

/****************  Password Change ****************/
window.addEventListener('load', () => {
    document.getElementById("newPassword").addEventListener('input', verifyPasswordChange);
    document.getElementById("confirmPassword").addEventListener('input', verifyPasswordChange);
});

function verifyPasswordChange() {
    const newPassword = document.getElementById("newPassword").value;
    const confirmPassword = document.getElementById("confirmPassword").value;
    const passwordError = document.getElementById("passwordError");
    document.getElementById("pwSubmit").disabled = false; // Enable the submit button by default
    passwordError.innerHTML = ""; // Clear previous error messages

    // Validate password strength
    if (!passwordStrength(newPassword)) {
        passwordError.innerHTML += "Password must contain:" + "<br/>" +
            "<nb></nb> Minimum 8 Characters" + "<br/>" +
            "<nb></nb> A number (0-9) <br/>" +
            "<nb></nb> A lowercase letter (a-z) <br/>" +
            "<nb></nb> An uppercase letter (A-Z) <br/>" +
            "<nb></nb> A special character <br/>";
        document.getElementById("pwSubmit").disabled = true; // Disable the submit button
    }

    // Clears message if form is empty
    if (newPassword === "") {
        document.getElementById("passwordError").innerHTML = "";
        document.getElementById("pwSubmit").disabled = true;
        return;
    }

    // Validate if passwords match
    if (newPassword !== confirmPassword) {
        passwordError.innerHTML += "Passwords do not match.";
        document.getElementById("pwSubmit").disabled = true; // Disable the submit button
    }
}

/**************** Validation Constraints ****************/
//Validates password constraits
function passwordStrength(password) {
    let isValid = false;
    let validationRegex = [
        { regex: /.{8,}/ }, // min 8 letters,
        { regex: /[0-9]/ }, // numbers from 0 - 9
        { regex: /[a-z]/ }, // letters from a - z (lowercase)
        { regex: /[A-Z]/ }, // letters from A-Z (uppercase),
        { regex: /[^A-Za-z0-9]/ } // special characters
    ]

    validationRegex.forEach((item, i) => {
        isValid = item.regex.test(password);
    })
    return isValid
}

//Validates email constraints
const validateEmail = (email) => {
    return email.match(
        /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
    );
};

//Validates phone constraints
const validatePhone = (phone) => {
    // Ensure phone matches exactly 10 digits
    return /^\d{10}$/.test(phone);
};

/**************** Error Handling ****************/
//Confirm for Account Deletion (3-Step Verification)
function confirmDeletion() {
    // First confirmation
    var firstConfirmation = confirm("Are you sure you want to delete your account?");
    if (firstConfirmation) {
        // Second confirmation
        var secondConfirmation = confirm("This action is irreversible. Do you really want to proceed?");
        if (secondConfirmation) {
            // Third confirmation
            var thirdConfirmation = confirm("Please confirm one last time. Are you absolutely certain?");
            return thirdConfirmation;  // Only if third confirmation is confirmed, proceed with form submission
        }
    }
    return false;  // If any of the confirmations are cancelled, stop form submission
}

//Alert for saving page changes
function alertError() {
    window.alert("Error saving page content. Please contact an administrator.");
}

//Animation
$(document).ready(function () {
    $('.dropdown').hover(function () {
        $(this).find('.dropdown-menu')
            .stop(true, true).delay(100).fadeIn(200);
    }, function () {
        $(this).find('.dropdown-menu')
            .stop(true, true).delay(100).fadeOut(200);
    });
});

/**************** Page Hiding ****************/
$(document).ready(function () {
    $('#toggleVisibilityButton').on('click', function () {
        const contentId = $(this).data('content-id');
        const isVisible = $(this).text() === 'HIDE CURRENT PAGE';

        const confirmationMessage = isVisible
            ? 'Are you sure you want to make this page hidden?'
            : 'Are you sure you want to make this page public?';

        if (confirm(confirmationMessage)) {
            $.ajax({
                url: '/togglePageVisibility',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify({ contentId: contentId, isVisible: isVisible }),
                success: function () {
                    $('#toggleVisibilityButton').text(isVisible ? 'UNHIDE CURRENT PAGE' : 'HIDE CURRENT PAGE');
                    alert(`Content is now ${isVisible ? 'hidden' : 'public'}.`);

                    if (!$('#toggleVisibilityButton').closest('.loginNotice').find('.glyphicon-sunglasses').length) {
                        if (isVisible) {
                            $('.navbar-nav .nav-link[href="/"]').closest('.nav-item').hide();
                        } else {
                            $('.navbar-nav .nav-link[href="/"]').closest('.nav-item').show();
                        }
                    }
                },
                error: function () {
                    alert('Failed to update visibility.');
                }
            });
        }
    });
});


function copyEmailToClipboard(){
    var copyText = document.getElementById("filteredEmailOutput");
    copyText.select()
    copyText.setSelectionRange(0, 99999);

    navigator.clipboard.writeText(copyText.value);

    alert("Copied Emails to clipboard");

}

