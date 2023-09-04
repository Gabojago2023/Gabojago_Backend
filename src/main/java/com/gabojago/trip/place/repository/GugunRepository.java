package com.gabojago.trip.place.repository;

import com.gabojago.trip.place.domain.Gugun;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GugunRepository extends JpaRepository<Gugun, Integer> {

    @Query("SELECT g.gugunCode, g.gugunName FROM Gugun g WHERE g.sido.sidoCode = :sidoId")
    List<Object[]> findGugunByIdSidoOrderById(@Param("sidoId") Integer sidoId);
}
