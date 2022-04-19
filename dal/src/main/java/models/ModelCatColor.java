package models;

import enums.CatColor;
import lombok.Value;

import javax.persistence.*;

@Entity
@Value
@Table(name = "cat_color")
public class ModelCatColor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    int id;

    @Column(name = "color", unique = true, nullable = true)
    @Enumerated(EnumType.STRING)
    CatColor color;
}
