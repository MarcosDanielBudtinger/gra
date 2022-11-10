package com.marcos.gra.dto;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
public class ResumeValuesDTO {
    Integer interval;
    Integer previousWin;
    Integer followingWin;
}
