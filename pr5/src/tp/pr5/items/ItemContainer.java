package tp.pr5.items;

import java.util.ArrayList;

import tp.pr5.Observable;

public class ItemContainer extends Observable<InventoryObserver> {
	
	/**
	 * Constructor sin parametros
	 */
	public ItemContainer() {
		this.items = new Item[ItemContainer.tamanyoInicial];
		this.numItems = 0;
		this.observers = new ArrayList<InventoryObserver>();
	}
	
	/**
	 * Aniade el item al container
	 * 
	 * @param item
	 * @return True si ha podido aniadirse el item
	 */
	public boolean addItem(Item item) {
		if (this.numItems == this.items.length)
			this.resizeArray();
		
		int pos = this.findItem(item.getId(), 0, this.numItems-1);
		if (pos < this.numItems) {
			if (this.items[pos].getId().equalsIgnoreCase(item.getId())) // Ya esta en el inventario
					return false;
			// Desplazar a la derecha
			for (int i = this.numItems-1; i >= pos; i--)
				this.items[i+1] = this.items[i];
		}
		this.items[pos] = item;
		this.numItems++;
		
		for (InventoryObserver o : this.observers)
				o.inventoryChange(this.convertToList());
		return true;
	}
	
	/**
	 * Elimina el item con id dado del container
	 * 
	 * @param id
	 * @return El item eliminado
	 */
	public Item pickItem(String id) {
		int pos = this.findItem(id, 0, this.numItems-1);
		if ((pos < this.numItems) && (this.items[pos].getId().equalsIgnoreCase(id))) {
			Item item = this.items[pos];
			// Desplazar a la izquierda
			for(int i = pos; i < this.numItems-1; i++)
				this.items[i] = this.items[i+1];
			this.numItems--;
			for (InventoryObserver o : this.observers)
				o.inventoryChange(this.convertToList());
			return item;
		}		
		return null;
	}
	
	/**
	 * Solicita el scan del inventorio
	 */
	public void requestScanCollection(){
		String s;
		if (this.numberOfItems() == 0)
			s = "My inventory is empty";
		else
			s = "I am carrying the following items" + this.toString();
		s += LINE_SEPARATOR;
		for (InventoryObserver o : this.observers)
			o.inventoryScanned(s);
	}
	
	/**
	 * Solicita el scan del item dado
	 * Precondición: el item existe
	 * 
	 * @param id
	 */
	public void requestScanItem(String id){
		Item item = this.getItem(id);
		for (InventoryObserver o : this.observers)
			o.itemScanned(id + ": " + item.toString());
	}
	
	/**
	 * Si el item no puede seguir usandose lo elimina
	 * 
	 * @param item
	 */
	public void useItem(Item item){
		if (!item.canBeUsed()){ // No puede seguir usandose
			this.pickItem(item.getId()); // Se borra del container
			for (InventoryObserver o : this.observers)
				o.itemEmpty(item.getId());
		}
		this.emitInventoryChange();
	}
	
	/**
	 * Funcion auxiliar para informar de un cambio en el inventorio
	 */
	public void emitInventoryChange() {
		for (InventoryObserver o : this.observers)
			o.inventoryChange(this.convertToList());
	}
	
	
	/**
	 * 
	 * @return El numero de items del container
	 */
	public int numberOfItems() {
		return this.numItems;
	}
	
	/**
	 * 
	 * @return Un string con los id de todos los items del container
	 */
	public String toString() {
		String cad = "";
		for (int i = 0; i < this.numberOfItems(); i++)
			cad += LINE_SEPARATOR + "   " + this.items[i].getId();
		return cad;
	}
	
	/**
	 * Obtiene el item con id dado
	 * 
	 * @param id
	 * @return El item con id dado
	 */
	public Item getItem(String id) {
		int pos = this.findItem(id, 0, this.numItems-1);
		if ((pos < this.numItems) && (this.items[pos].getId().equalsIgnoreCase(id)))
			return this.items[pos];
		return null;
	}
	

	/**
	 * Comprueba si el item con ese id esta en el container
	 * 
	 * @param id
	 * @return True si esta el item
	 */
	public boolean containsItem(String id) {
		return this.getItem(id) != null;		
	}
	
	/**
	 * Busca un item dado por su id en el container desde ini hasta fin
	 * 
	 * @param id
	 * @param ini
	 * @param fin
	 * @return La posicion en la que esta el item o en la que deberia estar
	 */
	private int findItem(String id, int ini, int fin) {
		int pos = 0;
		while (ini <= fin) {
			pos = (ini + fin) / 2;
			if ((this.items[pos].getId().compareToIgnoreCase(id)) == 0)
				return pos;
			else if ((this.items[pos].getId().compareToIgnoreCase(id)) > 0)
				fin = pos-1;
			else 
				ini = pos+1;
		}
		return ini;
	}
	
	/**
	 * Aumenta el tamanio del container (x2)
	 */
	private void resizeArray() {
		Item items[] = new Item[this.items.length * 2];
		for (int i = 0; i < this.numItems; i++)
			items[i] = this.items[i];
		this.items = items;
	}
	
	/**
	 * 
	 * 
	 * @return Array de items contenidos en el container
	 */
	public Item[] getItems(){
		return this.items;
	}
	
	/**
	 * Funcion auxiliar que convierte un array de items en un array list
	 * 
	 * @return
	 */
	private ArrayList<Item> convertToList() {
		ArrayList<Item> list = new ArrayList<Item>();
		for (int i = 0; i < this.numItems; i++)
			list.add(this.items[i]);
		return list;
	}
	
	protected Item[] items;
	private int numItems;
	
	private static final String LINE_SEPARATOR = System.getProperty("line.separator");
	
	private static final int tamanyoInicial = 10;
}
