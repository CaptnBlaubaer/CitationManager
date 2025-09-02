package de.apaschold.demo.additionals;

import de.apaschold.demo.logic.filehandling.WebHandler;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.SQLOutput;

public class TestStuff {
    public static void main(String[] args) {
        String input = "2014248";
        JSONObject info = WebHandler.getInstance().getRecordsFromPubMedId(input);

        //String info = "<?xml version='1.0' encoding='UTF-8'?>   <posts>    <post postId='1'>    <title>Parsing XML as a String in Java</title>   <author>John Doe</author>  </post> </posts>";
        System.out.println(info);

        JSONArray authorsAsJsonArray = info.getJSONArray("authors");

        StringBuilder authorsAsString = new StringBuilder();

        int authorsLength = authorsAsJsonArray.length();

        for(int index = 0; index < authorsLength; index++){
            JSONObject jo = authorsAsJsonArray.getJSONObject(index);

            String author = jo.getString("name").replaceFirst(" ", ", ");
            authorsAsString.append(author).append("; ");
        }

        System.out.println(authorsAsString);





    }
}
