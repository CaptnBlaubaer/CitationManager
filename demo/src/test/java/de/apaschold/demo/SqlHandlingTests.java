package de.apaschold.demo;


import de.apaschold.demo.additionals.AppTexts;
import de.apaschold.demo.gui.GuiController;
import de.apaschold.demo.logic.databasehandling.SqlManager;
import de.apaschold.demo.logic.databasehandling.SqlReader;
import de.apaschold.demo.logic.databasehandling.SqlWriter;
import de.apaschold.demo.logic.filehandling.TextFileHandler;
import de.apaschold.demo.model.Citation;
import de.apaschold.demo.model.PhdThesis;
import org.junit.jupiter.api.*;

import java.io.File;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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

    //1. attributes
    private static File testLibraryDbFile;

    //2. set-up
    @BeforeAll
    static void setup(){
        TextFileHandler.setActiveLibraryFilePath(TEST_LIBRARY_PATH);
        GuiController.getInstance();
    }

    //3. tests
    @Test
    @Order(1)
    void testSqlConnection(){
        try {
            SqlManager.getInstance().getSqliteDatabaseConnection();
            assert true;
        } catch (SQLException e) {
            fail("SQL Connection could not be established: " + e.getMessage());
        }
    }

    @Test
    @Order(2)
    void testImportCitationsFromDatabase(){
        List<Citation> importedCitations = SqlReader.importCitationsFromLibraryTable("all_citations");

        assert importedCitations.size() == EXPECTED_CITATION_COUNT : "Expected 6 citations, but got " + importedCitations.size();
        assert importedCitations.getFirst().equals(CITATION_EXAMPLE);
    }

    @Test
    @Order(3)
    void testCreateNewLibraryDatabase(){
        String testLibraryDbPath = "src/test/resources/de/apaschold/demo/data/test-library.db";
        GuiController.getInstance().createNewLibrary(testLibraryDbPath);

        //verify that the database file was created
        testLibraryDbFile = new File(testLibraryDbPath);
        assertTrue(testLibraryDbFile.exists(), "Database file was not created.");
    }

    @Test
    @Order(4)
    void testAddNewCitationToLibraryTable(){
        //checks wether citation only exists once before adding
        List<Citation> importedCitations = SqlReader.importCitationsFromLibraryTable(AppTexts.SQLITE_TABLE_NAME_ALL_CITATIONS);

        assertEquals(0, Collections.frequency(importedCitations, CITATION_EXAMPLE));

        //adds duplicate citation
        SqlWriter.addNewCitationToLibraryTable(AppTexts.SQLITE_TABLE_NAME_ALL_CITATIONS, CITATION_EXAMPLE);

        //checks wether citation now exists twice
        importedCitations = SqlReader.importCitationsFromLibraryTable(AppTexts.SQLITE_TABLE_NAME_ALL_CITATIONS);

        assertEquals(1, Collections.frequency(importedCitations, CITATION_EXAMPLE));
    }

    @Test
    @Order(5)
    void testUpdateCitationInLibrary(){
        //updates the citation
        List<Citation> importedCitations = SqlReader.importCitationsFromLibraryTable(AppTexts.SQLITE_TABLE_NAME_ALL_CITATIONS);
        Citation citationToUpdate = importedCitations.getFirst();
        PhdThesis updatedCitation = new PhdThesis(
                citationToUpdate.getId(),
                "Photocontrol and Structural Analysis of Amyloid Fibril Formation"+
                        " Using Azobenzene and Novel Spiropyran Photoswitches",
                "Paschold, André",
                2025,
                "updated-doi",
                "newFilePath"
        );
        SqlWriter.updateCitationInLibrary(AppTexts.SQLITE_TABLE_NAME_ALL_CITATIONS, updatedCitation);

        //checks wether citation was updated
        importedCitations = SqlReader.importCitationsFromLibraryTable(AppTexts.SQLITE_TABLE_NAME_ALL_CITATIONS);
        Citation fetchedUpdatedCitation = importedCitations.getFirst();

        assertEquals(fetchedUpdatedCitation, updatedCitation);
    }

    @Test
    @Order(6)
    void testDeleteCitationFromLibrary(){
        //deletes the citation
        List<Citation> importedCitations = SqlReader.importCitationsFromLibraryTable(AppTexts.SQLITE_TABLE_NAME_ALL_CITATIONS);
        Citation citationToDelete = importedCitations.getFirst();
        SqlWriter.deleteCitationFromLibrary(AppTexts.SQLITE_TABLE_NAME_ALL_CITATIONS, citationToDelete);

        //checks wether citation no longer exists
        importedCitations = SqlReader.importCitationsFromLibraryTable(AppTexts.SQLITE_TABLE_NAME_ALL_CITATIONS);
        assertEquals(0, Collections.frequency(importedCitations, CITATION_EXAMPLE));
    }

    @AfterAll
    static void cleanup(){
        if(testLibraryDbFile != null && testLibraryDbFile.exists()){
            assertTrue(testLibraryDbFile.delete(), "Test library database file could not be deleted.");
        }
    }
}
