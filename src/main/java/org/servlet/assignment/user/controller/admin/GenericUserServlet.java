package org.servlet.assignment.user.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.servlet.assignment.generic.dao.GenericDao;
import org.servlet.assignment.user.UsernameAlreadyExistException;
import org.servlet.assignment.utils.ValidateUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public abstract class GenericUserServlet<T> extends HttpServlet {
    private GenericDao<T> genericDao;

    public GenericUserServlet(GenericDao<T> genericDao) {
        this.genericDao = genericDao;
    }

    abstract void processRequest(HttpServletRequest request
            , HttpServletResponse response, T entity)
            throws IOException;

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
        Map<String, String> violations = new HashMap<>();
        try {
            T entity = createEntity(req);
            violations = ValidateUtils.validate(entity);
            if (violations.isEmpty()) {
                genericDao.save(entity);
                processRequest(req, resp, entity);
            } else {
                handleErrors(req, resp, violations, entity);
            }
        } catch (UsernameAlreadyExistException | InvocationTargetException | IllegalAccessException e) {
            violations.put("username", "Username already exist");
            handleErrors(req, resp, violations, null);
        }
    }

    abstract void handleErrors(HttpServletRequest req, HttpServletResponse resp, Map<String, String> violations, T entity) throws ServletException, IOException;

    abstract T createEntity(HttpServletRequest request) throws InvocationTargetException, IllegalAccessException, ServletException, IOException;
}
