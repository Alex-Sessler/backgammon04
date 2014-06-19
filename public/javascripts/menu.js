$(document).ready(function() {
	var availableGames = loadGames(document.getElementById("loadType").value);
	populateGamesList(availableGames, document.getElementById("loadType").value);
	
});

function loadGames(loadType) {
	// TODO: implement ajax call;

	var games = new Array();
	var url = "/api/user/game/list/";
	
	if(loadType == "Join") {
		url = "/api/game/list/";
	} 
	
	$.ajax({
		type:'GET',
		url:url,
		async: false,
		success: function(data) {

			for (var i = 0; i < data.length; i++) {
				games[i] = new Object();
				games[i].id = data[i].id;

				if (data[i].players[0].color == "BLACK") {
					games[i].originator = data[i].players[0].user.username;
				} else {
					games[i].originator = data[i].players[1].user.username;
				}

				games[i].creationDate = new Date(data[i].initialized);
				if(data[i].started == null) {
					games[i].status = '<font color="red">waiting</font>';
				} else {
					games[i].status = '<font color="green">started</font>';
				}
			}
		}
		
	});

	return games;

}

function populateGamesList(games, loadType) {
	var gamesList = document.getElementById("gamesList");

	for ( var i = 0; i < games.length; i++) {
		var listItem = createGameListItem(games[i].id, games[i].originator,
				games[i].creationDate, games[i].status, loadType)
		gamesList.appendChild(listItem);
	}

}

function formatDate(date) {
	var day = date.getDate();
	var month = date.getMonth() + 1;
	var year = date.getFullYear();
	var hour = date.getHours();
	var minutes = date.getMinutes();
	return '' + (day <= 9 ? '0' + day : day) + "."
			+ (month <= 9 ? '0' + month : month) + '.' + year + " "
			+ (hour <= 9 ? '0' + hour : hour) + ":"
			+ (minutes <= 9 ? '0' + minutes : minutes);
}

function createNewGame() {
	var auth = make_base_auth($.cookie('username'), $.cookie('password'));
	var url = '/api/game';
	
	$.ajax({
		headers : {
			"Authorization" : auth,
		},
		async : false,
		type : "POST",
		url : url,
		success : function(data) {
			$(location).attr('href',document.URL);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			console.log(textStatus + ' / ' + errorThrown);
			alert("Error");
		}
	});
	return false;
}

function createGameListItem(id, originator, date, status, buttonText) {
	var listItem = document.createElement("li");
	listItem.setAttribute("class", "gameListItem");

	var idText = document.createElement("p");
	idText.innerHTML = "ID: " + id;
	var creatorText = document.createElement("p");
	creatorText.innerHTML = "Created: " + originator;
	var dateText = document.createElement("p");
	dateText.innerHTML = "Date: " + formatDate(date);
	var statusText = document.createElement("p");
	statusText.innerHTML = "Status: " + status;
	var joinButton = document.createElement("button");
	joinButton.innerHTML = buttonText;
	joinButton.setAttribute("style", "height: 30px; width: 60px;");
	joinButton.onclick = function(){
		if(buttonText == "Join") {
			join(id);
		} else {
			redirect(true, id);
		}
	    
	    return false;
	  };
	  
	listItem.appendChild(idText);
	listItem.appendChild(creatorText);
	listItem.appendChild(dateText);
	listItem.appendChild(statusText);
	listItem.appendChild(joinButton);
	return listItem;
}

function join(id) {
	var auth = make_base_auth($.cookie('username'), $.cookie('password'));
	var url = '/api/game/'+id+'/join';
	
	$.ajax({
		headers : {
			"Authorization" : auth,
		},
		async : false,
		type : "POST",
		url : url,
		success : function(data) {
			redirect(true, id);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			console.log(textStatus + ' / ' + errorThrown);
			redirect(false, id);
		}
	});
	return false;
}

function redirect(succsess, id) {
	if(succsess) {
		var url = "game/"+id;    
		$(location).attr('href',url);
	} else {
		alert("You are not permitted to join the game with id: " +id);
	}
	return false;
}

function getStatus(game) {
	
}