package tp.pr5.items;

import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;

public class Fuel extends ItemVolatil {
	
	/**
	 * Constructor
	 * 
	 * @param id
	 * @param description
	 * @param power
	 * @param times
	 */
	public Fuel(String id, String description,int power, int times) {
		super(id, description, times);
		this.power = power;	
	}
	
	/**
	 * Uso efectivo del fuel
	 * 
	 * @param robot
	 * @param place
	 * @return True, siempre se puede aniadir fuel
	 */
	public boolean doUse(RobotEngine robot, NavigationModule navigation) {
		robot.addFuel(this.power);
		return true;
	}
	
	public void doUnUse(RobotEngine robot, NavigationModule navigation) {
		robot.addFuel(-this.power);
	}
	
	/**
	 * 
	 * @return Un string con la descripcion del fuel, la energia que aporta y el numero de veces que puede ser usado
	 */
	public String toString() {
		return super.toString() + "// power = " + this.power + ", times = " + this.times;		
	}
	
	private int power;
}
