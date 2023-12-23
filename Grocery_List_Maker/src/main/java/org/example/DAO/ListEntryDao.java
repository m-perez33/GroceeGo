package org.example.DAO;

import org.example.Model.ListEntry;

import java.util.List;

public interface ListEntryDao {

    List<ListEntry> getListEntries();

    ListEntry getListEntryById(int id);

    ListEntry createListEntry(ListEntry listEntry);

    ListEntry updatelistEntry(ListEntry listEntry);

    int deleteListEntry(int id);


}
