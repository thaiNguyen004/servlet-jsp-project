package org.servlet.assignment.order.impl;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.servlet.assignment.configuration.HibernateUtils;
import org.servlet.assignment.generic.dao.Dao;
import org.servlet.assignment.generic.dao.GenericDao;
import org.servlet.assignment.order.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

public class OrderDao extends GenericDao<Order> implements Dao<Order, Long> {
    private static final Logger logger = LoggerFactory.getLogger(OrderDao.class);

    @Override
    public List<Order> findAll(int limit, int offset) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Query<Order> query = session.createQuery("FROM Order where isDeleted = false", Order.class);
        List<Order> orders = null;
        try {
            Transaction transaction = session.beginTransaction();
            orders = query.setFirstResult(offset).setMaxResults(limit).getResultList();
            transaction.commit();
        } catch (Exception ex) {
            logger.error("an error occurred at " + this.getClass());
        } finally {
            session.close();
        }
        return orders;
    }

    @Override
    public Order findById(Long aLong) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Query<Order> query = session.createQuery("FROM Order o WHERE o.id = :id and o.isDeleted = false", Order.class);
        query.setParameter("id", aLong);
        Order order = null;
        try {
            order = query.getSingleResult();
        } catch (Exception e) {
            logger.error("an error occurred at " + this.getClass());
        } finally {
            session.close();
        }
        return order;
    }

    public List<Order> findAllOrdersByUsername(String username, int limit, int offset) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Query<Order> query = session.createQuery("FROM Order o WHERE o.user.username = :username and o.isDeleted = false", Order.class);
        query.setParameter("username", username);
        List<Order> orders = null;
        try {
            Transaction transaction = session.beginTransaction();
            orders = query.setMaxResults(limit).setFirstResult(offset).getResultList();
            transaction.commit();
            return orders;
        } catch (Exception e) {
            logger.error("an error occurred at " + this.getClass());
        } finally {
            session.close();
        }
        return Collections.emptyList();
    }

    @Override
    public void updateById(Order patch, Long aLong) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        try {
            session.get(Order.class, aLong);
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
            Order order = session.get(Order.class, aLong);
            order.setDeleted(true);
            session.persist(order);
            transaction.commit();
        } catch (Exception ex) {
            logger.error("an error occurred at " + this.getClass());
        } finally {
            session.close();
        }
    }
}
