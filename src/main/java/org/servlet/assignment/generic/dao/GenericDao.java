package org.servlet.assignment.generic.dao;

import org.hibernate.Session;
import org.servlet.assignment.configuration.HibernateUtils;

public abstract class GenericDao <T> implements Dao<T, Long> {

    public void save(T entity) {
        try {
            Session session = HibernateUtils.getSessionFactory().openSession();
            session.beginTransaction();
            session.persist(session.merge(entity));
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
