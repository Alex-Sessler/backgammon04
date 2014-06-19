package service.impl.exception;

public class ForbiddenMoveException extends Exception {

	private static final long serialVersionUID = -2370770515216240912L;
	
	public ForbiddenMoveException() {
	}
	
	public ForbiddenMoveException(String message) {
		super(message);
	}

}
