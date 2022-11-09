package com.marcos.gra.dto;

import com.marcos.gra.entity.Movie;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieDTO {
    String name;
    Integer interval;
    Integer previousWin;
    Integer followingWin;

    public static MovieDTO of(String name ) {
        return MovieDTO
                .builder()
                .name(name)
                .build();
    }
}
