package com.kiri.hobby_tracker.Model;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
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

    

    public Hobby() {}

    public Hobby(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
