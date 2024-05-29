package com.sportsConnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class MenuDrivenJdbcExample {

    // Database URL, Username, and Password
    static final String DB_URL = "jdbc:mysql://localhost:3306/sportsConnect";
    static final String USER = "root";
    static final int PASS_INT = 7730;  // Example integer password
    static final String PASS = String.valueOf(PASS_INT);  // Convert integer password to string

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        PreparedStatement pstmt = null;
        Scanner scanner = new Scanner(System.in);

        try {
            // Register JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            while (true) {
                // Display menu
                System.out.println("Menu:");
                System.out.println("1. Insert a match");
                System.out.println("2. Fetch all matches");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();  // Consume newline

                switch (choice) {
                    case 1:
                        // Insert values into the table
                        System.out.print("Enter matchId: ");
                        int matchId = scanner.nextInt();
                        scanner.nextLine();  // Consume newline

                        System.out.print("Enter team1: ");
                        String team1 = scanner.nextLine();

                        System.out.print("Enter team2: ");
                        String team2 = scanner.nextLine();

                        System.out.print("Enter matchDate (YYYY-MM-DD): ");
                        String matchDate = scanner.nextLine();

                        System.out.print("Enter score: ");
                        String score = scanner.nextLine();

                        System.out.print("Enter matchType: ");
                        String matchType = scanner.nextLine();

                        String insertSQL = "INSERT INTO footballMatches(matchId, team1, team2, matchDate, score, MatchType) VALUES (?, ?, ?, ?, ?, ?)";
                        pstmt = conn.prepareStatement(insertSQL);
                        pstmt.setInt(1, matchId);
                        pstmt.setString(2, team1);
                        pstmt.setString(3, team2);
                        pstmt.setString(4, matchDate);
                        pstmt.setString(5, score);
                        pstmt.setString(6, matchType);
                        pstmt.executeUpdate();

                        System.out.println("Match inserted successfully!");
                        break;

                    case 2:
                        // Fetch values from the table
                        stmt = conn.createStatement();
                        String selectSQL = "SELECT matchId, team1, team2, matchDate, score, MatchType FROM footballMatches";
                        ResultSet rs = stmt.executeQuery(selectSQL);

                        // Extract data from result set
                        while(rs.next()){
                            int mId  = rs.getInt("matchId");
                            String t1 = rs.getString("team1");
                            String t2 = rs.getString("team2");
                            String mDate = rs.getString("matchDate");
                            String mScore = rs.getString("score");
                            String mType = rs.getString("MatchType");

                            // Display values
                            System.out.print("Match ID: " + mId + "\n");
                            System.out.print( t1+ " vs "+ t2 +"\n");
                            System.out.print(" Score: " + mScore + "\n");
                            System.out.print(" Match Date: " + mDate +"\n");
                            System.out.println("Match Type: " + mType);
                        }
                        rs.close();
                        break;

                    case 3:
                        // Exit the program
                        System.out.println("Goodbye!");
                        return;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }

        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(stmt != null) stmt.close();
                if(pstmt != null) pstmt.close();
                if(conn != null) conn.close();
                scanner.close();
            } catch(SQLException se) {
                se.printStackTrace();
            }
        }
    }
}
