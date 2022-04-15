package models;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Value
@Builder(builderClassName = "OwnerBuilder",
        builderMethodName = "createBuilder",
        toBuilder = true,
        access = AccessLevel.PRIVATE,
        setterPrefix = "with")
@DynamicInsert
@DynamicUpdate
@Table(name = "owner")
public class ModelOwner {

    @Id
    @SequenceGenerator(name = "owner_seq_gen", sequenceName = "own_sequence", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "owner_seq_gen")
    @Column(name = "id", nullable = false)
    int id;
    
    @Column(name = "name")
    String name;
    
    @Column(name = "surname")
    String surname;
    
    @Column(name = "DateOfBirth")
    LocalDate DateOfBirth;


    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany
    @JoinTable(name = "affiliation",
            joinColumns = @JoinColumn(name = "id_of_owner", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_of_cat", referencedColumnName = "id"))
    List<ModelCat> cats;
}
