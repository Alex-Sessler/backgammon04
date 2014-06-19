var DICES_Y = 285;
var TWO_DICES_FIRST_DICE_X = 145;
var TWO_DICES_SECOND_DICE_X = 505;

var FOUR_DICES_FIRST_X = 55;
var FOUR_DICES_SECOND_X = 235;
var FOUR_DICES_THIRD_X = 415;
var FOUR_DICES_FOURTH_X = 595;

var activeDices = new Array();

var dice_canvas = document.getElementById("dice_canvas");
var dice_ctx = checker_canvas.getContext("2d");

/**
 * Draws the two dices (or four if the values are the same)
 * 
 * @param firstValue
 * @param secondValue
 */
function drawDices(firstValue, secondValue) {

	clearDiceArea();

	if (firstValue == secondValue) {
		activeDices[0] = getNewDiceForValue(firstValue, FOUR_DICES_FIRST_X);
		activeDices[1] = getNewDiceForValue(firstValue, FOUR_DICES_SECOND_X);
		activeDices[2] = getNewDiceForValue(firstValue, FOUR_DICES_THIRD_X);
		activeDices[3] = getNewDiceForValue(firstValue, FOUR_DICES_FOURTH_X);
	} else {
		activeDices[0] = getNewDiceForValue(firstValue, TWO_DICES_FIRST_DICE_X);
		activeDices[1] = getNewDiceForValue(secondValue,
				TWO_DICES_SECOND_DICE_X);

	}

	drawActiveDices();
}

/**
 * Clears the dice area
 */
function clearDiceArea() {

	// Use the identity matrix while clearing the canvas
	dice_ctx.save();

	dice_ctx.setTransform(1, 0, 0, 1, 0, 0);
	dice_ctx.clearRect(0, DICES_Y, dice_canvas.width, SPRITE_DICE_HEIGHT);

	// Restore the transform
	dice_ctx.restore();

	activeDices = new Array();
}

/**
 * Replaces the dice image of the dice with value with a grayed out version
 * 
 * @param value
 *            the value of the dice to gray out
 */
function markDiceAsUsed(value) {
	for (var i = 0; i < activeDices.length; i++) {
		if (activeDices[i].value == value && activeDices[i].used == false) {
			activeDices[i].used = true;
			break;
		}

	}
	drawActiveDices();
}

/**
 * Used to refresh the dices
 */
function drawActiveDices() {
	for (var i = 0; i < activeDices.length; i++) {
		if (activeDices[i].used) {
			dice_ctx.drawImage(imgSprite, activeDices[i].spriteX,
					SPRITE_DICES_DARK_Y, SPRITE_DICE_WIDTH, SPRITE_DICE_HEIGHT,
					activeDices[i].x, DICES_Y, SPRITE_DICE_WIDTH,
					SPRITE_DICE_HEIGHT);
		} else {
			dice_ctx.drawImage(imgSprite, activeDices[i].spriteX,
					SPRITE_DICES_Y, SPRITE_DICE_WIDTH, SPRITE_DICE_HEIGHT,
					activeDices[i].x, DICES_Y, SPRITE_DICE_WIDTH,
					SPRITE_DICE_HEIGHT);
		}
	}
}

function Dice(value, x, spriteX) {
	this.value = value;
	this.x = x;
	this.spriteX = spriteX;
	this.used = false;
}

function getNewDiceForValue(value, x) {
	switch (value) {
	case 1:
		return new Dice(1, x, SPRITE_DICE_ONE_X, SPRITE_DICES_Y);
		break;
	case 2:
		return new Dice(2, x, SPRITE_DICE_TWO_X, SPRITE_DICES_Y);
		break;
	case 3:
		return new Dice(3, x, SPRITE_DICE_THREE_X, SPRITE_DICES_Y);
		break;
	case 4:
		return new Dice(4, x, SPRITE_DICE_FOUR_X, SPRITE_DICES_Y);
		break;
	case 5:
		return new Dice(5, x, SPRITE_DICE_FIVE_X, SPRITE_DICES_Y);
		break;
	case 6:
		return new Dice(6, x, SPRITE_DICE_SIX_X, SPRITE_DICES_Y);
		break;
	}
}

function playDice() {
	var val1 = 0;
	var val2 = 0;
	
	var retval = playDiceConnection();
	if(retval) {
		val1 = retval[0].value;
		val2 = retval[1].value;
	} else {
		alert("You are not permittet to play!");
		return false;
	}
	
	drawDices(val1, val2);
}

function playDiceConnection() {
	var auth = make_base_auth($.cookie('username'), $.cookie('password'));
	var url = '/api/game/'+document.getElementById("gameId").value+'/dice';
	var retval = false;
	var value = 0;
	
	$.ajax({
		headers : {
			"Authorization" : auth,
		},
		async : false,
		type : "POST",
		url : url,
		success : function(data) {
			value = data;
			retval = true;
		},
		error : function(jqXHR, textStatus, errorThrown) {
			console.log(textStatus + ' / ' + errorThrown);
			retval = false;
		}
	});

	if(retval) {
		return value;
	} else {
		return false;
	}
}
