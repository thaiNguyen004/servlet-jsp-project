package org.servlet.assignment.catalog.dao.impl;

import jakarta.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.servlet.assignment.catalog.Picture;
import org.servlet.assignment.catalog.Product;
import org.servlet.assignment.catalog.dao.Dao;
import org.servlet.assignment.configuration.HibernateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ProductDao implements Dao<Product, Long> {

    private static final Logger logger = LoggerFactory.getLogger(ProductDao.class);

    @Override
    public List<Product> findAll(int limit, int offset) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Query<Product> query = session.createQuery("FROM Product", Product.class);
        List<Product> products = null;
        try {
            Transaction transaction = session.beginTransaction();
            products = query.setFirstResult(offset).setMaxResults(limit).getResultList();
            transaction.commit();
        } catch (NoResultException ex) {
            logger.error("an error occurred at " + this.getClass());
        } finally {
            session.close();
        }
        return products;
    }

    public List<Product> findAllByCategoryId(long categoryId, int limit, int offset) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Query<Product> query = session.createQuery("FROM Product WHERE category.id = :categoryId", Product.class);
        List<Product> products = null;
        try {
            Transaction transaction = session.beginTransaction();
            query.setParameter("categoryId", categoryId);
            products = query.setFirstResult(offset).setMaxResults(limit).getResultList();
            transaction.commit();
        } catch (NoResultException ex) {
            logger.error("an error occurred at " + this.getClass());
        } finally {
            session.close();
        }
        return products;
    }

    public List<Picture> findAllPicturesByProductId(Long id, int offset, int limit) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Query<Picture> query = session.createQuery("FROM Picture p WHERE p.product.id = :id", Picture.class);
        query.setParameter("id", id);
        List<Picture> pictures = null;
        try {
            pictures = query.setFirstResult(offset).setMaxResults(limit).getResultList();
        } catch (NoResultException e) {
            logger.error("an error occurred at " + this.getClass());
        } finally {
            session.close();
        }
        return pictures;
    }

    @Override
    public Product findById(Long id) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Query<Product> query = session.createQuery("FROM Product c WHERE c.id = :id", Product.class);
        query.setParameter("id", id);
        Product product = null;
        try {
            product = query.getSingleResult();
        } catch (NoResultException e) {
            logger.error("an error occurred at " + this.getClass());
        } finally {
            session.close();
        }
        return product;
    }

    @Override
    public void save(Product entity) {
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
    public void updateById(Product patch, Long aLong) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        try {
            session.get(Product.class, aLong);
            patch.setId(aLong);
            session.save(patch);
        } catch (NoResultException ex) {
            logger.error("an error occurred at " + this.getClass());
        } finally {
            session.close();
        }
    }
}
