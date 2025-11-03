package de.apaschold.demo.additionals;

import de.apaschold.demo.logic.databasehandling.SqlManager;
import de.apaschold.demo.logic.databasehandling.SqlReader;
import de.apaschold.demo.logic.databasehandling.SqlWriter;

import java.sql.Connection;

public class TestStuff {



    public static void main(String[] args) throws Exception {
        SqlWriter.createNewLibraryDatabase();

        SqlWriter.createNewLibraryTableSqlite(AppTexts.SQLITE_TABLE_NAME_ALL_CITATIONS);
    }
}
