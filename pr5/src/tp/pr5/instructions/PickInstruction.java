package tp.pr5.instructions;

import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;
import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;
import tp.pr5.items.ItemContainer;

public class PickInstruction implements Instruction {
	
	/**
	 * Constructor por defecto
	 */
	public PickInstruction() {
		this.id = null;
	}
	
	/**
	 * Constructor con parametros
	 * @param string
	 */
	public PickInstruction(String string) {
		this.id = string;
	}
	
	public Instruction parse(String cad) throws WrongInstructionFormatException {
		String cadena[] = cad.split(" ");
		int tamanyo = cadena.length;
		if ((cadena[0].equalsIgnoreCase("PICK") || cadena[0].equalsIgnoreCase("COGER")) && (tamanyo == 2)) {
			return new PickInstruction(cadena[1]);
		}
		throw new WrongInstructionFormatException();
	}

	public void execute() throws InstructionExecutionException {
		if ((this.id == null) || (this.id.equals("")))
			throw new InstructionExecutionException("Ooops, you haven't said which object you want to pick!");
		if (!this.navigation.findItemAtCurrentPlace(this.id)) // El item no esta en el lugar
			throw new InstructionExecutionException("Ooops, this place has not the object " + this.id);
		else { // Esta en el lugar
			if (this.container.addItem(this.navigation.getItemFromCurrentPlace(this.id))) {
				this.navigation.pickItemFromCurrentPlace(this.id); // Se borra del lugar
				this.robot.getInstructionsExecuted().push(this); // apilamos la instruccion
				this.robot.saySomething("WALL·E says: I am happy! Now I have " + this.id + "\n");
			}								
			else // Ya tiene el item
				throw new InstructionExecutionException("I am stupid! I had already the object " + this.id);
		}
	}
	
	public void undo() {
		DropInstruction drop = new DropInstruction(this.id);
		this.robot.communicateRobot(drop);
		this.robot.getInstructionsExecuted().pop(); // Desapilamos el drop
	}

	public String getHelp() {
		return "PICK|COGER <id>";
	}

	public void configureContext(RobotEngine engine, NavigationModule navigation, ItemContainer robotContainer) {
		this.robot = engine;
		this.navigation = navigation;
		this.container = robotContainer;
	}
	
	private RobotEngine robot;
	private NavigationModule navigation;
	private ItemContainer container;
	private String id;
}

