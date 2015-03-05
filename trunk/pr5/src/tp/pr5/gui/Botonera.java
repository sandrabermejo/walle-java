package tp.pr5.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import tp.pr5.Rotation;
import tp.pr5.instructions.*;

public class Botonera extends JPanel {
	
	/**
	 * Constructor
	 * 
	 * @param robotPanel
	 * @param navPanel
	 */
	public Botonera(final GUIController controller, final RobotPanel robotPanel){
		
		// Titulo del panel
		TitledBorder title = BorderFactory.createTitledBorder("Instructions");
		this.setBorder(title);
		
		// Creamos los botones
		// el JComboBox para la rotation
		// el JTextField para el item
		JButton move = new JButton("MOVE");
		JButton quit = new JButton("QUIT");
		JButton turn = new JButton("TURN");
		final JComboBox<Rotation> rotation = new JComboBox<Rotation>(this.options);
		JButton pick = new JButton("PICK");
		final JTextField item = new JTextField();
		JButton drop = new JButton("DROP");
		JButton operate = new JButton("OPERATE");
		JButton undo = new JButton("UNDO");
		
		// Aniadiamos un oyente boton especifico
		// para cada boton y lo inicializamos
		move.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				controller.communicateInstruction(new MoveInstruction()); 
			}
			
		});
		
		quit.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				controller.communicateInstruction(new QuitInstruction());	
			}			
			
		});
		
		turn.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				controller.communicateInstruction(new TurnInstruction((Rotation) rotation.getSelectedItem()));
			}
			
		});
		
		pick.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				controller.communicateInstruction(new PickInstruction(item.getText()));
			}
		
		});
		
		drop.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				controller.communicateInstruction(new DropInstruction(robotPanel.getSelectedItemName()));
			}
			
		});
		
		operate.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				controller.communicateInstruction(new OperateInstruction(robotPanel.getSelectedItemName()));
			}
			
		});
		
		undo.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				controller.communicateInstruction(new UndoInstruction());
			}
			
		});
		
		// Dividimos la ventana en 5x2
		// Aniadimos lo anterior
		this.setLayout(new GridLayout(5, 2));
		this.add(move);
		this.add(quit);
		this.add(turn);
		this.add(rotation);
		this.add(pick);
		this.add(item);
		this.add(drop);
		this.add(operate);
		this.add(undo);
	}
	
	// Opciones para el JComboBox
	private Rotation[] options = {Rotation.LEFT, Rotation.RIGHT};
	
	private static final long serialVersionUID = 1L;
}
