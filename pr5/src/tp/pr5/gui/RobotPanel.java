package tp.pr5.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import tp.pr5.RobotEngineObserver;
import tp.pr5.items.InventoryObserver;
import tp.pr5.items.Item;


public class RobotPanel extends JPanel implements RobotEngineObserver, InventoryObserver {
	
	/**
	 * Constructor
	 * 
	 * @param robot
	 */
	 public RobotPanel() {
		 
		// Titulo de panel
		TitledBorder title = BorderFactory.createTitledBorder("Robot info");
		this.setBorder(title);
		
		this.setLayout(new BorderLayout());
		
		//
		// Informacion robot
		//
		// Creamos las etiquetas fuel y material		
		this.fuel = new JLabel();
		this.material = new JLabel();
		// Las centramos con el FlowLayout
		this.fuel.setLayout(new FlowLayout());
		this.material.setLayout(new FlowLayout());
		// Creamos el panel de informacion
		// y aniadimos las dos etiquetas
		JPanel info = new JPanel();
		info.add(this.fuel);
		info.add(this.material);		
		// Las aniadimos al Norte del panel
		this.add(info, BorderLayout.NORTH);
				
		// 
		// Tabla
		//
		// Creamos la tabla
		this.modelo = new DefaultTableModel();
		this.modelo.addColumn("Id");
		this.modelo.addColumn("Description");
		this.table = new JTable(modelo);
		// Tamanio de la tabla
		this.table.setPreferredScrollableViewportSize(new Dimension(100, 50));
		// La aniadimos al Centro del panel
		this.add(new JScrollPane(this.table)); // BorderLayout.CENTER
	}
	 
	 /**
	 * Actualiza la tabla con el inventario del robot
	 * 
	 * @param container
	 */
	public void updateTable(List<Item> container){
		Object[][] data = this.dataTable(container);
		int numFilas = this.modelo.getRowCount(); // contando titulos de las columnas
		int numColumnas = this.modelo.getColumnCount();
		int numItems = container.size();
		
		if (numFilas > numItems) // Hemos borrado algun item (drop || operate y times = 0)
			this.modelo.removeRow(numFilas-1); 
		else if (numFilas < numItems) // Hemos aniadido algun item (pick)
			this.modelo.addRow(new Object[][]{});
		
		numFilas = numItems; // Nuevo número de filas
		
		for (int fila = 0; fila < numFilas; fila++) // Actualizamos el contenido
			for (int columna = 0; columna < numColumnas; columna++) {
				String value = (String) this.modelo.getValueAt(fila, columna);
				if ((value == null) || !value.toString().equals(data[fila][columna]))
					this.modelo.setValueAt(data[fila][columna], fila, columna);
			}
	}
	
	/**
	 * 
	 * @return El nombre del Item seleccionado en la tabla
	 */
	public String getSelectedItemName(){
		int fila = this.table.getSelectedRow();
		if (fila == -1) // Selection is empty 
			return null;
		return (String) this.table.getValueAt(fila, 0);
	}
	
	/**
	 * Funcion auxilar
	 * 
	 * @param container
	 * @return Una matriz con los datos del container dado
	 */
	private Object[][] dataTable(List<Item> container){ // Datos de las columnas
		int N = container.size();
		Object[][] tabla = new Object[N][2];
		for (int i = 0; i < N; i++) {
			tabla[i][0] = container.get(i).getId();
			tabla[i][1] = container.get(i).toString();
		}
		return tabla;
	}
	
	public void inventoryChange(List<Item> inventory) {
		this.updateTable(inventory);
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

	/**
	  * Actualiza la informacion del robot (fuel y recycled material)
	  * 
	  * @param fuel
	  * @param recycledMaterial
	  */
	public void robotUpdate(int fuel, int recycledMaterial) {
		// Actualizamos el fuel
		this.fuel.setText("Fuel: " + fuel);
		// Actualizamos el material reciclado
		this.material.setText("Recycled: " + recycledMaterial);
	}

	public void robotSays(String message) {
		// no procede
	}
	
	private JLabel fuel;
	private JLabel material;
	private JTable table;
	
	private DefaultTableModel modelo;
		
	private static final long serialVersionUID = 1L;
}