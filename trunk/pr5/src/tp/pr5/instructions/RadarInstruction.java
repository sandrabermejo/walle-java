package tp.pr5.instructions;

import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;
import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;
import tp.pr5.items.ItemContainer;

public class RadarInstruction implements Instruction {
	
	/**
	 * Constructor por defecto
	 */
	public RadarInstruction(){
	}

	public Instruction parse(String cad) throws WrongInstructionFormatException {
		if (cad.equalsIgnoreCase("RADAR"))
			return new RadarInstruction();
		throw new WrongInstructionFormatException();
	}

	public void execute() throws InstructionExecutionException {
		this.navigation.scanCurrentPlace();
	}
	
	public void undo() {
		// No procede
	}

	public String getHelp() {
		return "RADAR";
	}

	public void configureContext(RobotEngine engine, NavigationModule navigation, ItemContainer robotContainer) {
		this.navigation = navigation;
	}
	
	private NavigationModule navigation;
}
