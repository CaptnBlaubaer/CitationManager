package de.apaschold.demo.additionals;

import de.apaschold.demo.logic.databasehandling.SqlReader;
import de.apaschold.demo.logic.databasehandling.SqlWriter;
import de.apaschold.demo.model.*;

public class TestStuff {



    public static void main(String[] args) throws Exception {
        String tableName = "my_new_library";

        System.out.println(SqlReader.importCitationsFromLibraryTable(tableName));

        Citation citation = new Unpublished(
                1,
                "Sample Title",
                "John Doe",
                2023,
                "/path/to/sample.pdf"
        );

        //SqlWriter.createNewLibraryTable(tableName);
        SqlWriter.addNewCitationToLibraryTable(tableName, citation);
        //SqlWriter.updateCitationInLibrary(tableName, citation);
    }
}
