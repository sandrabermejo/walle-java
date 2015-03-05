package tp.pr5.console;

import java.util.Scanner;

import tp.pr5.Controller;
import tp.pr5.Interpreter;
import tp.pr5.RobotEngine;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;
import tp.pr5.instructions.Instruction;

public class ConsoleController extends Controller {

	public ConsoleController(RobotEngine robot) {
		super(robot);
	}
	
	/**
	 * Bucle de la simulacion
	 * 
	 */
	public void startController() {
		this.robot.requestStart();
		
		Scanner sc = new Scanner (System.in); // Creamos el scanner
		String line;
		Instruction instruction;
		do {
			this.robot.saySomething("WALL·E> "); // Mostramos el prompt
			line = sc.nextLine(); // Leemos la siguiente linea de teclado
			try {
				instruction = Interpreter.generateInstruction(line); // may throw exception
				this.robot.communicateRobot(instruction);
			} catch (WrongInstructionFormatException e){
				this.robot.requestError(e.getMessage());
			}
		} while(!this.robot.isOver());
		sc.close(); // Cerramos el scanner	
	}
}
