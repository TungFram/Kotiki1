package dao;

import enums.CatColor;
import enums.CatType;
import models.ModelOwner;
import models.ModelOwner;
import models.ModelOwner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OwnerDaoTest {

    private static ModelOwner mafioznik;
    private static ModelOwner limarchik;
    private static OwnerDao ownerDao;

    @BeforeEach
    void setUp() {
        mafioznik = ModelOwner.createBuilder()
                .withSurname("Zubenko")
                .withName("Mihail Petrovich")
                .withDateOfBirth(LocalDate.now())
                .withMail("LetsCelebrateAndLoveSomeCats@gmail.com")
                .build();

        limarchik = ModelOwner.createBuilder()
                .withSurname("Limar")
                .withName("Sraniy Matstat")
                .withDateOfBirth(LocalDate.now())
                .withMail("NeIdemNaPary@mail.ru")
                .build();
        
        ownerDao = new OwnerDao();
    }


    @Test
    void persist() {
        ownerDao.deleteAll();

        ownerDao.persist(mafioznik);
        List<ModelOwner> currentOwners = ownerDao.findAll();

        Assertions.assertEquals(1, currentOwners.size());
    }

    @Test
    void update() {
        ownerDao.deleteAll();

        ModelOwner clone = mafioznik.toBuilder().withMail("oldMail (needUnique)").build();
        ownerDao.persist(clone);

        ModelOwner mafioznikBefore = ownerDao.persist(mafioznik);
        mafioznik = mafioznikBefore.toBuilder().withName("Fredi Shoevich").withSurname("Povishev").withMail("HelloDopsa@gmail.com").build();
        ModelOwner mafioznikAfter = ownerDao.update(mafioznik);

        Assertions.assertEquals("Fredi Shoevich", mafioznikAfter.getName());
        Assertions.assertEquals("Povishev", mafioznikAfter.getSurname());
        Assertions.assertEquals("HelloDopsa@gmail.com", mafioznikAfter.getMail());

        Assertions.assertNotEquals(mafioznikBefore.getName(), mafioznikAfter.getName());
        Assertions.assertNotEquals(mafioznikBefore.getSurname(), mafioznikAfter.getSurname());
        Assertions.assertNotEquals(mafioznikBefore.getMail(), mafioznikAfter.getMail());
    }

    @Test
    void findById() {
        ownerDao.deleteAll();

        ModelOwner persistedMafioznik = ownerDao.persist(mafioznik);
        ModelOwner foundedMafioznik = ownerDao.findById(persistedMafioznik.getId());

        Assertions.assertNotNull(foundedMafioznik);
        Assertions.assertEquals(persistedMafioznik.getId(), foundedMafioznik.getId());
        Assertions.assertEquals(persistedMafioznik, foundedMafioznik);
    }

    @Test
    void delete() {
        ownerDao.deleteAll();

        ModelOwner persistedMafioznik = ownerDao.persist(mafioznik);
        ModelOwner persistedLimar = ownerDao.persist(limarchik);
        ownerDao.delete(persistedMafioznik);

        ModelOwner foundedMafioznik = ownerDao.findById(persistedMafioznik.getId());

        Assertions.assertNull(foundedMafioznik);
        List<ModelOwner> currentCats = ownerDao.findAll();
        Assertions.assertEquals(1, currentCats.size());
        Assertions.assertEquals(currentCats.get(0), persistedLimar);
    }
}