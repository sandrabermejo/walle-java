package tp.pr5.instructions.exceptions;

public class WrongInstructionFormatException extends Exception {
	
	public WrongInstructionFormatException() {
		super();
	}
	
	public WrongInstructionFormatException(String arg0) {
		super(arg0);
	}
	
	public WrongInstructionFormatException(Throwable arg0) {
		super(arg0);
	}
	
	public WrongInstructionFormatException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	private static final long serialVersionUID = 1L;
}
