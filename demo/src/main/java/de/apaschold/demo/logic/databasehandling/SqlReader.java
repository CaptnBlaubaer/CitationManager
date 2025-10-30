package de.apaschold.demo.logic.databasehandling;

import de.apaschold.demo.additionals.AppTexts;
import de.apaschold.demo.logic.CitationFactory;
import de.apaschold.demo.model.Citation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SqlReader {
    //0. constants
    private static final String ALL_CITATIONS_FROM_TABLE_QUERY_TEMPLATE = "SELECT * FROM %s;";

    //1. attributes

    //2. constructors
    private SqlReader() {}

    //3. methods
    public static List<Citation> importCitationsFromLibraryTable(String tableName){
        String importCitationsFromTableQuery = String.format(ALL_CITATIONS_FROM_TABLE_QUERY_TEMPLATE, tableName);

        List<Citation> importedCitations = new ArrayList<>();

        try(Connection connection = SqlManager.getInstance().getDatabaseConnection();
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
}
