package dao;

import dao.interfaces.IOwnerDao;
import models.ModelCat;
import models.ModelOwner;

import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.query.criteria.JpaCriteriaQuery;
import org.hibernate.query.criteria.JpaRoot;

import util.HibernateSessionFactory;

import java.util.List;

@NoArgsConstructor
@Getter
public class OwnerDao implements IOwnerDao<ModelOwner, Integer> {

    private Session currentSession;
    private Transaction currentTransaction;

    @Override
    public void persist(ModelOwner entity) {
        getCurrentSession().persist(entity);
    }

    @Override
    public void update(ModelOwner entity) {
        getCurrentSession().merge(entity);
    }

    @Override
    public ModelOwner findById(Integer id) {
        return HibernateSessionFactory.getSessionFactory().openSession().get(ModelOwner.class, id);
    }

    @Override
    public List<ModelOwner> findAll() {
        HibernateCriteriaBuilder builder = currentSession.getCriteriaBuilder();
        JpaCriteriaQuery<ModelOwner> criteriaQuery = builder.createQuery(ModelOwner.class);
        JpaRoot<ModelOwner> rootEntry = criteriaQuery.from(ModelOwner.class);
        JpaCriteriaQuery<ModelOwner> all = criteriaQuery.select(rootEntry);

        Query<ModelOwner> allQuery = currentSession.createQuery(all);
        return allQuery.getResultList();
    }

    @Override
    public void delete(ModelOwner entity) {
        currentSession.remove(entity);
    }

    @Override
    public void deleteAll() {
        List<ModelOwner> entityList = findAll();
        for (ModelOwner entity : entityList) {
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
