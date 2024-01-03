package org.example.DAO;

import org.example.Exception.DaoException;
import org.example.Model.ListEntry;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JdbcListEntryDao implements ListEntryDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcListEntryDao (DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<ListEntry> getListEntries() {
        List<ListEntry> listEntries = new ArrayList<>();
        String sql = "SELECT cost, quantity, list_entry_id, l.product_id, grocery_list_id, name \n" +
                "FROM list_entry l\n" +
                "JOIN product p ON p.product_id = l.product_id ";

        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()){
                ListEntry entry = mapRowToEntry(results);
                listEntries.add(entry);
            }
        }catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }

        return listEntries;
    }

    @Override
    public List<ListEntry> getListEntriesByListId(int id) {
        List<ListEntry> listEntries = new ArrayList<>();
        String sql = "SELECT cost, quantity, list_entry_id, l.product_id, grocery_list_id, name \n" +
                "FROM list_entry l\n" +
                "JOIN product p ON p.product_id = l.product_id " +
                "WHERE grocery_list_id = ? ";

        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
            while (results.next()){
                ListEntry entry = mapRowToEntry(results);
                listEntries.add(entry);
            }
        }catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }

        return listEntries;
    }

    @Override
    public ListEntry getListEntryById(int id) {
        ListEntry listEntry = null;
        String sql = "SELECT cost, quantity, list_entry_id, l.product_id, grocery_list_id, name \n" +
                "FROM list_entry l\n" +
                "JOIN product p ON p.product_id = l.product_id " +
                "WHERE list_entry_id = ? ";

        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
            if (results.next()){
                listEntry = mapRowToEntry(results);
            }
        }catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }

        return listEntry;
    }



    @Override
    public ListEntry createListEntry(ListEntry listEntry) {
        ListEntry newListEntry = null;
        String sql = "INSERT INTO list_entry (quantity, cost, grocery_list_id, product_id ) VALUES (?,?,?,?) RETURNING list_entry_id";
        try {
            int leID = jdbcTemplate.queryForObject(sql, int.class, listEntry.getQuantity(),listEntry.getCost(), listEntry.getListId(), listEntry.getProductId());
            newListEntry = getListEntryById(leID);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newListEntry;
    }

    @Override
    public ListEntry updatelistEntry(ListEntry listEntry) {
        ListEntry updatedEntry = null;
        String sql = "UPDATE list_entry SET quantity = ?, cost = ? WHERE list_entry_id = ?";
        try {
            int rowsAffected = jdbcTemplate.update(sql, listEntry.getQuantity(), listEntry.getCost(), listEntry.getListEntryId());
            if (rowsAffected == 0) {
                throw new DaoException("Zero rows affected, expected at least one");
            }
            updatedEntry = getListEntryById(listEntry.getListEntryId());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return updatedEntry;

    }

    @Override
    public int deleteListEntry(int id) {
        int numberOfRows = 0;
        String sql = "DELETE FROM list_entry WHERE list_entry_id = ?";
        try {
            numberOfRows = jdbcTemplate.update(sql, id);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return numberOfRows;
    }
    private ListEntry mapRowToEntry(SqlRowSet rs){
        ListEntry le = new ListEntry();
        le.setQuantity(rs.getDouble("quantity"));
        le.setCost(rs.getDouble("cost"));
        le.setListId(rs.getInt("grocery_list_id"));
        le.setProductId(rs.getInt("product_id"));
        le.setProductName(rs.getString("name"));
        le.setListEntryId(rs.getInt("list_entry_id"));
        return le;
    }
}
