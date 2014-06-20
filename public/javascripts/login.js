function make_base_auth(user, pass) {
	var tok = user + ':' + pass;
	var hash = btoa(tok);
	return "Basic " + hash;
}

function signIn() {

	var nick = document.getElementById("username").value;
	var pw = document.getElementById("password").value;
	var validUser = validate(nick, pw);
}

function validate(nickname, password) {
	var auth = make_base_auth(nickname, password);
	var url = '/api/login';

	$.ajax({
		headers : {
			"Authorization" : auth,
		},
		async : false,
		type : "GET",
		url : url,
		success : function(data) {
			setCookie(nickname, password, data.id, data.email);
			return true;
		},
		error : function(jqXHR, textStatus, errorThrown) {
			console.log(textStatus + ' / ' + errorThrown);
			return false;
		}
	});

	return true;
}

function setCookie(nick, pw, id, email) {
	$.cookie('username', nick);
	$.cookie('password', pw);
	$.cookie('id', id);
	$.cookie('email', email);
}

function logout() {
	$.removeCookie('username');
	$.removeCookie('password');
	$.removeCookie('id');
	$.removeCookie('email');
	var url = "@routes.Application.signin()";    
	$(location).attr('href',url);
	return false;
}