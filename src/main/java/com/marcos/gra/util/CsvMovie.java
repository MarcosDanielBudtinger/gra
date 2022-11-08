package com.marcos.gra.util;
import com.opencsv.bean.CsvBindByName;
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
    @CsvBindByName(column = "winner", required = false)
    private String winner;
}
