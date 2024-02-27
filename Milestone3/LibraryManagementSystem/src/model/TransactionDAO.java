package model;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class TransactionDAO {
    Connection connection;
    public TransactionDAO(String url) throws SQLException {

        connection = DriverManager.getConnection(url);
    }
    // method to add a new transaction
    // needed when a book is issued
    public void insert(Transaction transaction) throws SQLException {
        LocalDate localDate = transaction.getIssueDate();
        java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);
        PreparedStatement ps = connection.prepareStatement("INSERT INTO libTransactions VALUES(?,?,?,?)");
        ps.setInt(1,transaction.getBookID());
        ps.setInt(2,transaction.getUserID());
        ps.setDate(3,sqlDate);
        ps.setBoolean(4, transaction.isStatus());
        ps.executeUpdate();
    }
    // method to update the transactions
    // needed when a book is returned
    public void update(Transaction transaction) throws SQLException {
        LocalDate localDate = transaction.getIssueDate();
        java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);
        PreparedStatement ps = connection.prepareStatement("UPDATE libTransactions SET status= ? WHERE bookID=? AND userID=?");
        ps.setBoolean(1, transaction.isStatus());
        ps.setInt(2, transaction.getBookID());
        ps.setInt(3, transaction.getUserID());
        ps.executeUpdate();
    }
    // method to get all transactions
    public ArrayList<Transaction> getAll() throws SQLException {
        ArrayList<Transaction> transactions = new ArrayList<>();
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM libTransactions");
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            java.sql.Date sqlDate = new java.sql.Date(new java.util.Date(rs.getLong(3)).getTime());
           LocalDate localDate = sqlDate.toLocalDate();
            Transaction temp = new Transaction(
                   rs.getInt(1),
                    rs.getInt(2),
                    localDate,
                    rs.getBoolean(4)
            );
            transactions.add(temp);

        }
        rs.close();
        return transactions;
    }



}
