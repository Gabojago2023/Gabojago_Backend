package com.gabojago.trip.onepick.dto;

import com.gabojago.trip.onepick.domain.OnePick;
import com.gabojago.trip.place.domain.Place;
import java.math.BigDecimal;
import java.sql.Timestamp;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OnePickResponseDto {
    private Integer id;
    private Integer likeCount;

    private Integer userId;
    private String userName;

    private String description;
    private Timestamp createdDate;
    private Integer placeId;
    private String placeName;
    private String address;
    private String placeImg;

    private Integer category;
    private Integer baseLocation;

    private BigDecimal latitude;
    private BigDecimal longitude;


    @Builder
    public OnePickResponseDto(Integer id, Integer userId, String userName, String description, Timestamp createdDate, Integer placeId, String placeName, String address, String placeImg, Integer category, Integer baseLocation, BigDecimal latitude, BigDecimal longitude) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.description = description;
        this.createdDate = createdDate;
        this.placeId = placeId;
        this.placeName = placeName;
        this.address = address;
        this.placeImg = placeImg;
        this.category = category;
        this.baseLocation = baseLocation;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static OnePickResponseDto from(OnePick onePick) {
        return OnePickResponseDto.builder()
                .id(onePick.getId())
                .userId(onePick.getUser().getId())
                .userName(onePick.getUser().getName())
                .description(onePick.getDescription())
                .createdDate(onePick.getCreatedDate())
                .placeId(onePick.getPlace().getId())
                .placeName(onePick.getPlace().getName())
                .address(onePick.getPlace().getAddress())
                .placeImg(onePick.getPlace().getImgUrl())
                .category(onePick.getCategory())
                .baseLocation(onePick.getBaseLocation())
                .latitude(onePick.getPlace().getLatitude())
                .longitude(onePick.getPlace().getLongitude())
                .build();
    }

}
