package org.example.DAO;

import org.example.Model.Product;

public interface ProductDao {

    Product getProductById (int id);

    Product createProduct (Product product);

}
