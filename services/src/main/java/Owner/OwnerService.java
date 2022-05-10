package Owner;

import dao.CatDao;
import dao.OwnerDao;
import lombok.AllArgsConstructor;
import models.ModelCat;
import models.ModelOwner;

import java.util.List;

@AllArgsConstructor
public class OwnerService {
    
    private final OwnerDao ownerDao;

    public ModelOwner createOwner(ModelOwner entity) throws Exception {
        if (entity == null)
            throw new Exception("Entity was null");
        return ownerDao.persist(entity);
    }

    public ModelOwner findOwnerById(int id) {
        return ownerDao.findById(id);
    }
    
    public List<ModelOwner> findAllOwners() {
        return ownerDao.findAll();
    }

    public void deleteById(int id) {
        ModelOwner owner = ownerDao.findById(id);
        if (owner == null)
            return;
        ownerDao.delete(owner);
    }

    public void deleteAll() {
        ownerDao.deleteAll();
    }
    
    public ModelOwner changeMail(int idOfOwner, String mail) throws Exception {
        ModelOwner owner = findOwnerById(idOfOwner);
        if (owner == null)
            throw new Exception("Can't find owner.");
        
        owner = owner.toBuilder().withMail(mail).build();
        return ownerDao.update(owner);
    }
    
    public ModelOwner addCat(ModelOwner owner, ModelCat cat) throws Exception {
        if (cat == null)
            throw new Exception("Invalid cat");
        if(owner == null)
            throw new Exception("Invalid owner");
        
        owner = owner.toBuilder().withCat(cat).build();
        ModelOwner updatedOwner = ownerDao.update(owner);
        return updatedOwner;
    }
    
    public void deleteCat(int idOfOwner, ModelCat cat) throws Exception {
        if(cat == null)
            return;
        
        ModelOwner owner = ownerDao.findById(idOfOwner);
        if (owner == null)
            throw new Exception("Can't find owner");

        List<ModelCat> cats = findCatsOfOwner(idOfOwner);
        ModelCat foundedCat = cats.stream().filter(someCat -> someCat.getId() == cat.getId())
                .findFirst().orElse(null);
        if (foundedCat == null)
            throw new Exception("Can't find owner's cat");
        cats.remove(foundedCat);
        
        owner = owner.toBuilder().clearCats().withCats(cats).build();
        ownerDao.update(owner);
    }
    
    public List<ModelCat> findCatsOfOwner(int idOfOwner) throws Exception {
        ModelOwner owner = findOwnerById(idOfOwner);
        if (owner == null)
            throw new Exception("Can't find owner");
        
        return owner.getCats();
    }
}
