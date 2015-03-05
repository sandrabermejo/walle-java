package tp.pr5.console;

import java.util.List;

import tp.pr5.Direction;
import tp.pr5.NavigationObserver;
import tp.pr5.PlaceInfo;
import tp.pr5.RobotEngineObserver;
import tp.pr5.items.InventoryObserver;
import tp.pr5.items.Item;

public class Console implements NavigationObserver, RobotEngineObserver, InventoryObserver {

	public Console(ConsoleController cConsole) {
	}

	public void inventoryChange(List<Item> inventory) {
		// en consola no se muestran los cambios del inventario
	}

	public void inventoryScanned(String inventoryDescription) {
		System.out.println("WALL·E says:" + inventoryDescription);		
	}

	public void itemScanned(String description) {
		System.out.println(description);
	}

	public void itemEmpty(String itemName) {
		this.robotSays("WALL·E says: What a pity! I have no more " + itemName + " in my inventory");
	}

	public void raiseError(String msg) {
		System.out.println(msg);
	}

	public void communicationHelp(String help) {
		System.out.println(help);
	}

	public void engineOff(boolean atShip) {
		if (atShip)
			System.out.println("WALL·E says: I am at my space ship. Bye Bye");
		else
			System.out.println("WALL·E says: I run out of fuel. I cannot move. Shutting down...");
		System.exit(0);
		}

	public void communicationCompleted() {
		System.out.println("WALL·E says: I have communication problems. Bye Bye");
		System.exit(0);
	}

	public void robotUpdate(int fuel, int recycledMaterial) {
		if (fuel < 0) fuel = 0;
		System.out.println("      * My power is " + fuel);
		System.out.println("      * My reclycled material is " + recycledMaterial);
	}

	public void robotSays(String message) {
		System.out.print(message);
	}

	public void headingChanged(Direction newHeading) {
		System.out.println("WALL·E is looking at direction " + newHeading);
	}

	public void initNavigationModule(PlaceInfo initialPlace, Direction heading) {
		System.out.println(initialPlace.toString());
		this.headingChanged(heading);
	}

	public void robotArrivesAtPlace(Direction heading, PlaceInfo place) {
		System.out.println("WALL·E says: Moving in direction " + heading);
		System.out.println(place.toString());
	}

	public void placeScanned(PlaceInfo place) {
		System.out.println(place.getDescription());
	}

	public void placeHasChanged(PlaceInfo place) {
		// en consola no se muestran los cambios de un place
	}
}
