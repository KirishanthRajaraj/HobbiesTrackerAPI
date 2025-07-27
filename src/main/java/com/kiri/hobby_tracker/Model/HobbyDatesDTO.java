package com.kiri.hobby_tracker.Model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class HobbyDatesDTO {
    private Long id;
    private Long hobbyId;
    private LocalDate date;
}
