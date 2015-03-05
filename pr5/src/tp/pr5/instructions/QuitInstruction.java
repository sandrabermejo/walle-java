package tp.pr5.instructions;

import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;
import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;
import tp.pr5.items.ItemContainer;

public class QuitInstruction implements Instruction {

	public Instruction parse(String cad) throws WrongInstructionFormatException {
		if (cad.equalsIgnoreCase("QUIT") || cad.equalsIgnoreCase("SALIR"))
			return new QuitInstruction();
		throw new WrongInstructionFormatException();
	}

	public void execute() throws InstructionExecutionException {
		this.robot.requestQuit();		
	}
	
	public void undo() {
		// No procede
	}

	public String getHelp() {
		return "QUIT|SALIR";
	}

	public void configureContext(RobotEngine engine, NavigationModule navigation, ItemContainer robotContainer) {
		this.robot = engine;
	}
	
	private RobotEngine robot;
}
