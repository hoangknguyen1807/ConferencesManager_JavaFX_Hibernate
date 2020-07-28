package com.confmanage.DAOs;

import com.confmanage.entities.Conference;
import hibernate.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Metamodel;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.metamodel.EntityType;
import java.util.List;

public class ConferenceDAO implements DAO<Conference> {

    @Override
    public List<Conference> getAll() {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        List<Conference> conferences = null;
        try {
            tx = session.beginTransaction();

            final Metamodel metamodel = session.getSessionFactory().getMetamodel();
            EntityType<?> entityType = metamodel.entity(Conference.class);
            final Query query = session.createQuery("from " + entityType.getName());

            conferences = query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return conferences;
    }

    @Override
    public Conference get(Integer id) {
        Session session = HibernateUtil.getSession();
        Conference conf = null;
        try {
            conf = session.get(Conference.class, id);
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return conf;
    }

    @Override
    public Integer save(Conference obj) {
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
    public void update(Conference obj) {
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
    public void delete(Conference obj) {
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
