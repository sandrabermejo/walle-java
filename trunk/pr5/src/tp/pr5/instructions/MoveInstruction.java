package tp.pr5.instructions;

import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;
import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;
import tp.pr5.items.ItemContainer;

public class MoveInstruction implements Instruction {

	public Instruction parse(String cad) throws WrongInstructionFormatException {
		if (cad.equalsIgnoreCase("MOVE") || cad.equalsIgnoreCase("MOVER"))
			return new MoveInstruction();
		throw new WrongInstructionFormatException();
	}

	public void execute() throws InstructionExecutionException {
		this.navigation.move(); // may throw exception
		this.robot.getInstructionsExecuted().push(this); // apilamos la instruccion
		this.robot.addFuel(MoveInstruction.moveCost);
	}
	
	public void undo() {
		
		this.navigation.setCurrentHeading(this.navigation.getOppositeHeading()); // Gira 180 grados
		
		try {
			this.navigation.move();
		} catch (InstructionExecutionException e) {
			// Siempre se va a poder mover (por donde ha venido)
		}
		
		this.navigation.setCurrentHeading(this.navigation.getOppositeHeading()); // Gira 180 grados para volver a la direccion inicial
		this.robot.addFuel(-MoveInstruction.moveCost);
	}

	public String getHelp() {
		return "MOVE|MOVER";
	}

	public void configureContext(RobotEngine engine, NavigationModule navigation, ItemContainer robotContainer) {
		this.robot = engine;
		this.navigation = navigation;
	}
	
	private RobotEngine robot;
	private NavigationModule navigation;
	private static final int moveCost = -5;

}
