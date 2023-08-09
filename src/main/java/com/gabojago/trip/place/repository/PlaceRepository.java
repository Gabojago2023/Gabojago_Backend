package com.gabojago.trip.place.repository;

import com.gabojago.trip.place.domain.Place;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Integer> {

    // 가장 많이 스크랩된 3개 장소의 id 리스트 정보
    @Query("SELECT ps.place.id FROM PlaceScrap ps GROUP BY ps.place.id ORDER BY count(ps.place.id) DESC")
    List<Integer> findTop3ScrappedPlacesId(Pageable pageable);

    @Query("SELECT p.id, p.name, p.longitude, p.latitude, p.address, p.category, p.imgUrl, p.imgUrl2, p.sido.sidoCode, p.gugun.gugunCode, p.overview, " +
            "CASE WHEN ps.user.id IS NOT NULL THEN 1 ELSE 0 END AS isBookmarked " +
            "FROM Place p " +
            "LEFT JOIN PlaceScrap ps ON p.id = ps.place.id AND ps.user.id = :userId " +
            "WHERE p.sido.sidoCode = :sidoCode " +
            "AND p.gugun.gugunCode = :gugunCode " +
            "AND p.name LIKE CONCAT('%', :keyword, '%') " +
            "ORDER BY CASE WHEN ps.user.id IS NOT NULL THEN 1 ELSE 0 END DESC, p.id")
    List<Object[]> findPlacesByFilter(@Param("userId") Integer userId, @Param("sidoCode") Integer sidoCode,
            @Param("gugunCode") Integer gugunCode, @Param("keyword") String keyword);
}
