package org.servlet.assignment.order.impl;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.servlet.assignment.configuration.HibernateUtils;
import org.servlet.assignment.generic.dao.Dao;
import org.servlet.assignment.generic.dao.GenericDao;
import org.servlet.assignment.order.Discount;
import org.servlet.assignment.order.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class DiscountDao extends GenericDao<Discount> implements Dao<Discount, Long> {

    private static final Logger logger = LoggerFactory.getLogger(DiscountDao.class);

    @Override
    public List<Discount> findAll(int limit, int offset) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Query<Discount> query = session.createQuery("FROM Discount where isDeleted = false", Discount.class);
        List<Discount> discounts = null;
        try {
            Transaction transaction = session.beginTransaction();
            discounts = query.setFirstResult(offset).setMaxResults(limit).getResultList();
            transaction.commit();
        } catch (Exception ex) {
            logger.error("an error occurred at " + this.getClass());
        } finally {
            session.close();
        }
        return discounts;
    }

    public List<Order> findOrdersByDiscountId(Long id) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Query<Order> query = session.createQuery("FROM Order c WHERE c.discount.id = :id and c.isDeleted = false", Order.class);
        query.setParameter("id", id);
        List<Order> orders = null;
        try {
            orders = query.getResultList();
        } catch (Exception e) {
            logger.error("an error occurred at " + this.getClass());
        } finally {
            session.close();
        }
        return orders;
    }


    @Override
    public Discount findById(Long id) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Query<Discount> query = session.createQuery("FROM Discount c WHERE c.id = :id and c.isDeleted = false", Discount.class);
        query.setParameter("id", id);
        Discount product = null;
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
    public void updateById(Discount patch, Long aLong) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        try {
            session.get(Discount.class, aLong);
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
            Discount product = session.get(Discount.class, id);
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
