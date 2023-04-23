package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hibernate tutorial");

        Session session = Util.getSessionFactory().openSession();

        session.beginTransaction();

        User contactEntity = new User();

        contactEntity.setLastName("Adam");
        contactEntity.setName("Nick");
        contactEntity.setAge( (byte) 2);

        session.save(contactEntity);
        session.getTransaction().commit();

        session.close();
    }
}
