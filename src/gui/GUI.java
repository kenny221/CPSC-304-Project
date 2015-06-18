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
				String name = (String) JOptionPane.showInputDialog(
						frame, "Enter the name of the trainer", "Customized Dialog",
						JOptionPane.PLAIN_MESSAGE, null, null, null);

				if (name == null || name.isEmpty() || name.matches("^.*[^a-zA-Z0-9 ].*$")) {
					name = (String) JOptionPane.showInputDialog(
							frame, "Please enter a valid name", "Customized Dialog",
							JOptionPane.PLAIN_MESSAGE, null, null, null);
				}

				if (!(name == null || name.isEmpty() || name.matches("^.*[^a-zA-Z0-9 ].*$"))) {
					Object[] genders = {"Male", "Female"};
					String gender = (String) JOptionPane.showInputDialog(
							frame, "Select the gender of the trainer", "Customized Dialog",
							JOptionPane.PLAIN_MESSAGE, null, genders, null);

					QueryEngine.addTrainer(name, gender);
					JOptionPane.showMessageDialog(frame, "Success!");
				} else {
					JOptionPane.showMessageDialog(frame, "Cancelled");
				}
			}
		});
		addMenu.add(addMenuItem1);

		JMenuItem addMenuItem2 = new JMenuItem("Trainer Pokemon");
		addMenuItem2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String trainerID = (String) JOptionPane.showInputDialog(
						frame, "Enter the trainer ID", "Customized Dialog",
						JOptionPane.PLAIN_MESSAGE, null, null, null);

				if (trainerID == null || trainerID.isEmpty() || trainerID.matches("^.*[^0-9].*$")) {
					trainerID = (String) JOptionPane.showInputDialog(
							frame, "Please enter a valid trainer ID", "Customized Dialog",
							JOptionPane.PLAIN_MESSAGE, null, null, null);
				}

				if (!(trainerID == null || trainerID.isEmpty() || trainerID.matches("^.*[^0-9].*$"))) {
					String pokemonID = (String) JOptionPane.showInputDialog(
							frame, "Enter the Pokemon ID", "Customized Dialog",
							JOptionPane.PLAIN_MESSAGE, null, null, null);

					if (pokemonID == null || pokemonID.isEmpty() || pokemonID.matches("^.*[^0-9].*$")) {
						pokemonID = (String) JOptionPane.showInputDialog(
								frame, "Please enter a valid pokemon ID", "Customized Dialog",
								JOptionPane.PLAIN_MESSAGE, null, null, null);
					}

					if (!(pokemonID == null || pokemonID.isEmpty() || pokemonID.matches("^.*[^0-9].*$"))) {
						if (QueryEngine.addTrainerPokemon(Integer.parseInt(trainerID),
								Integer.parseInt(pokemonID))) {
							JOptionPane.showMessageDialog(frame, "Success!");
						} else {
							JOptionPane.showMessageDialog(frame, "Failure!");
						}
					} else {
						JOptionPane.showMessageDialog(frame, "Cancelled");
					}
				} else {
					JOptionPane.showMessageDialog(frame, "Cancelled");
				}
			}
		});
		addMenu.add(addMenuItem2);

		JMenuItem addMenuItem3 = new JMenuItem("Pokemon Move");
		addMenuItem3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String pokemonID = (String) JOptionPane.showInputDialog(
						frame, "Enter the pokemon ID", "Customized Dialog",
						JOptionPane.PLAIN_MESSAGE, null, null, null);

				if (pokemonID == null || pokemonID.isEmpty() || pokemonID.matches("^.*[^0-9].*$")) {
					pokemonID = (String) JOptionPane.showInputDialog(
							frame, "Please enter a valid pokemon ID", "Customized Dialog",
							JOptionPane.PLAIN_MESSAGE, null, null, null);
				}

				if (!(pokemonID == null || pokemonID.isEmpty() || pokemonID.matches("^.*[^0-9].*$"))) {
					String move = (String) JOptionPane.showInputDialog(
							frame, "Enter the move name", "Customized Dialog",
							JOptionPane.PLAIN_MESSAGE, null, null, null);

					if (move == null || move.isEmpty() || move.matches("^.*[^a-zA-Z0-9 ].*$")) {
						move = (String) JOptionPane.showInputDialog(
								frame, "Please enter a valid move name", "Customized Dialog",
								JOptionPane.PLAIN_MESSAGE, null, null, null);
					}

					if (!(move == null || move.isEmpty() || move.matches("^.*[^a-zA-Z0-9 ].*$"))) {
						if (QueryEngine.addPokemonMove(Integer.parseInt(pokemonID), move)) {
							JOptionPane.showMessageDialog(frame, "Success!");
						} else {
							JOptionPane.showMessageDialog(frame, "Failure!");
						}
					} else {
						JOptionPane.showMessageDialog(frame, "Cancelled");
					}
				} else {
					JOptionPane.showMessageDialog(frame, "Cancelled");
				}
			}
		});
		addMenu.add(addMenuItem3);

		JMenuItem addMenuItem4 = new JMenuItem("Trainer TMHM");
		addMenuItem4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String trainerID = (String) JOptionPane.showInputDialog(
						frame, "Enter the trainer ID", "Customized Dialog",
						JOptionPane.PLAIN_MESSAGE, null, null, null);

				if (trainerID == null || trainerID.isEmpty() || trainerID.matches("^.*[^0-9].*$")) {
					trainerID = (String) JOptionPane.showInputDialog(
							frame, "Please enter a valid trainer ID", "Customized Dialog",
							JOptionPane.PLAIN_MESSAGE, null, null, null);
				}

				if (!(trainerID == null || trainerID.isEmpty() || trainerID.matches("^.*[^0-9].*$"))) {
					String tmhm = (String) JOptionPane.showInputDialog(
							frame, "Enter the TMHM name", "Customized Dialog",
							JOptionPane.PLAIN_MESSAGE, null, null, null);

					if (tmhm == null || tmhm.isEmpty() || tmhm.matches("^.*[^a-zA-Z0-9 ].*$")) {
						tmhm = (String) JOptionPane.showInputDialog(
								frame, "Please enter a valid TMHM name", "Customized Dialog",
								JOptionPane.PLAIN_MESSAGE, null, null, null);
					}

					if (!(tmhm == null || tmhm.isEmpty() || tmhm.matches("^.*[^a-zA-Z0-9 ].*$"))) {
						if (QueryEngine.addTrainerTMHM(Integer.parseInt(trainerID), tmhm)) {
							JOptionPane.showMessageDialog(frame, "Success!");
						} else {
							JOptionPane.showMessageDialog(frame, "Failure!");
						}
					} else {
						JOptionPane.showMessageDialog(frame, "Cancelled");
					}
				} else {
					JOptionPane.showMessageDialog(frame, "Cancelled");
				}
			}
		});
		addMenu.add(addMenuItem4);

		JMenuItem addMenuItem5 = new JMenuItem("Trainer Consumable");
		addMenuItem5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String trainerID = (String) JOptionPane.showInputDialog(
						frame, "Enter the trainer ID", "Customized Dialog",
						JOptionPane.PLAIN_MESSAGE, null, null, null);

				if (trainerID == null || trainerID.isEmpty() || trainerID.matches("^.*[^0-9].*$")) {
					trainerID = (String) JOptionPane.showInputDialog(
							frame, "Please enter a valid trainer ID", "Customized Dialog",
							JOptionPane.PLAIN_MESSAGE, null, null, null);
				}

				if (!(trainerID == null || trainerID.isEmpty() || trainerID.matches("^.*[^0-9].*$"))) {
					String consumable = (String) JOptionPane.showInputDialog(
							frame, "Enter the consumable name", "Customized Dialog",
							JOptionPane.PLAIN_MESSAGE, null, null, null);

					if (consumable == null || consumable.isEmpty() || consumable.matches("^.*[^a-zA-Z0-9 ].*$")) {
						consumable = (String) JOptionPane.showInputDialog(
								frame, "Please enter a valid consumable name", "Customized Dialog",
								JOptionPane.PLAIN_MESSAGE, null, null, null);
					}

					if (!(consumable == null || consumable.isEmpty() || consumable.matches("^.*[^a-zA-Z0-9 ].*$"))) {
						if (QueryEngine.addTrainerConsumable(Integer.parseInt(trainerID), consumable)) {
							JOptionPane.showMessageDialog(frame, "Success!");
						} else {
							JOptionPane.showMessageDialog(frame, "Failure!");
						}
					} else {
						JOptionPane.showMessageDialog(frame, "Cancelled");
					}
				} else {
					JOptionPane.showMessageDialog(frame, "Cancelled");
				}
			}
		});
		addMenu.add(addMenuItem5);

		JMenuItem addMenuItem6 = new JMenuItem("Trainer Pokeball");
		addMenuItem6.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String trainerID = (String) JOptionPane.showInputDialog(
						frame, "Enter the trainer ID", "Customized Dialog",
						JOptionPane.PLAIN_MESSAGE, null, null, null);

				if (trainerID == null || trainerID.isEmpty() || trainerID.matches("^.*[^0-9].*$")) {
					trainerID = (String) JOptionPane.showInputDialog(
							frame, "Please enter a valid trainer ID", "Customized Dialog",
							JOptionPane.PLAIN_MESSAGE, null, null, null);
				}

				if (!(trainerID == null || trainerID.isEmpty() || trainerID.matches("^.*[^0-9].*$"))) {
					String pokeball = (String) JOptionPane.showInputDialog(
							frame, "Enter the Pokeball name", "Customized Dialog",
							JOptionPane.PLAIN_MESSAGE, null, null, null);

					if (pokeball == null || pokeball.isEmpty() || pokeball.matches("^.*[^a-zA-Z0-9 ].*$")) {
						pokeball = (String) JOptionPane.showInputDialog(
								frame, "Please enter a valid Pokeball name", "Customized Dialog",
								JOptionPane.PLAIN_MESSAGE, null, null, null);
					}

					if (!(pokeball == null || pokeball.isEmpty() || pokeball.matches("^.*[^a-zA-Z0-9 ].*$"))) {
						if (QueryEngine.addTrainerPokeball(Integer.parseInt(trainerID), pokeball)) {
							JOptionPane.showMessageDialog(frame, "Success!");
						} else {
							JOptionPane.showMessageDialog(frame, "Failure!");
						}
					} else {
						JOptionPane.showMessageDialog(frame, "Cancelled");
					}
				} else {
					JOptionPane.showMessageDialog(frame, "Cancelled");
				}
			}
		});
		addMenu.add(addMenuItem6);

		// Create the Remove menu items
		JMenuItem removeMenuItem1 = new JMenuItem("Trainer");
		removeMenuItem1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String trainerID = (String) JOptionPane.showInputDialog(
						frame, "Enter the trainer ID", "Customized Dialog",
						JOptionPane.PLAIN_MESSAGE, null, null, null);

				if (trainerID == null || trainerID.isEmpty() || trainerID.matches("^.*[^0-9].*$")) {
					trainerID = (String) JOptionPane.showInputDialog(
							frame, "Please enter a valid trainer ID", "Customized Dialog",
							JOptionPane.PLAIN_MESSAGE, null, null, null);
				}

				if (!(trainerID == null || trainerID.isEmpty() || trainerID.matches("^.*[^0-9].*$"))) {
					if (QueryEngine.removeTrainer(Integer.parseInt(trainerID))) {
						JOptionPane.showMessageDialog(frame, "Success!");
					} else {
						JOptionPane.showMessageDialog(frame, "Failure!");
					}
				} else {
					JOptionPane.showMessageDialog(frame, "Cancelled");
				}
			}
		});
		removeMenu.add(removeMenuItem1);

		JMenuItem removeMenuItem2 = new JMenuItem("Trainer Pokemon");
		removeMenuItem2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String trainerID = (String) JOptionPane.showInputDialog(
						frame, "Enter the trainer ID", "Customized Dialog",
						JOptionPane.PLAIN_MESSAGE, null, null, null);

				if (trainerID == null || trainerID.isEmpty() || trainerID.matches("^.*[^0-9].*$")) {
					trainerID = (String) JOptionPane.showInputDialog(
							frame, "Please enter a valid trainer ID", "Customized Dialog",
							JOptionPane.PLAIN_MESSAGE, null, null, null);
				}

				if (!(trainerID == null || trainerID.isEmpty() || trainerID.matches("^.*[^0-9].*$"))) {
					String pokemonID = (String) JOptionPane.showInputDialog(
							frame, "Enter the Pokemon ID", "Customized Dialog",
							JOptionPane.PLAIN_MESSAGE, null, null, null);

					if (pokemonID == null || pokemonID.isEmpty() || pokemonID.matches("^.*[^0-9].*$")) {
						pokemonID = (String) JOptionPane.showInputDialog(
								frame, "Please enter a valid pokemon ID", "Customized Dialog",
								JOptionPane.PLAIN_MESSAGE, null, null, null);
					}

					if (!(pokemonID == null || pokemonID.isEmpty() || pokemonID.matches("^.*[^0-9].*$"))) {
						if (QueryEngine.removeTrainerPokemon(Integer.parseInt(trainerID),
								Integer.parseInt(pokemonID))) {
							JOptionPane.showMessageDialog(frame, "Success!");
						} else {
							JOptionPane.showMessageDialog(frame, "Failure!");
						}
					} else {
						JOptionPane.showMessageDialog(frame, "Cancelled");
					}
				} else {
					JOptionPane.showMessageDialog(frame, "Cancelled");
				}
			}
		});
		removeMenu.add(removeMenuItem2);

		JMenuItem removeMenuItem3 = new JMenuItem("Pokemon Move");
		removeMenuItem3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String pokemonID = (String) JOptionPane.showInputDialog(
						frame, "Enter the pokemon ID", "Customized Dialog",
						JOptionPane.PLAIN_MESSAGE, null, null, null);

				if (pokemonID == null || pokemonID.isEmpty() || pokemonID.matches("^.*[^0-9].*$")) {
					pokemonID = (String) JOptionPane.showInputDialog(
							frame, "Please enter a valid pokemon ID", "Customized Dialog",
							JOptionPane.PLAIN_MESSAGE, null, null, null);
				}

				if (!(pokemonID == null || pokemonID.isEmpty() || pokemonID.matches("^.*[^0-9].*$"))) {
					String move = (String) JOptionPane.showInputDialog(
							frame, "Enter the move name", "Customized Dialog",
							JOptionPane.PLAIN_MESSAGE, null, null, null);

					if (move == null || move.isEmpty() || move.matches("^.*[^a-zA-Z0-9 ].*$")) {
						move = (String) JOptionPane.showInputDialog(
								frame, "Please enter a valid move name", "Customized Dialog",
								JOptionPane.PLAIN_MESSAGE, null, null, null);
					}

					if (!(move == null || move.isEmpty() || move.matches("^.*[^a-zA-Z0-9 ].*$"))) {
						if (QueryEngine.removePokemonMove(Integer.parseInt(pokemonID), move)) {
							JOptionPane.showMessageDialog(frame, "Success!");
						} else {
							JOptionPane.showMessageDialog(frame, "Failure!");
						}
					} else {
						JOptionPane.showMessageDialog(frame, "Cancelled");
					}
				} else {
					JOptionPane.showMessageDialog(frame, "Cancelled");
				}
			}
		});
		removeMenu.add(removeMenuItem3);

		JMenuItem removeMenuItem4 = new JMenuItem("Trainer TMHM");
		removeMenuItem4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String trainerID = (String) JOptionPane.showInputDialog(
						frame, "Enter the trainer ID", "Customized Dialog",
						JOptionPane.PLAIN_MESSAGE, null, null, null);

				if (trainerID == null || trainerID.isEmpty() || trainerID.matches("^.*[^0-9].*$")) {
					trainerID = (String) JOptionPane.showInputDialog(
							frame, "Please enter a valid trainer ID", "Customized Dialog",
							JOptionPane.PLAIN_MESSAGE, null, null, null);
				}

				if (!(trainerID == null || trainerID.isEmpty() || trainerID.matches("^.*[^0-9].*$"))) {
					String tmhm = (String) JOptionPane.showInputDialog(
							frame, "Enter the TMHM name", "Customized Dialog",
							JOptionPane.PLAIN_MESSAGE, null, null, null);

					if (tmhm == null || tmhm.isEmpty() || tmhm.matches("^.*[^a-zA-Z0-9 ].*$")) {
						tmhm = (String) JOptionPane.showInputDialog(
								frame, "Please enter a valid TMHM name", "Customized Dialog",
								JOptionPane.PLAIN_MESSAGE, null, null, null);
					}

					if (!(tmhm == null || tmhm.isEmpty() || tmhm.matches("^.*[^a-zA-Z0-9 ].*$"))) {
						if (QueryEngine.removeTrainerTMHM(Integer.parseInt(trainerID), tmhm)) {
							JOptionPane.showMessageDialog(frame, "Success!");
						} else {
							JOptionPane.showMessageDialog(frame, "Failure!");
						}
					} else {
						JOptionPane.showMessageDialog(frame, "Cancelled");
					}
				} else {
					JOptionPane.showMessageDialog(frame, "Cancelled");
				}
			}
		});
		removeMenu.add(removeMenuItem4);

		JMenuItem removeMenuItem5 = new JMenuItem("Trainer Consumable");
		removeMenuItem5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String trainerID = (String) JOptionPane.showInputDialog(
						frame, "Enter the trainer ID", "Customized Dialog",
						JOptionPane.PLAIN_MESSAGE, null, null, null);

				if (trainerID == null || trainerID.isEmpty() || trainerID.matches("^.*[^0-9].*$")) {
					trainerID = (String) JOptionPane.showInputDialog(
							frame, "Please enter a valid trainer ID", "Customized Dialog",
							JOptionPane.PLAIN_MESSAGE, null, null, null);
				}

				if (!(trainerID == null || trainerID.isEmpty() || trainerID.matches("^.*[^0-9].*$"))) {
					String consumable = (String) JOptionPane.showInputDialog(
							frame, "Enter the consumable name", "Customized Dialog",
							JOptionPane.PLAIN_MESSAGE, null, null, null);

					if (consumable == null || consumable.isEmpty() || consumable.matches("^.*[^a-zA-Z0-9 ].*$")) {
						consumable = (String) JOptionPane.showInputDialog(
								frame, "Please enter a valid consumable name", "Customized Dialog",
								JOptionPane.PLAIN_MESSAGE, null, null, null);
					}

					if (!(consumable == null || consumable.isEmpty() || consumable.matches("^.*[^a-zA-Z0-9 ].*$"))) {
						if (QueryEngine.removeTrainerConsumable(Integer.parseInt(trainerID), consumable)) {
							JOptionPane.showMessageDialog(frame, "Success!");
						} else {
							JOptionPane.showMessageDialog(frame, "Failure!");
						}
					} else {
						JOptionPane.showMessageDialog(frame, "Cancelled");
					}
				} else {
					JOptionPane.showMessageDialog(frame, "Cancelled");
				}
			}
		});
		removeMenu.add(removeMenuItem5);

		JMenuItem removeMenuItem6 = new JMenuItem("Trainer Pokeball");
		removeMenuItem6.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String trainerID = (String) JOptionPane.showInputDialog(
						frame, "Enter the trainer ID", "Customized Dialog",
						JOptionPane.PLAIN_MESSAGE, null, null, null);

				if (trainerID == null || trainerID.isEmpty() || trainerID.matches("^.*[^0-9].*$")) {
					trainerID = (String) JOptionPane.showInputDialog(
							frame, "Please enter a valid trainer ID", "Customized Dialog",
							JOptionPane.PLAIN_MESSAGE, null, null, null);
				}

				if (!(trainerID == null || trainerID.isEmpty() || trainerID.matches("^.*[^0-9].*$"))) {
					String pokeball = (String) JOptionPane.showInputDialog(
							frame, "Enter the Pokeball name", "Customized Dialog",
							JOptionPane.PLAIN_MESSAGE, null, null, null);

					if (pokeball == null || pokeball.isEmpty() || pokeball.matches("^.*[^a-zA-Z0-9 ].*$")) {
						pokeball = (String) JOptionPane.showInputDialog(
								frame, "Please enter a valid Pokeball name", "Customized Dialog",
								JOptionPane.PLAIN_MESSAGE, null, null, null);
					}

					if (!(pokeball == null || pokeball.isEmpty() || pokeball.matches("^.*[^a-zA-Z0-9 ].*$"))) {
						if (QueryEngine.removeTrainerPokeball(Integer.parseInt(trainerID), pokeball)) {
							JOptionPane.showMessageDialog(frame, "Success!");
						} else {
							JOptionPane.showMessageDialog(frame, "Failure!");
						}
					} else {
						JOptionPane.showMessageDialog(frame, "Cancelled");
					}
				} else {
					JOptionPane.showMessageDialog(frame, "Cancelled");
				}
			}
		});
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
