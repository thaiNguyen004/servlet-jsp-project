package org.servlet.assignment.order.impl;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.servlet.assignment.configuration.HibernateUtils;
import org.servlet.assignment.generic.dao.Dao;
import org.servlet.assignment.generic.dao.GenericDao;
import org.servlet.assignment.order.CartItem;
import org.servlet.assignment.order.ShoppingSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ShoppingSessionDao extends GenericDao<ShoppingSession> implements Dao<ShoppingSession, Long> {
    private static final Logger logger = LoggerFactory.getLogger(ShoppingSessionDao.class);

    @Override
    public List<ShoppingSession> findAll(int limit, int offset) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Query<ShoppingSession> query = session.createQuery("FROM ShoppingSession", ShoppingSession.class);
        List<ShoppingSession> shoppingSessions = null;
        try {
            Transaction transaction = session.beginTransaction();
            shoppingSessions = query.setFirstResult(offset).setMaxResults(limit).getResultList();
            transaction.commit();
        } catch (Exception ex) {
            logger.error("an error occurred at " + this.getClass());
        } finally {
            session.close();
        }
        return shoppingSessions;
    }

    public void saveCartItem(CartItem cartItem, Long shoppingSessionId) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        try {
            Transaction transaction = session.beginTransaction();
            ShoppingSession shoppingSession = session.get(ShoppingSession.class, shoppingSessionId);
            shoppingSession.getCartItems().add(cartItem);
            session.persist(shoppingSession);
            transaction.commit();
        } catch (Exception ex) {
            logger.error("an error occurred at " + this.getClass());
        } finally {
            session.close();
        }
    }

    @Override
    public ShoppingSession findById(Long aLong) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Query<ShoppingSession> query = session.createQuery("FROM ShoppingSession o WHERE o.id = :id", ShoppingSession.class);
        query.setParameter("id", aLong);
        ShoppingSession shoppingSession = null;
        try {
            shoppingSession = query.getSingleResult();
        } catch (Exception e) {
            logger.error("an error occurred at " + this.getClass());
        } finally {
            session.close();
        }
        return shoppingSession;
    }

    public ShoppingSession findByUsername(String username) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Query<ShoppingSession> query = session.createQuery("FROM ShoppingSession o WHERE o.user.username = :username", ShoppingSession.class);
        query.setParameter("username", username);
        ShoppingSession shoppingSession = null;
        try {
            shoppingSession = query.getSingleResult();
        } catch (Exception e) {
            logger.error("an error occurred at " + this.getClass());
        } finally {
            session.close();
        }
        return shoppingSession;
    }

    @Override
    public void updateById(ShoppingSession patch, Long aLong) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        try {
            session.get(ShoppingSession.class, aLong);
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
        // Not implemented
    }
}
