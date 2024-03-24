package org.servlet.assignment.order.impl;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.servlet.assignment.configuration.HibernateUtils;
import org.servlet.assignment.generic.dao.Dao;
import org.servlet.assignment.generic.dao.GenericDao;
import org.servlet.assignment.order.CartItem;
import org.slf4j.Logger;

import java.util.Collections;
import java.util.List;

public class CartDao extends GenericDao<CartItem> implements Dao<CartItem, Long> {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(CartDao.class); 
    
    @Override
    public List<CartItem> findAll(int limit, int offset) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Query<CartItem> query = session.createQuery("FROM CartItem", CartItem.class);
        List<CartItem> cartItems = null;
        try {
            Transaction transaction = session.beginTransaction();
            cartItems = query.setFirstResult(offset).setMaxResults(limit).getResultList();
            transaction.commit();
        } catch (Exception ex) {
            logger.error("an error occurred at " + this.getClass());
        } finally {
            session.close();
        }
        return cartItems;
    }

    public CartItem findCartItemByProductId(Long productId) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Query<CartItem> query = session.createQuery("FROM CartItem o WHERE o.product.id = :productId", CartItem.class);
        query.setParameter("productId", productId);
        CartItem cartItem = null;
        try {
            cartItem = query.getSingleResult();
        } catch (Exception e) {
            logger.error("an error occurred at " + this.getClass());
        } finally {
            session.close();
        }
        return cartItem;
    }

    @Override
    public CartItem findById(Long aLong) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Query<CartItem> query = session.createQuery("FROM CartItem o WHERE o.id = :id", CartItem.class);
        query.setParameter("id", aLong);
        CartItem cartItem = null;
        try {
            cartItem = query.getSingleResult();
        } catch (Exception e) {
            logger.error("an error occurred at " + this.getClass());
        } finally {
            session.close();
        }
        return cartItem;
    }

    public List<CartItem> findAllCartItemsByShoppingSessionId(Long shoppingSessionId, int limit, int offset) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Query<CartItem> query = session.createQuery("FROM CartItem o WHERE o.shoppingSession.id = :shoppingSessionId", CartItem.class);
        query.setParameter("shoppingSessionId", shoppingSessionId);
        List<CartItem> cartItems = null;
        try {
            Transaction transaction = session.beginTransaction();
            cartItems = query.setMaxResults(limit).setFirstResult(offset).getResultList();
            transaction.commit();
            return cartItems;
        } catch (Exception e) {
            logger.error("an error occurred at " + this.getClass());
        } finally {
            session.close();
        }
        return Collections.emptyList();
    }

    @Override
    public void updateById(CartItem patch, Long aLong) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        try {
            session.get(CartItem.class, aLong);
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
            CartItem cartItem = session.get(CartItem.class, aLong);
            session.remove(cartItem);
            transaction.commit();
        } catch (Exception ex) {
            logger.error("an error occurred at " + this.getClass());
        } finally {
            session.close();
        }
    }
}
