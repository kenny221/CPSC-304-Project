package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

public class QueryEngine {
	private static Connection c;

	/**
	 * Adds a new trainer to the database.
	 * @param name The name of the trainer.
	 * @param gender The gender of the trainer.
	 */
	public static void addTrainer(String name, String gender) {
		try {
			Statement s = c.createStatement();
			ResultSet r = s.executeQuery("SELECT * FROM Trainer");
			ArrayList<Integer> ids = new ArrayList<Integer>();

			while (r.next()) {
				ids.add(r.getInt(1));
			}

			// Randomly generate a new ID
			int id = random(1, 999999);

			// Check if ID already exists
			while (ids.contains(id)) {
				id = random(1, 999999);
			}

			s.executeUpdate("INSERT INTO Trainer VALUES (" + id + ", '"
					+ name + "', '" + gender + "')");

			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Removes a trainer and all information about the Pokemon and items
	 * owned by that trainer.
	 * @param id The ID of the trainer.
	 */
	public static void removeTrainer(int id) {
		try {
			Statement s = c.createStatement();
			ResultSet r = s.executeQuery("SELECT * FROM Trainer");
			ArrayList<Integer> ids = new ArrayList<Integer>();

			while (r.next()) {
				ids.add(r.getInt(1));
			}

			// Check if trainer exists
			if (ids.contains(id)) {
				s.executeUpdate("DELETE FROM Trainer WHERE ID = " + id);
			} else {
				System.out.println("ERROR: Trainer does not exist");
			}

			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds a Pokemon to the list of Pokemon owned by the given trainer.
	 * @param trainerID The ID of the trainer.
	 * @param pokemonID The ID of the Pokemon.
	 */
	public static void addTrainerPokemon(int trainerID, int pokemonID) {
		try {
			Statement s = c.createStatement();
			ResultSet r = s.executeQuery("SELECT * FROM Trainer");
			ArrayList<Integer> trainerIDs = new ArrayList<Integer>();

			while (r.next()) {
				trainerIDs.add(r.getInt(1));
			}

			// Check if trainer exists
			if (trainerIDs.contains(trainerID)) {
				r = s.executeQuery("SELECT * FROM Pokemon");
				ArrayList<Integer> pokemonIDs = new ArrayList<Integer>();

				while (r.next()) {
					pokemonIDs.add(r.getInt(1));
				}

				// Check if Pokemon exists
				if (pokemonIDs.contains(pokemonID)) {
					s.executeUpdate("INSERT INTO Catches VALUES (" + trainerID + ", "
							+ pokemonID + ")");
				} else {
					System.out.println("ERROR: Pokemon does not exist");
				}
			} else {
				System.out.println("ERROR: Trainer does not exist");
			}

			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Removes a Pokemon from the list of Pokemon owned by the given trainer.
	 * @param trainerID The ID of the trainer.
	 * @param pokemonID The ID of the Pokemon.
	 */
	public static void removeTrainerPokemon(int trainerID, int pokemonID) {
		try {
			Statement s = c.createStatement();
			ResultSet r = s.executeQuery("SELECT * FROM Trainer");
			ArrayList<Integer> trainerIDs = new ArrayList<Integer>();

			while (r.next()) {
				trainerIDs.add(r.getInt(1));
			}

			// Check if trainer exists
			if (trainerIDs.contains(trainerID)) {
				r = s.executeQuery("SELECT * FROM Pokemon");
				ArrayList<Integer> pokemonIDs = new ArrayList<Integer>();

				while (r.next()) {
					pokemonIDs.add(r.getInt(1));
				}

				// Check if Pokemon exists
				if (pokemonIDs.contains(pokemonID)) {
					s.executeUpdate("DELETE FROM Catches WHERE TrainerID = " + trainerID
							+ " AND PokemonID = " + pokemonID);
				} else {
					System.out.println("ERROR: Pokemon does not exist");
				}
			} else {
				System.out.println("ERROR: Trainer does not exist");
			}

			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds a move to the list of moves known by the given Pokemon.
	 * @param pokemonID The ID of the Pokemon.
	 * @param move The name of the move.
	 */
	public static void addPokemonMove(int pokemonID, String move) {
		move = addWhitespace(move, 20);

		try {
			Statement s = c.createStatement();
			ResultSet r = s.executeQuery("SELECT * FROM Pokemon");
			ArrayList<Integer> pokemonIDs = new ArrayList<Integer>();

			while (r.next()) {
				pokemonIDs.add(r.getInt(1));
			}

			// Check if Pokemon exists
			if (pokemonIDs.contains(pokemonID)) {
				r = s.executeQuery("SELECT * FROM Move");
				ArrayList<String> moves = new ArrayList<String>();

				while (r.next()) {
					moves.add(r.getString(1));
				}

				// Check if move exists
				if (moves.contains(move)) {
					s.executeUpdate("INSERT INTO KnowsMove VALUES (" + pokemonID + ", '"
							+ move + "')");
				} else {
					System.out.println("ERROR: Move does not exist");
				}
			} else {
				System.out.println("ERROR: Pokemon does not exist");
			}

			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Removes a move from the list of moves known by the given Pokemon.
	 * @param pokemonID The ID of the Pokemon.
	 * @param move The name of the move.
	 */
	public static void removePokemonMove(int pokemonID, String move) {
		move = addWhitespace(move, 20);

		try {
			Statement s = c.createStatement();
			ResultSet r = s.executeQuery("SELECT * FROM Pokemon");
			ArrayList<Integer> pokemonIDs = new ArrayList<Integer>();

			while (r.next()) {
				pokemonIDs.add(r.getInt(1));
			}

			// Check if Pokemon exists
			if (pokemonIDs.contains(pokemonID)) {
				r = s.executeQuery("SELECT * FROM Move");
				ArrayList<String> moves = new ArrayList<String>();

				while (r.next()) {
					moves.add(r.getString(1));
				}

				// Check if move exists
				if (moves.contains(move)) {
					s.executeUpdate("DELETE FROM KnowsMove WHERE PokemonID = " + pokemonID
							+ " AND MoveName = '" + move + "'");
				} else {
					System.out.println("ERROR: Move does not exist");
				}
			} else {
				System.out.println("ERROR: Pokemon does not exist");
			}

			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds a TMHM to the list of TMHMs owned by the given trainer.
	 * @param trainerID The ID of the trainer.
	 * @param tmhm The name of the TMHM.
	 */
	public static void addTrainerTMHM(int trainerID, String tmhm) {
		tmhm = addWhitespace(tmhm, 5);

		try {
			Statement s = c.createStatement();
			ResultSet r = s.executeQuery("SELECT * FROM Trainer");
			ArrayList<Integer> trainerIDs = new ArrayList<Integer>();

			while (r.next()) {
				trainerIDs.add(r.getInt(1));
			}

			// Check if trainer exists
			if (trainerIDs.contains(trainerID)) {
				r = s.executeQuery("SELECT * FROM TMHM");
				ArrayList<String> tmhms = new ArrayList<String>();

				while (r.next()) {
					tmhms.add(r.getString(1));
				}

				// Check if TMHM exists
				if (tmhms.contains(tmhm)) {
					s.executeUpdate("INSERT INTO HasTMHM VALUES (" + trainerID + ", '"
							+ tmhm + "')");
				} else {
					System.out.println("ERROR: TMHM does not exist");
				}
			} else {
				System.out.println("ERROR: Trainer does not exist");
			}

			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Removes a TMHM from the list of TMHMs owned by the given trainer.
	 * @param trainerID The ID of the trainer.
	 * @param tmhm The name of the TMHM.
	 */
	public static void removeTrainerTMHM(int trainerID, String tmhm) {
		tmhm = addWhitespace(tmhm, 5);

		try {
			Statement s = c.createStatement();
			ResultSet r = s.executeQuery("SELECT * FROM Trainer");
			ArrayList<Integer> trainerIDs = new ArrayList<Integer>();

			while (r.next()) {
				trainerIDs.add(r.getInt(1));
			}

			// Check if trainer exists
			if (trainerIDs.contains(trainerID)) {
				r = s.executeQuery("SELECT * FROM TMHM");
				ArrayList<String> tmhms = new ArrayList<String>();

				while (r.next()) {
					tmhms.add(r.getString(1));
				}

				// Check if TMHM exists
				if (tmhms.contains(tmhm)) {
					s.executeUpdate("DELETE FROM HasTMHM WHERE TrainerID = " + trainerID
							+ " AND TMHMName = '" + tmhm + "'");
				} else {
					System.out.println("ERROR: TMHM does not exist");
				}
			} else {
				System.out.println("ERROR: Trainer does not exist");
			}

			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds a consumable to the list of consumables owned by the given trainer.
	 * @param trainerID The ID of the trainer.
	 * @param consumable The name of the consumable.
	 */
	public static void addTrainerConsumable(int trainerID, String consumable) {
		consumable = addWhitespace(consumable, 20);

		try {
			Statement s = c.createStatement();
			ResultSet r = s.executeQuery("SELECT * FROM Trainer");
			ArrayList<String> trainerIDs = new ArrayList<String>();

			while (r.next()) {
				trainerIDs.add(r.getString(1));
			}

			// Check if trainer exists
			if (trainerIDs.contains(trainerID)) {
				r = s.executeQuery("SELECT * FROM Consumable");
				ArrayList<Integer> consumables = new ArrayList<Integer>();

				while (r.next()) {
					consumables.add(r.getInt(1));
				}

				// Check if consumable exists
				if (consumables.contains(consumable)) {
					s.executeUpdate("INSERT INTO HasConsumable VALUES (" + trainerID + ", '"
							+ consumable + "')");
				} else {
					System.out.println("ERROR: Consumable does not exist");
				}
			} else {
				System.out.println("ERROR: Trainer does not exist");
			}

			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Removes a consumable from the list of consumables owned by the given trainer.
	 * @param trainerID The ID of the trainer.
	 * @param consumable The name of the consumable.
	 */
	public static void removeTrainerConsumable(int trainerID, String consumable) {
		consumable = addWhitespace(consumable, 20);

		try {
			Statement s = c.createStatement();
			ResultSet r = s.executeQuery("SELECT * FROM Trainer");
			ArrayList<Integer> trainerIDs = new ArrayList<Integer>();

			while (r.next()) {
				trainerIDs.add(r.getInt(1));
			}

			// Check if trainer exists
			if (trainerIDs.contains(trainerID)) {
				r = s.executeQuery("SELECT * FROM Consumable");
				ArrayList<String> consumables = new ArrayList<String>();

				while (r.next()) {
					consumables.add(r.getString(1));
				}

				// Check if consumable exists
				if (consumables.contains(consumable)) {
					s.executeUpdate("DELETE FROM HasConsumable WHERE TrainerID = " + trainerID
							+ " AND ConsumableName = '" + consumable + "'");
				} else {
					System.out.println("ERROR: Consumable does not exist");
				}
			} else {
				System.out.println("ERROR: Trainer does not exist");
			}

			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds a Pokeball to the list of Pokeballs owned by the given trainer.
	 * @param trainerID The ID of the trainer.
	 * @param pokeball The name of the Pokeball.
	 */
	public static void addTrainerPokeball(int trainerID, String pokeball) {
		pokeball = addWhitespace(pokeball, 20);

		try {
			Statement s = c.createStatement();
			ResultSet r = s.executeQuery("SELECT * FROM Trainer");
			ArrayList<String> trainerIDs = new ArrayList<String>();

			while (r.next()) {
				trainerIDs.add(r.getString(1));
			}

			// Check if trainer exists
			if (trainerIDs.contains(trainerID)) {
				r = s.executeQuery("SELECT * FROM Pokeball");
				ArrayList<Integer> pokeballs = new ArrayList<Integer>();

				while (r.next()) {
					pokeballs.add(r.getInt(1));
				}

				// Check if Pokeball exists
				if (pokeballs.contains(pokeball)) {
					s.executeUpdate("INSERT INTO HasPokeball VALUES (" + trainerID + ", '"
							+ pokeball + "')");
				} else {
					System.out.println("ERROR: Pokeball does not exist");
				}
			} else {
				System.out.println("ERROR: Trainer does not exist");
			}

			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Removes a Pokeball from the list of Pokeballs owned by the given trainer.
	 * @param trainerID The ID of the trainer.
	 * @param pokeball The name of the Pokeball.
	 */
	public static void removeTrainerPokeball(int trainerID, String pokeball) {
		pokeball = addWhitespace(pokeball, 20);

		try {
			Statement s = c.createStatement();
			ResultSet r = s.executeQuery("SELECT * FROM Trainer");
			ArrayList<Integer> trainerIDs = new ArrayList<Integer>();

			while (r.next()) {
				trainerIDs.add(r.getInt(1));
			}

			// Check if trainer ID exists
			if (trainerIDs.contains(trainerID)) {
				r = s.executeQuery("SELECT * FROM Pokeball");
				ArrayList<String> pokeballs = new ArrayList<String>();

				while (r.next()) {
					pokeballs.add(r.getString(1));
				}

				// Check if Pokeball exists
				if (pokeballs.contains(pokeball)) {
					s.executeUpdate("DELETE FROM HasPokeball WHERE TrainerID = " + trainerID
							+ " AND PokeballName = '" + pokeball + "'");
				} else {
					System.out.println("ERROR: Pokeball does not exist");
				}
			} else {
				System.out.println("ERROR: Trainer does not exist");
			}

			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Finds the trainer with the specified ID.
	 * @param id The ID of the trainer.
	 * @return The row containing the trainer's information.
	 */
	public static ResultSet searchTrainerID(int id) {
		ResultSet r = null;

		try {
			Statement s = c.createStatement();
			r = s.executeQuery("SELECT * FROM Trainer WHERE ID = " + id);
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return r;
	}

	/**
	 * Finds all trainers with the specified name.
	 * @param name The name of the trainers.
	 * @return The rows containing the trainers' information.
	 */
	public static ResultSet searchTrainer(String name) {
		ResultSet r = null;

		try {
			Statement s = c.createStatement();	
			r = s.executeQuery("SELECT * FROM Trainer WHERE Name LIKE '%" + name + "%'");
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return r;
	}

	/**
	 * Finds the Pokemon with the specified ID.
	 * @param id The ID of the Pokemon.
	 * @return The row containing the Pokemon's information.
	 */
	public static ResultSet searchPokemonID(int id) {
		ResultSet r = null;

		try {
			Statement s = c.createStatement();
			r = s.executeQuery("SELECT * FROM Pokemon WHERE ID = " + id);
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return r;
	}

	/**
	 * Finds all Pokemon with the specified name.
	 * @param name The name of the Pokemon.
	 * @return The rows containing the Pokemon information.
	 */
	public static ResultSet searchPokemon(String name) {
		ResultSet r = null;

		try {
			Statement s = c.createStatement();
			r = s.executeQuery("SELECT * FROM Pokemon WHERE Name LIKE '%" + name + "%'");
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return r;
	}

	/**
	 * Finds all moves with the specified name.
	 * @param name The name of the move.
	 * @return The rows containing the moves' information.
	 */
	public static ResultSet searchMove(String name) {
		ResultSet r = null;

		try {
			Statement s = c.createStatement();
			r = s.executeQuery("SELECT * FROM Move WHERE Name LIKE '%" + name + "%'");
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return r;
	}

	/**
	 * Finds all items with the specified name.
	 * @param name The name of the item.
	 * @return The rows containing the items' information.
	 */
	public static ResultSet searchItem(String name) {
		ResultSet r = null;

		try {
			Statement s = c.createStatement();
			r = s.executeQuery("SELECT Name FROM TMHM WHERE Name LIKE '%" + name + "%'"
					+ " UNION SELECT Name FROM Consumable WHERE Name LIKE '%" + name + "%'"
					+ " UNION SELECT Name FROM Pokeball WHERE Name LIKE '%" + name + "%'");
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return r;
	}

	/**
	 * Show all of the Pokemon owned by the trainer with the specified ID.
	 * @param id The ID of the trainer.
	 * @return The rows containing the Pokemon owned by the specified trainer.
	 */
	public static ResultSet getTrainerPokemon(int id) {
		ResultSet r = null;

		try {
			Statement s = c.createStatement();
			r = s.executeQuery("SELECT * FROM Trainer");
			ArrayList<Integer> trainerIDs = new ArrayList<Integer>();

			while (r.next()) {
				trainerIDs.add(r.getInt(1));
			}

			// Check if trainer exists
			if (trainerIDs.contains(id)) {
				r = s.executeQuery("SELECT * FROM Catches WHERE TrainerID = " + id);
			} else {
				System.out.println("ERROR: Trainer does not exist");
			}

			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return r;
	}

	/**
	 * Show all of the items owned by the trainer with the specified ID.
	 * @param id The ID of the trainer.
	 * @return The rows containing the items owned by the specified trainer.
	 */
	public static ResultSet getTrainerItems(int id) {
		ResultSet r = null;

		try {
			Statement s = c.createStatement();
			r = s.executeQuery("SELECT * FROM Trainer");
			ArrayList<Integer> trainerIDs = new ArrayList<Integer>();

			while (r.next()) {
				trainerIDs.add(r.getInt(1));
			}

			// Check if trainer exists
			if (trainerIDs.contains(id)) {
				r = s.executeQuery("SELECT * FROM HasTMHM WHERE TrainerID = " + id
						+ " UNION SELECT * FROM HasConsumable WHERE TrainerID = " + id
						+ " UNION SELECT * FROM HasPokeball WHERE TrainerID = " + id);
			} else {
				System.out.println("ERROR: Trainer does not exist");
			}

			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return r;
	}

	/**
	 * Update the name of the trainer with the specified ID.
	 * @param id The ID of the trainer.
	 * @param name The new name of the trainer.
	 */
	public static void updateTrainerName(int id, String name) {
		try {
			Statement s = c.createStatement();
			ResultSet r = s.executeQuery("SELECT * FROM Trainer");
			ArrayList<Integer> ids = new ArrayList<Integer>();

			while (r.next()) {
				ids.add(r.getInt(1));
			}

			// Check if trainer exists
			if (ids.contains(id)) {
				s.executeQuery("UPDATE Trainer SET Name = '" + name + "' WHERE ID = " + id);
			} else {
				System.out.println("ERROR: Trainer does not exist");
			}

			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Find the heaviest Pokemon.
	 * @return The row containing the Pokemon's information.
	 */
	public static ResultSet findHeaviestPokemon() {
		ResultSet r = null;

		try {
			Statement s = c.createStatement();
			r = s.executeQuery("SELECT * FROM Pokemon P WHERE P.weight"
					+ " = (SELECT MAX(P.weight) FROM Pokemon P)");
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return r;
	}

	/**
	 * Find the tallest Pokemon.
	 * @return The row containing the Pokemon's information.
	 */
	public static ResultSet findTallestPokemon() {
		ResultSet r = null;

		try {
			Statement s = c.createStatement();
			r = s.executeQuery("SELECT * FROM Pokemon P WHERE P.height"
					+ " = (SELECT MAX(P.height) FROM Pokemon P)");
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return r;
	}

	/**
	 * Find all the Pokemon shorter than the specified height.
	 * @param height The height cutoff.
	 * @return The rows containing the Pokemon information.
	 */
	public static ResultSet searchPokemonHeightLesser(int height) {
		ResultSet r = null;

		try {
			Statement s = c.createStatement();
			r = s.executeQuery("SELECT * FROM Pokemon WHERE Height < " + height);
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return r;
	}

	/**
	 * Find all the Pokemon taller than the specified height.
	 * @param height The height cutoff.
	 * @return The rows containing the Pokemon information.
	 */
	public static ResultSet searchPokemonHeightGreater(int height) {
		ResultSet r = null;

		try {
			Statement s = c.createStatement();
			r = s.executeQuery("SELECT * FROM Pokemon WHERE Height > " + height);
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return r;
	}

	/**
	 * Find all the Pokemon lighter than the specified weight.
	 * @param weight The weight cutoff.
	 * @return The rows containing the Pokemon information.
	 */
	public static ResultSet searchPokemonWeightLesser(int weight) {
		ResultSet r = null;

		try {
			Statement s = c.createStatement();
			r = s.executeQuery("SELECT * FROM Pokemon WHERE Weight < " + weight);
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return r;
	}

	/**
	 * Find all the Pokemon heavier than the specified weight.
	 * @param weight The weight cutoff.
	 * @return The rows containing the Pokemon information.
	 */
	public static ResultSet searchPokemonWeightGreater(int weight) {
		ResultSet r = null;

		try {
			Statement s = c.createStatement();
			r = s.executeQuery("SELECT * FROM Pokemon WHERE Weight > " + weight);
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return r;
	}

	/**
	 * Find all the Pokemon not owned by the specified trainer.
	 * @param id The ID of the trainer.
	 * @return The rows containing the Pokemon information.
	 */
	public static ResultSet findPokemonNotOwnedByTrainer(int id) {
		ResultSet r = null;

		try {
			Statement s = c.createStatement();
			r = s.executeQuery("SELECT ID FROM Pokemon MINUS SELECT PokemonID"
					+ " FROM Catches WHERE TrainerID = " + id);

			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return r;
	}

	/**
	 * Extend a string using whitespace up to the given length.
	 * @param s The string to be extended.
	 * @param length The length to extend to.
	 * @return
	 */
	public static String addWhitespace(String s, int length) {
		int stringLength = s.length();

		while (stringLength < length) {
			s += " ";
			stringLength++;
		}

		return s;
	}

	/**
	 * Generate a random number within the specified range.
	 * @param min The minimum value.
	 * @param max The maximum value.
	 * @return The randomly generated value.
	 */
	public static int random(int min, int max) {
		Random r = new Random();

		return r.nextInt((max - min) + 1) + min;
	}

	/**
	 * Connects to the database.
	 */
	public static void initializeConnection() {
		try {
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

			c = DriverManager.getConnection(
					"jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug",
					"ora_d6x8", "a65826083");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Closes the connection to the database.
	 */
	public static void closeConnection() {
		try {
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
