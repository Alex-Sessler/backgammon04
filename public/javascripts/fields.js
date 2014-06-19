var FIELD_WIDTH = 48;
var REGULAR_FIELD_HEIGHT = 220;

var PENALTY_CHECKERS_FIELD_HEIGHT = 182;

var FINISHED_CHECKERS_FIELD_HEIGHT = 308;

var FIRST_CHECKER_TOP_ROW = 34;
var FIRST_CHECKER_BOTTOM_ROW = 563;

var fields = new Array();

/**
 *	ID, X, Y, height, firstCheckerYPosition, checkerOffsetOrientation
 */
function Field(id, x, y, height, firstCheckerYPosition, checkerOffsetOrientation) {
	this.id = id;
	this.x = x;
	this.y = y;
	this.height = height;
	this.numberOfCheckers = 0;
	this.firstCheckerYPosition = firstCheckerYPosition;
	this.checkerOffsetOrientation = checkerOffsetOrientation;
}

function initFields() {
	fields[0] = new Field(1, 639, 388, REGULAR_FIELD_HEIGHT, FIRST_CHECKER_BOTTOM_ROW, CheckerOffsetOrientation.north);
	fields[1] = new Field(2, 589, 388, REGULAR_FIELD_HEIGHT, FIRST_CHECKER_BOTTOM_ROW, CheckerOffsetOrientation.north);
	fields[2] = new Field(3, 540, 388, REGULAR_FIELD_HEIGHT, FIRST_CHECKER_BOTTOM_ROW, CheckerOffsetOrientation.north);
	fields[3] = new Field(4, 491, 388, REGULAR_FIELD_HEIGHT, FIRST_CHECKER_BOTTOM_ROW, CheckerOffsetOrientation.north);
	fields[4] = new Field(5, 442, 388, REGULAR_FIELD_HEIGHT, FIRST_CHECKER_BOTTOM_ROW, CheckerOffsetOrientation.north);
	fields[5] = new Field(6, 393, 388, REGULAR_FIELD_HEIGHT, FIRST_CHECKER_BOTTOM_ROW, CheckerOffsetOrientation.north);
	fields[6] = new Field(7, 281, 388, REGULAR_FIELD_HEIGHT, FIRST_CHECKER_BOTTOM_ROW, CheckerOffsetOrientation.north);
	fields[7] = new Field(8, 231, 388, REGULAR_FIELD_HEIGHT, FIRST_CHECKER_BOTTOM_ROW, CheckerOffsetOrientation.north);
	fields[8] = new Field(9, 182, 388, REGULAR_FIELD_HEIGHT, FIRST_CHECKER_BOTTOM_ROW, CheckerOffsetOrientation.north);
	fields[9] = new Field(10, 133, 388, REGULAR_FIELD_HEIGHT, FIRST_CHECKER_BOTTOM_ROW, CheckerOffsetOrientation.north);
	fields[10] = new Field(11, 84, 388, REGULAR_FIELD_HEIGHT, FIRST_CHECKER_BOTTOM_ROW, CheckerOffsetOrientation.north);
	fields[11] = new Field(12, 35, 388, REGULAR_FIELD_HEIGHT, FIRST_CHECKER_BOTTOM_ROW, CheckerOffsetOrientation.north);
	fields[12] = new Field(13, 36, 33, REGULAR_FIELD_HEIGHT, FIRST_CHECKER_TOP_ROW, CheckerOffsetOrientation.south);
	fields[13] = new Field(14, 86, 33, REGULAR_FIELD_HEIGHT, FIRST_CHECKER_TOP_ROW, CheckerOffsetOrientation.south);
	fields[14] = new Field(15, 135, 33, REGULAR_FIELD_HEIGHT, FIRST_CHECKER_TOP_ROW, CheckerOffsetOrientation.south);
	fields[15] = new Field(16, 184, 33, REGULAR_FIELD_HEIGHT, FIRST_CHECKER_TOP_ROW, CheckerOffsetOrientation.south);
	fields[16] = new Field(17, 233, 33, REGULAR_FIELD_HEIGHT, FIRST_CHECKER_TOP_ROW, CheckerOffsetOrientation.south);
	fields[17] = new Field(18, 283, 33, REGULAR_FIELD_HEIGHT, FIRST_CHECKER_TOP_ROW, CheckerOffsetOrientation.south);
	fields[18] = new Field(19, 395, 33, REGULAR_FIELD_HEIGHT, FIRST_CHECKER_TOP_ROW, CheckerOffsetOrientation.south);
	fields[19] = new Field(20, 444, 33, REGULAR_FIELD_HEIGHT, FIRST_CHECKER_TOP_ROW, CheckerOffsetOrientation.south);
	fields[20] = new Field(21, 493, 33, REGULAR_FIELD_HEIGHT, FIRST_CHECKER_TOP_ROW, CheckerOffsetOrientation.south);
	fields[21] = new Field(22, 542, 33, REGULAR_FIELD_HEIGHT, FIRST_CHECKER_TOP_ROW, CheckerOffsetOrientation.south);
	fields[22] = new Field(23, 591, 33, REGULAR_FIELD_HEIGHT, FIRST_CHECKER_TOP_ROW, CheckerOffsetOrientation.south);
	fields[23] = new Field(24, 641, 33, REGULAR_FIELD_HEIGHT, FIRST_CHECKER_TOP_ROW, CheckerOffsetOrientation.south);
	fields[24] = new Field(25, 335, 62, PENALTY_CHECKERS_FIELD_HEIGHT, 62, CheckerOffsetOrientation.south);
	fields[25] = new Field(26, 335, 398, PENALTY_CHECKERS_FIELD_HEIGHT, 531, CheckerOffsetOrientation.north);
	fields[26] = new Field(27, 686, 0, FINISHED_CHECKERS_FIELD_HEIGHT, 261, CheckerOffsetOrientation.north);
	fields[27] = new Field(28, 686, 329, FINISHED_CHECKERS_FIELD_HEIGHT, 329, CheckerOffsetOrientation.south);
}

Field.prototype.getNextCheckerYPosition = function() {
	return this.numberOfCheckers == 0 ? this.firstCheckerYPosition : 
		this.firstCheckerYPosition + (this.checkerOffsetOrientation * (this.numberOfCheckers * CHECKER_WIDTH/2));
};

var CheckerOffsetOrientation = Object.freeze({
	"south" : 1,
	"north" : -1
});