package com.gabojago.trip.place.repository;

import com.gabojago.trip.place.domain.Place;
import com.gabojago.trip.place.dto.response.RandomImageResponseDto;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Integer> {

    // 가장 많이 스크랩된 3개 장소의 id 리스트 정보
    @Query("SELECT ps.place.id " + "FROM PlaceScrap ps " + "GROUP BY ps.place.id "
            + "ORDER BY count(ps.place.id) DESC")
    List<Integer> findTop3ScrappedPlacesId(Pageable pageable);

    @Query("SELECT p.id, p.name, p.longitude, p.latitude, p.address, p.category, p.imgUrl, p.imgUrl2, p.sido.sidoCode, p.gugun.gugunCode, p.overview, "
            + "CASE WHEN ps.user.id IS NOT NULL THEN 1 ELSE 0 END AS isBookmarked "
            + "FROM Place p "
            + "LEFT JOIN PlaceScrap ps ON p.id = ps.place.id AND ps.user.id = :userId "
            + "WHERE p.address LIKE CONCAT('%', :location, '%') "
            + "ORDER BY CASE WHEN ps.user.id IS NOT NULL THEN 1 ELSE 0 END DESC, p.id")
    List<Object[]> findPlacesByLocation(@Param("location") String location,
            @Param("userId") Integer userId, Pageable pageable);

    @Query("SELECT p.id, p.name, p.longitude, p.latitude, p.address, p.category, p.imgUrl, p.imgUrl2, p.sido.sidoCode, p.gugun.gugunCode, p.overview, "
            + "CASE WHEN ps.user.id IS NOT NULL THEN 1 ELSE 0 END AS isBookmarked "
            + "FROM Place p "
            + "LEFT JOIN PlaceScrap ps ON p.id = ps.place.id AND ps.user.id = :userId "
            + "WHERE p.sido.sidoCode = :sidoCode " + "AND p.gugun.gugunCode = :gugunCode "
            + "AND p.name LIKE CONCAT('%', :keyword, '%') "
            + "ORDER BY CASE WHEN ps.user.id IS NOT NULL THEN 1 ELSE 0 END DESC, p.id")
    List<Object[]> findPlacesByFilter(@Param("userId") Integer userId,
            @Param("sidoCode") Integer sidoCode, @Param("gugunCode") Integer gugunCode,
            @Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT p.id, p.name, p.longitude, p.latitude, p.address, p.category, p.imgUrl, p.imgUrl2, p.sido.sidoCode, p.gugun.gugunCode, p.overview, ps.id, "
            + "CASE WHEN ps.user.id IS NOT NULL THEN 1 ELSE 0 END AS isBookmarked "
            + "FROM Place p " + "JOIN PlaceScrap ps ON p.id = ps.place.id "
            + "WHERE ps.user.id = :userId "
            + "ORDER BY ps.id DESC")
    List<Object[]> findBookmarkedPlacesByUserId(@Param("userId") Integer userId, Pageable pageable);

    @Query("SELECT p.id, p.name, p.longitude, p.latitude, p.address, p.category, p.imgUrl, p.imgUrl2, p.sido.sidoCode, p.gugun.gugunCode, p.overview, ps.id, "
            + "CASE WHEN ps.user.id IS NOT NULL THEN 1 ELSE 0 END AS isBookmarked "
            + "FROM Place p " + "JOIN PlaceScrap ps ON p.id = ps.place.id "
            + "WHERE ps.user.id = :userId "
            + "AND ps.id < :cursor "
            + "ORDER BY ps.id DESC")
    List<Object[]> findNextBookmarkedPlacesByUserId(@Param("userId") Integer userId,
            @Param("cursor") Integer cursor, Pageable pageable);

    @Query("SELECT p.id, p.name, p.longitude, p.latitude, p.address, p.category, p.imgUrl, p.imgUrl2, "
            + "p.sido.sidoCode, p.gugun.gugunCode, p.overview, "
            + "AVG(c.starRating) AS avgRating, "
            + "COUNT(c.place.id) AS commentCount "
            + "FROM Place p "
            + "LEFT JOIN Comment c ON p.id = c.place.id WHERE p.id = :placeId")
    Object[] findPlaceWithAvgRatingAndCommentCount(@Param("placeId") Integer placeId);

    @Query(value = "SELECT new com.gabojago.trip.place.dto.response.RandomImageResponseDto(p.imgUrl) FROM Place p WHERE p.imgUrl <> '' ORDER BY FUNCTION('RAND')")
    List<RandomImageResponseDto> findRandomImages(Pageable pageable);
}
