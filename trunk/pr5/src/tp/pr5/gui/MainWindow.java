package tp.pr5.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;

import tp.pr5.RobotEngineObserver;

public class MainWindow extends JFrame implements RobotEngineObserver {
		
	/**
	 * Constructor
	 * 
	 * @param robot
	 */
	public MainWindow(GUIController guiController) {
		// Titulo de la ventana
		super("WALL·E The garbage collector");
		// Tamanio de la ventana (por defecto)
		this.setSize(900, 600);
		// Salir de la ventana al hacer click en la X
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setLayout(new BorderLayout());
		
		// Aniadimos la ventana como observadora
		guiController.registerEngineObserver(this);
		
		//
		// NavigationPanel
		//
		NavigationPanel navPanel = new NavigationPanel();
		this.add(navPanel, BorderLayout.CENTER);
		// Aniadimos el NavigationPanel como observador
		guiController.registerRobotObserver(navPanel);
		
		//
		// Mensajes de WALLE
		//
		InfoPanel msg = new InfoPanel();
		this.add(msg, BorderLayout.SOUTH);
		// Aniadimos el InfoPanel como observador
		guiController.registerEngineObserver(msg);
		guiController.registerItemContainerObserver(msg);
		guiController.registerRobotObserver(msg);
	
		//
		// Panel superior (splitPanel)
		//
		// Creamos la botonera y el RobotPanel
		// y los aniadimos en un SplitPanel
		RobotPanel robotPanel = new RobotPanel();
		// Aniadimos el robotPanel como observador
		guiController.registerEngineObserver(robotPanel);
		guiController.registerItemContainerObserver(robotPanel);
		
		Botonera botonera = new Botonera(guiController, robotPanel);
		JSplitPane panelNorte = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, botonera, robotPanel);
		// que aniadimos al Norte de la ventana
		this.add(panelNorte, BorderLayout.NORTH);
		
		//
		// Menu
		//
		JMenuBar menu = new JMenuBar();
		JMenu file = new JMenu("File");
		JMenuItem quit = new JMenuItem("Quit", KeyEvent.VK_Q); // 'Q' es la tecla rapida para salir
		quit.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				MainWindow.this.communicationCompleted();
			}
			
		}); // Capturamos el evento del menu
		file.add(quit);
		menu.add(file);
		this.setJMenuBar(menu); // Aniadimos la barra de menu a la ventana
		
		this.setVisible(true);							
	}
		
	public void raiseError(String msg) {
		JOptionPane.showMessageDialog(null, msg);
	}

	public void communicationHelp(String help) {
		// en swing no procede
	}

	public void engineOff(boolean atShip) {
		if (atShip)
			JOptionPane.showMessageDialog(null, "I am at my space ship. Bye Bye");
		else
			JOptionPane.showMessageDialog(null, "I run out of fuel. I cannot move. Shutting down...");
		System.exit(0);
	}

	public void communicationCompleted() {
		if (JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
			System.exit(0);
	}

	public void robotUpdate(int fuel, int recycledMaterial) {
		if (fuel <= 0)
			this.engineOff(false);
	}

	public void robotSays(String message) {
		// no procede
	}
	
	private static final long serialVersionUID = 1L;
}
