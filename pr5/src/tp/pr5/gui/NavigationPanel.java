package tp.pr5.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import tp.pr5.Direction;
import tp.pr5.NavigationObserver;
import tp.pr5.PlaceInfo;

public class NavigationPanel extends JPanel implements NavigationObserver {
	
	/**
	 * Constructor
	 * 
	 * @param robot
	 */
	public NavigationPanel() {
		this.setLayout(new BorderLayout());
		
		//
		// Panel central (imagen y mapa)
		//
		JPanel centro = new JPanel();
		JPanel imagen = new JPanel();
		centro.setLayout(new BorderLayout());
		imagen.setLayout(new BoxLayout(imagen, BoxLayout.Y_AXIS));
		
		//
		// Imagen de WALLE
		//
		// Creamos el ImageIcon
		this.image = new ImageIcon();
		// Incluimos la imagen en un JLabel sin texto
		this.label = new JLabel("", this.image, JLabel.CENTER);
		this.label.setPreferredSize(new Dimension(120, 120));
		
		imagen.add(Box.createVerticalGlue());		
		imagen.add(this.label);
		imagen.add(Box.createVerticalGlue());
		
		centro.add(imagen, BorderLayout.WEST);
		
		//
		// Mapa
		//
		this.filaActual = NavigationPanel.FILA_INICIAL;
		this.columnaActual = NavigationPanel.COLUMNA_INICIAL;
		JPanel mapa = new JPanel();
		mapa.setLayout(new GridLayout(NavigationPanel.NUM_FILAS, NavigationPanel.NUM_COLUMNAS));
		
		// Creamos una matriz de PlaceCell de 11 x 11
			 					
		this.cellMap = new PlaceCell[NavigationPanel.NUM_FILAS][NavigationPanel.NUM_COLUMNAS];
		for (int i = 0; i < 11; i++)
			for (int j = 0; j < 11; j++) {
				this.cellMap[i][j] = new PlaceCell();
				this.cellMap[i][j].addActionListener(new ActionListener(){

					public void actionPerformed(ActionEvent e) {
						if (((PlaceCell) e.getSource()).isVisited())
							NavigationPanel.this.placeScanned(((PlaceCell) e.getSource()).getPlaceInfo());
					}					
				});
				mapa.add(this.cellMap[i][j]);
			}
				
		
		// Titulo del panel
		TitledBorder titmap = BorderFactory.createTitledBorder("City Map");
		mapa.setBorder(titmap);
		
		centro.add(mapa);
		
		this.add(centro);	


		//
		// Log
		//
		this.description = new JTextArea();
		JScrollPane log = new JScrollPane(this.description);
		// Titulo del panel
		TitledBorder titlog = BorderFactory.createTitledBorder("Log");
		log.setBorder(titlog);
		
		this.add(log, BorderLayout.SOUTH);				
	}
	
	/**
	 * Comprueba si una celda se puede editar
	 * 
	 * @param fila
	 * @param columna
	 * @return True si la celda situada en [fila, columna] está en el rango editable
	 */
	public boolean isCellEditable(int fila, int columna){
		return (fila >= 0) && (fila < NavigationPanel.NUM_FILAS) && (columna >= 0) && (columna < NavigationPanel.NUM_COLUMNAS);
	}
	
	/**
	 * 
	 * @return La celda en la que esta el robot
	 */	
	public PlaceCell getCurrentCell(){
		return this.cellMap[this.filaActual][this.columnaActual];		
	}
	
