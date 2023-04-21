package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class UserDaoJDBCImpl implements UserDao {
    private static final Logger LOGGER =  Logger.getLogger(UserDaoJDBCImpl.class.getName());
    private final Connection connection;
    private final Statement statement;
    public UserDaoJDBCImpl() {
        try {
            connection = Util.getConnection();
            statement = connection.createStatement();
            statement.execute( " CREATE DATABASE IF NOT EXISTS data");
            statement.execute( "use data");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createUsersTable()  {
        try {
            statement.execute("CREATE TABLE IF NOT EXISTS `UsersSQL` (" +
                    " id bigint default '0'," +
                    " userName varchar(255)," +
                    " lastName varchar(255)," +
                    " age tinyint," +
                    " PRIMARY KEY (id) )");
        } catch (SQLException e) {
            LOGGER.warning( " Не удалось создать таблицу ");
        }
    }
    @Override
    public void dropUsersTable() {
        try {
            statement.execute("DROP TABLE IF EXISTS UsersSQL");
        } catch (SQLException e) {
            LOGGER.warning( " Не удалось удалить таблицу ");
        }
    }
    @Override
    public void saveUser(String name, String lastName, byte age){
        String sql = "INSERT INTO UsersSQL ( id,userName, lastName , age) VALUES (?,?,?,?)";
        try {
            ResultSet res = statement.executeQuery("select count(*) from UsersSQL");
            res.next();
            var id = res.getLong(1);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, lastName);
            preparedStatement.setByte(4, age);
            preparedStatement.executeUpdate();
            System.out.println( String.format( " User с именем %s был добавлен в таблицу " , name));
        } catch ( SQLException e) {
            LOGGER.warning( " Не удалось внести данные в таблицу ");
        }
    }
    @Override
    public void removeUserById(long id){
        String sql = "DELETE from UsersSQL where id = " + ( id - 1 );
        try {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.executeUpdate();
        } catch ( SQLException e ) {

            LOGGER.warning( " Не удалось удалить запись в  таблице ");
        }
    }
    @Override
    public List<User> getAllUsers(){
        String sql = "SELECT * FROM UsersSQL ORDER BY 'id' DESC";
        try {
        ResultSet res = statement.executeQuery(sql);
        List<User> users = new ArrayList<>();
        while (res.next()) {
            users.add( new User ( res.getString(2), res.getString(3),res.getByte(4)) );
        }
        return users;
        } catch ( SQLException e) {
            LOGGER.warning( " Не удалось получить данные из таблицы ");
            return null;
        }
    }
    @Override
    public void cleanUsersTable(){
        String sql = "DELETE FROM UsersSQL";
        try {
            statement.execute(sql);
        } catch (SQLException e) {
            LOGGER.warning( " Не удалось отчистить таблицу ");
        }
    }
}