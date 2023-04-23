package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.logging.Logger;

public class UserDaoHibernateImpl implements UserDao {
    private static final Logger LOGGER =  Logger.getLogger(UserDaoHibernateImpl.class.getName());
    private static final SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        String sql = " CREATE DATABASE IF NOT EXISTS data_Hibernate";
        transaction.commit();
        session.close();
    }

    public void createUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        String sql = "CREATE TABLE IF NOT EXISTS users " +
                "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, " +
                "age TINYINT NOT NULL)";
        session.createSQLQuery(sql).addEntity(User.class).executeUpdate();

        transaction.commit();
        session.close();
    }

    public void dropUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.createSQLQuery("drop table if EXISTS users").executeUpdate();
        transaction.commit();
        session.close();

    }

    public void saveUser(String name, String lastName, byte age) {
            Session session = sessionFactory.getCurrentSession();
            User user = new User(name, lastName, age);
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            session.close();
    }

    public void removeUserById(long id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        String sql = "delete User where id = '{id}'".replace("{id}", String.valueOf(id));
        session.createQuery( sql ).executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    public List<User> getAllUsers() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        List<User> allUsers = session.createQuery( "from " + User.class.getSimpleName()).getResultList();
        session.getTransaction().commit();
        session.close();
        return allUsers;
    }

    public void cleanUsersTable() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();


        String stringQuery = "DELETE FROM " + User.class.getSimpleName();
        Query query = session.createQuery(stringQuery);
        query.executeUpdate();

        session.getTransaction().commit();
        session.close();
    }
}
