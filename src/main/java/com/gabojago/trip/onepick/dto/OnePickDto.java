package com.gabojago.trip.onepick.dto;

import com.gabojago.trip.onepick.domain.OnePick;
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
    private Integer placeId;
    private Integer category;
    private Integer baseLocation;

    @Builder
    public OnePickDto(Integer id, String description, Timestamp createdDate, Integer placeId, Integer category, Integer baseLocation) {
        this.id = id;
        this.description = description;
        this.createdDate = createdDate;
        this.placeId = placeId;
        this.category = category;
        this.baseLocation = baseLocation;
    }

    public static OnePickDto from(OnePick onePick) {
        return OnePickDto.builder()
                .id(onePick.getId())
                .description(onePick.getDescription())
                .createdDate(onePick.getCreatedDate())
                .placeId(onePick.getPlace().getId())
                .category(onePick.getCategory())
                .baseLocation(onePick.getBaseLocation())
                .build();
    }

}
