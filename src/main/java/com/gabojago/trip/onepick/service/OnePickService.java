package com.gabojago.trip.onepick.service;

import com.gabojago.trip.onepick.domain.DistributedOnePick;
import com.gabojago.trip.onepick.domain.OnePick;
import java.util.List;

public interface OnePickService {

    // 나의 모든 원픽 중 가장 최근 것만 조회
    List<OnePick> getAllValidOnePicksByUserId(Integer userId);
    // 나의 모든 원픽 조회
    List<OnePick> getAllOnePicksByUserId(Integer userId);

    // OnePick 하나 뽑기
    OnePick getOnePick(Integer category, Integer baseLocation, Integer userId);

    // 나의 모든 DistributedOnePick 조회
    List<DistributedOnePick> getAllDistributedOnePick(Integer userId);

    void addOnePick(OnePick onePick);

    // 내가 뽑은 원픽 장소 평가
    void rateDistributedOnePick(Integer distributedOnePickId, Double rate);

}
