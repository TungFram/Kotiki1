package models;

import java.time.LocalDate;
import java.util.List;


import jakarta.persistence.*;
import lombok.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Value
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@Builder(builderClassName = "CatBuilder",
        builderMethodName = "createBuilder",
        toBuilder = true,
        access = AccessLevel.PUBLIC,
        setterPrefix = "with")
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "Cat")
public class ModelCat { //сделать дефолтный конструктор
    
    @Id
    @SequenceGenerator(name = "pet_seq_gen", sequenceName = "pet_sequence", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pet_seq_gen")
    @Column(name = "CatID", nullable = false)
    int id;

    @EqualsAndHashCode.Exclude
    @Column(name = "Name", length = 64)
    String name;
    
    @Column(name = "Date_birth")
    @Builder.Default LocalDate dateOfBirth = LocalDate.now();

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "Id_of_type", unique = true, nullable = false)
    ModelCatType type;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "Cat_color",
            inverseJoinColumns = @JoinColumn(name = "Id_of_color", referencedColumnName = "ColorID"))
    ModelCatColor color;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "Affiliation",
            joinColumns = @JoinColumn(name = "Id_of_cat", referencedColumnName = "CatID"),
            inverseJoinColumns = @JoinColumn(name = "Id_of_owner", referencedColumnName = "OwnerID"))
    ModelOwner owner;
    
    
    
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "Friendship",
            joinColumns = @JoinColumn(name = "id_of_first_cat", referencedColumnName = "CatID"),
            inverseJoinColumns = @JoinColumn(name = "id_of_second_cat", referencedColumnName = "CatID"))
    @Singular List<ModelCat> fiends;
}
