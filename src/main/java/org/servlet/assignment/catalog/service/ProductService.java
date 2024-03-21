package org.servlet.assignment.catalog.service;

import org.servlet.assignment.catalog.Picture;
import org.servlet.assignment.catalog.Product;
import org.servlet.assignment.catalog.dao.impl.ProductDao;

import java.util.List;

public class ProductService {

    private final ProductDao productDao = new ProductDao();

    public List<Product> findAllProducts(int limit, int offset) {
        return productDao.findAll(limit, offset);
    }

    public List<Picture> findAllPicturesByProductId(Long id, int limit, int offset) {
        return productDao.findAllPicturesByProductId(id, offset, limit);
    }

    public void create(Product product) {
        productDao.save(product);
    }
}
