package tp.pr5.items;

import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;

public abstract class Item {
	
	/**
	 * Constructor
	 * 
	 * @param id
	 * @param description
	 */
	public Item(String id, String description) {
		this.id = id;
		this.description = description;
		
	}
	
	/**
	 * 
	 * @return Un string con la descripcion del item
	 */
	public String toString() {
		return this.description;
	}
	
	public String getId() {
		return this.id;		
	}
	
	/**
	 * Metodo abstracto
	 * 
	 * @return True, si el item puede usarse
	 */
	public abstract boolean canBeUsed();
	
	/**
	 * Metodo abstracto
	 * 
	 * @param robot
	 * @param place
	 * @return True, si el item se ha usado
	 */
	public abstract boolean use(RobotEngine robot, NavigationModule navigation);
	
	/**
	 * 
	 * @return True si el item es volatil
	 */
	public boolean esVolatil(){
		return this.volatil;
	}	
	
	private String id;
	private String description;
	protected boolean volatil;
}
