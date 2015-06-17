package gui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import app.QueryEngine;

public class GUI {
	private static void createGUI() {
		// Create the main window
		JFrame frame = new JFrame("PokemonDB");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				QueryEngine.closeConnection();
			}
		});

		// Create the menu bar
		JMenuBar menuBar = new JMenuBar();
		menuBar.setOpaque(true);
		menuBar.setBackground(new Color(230, 230, 250));
		menuBar.setPreferredSize(new Dimension(800, 20));

		// Create the menus
		JMenu addMenu = new JMenu("Add");
		menuBar.add(addMenu);
		JMenu removeMenu = new JMenu("Remove");
		menuBar.add(removeMenu);

		// Create the Add menu items
		JMenuItem addMenuItem1 = new JMenuItem("Trainer");
		addMenuItem1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object[] namePossibilities = null;
				String name = (String) JOptionPane.showInputDialog(
						frame, "Enter the name of the trainer", "Customized Dialog",
						JOptionPane.PLAIN_MESSAGE, null, namePossibilities, null);

				if (name == null || name.isEmpty() || name.matches("^.*[^a-zA-Z0-9 ].*$")) {
					name = (String) JOptionPane.showInputDialog(
							frame, "Please enter a valid name", "Customized Dialog",
							JOptionPane.PLAIN_MESSAGE, null, namePossibilities, null);
				}

				if (!(name == null || name.isEmpty() || name.matches("^.*[^a-zA-Z0-9 ].*$"))) {
					Object[] genderPossibilities = {"Male", "Female"};
					String gender = (String) JOptionPane.showInputDialog(
							frame, "Select the gender of the trainer", "Customized Dialog",
							JOptionPane.PLAIN_MESSAGE, null, genderPossibilities, null);

					QueryEngine.addTrainer(name, gender);
				}
			}
		});
		addMenu.add(addMenuItem1);
		JMenuItem addMenuItem2 = new JMenuItem("Trainer Pokemon");
		addMenu.add(addMenuItem2);
		JMenuItem addMenuItem3 = new JMenuItem("Pokemon Move");
		addMenu.add(addMenuItem3);
		JMenuItem addMenuItem4 = new JMenuItem("Trainer TMHM");
		addMenu.add(addMenuItem4);
		JMenuItem addMenuItem5 = new JMenuItem("Trainer Consumable");
		addMenu.add(addMenuItem5);
		JMenuItem addMenuItem6 = new JMenuItem("Trainer Pokeball");
		addMenu.add(addMenuItem6);

		// Create the Remove menu items
		JMenuItem removeMenuItem1 = new JMenuItem("Trainer");
		removeMenu.add(removeMenuItem1);
		JMenuItem removeMenuItem2 = new JMenuItem("Trainer Pokemon");
		removeMenu.add(removeMenuItem2);
		JMenuItem removeMenuItem3 = new JMenuItem("Pokemon Move");
		removeMenu.add(removeMenuItem3);
		JMenuItem removeMenuItem4 = new JMenuItem("Trainer TMHM");
		removeMenu.add(removeMenuItem4);
		JMenuItem removeMenuItem5 = new JMenuItem("Trainer Consumable");
		removeMenu.add(removeMenuItem5);
		JMenuItem removeMenuItem6 = new JMenuItem("Trainer Pokeball");
		removeMenu.add(removeMenuItem6);

		// Create the background
		JLabel background = new JLabel();
		background.setOpaque(true);
		background.setBackground(new Color(135, 206, 250));
		background.setPreferredSize(new Dimension(800, 600));

		// Set the menu bar and background to the content pane
		frame.setJMenuBar(menuBar);
		frame.getContentPane().add(background, BorderLayout.CENTER);

		// Display the window
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		QueryEngine.initializeConnection();

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createGUI();
			}
		});
	}
}
