package models;

import enums.CatType;
import lombok.Value;

import jakarta.persistence.*;

@Entity
@Value
@Table(name = "cat_type")
public class ModelCatType {

    @Id
    @Column(name = "id", nullable = false)
    int id;

    @Column(name = "type", length = 32, unique = true, nullable = true)
    @Enumerated(EnumType.STRING)
    CatType type;

    public ModelCatType(CatType type) {
        this.type = type;
        this.id = type.ordinal();
    }
}
