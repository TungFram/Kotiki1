package models;

import enums.CatColor;
import lombok.Value;

import jakarta.persistence.*;

@Entity
@Value
@Table(name = "cat_color")
public class ModelCatColor {
    
    @Id
    @Column(name = "id", nullable = false)
    int id;

    @Column(name = "color", length = 32, unique = true, nullable = true)
    @Enumerated(EnumType.STRING)
    CatColor color;
    
    public ModelCatColor(CatColor color) {
        this.color = color;
        this.id = color.ordinal();
    }
}
