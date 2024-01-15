package org.example.Services;

import org.example.Model.ListEntry;
import org.example.Model.Product;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class ProductService {


    private static final String API_BASE_URL = "http://localhost:8080/api/Product";
    private final RestTemplate restTemplate = new RestTemplate();

    private String authToken = null;

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public List<Product> getProducts() {
        Product[] p = null;
        List<Product> products = null;

        try {
            p = restTemplate.getForObject(API_BASE_URL, Product[].class);
            products = Arrays.asList(p);

        } catch (RestClientResponseException e) {
            System.out.println(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            System.out.println(e.getMessage());
        }
        return products;
    }

    public Product getProductyById(int id) {
        Product p = null;

        try {
            p = restTemplate.getForObject(API_BASE_URL + id, Product.class);

        } catch (RestClientResponseException e) {
            System.out.println(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            System.out.println(e.getMessage());
        }
        return p;
    }

    public Product createProduct(Product newProduct) {
        HttpEntity<Product> entity = makeProductEntity(newProduct);

        Product returnedProduct = null;
        try {
            returnedProduct = restTemplate.postForObject(API_BASE_URL, entity, Product.class);
        } catch (RestClientResponseException e) {
            System.out.println(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            System.out.println(e.getMessage());
        }
        return returnedProduct;
    }

    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }

    private HttpEntity<Product> makeProductEntity(Product product) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(product, headers);
    }

}
