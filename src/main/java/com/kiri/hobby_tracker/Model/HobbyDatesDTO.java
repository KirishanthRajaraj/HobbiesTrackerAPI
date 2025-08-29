package com.kiri.hobby_tracker.Model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class HobbyDatesDTO {

    private Long id;
    private Long hobbyId;
    private LocalDate date;

    public HobbyDatesDTO() {
    } 

    public HobbyDatesDTO(HobbyDates entity) {
        this.id = entity.getId();
        this.hobbyId = entity.getHobby().getId();
        this.date = entity.getDate();
    }
}
