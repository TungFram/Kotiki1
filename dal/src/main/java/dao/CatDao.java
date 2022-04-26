package dao;

import dao.interfaces.ICatDao;
import models.ModelCat;

import lombok.NoArgsConstructor;

import org.hibernate.query.Query;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.criteria.JpaCriteriaQuery;
import org.hibernate.query.criteria.JpaRoot;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import util.HibernateSessionFactory;

import java.util.List;


@NoArgsConstructor
public class CatDao implements ICatDao<ModelCat, Integer> {

    @Override
    public void persist(ModelCat entity) {
        Session session = this.getSession();
        Transaction transaction = this.getTransaction(session);
        
        session.persist(entity);
        
        transaction.commit();
        session.close();
    }

    @Override
    public void update(ModelCat entity) {
        Session session = this.getSession();
        Transaction transaction = this.getTransaction(session);
        
        session.merge(entity);
        
        transaction.commit();
        session.close();
    }
    
    @Override
    public ModelCat findById(Integer id) {
        Session session = this.getSession();
        
        ModelCat model = session.get(ModelCat.class, id);
        
        session.close();
        return model;
    }
    
    @Override
    public List<ModelCat> findAll() {
        Session session = this.getSession();
        Transaction transaction = getTransaction(session);
        
        HibernateCriteriaBuilder builder = this.getSession().getCriteriaBuilder();
        JpaCriteriaQuery<ModelCat> criteriaQuery = builder.createQuery(ModelCat.class);
        JpaRoot<ModelCat> rootEntry = criteriaQuery.from(ModelCat.class);
        JpaCriteriaQuery<ModelCat> all = criteriaQuery.select(rootEntry);

        Query<ModelCat> allQuery = this.getSession().createQuery(all);
        List<ModelCat> resultList = allQuery.getResultList();
        
        transaction.commit();
        session.close();
        return resultList;
    }

    @Override
    public void delete(ModelCat entity) {
        Session session = this.getSession();
        Transaction transaction = getTransaction(session);
        
        session.remove(entity);
        
        transaction.commit();
        session.close();
    }

    @Override
    public void deleteAll() {
        List<ModelCat> entityList = findAll();
        for (ModelCat entity : entityList) {
            delete(entity);
        }
    }

    protected Session getSession() {
        Session session = HibernateSessionFactory.getSessionFactory().getCurrentSession();

        if (session == null)
            session = HibernateSessionFactory.getSessionFactory().openSession();

        return session;

    }

    protected Transaction getTransaction(Session session) {
        Transaction transaction = session.getTransaction();
        
        if (!TransactionStatus.ACTIVE.equals(transaction.getStatus()))
            transaction = session.beginTransaction();

        return transaction;
    }
}
