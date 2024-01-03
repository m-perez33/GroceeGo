package org.example.DAO;

import org.example.Model.Product;

import java.util.List;

public interface ProductDao {

    List<Product> getProducts();

    Product getProductById (int id);

    Product createProduct (Product product);

}
