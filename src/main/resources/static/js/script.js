window.addEventListener(
    'load',
    () => {
        var elements = document.getElementsByClassName('verify');
        for (var i = 0; i < elements.length; i++) {
            elements.item(i).addEventListener('change', verify);
        }
    });

function verify(){
    document.getElementById("registerButton").disabled = false
    document.getElementById("signupError").innerHTML=""
    var pass1 = document.forms['form']['password'].value;
    var pass2 = document.forms['form']['vpassword'].value;
    var email = document.forms['form']['email'].value;
    var firstName = document.forms['form']['firstName'].value;
    var lastName = document.forms['form']['lastName'].value;
    var phone = document.forms['form']['phone'].value;
    var province = document.forms['form']['province'].value;
    var city = document.forms['form']['city'].value;


    if(firstName==null || lastName==null || phone==null || province==null || city==null
    ){
        document.getElementById("signupError").innerHTML+="Please fill in all required fields." + "<br/>";
        document.getElementById("registerButton").disabled = true
    }

    if(firstName=="" || lastName=="" || phone=="" || province=="" || city==""
    ){
        document.getElementById("signupError").innerHTML+="Please fill in all required fields." + "<br/>";
        document.getElementById("registerButton").disabled = true
    }

    if(!passwordStrength(pass1)){
        document.getElementById("registerButton").disabled = true
        document.getElementById("signupError").innerHTML+="Password must contain:" + "<br/>" + "" +
            "<nb></nb> Minimum 8 Characters" + "<br/>"
            + "<nb></nb> A number (0-9) <br/>"
            + "<nb></nb> A lowercase letter (a-z) <br/>"
            + "<nb></nb> An upercase letter (A-Z) <br/>"
            + "<nb></nb> A special character <br/>" ;
    }


    if(pass1=null || pass1=="" || pass1 != pass2){
        document.getElementById("signupError").innerHTML+="Please check your passwords are matching." + "<br/>";
        document.getElementById("registerButton").disabled = true
    }
    if(!validateEmail(email)){
        document.getElementById("signupError").innerHTML+="Invalid email format." + "<br/>";
        document.getElementById("registerButton").disabled = true
    }


}

function passwordStrength(password){
    let isValid =false;
    let validationRegex = [
        { regex: /.{8,}/ }, // min 8 letters,
        { regex: /[0-9]/ }, // numbers from 0 - 9
        { regex: /[a-z]/ }, // letters from a - z (lowercase)
        { regex: /[A-Z]/}, // letters from A-Z (uppercase),
        { regex: /[^A-Za-z0-9]/} // special characters
    ]

    validationRegex.forEach((item, i)=>{
        isValid = item.regex.test(password);
    })
    return isValid
}

const validateEmail = (email) => {
    return email.match(
        /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
    );
};



function alertError(){
    window.alert("Error saving page content. Please contact an administrator.");
}


$(document).ready(function(){
    $('.dropdown').hover(function(){
        $(this).find('.dropdown-menu')
            .stop(true, true).delay(100).fadeIn(200);
    }, function(){
        $(this).find('.dropdown-menu')
            .stop(true, true).delay(100).fadeOut(200);
    });
});