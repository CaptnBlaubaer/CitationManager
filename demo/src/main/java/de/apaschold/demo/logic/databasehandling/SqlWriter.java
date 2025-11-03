package de.apaschold.demo.logic.databasehandling;

import de.apaschold.demo.additionals.AppTexts;
import de.apaschold.demo.additionals.MyLittleHelpers;
import de.apaschold.demo.model.Citation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

//TODO add alerts for catch Blocks
/**
 * <h2>SqlWriter</h2>
 * <li>Handles writing operations to the SQL database, including creating library tables,
 * adding new citations, deleting citations, and updating existing citations.</li>
 */
public class SqlWriter {
    //0. constants
    private static final String CREATE_NEW_LIBRARY_TABLE_PROMPT =
            "CREATE TABLE IF NOT EXISTS %s (" +
                    "id INT AUTOINCREMENT PRIMARY KEY," +
                    "citation_type VARCHAR(255) NOT NULL," +
                    "title TEXT," +
                    "author TEXT," +
                    "journal TEXT," +
                    "year VARCHAR(4)," +
                    "doi TEXT," +
                    "pdf_file_path TEXT," +
                    "journal_abbreviation TEXT," +
                    "volume TEXT," +
                    "issue TEXT," +
                    "pages TEXT," +
                    "book_title TEXT," +
                    "editor TEXT" +
                    ");";

    private static final String CREATE_NEW_LIBRARY_TABLE_PROMPT_SQLITE =
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

    private static final String ADD_NEW_CITATION_TO_LIBRARY_TABLE_PROMPT =
            "INSERT INTO %s (citation_type, title, author, journal, year, doi, pdf_file_path, journal_abbreviation, volume, issue, pages, book_title, editor) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

    private static final String UPDATE_CITATION_IN_LIBRARY_TABLE_PROMPT =
            "UPDATE %s SET citation_type = ?, title = ?, author = ?, journal = ?, year = ?, doi = ?, pdf_file_path = ?, journal_abbreviation = ?, volume = ?, issue = ?, pages = ?, book_title = ?, editor = ? " +
                    "WHERE id = ?;";

    //1. attributes

    //2. constructors
    private SqlWriter() {}

    //3. write methods
    public static void createNewLibraryDatabase(){
        try {
            SqlManager.getInstance().getSqliteDatabaseConnection();
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
        String createNewLibraryTableStatement = String.format(CREATE_NEW_LIBRARY_TABLE_PROMPT, tableName);

        try(Connection connection = SqlManager.getInstance().getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(createNewLibraryTableStatement)){

                preparedStatement.executeUpdate();

        } catch (Exception ee){
            ee.printStackTrace();
        }
    }

    public static void createNewLibraryTableSqlite(String tableName){
        String createNewLibraryTableStatement = String.format(CREATE_NEW_LIBRARY_TABLE_PROMPT_SQLITE, tableName);

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
    public static void addNewCitationToLibraryTable(String tableName, Citation citationToAdd){
        String addNewCitationToLibraryStatement = String.format(ADD_NEW_CITATION_TO_LIBRARY_TABLE_PROMPT, tableName);
        String[] citationDataInArray = citationToAdd.toCsvString()
                .replaceAll(AppTexts.PLACEHOLDER,"NULL")
                .split(";");

        try(Connection connection = SqlManager.getInstance().getDatabaseConnection();
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
            System.out.println("Hello world");
        }
    }

    /**
     * <h2>deleteCitationFromLibrary</h2>
     * <li>Deletes a citation from the specified library table in the database.</li>
     *
     * @param tableName        the name of the library table
     * @param citationToDelete the citation to be deleted
     */
    public static void deleteCitationFromLibrary(String tableName, Citation citationToDelete){
        String deleteCitationFromLibraryStatement = String.format("DELETE FROM %s WHERE id = ?;",tableName);

        try(Connection connection = SqlManager.getInstance().getDatabaseConnection();
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
    public static void updateCitationInLibrary(String tableName, Citation citationToUpdate){
        String updateCitationInLibraryStatement = String.format(UPDATE_CITATION_IN_LIBRARY_TABLE_PROMPT, tableName);

        String[] citationDataInArray = citationToUpdate.toCsvString()
                .replaceAll(AppTexts.PLACEHOLDER,"NULL")
                .split(";");

        try(Connection connection = SqlManager.getInstance().getDatabaseConnection();
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
            System.out.println("Hello world");
        }
    }

    /**
     * <h2>addCitationLibraryToTable</h2>
     * <li>Adds a list of {@link Citation} to the specified library table in the database.</li>
     *
     * @param tableName       the name of the library table
     * @param citationsToAdd  the list of citations to be added
     */
    public static void addCitationListToLibraryTable(String tableName, List<Citation> citationsToAdd){
        for(Citation citation : citationsToAdd){
            addNewCitationToLibraryTable(tableName, citation);
        }
    }
}
