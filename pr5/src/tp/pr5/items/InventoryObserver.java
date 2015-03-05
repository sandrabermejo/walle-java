package tp.pr5.items;

import java.util.List;

public interface InventoryObserver {

	public void inventoryChange(List<Item> inventory);
	
	public void inventoryScanned(String inventoryDescription);
	
	public void itemScanned(String description);
	
	public void itemEmpty(String itemName);
}
