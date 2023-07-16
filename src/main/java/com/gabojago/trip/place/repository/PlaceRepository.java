package com.gabojago.trip.place.repository;

import com.gabojago.trip.place.domain.Place;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Integer> {

    // id로 장소 정보 얻기
    Place findById(int id);

    // 가장 많이 스크랩된 3개 장소의 id 리스트 정보
    @Query("SELECT ps.placeId FROM PlaceScrap ps GROUP BY ps.placeId ORDER BY count(ps.placeId) DESC")
    List<Integer> findTop3ScrappedPlacesId();
}
