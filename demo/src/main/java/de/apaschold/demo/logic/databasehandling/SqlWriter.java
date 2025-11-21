package de.apaschold.demo.logic.databasehandling;

import de.apaschold.demo.additionals.AppTexts;
import de.apaschold.demo.additionals.MyLittleHelpers;
import de.apaschold.demo.model.AbstractCitation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//TODO add alerts for catch Blocks
/**
 * <h2>SqlWriter</h2>
 * <li>Handles writing operations to the SQL database, including creating library tables,
 * adding new citations, deleting citations, and updating existing citations.</li>
 */
public class SqlWriter {
    //0. constants
    private static final String CREATE_NEW_LIBRARY_TABLE_TEMPLATE =
            "CREATE TABLE IF NOT EXISTS %s (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    "citation_type text NOT NULL," +
                    "title text," +
                    "author text," +
                    "journal text," +
                    "year text," +
                    "doi text," +
                    "pdf_file_path text," +
                    "journal_abbreviation text," +
                    "volume text," +
                    "issue text," +
                    "pages text," +
                    "book_title text," +
                    "editor text" +
                    ");";

    private static final String ADD_NEW_CITATION_TO_LIBRARY_TABLE_TEMPLATE =
            "INSERT INTO %s (citation_type, title, author, journal, year, doi, pdf_file_path, journal_abbreviation, volume, issue, pages, book_title, editor) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

    private static final String UPDATE_CITATION_IN_LIBRARY_TABLE_TEMPLATE =
            "UPDATE %s SET citation_type = ?, title = ?, author = ?, journal = ?, year = ?, doi = ?, pdf_file_path = ?, journal_abbreviation = ?, volume = ?, issue = ?, pages = ?, book_title = ?, editor = ? " +
                    "WHERE id = ?;";

    //1. attributes

    //2. constructors
    private SqlWriter() {}

    //3. write methods
    /**
     * <h2>createNewLibraryDatabase</h2>
     * <li>Creates a new library database if it does not already exist.</li>
     */
    public static void createNewLibraryDatabase(){
        try {
            Connection connection = SqlManager.getInstance().getSqliteDatabaseConnection();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * <h2>createNewLibraryTable</h2>
     * <li>Creates a new library table in the database with the specified name.</li>
     *
     * @param tableName the name of the new library table
     */
    public static void createNewLibraryTable(String tableName){
        String createNewLibraryTableStatement = String.format(CREATE_NEW_LIBRARY_TABLE_TEMPLATE, tableName);

        try(Connection connection = SqlManager.getInstance().getSqliteDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(createNewLibraryTableStatement)){

            preparedStatement.executeUpdate();

        } catch (Exception ee){
            ee.printStackTrace();
        }
    }

    /**
     * <h2>addNewCitationToLibraryTable</h2>
     * <li>Adds a new citation to the specified library table in the database.</li>
     *
     * @param tableName      the name of the library table
     * @param citationToAdd  the citation to be added
     */
    public static void addNewCitationToLibraryTable(String tableName, AbstractCitation citationToAdd){
        String addNewCitationToLibraryStatement = String.format(ADD_NEW_CITATION_TO_LIBRARY_TABLE_TEMPLATE, tableName);
        String[] citationDataInArray = citationToAdd.toCsvString()
                .replaceAll(AppTexts.PLACEHOLDER,"NULL")
                .split(";");

        try(Connection connection = SqlManager.getInstance().getSqliteDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(addNewCitationToLibraryStatement)){

            preparedStatement.setString(1, citationDataInArray[1]); // CitationType
            preparedStatement.setString(2, citationDataInArray[2]); // Title
            preparedStatement.setString(3, citationDataInArray[3]); // Author
            preparedStatement.setString(4, citationDataInArray[4]); // Journal/Publisher
            preparedStatement.setString(5, citationDataInArray[5]); // Year
            preparedStatement.setString(6, citationDataInArray[6]); // DOI
            preparedStatement.setString(7, citationDataInArray[7]); // PDF
            preparedStatement.setString(8, citationDataInArray[8]); // Journal
            preparedStatement.setString(9, citationDataInArray[9]); // Volume
            preparedStatement.setString(10, citationDataInArray[10]); // Issue
            preparedStatement.setString(11, citationDataInArray[11]); // Pages
            preparedStatement.setString(12, citationDataInArray[12]); // Book Title
            preparedStatement.setString(13, citationDataInArray[13]); // Editor

            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * <h2>deleteCitationFromLibrary</h2>
     * <li>Deletes a citation from the specified library table in the database.</li>
     *
     * @param tableName        the name of the library table
     * @param citationToDelete the citation to be deleted
     */
    public static void deleteCitationFromLibrary(String tableName, AbstractCitation citationToDelete){
        String deleteCitationFromLibraryStatement = String.format("DELETE FROM %s WHERE id = ?;",tableName);

        try(Connection connection = SqlManager.getInstance().getSqliteDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(deleteCitationFromLibraryStatement)){

            preparedStatement.setInt(1, citationToDelete.getId());
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            System.out.println("Hello world");
        }
    }

    /**
     * <h2>updateCitationInLibrary</h2>
     * <li>Updates an existing citation in the specified library table in the database.</li>
     *
     * @param tableName        the name of the library table
     * @param citationToUpdate the citation with updated information
     */
    public static void updateCitationInLibrary(String tableName, AbstractCitation citationToUpdate){
        String updateCitationInLibraryStatement = String.format(UPDATE_CITATION_IN_LIBRARY_TABLE_TEMPLATE, tableName);

        String[] citationDataInArray = citationToUpdate.toCsvString()
                .replaceAll(AppTexts.PLACEHOLDER,"NULL")
                .split(";");

        try(Connection connection = SqlManager.getInstance().getSqliteDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(updateCitationInLibraryStatement)){

            preparedStatement.setString(1, citationDataInArray[1]); // CitationType
            preparedStatement.setString(2, citationDataInArray[2]); // Title
            preparedStatement.setString(3, citationDataInArray[3]); // Author
            preparedStatement.setString(4, citationDataInArray[4]); // Journal/Publisher
            preparedStatement.setString(5, citationDataInArray[5]); // Year
            preparedStatement.setString(6, citationDataInArray[6]); // DOI
            preparedStatement.setString(7, citationDataInArray[7]); // PDF
            preparedStatement.setString(8, citationDataInArray[8]); // Journal
            preparedStatement.setString(9, citationDataInArray[9]); // Volume
            preparedStatement.setString(10, citationDataInArray[10]); // Issue
            preparedStatement.setString(11, citationDataInArray[11]); // Pages
            preparedStatement.setString(12, citationDataInArray[12]); // Book Title
            preparedStatement.setString(13, citationDataInArray[13]); // Editor

            preparedStatement.setInt(14,
                    MyLittleHelpers.convertStringInputToInteger(citationDataInArray[0])); //id
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
