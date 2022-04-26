package dao;

import models.ModelCat;
import models.ModelOwner;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class OwnerDaoTest {

    private static ModelOwner mafioznik;

    @BeforeAll
    static void setUp() {
        mafioznik = ModelOwner.createBuilder()
                .withSurname("Зубенко")
                .withName("Михаил Петрович")
                .withDateOfBirth(LocalDate.now())
                .withMail("LetsCelebrateAndLoveSomeCats@gmail.com")
                .build();
    }
    
    
    @Test
    void persist() {
    }

    @Test
    void update() {
    }

    @Test
    void findById() {
    }

    @Test
    void findAll() {
    }

    @Test
    void delete() {
    }

    @Test
    void deleteAll() {
    }
}