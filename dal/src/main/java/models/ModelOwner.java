package models;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Value
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@Builder(builderClassName = "OwnerBuilder",
        builderMethodName = "createBuilder",
        toBuilder = true,
        access = AccessLevel.PUBLIC,
        setterPrefix = "with")
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "Owner")
public class ModelOwner {

    @Id
    @SequenceGenerator(name = "owner_seq_gen", sequenceName = "own_sequence", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "owner_seq_gen")
    @Column(name = "OwnerID", nullable = false)
    int id;
    
    @Column(name = "Name", length = 32)
    String name;
    
    @Column(name = "Surname", length = 32)
    String surname;
    
    @Column(name = "Date_birth")
    LocalDate dateOfBirth;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Column(name = "Mail", length = 64)
    String mail;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "owner", orphanRemoval = true, fetch = FetchType.LAZY)
    @Singular
    List<ModelCat> cats;

}
