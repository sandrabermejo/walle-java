package tp.pr5.gui;

import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import tp.pr5.Direction;
import tp.pr5.NavigationObserver;
import tp.pr5.PlaceInfo;
import tp.pr5.RobotEngineObserver;
import tp.pr5.items.InventoryObserver;
import tp.pr5.items.Item;

public class InfoPanel extends JPanel implements RobotEngineObserver,
		InventoryObserver, NavigationObserver {
	
	public InfoPanel() {
		this.message = new JLabel();
		// Aniadimos la etiqueta al infoPanel
		this.add(this.message);
	}

	public void inventoryChange(List<Item> inventory) {
		// no procede
	}

	public void inventoryScanned(String inventoryDescription) {
		// no procede
	}

	public void itemScanned(String description) {
		// no procede
	}

	public void itemEmpty(String itemName) {
		// no procede
	}

	public void raiseError(String msg) {
		// no procede
	}

	public void communicationHelp(String help) {
		// no procede
	}

	public void engineOff(boolean atShip) {
		// no procede
	}

	public void communicationCompleted() {
		// no procede 
	}

	public void robotUpdate(int fuel, int recycledMaterial) {
		this.message.setText("Robot attributes have been updated: (" + fuel + "," + recycledMaterial + ")");
	}

	public void robotSays(String message) {
		this.message.setText(message);
	}

	public void headingChanged(Direction newHeading) {
		this.message.setText("WALL·E is looking at direction " + newHeading);
	}

	public void initNavigationModule(PlaceInfo initialPlace, Direction heading) {
		this.message.setText("WALL·E is at " + initialPlace.getName());
	}

	public void robotArrivesAtPlace(Direction heading, PlaceInfo place) {
		this.message.setText("WALL·E has arrived at " + place.getName());
	}

	public void placeScanned(PlaceInfo placeDescription) {
		// no procede
	}

	public void placeHasChanged(PlaceInfo placeDescription) {
		// no procede
	}
	
	private JLabel message;
	
	private static final long serialVersionUID = 1L;

}
