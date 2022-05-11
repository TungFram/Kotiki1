import Cat.CatService;
import Owner.OwnerService;

import dao.CatDao;
import dao.OwnerDao;
import enums.CatColor;
import enums.CatType;
import models.ModelCat;
import models.ModelOwner;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
class MainServiceTest {

    private static ModelCat lelik;
    private static ModelCat bolik;
    
    private static ModelOwner gigaChad;
    private static ModelOwner sweetMan;

    @Mock private CatDao catDaoMock;
    @Mock private OwnerDao ownerDaoMock;
    
    @InjectMocks private static OwnerService ownerService;
    @InjectMocks private static CatService catService;
    
    private static MainService mainService;
    
    public MainServiceTest() {
        MockitoAnnotations.initMocks(this);
    }
    
    @BeforeEach
    void setUp() {
        lelik = ModelCat.createBuilder()
                .withId(111)
                .withName("I'm so")
                .withDateOfBirth(LocalDate.now())
                .withColor(CatColor.HONEY)
                .withType(CatType.ASH)
                .build();

        bolik = ModelCat.createBuilder()
                .withId(222)
                .withName("Baldejnik")
                .withDateOfBirth(LocalDate.now())
                .withColor(CatColor.BALD)
                .withType(CatType.BAL)
                .build();

        gigaChad = ModelOwner.createBuilder()
                .withId(777)
                .withSurname("Gigachad")
                .withName("Average Enjoyer")
                .withDateOfBirth(LocalDate.now())
                .withMail("TheBestOfTheBest@gmail.com")
                .withCats(new ArrayList<>())
                .build();

        sweetMan = ModelOwner.createBuilder()
                .withId(999)
                .withSurname("Milos")
                .withName("Ricardo")
                .withDateOfBirth(LocalDate.now())
                .withMail("Golosovanie@mail.ru")
                .withCats(new ArrayList<>())
                .build();
         
        
        ownerService = new OwnerService(ownerDaoMock);
        catService = new CatService(catDaoMock);
        mainService = new MainService(ownerService, catService);
    }

    @Test
    void registerValidOwner_shouldReturnSameOwner() throws Exception {
        String expectedName = "Aartur";
        String expectedSurname = "Pirojkooov";
        String expectedMail = "nastavitMujuRojkov@gmail.com";
        LocalDate expectedDate = LocalDate.now();
        List<ModelCat> cats = new ArrayList<>();
        
        ModelOwner expectedArtur = ModelOwner.createBuilder()
                .withSurname(expectedName)
                .withName(expectedSurname)
                .withDateOfBirth(expectedDate)
                .withMail(expectedMail)
                .withCats(cats)
                .build();
        Mockito.when(ownerDaoMock.persist(Mockito.any())).thenReturn(expectedArtur);
        
        ModelOwner actualArtur = mainService.registerOwner(
                expectedName,
                expectedSurname,
                expectedMail,
                expectedDate,
                cats);
        Assertions.assertEquals(expectedArtur, actualArtur);
    }
    
