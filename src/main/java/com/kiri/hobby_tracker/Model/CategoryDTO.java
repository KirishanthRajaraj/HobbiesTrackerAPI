package com.kiri.hobby_tracker.Model;
import lombok.Data;

@Data
public class CategoryDTO {
    private Long id;
    private String name;
    private Long hobbyId;

    public CategoryDTO() {
    }
    public CategoryDTO(Long id, String name, Long hobbyId) {
        this.id = id;
        this.name = name;
        this.hobbyId = hobbyId; 
    }
}