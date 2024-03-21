package org.servlet.assignment.catalog.service;

import org.servlet.assignment.catalog.Category;
import org.servlet.assignment.catalog.dao.impl.CategoryDao;

import java.util.List;

public class CategoryService {
    private static final CategoryDao categoryDao = new CategoryDao();

    public List<Category> findAllCategory(int limit, int offset) {
        return categoryDao.findAll(limit, offset);
    }

    public Category findById(long id) {
        return categoryDao.findById(id);
    }
}
