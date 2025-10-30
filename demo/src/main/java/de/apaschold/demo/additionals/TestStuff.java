package de.apaschold.demo.additionals;

import de.apaschold.demo.logic.CitationFactory;
import de.apaschold.demo.logic.CitationLibrary;
import de.apaschold.demo.logic.databasehandling.MySqlManager;
import de.apaschold.demo.logic.filehandling.FileHandler;
import de.apaschold.demo.logic.filehandling.SeleniumWebHandlerHeadless;
import de.apaschold.demo.model.Citation;
import de.apaschold.demo.model.CitationType;
import de.apaschold.demo.model.Unpublished;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestStuff {



    public static void main(String[] args) throws Exception {



            String statement = "SELECT * FROM persons";

            try (Connection connection = MySqlManager.getInstance().getDatabaseConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(statement);
                 ResultSet resultSet = preparedStatement.executeQuery()
            ) {
                while (resultSet.next()) {
                    //for (resultSet.)
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

    }
}
