package org.servlet.assignment.order.impl;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.servlet.assignment.configuration.HibernateUtils;
import org.servlet.assignment.generic.dao.Dao;
import org.servlet.assignment.generic.dao.GenericDao;
import org.servlet.assignment.order.Payment;
import org.servlet.assignment.order.Order;
import org.servlet.assignment.order.Payment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PaymentDao extends GenericDao<Payment> implements Dao<Payment, Long> {

    private static final Logger logger = LoggerFactory.getLogger(PaymentDao.class);

    @Override
    public List<Payment> findAll(int limit, int offset) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Query<Payment> query = session.createQuery("FROM Payment", Payment.class);
        List<Payment> payments = null;
        try {
            Transaction transaction = session.beginTransaction();
            payments = query.setFirstResult(offset).setMaxResults(limit).getResultList();
            transaction.commit();
        } catch (Exception ex) {
            logger.error("an error occurred at " + this.getClass());
        } finally {
            session.close();
        }
        return payments;
    }

    @Override
    public Payment findById(Long id) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Query<Payment> query = session.createQuery("FROM Payment c WHERE c.id = :id", Payment.class);
        query.setParameter("id", id);
        Payment payment = null;
        try {

            Transaction transaction = session.beginTransaction();
            payment = query.getSingleResult();
            transaction.commit();
        } catch (Exception e) {
            logger.error("an error occurred at " + this.getClass());
        } finally {
            session.close();
        }
        return payment;
    }

    public Payment findByOrderId(Long id) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Query<Payment> query = session.createQuery("FROM Payment c WHERE c.order.id = :id", Payment.class);
        query.setParameter("id", id);
        Payment payment = null;
        try {
            Transaction transaction = session.beginTransaction();
            payment = query.getSingleResult();
            transaction.commit();
        } catch (Exception e) {
            logger.error("an error occurred at " + this.getClass());
        } finally {
            session.close();
        }
        return payment;
    }

    public List<Payment> findAllPaymentByUserAndStatus(String username, String status, int limit, int offset) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Query<Payment> query = session.createQuery("FROM Payment c WHERE c.order.user.username = :username AND c.status = :status", Payment.class);
        query.setParameter("username", username);
        query.setParameter("status", status);
        List<Payment> payments = null;
        try {

            Transaction transaction = session.beginTransaction();
            payments = query.setMaxResults(limit).setFirstResult(offset).getResultList();
            transaction.commit();
        } catch (Exception e) {
            logger.error("an error occurred at " + this.getClass());
        } finally {
            session.close();
        }
        return payments;
    }

    @Override
    public void updateById(Payment patch, Long aLong) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        try {
            session.get(Payment.class, aLong);
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
            Payment payment = session.get(Payment.class, id);
            session.remove(payment);
            transaction.commit();
        } catch (Exception ex) {
            logger.error("an error occurred at " + this.getClass());
        } finally {
            session.close();
        }
    }
}
