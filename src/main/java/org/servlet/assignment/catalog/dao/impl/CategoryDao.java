package org.servlet.assignment.catalog.dao.impl;

import jakarta.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.servlet.assignment.catalog.Category;
import org.servlet.assignment.catalog.dao.Dao;
import org.servlet.assignment.configuration.HibernateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CategoryDao implements Dao<Category, Long> {

    private static final Logger logger = LoggerFactory.getLogger(CategoryDao.class);
    private ProductDao productDao = new ProductDao();

    @Override
    public List<Category> findAll(int limit, int offset) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Query<Category> query = session.createQuery("FROM Category where isDeleted = false", Category.class);
        List<Category> categories = null;
        try {
            categories = query.setFirstResult(offset).setMaxResults(limit).getResultList();
        } catch (NoResultException ex) {
            logger.error("an error occurred at " + this.getClass());
        } finally {
            session.close();
        }
        return categories;
    }

    @Override
    public Category findById(Long id) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Query<Category> query = session.createQuery("FROM Category c WHERE c.id = :id and c.isDeleted = false", Category.class);
        query.setParameter("id", id);
        Category category = null;
        try {
            category = query.getSingleResult();
        } catch (NoResultException e) {
            logger.error("an error occurred at " + this.getClass());
        } finally {
            session.close();
        }
        return category;
    }

    @Override
    public void save(Category entity) {
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
    public void updateById(Category patch, Long aLong) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        try {
            session.get(Category.class, aLong);
            patch.setId(aLong);
            session.save(patch);
        } catch (NoResultException ex) {
            logger.error("an error occurred at " + this.getClass());
        } finally {
            session.close();
        }
    }

    @Override
    public void deleteById(Long aLong) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        try {
            Transaction transaction = session.beginTransaction();
            Category category = session.get(Category.class, aLong);
            category.setDeleted(true);
            session.persist(category);
            transaction.commit();
        } catch (NoResultException ex) {
            logger.error("an error occurred at " + this.getClass());
        } finally {
            session.close();
        }
    }
}
