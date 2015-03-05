package tp.pr5;

import tp.pr5.items.Item;
import tp.pr5.items.ItemContainer;

public class Place implements PlaceInfo {
	
	/**
	 * Constructor
	 * 
	 * @param name
	 * @param isSpaceShip
	 * @param description
	 */
	public Place(String name, boolean isSpaceShip, String description) {
		this.name = name;
		this.isSpaceShip = isSpaceShip;
		this.description = description;
		this.container = new ItemContainer();
	}
	
	/**
	 * Aniade el item dado al lugar
	 * 
	 * @param i
	 * @return True si se ha podido aniadirse el item
	 */
	public boolean addItem(Item i){
		return this.container.addItem(i);
	}
	
	/**
	 * Suelta el item dado al lugar
	 * 
	 * @param it
	 * @return True si se ha podido soltar el item
	 */
	public boolean dropItem(Item it) {
		if (this.existItem(it.getId())) // Ya existe el item en el lugar
			return false;
		this.addItem(it);
		return true;
	}
	
	/**
	 * Comprueba si el item esta en el lugar
	 * 
	 * @param id
	 * @return True si existe el item
	 */
	public boolean existItem(String id) {
		return (this.container.getItem(id) != null);		
	}
	
	/**
	 * 
	 * @return True si en el lugar esta la nave
	 */
	public boolean isSpaceship(){
		return this.isSpaceShip;
	}	
	

	/**
	 * 
	 * @return El nombre, la descripcion y los items que contiene el lugar 
	 */
	public String toString(){
		String cad = this.name + LINE_SEPARATOR + this.description + LINE_SEPARATOR;
		if (this.container.numberOfItems() == 0)
			cad += "The place is empty. There are no objects to pick";
		else 
			cad += "The place contains these objects:" + this.container.toString();
		return cad + LINE_SEPARATOR;
	}
	
		
	/**
	 * Elimina el item dado por su id del lugar
	 * 
	 * @param id
	 * @return El item eliminado
	 */
	public Item pickItem(String id){
		return this.container.pickItem(id);
	}
	
	/**
	 * 
	 * @return El nombre del lugar
	 */
	public String getName(){
		return this.name;
	}
	
	public String getDescription(){
		return this.description + this.container.toString() + LINE_SEPARATOR;
	}
	
	/**
	 * 
	 * @return El contenedor del lugar
	 */
	public ItemContainer getContainer() {
		return this.container;
	}
	
	private String name;
	private String description;
	private boolean isSpaceShip;
	protected ItemContainer container;
	
	private static final String LINE_SEPARATOR = System.getProperty("line.separator");
	
}
