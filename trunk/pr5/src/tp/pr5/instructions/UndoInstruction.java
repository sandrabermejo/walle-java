package tp.pr5.instructions;

import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;
import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;
import tp.pr5.items.ItemContainer;

public class UndoInstruction implements Instruction {
	
	/**
	 * Constructor por defecto
	 */
	public UndoInstruction(){
	}

	public Instruction parse(String cad) throws WrongInstructionFormatException {
		if (cad.equalsIgnoreCase("UNDO") || cad.equalsIgnoreCase("DESHACER"))
			return new UndoInstruction();
		throw new WrongInstructionFormatException();
	}

	public void execute() throws InstructionExecutionException {
		if (this.robot.getInstructionsExecuted().isEmpty())
			throw new InstructionExecutionException("There is no instruction to undo");
		this.robot.getInstructionsExecuted().pop().undo(); // Desapila y deshace la ultima instruccion
	}

	public void undo() {
		// No procede
	}

	public String getHelp() {
		return "UNDO|DESHACER";
	}

	public void configureContext(RobotEngine engine, NavigationModule navigation, ItemContainer robotContainer) {
		this.robot = engine;
	}
	
	private RobotEngine robot;

}
