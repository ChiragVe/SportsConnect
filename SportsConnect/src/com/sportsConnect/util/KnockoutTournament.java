package com.sportsConnect.util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class KnockoutTournament {

    private List<String> teams;

    public KnockoutTournament(int numTeams, List<String> teamNames) {
        if (teamNames.size() != numTeams) {
            throw new IllegalArgumentException("Number of team names provided does not match the number of teams.");
        }
        this.teams = new ArrayList<>(teamNames);
    }

    public void createAndStoreFixtures(Connection conn) throws SQLException {
        List<String> currentRound = new ArrayList<>(teams);
        Random rand = new Random();
        int matchId = 1;

        System.out.println("Storing Single Round Fixtures:");
        Collections.shuffle(currentRound);

        if (currentRound.size() % 2 != 0) {
            int byeIndex = rand.nextInt(currentRound.size());
            System.out.println(currentRound.get(byeIndex) + " gets a bye");
            String teamWithBye = currentRound.remove(byeIndex);
            currentRound.add(teamWithBye); // Add the team with bye to the end of the list for the next round
        }

        for (int i = 0; i < currentRound.size() - 1; i += 2) {
            String team1 = currentRound.get(i);
            String team2 = currentRound.get(i + 1);

            System.out.println(team1 + " vs " + team2);

            // Insert match into the database
            String insertSQL = "INSERT INTO footballMatches(matchId, team1, team2, matchDate, score, MatchType) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
                pstmt.setInt(1, matchId++);
                pstmt.setString(2, team1);
                pstmt.setString(3, team2);
                pstmt.setString(4, null);  // matchDate will be null initially
                pstmt.setString(5, null);  // score will be null initially
                pstmt.setString(6, "Knockout");
                pstmt.executeUpdate();
            }
        }
    }
}
