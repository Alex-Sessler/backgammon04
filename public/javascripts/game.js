$(document).ready(function() {
    setInterval("init()",2000);
});

var CHECKER_WIDTH = 46;
var BOARD_HEIGHT = 642;

var checker_canvas = document.getElementById("checker_canvas");
var checker_ctx = checker_canvas.getContext("2d");

var gameWidth = checker_canvas.width;
var gameHeight = checker_canvas.height;

var canvasState;

function init() {
	// is called on sprite_constants load	
	
	
	if (canvasState == null) {
		initFields();
		var checkers = initCheckers();
		canvasState = new CanvasState(checker_canvas, checkers);
	} else {
		if (canvasState.activeChecker != null) {
			return;
		}
		initFields();
		var checkers = initCheckers();
		canvasState.checkers = checkers;
	}
	canvasState.redrawNeeded = true;
}

function initPlayerStatusBox(players){
	var blackPlayerNickname;
	var whitePlayerNickname;
	if (players[0].color == "BLACK") {
		
		blackPlayerNickname = players[0].user.username;
		whitePlayerNickname = players[1].user.username;
	} else {
		blackPlayerNickname = players[1].user.username;
		whitePlayerNickname = players[0].user.username;
	}

	var black_checker = document.getElementById("black_checker");
	black_checker.setAttribute("style","background-image: url(../assets/images/sprite.png); width: 46px; height: 46px; background-position: 294px 114px; float: left; margin-top: 1px; margin-left: 2px;");
	var nickname_black_player = document.getElementById("nickname_black_player");
	nickname_black_player.innerHTML = blackPlayerNickname;
	
	var white_checker = document.getElementById("white_checker");
	white_checker.setAttribute("style","background-image: url(../assets/images/sprite.png); width: 46px; height: 46px; background-position: 294px 160px; float: left; margin-top: 1px; margin-left: 2px;");
	var nickname_white_player = document.getElementById("nickname_white_player");
	nickname_white_player.innerHTML = whitePlayerNickname;
}

function setPlayerActive(player) {

	if (player == "BLACK") {
		var box_player_black = document.getElementById("box_player_black");
		box_player_black.style.border = "5px solid green";
		
		var box_player_white = document.getElementById("box_player_white");
		box_player_white.style.border = "5px solid black";	
		
		var arrow = document.getElementById("arrow");
		arrow.innerHTML = "&larr;"
	} else {
		var box_player_black = document.getElementById("box_player_black");
		box_player_black.style.border = "5px solid black";
		var box_player_white = document.getElementById("box_player_white");
		box_player_white.style.border = "5px solid green";	
		
		var arrow = document.getElementById("arrow");
		arrow.innerHTML = "&rarr;"	
	}
}

function initCheckers() {
	var checkers = new Array();
	
	var url = "/api/game/"+document.getElementById("gameId").value;
	
	
	$.ajax({
		type:'GET',
		url:url,
		async: false,
		success: function(data) {
			
			// initialize player status box and mark active player
			if (document.getElementById("arrow").innerHTML.trim() == "") {
				initPlayerStatusBox(data.players);
			}			
			setPlayerActive(data.currentPlayer);	
			
			for (var i = 0; i < 28; i++) {
				
				for (var j = 0; j < data.board.pointList[i].numberOfCheckers; j++) {
					
					if (data.board.pointList[i].color == "WHITE") {
						checkers[checkers.length] = new Checker(Colors.white, fields[i]);
					} else {
						checkers[checkers.length] = new Checker(Colors.black, fields[i]);
					}
					
				}
				
			}
			document.getElementById('diceButton').disabled = false;
			
			if(data.players[0].user.username == $.cookie('username')) {
				if (data.players[0].color != data.currentPlayer ) {
					document.getElementById('diceButton').disabled = true; 
				}
			} else {
				if (data.players[1].color != data.currentPlayer) {
					document.getElementById('diceButton').disabled = true;
				}
			}
			
			if(data.dices == null) {
				clearDiceArea();
			}
			
			if(data.dices != null) {
				drawDices(data.dices[0].value, data.dices[1].value);
				if(data.dices[0].played ) {
					markDiceAsUsed(data.dices[0].value);
				}
				if(data.dices[1].played) {
					markDiceAsUsed(data.dices[1].value);
				}
				if(data.dices[2] != null && data.dices[2].played) {
					markDiceAsUsed(data.dices[2].value);
				}
				if(data.dices[3] != null && data.dices[3].played) {
					markDiceAsUsed(data.dices[3].value);
				}
				if(data.players[0].user.username == $.cookie('username')) {
					if (data.players[0].color != data.currentPlayer ) {
						markDiceAsUsed(data.dices[0].value);
						markDiceAsUsed(data.dices[1].value);
						if(data.dices[0].value == data.dices[1].value) {
							markDiceAsUsed(data.dices[2].value);
							markDiceAsUsed(data.dices[3].value)
						}
					}
				} else {
					if (data.players[1].color != data.currentPlayer) {
						markDiceAsUsed(data.dices[0].value);
						markDiceAsUsed(data.dices[1].value);
						if(data.dices[0].value == data.dices[1].value) {
							markDiceAsUsed(data.dices[2].value);
							markDiceAsUsed(data.dices[3].value)
						}
					}
				}
			}

		}
		
	});
	
	return checkers;
}


