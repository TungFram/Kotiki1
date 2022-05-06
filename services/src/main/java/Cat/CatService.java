package Cat;

import dao.CatDao;
import models.ModelCat;

import java.util.List;

public class CatService {
    
    private final CatDao catDao = new CatDao();
    
    public ModelCat findCatById(int id) {
        return catDao.findById(id);
    }
    
    public List<ModelCat> findAllCats() {
        return catDao.findAll();
    }
    
    public void deleteCatById(int id) {
        ModelCat cat = catDao.findById(id);
        if (cat == null)
            return;
        
        catDao.delete(cat);
    }
    
    public void deleteAllCats() {
        catDao.deleteAll();
    }
    
    public ModelCat updateCat(ModelCat cat) {
        if (cat == null)
            return null;
        
        return catDao.update(cat);
    }

    public void friendCats(int idOfFirstCat, int idOfSecondCat) throws Exception {
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
}
