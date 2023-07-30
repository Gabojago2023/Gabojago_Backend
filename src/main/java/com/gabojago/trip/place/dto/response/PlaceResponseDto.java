package com.gabojago.trip.place.dto.response;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class PlaceResponseDto {

    private Integer id;
    private String name;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private String address;
    private String category;
    private String imgUrl;
    private String imgUrl2;
    private Integer sidoCode;
    private Integer gugunCode;
    private String overview;
    private Integer isBookmarked;

    @Builder
    public PlaceResponseDto(Integer id, String name, BigDecimal longitude, BigDecimal latitude,
            String address, String category, String imgUrl, String imgUrl2, int sidoCode,
            int gugunCode,
            String overview, int isBookmarked) {
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
                .isBookmarked((Integer) o[11])
                .build();
    }
}
