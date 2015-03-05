package tp.pr5.instructions;

import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;
import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;
import tp.pr5.items.*;

public class OperateInstruction implements Instruction {
	
	/**
	 * Constructor por defecto
	 */
	public OperateInstruction(){
		this.id = null;
	}
	
	/**
	 * Constructor con parametros
	 * @param string
	 */
	public OperateInstruction(String string) {
		this.id = string;
	}

	public Instruction parse(String cad) throws WrongInstructionFormatException {
		String cadena[] = cad.split(" ");
		int tamanyo = cadena.length;
		if ((cadena[0].equalsIgnoreCase("OPERATE") || cadena[0].equalsIgnoreCase("OPERAR")) && (tamanyo == 2))
			return new OperateInstruction(cadena[1]);
		throw new WrongInstructionFormatException();
	}

	public void execute() throws InstructionExecutionException {
		if ((this.id == null) || (this.id.equals("")))
			throw new InstructionExecutionException("Ooops, you haven't said which object you want to operate!");
		Item item = this.container.getItem(this.id);
		if (item == null) // No tiene el item
			throw new InstructionExecutionException("I have no such object");
		else { 
			// Tiene el item
			if (item.use(this.robot, this.navigation)) { // Se ha podido usar
				this.container.useItem(item);
				this.robot.getInstructionsExecuted().push(this); // apilamos la instruccion
				this.robot.getItemsOperated().push(item); // apilamos el item usado					
			}
			else // No se ha podido usar
				throw new InstructionExecutionException("I have problems using the object " + this.id);
		}
	}
	
	public void undo() {
		Item item = this.robot.getItemsOperated().pop(); // Devuelve el ultimo elemento usado y lo elimina de la pila
		if (item.esVolatil())
			((ItemVolatil) item).unUse(this.robot, this.navigation);
		else // Un item no volatil (CodeCard) se desusa usandolo de nuevo
			item.use(this.robot, this.navigation);
		this.container.emitInventoryChange();
	}

	public String getHelp() {
		return "OPERATE|OPERAR <id>";
	}

	public void configureContext(RobotEngine engine, NavigationModule navigation, ItemContainer robotContainer) {
		this.robot = engine;
		this.navigation = navigation;
		this.container = robotContainer;
		
	}
	
	private String id;
	private RobotEngine robot;
	private NavigationModule navigation;
	private ItemContainer container;
	
}
