package tp.pr5.instructions.exceptions;

public class InstructionExecutionException extends Exception {
	
	public InstructionExecutionException() {
		super();
	}
	
	public InstructionExecutionException(String arg0){
		super(arg0);
	}
	
	public InstructionExecutionException(Throwable arg0) {
		super(arg0);
	}
	
	public InstructionExecutionException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	private static final long serialVersionUID = 1L;

}
