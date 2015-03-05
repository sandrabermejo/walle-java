/*
PRACTICA 5: "Implementacion del MVC"

Cristina Alonso Fernandez
Sandra Bermejo Cañadas
 */

package tp.pr5;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;

import org.apache.commons.cli.*; // Necesaria para el tratamiento de lineas de comando (para que la reconozca hay que añadir como libreria externa el commons-cli-1.2.jar)
import tp.pr5.RobotEngine;
import tp.pr5.cityLoader.CityLoaderFromTxtFile;
import tp.pr5.console.Console;
import tp.pr5.console.ConsoleController;
import tp.pr5.gui.GUIController;
import tp.pr5.gui.MainWindow;



public class Main {

	/**
	 * Creates the city, the engine and finally
	 * starts the simulation
	 * @param args
	 */
	public static void main(String[] args) {
		FileInputStream file;
		City initialCity = null;
		CityLoaderFromTxtFile cityLoader = new CityLoaderFromTxtFile();
		String interfaz = null;
		String mapFile = null;
						
		// Configuramos las opciones de entrada
		Options options = new Options();
		options.addOption("h", "help", false, "Shows the help message");
		options.addOption("i", "interface", true, "The type of interface: console or swing");
		options.addOption("m", "map", true, "File with the description of the city");		
		
		// Creamos el parse
		CommandLineParser parser = new BasicParser();
		
		try {
	        // Parseamos la entrada con la configuracion establecida
	        CommandLine line = parser.parse(options, args);
	        
	        // Analizamos los resultados y realizamos lo que proceda
	        if(line.hasOption("h")) { // Si el usuario solicita ayuda imprimimos el mensaje de ayuda
			    System.out.println(Main.HELP);
			    if (!line.hasOption("i") && !line.hasOption("m"))
			    	System.exit(0);
			}
	        
	        mapFile = line.getOptionValue("m");
	        if (mapFile == null) // fichero de mapa no especificado
	        	throw new ParseException("Map file not specified");
	        
	        interfaz = line.getOptionValue("i");
	        if (interfaz == null) // interfaz no especificado
				throw new ParseException("Interface not specified");
	        
	        if (!interfaz.equals("console") && !interfaz.equals("swing") && !interfaz.equals("both")) { // Interfaz no válido
				System.err.println("Wrong type of interface");
				System.exit(3);     
	        } 
			
			// Cargamos el mapa
			try {	
				file = new FileInputStream(mapFile); // may throw exception
				initialCity = cityLoader.loadCity(file); // may throw exception
				if (file != null)
					file.close();
			}catch (IOException e){
				System.err.println("Error reading the map file: " + mapFile + " (No existe el fichero o el directorio)");
				System.exit(2);
			}
			
			// Todos los parametros de entrada son correctos y se ha podido cargar el mapa
			// Creamos el modelo
			RobotEngine WALLE = new RobotEngine(initialCity, cityLoader.getInitialPlace(), Direction.NORTH);
			
			
			if (interfaz.equals("console")) {
				// Creamos el controlador (de consola)
				ConsoleController cConsole = new ConsoleController(WALLE);
				// Creamos la vista (consola)
				Console console = new Console(cConsole);
				// Aniadimos la consola como observador del modelo
				cConsole.registerEngineObserver(console);
				cConsole.registerItemContainerObserver(console);
				cConsole.registerRobotObserver(console);
				cConsole.startController();
				System.exit(0);
			}
			else if (interfaz.equals("swing")) {
				Locale.setDefault(Locale.ENGLISH); // Para que muestre las opciones de los mensajes emergentes en ingles
				// Creamos el controlador (de swing)
				GUIController cGui = new GUIController(WALLE);
				// Creamos la vista (ventana)
				MainWindow ventana = new MainWindow(cGui);
				cGui.startController();
			}
			else { // interfaz.equals("both")
				// CONSOLA
				ConsoleController cConsole = new ConsoleController(WALLE);
				Console console = new Console(cConsole);
				cConsole.registerEngineObserver(console);
				cConsole.registerItemContainerObserver(console);
				cConsole.registerRobotObserver(console);
				
				// SWING
				Locale.setDefault(Locale.ENGLISH);
				GUIController cGui = new GUIController(WALLE);
				MainWindow ventana = new MainWindow(cGui);
				cGui.startController();
			}
			
		} catch(ParseException e) {
	        System.err.println(e.getMessage());
	        System.exit(1);
	    }				
	}

	private static final String LINE_SEPARATOR = System.getProperty("line.separator");
	
	private static final String HELP = 
			"Execute this assignment with these parameters:" + LINE_SEPARATOR +
			"usage: tp.pr5.Main [-h] [-i <type>] [-m <mapfile>]" + LINE_SEPARATOR +
			" -h,--help               Shows this help message" + LINE_SEPARATOR +
			" -i,--interface <type>   The type of interface: console, swing or both" + LINE_SEPARATOR +
			" -m,--map <mapfile>      File with the description of the city";
}
