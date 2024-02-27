package model;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class BookDAO {
    Connection connection = null;

    // establish a connection to the db
    public BookDAO(String url) throws SQLException {

        connection = DriverManager.getConnection(url);
    }

    // method to insert a new book into the database
    public void insert(Book book) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("INSERT INTO BOOKS VALUES(?,?,?,?,?,?,?)");
        ps.setString(2,book.getName());
        ps.setString(3,book.getAuthor());
        ps.setString(4,book.getPublisher());
        ps.setString(5,book.getGenre());
        ps.setString(6, book.getISBN());
        ps.setLong(7, book.getYear());
        ps.executeUpdate();
    }


   // method that gets all books from the database
    public ArrayList<Book> getAll() throws SQLException {
        ArrayList<Book> books = new ArrayList<>();
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM books");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Book temp = new Book(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getLong(7)
            );
            books.add(temp);

        }
        rs.close();
        return books;
    }


}
