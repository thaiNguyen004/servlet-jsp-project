package org.servlet.assignment.catalog.service;

import org.servlet.assignment.catalog.Product;
import org.servlet.assignment.catalog.dao.impl.ProductDao;

import java.util.List;

public class ProductService {

    private final ProductDao productDao = new ProductDao();
    private final CategoryService categoryService = new CategoryService();

    public List<Product> findAllProducts(int limit, int offset) {
        return productDao.findAll(limit, offset);
    }

    public void create(Product product) {
        productDao.save(product);
    }

    public Product findById(Long id) {
        return productDao.findById(id);
    }

    public void deleteById(Long id) {
        productDao.deleteById(id);
    }
}
