package com.confmanage.DAOs;

import com.confmanage.entities.User;
import hibernate.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Metamodel;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.metamodel.EntityType;
import java.util.List;

public class UserDAO implements DAO<User> {

    @Override
    public List<User> getAll() {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        List<User> users = null;
        try {
            tx = session.beginTransaction();

            final Metamodel metamodel = session.getSessionFactory().getMetamodel();
            EntityType<?> entityType = metamodel.entity(User.class);
            final Query query = session.createQuery("from " + entityType.getName());

            users = query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return users;
    }

    @Override
    public User get(Integer id) {
        Session session = HibernateUtil.getSession();
        User user = null;
        try {
            user = session.get(User.class, id);
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return user;
    }

    @Override
    public Integer save(User obj) {
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
    public void update(User obj) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
//            session.get(User.class, obj.getConfId());
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
    public void delete(User obj) {
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