function reInit(data) {
	// is called on sprite_constants load
	initFields();
	var checkers = reInitCheckers(data);
	canvasState.checkers = checkers;
	if(data.dices != null) {
		drawDices(data.dices[0].value, data.dices[1].value);
	}
}

function reInitCheckers(data) {
	var checkers = new Array();
	

			for (var i = 0; i < 28; i++) {
				
				for (var j = 0; j < data.board.pointList[i].numberOfCheckers; j++) {
					
					if (data.board.pointList[i].color == "WHITE") {
						checkers[checkers.length] = new Checker(Colors.white, fields[i]);
					} else {
						checkers[checkers.length] = new Checker(Colors.black, fields[i]);
					}
					
				}
				
			}
			
			document.getElementById('diceButton').disabled = false;
			
			if(data.players[0].user.username == $.cookie('username')) {
				if (data.players[0].color != data.currentPlayer ) {
					document.getElementById('diceButton').disabled = true; 
				}
			} else {
				if (data.players[1].color != data.currentPlayer) {
					document.getElementById('diceButton').disabled = true;
				}
			}
			
			if(data.dices == null) {
				clearDiceArea();
			}
			
			if(data.dices != null) {
				drawDices(data.dices[0].value, data.dices[1].value);
				if(data.dices[0].played ) {
					markDiceAsUsed(data.dices[0].value);
				}
				if(data.dices[1].played) {
					markDiceAsUsed(data.dices[1].value);
				}
				if(data.players[0].user.username == $.cookie('username')) {
					if (data.players[0].color != data.currentPlayer ) {
						markDiceAsUsed(data.dices[0].value);
						markDiceAsUsed(data.dices[1].value);
					}
				} else {
					if (data.players[1].color != data.currentPlayer) {
						markDiceAsUsed(data.dices[0].value);
						markDiceAsUsed(data.dices[1].value);
					}
				}
			}


	return checkers;
}

function CanvasState(canvas, checkers) {
	this.canvas = canvas;
	this.checkers = checkers;
	this.activeChecker;
	this.startField = null;
	this.redrawNeeded = true;
	this.adjustChecker = false; // this is used when a checker is moved to a
	// field
	// and needs to be adjusted to its final position
	this.dragoffx = 0;
	this.dragoffy = 0;

	var myState = this;

	canvas.addEventListener('mousedown', function(e) {
		//TODO @mse
		if (myState.startField != null) {
			return; // this happens if mouse was released outside of the canvas,
					// therefore the start field should not be overwritten
		}

		var mouse = myState.getMouse(e);
		var mouseX = mouse.x;
		var mouseY = mouse.y;

		myState.startField = findField(mouseX, mouseY);
		if (myState.startField != null) {
			var checker = myState.getTopCheckerForStartField();
			if (checker != null) {
				myState.dragoffx = mouseX - checker.x;
				myState.dragoffy = mouseY - checker.y;
				myState.activeChecker = checker;
				myState.redrawNeeded = true;
			}
		}
	}, false);

	canvas.addEventListener('mousemove', function(e) {
		if (myState.activeChecker) {
			var mouse = myState.getMouse(e);

			myState.activeChecker.x = mouse.x - myState.dragoffx;
			myState.activeChecker.y = mouse.y - myState.dragoffy;
			myState.redrawNeeded = true;

		}
	}, false);

	canvas.addEventListener('mouseup',
			function(e) {
				var mouse = myState.getMouse(e);
				var mouseX = mouse.x;
				var mouseY = mouse.y;
				myState.startField.numberOfCheckers--;
				var targetField = findField(mouseX, mouseY);

				if (targetField == null
						&& (targetField = findField(myState.activeChecker.x,
								myState.activeChecker.y)) == null) {
					// no target field, jump back
					myState.activeChecker.x = myState.startField.x;
					myState.activeChecker.y = myState.startField
							.getNextCheckerYPosition();
					myState.startField.numberOfCheckers++;
				} else {
					var validMove = validateMove(myState.activeChecker,
							myState.startField, targetField);
					if (validMove) {
						myState.activeChecker.x = targetField.x;
						myState.activeChecker.y = targetField
								.getNextCheckerYPosition();
						targetField.numberOfCheckers++;

						myState.sortCheckers(); // sort the checkers for
												// redrawing
					} else {
						myState.activeChecker.x = myState.startField.x;
						myState.activeChecker.y = myState.startField
								.getNextCheckerYPosition();
						myState.startField.numberOfCheckers++;
					}
				}
				myState.redrawNeeded = true;
				myState.activeChecker = null;
				myState.startField = null;

			}, false);

	this.interval = 30;
	setInterval(function() {
		myState.draw();
	}, myState.interval);

}

function validateMove(checker, startField, targetField) {
	var retval = move(startField.id, targetField.id);
	return retval;
}

