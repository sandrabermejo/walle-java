package tp.pr5.instructions;

import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;
import tp.pr5.Rotation;
import tp.pr5.instructions.exceptions.*;
import tp.pr5.items.ItemContainer;

public class TurnInstruction implements Instruction {
	
	/**
	 * Constructor por defecto
	 */
	public TurnInstruction() {
		this.turn = Rotation.UNKNOWN;
	}

	/**
	 * Constructor con parametros
	 * @param string
	 */
	public TurnInstruction(Rotation rotation) {
		this.turn = rotation;
	}

	public Instruction parse(String cad) throws WrongInstructionFormatException {
		String cadena[] = cad.split(" ");
		int tamanyo = cadena.length;
		if ((cadena[0].equalsIgnoreCase("TURN") || cadena[0].equalsIgnoreCase("GIRAR")) && (tamanyo == 2)) {
			if (cadena[1].equalsIgnoreCase("RIGHT"))
				return new TurnInstruction(Rotation.RIGHT);
			if (cadena[1].equalsIgnoreCase("LEFT"))
				return new TurnInstruction(Rotation.LEFT);
		}
		throw new WrongInstructionFormatException();
	}

	public void execute() throws InstructionExecutionException {
		// WALL·E cambia de direccion segun el giro
		this.navigation.rotate(this.turn);
		this.robot.getInstructionsExecuted().push(this); // apilamos la instruccion
		this.robot.addFuel(TurnInstruction.turnCost);						
	}
	
	public void undo() {
		this.navigation.rotate(this.turn.oppositeRotation());
		this.robot.addFuel(-TurnInstruction.turnCost);
	}

	public String getHelp() {
		return "TURN | GIRAR < LEFT|RIGHT >";
	}

	public void configureContext(RobotEngine engine, NavigationModule navigation, ItemContainer robotContainer) {
		this.robot = engine;
		this.navigation = navigation;
	}
	
	private RobotEngine robot;
	private NavigationModule navigation;
	private Rotation turn;	
	private static final int turnCost = -5;
}
