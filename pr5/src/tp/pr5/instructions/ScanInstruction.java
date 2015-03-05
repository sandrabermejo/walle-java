package tp.pr5.instructions;

import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;
import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;
import tp.pr5.items.ItemContainer;

public class ScanInstruction implements Instruction {
	
	/**
	 * Constructor por defecto
	 */
	public ScanInstruction() {
		this.id = null;
	}

	/**
	 * Constructor con parametros
	 * @param string
	 */
	public ScanInstruction(String string) {
		this.id = string;
	}

	public Instruction parse(String cad) throws WrongInstructionFormatException {
		String cadena[] = cad.split(" ");
		int tamanyo = cadena.length;
		if (cadena[0].equalsIgnoreCase("SCAN") || cadena[0].equalsIgnoreCase("ESCANEAR")){
			if (tamanyo == 1)
				return new ScanInstruction();
			if (tamanyo == 2)
				return new ScanInstruction(cadena[1]);
		}
		throw new WrongInstructionFormatException();
	}

	public void execute() throws InstructionExecutionException {
		if (this.id == null) // Scan sin parametro
			this.container.requestScanCollection();
		else { // Scan con parametro
			if (!this.container.containsItem(this.id)) // No tiene el item
				throw new InstructionExecutionException("I have no such object");
			else
				this.container.requestScanItem(this.id);
		}		
	}
	
	public void undo() {
		// No procede
	}

	public String getHelp() {
		return "SCAN|ESCANEAR [ <id> ]";
	}

	public void configureContext(RobotEngine engine, NavigationModule navigation, ItemContainer robotContainer) {
		this.container = robotContainer;
	}
	
	private String id;
	private ItemContainer container;

}
