package dao;

import enums.CatColor;
import enums.CatType;
import models.ModelCat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;

import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
class CatDaoTest {

    private static ModelCat biba;
    private static ModelCat boba;
    
    private static CatDao catDao;
    
    @BeforeEach
    void setUp() {        
        biba = ModelCat.createBuilder()
                .withName("Bibonskiy")
                .withDateOfBirth(LocalDate.now())
                .withColor(CatColor.BLACK)
                .withType(CatType.RUS)
                .build();

        boba = ModelCat.createBuilder()
                .withName("Bolkonskiy")
                .withDateOfBirth(LocalDate.now())
                .withColor(CatColor.WHITE)
                .withType(CatType.SYS)
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
    void updateCat_CatDataChanged() {
        catDao.deleteAll();

        ModelCat clone = biba.toBuilder().build();
        catDao.update(clone);
        
        ModelCat bibaBefore = catDao.persist(biba);
        biba = bibaBefore.toBuilder().withName("Ben, ohoho, no").withType(CatType.BEN).withColor(CatColor.EBONY).build();
        ModelCat bibaAfter = catDao.update(biba);
        
        Assertions.assertEquals("Ben, ohoho, no", bibaAfter.getName());
        Assertions.assertEquals(CatType.BEN.toString(), bibaAfter.getType().toString());
        Assertions.assertEquals(CatColor.EBONY.toString(), bibaAfter.getColor().toString());

        Assertions.assertNotEquals(bibaBefore.getName(), bibaAfter.getName());
        Assertions.assertNotEquals(bibaBefore.getType().toString(), bibaAfter.getType().toString());
        Assertions.assertNotEquals(bibaBefore.getColor().toString(), bibaAfter.getColor().toString());

    }

    @Test
    void findById() {
        catDao.deleteAll();
        
        ModelCat persistedBiba = catDao.persist(biba);
        ModelCat foundedBiba = catDao.findById(persistedBiba.getId());

        Assertions.assertNotNull(foundedBiba);
        Assertions.assertEquals(persistedBiba.getId(), foundedBiba.getId());
        Assertions.assertEquals(persistedBiba, foundedBiba);
    }

    @Test
    void delete() {
        catDao.deleteAll();

        ModelCat persistedBiba = catDao.persist(biba);
        ModelCat persistedBoba = catDao.persist(boba);
        catDao.delete(biba);

        ModelCat foundedBiba = catDao.findById(persistedBiba.getId());

        Assertions.assertNull(foundedBiba);
        List<ModelCat> currentCats = catDao.findAll();
        Assertions.assertEquals(1, currentCats.size());
        Assertions.assertEquals(currentCats.get(0), persistedBoba);
    }
}