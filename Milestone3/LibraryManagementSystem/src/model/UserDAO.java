package model;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class UserDAO {
    Connection connection = null;
    // establish a connection to the db
    public UserDAO(String url) throws SQLException {
        connection = DriverManager.getConnection(url);
    }

    // method to insert a new user into the database
    public void insert(User user) throws SQLException {
        LocalDate localDate = user.getDateOfBirth();
        java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);
        PreparedStatement ps = connection.prepareStatement("INSERT INTO users VALUES(?,?,?,?,?,?,?)");
        ps.setString(2, user.getName());
        ps.setString(3, user.getEmail());
        ps.setString(4, user.getAddress());
        ps.setDate(5, sqlDate);
        ps.setBoolean(6, user.getStudent());
        ps.setDouble(7, user.getBalance());
        ps.executeUpdate();
    }
    // method to get all users from the database
    public ArrayList<User> getAll() throws SQLException {
        ArrayList<User> users = new ArrayList<>();
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM users");
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            java.sql.Date sqlDate = new java.sql.Date(new Date(rs.getLong(5)).getTime());
            LocalDate localDate = sqlDate.toLocalDate();
            User temp = new User(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    localDate,
                    rs.getBoolean(6),
                    rs.getDouble(7)
            );
            users.add(temp);
        }
        rs.close();
        return users;
    }

}
