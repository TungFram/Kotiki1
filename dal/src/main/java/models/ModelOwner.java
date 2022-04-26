package models;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    @JoinTable(name = "affiliation",
            joinColumns = @JoinColumn(name = "id_of_owner", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_of_cat", referencedColumnName = "id"))
    @Singular
    List<ModelCat> cats;

}
