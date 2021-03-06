package org.kodejava.example.sql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MaxConcurrentConnection {
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost/kodejava";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public static void main(String[] args) throws Exception {
        Connection connection = null;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            DatabaseMetaData metadata = connection.getMetaData();

            //
            // Get the information of maximum concurrent connection to the
            // database. The maximum number of active connections possible at
            // one time; a result of zero means that there is no limit or the
            // limit is not known
            //
            int maxConnection = metadata.getMaxConnections();
            System.out.println("Maximum Connection = " + maxConnection);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
}
