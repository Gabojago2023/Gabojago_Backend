package com.gabojago.trip.onepick.repository;

import com.gabojago.trip.onepick.domain.DistributedOnePick;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DistributedOnePickRepository extends JpaRepository<DistributedOnePick, Integer> {
    List<DistributedOnePick> findAllByUserId(Integer userId);
}
