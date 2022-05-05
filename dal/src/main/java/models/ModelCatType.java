package models;

import enums.CatType;
import lombok.NoArgsConstructor;
import lombok.Value;

import jakarta.persistence.*;

@Entity
@Value
@NoArgsConstructor(force = true)
@Table(name = "Cat_type")
public class ModelCatType {

    @Id
    @Column(name = "TypeID", nullable = false)
    int id;

    @Column(name = "Type", length = 32, unique = true, nullable = true)
    @Enumerated(EnumType.STRING)
    CatType type;

    public ModelCatType(CatType type) {
        this.type = type;
        this.id = type.ordinal();
    }
}
