package models;

import enums.CatColor;
import lombok.NoArgsConstructor;
import lombok.Value;

import jakarta.persistence.*;

@Entity
@Value
@NoArgsConstructor(force = true)
@Table(name = "Cat_color")
public class ModelCatColor {
    
    @Id
    @Column(name = "ColorID", nullable = false)
    int id;

    @Column(name = "Color", length = 32, unique = true, nullable = true)
    @Enumerated(EnumType.STRING)
    CatColor color;
    
    public ModelCatColor(CatColor color) {
        this.color = color;
        this.id = color.ordinal();
    }
}
