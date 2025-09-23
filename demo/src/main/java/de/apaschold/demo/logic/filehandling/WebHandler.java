package de.apaschold.demo.logic.filehandling;

import de.apaschold.demo.additionals.AppTexts;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


public class WebHandler {
    //0. constants
    public static final String PUB_MED_ENDPOINT_URL = "https://eutils.ncbi.nlm.nih.gov/entrez/eutils/";
    public static final String PUB_MED_GET_RECORDS_PROMPT_TEST = "esummary.fcgi?db=pubmed&id=2014248";
    public static final String PUB_MED_GET_RECORDS_PROMPT = "esummary.fcgi?db=pubmed&id=%s&retmode=json";
    public static final String PUB_MED_SEARCH_BY_TERM_PROMPT = "ecitmatch.cgi?db=pubmed&retmode=xml&bdata=%s";
    public static final String PUB_MED_SEARCH_BY_TERM_TEST = "ecitmatch.cgi?db=pubmed&retmode=xml&bdata=Proceedings+of+the+National+Academy+of+Sciences+of+the+United+States+of+America|1991||3248|mann|Art1|";

    //1. attributes
    private static WebHandler instance;

    //2. constructors
    private WebHandler() {
    }

    public static synchronized WebHandler getInstance() {
        if (instance == null) {
            instance = new WebHandler();
        }
        return instance;
    }

    //3. web methods
    private String webRequest(String urlAsString){
        StringBuilder content = new StringBuilder();

        try {
            URL url = new URL(urlAsString);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;


            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            // Close connections
            in.close();
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return content.toString();
    }

    public String getPubMedId(String articlePubMedString){
        return webRequest(PUB_MED_ENDPOINT_URL + String.format(PUB_MED_SEARCH_BY_TERM_PROMPT, articlePubMedString));
    }

    public JSONObject getRecordsFromPubMedId(String pubMedId){
        String webResponse = webRequest(PUB_MED_ENDPOINT_URL + String.format(PUB_MED_GET_RECORDS_PROMPT, pubMedId));

        JSONObject webResponseAsJsonObject = new JSONObject(webResponse);

        return webResponseAsJsonObject.getJSONObject("result").getJSONObject(pubMedId);
    }

    //4. page scrapping
    public void searchForPdf(String doi){
        doi = AppTexts.HTTPS_FOR_DOI + doi;

        System.out.println(doi);
    }


}

