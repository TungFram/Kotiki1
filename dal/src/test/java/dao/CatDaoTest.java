package dao;

import enums.CatColor;
import enums.CatType;
import models.ModelCat;
import models.ModelCatColor;
import models.ModelCatType;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

class CatDaoTest {

    private static ModelCat biba;
    private static ModelCat boba;
    
    @BeforeAll
    static void setUp() {        
        biba = ModelCat.createBuilder()
                .withName("Bibonskiy")
                .withDateOfBirth(LocalDate.now())
                .withColor(new ModelCatColor(CatColor.SMOKE))
                .withType(new ModelCatType(CatType.RUS))
                .build();

        boba = ModelCat.createBuilder()
                .withName("Bolkonskiy")
                .withDateOfBirth(LocalDate.now())
                .withColor(new ModelCatColor(CatColor.WHITE))
                .withType(new ModelCatType(CatType.SYS))
                .build();
    }
    

    @Test
    void persist() {
        CatDao catDao = new CatDao();
        catDao.deleteAll();
        
        catDao.persist(biba);

        List<ModelCat> currentCats = catDao.findAll();
        Assertions.assertEquals(1, currentCats.size());
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