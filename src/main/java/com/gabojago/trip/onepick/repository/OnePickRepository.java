package com.gabojago.trip.onepick.repository;

import com.gabojago.trip.onepick.domain.DistributedOnePick;
import com.gabojago.trip.onepick.domain.OnePick;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OnePickRepository extends JpaRepository<OnePick, Integer> {
    List<OnePick> findAllByUserId(Integer userId);

    // 모든 원픽 조회
    @Query("SELECT o FROM OnePick o")
    List<OnePick> findAllOnePick();

    // get DistributedOnePicks by user id
    @Query("SELECT d FROM DistributedOnePick d WHERE d.user.id = :userId")
    List<DistributedOnePick> getDistributedOnePicksByUserId(Integer userId);

}
