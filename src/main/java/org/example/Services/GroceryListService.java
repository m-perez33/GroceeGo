package org.example.Services;

import org.example.Model.GroceryList;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class GroceryListService {
    private static final String API_BASE_URL = "http://localhost:8080/api/GroceryLists";
    private final RestTemplate restTemplate = new RestTemplate();

    private String authToken = null;


    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }




    public List<GroceryList> getGroceryLists() {
        GroceryList[] gl = null;
        List<GroceryList> groceryLists = null;

        try {
            gl = restTemplate.getForObject(API_BASE_URL, GroceryList[].class);
            groceryLists = Arrays.asList(gl);

        } catch (RestClientResponseException e) {
            System.out.println("wha  hoppen");
            System.out.println(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            System.out.println(e.getMessage());
        }
        return groceryLists ;

    }

    public GroceryList getGroceryListById(int id) {
        GroceryList gl = null;

        try {
            gl = restTemplate.getForObject(API_BASE_URL + "/" + id, GroceryList.class);

        } catch (RestClientResponseException e) {

            System.out.println(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            System.out.println(e.getMessage());
        }
        return gl;
    }

    public GroceryList createGrocery(GroceryList newGlist) {
        HttpEntity<GroceryList> entity = makeGroceryListEntity(newGlist);

        GroceryList returnedGList = null;
        try {
            returnedGList = restTemplate.postForObject(API_BASE_URL, entity, GroceryList.class);
        } catch (RestClientResponseException e) {
            System.out.println(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            System.out.println(e.getMessage());
        }
        return returnedGList;
    }

    public boolean deleteGroceryList(int glId) {
        boolean success = false;

        try {
            restTemplate.delete(API_BASE_URL + "/" + glId);
            success = true;
        } catch (RestClientResponseException e) {
            System.out.println(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            System.out.println(e.getMessage());
        }
        return success;
    }

    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }

    private HttpEntity<GroceryList> makeGroceryListEntity(GroceryList groceryList) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(groceryList, headers);
    }
}
