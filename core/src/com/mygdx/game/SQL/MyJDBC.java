package com.mygdx.game.SQL;

import java.sql.*;
import java.util.ArrayList;

public class MyJDBC {

    private static final String url = "jdbc:mysql://localhost:3306/caveflip";
    private static final String usernameSQL = "root";
    private static final String passwordSQL = "rootpassword";

    public static Statement JDBCConnection() {
        Statement statement = null;
        try {
            Connection connection = DriverManager.getConnection(url, usernameSQL, passwordSQL);


            statement = connection.createStatement();

            ResultSet resultset = statement.executeQuery("select * from player");

            while (resultset.next()) {
                System.out.println(resultset.getString("username"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statement;
    }

    public static String[] login(String username, String password) {

        String query = "SELECT * FROM player WHERE username = " + "'" + username + "'" + "AND password = " + "'" + password + "'";
        String usernameResult;
        String passwordResult;
        String[] loginResult = new String[0];

        try {
            Statement stmt = JDBCConnection();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                usernameResult = rs.getString("username");
                passwordResult = rs.getString("password");
                System.out.println("Username: " + usernameResult + " Password: " + passwordResult);
                loginResult = new String[]{usernameResult, passwordResult};
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();

        }

        return loginResult;
    }

    public static boolean verifyUsername(String username) {

        String query = "SELECT * FROM player WHERE username = " + "'" + username + "'";
        String usernameResult;
        boolean usernameExists = false;
        try {
            Statement stmt = JDBCConnection();
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                usernameResult = rs.getString("username");
                System.out.println("Username: " + usernameResult);
                usernameExists = true;
            }

            rs.close();
            stmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return usernameExists;
    }

    public static void register(String username, String password) {

        try {
            Connection conn = DriverManager.getConnection(url, usernameSQL, passwordSQL);

            // Create a prepared statement for the SQL INSERT statement
            String query = "INSERT INTO player (username, password) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);

            // Set the parameter values for the prepared statement
            stmt.setString(1, username);
            stmt.setString(2, password);

            // Execute the prepared statement to insert the new username and password
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new user was inserted successfully!");
            }

            // Close the prepared statement and connection
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        try {
            Connection conn = DriverManager.getConnection(url, usernameSQL, passwordSQL);

// Prepare the statement
            String sql = "INSERT INTO inventory (username, selectedItem, itemID) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username); // Replace "example_username" with the actual username
            stmt.setInt(2, 1);
            stmt.setInt(3, 1);

// Execute the statement
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new row has been inserted into the inventory table.");
            }

// Close the resources
            stmt.close();
            conn.close();
        } catch (Exception e) {

        }
    }

    public void updateCoins(String username, int coinsInGame) {
        try {
            // Create a statement
            Connection conn = DriverManager.getConnection(url, usernameSQL, passwordSQL);

            Statement stmt = conn.createStatement();

            // Select the number of coins from the player table
            String sql = "SELECT coins FROM player WHERE username = '" + username + "'";
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            int coins = rs.getInt("coins");

            // Add the number of coins in the game
            int newCoins = coins + coinsInGame;

            // Update the player table with the new value of coins
            sql = "UPDATE player SET coins = " + newCoins + " WHERE username = '" + username + "'";
            stmt.executeUpdate(sql);

            // Close the statement and result set
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void updateHighScore(String username, int score) {
        // create a connection to the database
        try (Connection connection = DriverManager.getConnection(url, usernameSQL, passwordSQL)) {

            // create a statement to select the highscore for the specified username
            String selectHighScore = "SELECT highscore FROM player WHERE username = ?";
            PreparedStatement selectStmt = connection.prepareStatement(selectHighScore);
            selectStmt.setString(1, username);

            // execute the select statement and get the highscore value
            ResultSet resultSet = selectStmt.executeQuery();
            int highScore = 0;
            if (resultSet.next()) {
                highScore = resultSet.getInt("highscore");
            }

            // compare the highscore to the game score and update the table if necessary
            if (score > highScore) {
                String updateHighScore = "UPDATE player SET highscore = ? WHERE username = ?";
                PreparedStatement updateStmt = connection.prepareStatement(updateHighScore);
                updateStmt.setInt(1, score);
                updateStmt.setString(2, username);
                updateStmt.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static int getSelectedItem(String username) {
        int selectedItem = 0;

        try {
            Connection conn = DriverManager.getConnection(url, usernameSQL, passwordSQL);
            String query = "SELECT selectedItem FROM inventory WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    selectedItem = rs.getInt("selectedItem");
                }

                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return selectedItem;
    }

    public void buyItem(String username, int itemID) {
        int coinsToSubtract = 0; // Initialize the coins to subtract
        ArrayList<Integer> itemIds = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url, usernameSQL, passwordSQL)) {
            String selectItemIDQuery = "SELECT itemid FROM inventory WHERE username = ?";
            String updateSelectedItemQuery = "UPDATE inventory SET selectedItem = ? WHERE username = ?";
            String selectItemValueQuery = "SELECT value FROM item WHERE itemID = ?";
            String selectCoinsQuery = "SELECT coins FROM player WHERE username = ?";
            String updateCoinsQuery = "UPDATE player SET coins = ? WHERE username = ?";
            String insertInventoryQuery = "INSERT INTO inventory (itemId, username) VALUES (?, ?)";

            try (PreparedStatement selectItemIDStmt = conn.prepareStatement(selectItemIDQuery);
                 PreparedStatement updateSelectedItemStmt = conn.prepareStatement(updateSelectedItemQuery);
                 PreparedStatement selectItemValueStmt = conn.prepareStatement(selectItemValueQuery);
                 PreparedStatement selectCoinsStmt = conn.prepareStatement(selectCoinsQuery);
                 PreparedStatement updateCoinsStmt = conn.prepareStatement(updateCoinsQuery);
                 PreparedStatement insertInventoryStmt = conn.prepareStatement(insertInventoryQuery)) {

                selectItemIDStmt.setString(1, username);
                ResultSet itemIDResult = selectItemIDStmt.executeQuery();
                while (itemIDResult.next()) {
                    int itemIDResultInt = itemIDResult.getInt("itemid");
                    itemIds.add(itemIDResultInt);
                }

                if (itemIds.contains(itemID)) {
                    updateSelectedItemStmt.setInt(1, itemID);
                    updateSelectedItemStmt.setString(2, username);
                    updateSelectedItemStmt.executeUpdate();
                    System.out.println("Selected item updated in the inventory table");
                } else {
                    selectItemValueStmt.setInt(1, itemID);
                    ResultSet itemValueResult = selectItemValueStmt.executeQuery();
                    if (itemValueResult.next()) {
                        int itemValue = itemValueResult.getInt("value");
                        System.out.println("Item value: " + itemValue);

                        selectCoinsStmt.setString(1, username);
                        ResultSet coinsResult = selectCoinsStmt.executeQuery();
                        if (coinsResult.next()) {
                            int coins = coinsResult.getInt("coins");
                            coinsToSubtract = itemValue;
                            System.out.println("Coins before subtract: " + coins);
                            if (coinsToSubtract > coins) {
                                System.out.println("You do not have enough money!");
                            } else {
                                coins -= coinsToSubtract;
                                System.out.println("Coins after subtract: " + coins);

                                updateCoinsStmt.setInt(1, coins);
                                updateCoinsStmt.setString(2, username);
                                updateCoinsStmt.executeUpdate();
                                System.out.println("Coins updated in the player table");

                                insertInventoryStmt.setInt(1, itemID);
                                insertInventoryStmt.setString(2, username);
                                insertInventoryStmt.executeUpdate();
                                System.out.println("Data inserted into inventory table.");

                                updateSelectedItemStmt.setInt(1, itemID);
                                updateSelectedItemStmt.setString(2, username);
                                updateSelectedItemStmt.executeUpdate();
                                System.out.println("Selected item updated");
                            }
                        } else {
                            System.out.println("Item with ID " + itemID + " not found");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
