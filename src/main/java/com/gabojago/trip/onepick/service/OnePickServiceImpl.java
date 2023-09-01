package com.gabojago.trip.onepick.service;

import com.gabojago.trip.onepick.domain.DistributedOnePick;
import com.gabojago.trip.onepick.domain.OnePick;
import com.gabojago.trip.onepick.dto.DistributedRateDto;
import com.gabojago.trip.onepick.dto.OnePickDto;
import com.gabojago.trip.onepick.dto.RankedOnePickDto;
import com.gabojago.trip.onepick.repository.DistributedOnePickRepository;
import com.gabojago.trip.onepick.repository.OnePickRepository;
import com.gabojago.trip.user.domain.User;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;

@Service
public class OnePickServiceImpl implements OnePickService {

    private final OnePickRepository onePickRepository;
    private final DistributedOnePickRepository distributedOnePickRepository;

    public OnePickServiceImpl(OnePickRepository onePickRepository, DistributedOnePickRepository distributedOnePickRepository) {
        this.onePickRepository = onePickRepository;
        this.distributedOnePickRepository = distributedOnePickRepository;
    }

    // 나의 모든 원픽 중 가장 최근 것만 조회
    public List<OnePick> getAllValidOnePicksByUserId(Integer userId) {
        List<OnePick> userOnePicks = getAllOnePicksByUserId(userId);

        // sort user one picks based on created date
        userOnePicks.sort(new Comparator<OnePick>() {
            @Override
            public int compare(OnePick o1, OnePick o2) {
                return o1.getCreatedDate().compareTo(o2.getCreatedDate());
            }
        });

        HashMap<Integer, OnePick> validOnePicks = new HashMap<>();
        for (OnePick onePick : userOnePicks) {
            validOnePicks.put(onePick.getPlace().getId(), onePick);
        }

        return new ArrayList<>(validOnePicks.values());
    }

    // 나의 모든 원픽 조회
    public List<OnePick> getAllOnePicksByUserId(Integer userId) {
        return onePickRepository.findAllByUserId(userId);
    }

    // 랜덤한 OnePick 하나 뽑기
    @Override
    public OnePick getOnePick(Integer category, Integer baseLocation, Integer userId) {
        OnePick onePick = null;

        Stream<OnePick> filterStream = onePickRepository.findAllOnePick().stream();
        Stream<DistributedOnePick> existStream = onePickRepository.getDistributedOnePicksByUserId(userId).stream();

        if (category != null) {
            filterStream = filterStream.filter(op -> op.getCategory().equals(category));
            existStream = existStream.filter(op -> op.getOnePick().getCategory().equals(category));
        }

        if (baseLocation != null) {
            filterStream = filterStream.filter(op -> op.getBaseLocation().equals(baseLocation));
            existStream = existStream.filter(op -> op.getOnePick().getBaseLocation().equals(baseLocation));
        }

        List<OnePick> allOnePicks = new ArrayList<>(filterStream.toList());
        Collections.shuffle(allOnePicks);

        Set<Integer> existOnePickIds = existStream.map(op -> op.getOnePick().getId()).collect(
                Collectors.toSet());

        if (allOnePicks.isEmpty() || allOnePicks.size() == existOnePickIds.size()) {
            return null;
        }


        List<OnePick> myOnePicks = getAllOnePicksByUserId(userId);
        Set<Integer> userOnePickIds = new HashSet<>();
        for (OnePick myOnePick : myOnePicks)
            userOnePickIds.add(myOnePick.getId());

        for (OnePick op : allOnePicks) {
            if (!existOnePickIds.contains(op.getId()) && !userOnePickIds.contains(op.getId())) {
                onePick = op;
                DistributedOnePick distributedOnePick = DistributedOnePick.builder()
                        .onePick(onePick)
                        .user(new User(userId))
                        .build();
                distributedOnePickRepository.save(distributedOnePick);
                break;
            }
        }

        return onePick;
    }

    // 나의 모든 DistributedOnePick 조회
    @Override
    public List<DistributedOnePick> getAllDistributedOnePick(Integer userId) {
        return distributedOnePickRepository.findAllByUserId(userId);
    }

    @Override
    public void addOnePick(OnePick onePick) {
        onePickRepository.save(onePick);
    }


    // 내가 뽑은 원픽 장소 평가
    public void rateDistributedOnePick(DistributedRateDto distributedRateDto) {

        DistributedOnePick distributedOnePick = distributedOnePickRepository.findById(distributedRateDto.getDistributedId()).get();

        if (distributedRateDto.getRate() != null) {
            distributedOnePick.setRate(distributedRateDto.getRate());
        }
        if (distributedRateDto.getDescription() != null) {
            distributedOnePick.setDescription(distributedRateDto.getDescription());
        }

        distributedOnePickRepository.save(distributedOnePick);

    }

    // 내가 뽑은 원픽 좋아요 추가
    public void likeDistributedOnePick(Integer distributedOnePickId) {
        DistributedOnePick distributedOnePick = distributedOnePickRepository.findById(distributedOnePickId).get();
        if (distributedOnePick.getLiked() == null)
            distributedOnePick.setLiked(false);
        distributedOnePick.setLiked(!distributedOnePick.getLiked());
        distributedOnePickRepository.save(distributedOnePick);
    }

    // 가장 많은 좋아요를 받은 원픽 조회
    public RankedOnePickDto getMostLikedOnePick(Integer category) {
        List<DistributedOnePick> onePickList = distributedOnePickRepository.findAllByCategory(category);
        Map<Integer, Integer> onePickLikedCount = new HashMap<>();
        PriorityQueue<Integer> heap = new PriorityQueue<>(Comparator.reverseOrder());

        for (DistributedOnePick distributedOnePick : onePickList) {
            if (distributedOnePick.getLiked() == null || !distributedOnePick.getLiked())
                continue;
            Integer id = distributedOnePick.getOnePick().getId();
            onePickLikedCount.put(id, onePickLikedCount.getOrDefault(id, 0)+1);
        }

        if (onePickLikedCount.isEmpty())
            return null;

        PriorityQueue<Map.Entry<Integer, Integer>> maxLikesHeap = new PriorityQueue<>(
                (a, b) -> b.getValue() - a.getValue()
        );

        maxLikesHeap.addAll(onePickLikedCount.entrySet());

        Map.Entry<Integer, Integer> mostLikedPost = maxLikesHeap.poll();
        Integer mostLikedPostId = mostLikedPost.getKey();
        Integer mostLikedPostLikes = mostLikedPost.getValue();

        OnePick mostLikedOnePick = onePickRepository.findById(mostLikedPostId).get();
        OnePickDto onePickDto = OnePickDto.from(mostLikedOnePick);

        return  RankedOnePickDto.builder()
                .userId(mostLikedOnePick.getUser().getId())
                .onePickDto(onePickDto)
                .likedCount(mostLikedPostLikes)
                .build();

    }
}
