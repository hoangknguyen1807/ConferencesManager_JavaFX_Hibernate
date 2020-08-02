package com.confmanage.DAOs;

import com.confmanage.entities.Conference_User;
import hibernate.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Metamodel;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.metamodel.EntityType;
import java.util.List;

public class Conference_UserDAO implements DAO<Conference_User> {
    @Override
    public List<Conference_User> getAll() {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        List<Conference_User> conference_userList = null;
        try {
            tx = session.beginTransaction();

            final Metamodel metamodel = session.getSessionFactory().getMetamodel();
            EntityType<?> entityType = metamodel.entity(Conference_User.class);
            final Query query = session.createQuery("from " + entityType.getName());

            conference_userList = query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return conference_userList;
    }

    @Override
    public Conference_User get(Integer id) {
        Session session = HibernateUtil.getSession();
        Conference_User cu = null;
        try {
            cu = session.get(Conference_User.class, id);
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return cu;
    }

    @Override
    public Integer save(Conference_User obj) {
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
    public void update(Conference_User obj) {
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
    public void delete(Conference_User obj) {
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
