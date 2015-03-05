package tp.pr5;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import tp.pr5.cityLoader.CityLoaderFromTxtFile;
import tp.pr5.instructions.*;
import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.items.ItemContainer;

public class FindExit {

	public static void main(String args[]) {
		FileInputStream file;
		City initialCity = null;
		CityLoaderFromTxtFile cityLoader = new CityLoaderFromTxtFile();
		String depth = null;
		String mapFile = null;
		
		// Configuramos las opciones de entrada
		Options options = new Options();
		options.addOption("d", "max-depth", true, "Configure the maximal depth");
		options.addOption("m", "map", true, "File with the description of the city");
		
		// Creamos el parse
		CommandLineParser parser = new BasicParser();
		
		try {
	        // Parseamos la entrada con la configuracion establecida
	        CommandLine line = parser.parse(options, args);
	        
	       depth = line.getOptionValue("d");
	       if (depth == null) // profundidad no especificada
				throw new ParseException("Depth not specified");
	        
	       mapFile = line.getOptionValue("m");
	       if (mapFile == null) // fichero de mapa no especificado
	        	throw new ParseException("Map file not specified");
	       
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
	       
	       // Inicializamos el robot
	       WALLE.requestStart();
	       
	       // Llamar al backtracking
	      Stack<Instruction> solution = searchSolution(WALLE, Integer.parseInt(depth));
	        
	       // Mostrar solucion
	       if (solution.isEmpty())
	    	   System.out.println("No existe solucion en " + depth + " instrucciones");
	       else {
	    	   solution = invertirPila(solution);
	    	   System.out.println("Las instrucciones necesarias para encontrar la salida (solución óptima) son:");
	    	   while (!solution.isEmpty())
	    		   System.out.println(solution.pop().toString());
	       }
	       	       
	       
	     } catch(ParseException e) {
		        System.err.println(e.getMessage());
		        System.exit(1);
		 }	
	}

	/**
	 * Intenta llevar al robot a la spaceship en el menor numero de instrucciones
	 * sin exceder maxDepth instrucciones
	 * 
	 * @param robot
	 * @param maxDepth
	 * @return Pila de instrucciones para la solucion optima (vacia si no hay solucion)
	 */
	public static Stack<Instruction> searchSolution(RobotEngine robot, int maxDepth) {
		Stack<Instruction> ins = new Stack<Instruction>();
		int i = 1;
		boolean find = false;
		while (i <= maxDepth && !find){
			find = backtracking(ins, robot, 1, i);
			i++;
		}
		return ins;
	}
	
	/**
	 * Metodo que realiza la vuelta atras
	 * Intenta llevar al robot a la spaceship en depth instrucciones
	 * sin exceder maxDepth instrucciones
	 * 
	 * @param ins
	 * @param robot
	 * @param depth
	 * @param maxDepth
	 * @return True si se ha llegado a la solucion
	 */
	public static boolean backtracking(Stack<Instruction> ins, RobotEngine robot, int depth, int maxDepth) {
		if (depth > maxDepth)
			return false;
		ArrayList<Instruction> opciones = generarOpciones(robot);
		boolean find = false;
		for (int i = 0; i < opciones.size() && !find; i++) {
			opciones.get(i).configureContext(robot, robot.getNavigation(), robot.getContainer());
			try {
				opciones.get(i).execute();
				if (isValid(robot, ins)) {
					ins.push(opciones.get(i)); // Apilamos la instruccion a la solucion
					if (robot.getNavigation().atSpaceship())
						find = true;
					else {
						find = backtracking(ins, robot, depth+1, maxDepth);
						if (!find)
							ins.pop().undo(); // Desapila y deshace la instruccion ya que no lleva a la solucion
					}
				}
				else 
					opciones.get(i).undo(); // Deshace la instruccion ya que no es valida
			} catch (InstructionExecutionException e) {}
		}		
		return find;
	}
	
	/**
	 * Dado un robot genera las posibles instrucciones a ejecutar
	 * 
	 * @param robot
	 * @return La  lista con las posibles instrucciones
	 */
	public static ArrayList<Instruction> generarOpciones(RobotEngine robot) {
		ArrayList<Instruction> opciones = new ArrayList<Instruction>();
		opciones.add(new MoveInstruction());
		opciones.add(new TurnInstruction(Rotation.LEFT));
		opciones.add(new TurnInstruction(Rotation.RIGHT));
		
		ItemContainer placeItems = robot.getItemsFromCurrentPlace();
		for (int i = 0; i < placeItems.numberOfItems(); i++)
			opciones.add(new PickInstruction(placeItems.getItems()[i].getId()));
		
		for (int j = 0; j < robot.getContainer().numberOfItems(); j++)
			opciones.add(new OperateInstruction(robot.getContainer().getItems()[j].getId()));
		
		return opciones;
	}

	/**
	 * Comprueba si una solucion es valida
	 * 
	 * @param robot
	 * @param ins
	 * @return True si el robot sigue con fuel
	 */
	public static boolean isValid(RobotEngine robot, Stack<Instruction> ins) {
		return (robot.getFuel() > 0);
	}
	
	/**
	 * Funcion auxiliar que invierte una pila
	 * 
	 * @param solution
	 * @return pila
	 */
	private static Stack<Instruction> invertirPila(Stack<Instruction> solution) {
		Stack<Instruction> pila = new Stack<Instruction>();
		int size = solution.size();
		for (int i = 0; i < size; i++)
			pila.add(solution.pop());
		return pila;
	}
}
