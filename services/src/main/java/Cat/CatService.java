package Cat;

import dao.CatDao;
import lombok.AllArgsConstructor;
import models.ModelCat;
import models.ModelOwner;

import java.util.List;

@AllArgsConstructor
public class CatService {
    
    private final CatDao catDao;
    
    public ModelCat createCat(ModelCat cat) throws Exception {
        if (cat == null)
            throw new Exception("Invalid cat.");
        
        return catDao.persist(cat);
    }
    
    public ModelCat findCatById(int id) {
        return catDao.findById(id);
    }
    
    public List<ModelCat> findAllCats() {
        return catDao.findAll();
    }
    
    public void deleteById(int id) {
        ModelCat cat = catDao.findById(id);
        if (cat == null)
            return;
        
        catDao.delete(cat);
    }
    
    public void deleteAll() {
        catDao.deleteAll();
    }
    
    
    
    public ModelCat changeOwnerOfCat(int idOfCat, ModelOwner newOwner) throws Exception {
        ModelCat cat = findCatById(idOfCat);
        if (cat == null)
            throw new Exception("Can't find cat");
        
        cat = cat.toBuilder().withOwner(newOwner).build();
        return catDao.update(cat);
    }
    
    public ModelCat changeNameOfCat(int idOfCat, String name) throws Exception {
        ModelCat cat = findCatById(idOfCat);
        if (cat == null)
            throw new Exception("Can't find cat");
        
        cat = cat.toBuilder().withName(name).build();
        return catDao.update(cat);
    }

    public void friendCats(int idOfFirstCat, int idOfSecondCat) throws Exception {
        if (idOfFirstCat == idOfSecondCat)
            return;
        
        ModelCat firstCat = findCatById(idOfFirstCat);
        ModelCat secondCat = findCatById(idOfSecondCat);
        if (firstCat == null || secondCat == null)
            return;
        
        ModelCat foundedFirstCat = secondCat.getFriends().stream()
                .filter(cat -> cat.getId() == idOfFirstCat).findFirst().orElse(null);
        ModelCat foundedSecondCat = firstCat.getFriends().stream()
                .filter(cat -> cat.getId() == idOfSecondCat).findFirst().orElse(null);
    
        if (foundedFirstCat == null && foundedSecondCat != null ||
                foundedFirstCat != null && foundedSecondCat == null) {
            throw new Exception("Friendship isn't bidirectional");
        }
        
        if (foundedFirstCat != null && foundedSecondCat != null) {
            return;
        }
        
        firstCat = firstCat.toBuilder().withFriend(secondCat).build();
        secondCat = secondCat.toBuilder().withFriend(firstCat).build();
        
        catDao.update(firstCat);
        catDao.update(secondCat);
    }

    public void unfriendCats(int idOfFirstCat, int idOfSecondCat) throws Exception {
        if (idOfFirstCat == idOfSecondCat)
            return;
        
        ModelCat firstCat = findCatById(idOfFirstCat);
        ModelCat secondCat = findCatById(idOfSecondCat);
        if (firstCat == null || secondCat == null)
            return;

        List<ModelCat> friendsOfFirstCat = getFriendsOfCat(idOfFirstCat);
        List<ModelCat> friendsOfSecondCat = getFriendsOfCat(idOfSecondCat);
        
        ModelCat foundedFirstCat = friendsOfSecondCat.stream()
                .filter(cat -> cat.getId() == idOfFirstCat).findFirst().orElse(null);
        ModelCat foundedSecondCat = friendsOfFirstCat.stream()
                .filter(cat -> cat.getId() == idOfSecondCat).findFirst().orElse(null);

        if (foundedFirstCat == null && foundedSecondCat != null ||
                foundedFirstCat != null && foundedSecondCat == null) {
            throw new Exception("Friendship isn't bidirectional");
        }
        
        if (foundedFirstCat == null && foundedSecondCat == null) {
            return;
        }

        friendsOfFirstCat.remove(secondCat);
        friendsOfSecondCat.remove(firstCat);
        firstCat = firstCat.toBuilder().clearFriends().withFriends(friendsOfFirstCat).build();
        secondCat = secondCat.toBuilder().clearFriends().withFriends(friendsOfSecondCat).build();

        catDao.update(firstCat);
        catDao.update(secondCat);
    }
    
    public List<ModelCat> getFriendsOfCat(int idOfCat) throws Exception {
        ModelCat cat = findCatById(idOfCat);
        if (cat == null)
            throw new Exception("Invalid cat");

        return cat.getFriends();
    }
}
