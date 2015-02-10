package com.multiconnect.dao.impl;

import com.multiconnect.dao.EntryDAO;
import com.multiconnect.mapping.Entry;
import com.multiconnect.util.HibernateUtil;
import java.sql.SQLException;
import java.util.*;
import org.hibernate.Session;
import org.slf4j.*;

/**
 *
 * @author Novikov Dmitry
 */
public class EntryDAOImpl implements EntryDAO {

    static final Logger log = LoggerFactory.getLogger(EntryDAOImpl.class);

    @Override
    public void addEnties(Entry entry) throws SQLException {
        log.debug("addEnties entry={}.", entry);
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(entry);
            session.getTransaction().commit();
        } catch (Exception e) {
            log.error("Ошибка при вставке: {}.", e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public List<Entry> getEntries() throws SQLException {
        Session session = null;
        List<Entry> entries = new ArrayList<Entry>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            entries = session.createCriteria(Entry.class).list();
        } catch (Exception e) {
            log.error("Ошибка при получении: {}.", e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return entries;
    }

}