    @Test
    void registerValidCat_shouldReturnSameCat() throws Exception {
        String expectedName = lelik.getName();
        LocalDate expectedDate = lelik.getDateOfBirth();
        CatType expectedType = lelik.getType();
        CatColor expectedColor = lelik.getColor();
        int idOfOwner = gigaChad.getId();
        List<ModelCat> friends = lelik.getFriends();

        ModelCat expectedCat = ModelCat.createBuilder()
                .withName(expectedName)
                .withDateOfBirth(expectedDate)
                .withType(expectedType)
                .withColor(expectedColor)
                .withOwner(gigaChad)
                .withFriends(friends)
                .build();
        
        Mockito.when(ownerDaoMock.findById(Mockito.any())).thenReturn(gigaChad);
        Mockito.when(ownerDaoMock.update(Mockito.any()))
                .thenReturn(gigaChad.toBuilder().withCat(lelik).build());
        
        Mockito.when(catDaoMock.persist(Mockito.any()))
                .thenReturn(lelik.toBuilder().withOwner(gigaChad).build());
        
        ModelCat createdLelik = mainService.registerCat(
                expectedName,
                expectedDate,
                expectedType,
                expectedColor,
                idOfOwner,
                friends);

        Mockito.verify(ownerDaoMock, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(catDaoMock, Mockito.times(1)).persist(Mockito.any());

        Assertions.assertEquals(gigaChad, createdLelik.getOwner());
        Assertions.assertEquals(expectedCat.getName(), createdLelik.getName());
        Assertions.assertEquals(expectedCat.getDateOfBirth(), createdLelik.getDateOfBirth());
        Assertions.assertEquals(expectedCat.getType(), createdLelik.getType());
        Assertions.assertEquals(expectedCat.getColor(), createdLelik.getColor());
        Assertions.assertEquals(expectedCat.getFriends(), createdLelik.getFriends());

        Assertions.assertEquals(expectedCat, createdLelik);

    }

    @Test
    void registerCatWithInvalidOwnerID_shouldThrowException() throws Exception {
        String expectedName = lelik.getName();
        LocalDate expectedDate = lelik.getDateOfBirth();
        CatType expectedType = lelik.getType();
        CatColor expectedColor = lelik.getColor();
        int idOfOwner = -10;
        List<ModelCat> friends = lelik.getFriends();
        
        Throwable throwable = Assertions.assertThrows(Exception.class, () ->
                mainService.registerCat(
                        expectedName,
                        expectedDate,
                        expectedType,
                        expectedColor,
                        idOfOwner,
                        friends));
        Assertions.assertNotNull(throwable);
    }
    
    @Test
    void getCatsOfOwner() throws Exception {
        Mockito.when(ownerDaoMock.findById(Mockito.any())).thenReturn(gigaChad);
        
        Mockito.when(ownerDaoMock.update(gigaChad))
                .thenReturn(gigaChad.toBuilder().withCat(lelik).build())
                .thenReturn(gigaChad.toBuilder().withCat(bolik).build());
        
        Mockito.when(catDaoMock.persist(Mockito.any())).thenReturn(lelik)
                .thenReturn(bolik);

        ModelCat cat1 = registerLelikWithGigaId();
        ModelCat cat2 = registerBolikWithGigaId();

        lelik = lelik.toBuilder().withOwner(gigaChad).build();
        Assertions.assertEquals(lelik, cat1);
        List<ModelCat> expectedCats = new ArrayList<>();
        expectedCats.add(cat1);
        expectedCats.add(cat2);

        gigaChad = gigaChad.toBuilder().withCat(cat1).withCat(cat2).build();
        Mockito.when(ownerDaoMock.findById(Mockito.any())).thenReturn(gigaChad);
        List<ModelCat> actualCats = mainService.getCatsOfOwner(gigaChad.getId());

        Assertions.assertNotNull(actualCats);
        Assertions.assertEquals(expectedCats, gigaChad.getCats());
        Assertions.assertEquals(expectedCats, actualCats);
    }
    
    @Test
    void friend2Cats() throws Exception {
        Mockito.when(ownerDaoMock.findById(Mockito.any())).thenReturn(gigaChad)
                .thenReturn(sweetMan);
        
        Mockito.when(catDaoMock.persist(Mockito.any()))
                .thenReturn(lelik.toBuilder().withOwner(gigaChad).build())
                .thenReturn(bolik.toBuilder().withOwner(sweetMan).build());

        ModelCat registeredLelik = registerLelikWithGigaId();
        ModelCat registeredBolik = registerBolikWithMilosId();

        gigaChad = gigaChad.toBuilder().withCat(registeredLelik).build();
        sweetMan = sweetMan.toBuilder().withCat(registeredBolik).build();

        Mockito.when(catDaoMock.findById(Mockito.any()))
                .thenReturn(registeredLelik)
                .thenReturn(registeredBolik);
        
        mainService.friend2Cats(registeredLelik.getId(), registeredBolik.getId());

        Mockito.verify(catDaoMock, Mockito.times(1)).findById(registeredLelik.getId());
        Mockito.verify(catDaoMock, Mockito.times(1)).findById(registeredBolik.getId());

        Mockito.verify(catDaoMock, Mockito.times(1)).update(registeredLelik);
        Mockito.verify(catDaoMock, Mockito.times(1)).update(registeredBolik);

    }
    

    @Test
    void unfriend2Cats() {
    }

    @Test
    void friendCats() throws Exception {
    }
    
//    @Test //Тест на рельной бд, все работает!!!)
//    void friendCats() throws Exception {
//        MainService service = new MainService(
//                new OwnerService(new OwnerDao()),
//                new CatService(new CatDao())
//        );
//        
//        service.deleteAllOwners();
//        service.deleteAllCats();
//        
//        ModelOwner registeredGiga = service.registerOwner(
//                gigaChad.getName(),
//                gigaChad.getSurname(),
//                gigaChad.getMail(),
//                gigaChad.getDateOfBirth(),
//                gigaChad.getCats()
//        );
//        ModelOwner registeredMilos = service.registerOwner(
//                sweetMan.getName(),
//                sweetMan.getSurname(),
//                sweetMan.getMail(),
//                sweetMan.getDateOfBirth(),
//                sweetMan.getCats()
//        );
//        
//        ModelCat registeredLelik = service.registerCat(
//                lelik.getName(),
//                lelik.getDateOfBirth(),
//                lelik.getType(),
//                lelik.getColor(),
//                registeredGiga.getId(),
//                lelik.getFriends());
//        ModelCat registeredBolik = service.registerCat(
//                bolik.getName(),
//                bolik.getDateOfBirth(),
//                bolik.getType(),
//                bolik.getColor(),
//                registeredMilos.getId(),
//                bolik.getFriends());
//        
//        service.friend2Cats(registeredLelik.getId(), registeredBolik.getId());
//    }

    @Test
    void unfriendCats() {
    }

    @Test
    void getFriendsOfCat() {
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
    
    private ModelCat registerLelikWithGigaId() throws Exception {
        return mainService.registerCat(
                lelik.getName(),
                lelik.getDateOfBirth(),
                lelik.getType(),
                lelik.getColor(),
                gigaChad.getId(),
                lelik.getFriends());
    }

    private ModelCat registerBolikWithGigaId() throws Exception {
        return mainService.registerCat(
                bolik.getName(),
                bolik.getDateOfBirth(),
                bolik.getType(),
                bolik.getColor(),
                gigaChad.getId(),
                bolik.getFriends());
    }
    private ModelCat registerBolikWithMilosId() throws Exception {
        return mainService.registerCat(
                bolik.getName(),
                bolik.getDateOfBirth(),
                bolik.getType(),
                bolik.getColor(),
                sweetMan.getId(),
                bolik.getFriends());
    }
}