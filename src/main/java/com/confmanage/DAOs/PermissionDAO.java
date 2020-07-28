package com.confmanage.DAOs;

import com.confmanage.entities.Permission;
import hibernate.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Metamodel;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.metamodel.EntityType;
import java.util.List;

public class PermissionDAO implements DAO<Permission> {

    @Override
    public List<Permission> getAll() {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        List<Permission> permissions = null;

        try {
            tx = session.beginTransaction();

            final Metamodel metamodel = session.getSessionFactory().getMetamodel();
            EntityType<?> entityType = metamodel.entity(Permission.class);
            final Query query = session.createQuery("from " + entityType.getName());

            permissions = query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return permissions;
    }

    @Override
    public Permission get(Integer id) {
        Session session = HibernateUtil.getSession();
        Permission permission = null;
        try {
            permission = session.get(Permission.class, id);
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return permission;
    }

    @Override
    public Integer save(Permission obj) {
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
    public void update(Permission obj) {
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
    public void delete(Permission obj) {
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
