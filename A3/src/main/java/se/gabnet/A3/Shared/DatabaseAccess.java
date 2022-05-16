package Shared;

import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class DatabaseAccess {

    Connection dbConnection = null;

    public DatabaseAccess() {
        String dbName = "dbadmin";
        String dbPassword = "XEHjqXmGh2GYT2zfjJFkpQR8TQjjsk9aHPPiynUHYVqc5ycnf6jM5by2FFncgGY2Mr9UJvaQKFkxnhy8BUQ72ra3TCZmyYFV3mDoFuxLZC3zML6b6Cqp286wb5GmFupj";
        String url = "jdbc:postgresql://gabnet.se:5432/da379a_A3";

        try {
            Properties properties = new Properties();
            properties.put("user", dbName);
            properties.put("password", dbPassword);
            dbConnection = DriverManager.getConnection(url, properties);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getAllBooks() {
        return "BOOOOOOOOOOOOOOOOOOOOOOOOOKS";
    }

    public ArrayList<Book> f_get_books() {
        try {
            String query = "SELECT * FROM f_get_books()";
            PreparedStatement statement = dbConnection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            ArrayList<Book> bookList = new ArrayList<>();
            while (rs.next()) {
                Book book = new Book();
                book.setId      (rs.getInt      (1));
                book.setName    (rs.getString   (2));
                book.setAuthor  (rs.getString   (3));
                book.setYear    (rs.getInt      (4));
                bookList.add(book);
            }
            dbConnection.close();
            return bookList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void p_put_book(String name, String author, int year) {
        try {
            String query = "CALL p_put_book(?,?,?)";
            PreparedStatement statement = dbConnection.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, author);
            statement.setInt   (3, year);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
