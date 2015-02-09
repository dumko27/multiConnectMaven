package com.multiconnect;

import com.multiconnect.dao.EntryDAO;
import com.multiconnect.dao.impl.EntryDAOImpl;

/**
 *
 * @author Novikov Dmitry
 */
public class Factory {

    private static EntryDAO entryDAO = null;
    private static Factory instance = null;

    public static synchronized Factory getInstance() {
        if (instance == null) {
            instance = new Factory();
        }
        return instance;
    }

    public EntryDAO getEntryDAO() {
        if (entryDAO == null) {
            entryDAO = new EntryDAOImpl();
        }
        return entryDAO;
    }

}
