package com.app.sportsConnect.service;

import com.app.sportsConnect.entity.CricketFixture;
import com.app.sportsConnect.entity.FootballFixture;
import com.app.sportsConnect.repository.CricketFixtureRepository;
import com.app.sportsConnect.repository.FootballFixtureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FixtureService {

    @Autowired
    private CricketFixtureRepository cricketFixtureRepository;

    @Autowired
    private FootballFixtureRepository footballFixtureRepository;

    public CricketFixture saveCricketFixture(CricketFixture fixture) {
        return cricketFixtureRepository.save(fixture);
    }

    public List<CricketFixture> getAllCricketFixtures() {
        return cricketFixtureRepository.findAll();
    }

    public FootballFixture saveFootballFixture(FootballFixture fixture) {
        return footballFixtureRepository.save(fixture);
    }

    public List<FootballFixture> getAllFootballFixtures() {
        return footballFixtureRepository.findAll();
    }

    public CricketFixture updateCricketFixture(Long matchId, CricketFixture updatedFixture) {
        if (cricketFixtureRepository.existsById(matchId)) {
            updatedFixture.setMatchId(matchId);
            return cricketFixtureRepository.save(updatedFixture);
        }
        return null;
    }

    public FootballFixture updateFootballFixture(Long matchId, FootballFixture updatedFixture) {
        if (footballFixtureRepository.existsById(matchId)) {
            updatedFixture.setMatchId(matchId);
            return footballFixtureRepository.save(updatedFixture);
        }
        return null;
    }
}
