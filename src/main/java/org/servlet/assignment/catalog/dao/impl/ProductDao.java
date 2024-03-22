package org.servlet.assignment.catalog.dao.impl;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
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
        Query<Product> query = session.createQuery("FROM Product where isDeleted = false", Product.class);
        List<Product> products = null;
        try {
            Transaction transaction = session.beginTransaction();
            products = query.setFirstResult(offset).setMaxResults(limit).getResultList();
            transaction.commit();
        } catch (Exception ex) {
            logger.error("an error occurred at " + this.getClass());
        } finally {
            session.close();
        }
        return products;
    }

    public List<Product> findAllByCategoryId(long categoryId, int limit, int offset) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Query<Product> query = session.createQuery("FROM Product WHERE category.id = :categoryId and isDeleted = false", Product.class);
        List<Product> products = null;
        try {
            Transaction transaction = session.beginTransaction();
            query.setParameter("categoryId", categoryId);
            products = query.setFirstResult(offset).setMaxResults(limit).getResultList();
            transaction.commit();
        } catch (Exception ex) {
            logger.error("an error occurred at " + this.getClass());
        } finally {
            session.close();
        }
        return products;
    }

    @Override
    public Product findById(Long id) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Query<Product> query = session.createQuery("FROM Product c WHERE c.id = :id and c.isDeleted = false", Product.class);
        query.setParameter("id", id);
        Product product = null;
        try {
            product = query.getSingleResult();
        } catch (Exception e) {
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
        } catch (Exception ex) {
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
        } catch (Exception ex) {
            logger.error("an error occurred at " + this.getClass());
        } finally {
            session.close();
        }
    }

    @Override
    public void deleteById(Long id) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        try {
            Transaction transaction = session.beginTransaction();
            Product product = session.get(Product.class, id);
            product.setDeleted(true);
            session.persist(product);
            transaction.commit();
        } catch (Exception ex) {
            logger.error("an error occurred at " + this.getClass());
        } finally {
            session.close();
        }
    }
}
