package com.marcos.gra.dto;

import lombok.*;

import java.util.Collections;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MovieDTO {
    String producer;
    Integer interval;
    Integer previousWin;
    Integer followingWin;

    public static MovieDTO of(String producer, List<Integer> years) {
        ResumeValuesDTO resumeValuesDTO = calculateIntervalAndOrdenate(years);
        return MovieDTO
                .builder()
                .producer(producer)
                .interval(resumeValuesDTO.getInterval())
                .previousWin(resumeValuesDTO.getPreviousWin())
                .followingWin(resumeValuesDTO.getFollowingWin())
                .build();
    }

    private static ResumeValuesDTO calculateIntervalAndOrdenate(List<Integer> years) {
        Collections.sort(years);
        int interval = years.get(1) - years.get(0);

        return ResumeValuesDTO
                .builder()
                .interval(interval)
                .previousWin(years.get(0))
                .followingWin(years.get(1))
                .build();
    }
}
