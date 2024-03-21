package org.servlet.assignment.catalog.service;

import org.servlet.assignment.catalog.ProductSize;
import org.servlet.assignment.catalog.dao.impl.SizeDao;

import java.util.List;

public class SizeService {
    private final SizeDao sizeDao = new SizeDao();

    public List<ProductSize> findAllSize(int limit, int offset) {
        return sizeDao.findAll(limit, offset);
    }

    public void createSize(ProductSize size) {
        sizeDao.save(size);
    }

    public ProductSize findById(long id) {
        return sizeDao.findById(id);
    }
}
