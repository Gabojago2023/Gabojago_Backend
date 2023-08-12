package com.gabojago.trip.onepick.service;

import com.gabojago.trip.onepick.domain.DistributedOnePick;
import com.gabojago.trip.onepick.domain.OnePick;
import com.gabojago.trip.onepick.repository.OnePickRepository;
import org.springframework.stereotype.Service;

@Service
public class OnePickServiceImpl implements OnePickService {

    private final OnePickRepository onePickRepository;

    public OnePickServiceImpl(OnePickRepository onePickRepository) {
        this.onePickRepository = onePickRepository;
    }

    // OnePick 하나 뽑기
    @Override
    public OnePick getOnePick(String category, String baseLocation, Integer userId) {
        OnePick onePick;
        do {
            if (category != null && baseLocation != null) {
                onePick = onePickRepository.findOnePickByCategoryAndBaseLocation(category, baseLocation);
            } else if (category != null) {
                onePick = onePickRepository.findOnePickByCategory(category);
            } else if (baseLocation != null) {
                onePick = onePickRepository.findOnePickByBaseLocation(baseLocation);
            } else {
                onePick = onePickRepository.findRandomOnePick();
            }
        } while (onePickRepository.existsDistributedOnePickByOnePickIdAndUserId(onePick.getId(), userId));

        return onePick;
    }

    // 나의 모든 DistributedOnePick 조회
    @Override
    public DistributedOnePick[] getAllDistributedOnePick(Integer userId) {
        return onePickRepository.findAllDistributedOnePickByUserId(userId);
    }

    // 내가 등록한 원픽 장소 조회
    @Override
    public OnePick[] getAllOnePick(Integer userId) {
        return onePickRepository.findAllOnePickByUserId(userId);
    }

    // 원픽 장소 등록
    @Override
    public void addOnePick(String description, Integer place, Integer user, Integer category,
            Integer baseLocation) {
        onePickRepository.addOnePick(description, place, user, category, baseLocation);
    }
    // 원픽 장소 수정
    @Override
    public void modifyOnePick(Integer onePickId, String description, Integer place, Integer user,
            Integer category, Integer baseLocation) {
        onePickRepository.addOnePick(description, place, user, category, baseLocation);
    }
}
