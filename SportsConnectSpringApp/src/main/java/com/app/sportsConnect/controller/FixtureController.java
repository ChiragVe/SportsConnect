package com.app.sportsConnect.controller;

import com.app.sportsConnect.entity.CricketFixture;
import com.app.sportsConnect.entity.FootballFixture;
import com.app.sportsConnect.service.FixtureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fixtures")
public class FixtureController {

    @Autowired
    private FixtureService fixtureService;

    // Create a new cricket fixture
    @PostMapping("/cricket")
    public ResponseEntity<CricketFixture> createCricketFixture(@RequestBody CricketFixture fixture) {
        CricketFixture savedFixture = fixtureService.saveCricketFixture(fixture);
        return ResponseEntity.ok(savedFixture);
    }

    // Get all cricket fixtures
    @GetMapping("/cricket")
    public ResponseEntity<List<CricketFixture>> getAllCricketFixtures() {
        List<CricketFixture> fixtures = fixtureService.getAllCricketFixtures();
        return ResponseEntity.ok(fixtures);
    }

    // Update a specific cricket fixture
    @PutMapping("/cricket/{matchId}")
    public ResponseEntity<CricketFixture> updateCricketFixture(@PathVariable Long matchId, @RequestBody CricketFixture updatedFixture) {
        CricketFixture fixture = fixtureService.updateCricketFixture(matchId, updatedFixture);
        return fixture != null ? ResponseEntity.ok(fixture) : ResponseEntity.notFound().build();
    }

    // Create a new football fixture
    @PostMapping("/football")
    public ResponseEntity<FootballFixture> createFootballFixture(@RequestBody FootballFixture fixture) {
        FootballFixture savedFixture = fixtureService.saveFootballFixture(fixture);
        return ResponseEntity.ok(savedFixture);
    }

    // Get all football fixtures
    @GetMapping("/football")
    public ResponseEntity<List<FootballFixture>> getAllFootballFixtures() {
        List<FootballFixture> fixtures = fixtureService.getAllFootballFixtures();
        return ResponseEntity.ok(fixtures);
    }

    // Update a specific football fixture
    @PutMapping("/football/{matchId}")
    public ResponseEntity<FootballFixture> updateFootballFixture(@PathVariable Long matchId, @RequestBody FootballFixture updatedFixture) {
        FootballFixture fixture = fixtureService.updateFootballFixture(matchId, updatedFixture);
        return fixture != null ? ResponseEntity.ok(fixture) : ResponseEntity.notFound().build();
    }
}
