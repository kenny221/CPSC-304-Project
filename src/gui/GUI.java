package gui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import app.QueryEngine;

public class GUI {
	private static boolean adminAccess = false;

	private static final String ADMIN_PASSWORD = "admin";

	/**
	 * Creates the GUI.
	 */
	private static void createGUI() {
		// Create the main window
		JFrame frame = new JFrame("Pokemon Database");
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
		menuBar.setPreferredSize(new Dimension(500, 20));

		// Create the menus
		JMenu addMenu = new JMenu("Add");
		menuBar.add(addMenu);
		JMenu removeMenu = new JMenu("Remove");
		menuBar.add(removeMenu);
		JMenu searchMenu = new JMenu("Search");
		menuBar.add(searchMenu);
		JMenu showMenu = new JMenu("Show");
		menuBar.add(showMenu);
		JMenu updateMenu = new JMenu("Update");
		menuBar.add(updateMenu);
		JMenu avgMenu = new JMenu("AVG");
		menuBar.add(avgMenu);
		JMenu minMenu = new JMenu("MIN");
		menuBar.add(minMenu);
		JMenu maxMenu = new JMenu("MAX");
		menuBar.add(maxMenu);
		JMenu countMenu = new JMenu("COUNT");
		menuBar.add(countMenu);
		JMenu adminMenu = new JMenu("Admin");
		menuBar.add(adminMenu);

		// Create the Add menu items
		JMenuItem addMenuItem1 = new JMenuItem("Trainer");
		addMenuItem1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (adminAccess) {
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

						if (gender != null) {
							QueryEngine.addTrainer(name, gender);
							JOptionPane.showMessageDialog(frame, "Success!");
						} else {
							JOptionPane.showMessageDialog(frame, "Cancelled");
						}

					} else {
						JOptionPane.showMessageDialog(frame, "Cancelled");
					}
				} else {
					JOptionPane.showMessageDialog(frame, "Insufficient privileges");
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

					if (consumable == null || consumable.isEmpty()
							|| consumable.matches("^.*[^a-zA-Z0-9 ].*$")) {
						consumable = (String) JOptionPane.showInputDialog(
								frame, "Please enter a valid consumable name", "Customized Dialog",
								JOptionPane.PLAIN_MESSAGE, null, null, null);
					}

					if (!(consumable == null || consumable.isEmpty()
							|| consumable.matches("^.*[^a-zA-Z0-9 ].*$"))) {
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
				if (adminAccess) {
					String id = (String) JOptionPane.showInputDialog(
							frame, "Enter the trainer ID", "Customized Dialog",
							JOptionPane.PLAIN_MESSAGE, null, null, null);

					if (id == null || id.isEmpty() || id.matches("^.*[^0-9].*$")) {
						id = (String) JOptionPane.showInputDialog(
								frame, "Please enter a valid trainer ID", "Customized Dialog",
								JOptionPane.PLAIN_MESSAGE, null, null, null);
					}

					if (!(id == null || id.isEmpty() || id.matches("^.*[^0-9].*$"))) {
						if (QueryEngine.removeTrainer(Integer.parseInt(id))) {
							JOptionPane.showMessageDialog(frame, "Success!");
						} else {
							JOptionPane.showMessageDialog(frame, "Failure!");
						}
					} else {
						JOptionPane.showMessageDialog(frame, "Cancelled");
					}
				} else {
					JOptionPane.showMessageDialog(frame, "Insufficient privileges");
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

				if (!(trainerID == null || trainerID.isEmpty()
						|| trainerID.matches("^.*[^0-9].*$"))) {
					String consumable = (String) JOptionPane.showInputDialog(
							frame, "Enter the consumable name", "Customized Dialog",
							JOptionPane.PLAIN_MESSAGE, null, null, null);

					if (consumable == null || consumable.isEmpty()
							|| consumable.matches("^.*[^a-zA-Z0-9 ].*$")) {
						consumable = (String) JOptionPane.showInputDialog(
								frame, "Please enter a valid consumable name", "Customized Dialog",
								JOptionPane.PLAIN_MESSAGE, null, null, null);
					}

					if (!(consumable == null || consumable.isEmpty()
							|| consumable.matches("^.*[^a-zA-Z0-9 ].*$"))) {
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

				if (!(trainerID == null || trainerID.isEmpty()
						|| trainerID.matches("^.*[^0-9].*$"))) {
					String pokeball = (String) JOptionPane.showInputDialog(
							frame, "Enter the Pokeball name", "Customized Dialog",
							JOptionPane.PLAIN_MESSAGE, null, null, null);

					if (pokeball == null || pokeball.isEmpty()
							|| pokeball.matches("^.*[^a-zA-Z0-9 ].*$")) {
						pokeball = (String) JOptionPane.showInputDialog(
								frame, "Please enter a valid Pokeball name", "Customized Dialog",
								JOptionPane.PLAIN_MESSAGE, null, null, null);
					}

					if (!(pokeball == null || pokeball.isEmpty()
							|| pokeball.matches("^.*[^a-zA-Z0-9 ].*$"))) {
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

		// Create the Update menu items
		JMenuItem updateMenuItem1 = new JMenuItem("Trainer Name");
		updateMenuItem1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String id = (String) JOptionPane.showInputDialog(
						frame, "Enter the trainer ID", "Customized Dialog",
						JOptionPane.PLAIN_MESSAGE, null, null, null);

				if (id == null || id.isEmpty() || id.matches("^.*[^0-9].*$")) {
					id = (String) JOptionPane.showInputDialog(
							frame, "Please enter a valid trainer ID", "Customized Dialog",
							JOptionPane.PLAIN_MESSAGE, null, null, null);
				}

				if (!(id == null || id.isEmpty() || id.matches("^.*[^0-9].*$"))) {
					String name = (String) JOptionPane.showInputDialog(
							frame, "Enter the new trainer name", "Customized Dialog",
							JOptionPane.PLAIN_MESSAGE, null, null, null);

					if (name == null || name.isEmpty() || name.matches("^.*[^a-zA-Z0-9 ].*$")) {
						name = (String) JOptionPane.showInputDialog(
								frame, "Please enter a valid name", "Customized Dialog",
								JOptionPane.PLAIN_MESSAGE, null, null, null);
					}

					if (!(name == null || name.isEmpty() || name.matches("^.*[^a-zA-Z0-9 ].*$"))) {
						if (QueryEngine.updateTrainerName(Integer.parseInt(id), name)) {
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
		updateMenu.add(updateMenuItem1);

		// Create the Search menu items
		JMenuItem searchMenuItem1 = new JMenuItem("Trainer ID");
		searchMenuItem1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String id = (String) JOptionPane.showInputDialog(
						frame, "Enter the trainer ID", "Customized Dialog",
						JOptionPane.PLAIN_MESSAGE, null, null, null);

				if (id == null || id.isEmpty() || id.matches("^.*[^0-9].*$")) {
					id = (String) JOptionPane.showInputDialog(
							frame, "Please enter a valid trainer ID", "Customized Dialog",
							JOptionPane.PLAIN_MESSAGE, null, null, null);
				}

				if (!(id == null || id.isEmpty() || id.matches("^.*[^0-9].*$"))) {
					JTable table = null;

					try {
						table = new JTable(buildTableModel(QueryEngine.
								searchTrainerID(Integer.parseInt(id))));
					} catch (SQLException exception) {
						exception.printStackTrace();
					}

					JOptionPane.showMessageDialog(null, new JScrollPane(table));
				} else {
					JOptionPane.showMessageDialog(frame, "Cancelled");
				}
			}
		});
		searchMenu.add(searchMenuItem1);

		JMenuItem searchMenuItem2 = new JMenuItem("Trainer Name");
		searchMenuItem2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = (String) JOptionPane.showInputDialog(
						frame, "Enter the trainer name", "Customized Dialog",
						JOptionPane.PLAIN_MESSAGE, null, null, null);

				if (name == null || name.isEmpty() || name.matches("^.*[^a-zA-Z0-9 ].*$")) {
					name = (String) JOptionPane.showInputDialog(
							frame, "Please enter a valid trainer name", "Customized Dialog",
							JOptionPane.PLAIN_MESSAGE, null, null, null);
				}

				if (!(name == null || name.isEmpty() || name.matches("^.*[^a-zA-Z0-9 ].*$"))) {
					JTable table = null;

					try {
						table = new JTable(buildTableModel(QueryEngine.searchTrainer(name)));
					} catch (SQLException exception) {
						exception.printStackTrace();
					}

					JOptionPane.showMessageDialog(null, new JScrollPane(table));
				} else {
					JOptionPane.showMessageDialog(frame, "Cancelled");
				}
			}
		});
		searchMenu.add(searchMenuItem2);

		JMenuItem searchMenuItem3 = new JMenuItem("Pokemon ID");
		searchMenuItem3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String id = (String) JOptionPane.showInputDialog(
						frame, "Enter the Pokemon ID", "Customized Dialog",
						JOptionPane.PLAIN_MESSAGE, null, null, null);

				if (id == null || id.isEmpty() || id.matches("^.*[^0-9].*$")) {
					id = (String) JOptionPane.showInputDialog(
							frame, "Please enter a valid Pokemon ID", "Customized Dialog",
							JOptionPane.PLAIN_MESSAGE, null, null, null);
				}

				if (!(id == null || id.isEmpty() || id.matches("^.*[^0-9].*$"))) {
					JTable table = null;

					try {
						table = new JTable(buildTableModel(QueryEngine.
								searchPokemonID(Integer.parseInt(id))));
					} catch (SQLException exception) {
						exception.printStackTrace();
					}

					JOptionPane.showMessageDialog(null, new JScrollPane(table));
				} else {
					JOptionPane.showMessageDialog(frame, "Cancelled");
				}
			}
		});
		searchMenu.add(searchMenuItem3);

		JMenuItem searchMenuItem4 = new JMenuItem("Pokemon Name");
		searchMenuItem4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = (String) JOptionPane.showInputDialog(
						frame, "Enter the Pokemon name", "Customized Dialog",
						JOptionPane.PLAIN_MESSAGE, null, null, null);

				if (name == null || name.isEmpty() || name.matches("^.*[^a-zA-Z0-9 ].*$")) {
					name = (String) JOptionPane.showInputDialog(
							frame, "Please enter a valid Pokemon name", "Customized Dialog",
							JOptionPane.PLAIN_MESSAGE, null, null, null);
				}

				if (!(name == null || name.isEmpty() || name.matches("^.*[^a-zA-Z0-9 ].*$"))) {
					JTable table = null;

					try {
						table = new JTable(buildTableModel(QueryEngine.searchPokemon(name)));
					} catch (SQLException exception) {
						exception.printStackTrace();
					}

					JOptionPane.showMessageDialog(null, new JScrollPane(table));
				} else {
					JOptionPane.showMessageDialog(frame, "Cancelled");
				}
			}
		});
		searchMenu.add(searchMenuItem4);

		JMenuItem searchMenuItem5 = new JMenuItem("Move");
		searchMenuItem5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = (String) JOptionPane.showInputDialog(
						frame, "Enter the move name", "Customized Dialog",
						JOptionPane.PLAIN_MESSAGE, null, null, null);

				if (name == null || name.isEmpty() || name.matches("^.*[^a-zA-Z0-9 ].*$")) {
					name = (String) JOptionPane.showInputDialog(
							frame, "Please enter a valid move name", "Customized Dialog",
							JOptionPane.PLAIN_MESSAGE, null, null, null);
				}

				if (!(name == null || name.isEmpty() || name.matches("^.*[^a-zA-Z0-9 ].*$"))) {
					JTable table = null;

					try {
						table = new JTable(buildTableModel(QueryEngine.searchMove(name)));
					} catch (SQLException exception) {
						exception.printStackTrace();
					}

					JOptionPane.showMessageDialog(null, new JScrollPane(table));
				} else {
					JOptionPane.showMessageDialog(frame, "Cancelled");
				}
			}
		});
		searchMenu.add(searchMenuItem5);

		JMenuItem searchMenuItem6 = new JMenuItem("Item");
		searchMenuItem6.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = (String) JOptionPane.showInputDialog(
						frame, "Enter the item name", "Customized Dialog",
						JOptionPane.PLAIN_MESSAGE, null, null, null);

				if (name == null || name.isEmpty() || name.matches("^.*[^a-zA-Z0-9 ].*$")) {
					name = (String) JOptionPane.showInputDialog(
							frame, "Please enter a valid item name", "Customized Dialog",
							JOptionPane.PLAIN_MESSAGE, null, null, null);
				}

				if (!(name == null || name.isEmpty() || name.matches("^.*[^a-zA-Z0-9 ].*$"))) {
					JTable table = null;

					try {
						table = new JTable(buildTableModel(QueryEngine.searchItem(name)));
					} catch (SQLException exception) {
						exception.printStackTrace();
					}

					JOptionPane.showMessageDialog(null, new JScrollPane(table));
				} else {
					JOptionPane.showMessageDialog(frame, "Cancelled");
				}
			}
		});
		searchMenu.add(searchMenuItem6);

		// Create the Show menu items
		JMenuItem showMenuItem1 = new JMenuItem("Trainers");
		showMenuItem1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object[] options = {"ID", "Name", "Gender", "ID, Name",
						"ID, Gender", "Name, Gender", "ID, Name, Gender"};
				String option = (String) JOptionPane.showInputDialog(
						frame, "Select the desired attributes", "Customized Dialog",
						JOptionPane.PLAIN_MESSAGE, null, options, null);

				if (option != null) {
					Object[] genders = {"Male", "Female", "Both"};
					String gender = (String) JOptionPane.showInputDialog(
							frame, "Select the desired gender", "Customized Dialog",
							JOptionPane.PLAIN_MESSAGE, null, genders, null);

					if (gender != null) {
						JTable table = null;

						try {
							table = new JTable(buildTableModel(QueryEngine.
									showAllTrainers(option, gender)));
						} catch (SQLException exception) {
							exception.printStackTrace();
						}

						JOptionPane.showMessageDialog(null, new JScrollPane(table));
					}
				}
			}
		});
		showMenu.add(showMenuItem1);

		JMenuItem showMenuItem2 = new JMenuItem("Trainer Pokemon");
		showMenuItem2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String id = (String) JOptionPane.showInputDialog(
						frame, "Enter the trainer ID", "Customized Dialog",
						JOptionPane.PLAIN_MESSAGE, null, null, null);

				if (id == null || id.isEmpty() || id.matches("^.*[^0-9].*$")) {
					id = (String) JOptionPane.showInputDialog(
							frame, "Please enter a valid trainer ID", "Customized Dialog",
							JOptionPane.PLAIN_MESSAGE, null, null, null);
				}

				if (!(id == null || id.isEmpty() || id.matches("^.*[^0-9].*$"))) {
					JTable table = null;

					try {
						table = new JTable(buildTableModel(QueryEngine.
								getTrainerPokemon(Integer.parseInt(id))));
					} catch (SQLException exception) {
						exception.printStackTrace();
					}

					JOptionPane.showMessageDialog(null, new JScrollPane(table));
				} else {
					JOptionPane.showMessageDialog(frame, "Cancelled");
				}
			}
		});
		showMenu.add(showMenuItem2);

		JMenuItem showMenuItem3 = new JMenuItem("Trainer Items");
		showMenuItem3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String id = (String) JOptionPane.showInputDialog(
						frame, "Enter the trainer ID", "Customized Dialog",
						JOptionPane.PLAIN_MESSAGE, null, null, null);

				if (id == null || id.isEmpty() || id.matches("^.*[^0-9].*$")) {
					id = (String) JOptionPane.showInputDialog(
							frame, "Please enter a valid trainer ID", "Customized Dialog",
							JOptionPane.PLAIN_MESSAGE, null, null, null);
				}

				if (!(id == null || id.isEmpty() || id.matches("^.*[^0-9].*$"))) {
					JTable table = null;

					try {
						table = new JTable(buildTableModel(QueryEngine.
								getTrainerItems(Integer.parseInt(id))));
					} catch (SQLException exception) {
						exception.printStackTrace();
					}

					JOptionPane.showMessageDialog(null, new JScrollPane(table));
				} else {
					JOptionPane.showMessageDialog(frame, "Cancelled");
				}
			}
		});
		showMenu.add(showMenuItem3);

		JMenuItem showMenuItem4 = new JMenuItem("Trainer Unowned Pokemon");
		showMenuItem4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String id = (String) JOptionPane.showInputDialog(
						frame, "Enter the trainer ID", "Customized Dialog",
						JOptionPane.PLAIN_MESSAGE, null, null, null);

				if (id == null || id.isEmpty() || id.matches("^.*[^0-9].*$")) {
					id = (String) JOptionPane.showInputDialog(
							frame, "Please enter a valid trainer ID", "Customized Dialog",
							JOptionPane.PLAIN_MESSAGE, null, null, null);
				}

				if (!(id == null || id.isEmpty() || id.matches("^.*[^0-9].*$"))) {
					JTable table = null;

					try {
						table = new JTable(buildTableModel(QueryEngine.
								findPokemonNotOwnedByTrainer(Integer.parseInt(id))));
					} catch (SQLException exception) {
						exception.printStackTrace();
					}

					JOptionPane.showMessageDialog(null, new JScrollPane(table));
				} else {
					JOptionPane.showMessageDialog(frame, "Cancelled");
				}
			}
		});
		showMenu.add(showMenuItem4);

		// Create the AVG menu items
		JMenuItem avgMenuItem1 = new JMenuItem("Height by Pokemon Species");
		avgMenuItem1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object[] options = {"Species with the MINIMUM average height",
				"Species with the MAXIMUM average height"};
				String option = (String) JOptionPane.showInputDialog(
						frame, "Select the option you want", "Customized Dialog",
						JOptionPane.PLAIN_MESSAGE, null, options, null);

				if (option != null) {
					JTable table = null;

					try {
						if (option.contains("MINIMUM")) {
							table = new JTable(buildTableModel(QueryEngine.
									findSpeciesWithMinimumAverageHeight()));
						} else {
							table = new JTable(buildTableModel(QueryEngine.
									findSpeciesWithMaximumAverageHeight()));
						}
					} catch (SQLException exception) {
						exception.printStackTrace();
					}

					JOptionPane.showMessageDialog(null, new JScrollPane(table));
				}
			}
		});
		avgMenu.add(avgMenuItem1);

		JMenuItem avgMenuItem2 = new JMenuItem("Weight by Pokemon Species");
		avgMenuItem2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object[] options = {"Species with the MINIMUM average weight",
				"Species with the MAXIMUM average weight"};
				String option = (String) JOptionPane.showInputDialog(
						frame, "Select the option you want", "Customized Dialog",
						JOptionPane.PLAIN_MESSAGE, null, options, null);

				if (option != null) {
					JTable table = null;

					try {
						if (option.contains("MINIMUM")) {
							table = new JTable(buildTableModel(QueryEngine.
									findSpeciesWithMinimumAverageWeight()));
						} else {
							table = new JTable(buildTableModel(QueryEngine.
									findSpeciesWithMaximumAverageWeight()));
						}
					} catch (SQLException exception) {
						exception.printStackTrace();
					}

					JOptionPane.showMessageDialog(null, new JScrollPane(table));
				}
			}
		});
		avgMenu.add(avgMenuItem2);

		// Create the MIN menu items
		JMenuItem minMenuItem1 = new JMenuItem("Pokemon Height");
		minMenuItem1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JTable table = null;

				try {
					table = new JTable(buildTableModel(QueryEngine.findShortestPokemon()));
				} catch (SQLException exception) {
					exception.printStackTrace();
				}

				JOptionPane.showMessageDialog(null, new JScrollPane(table));
			}
		});
		minMenu.add(minMenuItem1);

		JMenuItem minMenuItem2 = new JMenuItem("Pokemon Weight");
		minMenuItem2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JTable table = null;

				try {
					table = new JTable(buildTableModel(QueryEngine.findLightestPokemon()));
				} catch (SQLException exception) {
					exception.printStackTrace();
				}

				JOptionPane.showMessageDialog(null, new JScrollPane(table));
			}
		});
		minMenu.add(minMenuItem2);

		// Create the MAX menu items
		JMenuItem maxMenuItem1 = new JMenuItem("Pokemon Height");
		maxMenuItem1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JTable table = null;

				try {
					table = new JTable(buildTableModel(QueryEngine.findTallestPokemon()));
				} catch (SQLException exception) {
					exception.printStackTrace();
				}

				JOptionPane.showMessageDialog(null, new JScrollPane(table));
			}
		});
		maxMenu.add(maxMenuItem1);

		JMenuItem maxMenuItem2 = new JMenuItem("Pokemon Weight");
		maxMenuItem2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JTable table = null;

				try {
					table = new JTable(buildTableModel(QueryEngine.findHeaviestPokemon()));
				} catch (SQLException exception) {
					exception.printStackTrace();
				}

				JOptionPane.showMessageDialog(null, new JScrollPane(table));
			}
		});
		maxMenu.add(maxMenuItem2);

		// Create the COUNT menu items
		JMenuItem countMenuItem1 = new JMenuItem("Pokemon by Species");
		countMenuItem1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object[] options = {"Species with the MINIMUM count",
				"Species with the MAXIMUM count"};
				String option = (String) JOptionPane.showInputDialog(
						frame, "Select the option you want", "Customized Dialog",
						JOptionPane.PLAIN_MESSAGE, null, options, null);

				if (option != null) {
					JTable table = null;

					try {
						if (option.contains("MINIMUM")) {
							table = new JTable(buildTableModel(QueryEngine.
									findSpeciesWithMinimumCount()));
						} else {
							table = new JTable(buildTableModel(QueryEngine.
									findSpeciesWithMaximumCount()));
						}
					} catch (SQLException exception) {
						exception.printStackTrace();
					}

					JOptionPane.showMessageDialog(null, new JScrollPane(table));
				}
			}
		});
		countMenu.add(countMenuItem1);

		// Create the Admin menu items
		JMenuItem adminMenuItem1 = new JMenuItem("Login");
		adminMenuItem1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (adminAccess) {
					JOptionPane.showMessageDialog(frame, "Already logged in");
				} else {
					String password = (String) JOptionPane.showInputDialog(
							frame, "Enter the admin password", "Customized Dialog",
							JOptionPane.PLAIN_MESSAGE, null, null, null);

					if (password == null) {
						// Do nothing
					} else if (password.isEmpty() || !password.equals(ADMIN_PASSWORD)) {
						password = (String) JOptionPane.showInputDialog(frame,
								"Password incorrect, please try again", "Customized Dialog",
								JOptionPane.PLAIN_MESSAGE, null, null, null);
					}

					if (password == null) {
						// Do nothing
					} else if (password.equals(ADMIN_PASSWORD)) {
						adminAccess = true;
						JOptionPane.showMessageDialog(frame, "Login success");
					} else {
						JOptionPane.showMessageDialog(frame, "Cancelled");
					}
				}
			}
		});
		adminMenu.add(adminMenuItem1);

		JMenuItem adminMenuItem2 = new JMenuItem("Logout");
		adminMenuItem2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (adminAccess) {
					adminAccess = false;
					JOptionPane.showMessageDialog(frame, "Logout success");
				} else {
					JOptionPane.showMessageDialog(frame, "You are not logged in as an admin");
				}
			}
		});
		adminMenu.add(adminMenuItem2);

		// Create the background
		JLabel background = new JLabel();
		background.setOpaque(true);
		background.setBackground(new Color(135, 206, 250));
		background.setPreferredSize(new Dimension(500, 300));

		// Set the menu bar and background to the content pane
		frame.setJMenuBar(menuBar);
		frame.getContentPane().add(background, BorderLayout.CENTER);

		// Display the window
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Produces a table given a ResultSet object.
	 * This code is based on the code found at:
	 * http://stackoverflow.com/questions/11734561/how-to-view-database-resultset-in-java-swing
	 * @param r The row(s) containing the query results.
	 * @return A model of the table.
	 * @throws SQLException
	 */
	public static DefaultTableModel buildTableModel(ResultSet r)
			throws SQLException {
		ResultSetMetaData rsMetaData = r.getMetaData();

		// Get column information
		Vector<String> names = new Vector<String>();
		int count = rsMetaData.getColumnCount();

		for (int column = 1; column <= count; column++) {
			names.add(rsMetaData.getColumnName(column));
		}

		// Get data
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();

		while (r.next()) {
			Vector<Object> vector = new Vector<Object>();

			for (int i = 1; i <= count; i++) {
				vector.add(r.getObject(i));
			}

			data.add(vector);
		}

		QueryEngine.closeStatement();

		return new DefaultTableModel(data, names);
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
