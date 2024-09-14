
jQuery(document).ready(function() {
    jQuery('.tabs .tab-links a').on('click', function(e)  {
        var currentAttrValue = jQuery(this).attr('href');

        // Show/Hide Tabs
        jQuery('.tabs ' + currentAttrValue).show().siblings().hide();

        // Change/remove current tab to active
        jQuery(this).parent('li').addClass('active').siblings().removeClass('active');

        e.preventDefault();
    });
});
window.onload = function() {
    document.getElementById("myButton").onclick = function () {
        document.getElementById("newPassword").style.display = "inline";
        document.getElementById("confirmPassword").style.display = "inline";
        document.getElementById("pwSubmit").style.display = "inline";
        document.getElementById("lb1").style.display = "inline";
        document.getElementById("lb2").style.display = "inline";
        document.getElementById("myButton").style.display = "none";
    }

}
