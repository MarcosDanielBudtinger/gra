package com.marcos.gra.util;
import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@Builder
public class CsvMovie {
    private int year;
    private String title;
    private String studios;
    private String producers;
    private String winner;
}
