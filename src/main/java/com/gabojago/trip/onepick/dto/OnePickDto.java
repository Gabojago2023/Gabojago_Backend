package com.gabojago.trip.onepick.dto;

import com.gabojago.trip.onepick.domain.OnePick;
import com.gabojago.trip.place.domain.Place;
import java.sql.Timestamp;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OnePickDto {
    private Integer id;
    private String description;
    private Timestamp createdDate;
    private Place place;
    private Integer category;
    private Integer baseLocation;

    @Builder
    public OnePickDto(Integer id, String description, Timestamp createdDate, Place place, Integer category, Integer baseLocation) {
        this.id = id;
        this.description = description;
        this.createdDate = createdDate;
        this.place = place;
        this.category = category;
        this.baseLocation = baseLocation;
    }

    public static OnePickDto from(OnePick onePick) {
        return OnePickDto.builder()
                .id(onePick.getId())
                .description(onePick.getDescription())
                .createdDate(onePick.getCreatedDate())
                .place(onePick.getPlace())
                .category(onePick.getCategory())
                .baseLocation(onePick.getBaseLocation())
                .build();
    }
}
