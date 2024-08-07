package com.app.sportsConnect;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.app.sportsConnect.util.KnockoutTournament;

public class CricketApp {

    public static void createAndStoreFixtures(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter the number of teams: ");
        int numTeams = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        List<String> teamNames = new ArrayList<>();
        for (int i = 0; i < numTeams; i++) {
            System.out.print("Enter the name of team " + (i + 1) + ": ");
            teamNames.add(scanner.nextLine());
        }

        KnockoutTournament tournament = new KnockoutTournament(numTeams, teamNames, "cricket");
        tournament.createAndStoreFixtures(conn);

        System.out.println("Cricket fixtures created and stored successfully!");
    }

    public static void fetchCricketMatches(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            String selectSQL = "SELECT matchId, team1, team2, matchDate, innings1Score, innings2Score, matchType FROM cricketFixtures";
            try (ResultSet rs = stmt.executeQuery(selectSQL)) {
                while (rs.next()) {
                    int matchId = rs.getInt("matchId");
                    String team1 = rs.getString("team1");
                    String team2 = rs.getString("team2");
                    String matchDate = rs.getString("matchDate");
                    String innings1Score = rs.getString("innings1Score");
                    String innings2Score = rs.getString("innings2Score");
                    String matchType = rs.getString("matchType");

                    System.out.println("Match ID: " + matchId);
                    System.out.println("Teams: " + team1 + " vs " + team2);
                    System.out.println("Match Date: " + matchDate);
                    System.out.println("Innings 1 Score: " + innings1Score);
                    System.out.println("Innings 2 Score: " + innings2Score);
                    System.out.println("Match Type: " + matchType);
                    System.out.println("-------------------------------");
                }
            }
        }
    }

    public static void updateCricketMatchDetails(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter matchId to update: ");
        int matchId = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        System.out.print("Enter new matchDate (YYYY-MM-DD): ");
        String newMatchDate = scanner.nextLine();

        System.out.print("Enter new innings1Score: ");
        String newInnings1Score = scanner.nextLine();

        System.out.print("Enter new innings2Score: ");
        String newInnings2Score = scanner.nextLine();

        String updateSQL = "UPDATE cricketFixtures SET matchDate = ?, innings1Score = ?, innings2Score = ? WHERE matchId = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
            pstmt.setString(1, newMatchDate);
            pstmt.setString(2, newInnings1Score);
            pstmt.setString(3, newInnings2Score);
            pstmt.setInt(4, matchId);
            pstmt.executeUpdate();

            System.out.println("Cricket match details updated successfully!");
        }
    }
}
