package com.app.sportsConnect.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class CricketFixture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matchId;
    
    private String team1;
    private String team2;
    private Date matchDate;
    private String innings1Score;
    private String innings2Score;
    private String matchType;
	public Long getMatchId() {
		return matchId;
	}
	public void setMatchId(Long matchId) {
		this.matchId = matchId;
	}
	public String getTeam1() {
		return team1;
	}
	public void setTeam1(String team1) {
		this.team1 = team1;
	}
	public String getTeam2() {
		return team2;
	}
	public void setTeam2(String team2) {
		this.team2 = team2;
	}
	public Date getMatchDate() {
		return matchDate;
	}
	public void setMatchDate(Date matchDate) {
		this.matchDate = matchDate;
	}
	public String getInnings1Score() {
		return innings1Score;
	}
	public void setInnings1Score(String innings1Score) {
		this.innings1Score = innings1Score;
	}
	public String getInnings2Score() {
		return innings2Score;
	}
	public void setInnings2Score(String innings2Score) {
		this.innings2Score = innings2Score;
	}
	public String getMatchType() {
		return matchType;
	}
	public void setMatchType(String matchType) {
		this.matchType = matchType;
	}

    
    // Getters and setters
}
