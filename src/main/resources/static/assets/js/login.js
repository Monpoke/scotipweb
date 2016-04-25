/**
 * Created by svevia on 23/04/2016.
 */
$(document).ready(function () {

    $("#loginForm").submit(function (e) {
        e.preventDefault();
        login(e);
    });

    function login(e) {
        e.preventDefault();
        var mail = $('#mail').val();
        var pass = $('#pass').val();
        checkLogin(mail, pass);
    }
})


function checkLogin(mail, pass) {
    $.ajax({
        beforeSend: function(){
          // lock fields
        },


        type: 'POST',
        url: "/login",
        dataType: "html",
        data: 'mail=' + mail + '&pass=' + pass,
        success: function (data) {

            if (data.charAt(0) === "@") {
                location.href = data.substr(1);
            } else {
                alert("Login error.");
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            alert("Oooops. Something goes wrong.")
        }
    });
}