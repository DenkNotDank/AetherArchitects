function verify(){
    var pass1 = document.forms['form']['password'].value;
    var pass2 = document.forms['form']['vpassword'].value;

    if(pass1=null || pass1=="" || pass1 != pass2){
        document.getElementById("error").innerHTML="Please check your passwords";
        return false;
    }
}

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