package org.servlet.assignment.configuration;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import org.servlet.assignment.catalog.Category;
import org.servlet.assignment.catalog.Picture;
import org.servlet.assignment.catalog.Product;
import org.servlet.assignment.catalog.ProductSize;
import org.servlet.assignment.order.LineItem;
import org.servlet.assignment.order.Order;
import org.servlet.assignment.order.Payment;
import org.servlet.assignment.user.Address;
import org.servlet.assignment.user.User;

import java.util.Properties;

public class HibernateUtils {
    private static final SessionFactory SESSION_FACTORY;

    static {
        Configuration conf = new Configuration();
        Properties properties = new Properties();
        properties.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
        properties.put(Environment.URL, "jdbc:mysql://localhost:3306/asmjava4");
        properties.put(Environment.USER, "root");
        properties.put(Environment.PASS, "123456");
        properties.put(Environment.SHOW_SQL, true);
        properties.put(Environment.JAKARTA_HBM2DDL_DATABASE_ACTION, "drop-and-create");
        properties.put(Environment.JAKARTA_HBM2DDL_LOAD_SCRIPT_SOURCE, "data.sql");

        conf.setProperties(properties);
        conf.addAnnotatedClass(Product.class);
        conf.addAnnotatedClass(ProductSize.class);
        conf.addAnnotatedClass(Picture.class);
        conf.addAnnotatedClass(User.class);
        conf.addAnnotatedClass(Address.class);
        conf.addAnnotatedClass(Order.class);
        conf.addAnnotatedClass(LineItem.class);
        conf.addAnnotatedClass(Payment.class);
        conf.addAnnotatedClass(Category.class);

        ServiceRegistry registry = new StandardServiceRegistryBuilder()
                .applySettings(conf.getProperties()).build();
        SESSION_FACTORY = conf.buildSessionFactory(registry);
    }

    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }
}
