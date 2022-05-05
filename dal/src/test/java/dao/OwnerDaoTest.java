package dao;

import models.ModelCat;
import models.ModelOwner;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

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
//
//class CatServiceTest
//{
//    @Mock
//    private CatRepository catRepository;
//    private final CatService catService;
//
//    public CatServiceTest(){
//        MockitoAnnotations.openMocks(this);
//        this.catService = new CatService(catRepository);
//    }
//
//    @Test
//    void findCat()
//    {
//        given(catRepository.findById(1)).willReturn(new Cat("Boris", Timestamp.valueOf("2000-11-11 11:11:11"), "Egyptian", CatColors.White));
//        Assertions.assertEquals(catService.findCat(1).getName(), "Boris");
//    }
//
//    @Test
//    void saveCat()
//    {
//        Cat cat = new Cat("Boris", Timestamp.valueOf("2000-11-11 11:11:11"), "Egyptian", CatColors.White);
//        catService.saveCat(cat);
//        verify(catRepository).save(cat);
//    }
//
//    @Test
//    void deleteCat()
//    {
//        Cat cat = new Cat("Boris", Timestamp.valueOf("2000-11-11 11:11:11"), "Egyptian", CatColors.White);
//        catService.deleteCat(cat);
//        verify(catRepository).delete(cat);
//    }
//
//    @Test
//    void findAllCats()
//    {
//        catService.findAllCats();
//        verify(catRepository).findAll();
//    }
//}