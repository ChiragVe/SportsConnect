package com.app.sportsConnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.app.sportsConnect.util.KnockoutTournament;

public class MenuDrivenJdbcExample {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/sportsconnect?useSSL=false&serverTimezone=UTC";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "1234";

    public static void main(String[] args) {
        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        Scanner scanner = new Scanner(System.in);
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            while (true) {
                System.out.println("1. Insert Football Fixture");
                System.out.println("2. Insert Cricket Fixture");
                System.out.println("3. Retrieve Football Fixtures");
                System.out.println("4. Retrieve Cricket Fixtures");
                System.out.println("5. Update Football Fixture");
                System.out.println("6. Update Cricket Fixture");
                System.out.println("7. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        insertFootballFixture(conn, scanner);
                        break;
                    case 2:
                        insertCricketFixture(conn, scanner);
                        break;
                    case 3:
                        retrieveFootballFixtures(conn);
                        break;
                    case 4:
                        retrieveCricketFixtures(conn);
                        break;
                    case 5:
                        updateFootballFixture(conn, scanner);
                        break;
                    case 6:
                        updateCricketFixture(conn, scanner);
                        break;
                    case 7:
                        System.out.println("Exiting...");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertFootballFixture(Connection conn, Scanner scanner) throws SQLException {
        List<String> teams = getTeamsFromUser(scanner);
        KnockoutTournament tournament = new KnockoutTournament(teams.size(), teams, "football");
        tournament.createAndStoreFixtures(conn);
    }

    private static void insertCricketFixture(Connection conn, Scanner scanner) throws SQLException {
        List<String> teams = getTeamsFromUser(scanner);
        KnockoutTournament tournament = new KnockoutTournament(teams.size(), teams, "cricket");
        tournament.createAndStoreFixtures(conn);
    }

    private static List<String> getTeamsFromUser(Scanner scanner) {
        System.out.print("Enter number of teams: ");
        int numTeams = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        List<String> teams = new ArrayList<>();
        for (int i = 0; i < numTeams; i++) {
            System.out.print("Enter team " + (i + 1) + " name: ");
            teams.add(scanner.nextLine());
        }
        return teams;
    }

    private static void retrieveFootballFixtures(Connection conn) throws SQLException {
        String query = "SELECT * FROM footballFixtures";
        try (var stmt = conn.createStatement();
             var rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                System.out.println("matchId: " + rs.getInt("matchId"));
                System.out.println("team1: " + rs.getString("team1"));
                System.out.println("team2: " + rs.getString("team2"));
                System.out.println("matchDate: " + rs.getDate("matchDate"));
                System.out.println("score: " + rs.getString("score"));
                System.out.println("matchType: " + rs.getString("matchType"));
                System.out.println("-------------------------------");
            }
        }
    }

    private static void retrieveCricketFixtures(Connection conn) throws SQLException {
        String query = "SELECT * FROM cricketFixtures";
        try (var stmt = conn.createStatement();
             var rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                System.out.println("matchId: " + rs.getInt("matchId"));
                System.out.println("team1: " + rs.getString("team1"));
                System.out.println("team2: " + rs.getString("team2"));
                System.out.println("matchDate: " + rs.getDate("matchDate"));
                System.out.println("innings1Score: " + rs.getString("innings1Score"));
                System.out.println("innings2Score: " + rs.getString("innings2Score"));
                System.out.println("matchType: " + rs.getString("matchType"));
                System.out.println("-------------------------------");
            }
        }
    }

    private static void updateFootballFixture(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter matchId to update: ");
        int matchId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter new match date (YYYY-MM-DD): ");
        String matchDate = scanner.nextLine();
        System.out.print("Enter new score: ");
        String score = scanner.nextLine();

        String updateSQL = "UPDATE footballFixtures SET matchDate = ?, score = ? WHERE matchId = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
            pstmt.setDate(1, java.sql.Date.valueOf(matchDate));
            pstmt.setString(2, score);
            pstmt.setInt(3, matchId);
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Football fixture updated successfully!");
            } else {
                System.out.println("Failed to update football fixture.");
            }
        }
    }

    private static void updateCricketFixture(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter matchId to update: ");
        int matchId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter new match date (YYYY-MM-DD): ");
        String matchDate = scanner.nextLine();
        System.out.print("Enter new innings1 score: ");
        String innings1Score = scanner.nextLine();
        System.out.print("Enter new innings2 score: ");
        String innings2Score = scanner.nextLine();

        String updateSQL = "UPDATE cricketFixtures SET matchDate = ?, innings1Score = ?, innings2Score = ? WHERE matchId = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
            pstmt.setDate(1, java.sql.Date.valueOf(matchDate));
            pstmt.setString(2, innings1Score);
            pstmt.setString(3, innings2Score);
            pstmt.setInt(4, matchId);
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Cricket fixture updated successfully!");
            } else {
                System.out.println("Failed to update cricket fixture.");
            }
        }
    }
}
