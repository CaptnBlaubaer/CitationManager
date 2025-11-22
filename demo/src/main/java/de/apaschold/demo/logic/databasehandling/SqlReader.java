package de.apaschold.demo.logic.databasehandling;

import de.apaschold.demo.additionals.AppTexts;
import de.apaschold.demo.logic.CitationFactory;
import de.apaschold.demo.model.AbstractCitation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SqlReader {
    //0. constants
    private static final String ALL_CITATIONS_FROM_TABLE_QUERY_TEMPLATE = "SELECT * FROM %s;";
    private static final String CHECK_TABLE_EXISTS_QUERY_TEMPLATE = "SHOW TABLES LIKE '%s';";

    private static final String FILTER_CITATIONS_FROM_TABLE_QUERY_TEMPLATE = "SELECT * FROM %s WHERE ";
    private static final String CONTAINS_KEYWORD_TEMPLATE = "%s LIKE '%s'";

    //1. attributes

    //2. constructors
    private SqlReader() {}

    //3. methods
    /**
     * <h2>importCitationsFromLibraryTableSqlite</h2>
     * <li>Imports all citations from the specified library table in the SQLite database.</li>
     *
     * @param tableName name of the library table to import citations from
     * @return list of imported Citation objects
     */
    public static List<AbstractCitation> importCitationsFromLibraryTable(String tableName){
        String importCitationsFromTableQuery = String.format(ALL_CITATIONS_FROM_TABLE_QUERY_TEMPLATE, tableName);

        List<AbstractCitation> importedCitations = new ArrayList<>();

        try(Connection connection = SqlManager.getInstance().getSqliteDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(importCitationsFromTableQuery);
            ResultSet resultSet = preparedStatement.executeQuery()){

            while(resultSet.next()){
                StringBuilder importedCitationDataAsCsvString = new StringBuilder();
                int columnCount = resultSet.getMetaData().getColumnCount();

                for(int index = 1; index <= columnCount; index++){
                    if (resultSet.getString(index).equals("NULL")){
                        importedCitationDataAsCsvString.append(AppTexts.PLACEHOLDER);
                    } else {
                        importedCitationDataAsCsvString.append(resultSet.getString(index));
                    }
                    if(index < columnCount){
                        importedCitationDataAsCsvString.append(";");
                    }
                }

                importedCitations.add(CitationFactory.createCitationFromCsvLine(importedCitationDataAsCsvString.toString()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return importedCitations;
    }

    /**
     * <h2>checkIfLibraryTableExist</h2>
     * <li>Checks if a library table with the given name exists in the database.</li>
     *
     * @param tableName name of the library table to check
     * @return true if the table exists, false otherwise
     */
    public static boolean checkIfLibraryTableExist(String tableName) {
        String checkTableExistsQuery = String.format(CHECK_TABLE_EXISTS_QUERY_TEMPLATE, tableName);
        boolean tableExists = false;

        try (Connection connection = SqlManager.getInstance().getSqliteDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(checkTableExistsQuery)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            tableExists = resultSet.next();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return tableExists;
    }

    public static List<AbstractCitation> filterCitationsByKeywords(String tableName, String authorKeyword, String titleKeyword) {
        List<AbstractCitation> filteredCitations = new ArrayList<>();

        String filterCitationsQuery = createFilterCitationsQuery(tableName, authorKeyword, titleKeyword);

        try(Connection connection = SqlManager.getInstance().getSqliteDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(filterCitationsQuery);
            ResultSet resultSet = preparedStatement.executeQuery()){

            while(resultSet.next()){
                StringBuilder importedCitationDataAsCsvString = new StringBuilder();
                int columnCount = resultSet.getMetaData().getColumnCount();

                for(int index = 1; index <= columnCount; index++){
                    if (resultSet.getString(index).equals("NULL")){
                        importedCitationDataAsCsvString.append(AppTexts.PLACEHOLDER);
                    } else {
                        importedCitationDataAsCsvString.append(resultSet.getString(index));
                    }
                    if(index < columnCount){
                        importedCitationDataAsCsvString.append(";");
                    }
                }

                filteredCitations.add(CitationFactory.createCitationFromCsvLine(importedCitationDataAsCsvString.toString()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return filteredCitations;
    }

    private static String createFilterCitationsQuery(String tableName, String authorKeyword, String titleKeyword) {
        String filterCitationsQuery = String.format(FILTER_CITATIONS_FROM_TABLE_QUERY_TEMPLATE, tableName);

        if (!authorKeyword.isEmpty()) {
            filterCitationsQuery += String.format(CONTAINS_KEYWORD_TEMPLATE, "author", "%" + authorKeyword + "%");
            if (!titleKeyword.isEmpty()) {
                filterCitationsQuery += " AND ";
            }
        }

        if (!titleKeyword.isEmpty()) {
            filterCitationsQuery += String.format(CONTAINS_KEYWORD_TEMPLATE, "title", "%" + titleKeyword +"%");
        }

        filterCitationsQuery += ";";

        return filterCitationsQuery;
    }
}
