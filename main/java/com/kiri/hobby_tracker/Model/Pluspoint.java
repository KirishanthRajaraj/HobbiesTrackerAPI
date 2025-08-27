package com.kiri.hobby_tracker.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Pluspoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    @ManyToOne
    @JoinColumn(name = "hobby_id")
    private Hobby hobby;

    public Pluspoint() {}
    public Pluspoint(Long id, String text, Hobby hobby) {
        this.id = id;
        this.text = text;
        this.hobby = hobby;
    }
    public String getText() {
        return text;
    }
}
