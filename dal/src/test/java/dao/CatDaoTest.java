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
import org.junit.runner.RunWith;

import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
class CatDaoTest {

    private static ModelCat biba;
    private static ModelCat boba;
    
    private static CatDao catDao;
    
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

        catDao = new CatDao();
    }
    

    @Test
    void persist1Cat_DBShouldContain1Cat() {
        catDao.deleteAll();
        //biba was created in setUp()
        
        catDao.persist(biba);

        List<ModelCat> currentCats = catDao.findAll();
        Assertions.assertEquals(1, currentCats.size());
    }

    @Test
    void update() {
        catDao.deleteAll();

        ModelCat clone = biba.toBuilder().build();
        catDao.persist(clone);
        //TODO: сделать енамы как человеческие строки в таблицах, нахрен надо так париться. А в сущностях просто енамы, а не OneToOne
        ModelCat bibaBefore = catDao.persist(biba);
        biba = bibaBefore.toBuilder().withName("Ben, ohoho no").withType(new ModelCatType(CatType.BEN)).build();
        ModelCat bibaAfter = catDao.update(biba);
        
        Assertions.assertEquals("Ben, ohoho no", bibaAfter.getName());
        Assertions.assertEquals(CatType.BEN.toString(), bibaAfter.getType().getType().toString());

        Assertions.assertNotEquals(bibaBefore.getName(), bibaAfter.getName());
        Assertions.assertNotEquals(bibaBefore.getType().getType().toString(), bibaAfter.getType().getType().toString());
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