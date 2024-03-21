package org.servlet.assignment.catalog.dao.impl;

import jakarta.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.servlet.assignment.catalog.ProductSize;
import org.servlet.assignment.catalog.dao.Dao;
import org.servlet.assignment.configuration.HibernateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SizeDao implements Dao<ProductSize, Long> {

    private static final Logger logger = LoggerFactory.getLogger(SizeDao.class);

    @Override
    public List<ProductSize> findAll(int limit, int offset) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Query<ProductSize> query = session.createQuery("FROM ProductSize", ProductSize.class);
        List<ProductSize> sizes = null;
        try {
            sizes = query.setFirstResult(offset).setMaxResults(limit).getResultList();
        } catch (NoResultException ex) {
            logger.error("an error occurred at " + this.getClass());
        } finally {
            session.close();
        }
        return sizes;
    }

    @Override
    public ProductSize findById(Long id) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Query<ProductSize> query = session.createQuery("FROM ProductSize c WHERE c.id = :id", ProductSize.class);
        query.setParameter("id", id);
        ProductSize size = null;
        try {
            size = query.getSingleResult();
        } catch (NoResultException e) {
            logger.error("an error occurred at " + this.getClass());
        } finally {
            session.close();
        }
        return size;
    }

    @Override
    public void save(ProductSize entity) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        try {
            Transaction transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (NoResultException ex) {
            logger.error("an error occurred at " + this.getClass());
        } finally {
            session.close();
        }
    }

    @Override
    public void updateById(ProductSize patch, Long aLong) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        try {
            session.get(ProductSize.class, aLong);
            patch.setId(aLong);
            session.save(patch);
        } catch (NoResultException ex) {
            logger.error("an error occurred at " + this.getClass());
        } finally {
            session.close();
        }
    }
}
