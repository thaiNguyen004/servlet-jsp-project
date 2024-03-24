package org.servlet.assignment.order.controller.home;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.servlet.assignment.generic.dao.GenericDao;
import org.servlet.assignment.order.ActionMustLoggedInException;
import org.servlet.assignment.order.CartItemWithProductAlreadyExistException;
import org.servlet.assignment.order.DateTimeInvalidException;
import org.servlet.assignment.utils.ValidateUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

public abstract class GenericServlet<T> extends HttpServlet {
    private GenericDao<T> genericDao;

    public GenericServlet(GenericDao<T> genericDao) {
        this.genericDao = genericDao;
    }

    abstract void processRequest(HttpServletRequest request
            , HttpServletResponse response, T entity)
            throws IOException, ServletException;

    abstract void processDoGet(HttpServletRequest req
            , HttpServletResponse resp)
            throws ServletException, IOException;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processDoGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            T entity = createEntity(req);
            Map<String, String> violations = ValidateUtils.validate(entity);
            if (violations.isEmpty()) {
                genericDao.save(entity);
                processRequest(req, resp, entity);
            } else {
                handleErrors(req, resp, violations, entity);
            }
        } catch (ActionMustLoggedInException ex) {
            resp.sendRedirect("/login");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    abstract void handleErrors(HttpServletRequest req, HttpServletResponse resp, Map<String, String> violations, T entity) throws ServletException, IOException;

    abstract T createEntity(HttpServletRequest request) throws InvocationTargetException, IllegalAccessException, ServletException, IOException;
}
