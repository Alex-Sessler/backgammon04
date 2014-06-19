function setCookie(name, wert, domain, expires, path, secure) {
	var cook = name + "=" + unescape(wert);
	cook += (domain) ? "; domain=" + domain : "";
	cook += (expires) ? "; expires=" + expires : "";
	cook += (path) ? "; path=" + path : "";

	cook += (secure) ? "; secure" : "";
	document.cookie = cook;
}

function eraseCookie(name, domain, path) {
	var cook = "name=; expires=Thu, 01-Jan-70 00:00:01 GMT";
	cook += (domain) ? "domain=" + domain : "";
	cook += (path) ? "path=" + path : "";
	document.cookie = cook;
}


function getCookie(name) {
	   var i=0;  // Suchposition im Cookie
	   var suche = name + "=";
	   while (i<document.cookie.length) {
	      if (document.cookie.substring(i, i + suche.length)
	      == suche) {
	         var ende = document.cookie.indexOf(";", i
	         + suche.length);
	         ende = (ende > –1) ? ende :
	         document.cookie.length;
	         var cook = document.cookie.substring(i
	            + suche.length, ende);
	         return unescape(cook);
	      }
	      i++;
	   }
	   return "";
}