	/**
	 * Actualiza la imagen del robot en funcion de la direccion a la que mira
	 * Codificamos el fichero de la imagen en un URL 
	 * 
	 * @param newHeading
	 */
	public void headingChanged(Direction newHeading) {
		if (newHeading.equals(Direction.NORTH))
			this.image = new ImageIcon(NavigationPanel.class.getResource("images/walleNorth.png"));
		else if (newHeading.equals(Direction.EAST))
			this.image = new ImageIcon(NavigationPanel.class.getResource("images/walleEast.png"));
		else if (newHeading.equals(Direction.SOUTH))	
			this.image = new ImageIcon(NavigationPanel.class.getResource("images/walleSouth.png"));
		else if (newHeading.equals(Direction.WEST))
			this.image = new ImageIcon(NavigationPanel.class.getResource("images/walleWest.png"));

		this.label.setIcon(this.image);
	}

	/**
	 * Inicializa el Navigation Panel
	 * 
	 * @param initialPlace
	 * @param heading
	 */
	public void initNavigationModule(PlaceInfo initialPlace, Direction heading) {
		// Inicializamos el lugar inicial en la celda (5, 5)
		this.getCurrentCell().setPlace(initialPlace);
		this.getCurrentCell().setName(initialPlace.getName()); // Mostramos el nombre del lugar
		this.getCurrentCell().addVisited();
		this.getCurrentCell().setBackground(Color.GREEN); // Poner fondo en verde
		
		// Inicializamos la imagen del robot
		this.headingChanged(heading);
		
		// Mostrar la descripcion del lugar inicial
		this.placeHasChanged(initialPlace);			
	}

	/**
	 * Actualiza el mapa
	 * 
	 * @param heading
	 * @param place
	 */
	public void robotArrivesAtPlace(Direction heading, PlaceInfo place) {
		int filaNueva = this.filaActual;
		int columnaNueva = this.columnaActual;
		switch(heading){
			case NORTH:  filaNueva--; break;
			case WEST: columnaNueva--; break;
			case SOUTH: filaNueva++; break;
			case EAST: columnaNueva++; break;
			default: ;
		}
		
		if (isCellEditable(filaNueva, columnaNueva)) {
			// Actualizamos la nueva celda actual
			PlaceCell celdaNueva = this.cellMap[filaNueva][columnaNueva];
			if (!celdaNueva.isVisited()){ // Si la celda no ha sido visitada...
				celdaNueva.setPlace(place); // Asignamos el lugar actual a la nueva celda
				celdaNueva.setName(place.getName()); // Mostramos el nombre del lugar en la celda
			}			
			celdaNueva.addVisited();
			celdaNueva.setBackground(Color.GREEN);
			// Actualizamos el color de la antigua celda
			PlaceCell celdaAntigua = this.cellMap[this.filaActual][this.columnaActual];
			if(celdaAntigua.isVisited())
				celdaAntigua.setBackground(Color.GRAY);
			else {
				celdaAntigua.setBackground(null);
				celdaAntigua.setName("");
			}
			// Actualizamos la posicion
			this.filaActual = filaNueva;
			this.columnaActual = columnaNueva;
			
			// Actualizamos el log
			this.placeScanned(place);
		}
	}

	/**
	 * Muestra la descripcion del lugar solicitado
	 * 
	 * @param placeDescription
	 */
	public void placeScanned(PlaceInfo placeDescription) {
		this.description.setText(placeDescription.toString()); 
		// En tiempo de ejecucion placeDescription es un Place que sobreescribe el metodo toString
	}
	
	/**
	 * Actualiza la descripcion del lugar en el que se encuentra el robot
	 * 
	 * @param placeDescription
	 */
	public void placeHasChanged(PlaceInfo placeDescription) {
		// Mostrar la descripcion del lugar actual
		this.placeScanned(placeDescription);
	}
	
	private JTextArea description;
	private ImageIcon image;
	private JLabel label;
	private PlaceCell[][] cellMap;
	
	private int filaActual;
	private int columnaActual;
	
	private static final int NUM_FILAS = 11;
	private static final int NUM_COLUMNAS = 11;
	private static final int FILA_INICIAL = 5;
	private static final int COLUMNA_INICIAL = 5;
	
	private static final long serialVersionUID = 1L;
}
