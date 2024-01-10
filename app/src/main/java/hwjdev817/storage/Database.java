package hwjdev817.storage;

import hwjdev817.prefs.Prefs;

import java.sql.*;

public class Database {
    private static final Database INSTANCE = new Database();
    private Connection connection;

    private Database() {
        String connectiondbUrl = new Prefs().getString(Prefs.DB_URL); // "jdbc:h2:./testdb";
        try {
            connection = DriverManager.getConnection(connectiondbUrl);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Database getInstance() {
        return INSTANCE;
    }

    public Connection getConnection() {
        return connection;
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int executeUpdate(String sql) {
        try (Statement st = connection.createStatement()) {
            return st.executeUpdate(sql);
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
    }
}