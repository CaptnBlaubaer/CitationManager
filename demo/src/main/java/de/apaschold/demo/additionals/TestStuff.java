package de.apaschold.demo.additionals;

import de.apaschold.demo.logic.databasehandling.SqlReader;

public class TestStuff {



    public static void main(String[] args) throws Exception {
        String tableName = "person";

        System.out.println(SqlReader.checkIfLibraryTableExist(tableName));
    }
}
