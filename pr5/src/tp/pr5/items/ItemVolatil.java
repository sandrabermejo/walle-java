package tp.pr5.items;

import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;

public abstract class ItemVolatil extends Item {

	/**
	 * Constructor
	 * 
	 * @param id
	 * @param description
	 * @param times
	 */
	public ItemVolatil(String id, String description, int times) {
		super(id, description);
		this.times = times;
		this.volatil = true;
	}

	/**
	 * 
	 * @return True si el itemVolatil puede seguir usandose
	 */
	public boolean canBeUsed(){
		return this.times != 0;
	}
	
	/**
	 * 
	 * @param robot
	 * @param place
	 * @return True si el itemVolatil se ha podido usar
	 */
	public boolean use(RobotEngine robot, NavigationModule navigation) {
		if (!this.canBeUsed())
			return false;
		// uso efectivo
		if (doUse(robot, navigation)){
			this.times--;
			return true;
		}
		else 
			return false;
	}
	
	/**
	 * Desusa el item
	 */
	public void unUse(RobotEngine robot, NavigationModule navigation){
		if (this.times == 0)
			robot.addItemToContainer(this);
		this.times++;
		doUnUse(robot, navigation);
	}
	
	/**
	 * Metodo abstracto
	 * 
	 * @param robot
	 * @param place
	 * @return True si el itemVolatil se ha usado
	 */
	public abstract boolean doUse(RobotEngine robot, NavigationModule navigation);
	
	/**
	 * Metodo abstracto
	 * 
	 * @param robot
	 * @param navigation
	 */
	public abstract void doUnUse(RobotEngine robot, NavigationModule navigation);
		
	protected int times;

}
