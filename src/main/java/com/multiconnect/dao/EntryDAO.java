package com.multiconnect.dao;

import com.multiconnect.mapping.Entry;
import java.sql.SQLException;

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
}
