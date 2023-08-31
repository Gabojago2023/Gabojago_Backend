package com.gabojago.trip.place.service;

import com.gabojago.trip.place.domain.Place;
import com.gabojago.trip.place.domain.PlaceScrap;
import com.gabojago.trip.place.dto.response.PlaceDetailResponseDto;
import com.gabojago.trip.place.dto.response.PlaceResponseDto;
import com.gabojago.trip.place.dto.response.RandomImageResponseDto;
import com.gabojago.trip.place.dto.response.SidoResponseDto;
import com.gabojago.trip.place.exception.PlaceAlreadyExistsException;
import com.gabojago.trip.place.exception.PlaceNotFoundException;
import com.gabojago.trip.place.exception.PlaceScrapNotFoundException;
import com.gabojago.trip.place.repository.PlaceRepository;
import com.gabojago.trip.place.repository.PlaceScrapRepository;
import com.gabojago.trip.place.repository.SidoRepository;
import com.gabojago.trip.user.domain.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlaceServiceImpl implements PlaceService {

    private final PlaceRepository placeRepository;
    private final PlaceScrapRepository placeScrapRepository;
    private final SidoRepository sidoRepository;

    @Autowired
    public PlaceServiceImpl(PlaceRepository placeRepository,
            PlaceScrapRepository placeScrapRepository, SidoRepository sidoRepository) {
        this.placeRepository = placeRepository;
        this.placeScrapRepository = placeScrapRepository;
        this.sidoRepository = sidoRepository;
    }

    @Override
    public List<PlaceResponseDto> searchAttractionByKeyword(Integer userId, Integer sidoCode,
            Integer gugunCode, String keyword, Integer pg, Integer spp) {
        PageRequest pageRequest = PageRequest.of(pg - 1, spp);

        List<PlaceResponseDto> placeResponseDtoList = new ArrayList<>();
        List<Object[]> placesByFilter = placeRepository.findPlacesByFilter(userId, sidoCode,
                gugunCode, keyword, pageRequest);
        for (Object[] o : placesByFilter) {
            PlaceResponseDto from = PlaceResponseDto.from(o);
            placeResponseDtoList.add(from);
        }
        return placeResponseDtoList;
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
    public List<PlaceResponseDto> searchAttractionByLocation(Integer userId, String location,
            Integer pg, Integer spp) {
        PageRequest pageRequest = PageRequest.of(pg - 1, spp);

        List<PlaceResponseDto> placeResponseDtoList = new ArrayList<>();
        List<Object[]> result = placeRepository.findPlacesByLocation(location, userId, pageRequest);
        for (Object[] o : result) {
            PlaceResponseDto from = PlaceResponseDto.from(o);
            placeResponseDtoList.add(from);
        }

        return placeResponseDtoList;
    }

    @Override
    public List<PlaceResponseDto> getBookmarkedAttractionsByUserId(Integer userId, Integer pg,
            Integer spp) {
        PageRequest pageRequest = PageRequest.of(pg - 1, spp);

        List<PlaceResponseDto> placeResponseDtoList = new ArrayList<>();
        List<Object[]> result = placeRepository.findBookmarkedPlacesByUserId(userId, pageRequest);
        for (Object[] o : result) {
            PlaceResponseDto from = PlaceResponseDto.from(o);
            placeResponseDtoList.add(from);
        }
        return placeResponseDtoList;
    }

    @Override
    public PlaceDetailResponseDto getPlaceDetailByPlaceId(Integer placeId) {
        Object[] result = placeRepository.findPlaceWithAvgRatingAndCommentCount(
                placeId);
        return PlaceDetailResponseDto.from((Object[]) result[0]);
    }

    @Override
    public void addScrapPlace(Integer placeId, Integer userId) {
        Optional<Place> byId = placeRepository.findById(placeId);

        PlaceScrap isExisting = placeScrapRepository.findByPlaceIdAndUserId(placeId,
                userId);
        if (byId.isEmpty()) {
            throw new PlaceNotFoundException(placeId.toString());
        } else if (isExisting != null) {
            throw new PlaceAlreadyExistsException(userId.toString() + " : " + placeId);
        } else {
            Place place = new Place(placeId);
            User user = new User(userId);
            PlaceScrap placeScrap = new PlaceScrap(place, user);
            placeScrapRepository.save(placeScrap);
        }
    }

    @Override
    @Transactional
    public void removeScrapPlace(Integer placeId, Integer userId) {
        Optional<Place> byId = placeRepository.findById(placeId);

        PlaceScrap isExisting = placeScrapRepository.findByPlaceIdAndUserId(placeId,
                userId);

        if (byId.isEmpty()) {
            throw new PlaceNotFoundException(placeId.toString());
        } else if (isExisting == null) {
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
}
