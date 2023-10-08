package com.gabojago.trip.user.domain;

import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserVisitRepository extends JpaRepository<UserVisit, Integer> {

    List<UserVisit> findAllByUserId(Integer userId);

    @Query("SELECT uv FROM UserVisit uv WHERE uv.user.id = :userId AND uv.lastVisit = :today")
    UserVisit findVisitTodayByUserId(Integer userId, LocalDate today);

}
