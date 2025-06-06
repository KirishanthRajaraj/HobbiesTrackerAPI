package com.kiri.hobby_tracker.Model;

import java.util.List;

import lombok.Data;

@Data
public class HobbyDTO {
    private Long id;
    private String name;
    private String description;
    private Integer effortLevel;
    private Integer interestLevel;
    private List<PointsDTO> pluspoints;
    private List<PointsDTO> minuspoints;
    private List<CategoryDTO> categories;

    public HobbyDTO() {}

    public HobbyDTO(Long id, String name, String description, Integer effortLevel, Integer interestLevel,
                    List<PointsDTO> pluspoints, List<PointsDTO> minuspoints, List<CategoryDTO> categories) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.effortLevel = effortLevel;
        this.interestLevel = interestLevel;
        this.pluspoints = pluspoints;
        this.minuspoints = minuspoints;
        this.categories = categories;
    }

}
