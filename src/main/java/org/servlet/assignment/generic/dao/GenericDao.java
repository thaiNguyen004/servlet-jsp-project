package org.servlet.assignment.generic.dao;

import org.hibernate.Session;
import org.servlet.assignment.configuration.HibernateUtils;

import java.util.List;

public class GenericDao <T> {
    private Class<T> entityClazz;

    public GenericDao(Class<T> entityClazz) {
        this.entityClazz = entityClazz;
    }

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

    public List<T> getAll() {
        try {
            Session session = HibernateUtils.getSessionFactory().openSession();
            session.beginTransaction();
            List<T> entities = session.createQuery("from " + entityClazz.getName()).list();
            session.getTransaction().commit();
            return entities;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
