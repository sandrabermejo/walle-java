package tp.pr5;

import tp.pr5.instructions.*;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;

public class Interpreter {

	/**
	 * 
	 * @param line
	 * @return Una instruccion segun el string recibido
	 */
	public static Instruction generateInstruction(String line) throws WrongInstructionFormatException{
		for (Instruction op: Interpreter.instrucciones) {
			try {
				return op.parse(line);			
			}catch (WrongInstructionFormatException e){}
		}
		throw new WrongInstructionFormatException("I do not understand. Please repeat");	
	}

	/**
	 * 
	 * @return Un string con las instrucciones validas para WALL·E
	 */
	public static String interpreterHelp() {
		String cad = "The valid instructions for WALL-E are:";
		for (Instruction op: Interpreter.instrucciones)
			cad += LINE_SEPARATOR + "     " + op.getHelp();
		return cad;
	}
	private static Instruction instrucciones[] = {
		new DropInstruction(),
		new HelpInstruction(),
		new MoveInstruction(),
		new OperateInstruction(),
		new PickInstruction(),
		new QuitInstruction(),
		new RadarInstruction(),
		new ScanInstruction(),
		new TurnInstruction(),
		new UndoInstruction()
	};
	
	private static final String LINE_SEPARATOR = System.getProperty("line.separator");
	
}
