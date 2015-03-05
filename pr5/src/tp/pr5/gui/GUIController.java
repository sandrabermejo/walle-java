package tp.pr5.gui;

import tp.pr5.Controller;
import tp.pr5.RobotEngine;
import tp.pr5.instructions.Instruction;

public class GUIController extends Controller {
	
	/**
	 * Constructor
	 * 
	 * @param robot
	 */
	public GUIController(RobotEngine robot) {
		super(robot);
	}
	
	
	public void startController() {
		this.robot.requestStart();
	}
	
	/**
	 * Pide al modelo que ejecute una instruccion 
	 */
	public void communicateInstruction(Instruction i) {
		this.robot.communicateRobot(i);
	}
	
	public void arriveAtSpaceship(){
		
	}
}
