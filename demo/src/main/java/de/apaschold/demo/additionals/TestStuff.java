package de.apaschold.demo.additionals;

import de.apaschold.demo.logic.databasehandling.SqlManager;
import de.apaschold.demo.logic.databasehandling.SqlReader;
import de.apaschold.demo.logic.databasehandling.SqlWriter;
import de.apaschold.demo.model.Citation;
import de.apaschold.demo.model.JournalArticle;
import de.apaschold.demo.model.Unpublished;

import java.sql.Connection;

public class TestStuff {



    public static void main(String[] args) throws Exception {
        //SqlWriter.createNewLibraryDatabase();

        //SqlWriter.createNewLibraryTableSqlite(AppTexts.SQLITE_TABLE_NAME_ALL_CITATIONS);

        //Citation newCit = new Unpublished();
        //Citation newJour = new JournalArticle();

        //SqlWriter.addNewCitationToLibraryTableSqlite(AppTexts.SQLITE_TABLE_NAME_ALL_CITATIONS, newCit);

        //SqlWriter.addNewCitationToLibraryTableSqlite(AppTexts.SQLITE_TABLE_NAME_ALL_CITATIONS, newJour);

        System.out.println(SqlReader.importCitationsFromLibraryTableSqlite(AppTexts.SQLITE_TABLE_NAME_ALL_CITATIONS));
    }
}
