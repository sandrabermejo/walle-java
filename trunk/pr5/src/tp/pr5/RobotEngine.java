
package tp.pr5;

import java.util.ArrayList;
import java.util.Stack;

import tp.pr5.instructions.Instruction;
import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.items.InventoryObserver;
import tp.pr5.items.Item;
import tp.pr5.items.ItemContainer;

public class RobotEngine extends Observable<RobotEngineObserver> { // Realiza la simulacion

	/**
	 * Constructor
	 * 
	 * @param citymap
	 * @param initialPlace
	 * @param direction
	 */
	public RobotEngine(City cityMap, Place initialPlace, Direction direction) {
		this.power = RobotEngine.initialFuel;
		this.recycledMaterial = 0;
		this.container = new ItemContainer();
		this.navigation = new NavigationModule(cityMap, initialPlace);
		this.end = false;
		this.initHeading = direction;
		this.instructionsExecuted = new Stack<Instruction>();
		this.itemsOperated = new Stack<Item>();
		this.observers = new ArrayList<RobotEngineObserver>();
	}
	
	/**
	 * Aniade al power la cantidad fuel
	 * 
	 * @param fuel
	 */
	public void addFuel(int fuel) {
		this.power += fuel;
		for (RobotEngineObserver o : this.observers)
			o.robotUpdate(this.power, this.recycledMaterial);
	}
	
	/**
	 * Aniade al recycledMaterial la cantidad weight
	 * 
	 * @param weight
	 */
	public void addRecycledMaterial(int weight) {
		this.recycledMaterial += weight;
		for (RobotEngineObserver o : this.observers)
			o.robotUpdate(this.power, this.recycledMaterial);
	}
	
	/**
	 * Configura el contexto y ejecuta la instruccion
	 * 
	 * @param c
	 */
	public void communicateRobot(Instruction c) {	
		c.configureContext(this, this.navigation, this.container);
		try {
			c.execute();
		} catch (InstructionExecutionException e) {
			this.requestError(e.getMessage());
		}
	}
	
	/**
	 * Comprueba si ha llegado el final de la ejecucion
	 * 
	 * @return True si ha llegado el final de la ejecucion
	 */
	public boolean isOver(){
		if (this.end) { // Si la opcion es QUIT
			for (RobotEngineObserver o : this.observers){
				o.communicationCompleted();
			}
			return true;
		}
		if (this.power <= 0) { // Si no tiene energia
			for (RobotEngineObserver o : this.observers){
				o.engineOff(false);
			}
			return true;
		}
		if (this.navigation.whereIAm.isSpaceship()) { // Si llega a la nave
			for (RobotEngineObserver o: this.observers){
				o.engineOff(true);
			}
			return true;
		}
		return false;	
	}
	
	/**
	 * Muestra los mensajes que dice WALL·E
	 * 
	 * @param message
	 */
	public void saySomething(String message) {
		for (RobotEngineObserver o : this.observers)
			o.robotSays(message);
	}
	
	/**
	 * Muestra las posibles instrucciones
	 * 
	 */
	public void requestHelp() {
		for (RobotEngineObserver o : this.observers)
			o.communicationHelp(Interpreter.interpreterHelp());		
	} 
		
	/**
	 * Solicita el final de la simulacion
	 * 
	 */
	public void requestQuit() {
		this.end = true;
		for (RobotEngineObserver o : this.observers)
			o.communicationCompleted();
	}	
	
	/**
	 * Solicita el comienzo de la simulación
	 * 
	 */
	public void requestStart(){
		this.navigation.initHeading(this.initHeading);
		// Informamos a los observers del estado inicial del robot
		for (RobotEngineObserver o : this.observers)
			o.robotUpdate(this.power, this.recycledMaterial);		
	}
	
	/**
	 * Muestra un mensaje de error
	 * 
	 * @param msg
	 */
	public void requestError(String msg){
		for (RobotEngineObserver o : this.observers)
			o.raiseError(msg);
	}	
	
	public void addNavigationObserver(NavigationObserver navObserver) {
		this.navigation.addObserver(navObserver);
	}
	
	public void addEngineObserver(RobotEngineObserver robotObserver) {
		this.addObserver(robotObserver);	
	}
	
	public void addItemContainerObserver(InventoryObserver invObserver) {
		this.container.addObserver(invObserver);
	}
	
	public Stack<Instruction> getInstructionsExecuted() {
		return this.instructionsExecuted;
	}
	
	public Stack<Item> getItemsOperated() {
		return this.itemsOperated;
	}
	
	public void addItemToContainer(Item item){
		this.container.addItem(item);
	}
	
	public int getFuel() {
		return this.power;
	}
	
	public int getRecycledMaterial() {
		return this.recycledMaterial;
	}
	
	// Necesarios para la parte opcional
	public NavigationModule getNavigation() {
		return this.navigation;
	}
	
	public ItemContainer getContainer() {
		return this.container;
	}
	
	public ItemContainer getItemsFromCurrentPlace() {
		return this.navigation.getCurrentPlace().getContainer();
	}
	
	private int power;
	private int recycledMaterial;
	private ItemContainer container;
	private NavigationModule navigation;
	private boolean end;
	private Direction initHeading;
	private Stack<Instruction> instructionsExecuted; // Necesaria para ejecutar el UNDO
	private Stack<Item> itemsOperated;
	
	private static final int initialFuel = 100;
}