function move(start, target) {
	var auth = make_base_auth($.cookie('username'), $.cookie('password'));
	var url = '/api/game/'+document.getElementById("gameId").value+'/move/'+start+'/'+target;
	var retval = false;

	$.ajax({
		headers : {
			"Authorization" : auth,
		},
		async : false,
		type : "POST",
		url : url,
		success : function(data) {
			reInit(data);
			retval = true;
		},
		error : function(jqXHR, textStatus, errorThrown) {
			console.log(textStatus + ' / ' + errorThrown);
			retval = false;
		}
	});

	return retval;
}
function findField(x, y) {
	for (var i = 0; i < fields.length; i++) {
		var xIsInCorrectRange = x >= fields[i].x
				&& x < (fields[i].x + FIELD_WIDTH);
		var yIsInCorrectRange = y >= fields[i].y
				&& y < (fields[i].y + fields[i].height);
		if (xIsInCorrectRange && yIsInCorrectRange) {
			return fields[i];
		}
	}
	return null;
}

CanvasState.prototype.draw = function() {
	if (this.redrawNeeded) {
		var checkers = this.checkers;
		checker_ctx.save();

		// Use the identity matrix while clearing the canvas
		checker_ctx.setTransform(1, 0, 0, 1, 0, 0);
		checker_ctx.clearRect(0, 0, this.canvas.width, this.canvas.height);

		// Restore the transform
		checker_ctx.restore();

		
		// redraw dices first
		drawActiveDices();
		
		
		
		for (var i = 0; i < checkers.length; i++) {
			checkers[i].draw();
		}
		if (this.activeChecker != null) {
			checker_ctx.globalCompositeOperation = "source-over";
			this.activeChecker.draw();
		}
		
		this.redrawNeeded = false;
	}

};

CanvasState.prototype.sortCheckers = function() {
	this.checkers
			.sort(function(a, b) {
				var fieldA = findField(a.x, a.y);
				var fieldB = findField(b.x, b.y);
				if (fieldA.id < fieldB.id) {
					return -1;
				} else if (fieldA.id > fieldB.id) {
					return 1;
				} else {
					if (fieldA.checkerOffsetOrientation == CheckerOffsetOrientation.south) {
						if (a.y < b.y) {
							return -1;
						} else {
							return 1;
						}
					} else {
						if (a.y > b.y) {
							return -1;
						} else {
							return 1;
						}
					}
				}
			});
};

CanvasState.prototype.getTopCheckerForStartField = function() {
	var topChecker = null;
	var checkers = this.checkers;
	for (var i = 0; i < checkers.length; i++) {
		var currentChecker = checkers[i];
		var currentCheckerField = findField(currentChecker.x, currentChecker.y);
		if (currentCheckerField == this.startField) {
			if (topChecker == null) {
				topChecker = currentChecker;
			} else {
				var fieldIsSouthOriented = currentCheckerField.checkerOffsetOrientation == CheckerOffsetOrientation.south;
				var checkerLiesMoreSouth = currentChecker.y >= topChecker.y;
				var fieldIsNorthOriented = currentCheckerField.checkerOffsetOrientation == CheckerOffsetOrientation.north;
				var checkerLiesMoreNorth = currentChecker.y <= topChecker.y;
				if ((fieldIsSouthOriented && checkerLiesMoreSouth)
						|| (fieldIsNorthOriented && checkerLiesMoreNorth)) {
					topChecker = currentChecker;
				}
			}
		}
	}
	return topChecker;
};

// Creates an object with x and y defined,
// set to the mouse position relative to the state's canvas
// If you wanna be super-correct this can be tricky,
// we have to worry about padding and borders
CanvasState.prototype.getMouse = function(e) {

	var element = this.canvas, offsetX = 0, offsetY = 0, mx, my;

	// Compute the total offset
	if (element.offsetParent !== undefined) {
		do {
			offsetX += element.offsetLeft;
			offsetY += element.offsetTop;
		} while ((element = element.offsetParent));
	}

	mx = e.pageX - offsetX;
	my = e.pageY - offsetY;

	// We return a simple javascript object (a hash) with x and y defined
	return {
		x : mx,
		y : my
	};
};

function Checker(color, field) {
	this.color = color;
	this.x = field.x;
	this.y = field.getNextCheckerYPosition();
	field.numberOfCheckers++;
}

Checker.prototype.draw = function() {
	if (this.color == Colors.black) {
		checker_ctx.drawImage(imgSprite, SPRITE_BLACK_CHECKER_X,
				SPRITE_BLACK_CHECKER_Y, CHECKER_WIDTH, CHECKER_WIDTH, this.x,
				this.y, CHECKER_WIDTH, CHECKER_WIDTH);
	} else if (this.color == Colors.white) {
		checker_ctx.drawImage(imgSprite, SPRITE_WHITE_CHECKER_X,
				SPRITE_WHITE_CHECKER_Y, CHECKER_WIDTH, CHECKER_WIDTH, this.x,
				this.y, CHECKER_WIDTH, CHECKER_WIDTH);
	}
};

// enum for colors
var Colors = Object.freeze({
	"black" : 1,
	"white" : 2
});
