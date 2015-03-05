package tp.pr5;

import java.util.ArrayList;

import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.items.Item;

public class NavigationModule extends Observable<NavigationObserver>{
	
	public NavigationModule (City aCity, Place initialPlace) {
		this.city = aCity;
		this.whereIAm = initialPlace;
		this.whereILook = Direction.UNKNOWN;
		this.observers = new ArrayList<NavigationObserver>();
	}
	
	/**
	 * Comprueba si el robot ha llegado a la nave
	 * 
	 * @return True si el lugar actual es la nave
	 */
	public boolean atSpaceship() {
		return this.whereIAm.isSpaceship();
	}
	
	/**
	 * Suelta el item en el lugar actual (asume que no hay otro item igual ahi)
	 * 
	 * @param it
	 */
	public void dropItemAtCurrentPlace(Item it) {
		this.whereIAm.addItem(it);
		for (NavigationObserver o : this.observers)
			o.placeHasChanged(this.whereIAm);
	} 
	
	/**
	 * Comprueba si hay un item con ese id en el lugar
	 * 
	 * @param id
	 * @return True si esta el item
	 */
	public boolean findItemAtCurrentPlace(String id) {
		if (this.whereIAm.container.getItem(id) != null)
			return true;
		return false;
	}
	
	/**
	 * Funcion auxiliar que devuelve el item solicitado del lugar actual
	 * @param id
	 * @return
	 */
	public Item getItemFromCurrentPlace(String id) {
		return this.whereIAm.container.getItem(id);
	}
	
	/**
	 * Intenta coger un item del lugar actual
	 * 
	 * @param id
	 * @return El item buscado o null si no esta
	 */
	public Item pickItemFromCurrentPlace(String id) {
		Item item = this.whereIAm.container.pickItem(id);
		for (NavigationObserver o : this.observers)
			o.placeHasChanged(this.whereIAm);
		return item;
	}
	
	/**
	 * Inicializa la direccion a la que mira el robot
	 * 
	 * @param heading
	 */
	public void initHeading(Direction heading) {
		this.whereILook = heading;
		for (NavigationObserver o : this.observers)
			o.initNavigationModule(this.whereIAm, heading);
	}
	
	/**
	 * Intenta mover el robot en la direccion actual
	 * 
	 */
	public void move() throws InstructionExecutionException {
		Street street = this.getHeadingStreet();
		if (street == null) // No existe calle
			throw new InstructionExecutionException("There is no street in direction " + this.whereILook);
		else { // Existe una calle
			if (street.isOpen()) {
				this.whereIAm = street.nextPlace(this.whereIAm);
				for (NavigationObserver o : this.observers)
					o.robotArrivesAtPlace(this.whereILook, this.whereIAm);
			}
			else
				throw new InstructionExecutionException("Arrggg, there is a street but it is closed!");
		}
	}
	
	/**
	 * Cambia la direccion del robot segun el parametro
	 * 
	 * @param rotation
	 */
	public void rotate(Rotation rotation) {
		this.whereILook = this.whereILook.changeDirection(rotation);
		for (NavigationObserver o : this.observers)
			o.headingChanged(this.whereILook);
	}
	
	/**
	 * Funcion auxiliar
	 * 
	 * @return la direccion opuesta a la actual
	 */
	public Direction getOppositeHeading() {
		return this.whereILook.opposite();
	}
	
	public void setCurrentHeading(Direction direction) {
		this.whereILook = direction;
	}
	
	/**
	 * Muestra el nombre, la descripcion y los items que contiene el lugar 
	 * 
	 */
	public void scanCurrentPlace() {
		for (NavigationObserver o : this.observers)
			o.placeScanned(this.whereIAm);
	}
	
	public Street getHeadingStreet() {
		return this.city.lookForStreet(this.whereIAm, this.whereILook);
	}	
	
	public Direction getCurrentHeading() {
		return this.whereILook;
	}
	
	public Place getCurrentPlace() {
		return this.whereIAm;
	}
			
	protected City city;
	protected Place whereIAm;
	protected Direction whereILook;
}
