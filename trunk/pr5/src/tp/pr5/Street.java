package tp.pr5;

import tp.pr5.items.CodeCard;

public class Street {

	/**
	 * Constructor para calle sin puerta
	 * 
	 * @param source
	 * @param direction
	 * @param target
	 */
	public Street(Place source, Direction direction, Place target) {
		this.source = source;		
		this.direction = direction;
		this.target = target;
		this.isOpen = true;
		this.code = null;
	}
	
	/**
	 * Constructor para calle con puerta
	 * 
	 * @param source
	 * @param direction
	 * @param target
	 * @param isOpen
	 * @param code
	 */
	public Street(Place source, Direction direction, Place target, boolean isOpen, String code) {
		this.source = source;		
		this.direction = direction;
		this.target = target;
		this.isOpen = isOpen;
		this.code = code;
	}
	
	/**
	 * Comprueba si la calle comunica con el lugar dado
	 * 
	 * @param place
	 * @param whichDirection
	 * @return True si la calle comunica el lugar dado en la direccion dada
	 */
	public boolean comeOutFrom(Place place, Direction whichDirection){ 
		// La calle procede del lugar en la direccion dada
		if ((this.source.equals(place)) && (this.direction.equals(whichDirection)))
			return true;
		// La calle llega al lugar en la direccion opuesta a la dada
		if ((this.target.equals(place))	&& (this.direction.equals(whichDirection.opposite())))
			return true;
		return false;
	}
	
	/**
	 * 
	 * @param whereAmI
	 * @return El lugar al otro lado de la calle
	 */
	public Place nextPlace(Place whereAmI){
		if (this.source.getName().equals(whereAmI.getName()))
			return this.target;
		if (this.target.getName().equals(whereAmI.getName()))
			return this.source;
		return null; // Si la calle no comunica con el lugar dado
	}
	
	/**
	 * 
	 * @param card
	 * @return True si la puerta se ha cerrado
	 */
	public boolean close(CodeCard card) {
		if (!this.isOpen) // Ya esta cerrada
			return true;		
		if (this.code == null) // No hay puerta
			return false;
		
		// Esta abierta
		if(this.code.equals(card.getCode())){ // El codigo es valido
			this.isOpen = false;
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param card
	 * @return True si la puerta se ha abierto
	 */
	public boolean open(CodeCard card) {
		if (this.isOpen) // Ya esta abierta o no hay puerta
			return true;
		
		// Esta cerrada		
		if(this.code.equals(card.getCode())){ // El codigo es valido
			this.isOpen = true;
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @return True si la puerta esta abierta
	 */
	public boolean isOpen() {
		return this.isOpen;
	}
	
	private Place source;
	private Place target;
	private Direction direction;
	private boolean isOpen;
	private String code;
	

}
