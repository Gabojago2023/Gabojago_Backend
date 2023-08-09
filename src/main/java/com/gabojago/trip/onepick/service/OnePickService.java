package com.gabojago.trip.onepick.service;

import com.gabojago.trip.onepick.domain.DistributedOnePick;
import com.gabojago.trip.onepick.domain.OnePick;

public interface OnePickService {

    // OnePick 하나 뽑기
    OnePick getOnePick(String category, String baseLocation, Integer userId);

    // 나의 모든 DistributedOnePick 조회
    DistributedOnePick[] getAllDistributedOnePick(Integer userId);

    // 내가 등록한 원픽 장소 조회
    OnePick[] getAllOnePick(Integer userId);

    // 원픽 장소 등록
    void addOnePick(String description, Integer place, Integer user, Integer category, Integer baseLocation);

    // 원픽 장소 수정
    void modifyOnePick(Integer onePickId, String description, Integer place, Integer user, Integer category, Integer baseLocation);

}
