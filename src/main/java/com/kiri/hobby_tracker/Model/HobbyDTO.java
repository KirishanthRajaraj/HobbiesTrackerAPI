package com.kiri.hobby_tracker.Model;

import java.util.List;
import java.util.Set;

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
    private List<HobbyDatesDTO> dates;
    private PointsInterval pointIntervalType;
    private Set<DaysOfWeek> intervalDaysOfWeek;
    private Set<Integer> intervalDaysOfMonth;
    private Integer pointsValued;
    private Integer pointsCurrent;

    public HobbyDTO() {}

    public HobbyDTO(Long id, String name, String description, Integer effortLevel, Integer interestLevel,
                    List<PointsDTO> pluspoints, List<PointsDTO> minuspoints, List<CategoryDTO> categories, List<HobbyDatesDTO> dates, PointsInterval pointIntervalType,
                    Set<DaysOfWeek> intervalDaysOfWeek, Set<Integer> intervalDaysOfMonth, Integer pointsValued, Integer pointsCurrent) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.effortLevel = effortLevel;
        this.interestLevel = interestLevel;
        this.pluspoints = pluspoints;
        this.minuspoints = minuspoints;
        this.categories = categories;
        this.dates = dates;       
        this.pointIntervalType = pointIntervalType;
        this.intervalDaysOfWeek = intervalDaysOfWeek;
        this.intervalDaysOfMonth = intervalDaysOfMonth;
        this.pointsValued = pointsValued;
        this.pointsCurrent = pointsCurrent;
    }

}
