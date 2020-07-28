package com.confmanage.DAOs;

import com.confmanage.entities.Venue;
import hibernate.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Metamodel;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.metamodel.EntityType;
import java.util.List;

public class VenueDAO implements DAO<Venue> {
    @Override
    public List<Venue> getAll() {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        List<Venue> venues = null;
        try {
            tx = session.beginTransaction();

            final Metamodel metamodel = session.getSessionFactory().getMetamodel();
            EntityType<?> entityType = metamodel.entity(Venue.class);
            final Query query = session.createQuery("from " + entityType.getName());

            venues = query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return venues;
    }

    @Override
    public Venue get(Integer id) {
        Session session = HibernateUtil.getSession();
        Venue venue = null;
        try {
            venue = session.get(Venue.class, id);
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return venue;
    }

    @Override
    public Integer save(Venue obj) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        Integer id = -1;

        try {
            tx = session.beginTransaction();
            id = (Integer) session.save(obj);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return id;
    }

    @Override
    public void update(Venue obj) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
//            session.get(Conference.class, obj.getConfId());
            session.update(obj);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void delete(Venue obj) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
//            session.get(Conference.class, obj.getConfId());
            session.delete(obj);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
