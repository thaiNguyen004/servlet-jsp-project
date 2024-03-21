package org.servlet.assignment.config;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.hibernate.Session;
import org.servlet.assignment.configuration.HibernateUtils;

@WebListener
public class ServletContextStartListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Session session = HibernateUtils.getSessionFactory().openSession();
    }

}
