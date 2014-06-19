	$(document).ready(function() {
			var availableGames = loadGames();
			populateGamesList(availableGames);
		});

		function loadGames() {
			// TODO: implement ajax call;

			var dummyGames = new Array();
			dummyGames[0] = new Object();
			dummyGames[0].id = "1234";
			dummyGames[0].originator = "Alex";
			dummyGames[0].creationDate = new Date(2013, 11, 2, 9, 50);
			dummyGames[0].state = "Active";
			dummyGames[1] = new Object();
			dummyGames[1].id = "2345";
			dummyGames[1].originator = "Mustafa";
			dummyGames[1].creationDate = new Date(2013, 11, 2, 8, 20);
			dummyGames[1].state = "New";
			dummyGames[2] = new Object();
			dummyGames[2].id = "3456";
			dummyGames[2].originator = "Mustafa";
			dummyGames[2].creationDate = new Date(2013, 11, 1, 8, 20);
			dummyGames[2].state = "New";
			dummyGames[3] = new Object();
			dummyGames[3].id = "4567";
			dummyGames[3].originator = "Mustafa";
			dummyGames[3].creationDate = new Date(2013, 8, 2, 8, 20);
			dummyGames[3].state = "Active";

			return dummyGames;

		}

		function populateGamesList(games) {
			var gamesList = document.getElementById("gamesList");

			for (var i = 0; i < games.length; i++) {
				var listItem = createGameListItem(games[i].id,
						games[i].originator, games[i].creationDate);
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
			var gamesList = document.getElementById("gamesList");
			var date = new Date();
			var listItem = createGameListItem(date.getTime(), "Alex", date);
			gamesList.insertBefore(listItem, gamesList.firstChild);
		}

		function createGameListItem(id, originator, date) {
			var listItem = document.createElement("li");
			listItem.setAttribute("class", "gameListItem");

			var creatorText = document.createElement("p");
			creatorText.innerHTML = "Created: " + originator;
			var dateText = document.createElement("p");
			dateText.innerHTML = "Date: " + formatDate(date);
			var joinButton = document.createElement("button");
			joinButton.innerHTML = "Join";
			joinButton.setAttribute("style", "height: 30px; width: 60px;");
			var href = "location.href='game.html'";
			joinButton.setAttribute("onclick", href);

			listItem.appendChild(creatorText);
			listItem.appendChild(dateText);
			listItem.appendChild(joinButton);
			return listItem;
		}