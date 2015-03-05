package tp.pr5.instructions;

import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;
import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;
import tp.pr5.items.ItemContainer;

public class HelpInstruction implements Instruction {
	
	/**
	 * Constructor por defecto
	 */
	public HelpInstruction() {
	}
	
	public Instruction parse(String cad) throws WrongInstructionFormatException {
		if (cad.equalsIgnoreCase("HELP") || cad.equalsIgnoreCase("AYUDA"))
			return new HelpInstruction();
		throw new WrongInstructionFormatException();
	}
	
	public void execute() throws InstructionExecutionException {
		this.robot.requestHelp(); 
	}
	
	public void undo() {
		// No procede
	}
	
	public String getHelp() {
		return "HELP|AYUDA";
	}
	
	public void configureContext(RobotEngine engine, NavigationModule navigation, ItemContainer robotContainer) {
		this.robot = engine;
	}
	
	private RobotEngine robot;
}
