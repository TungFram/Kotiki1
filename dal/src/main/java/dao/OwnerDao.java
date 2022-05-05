package dao;

import dao.interfaces.IOwnerDao;
import models.ModelCat;
import models.ModelOwner;

import lombok.NoArgsConstructor;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.query.criteria.JpaCriteriaQuery;
import org.hibernate.query.criteria.JpaRoot;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import util.HibernateSessionFactory;

import java.util.List;


@NoArgsConstructor
public class OwnerDao implements IOwnerDao<ModelOwner, Integer> {

    @Override
    public ModelOwner persist(ModelOwner entity) {
        Session session = this.getSession();
        Transaction transaction = this.getTransaction(session);
        
        session.persist(entity);
        
        transaction.commit();
        session.close();
        return entity;
    }

    @Override
    public ModelOwner update(ModelOwner entity) {
        Session session = this.getSession();
        Transaction transaction = this.getTransaction(session);

        ModelOwner newEntity = session.merge(entity);

        transaction.commit();
        session.close();
        return newEntity;
    }

    @Override
    public ModelOwner findById(Integer id) {
        Session session = this.getSession();

        ModelOwner model = session.get(ModelOwner.class, id);

        session.close();
        return model;
    }

    @Override
    public List<ModelOwner> findAll() {
        Session session = this.getSession();
        Transaction transaction = getTransaction(session);

        HibernateCriteriaBuilder builder = this.getSession().getCriteriaBuilder();
        JpaCriteriaQuery<ModelOwner> criteriaQuery = builder.createQuery(ModelOwner.class);
        JpaRoot<ModelOwner> rootEntry = criteriaQuery.from(ModelOwner.class);
        JpaCriteriaQuery<ModelOwner> all = criteriaQuery.select(rootEntry);

        Query<ModelOwner> allQuery = this.getSession().createQuery(all);
        List<ModelOwner> resultList = allQuery.getResultList();

        transaction.commit();
        session.close();
        return resultList;
    }

    @Override
    public void delete(ModelOwner entity) {
        Session session = this.getSession();
        Transaction transaction = getTransaction(session);

        session.remove(entity);

        transaction.commit();
        session.close();    }

    @Override
    public void deleteAll() {
        List<ModelOwner> entityList = findAll();
        for (ModelOwner entity : entityList) {
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
