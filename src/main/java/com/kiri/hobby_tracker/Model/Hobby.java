package com.kiri.hobby_tracker.Model;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(exclude = {"categories"})
@ToString(exclude = {"categories"})
@Entity
public class Hobby {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;
    private String description;
    @Column(name = "interest_level")
    private Integer interestLevel;
    @Column(name = "effort_level")
    private Integer effortLevel;
    @Column(name = "points_interval_type")
    @Enumerated(EnumType.STRING)
    private PointsInterval pointIntervalType;
    @ElementCollection(targetClass = DayOfWeek.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "hobby_interval_weekdays", joinColumns = @JoinColumn(name = "hobby_id"))
    @Column(name = "weekday")
    private Set<DaysOfWeek> intervalDaysOfWeek;
    @ElementCollection
    @CollectionTable(name = "hobby_interval_monthdays", joinColumns = @JoinColumn(name = "hobby_id"))
    @Column(name = "day_of_month")
    private Set<Integer> intervalDaysOfMonth;
    @Column(name = "points_current")
    private Integer pointsCurrent;
    @Column(name = "points_valued")
    private Integer pointsValued;

    @ManyToMany
    @JoinTable(
            name = "hobby_category",
            joinColumns = @JoinColumn(name = "hobby_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )

    private Set<Category> categories = new HashSet<>();

    @OneToMany(mappedBy = "hobby", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pluspoint> pluspoints = new ArrayList<>();

    @OneToMany(mappedBy = "hobby", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Minuspoint> minuspoints = new ArrayList<>();

    @OneToMany(mappedBy = "hobby", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HobbyDates> dates = new ArrayList<>();

    public Hobby() {
    }

    public Hobby(Long id, String name, String description, Integer interestLevel, Integer effortLevel, PointsInterval pointIntervalType,
            Set<DaysOfWeek> intervalDaysOfWeek, Set<Integer> intervalDaysOfMonth, Integer pointsCurrent,
            Integer pointsValued) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.interestLevel = interestLevel;
        this.effortLevel = effortLevel;
        this.pointIntervalType = pointIntervalType;
        this.intervalDaysOfWeek = intervalDaysOfWeek;
        this.intervalDaysOfMonth = intervalDaysOfMonth;
        this.pointsCurrent = pointsCurrent;
        this.pointsValued = pointsValued;
    }

}
