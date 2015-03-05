package tp.pr5.items;

import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;
import tp.pr5.Street;

public class CodeCard extends Item {

	/**
	 * Constructor
	 * 
	 * @param id
	 * @param description
	 * @param code
	 */
	public CodeCard(String id, String description, String code) {
		super(id, description);
		this.code = code;
		this.volatil = false;
	}
	
	/**
	 * 
	 * @return True, las tarjetas siempre se pueden usar
	 */
	public boolean canBeUsed(){
		return true;
	}	
	
	/**
	 * 
	 * @return True si se ha podido completar la accion de abrir / cerrar una puerta
	 */
	public boolean use(RobotEngine robot, NavigationModule navigation) {
		Street street = navigation.getHeadingStreet();
		if (street == null)
			return false;
		if (street.isOpen())
			return street.close(this);
		else
			return street.open(this);
	}
	
	public String getCode() {
		return this.code;
	}
	
	private String code;
}
