package com.marcos.gra.entity;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "movies")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Getter
    @Setter
    @Column(name = "syear")
    private Integer syear;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String studios;

    @Getter
    @Setter
    private String producers;

    @Getter
    @Setter
    private String winner;

    public static Movie of(int year, String title, String studios, String producers, String winner) {
        return Movie
                .builder()
                .syear(year)
                .title(title)
                .studios(studios)
                .producers(producers)
                .winner(winner)
                .build();
    }
}