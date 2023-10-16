package com.gabojago.trip.onepick.dto;

import com.gabojago.trip.onepick.domain.DistributedOnePick;
import java.math.BigDecimal;
import java.sql.Timestamp;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DistributedOnePickDto {

    private Integer id;
    private Double rate;
    private Timestamp createdAt;
    private OnePickResponseDto onePickResponseDto;
    private String description;
    private Boolean liked;

    @Builder
    public DistributedOnePickDto(Integer id, Double rate, Timestamp createdAt,
            OnePickResponseDto onePickResponseDto, String description, Boolean liked) {
        this.id = id;
        this.rate = rate;
        this.createdAt = createdAt;
        this.onePickResponseDto = onePickResponseDto;
        this.description = description;
        this.liked = liked;
    }

    public static DistributedOnePickDto from(DistributedOnePick distributedOnePick) {
        return DistributedOnePickDto.builder()
                .id(distributedOnePick.getId())
                .rate(distributedOnePick.getRate())
                .createdAt(distributedOnePick.getCreatedAt())
                .onePickResponseDto(OnePickResponseDto.from(distributedOnePick.getOnePick()))
                .description(distributedOnePick.getDescription())
                .liked(distributedOnePick.getLiked())
                .build();
    }

}
