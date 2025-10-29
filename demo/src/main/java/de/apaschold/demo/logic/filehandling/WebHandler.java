package de.apaschold.demo.logic.filehandling;

import de.apaschold.demo.additionals.AppTexts;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * <h2>WebHandler</h2>
 * <li>Singleton class that manages web requests to external APIs.</li>
 * <li>Retrieves data from PubMed database</li>
 */

public class WebHandler {
    //0. constants
    public static final String PUB_MED_ENDPOINT_URL = "https://eutils.ncbi.nlm.nih.gov/entrez/eutils/";
    //public static final String PUB_MED_GET_RECORDS_PROMPT_TEST = "esummary.fcgi?db=pubmed&id=2014248";
    public static final String PUB_MED_GET_RECORDS_PROMPT = "esummary.fcgi?db=pubmed&id=%s&retmode=json";
    public static final String PUB_MED_SEARCH_BY_TERM_PROMPT = "ecitmatch.cgi?db=pubmed&retmode=xml&bdata=%s";
    //public static final String PUB_MED_SEARCH_BY_TERM_TEST = "ecitmatch.cgi?db=pubmed&retmode=xml&bdata=Proceedings+of+the+National+Academy+of+Sciences+of+the+United+States+of+America|1991||3248|mann|Art1|";

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
    /**
     * <h2>webRequest</h2>
     * <li>Sends a GET request to the specified URL and returns the response content as a String.</li>
     *
     * @param urlAsString the URL to send the GET request to
     * @return the response content as a String
     */

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

    /** <h2>getPubMedId</h2>
     * <li>Sends a request to PubMed to search for an {@link de.apaschold.demo.model.JournalArticle} using a search term and retrieves the PubMed ID.</li>
     *
     * @param articlePubMedString the search term for the article
     * @return the PubMed ID as a String
     */
    public String getPubMedId(String articlePubMedString){
        return webRequest(PUB_MED_ENDPOINT_URL + String.format(PUB_MED_SEARCH_BY_TERM_PROMPT, articlePubMedString));
    }

    /** <h2>getRecordsFromPubMedId</h2>
     * <li>Sends a request to PubMed to retrieve {@link de.apaschold.demo.model.JournalArticle} records using the provided PubMed ID.</li>
     * <li>Retrieves data as {@link JSONObject} and returns only results part</li>
     *
     * @param pubMedId the PubMed ID of the article
     * @return the article records as a JSONObject
     */
    public JSONObject getRecordsFromPubMedId(String pubMedId) throws JSONException {
        String webResponse = webRequest(PUB_MED_ENDPOINT_URL + String.format(PUB_MED_GET_RECORDS_PROMPT, pubMedId));

        JSONObject webResponseAsJsonObject = new JSONObject(webResponse);

        return webResponseAsJsonObject.getJSONObject("result").getJSONObject(pubMedId);
    }
}

