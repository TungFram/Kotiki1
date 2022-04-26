package dao;

import dao.interfaces.ICatDao;
import org.hibernate.query.Query;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import models.ModelCat;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.criteria.JpaCriteriaQuery;
import org.hibernate.query.criteria.JpaRoot;
import util.HibernateSessionFactory;


import java.util.List;

@NoArgsConstructor
@Getter
public class CatDao implements ICatDao<ModelCat, Integer> {

    private Session currentSession;
    private Transaction currentTransaction;

    @Override
    public void persist(ModelCat entity) {
        getCurrentSession().persist(entity);
    }

    @Override
    public void update(ModelCat entity) {
        getCurrentSession().merge(entity);
    }
    
    @Override
    public ModelCat findById(Integer id) {
        return HibernateSessionFactory.getSessionFactory().openSession().get(ModelCat.class, id);
    }
    
    @Override
    public List<ModelCat> findAll() {
        HibernateCriteriaBuilder builder = currentSession.getCriteriaBuilder();
        JpaCriteriaQuery<ModelCat> criteriaQuery = builder.createQuery(ModelCat.class);
        JpaRoot<ModelCat> rootEntry = criteriaQuery.from(ModelCat.class);
        JpaCriteriaQuery<ModelCat> all = criteriaQuery.select(rootEntry);

        Query<ModelCat> allQuery = currentSession.createQuery(all);
        return allQuery.getResultList();
    }

    @Override
    public void delete(ModelCat entity) {
        currentSession.remove(entity);
    }

    @Override
    public void deleteAll() {
        List<ModelCat> entityList = findAll();
        for (ModelCat entity : entityList) {
            delete(entity);
        }
    }
    
    public Session openSession() {
        currentSession = HibernateSessionFactory.getSessionFactory().openSession();
        return currentSession;
    }

    public Session openSessionWithTransaction() {
        currentSession = HibernateSessionFactory.getSessionFactory().openSession();
        currentTransaction = currentSession.beginTransaction();
        return currentSession;
    }
    
    public void closeCurrentSession() {
        currentSession.close();
    }

    public void closeCurrentSessionWithTransaction() {
        currentSession.close();
        currentTransaction.commit();
    }
}
