import Cat.CatService;
import Owner.OwnerService;

import dao.CatDao;
import dao.OwnerDao;
import enums.CatColor;
import enums.CatType;
import models.ModelCat;
import models.ModelOwner;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;

@RunWith(MockitoJUnitRunner.class)
class MainServiceTest {

    private static ModelCat lelik;
    private static ModelCat bolik;
    
    private static ModelOwner gigaChad;
    private static ModelOwner sweetMan;

    @Mock private CatDao catDaoMock;
    @Mock private OwnerDao ownerDaoMock;
    
    private static OwnerService ownerService;
    private static CatService catService;
    
    private static MainService mainService;
    
    public MainServiceTest() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeAll
    static void beforeAll() {
        //behavior of mocks
    }
    
    @BeforeEach
    void setUp() {
        lelik = ModelCat.createBuilder()
                .withName("I'm so")
                .withDateOfBirth(LocalDate.now())
                .withColor(CatColor.HONEY)
                .withType(CatType.ASH)
                .build();

        bolik = ModelCat.createBuilder()
                .withName("Baldejnik")
                .withDateOfBirth(LocalDate.now())
                .withColor(CatColor.BALD)
                .withType(CatType.BAL)
                .build();

        gigaChad = ModelOwner.createBuilder()
                .withSurname("Gigachad")
                .withName("Average Enjoyer")
                .withDateOfBirth(LocalDate.now())
                .withMail("TheBestOfTheBest@gmail.com")
                .build();

        sweetMan = ModelOwner.createBuilder()
                .withSurname("Milos")
                .withName("Ricardo")
                .withDateOfBirth(LocalDate.now())
                .withMail("Golosovanie@mail.ru")
                .build();
         
        
        ownerService = new OwnerService(ownerDaoMock);
        catService = new CatService(catDaoMock);
        mainService = new MainService(ownerService, catService);
    }

    @Test
    void getCatsOfOwner() {
    }

    @Test
    void getFriendsOfCat() {
    }

    @Test
    void friend2Cats() {
    }

    @Test
    void unfriend2Cats() {
    }

    @Test
    void friendCats() {
    }

    @Test
    void unfriendCats() {
    }

    @Test
    void changeMailOfOwner() {
    }

    @Test
    void changeNameOfCat() {
    }

    @Test
    void changeOwnerOfCat() {
    }
    
}