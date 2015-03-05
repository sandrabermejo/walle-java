package tp.pr5.items;

import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;

public class Garbage extends ItemVolatil {
	
	/**
	 * Constructor
	 * 
	 * @param id
	 * @param description
	 * @param recycledMaterial
	 */
	public Garbage(String id, String description, int recycledMaterial) {
		super(id, description, 1);
		this.recycledMaterial = recycledMaterial;
	}
	
	/**
	 * Uso efectivo de la basura
	 * 
	 * @param robot
	 * @param place
	 * @return True, siempre se puede a�adir material reciclado 
	 */
	public boolean doUse(RobotEngine robot, NavigationModule navigation) {
		robot.addRecycledMaterial(this.recycledMaterial);
		return true;
	}
	
	public void doUnUse(RobotEngine robot, NavigationModule navigation) {
		robot.addRecycledMaterial(-this.recycledMaterial);
	}
	
	/**
	 * 
	 * @return Un string con la descripcion de la basura y la cantidad de material reciclado que aporta
	 */
	public String toString() {
		return super.toString() + "// recycled material = " + this.recycledMaterial;		
	}
	
	private int recycledMaterial;
}
