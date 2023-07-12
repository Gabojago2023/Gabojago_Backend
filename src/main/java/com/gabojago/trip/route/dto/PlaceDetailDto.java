package com.gabojago.trip.route.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@ToString
@Setter
@Getter
public class PlaceDetailDto {
    private int id;
    private String name;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private String address;
    private String category;
    private String imgUrl;
    private String imgUrl2;
    private int sidoCode;
    private int gugunCode;
    private String overview;

    @Builder
    public PlaceDetailDto(int id, String name, BigDecimal longitude, BigDecimal latitude, String address, String category, String imgUrl, String imgUrl2, int sidoCode, int gugunCode, String overview) {
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
    }
    @Builder
    public PlaceDetailDto(String name){
        this.name = name;
    }
}
