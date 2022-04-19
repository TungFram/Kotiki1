package models;

import enums.CatType;
import lombok.Value;

import javax.persistence.*;


@Entity
@Value
@Table(name = "cat_type")
public class ModelCatType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    int id;

    @Column(name = "type", unique = true, nullable = true)
    @Enumerated(EnumType.STRING)
    CatType color;
}
