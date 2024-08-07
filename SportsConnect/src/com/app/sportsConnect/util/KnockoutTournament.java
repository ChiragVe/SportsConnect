package com.app.sportsConnect.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class KnockoutTournament {
    private int numTeams;
    private List<String> teamNames;
    private String sportType;

    public KnockoutTournament(int numTeams, List<String> teamNames, String sportType) {
        this.numTeams = numTeams;
        this.teamNames = teamNames;
        this.sportType = sportType;
    }

    public void createAndStoreFixtures(Connection conn) throws SQLException {
        // If the number of teams is odd, add a "bye"
        if (teamNames.size() % 2 != 0) {
            teamNames.add("Bye");
        }

        String insertSQL = "INSERT INTO " + sportType + "Fixtures (team1, team2, matchDate, matchType) VALUES (?, ?, ?, ?)";
        
        List<String> fixtures = generateFixtures(teamNames);

        for (String fixture : fixtures) {
            String[] teams = fixture.split("-");
            try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
                pstmt.setString(1, teams[0]);
                pstmt.setString(2, teams[1]);
                pstmt.setString(3, null); // Placeholder for matchDate
                pstmt.setString(4, "Knockout"); // Example matchType
                pstmt.executeUpdate();
            }
        }
    }

    private List<String> generateFixtures(List<String> teams) {
        List<String> fixtures = new ArrayList<>();
        for (int i = 0; i < teams.size(); i += 2) {
            if (i + 1 < teams.size()) {
                fixtures.add(teams.get(i) + "-" + teams.get(i + 1));
            }
        }
        return fixtures;
    }
}
