package de.apaschold.demo.logic.databasehandling;

import de.apaschold.demo.additionals.AppTexts;
import de.apaschold.demo.gui.GuiController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLNonTransientConnectionException;

public class SqlManager {
    //0. constants
    private static final String DB_LOCAL_SERVER_IP_ADDRESS = "localhost/";
    private static final String DB_LOCAL_NAME = "citation_manager";

    private static final String SQLITE_DB_LOCAL_CONNECTION_URL_TEMPLATE =
            "jdbc:sqlite:%s";

    private static final String DB_LOCAL_CONNECTION_URL =
            "jdbc:mysql://" + DB_LOCAL_SERVER_IP_ADDRESS + DB_LOCAL_NAME;

    private static final String DB_LOCAL_USER_NAME = "java";
    private static final String DB_LOCAL_USER_PW = "password";
    //endregion

    //1. attributes
    private static SqlManager instance;
    //endregion

    //2. constructors
    private SqlManager() {
    }
    //endregion

    //3. getInstance Method
    public static synchronized SqlManager getInstance() {
        if (instance == null) {
            instance = new SqlManager();
        }
        return instance;
    }
    //endregion

    //4. read and write connection to database
    /**
     * Gets a connection to the local database.
     * First, it attempts to establish a connection and then closes it immediately, to check if the database is reachable.
     *
     * @return a Connection object to the database
     * @throws SQLException if a database access error occurs
     */
    public Connection getDatabaseConnection() throws SQLException {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(DB_LOCAL_CONNECTION_URL, DB_LOCAL_USER_NAME, DB_LOCAL_USER_PW);
            connection.close();

            connection = DriverManager.getConnection(DB_LOCAL_CONNECTION_URL, DB_LOCAL_USER_NAME, DB_LOCAL_USER_PW);
        } catch (SQLNonTransientConnectionException e) {
            System.err.println(AppTexts.ERROR_CONNECTING_TO_DATABASE + DB_LOCAL_CONNECTION_URL);
            e.printStackTrace();
        }

        return connection;
    }

    public Connection getSqliteDatabaseConnection() throws SQLException {
        //String dbFilePath = "C:\\Users\\Hein\\Desktop\\Programmierstuff\\JAva\\CitationManagerTestLibrary\\test.db";
        String dbFilePath = GuiController.getInstance().getActiveLibraryFilePath();

        String sqliteConnectionUrl = String.format(SQLITE_DB_LOCAL_CONNECTION_URL_TEMPLATE, dbFilePath);
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(sqliteConnectionUrl);
            connection.close();

            connection = DriverManager.getConnection(sqliteConnectionUrl);
        } catch (SQLNonTransientConnectionException e) {
            System.err.println(AppTexts.ERROR_CONNECTING_TO_DATABASE + sqliteConnectionUrl);
            e.printStackTrace();
        }

        return connection;
    }
}
