package org.servlet.assignment.catalog.service;

import org.servlet.assignment.catalog.Category;
import org.servlet.assignment.catalog.Product;
import org.servlet.assignment.catalog.dao.impl.CategoryDao;
import org.servlet.assignment.catalog.dao.impl.ProductDao;

import java.util.List;

public class CategoryService {
    private static final CategoryDao categoryDao = new CategoryDao();
    private static final ProductDao productDao = new ProductDao();

    public List<Category> findAllCategory(int limit, int offset) {
        return categoryDao.findAll(limit, offset);
    }

    public Category findById(long id) {
        return categoryDao.findById(id);
    }

    public List<Product> findAllProductById(long categoryId, int limit, int offset) {
        return productDao.findAllByCategoryId(categoryId, limit, offset);
    }

    public void create(Category category) {
        categoryDao.save(category);
    }
}
