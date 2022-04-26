package models;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Value
@Builder(builderClassName = "OwnerBuilder",
        builderMethodName = "createBuilder",
        toBuilder = true,
        access = AccessLevel.PUBLIC,
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
    
    @Column(name = "name", length = 32)
    String name;
    
    @Column(name = "surname", length = 32)
    String surname;
    
    @Column(name = "date_birth")
    LocalDate dateOfBirth;

    @Column(name = "mail", length = 64)
    String mail;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Singular
    List<ModelCat> cats;

}
