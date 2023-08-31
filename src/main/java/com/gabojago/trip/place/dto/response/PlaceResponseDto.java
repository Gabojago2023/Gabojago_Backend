package com.gabojago.trip.place.dto.response;

import com.gabojago.trip.place.domain.Place;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class PlaceResponseDto {

    private final Integer id;
    private final String name;
    private final BigDecimal longitude;
    private final BigDecimal latitude;
    private final String address;
    private final String category;
    private final String imgUrl;
    private final String imgUrl2;
    private final Integer sidoCode;
    private final Integer gugunCode;
    private final String overview;

    private final Integer psId;
    private final Integer isBookmarked;

    @Builder
    public PlaceResponseDto(Integer id, String name, BigDecimal longitude, BigDecimal latitude,
            String address, String category, String imgUrl, String imgUrl2, int sidoCode,
            int gugunCode,
            String overview, int psId, int isBookmarked) {
        this.id = id;
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.category = category;
        this.imgUrl = imgUrl;
        this.imgUrl2 = imgUrl2;
        this.sidoCode = sidoCode;
        this.gugunCode = gugunCode;
        this.overview = overview;
        this.psId = psId;
        this.isBookmarked = isBookmarked;
    }

    public static PlaceResponseDto from(Object[] o) {
        return PlaceResponseDto.builder()
                .id((Integer) o[0])
                .name((String) o[1])
                .longitude((BigDecimal) o[2])
                .latitude((BigDecimal) o[3])
                .address((String) o[4])
                .category((String) o[5])
                .imgUrl((String) o[6])
                .imgUrl2((String) o[7])
                .sidoCode((Integer) o[8])
                .gugunCode((Integer) o[9])
                .overview((String) o[10])
                .psId((Integer) o[11])
                .isBookmarked((Integer) o[12])
                .build();
    }

    public static PlaceResponseDto from(Place place) {
        return PlaceResponseDto.builder()
                .id(place.getId())
                .name(place.getName())
                .longitude(place.getLongitude())
                .latitude(place.getLatitude())
                .address(place.getAddress())
                .category(place.getCategory())
                .imgUrl(place.getImgUrl())
                .imgUrl2(place.getImgUrl2())
                .sidoCode(place.getSido().getSidoCode())
                .gugunCode(place.getGugun().getGugunCode())
                .overview(place.getOverview())
                .build();
    }
}
