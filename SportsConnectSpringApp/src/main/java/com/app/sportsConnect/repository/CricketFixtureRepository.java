package com.app.sportsConnect.repository;

import com.app.sportsConnect.entity.CricketFixture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CricketFixtureRepository extends JpaRepository<CricketFixture, Long> {
}
