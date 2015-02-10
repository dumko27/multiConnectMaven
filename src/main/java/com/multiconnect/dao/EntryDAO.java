package com.multiconnect.dao;

import com.multiconnect.mapping.Entry;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Novikov Dmitry
 */
public interface EntryDAO {
    /**
     * Добавление записи в таблицу.
     * 
     * @param entry
     * @throws SQLException 
     */
    public void addEnties(Entry entry) throws SQLException;
    
    public List<Entry> getEntries()  throws SQLException;
}
