package tp.pr5.instructions;

import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;
import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;
import tp.pr5.items.ItemContainer;

public interface Instruction {
	
	/**
	 * Parsea una cadena
	 * @param cad
	 * @return Una instruccion del tipo que corresponda a cad
	 * @throws WrongInstructionFormatException
	 */
	public Instruction parse(String cad) throws WrongInstructionFormatException;
	
	/**
	 * Ejecuta la instruccion
	 * @throws InstructionExecutionException
	 */
	public void execute() throws InstructionExecutionException;
	
	/**
	 * Deshace la instruccion
	 */
	public void undo();
	
	/**
	 * 
	 * @return El identificador de la instruccion
	 */
	public String getHelp();
	
	/**
	 * Configura el contexto de ejecucion
	 * @param engine
	 * @param navigation
	 * @param robotContainer
	 */
	public void configureContext(RobotEngine engine, NavigationModule navigation, ItemContainer robotContainer);
}
