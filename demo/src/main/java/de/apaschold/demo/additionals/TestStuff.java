package de.apaschold.demo.additionals;

import de.apaschold.demo.logic.databasehandling.SqlManager;
import de.apaschold.demo.logic.databasehandling.SqlReader;

import java.sql.Connection;

public class TestStuff {



    public static void main(String[] args) throws Exception {
        Connection connection = SqlManager.getInstance().getSqliteDatabaseConnection("testdb");

        if (connection != null) {
            System.out.println("Connection to SQLite database established successfully.");
        } else {
            System.out.println("Failed to establish connection to SQLite database.");
        }
    }
}
