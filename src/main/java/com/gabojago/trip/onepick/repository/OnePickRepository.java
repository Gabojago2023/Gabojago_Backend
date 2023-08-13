package com.gabojago.trip.onepick.repository;

import com.gabojago.trip.onepick.domain.DistributedOnePick;
import com.gabojago.trip.onepick.domain.OnePick;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OnePickRepository extends JpaRepository<OnePick, Integer> {

    // 랜덤한 원픽 조회: 카테고리, 지역
    @Query(value = "SELECT o FROM OnePick o WHERE o.category = :category AND o.baseLocation = :baseLocation ORDER BY RAND() LIMIT 1", nativeQuery = true)
    OnePick findOnePickByCategoryAndBaseLocation(String category, String baseLocation);

    // 랜덤한 원픽 조회: 카테고리
    @Query(value = "SELECT o FROM OnePick o WHERE o.category = :category ORDER BY RAND() LIMIT 1", nativeQuery = true)
    OnePick findOnePickByCategory(String category);

    // 랜덤한 원픽 조회: 지역
    @Query(value = "SELECT o FROM OnePick o WHERE o.baseLocation = :baseLocation ORDER BY RAND() LIMIT 1", nativeQuery = true)
    OnePick findOnePickByBaseLocation(String baseLocation);

    // 랜덤한 원픽 조회
    @Query("SELECT o FROM OnePick o")
    OnePick findRandomOnePick();

    // user id로 모든 원픽 조회
    @Query("SELECT o FROM OnePick o WHERE o.user.id = :userId ORDER BY o.createdDate DESC")
    OnePick[] findAllOnePickByUserId(Integer userId);

    // 새로운 원픽 추가 (수정도 추가로 처리 -> 생성 시간 필드로 버저닝 필드를 대체)
    @Query(value = "INSERT INTO OnePick (description, place, user, category, baseLocation) VALUES (:description, :place, :user, :category, :baseLocation)", nativeQuery = true)
    void addOnePick(String description, Integer place, Integer user, Integer category,
            Integer baseLocation);

    // DistributedOnePick 새롭게 등록
    @Query(value = "INSERT INTO DistributedOnePick (onePick, user) VALUES (:onePick, :user)", nativeQuery = true)
    void addDistributedOnePick(Integer onePick, Integer user);

    // 유저가 해당 원픽일 이미 뽑았는지의 여부
    @Query("SELECT CASE WHEN COUNT(d) > 0 THEN true ELSE false END FROM DistributedOnePick d WHERE d.onePick.id = :onePickId AND d.user.id = :userId")
    boolean existsDistributedOnePickByOnePickIdAndUserId(Integer onePickId, Integer userId);

    // 유저가 뽑은 원픽 전체 조회
    @Query("SELECT d FROM DistributedOnePick d WHERE d.user.id = :userId ORDER BY d.createdAt DESC")
    DistributedOnePick[] findAllDistributedOnePickByUserId(Integer userId);


}
