package org.example.DAO;

import org.example.Model.ListEntry;

import java.util.List;

public interface ListEntryDao {

    List<ListEntry> getListEntries();

    List<ListEntry> getListEntriesByListId(int id);

    ListEntry getListEntryById(int id);

    ListEntry createListEntry(ListEntry listEntry);

    ListEntry updatelistEntry(ListEntry listEntry);

    int deleteListEntry(int id);


}
