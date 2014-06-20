$(document).ready(function()
{

    /* sendung formular abfangen */
    $("#registerForm").submit(function() {
 
    	alert("document ready username=" + $("#username").val() + "&email=" + $("#email").val() + "&password1=" + $("#password1").val() + "&password2=" + $("#password2").val());
        
    	
    	var postData = $("registerForm").serialize();
    	/* ajax objekt zum aufruf & versand an das skript
        'name' und 'email' sind in der data-zeile die variablen für das php-skript */
        $.ajax({
            type: "POST",
            url: "/api/user/",
            async : false,
            data: "username=" + $("#username").val() + "&email=" + $("#email").val() + "&password1=" + $("#password1").val() + "&password2=" + $("#password2").val(),
            success: function(msg)
            {
            	var nick =  $("#username").val();
	        	var pw = $("#password1").val();
	        	var validUser = validate(nick, pw);
            }
        });
        
        /* wichtig!
        sonst schickt der browser das formular ab und
        und ruft die seite auf die bei action="" hinterlegt wurde.
        dann verlässt er nämlich die bisherige seite... */
        return true;
 
    });
 
});

function fill() {
	var username = $.cookie('username');
	var email = $.cookie('email');
	document.saveprofile[0].value = username;
	document.saveprofile[1].value = email;
}


function update() {
	
    var postData = $("form").serialize();
    $.ajax(
    {
        url : "/api/user/",
        type: "POST",
        data : postData,
        contentType: "application/x-www-form-urlencoded",
        success: function(data) 
        {
        	window.location = "/profile";

        },
        error: function(jqXHR, textStatus, errorThrown)
        {
        	window.location.replace("/register");

        }
    });
    e.preventDefault(); //STOP default action
}