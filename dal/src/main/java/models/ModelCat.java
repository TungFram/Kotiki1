package models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Value
@Builder(builderClassName = "CatBuilder",
        builderMethodName = "createBuilder",
        toBuilder = true,
        access = AccessLevel.PRIVATE,
        setterPrefix = "with")
@DynamicInsert
@DynamicUpdate
@Table(name = "cat")
public class ModelCat {
    
    @Id
    @SequenceGenerator(name = "pet_seq_gen", sequenceName = "pet_sequence", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pet_seq_gen")
    @Column(name = "id", nullable = false)
    int id;

    @EqualsAndHashCode.Exclude
    @Column(name = "name")
    String name;
    
    @Column(name = "date_birth")
    @Builder.Default LocalDate dateOfBirth = LocalDate.now();
    
    @Column(name = "id_of_type")
    int idOfType;
    
    @Column(name = "id_of_color")
    int idOfColor;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "cats")
    @Builder.Default List<ModelOwner> owners = new ArrayList<>();
    
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany
    @JoinTable(name = "friendship",
            joinColumns = @JoinColumn(name = "id_of_first_cat", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_of_second_cat", referencedColumnName = "id")) //todo: Можно ли так делать
    @Builder.Default List<ModelCat> fiends = new ArrayList<>();
}
