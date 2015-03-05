package tp.pr5;

import tp.pr5.items.InventoryObserver;

public abstract class Controller {
	
	public Controller(RobotEngine game){
		this.robot = game;
	}

	public abstract void startController();
	
	public void registerEngineObserver(RobotEngineObserver gameObserver){
		this.robot.addEngineObserver(gameObserver);
	}
	public void registerItemContainerObserver(InventoryObserver containerObserver){
		this.robot.addItemContainerObserver(containerObserver);
	}
	public void registerRobotObserver(NavigationObserver playerObserver) {
		this.robot.addNavigationObserver(playerObserver);
	}
	protected RobotEngine robot;
}
