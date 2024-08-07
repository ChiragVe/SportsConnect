package com.app.sportsConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.app.sportsConnect.util.KnockoutTournament;

public class FootballApp {

    public static void createAndStoreFixtures(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter the number of teams: ");
        int numTeams = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        List<String> teamNames = new ArrayList<>();
        for (int i = 0; i < numTeams; i++) {
            System.out.print("Enter the name of team " + (i + 1) + ": ");
            teamNames.add(scanner.nextLine());
        }

        KnockoutTournament tournament = new KnockoutTournament(numTeams, teamNames, "football");
        tournament.createAndStoreFixtures(conn);

        System.out.println("Football fixtures created and stored successfully!");
    }

    public static void fetchFootballMatches(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            String selectSQL = "SELECT matchId, team1, team2, matchDate, score, matchType FROM footballFixtures";
            try (ResultSet rs = stmt.executeQuery(selectSQL)) {
                while (rs.next()) {
                    int matchId = rs.getInt("matchId");
                    String team1 = rs.getString("team1");
                    String team2 = rs.getString("team2");
                    String matchDate = rs.getString("matchDate");
                    String score = rs.getString("score");
                    String matchType = rs.getString("matchType");

                    System.out.println("Match ID: " + matchId);
                    System.out.println("Teams: " + team1 + " vs " + team2);
                    System.out.println("Match Date: " + matchDate);
                    System.out.println("Score: " + score);
                    System.out.println("Match Type: " + matchType);
                    System.out.println("-------------------------------");
                }
            }
        }
    }

    public static void updateFootballMatchDetails(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter matchId to update: ");
        int matchId = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        System.out.print("Enter new matchDate (YYYY-MM-DD): ");
        String newMatchDate = scanner.nextLine();

        System.out.print("Enter new score: ");
        String newScore = scanner.nextLine();

        String updateSQL = "UPDATE footballFixtures SET matchDate = ?, score = ? WHERE matchId = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
            pstmt.setString(1, newMatchDate);
            pstmt.setString(2, newScore);
            pstmt.setInt(3, matchId);
            pstmt.executeUpdate();

            System.out.println("Football match details updated successfully!");
        }
    }
}
