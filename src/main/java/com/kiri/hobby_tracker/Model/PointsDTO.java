package com.kiri.hobby_tracker.Model;

import lombok.Data;

@Data
public class PointsDTO {

    private Long id;
    private String text;
    private Long hobbyId;

    public PointsDTO() {
    }

    public PointsDTO(Long id, String text, Long hobbyId) {
        this.id = id;
        this.text = text;
        this.hobbyId = hobbyId;
    }

}
