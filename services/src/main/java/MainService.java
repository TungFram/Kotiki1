import Cat.CatService;
import Owner.OwnerService;
import enums.CatColor;
import enums.CatType;
import lombok.AllArgsConstructor;
import models.ModelCat;
import models.ModelOwner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class MainService {
    
    private OwnerService ownerService;
    private CatService catService;
    
    public ModelCat registerCat(
            String name,
            LocalDate dateBirth,
            CatType type,
            CatColor color,
            int idOfOwner,
            List<ModelCat> friends) throws Exception {
        
        ModelOwner owner = ownerService.findOwnerById(idOfOwner);
        if (owner == null)
            throw new Exception("Can't register cat because his owner doesn't registered.");
        if (friends == null)
            friends = new ArrayList<>();
        
        ModelCat cat = ModelCat.createBuilder()
                .withName(name)
                .withDateOfBirth(dateBirth)
                .withType(type)
                .withColor(color)
                .withOwner(owner) //<--
                .withFriends(friends)
                .build();

        ModelOwner updatedOwner = ownerService.addCat(owner, cat);
        
        return catService.createCat(cat);
    }
    
    public ModelOwner registerOwner(
            String name,
            String surname,
            String mail,
            LocalDate dateBirth,
            List<ModelCat> cats) throws Exception {
        if (cats == null)
            cats = new ArrayList<>();
        
        ModelOwner owner = ModelOwner.createBuilder()
                .withName(name)
                .withSurname(surname)
                .withMail(mail)
                .withDateOfBirth(dateBirth)
                .withCats(cats)
                .build();
        
        return ownerService.createOwner(owner);
    }
    
    public List<ModelCat> getCatsOfOwner(int idOfOwner) throws Exception {
        return ownerService.findCatsOfOwner(idOfOwner);
    }
    
    public List<ModelCat> getFriendsOfCat(int idOfCat) throws Exception {
        return catService.getFriendsOfCat(idOfCat);
    }
    
    public void friend2Cats(int idOfFirstCat, int idOfSecondCat) throws Exception {
        catService.friendCats(idOfFirstCat, idOfSecondCat);
    }

    public void unfriend2Cats(int idOfFirstCat, int idOfSecondCat) throws Exception {
        catService.unfriendCats(idOfFirstCat, idOfSecondCat);
    }

    public void friendCats(List<Integer> ids) throws Exception {
        if (ids == null || ids.size() == 1)
            return;
        
        for (int leftId : ids) {
            for (int rightId : ids) {
                catService.friendCats(leftId, rightId);
            }
        }
    }

    public void unfriendCats(List<Integer> ids) throws Exception {
        if (ids == null || ids.size() == 1)
            return;

        for (int leftId : ids) {
            for (int rightId : ids) {
                catService.unfriendCats(leftId, rightId);
            }
        }
    }
    
    public ModelOwner changeMailOfOwner(int idOfOwner, String mail) throws Exception {
        return ownerService.changeMail(idOfOwner, mail);
    }
    
    public ModelCat changeNameOfCat(int idOfCat, String name) throws Exception {
        return catService.changeNameOfCat(idOfCat, name);
    }
    
    public ModelCat changeOwnerOfCat(int idOfCat, int idOfNewOwner) throws Exception {
        ModelOwner newOwner = ownerService.findOwnerById(idOfNewOwner);
        ModelCat cat = catService.findCatById(idOfCat);
        
        ownerService.deleteCat(cat.getOwner().getId(), cat);
        return catService.changeOwnerOfCat(idOfCat, newOwner);
    }
    
    
    
    public ModelCat findCatById(int id) {
        return catService.findCatById(id);
    }

    public List<ModelCat> findCatsById(List<Integer> ids) {
        List<ModelCat> res = new ArrayList<>();
        for(int id : ids) {
            res.add(findCatById(id));
        }
        
        return res;
    }
    
    public ModelOwner findOwnerById(int id) {
        return ownerService.findOwnerById(id);
    }

    public List<ModelOwner> findOwnersById(List<Integer> ids) {
        List<ModelOwner> res = new ArrayList<>();
        for(int id : ids) {
            res.add(findOwnerById(id));
        }

        return res;
    }
    public List<ModelCat> getAllCats() {
        return catService.findAllCats();
    }

    public List<ModelOwner> getAllOwners() {
        return ownerService.findAllOwners();
    }
    
    public void deleteCatById(int id) {
        catService.deleteById(id);
    }
    
    public void deleteOwnerById(int id) {
        ownerService.deleteById(id);
    }

    public void deleteCatsById(List<Integer> ids) {
        for(int id : ids) {
            catService.deleteById(id);
        }
    }

    public void deleteOwnersById(List<Integer> ids) {
        for(int id : ids) {
            ownerService.deleteById(id);
        }
    }
    
    public void deleteAllOwners() {
        ownerService.deleteAll();
    }
    
    public void deleteAllCats() {
        catService.deleteAll();
    }
    
    
}












