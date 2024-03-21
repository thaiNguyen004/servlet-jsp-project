package org.servlet.assignment;

import org.hibernate.Session;
import org.servlet.assignment.configuration.HibernateUtils;

public class Test {
    public static void main(String[] args) {
        Session session = HibernateUtils.getSessionFactory().openSession();
    }
}
