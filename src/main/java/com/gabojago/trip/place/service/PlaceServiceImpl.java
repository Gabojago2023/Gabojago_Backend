package com.gabojago.trip.place.service;

import com.gabojago.trip.place.domain.Place;
import com.gabojago.trip.place.domain.PlaceScrap;
import com.gabojago.trip.place.dto.response.CommentResponseDto;
import com.gabojago.trip.place.dto.response.GugunResponseDto;
import com.gabojago.trip.place.dto.response.PlaceDetailResponseDto;
import com.gabojago.trip.place.dto.response.PlaceResponseDto;
import com.gabojago.trip.place.dto.response.RandomImageResponseDto;
import com.gabojago.trip.place.dto.response.SidoResponseDto;
import com.gabojago.trip.place.exception.PlaceAlreadyExistsException;
import com.gabojago.trip.place.exception.PlaceNotFoundException;
import com.gabojago.trip.place.exception.PlaceScrapNotFoundException;
import com.gabojago.trip.place.repository.GugunRepository;
import com.gabojago.trip.place.repository.PlaceRepository;
import com.gabojago.trip.place.repository.PlaceScrapRepository;
import com.gabojago.trip.place.repository.SidoRepository;
import com.gabojago.trip.user.domain.User;
import com.gabojago.trip.user.domain.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlaceServiceImpl implements PlaceService {

    private final PlaceRepository placeRepository;
    private final PlaceScrapRepository placeScrapRepository;
    private final SidoRepository sidoRepository;
    private final GugunRepository gugunRepository;
    private final UserRepository userRepository;

    @Autowired
    public PlaceServiceImpl(PlaceRepository placeRepository,
            PlaceScrapRepository placeScrapRepository, SidoRepository sidoRepository,
            GugunRepository gugunRepository,
            UserRepository userRepository) {
        this.placeRepository = placeRepository;
        this.placeScrapRepository = placeScrapRepository;
        this.sidoRepository = sidoRepository;
        this.gugunRepository = gugunRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Slice<PlaceResponseDto> searchAttractionByKeyword(Integer userId, Integer sidoCode,
            Integer gugunCode, String keyword, Integer cursor,
            Integer size) {
        Pageable pageable = Pageable.ofSize(size + 1);

        List<PlaceResponseDto> placeResponseDtoList = new ArrayList<>();

        if (cursor == null) {
            List<Object[]> result = placeRepository.findPlacesByFilter(userId, sidoCode,
                    gugunCode, keyword, pageable);
            for (Object[] o : result) {
                PlaceResponseDto from = PlaceResponseDto.from2(o);
                placeResponseDtoList.add(from);
            }
            pageable = Pageable.ofSize(size);
            return checkLastPage2(pageable, placeResponseDtoList);
        } else {
            List<Object[]> result = placeRepository.findNextPlacesByFilter(userId, sidoCode,
                    gugunCode, keyword, cursor,
                    pageable);
            for (Object[] o : result) {
                PlaceResponseDto from = PlaceResponseDto.from2(o);
                placeResponseDtoList.add(from);
            }
            pageable = Pageable.ofSize(size);
            return checkLastPage2(pageable, placeResponseDtoList);
        }

//        List<Object[]> placesByFilter = placeRepository.findPlacesByFilter(userId, sidoCode,
//                gugunCode, keyword, pageRequest);
//        for (Object[] o : placesByFilter) {
//            PlaceResponseDto from = PlaceResponseDto.from(o);
//            placeResponseDtoList.add(from);
//        }
//        return placeResponseDtoList;
    }

    @Override
    public List<PlaceResponseDto> searchTop3ScrappedPlaces(Integer top) {
        PageRequest pageRequest = PageRequest.of(0, top);

        List<PlaceResponseDto> placeResponseDtoList = new ArrayList<>();
        List<Integer> top3ScrappedPlacesId = placeRepository.findTop3ScrappedPlacesId(pageRequest);
        for (Integer placeId : top3ScrappedPlacesId) {
            Optional<Place> optionalValue = placeRepository.findById(placeId);
            if (optionalValue.isPresent()) {
                Place place = optionalValue.get();
                PlaceResponseDto from = PlaceResponseDto.from(place);
                placeResponseDtoList.add(from);
            }
        }
        return placeResponseDtoList;
    }

    @Override
    public Slice<PlaceResponseDto> searchAttractionByLocation(Integer userId, String location,
            Integer cursor,
            Integer size) {
        Pageable pageable = Pageable.ofSize(size + 1);

        List<PlaceResponseDto> placeResponseDtoList = new ArrayList<>();

        if (cursor == null) {
            List<Object[]> result = placeRepository.findPlacesByLocation(location, userId,
                    pageable);
            for (Object[] o : result) {
                PlaceResponseDto from = PlaceResponseDto.from2(o);
                placeResponseDtoList.add(from);
            }
            pageable = Pageable.ofSize(size);
            return checkLastPage2(pageable, placeResponseDtoList);
        } else {
            List<Object[]> result = placeRepository.findNextPlacesByLocation(location, userId,
                    cursor,
                    pageable);
            for (Object[] o : result) {
                PlaceResponseDto from = PlaceResponseDto.from2(o);
                placeResponseDtoList.add(from);
            }
            pageable = Pageable.ofSize(size);
            return checkLastPage2(pageable, placeResponseDtoList);
        }

//        List<Object[]> result = placeRepository.findPlacesByLocation(location, userId, pageRequest);
//        for (Object[] o : result) {
//            PlaceResponseDto from = PlaceResponseDto.from(o);
//            placeResponseDtoList.add(from);
//        }
//
//        return placeResponseDtoList;
    }

    @Override
    public Slice<PlaceResponseDto> getBookmarkedAttractionsByUserId(Integer userId, Integer cursor,
            Integer size) {
        Pageable pageable = Pageable.ofSize(size + 1);

        List<PlaceResponseDto> placeResponseDtoList = new ArrayList<>();

        if (cursor == null) {
            List<Object[]> result = placeRepository.findBookmarkedPlacesByUserId(userId, pageable);
            for (Object[] o : result) {
                PlaceResponseDto from = PlaceResponseDto.from(o);
                placeResponseDtoList.add(from);
            }
            pageable = Pageable.ofSize(size);
            return checkLastPage2(pageable, placeResponseDtoList);
        } else {
            List<Object[]> result = placeRepository.findNextBookmarkedPlacesByUserId(userId, cursor,
                    pageable);
            for (Object[] o : result) {
                PlaceResponseDto from = PlaceResponseDto.from(o);
                placeResponseDtoList.add(from);
            }
            pageable = Pageable.ofSize(size);
            return checkLastPage2(pageable, placeResponseDtoList);
        }
    }

    private Slice<PlaceResponseDto> checkLastPage2(Pageable pageable,
            List<PlaceResponseDto> placeList) {
        boolean hasNext = false; //다음으로 가져올 데이터가 있는 지 여부를 알려줌
        if (placeList.size() > pageable.getPageSize()) {
            hasNext = true; //읽어 올 데이터가 있다면 true를 반환
            placeList.remove(pageable.getPageSize());
        }

        return new SliceImpl<>(placeList, pageable, hasNext);
    }

    private Slice<CommentResponseDto> checkLastPage(Pageable pageable,
            List<CommentResponseDto> commentList) {
        boolean hasNext = false; //다음으로 가져올 데이터가 있는 지 여부를 알려줌
        if (commentList.size() > pageable.getPageSize()) {
            hasNext = true; //읽어 올 데이터가 있다면 true를 반환
            commentList.remove(pageable.getPageSize());
        }

        return new SliceImpl<>(commentList, pageable, hasNext);
    }

    @Override
    public PlaceDetailResponseDto getPlaceDetailByPlaceId(Integer placeId, Integer userId) {
        Object[] result = placeRepository.findPlaceWithAvgRatingAndCommentCount(
                placeId, userId);
        return PlaceDetailResponseDto.from((Object[]) result[0]);
    }

    @Override
    public void addScrapPlace(Integer placeId, Integer userId) {
        Optional<Place> byId = placeRepository.findById(placeId);
        Optional<User> byId1 = userRepository.findById((userId));
        Optional<PlaceScrap> byPlaceIdAndUserId = placeScrapRepository.findByPlaceIdAndUserId(
                placeId, userId);
        if (byId.isEmpty()) {
            throw new PlaceNotFoundException(placeId.toString());
        } else if (byPlaceIdAndUserId.isPresent()) {
            throw new PlaceAlreadyExistsException(userId.toString() + " : " + placeId);
        } else {
            PlaceScrap placeScrap = new PlaceScrap(byId.get(), byId1.get());
            placeScrapRepository.save(placeScrap);
        }
    }

    @Override
    @Transactional
    public void removeScrapPlace(Integer placeId, Integer userId) {
        Optional<Place> byId = placeRepository.findById(placeId);

        Optional<PlaceScrap> byPlaceIdAndUserId = placeScrapRepository.findByPlaceIdAndUserId(
                placeId, userId);

        if (byId.isEmpty()) {
            throw new PlaceNotFoundException(placeId.toString());
        } else if (byPlaceIdAndUserId.isEmpty()) {
            throw new PlaceScrapNotFoundException(userId.toString() + " : " + placeId);
        } else {
            Place place = new Place(placeId);
            User user = new User(userId);
            placeScrapRepository.deleteByPlaceAndUser(place, user);
        }
    }

    @Override
    public List<RandomImageResponseDto> getRandomImages() {
        PageRequest pageRequest = PageRequest.of(0, 3);
        return placeRepository.findRandomImages(pageRequest);
    }

    @Override
    public List<SidoResponseDto> getSidoList() {
        return sidoRepository.findAll().stream()
                .map(SidoResponseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<GugunResponseDto> getGugunInSido(Integer sidoId) {
        List<GugunResponseDto> gugunResponseDtos = new ArrayList<>();

        List<Object[]> gugunByIdSidoOrderById = gugunRepository.findGugunByIdSidoOrderById(sidoId);
        for (Object[] o : gugunByIdSidoOrderById) {
            GugunResponseDto gugunResponseDto = new GugunResponseDto((Integer) o[0], (String) o[1]);
            gugunResponseDtos.add(gugunResponseDto);
        }

        return gugunResponseDtos;
    }
}
