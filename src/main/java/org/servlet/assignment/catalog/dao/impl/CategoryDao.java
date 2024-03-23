package org.servlet.assignment.catalog.dao.impl;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.servlet.assignment.catalog.Category;
import org.servlet.assignment.generic.dao.Dao;
import org.servlet.assignment.configuration.HibernateUtils;
import org.servlet.assignment.generic.dao.GenericDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CategoryDao extends GenericDao<Category> implements Dao<Category, Long> {

    private static final Logger logger = LoggerFactory.getLogger(CategoryDao.class);
    private ProductDao productDao = new ProductDao();

    @Override
    public List<Category> findAll(int limit, int offset) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Query<Category> query = session.createQuery("FROM Category where isDeleted = false", Category.class);
        List<Category> categories = null;
        try {
            categories = query.setFirstResult(offset).setMaxResults(limit).getResultList();
        } catch (Exception ex) {
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
        } catch (Exception e) {
            logger.error("an error occurred at " + this.getClass());
        } finally {
            session.close();
        }
        return category;
    }


    @Override
    public void updateById(Category patch, Long aLong) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        try {
            session.get(Category.class, aLong);
            patch.setId(aLong);
            session.save(patch);
        } catch (Exception ex) {
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
            session.createQuery("update Category c set c.isDeleted = true where c.id = :id")
                    .setParameter("id", aLong)
                    .executeUpdate();
            session.createQuery("update Product p set p.category = null where p.category.id = :id")
                    .setParameter("id", aLong)
                    .executeUpdate();
            session.persist(category);
            transaction.commit();
        } catch (Exception ex) {
            logger.error("an error occurred at " + this.getClass());
        } finally {
            session.close();
        }
    }

    public void deleteProductById(Long productId, Long categoryId) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        try {
            Transaction transaction = session.beginTransaction();
            session.createQuery("UPDATE Product p SET p.category = null WHERE p.category.id = :categoryId and p.id = :productId")
                    .setParameter("categoryId", categoryId)
                    .setParameter("productId", productId)
                    .executeUpdate();
            transaction.commit();
        } catch (Exception ex) {
            logger.error("an error occurred at " + this.getClass());
        } finally {
            session.close();
        }
    }
}
