package com.app.sportsConnect.repository;

import com.app.sportsConnect.entity.FootballFixture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FootballFixtureRepository extends JpaRepository<FootballFixture, Long> {
}
