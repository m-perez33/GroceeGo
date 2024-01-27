package org.example.Services;

import org.example.Model.GroceryList;
import org.example.Model.ListEntry;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class ListEntryService {

    private static final String API_BASE_URL = "http://localhost:8080/api/listEntry";
    private final RestTemplate restTemplate = new RestTemplate();

    private String authToken = null;

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

   /* public List<ListEntry> getListEntries() {
        ListEntry[] le = null;
        List<ListEntry> listEntries = null;

        try {
            le = restTemplate.getForObject(API_BASE_URL, ListEntry[].class);
            listEntries = Arrays.asList(le);

        } catch (RestClientResponseException e) {
            System.out.println(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            System.out.println(e.getMessage());
        }
        return listEntries;
*/


    public List<ListEntry> getListEntriesByListId(int id) {
        ListEntry[] le = null;
        List<ListEntry> listEntries = null;

        try {
            le = restTemplate.getForObject(API_BASE_URL + "/list/" + id, ListEntry[].class);
            listEntries = Arrays.asList(le);

        } catch (RestClientResponseException e) {
            System.out.println(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            System.out.println(e.getMessage());
        }
        return listEntries;
    }

    public ListEntry getListEntryById(int id) {
        ListEntry le = null;

        try {
            le = restTemplate.getForObject(API_BASE_URL + "/" + id, ListEntry.class);

        } catch (RestClientResponseException e) {
            System.out.println(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            System.out.println(e.getMessage());
        }
        return le;
    }


    public ListEntry createListEntry(ListEntry newEntry) {
        HttpEntity<ListEntry> entity = makeListEntryEntity(newEntry);

        ListEntry returnedEntry = null;
        try {
            returnedEntry = restTemplate.postForObject(API_BASE_URL, entity, ListEntry.class);
        } catch (RestClientResponseException e) {
            System.out.println(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            System.out.println(e.getMessage());
        }
        return returnedEntry;
    }

    public boolean updateListEntry(ListEntry updatedListEntry) {
        HttpEntity<ListEntry> entity = makeListEntryEntity(updatedListEntry);

        boolean success = false;
        try {
            restTemplate.put(API_BASE_URL + "/" + updatedListEntry.getListEntryId(), entity);
            success = true;
        } catch (RestClientResponseException e) {
            System.out.println(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            System.out.println(e.getMessage());
        }
        return success;
    }

    public boolean deleteListEntry(int glId) {
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

    private HttpEntity<ListEntry> makeListEntryEntity(ListEntry listEntry) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(listEntry, headers);
    }
}
























