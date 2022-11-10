package com.marcos.gra.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class MovieResponseDTO {
    private List<MovieDTO> min;
    private List<MovieDTO> max;

    public static MovieResponseDTO of(List<MovieDTO> min, List<MovieDTO> max) {
        return MovieResponseDTO
                .builder()
                .min(min)
                .max(max)
                .build();
    }
}
