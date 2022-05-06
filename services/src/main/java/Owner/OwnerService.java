package Owner;

import dao.CatDao;
import dao.OwnerDao;
import lombok.NoArgsConstructor;
import models.ModelCat;
import models.ModelOwner;

import java.util.List;

@NoArgsConstructor
public class OwnerService {
    
    private final OwnerDao ownerDao = new OwnerDao();
    private final CatDao catDao = new CatDao();

    public ModelOwner create(ModelOwner entity) {
        if (entity == null)
            return null;
        return ownerDao.persist(entity);
    }

    public ModelOwner findById(int id) {
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
    
    public void addCat(int idOfOwner, ModelCat cat) throws Exception {
        if (cat == null)
            throw new Exception("Invalid cat");
        
        ModelOwner owner = ownerDao.findById(idOfOwner);
        if(owner == null)
            return;
        
        owner = owner.toBuilder().withCat(cat).build();
        ownerDao.update(owner);
    }
}
