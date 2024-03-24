package org.servlet.assignment.user.dao.impl;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.servlet.assignment.configuration.HibernateUtils;
import org.servlet.assignment.generic.dao.Dao;
import org.servlet.assignment.generic.dao.GenericDao;
import org.servlet.assignment.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class UserDao extends GenericDao<User> implements Dao<User, Long> {
    private static final Logger logger = LoggerFactory.getLogger(UserDao.class);

    @Override
    public List<User> findAll(int limit, int offset) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Query<User> query = session.createQuery("FROM User where isDeleted = false", User.class);
        List<User> users = null;
        try {
            Transaction transaction = session.beginTransaction();
            users = query.setFirstResult(offset).setMaxResults(limit).getResultList();
            transaction.commit();
        } catch (Exception ex) {
            logger.error("an error occurred at " + this.getClass());
        } finally {
            session.close();
        }
        return users;
    }

    @Override
    public User findById(Long aLong) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Query<User> query = session.createQuery("FROM User u WHERE u.id = :id and isDeleted = false", User.class);
        query.setParameter("id", aLong);
        User user = null;
        try {
            user = query.getSingleResult();
        } catch (Exception e) {
            logger.error("an error occurred at " + this.getClass());
        } finally {
            session.close();
        }
        return user;
    }

    public User findByUsername(String username) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Query<User> query = session.createQuery("FROM User u WHERE u.username = :username and isDeleted = false and lock = false", User.class);
        query.setParameter("username", username);
        User user = null;
        try {
            user = query.getSingleResult();
        } catch (Exception e) {
            logger.error("an error occurred at " + this.getClass());
        } finally {
            session.close();
        }
        return user;
    }

    @Override
    public void updateById(User patch, Long aLong) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        try {
            session.get(User.class, aLong);
            patch.setId(aLong);
            session.save(patch);
        } catch (Exception ex) {
            logger.error("an error occurred at " + this.getClass());
        } finally {
            session.close();
        }
    }

    public void unlockUserById(Long id) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        try {
            Transaction transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            user.setLock(false);
            session.persist(user);
            transaction.commit();
        } catch (Exception ex) {
            logger.error("an error occurred at " + this.getClass());
        } finally {
            session.close();
        }
    }

    public void lockUserById(Long id) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        try {
            Transaction transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            user.setLock(true);
            session.persist(user);
            transaction.commit();
        } catch (Exception ex) {
            logger.error("an error occurred at " + this.getClass());
        } finally {
            session.close();
        }
    }

    public void deactivateUserById(Long id) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        try {
            Transaction transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            user.setActive(false);
            session.persist(user);
            transaction.commit();
        } catch (Exception ex) {
            logger.error("an error occurred at " + this.getClass());
        } finally {
            session.close();
        }
    }

    public void activateUserById(Long id) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        try {
            Transaction transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            user.setActive(true);
            session.persist(user);
            transaction.commit();
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
            User user = session.get(User.class, aLong);
            user.setDeleted(true);
            session.persist(user);
            transaction.commit();
        } catch (Exception ex) {
            logger.error("an error occurred at " + this.getClass());
        } finally {
            session.close();
        }
    }
}
