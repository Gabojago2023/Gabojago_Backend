package com.gabojago.trip.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserVisitRepository extends JpaRepository<UserVisit, Integer> {

    List<UserVisit> findAllByUserId(Integer userId);

}
