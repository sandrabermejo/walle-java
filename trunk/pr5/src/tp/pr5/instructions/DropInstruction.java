package tp.pr5.instructions;

import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;
import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;
import tp.pr5.items.Item;
import tp.pr5.items.ItemContainer;

public class DropInstruction implements Instruction {
	
	/**
	 * Constructor por defecto
	 */
	public DropInstruction() {
		this.id = null;
	}
	
	/**
	 * Constructor con parametros
	 * @param string
	 */
	public DropInstruction(String string) {
		this.id = string;
	}
	
	public Instruction parse(String cad) throws WrongInstructionFormatException {
		String cadena[] = cad.split(" ");
		int tamanyo = cadena.length;
		if ((cadena[0].equalsIgnoreCase("DROP") || cadena[0].equalsIgnoreCase("SOLTAR")) && (tamanyo == 2)) {
			return new DropInstruction(cadena[1]);
		}
		throw new WrongInstructionFormatException();
	}

	public void execute() throws InstructionExecutionException {
		if ((this.id == null) || (this.id.equals("")))
			throw new InstructionExecutionException("Ooops, you haven't said which object you want to drop!");
		Item item = this.container.getItem(this.id);
		if (item == null) // El item no esta en el inventario
			throw new InstructionExecutionException("You do not have any " + this.id + ".");
		else { // Esta en el inventario
			if (this.navigation.findItemAtCurrentPlace(this.id))
				throw new InstructionExecutionException("The place had already the object" + this.id);
			else {
				this.navigation.dropItemAtCurrentPlace(item);
				this.container.pickItem(this.id);
				this.robot.getInstructionsExecuted().push(this); // apilamos la instruccion
				this.robot.saySomething("WALL·E says: Great! I have dropped " + this.id + "\n");
			}
		}
	}
	
	public void undo() {
		PickInstruction pick = new PickInstruction(this.id);
		this.robot.communicateRobot(pick);
		this.robot.getInstructionsExecuted().pop(); // Desapilamos el pick
	}

	public String getHelp() {
		return "DROP|SOLTAR <id>";
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