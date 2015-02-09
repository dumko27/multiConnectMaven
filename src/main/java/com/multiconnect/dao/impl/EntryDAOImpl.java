package com.multiconnect.dao.impl;

import com.multiconnect.dao.EntryDAO;
import com.multiconnect.mapping.Entry;
import com.multiconnect.util.HibernateUtil;
import java.sql.SQLException;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Novikov Dmitry
 */
public class EntryDAOImpl implements EntryDAO {
    
    static final Logger log = LoggerFactory.getLogger(EntryDAOImpl.class);

    @Override
    public void addEnties(Entry entry) throws SQLException {
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
    
}
