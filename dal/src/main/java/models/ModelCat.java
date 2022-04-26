package models;

import java.time.LocalDate;
import java.util.List;


import jakarta.persistence.*;
import lombok.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Value
@Builder(builderClassName = "CatBuilder",
        builderMethodName = "createBuilder",
        toBuilder = true,
        access = AccessLevel.PUBLIC,
        setterPrefix = "with")
@DynamicInsert
@DynamicUpdate
@Table(name = "cat")
public class ModelCat { сделать дефолтный конструктор
    
    @Id
    @SequenceGenerator(name = "pet_seq_gen", sequenceName = "pet_sequence", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pet_seq_gen")
    @Column(name = "id", nullable = false)
    int id;

    @EqualsAndHashCode.Exclude
    @Column(name = "name", length = 64)
    String name;
    
    @Column(name = "date_birth")
    @Builder.Default LocalDate dateOfBirth = LocalDate.now();

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_of_type", unique = true, nullable = false)
    ModelCatType type;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_of_color", unique = true, nullable = false)
    ModelCatColor color;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(name = "affiliation",
            joinColumns = @JoinColumn(name = "id_of_cat", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_of_owner", referencedColumnName = "id"))
    ModelOwner owner;
    
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "friendship",
            joinColumns = @JoinColumn(name = "id_of_first_cat", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_of_second_cat", referencedColumnName = "id"))
    @Singular List<ModelCat> fiends;
}
