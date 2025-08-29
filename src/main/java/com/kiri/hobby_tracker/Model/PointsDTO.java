package com.kiri.hobby_tracker.Model;

import lombok.Data;

// not to be confused with the other points classes and enums
// this pointsDTO is for minus and plus points
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
