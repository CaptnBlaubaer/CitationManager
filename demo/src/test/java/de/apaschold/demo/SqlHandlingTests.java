package de.apaschold.demo;


import de.apaschold.demo.gui.GuiController;
import de.apaschold.demo.logic.databasehandling.SqlManager;
import de.apaschold.demo.logic.databasehandling.SqlReader;
import de.apaschold.demo.logic.filehandling.TextFileHandler;
import de.apaschold.demo.model.Citation;
import de.apaschold.demo.model.PhdThesis;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SqlHandlingTests {
    //0. constants
    private static final String TEST_LIBRARY_PATH = "src/test/resources/de/apaschold/demo/data/active-library.txt";

    private static final int EXPECTED_CITATION_COUNT = 6;

    private static final PhdThesis CITATION_EXAMPLE =
            new PhdThesis(
                    1, "Auswirkungen von Tabakkonsum auf die Spermien-Eileiter-Interaktion und die männliche Fertilität",
                    "Paschold, Rick",
                    2022,
                    "",
                    "");

    @BeforeAll
    static void setup(){
        TextFileHandler.setActiveLibraryFilePath(TEST_LIBRARY_PATH);
        GuiController.getInstance();
    }

    @Test
    void testSqlConnection(){
        try {
            SqlManager.getInstance().getSqliteDatabaseConnection();
            assert true;
        } catch (SQLException e) {
            fail("SQL Connection could not be established: " + e.getMessage());
        }
    }

    @Test
    void testImportCitationsFromDatabase(){
        List<Citation> importedCitations = SqlReader.importCitationsFromLibraryTable("all_citations");

        assert importedCitations.size() == EXPECTED_CITATION_COUNT : "Expected 6 citations, but got " + importedCitations.size();

        System.out.println("Imported test library has the correct length.");

        assert importedCitations.getFirst().equals(CITATION_EXAMPLE);
    }

}
