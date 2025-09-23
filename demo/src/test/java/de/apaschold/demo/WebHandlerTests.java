package de.apaschold.demo;


import de.apaschold.demo.logic.filehandling.WebHandler;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

public class WebHandlerTests {
    public static final String PUB_MED_ENDPOINT_URL = "https://eutils.ncbi.nlm.nih.gov/entrez/eutils/";
    public static final String PUB_MED_GET_RECORDS_PROMPT_TEST = "esummary.fcgi?db=pubmed&id=2014248";
    public static final String PUB_MED_SEARCH_BY_TERM_TEST = "ecitmatch.cgi?db=pubmed&retmode=xml&bdata=Proceedings+of+the+National+Academy+of+Sciences+of+the+United+States+of+America|1991||3248|mann|Art1|";

    @Test
    void testGetPubMedId(){
        String testDataSet = "Proceedings+of+the+National+Academy+of+Sciences+of+the+United+States+of+America|1991||3248|mann|Art1|";
        String expectedResponse = "Proceedings of the National Academy of Sciences of the United States of America|1991||3248|mann|Art1|2014248";

        String webResponse = WebHandler.getInstance().getPubMedId(testDataSet);

        assert webResponse.equals(expectedResponse);
    }

    @Test
    void testGetRecordsFromPubMedId(){
        String testId = "2014248";
        String expectedResponse = "{\"chapter\":\"\",\"references\":[],\"srcdate\":\"\",\"locationlabel\":\"\",\"sortpubdate\":\"1991/04/15 00:00\",\"edition\":\"\",\"source\":\"Proc Natl Acad Sci U S A\",\"pubtype\":[\"Journal Article\"],\"medium\":\"\",\"title\":\"Sequence of a cysteine-rich galactose-specific lectin of Entamoeba histolytica.\",\"nlmuniqueid\":\"7505876\",\"uid\":\"2014248\",\"pages\":\"3248-52\",\"elocationid\":\"\",\"sorttitle\":\"sequence of a cysteine rich galactose specific lectin of entamoeba histolytica\",\"doccontriblist\":[],\"articleids\":[{\"idtype\":\"pubmed\",\"idtypen\":1,\"value\":\"2014248\"},{\"idtype\":\"pmc\",\"idtypen\":8,\"value\":\"PMC51423\"},{\"idtype\":\"pmcid\",\"idtypen\":5,\"value\":\"pmc-id: PMC51423;\"},{\"idtype\":\"doi\",\"idtypen\":3,\"value\":\"10.1073/pnas.88.8.3248\"}],\"publishername\":\"\",\"lang\":[\"eng\"],\"booktitle\":\"\",\"docdate\":\"\",\"vernaculartitle\":\"\",\"pubdate\":\"1991 Apr 15\",\"fulljournalname\":\"Proceedings of the National Academy of Sciences of the United States of America\",\"srccontriblist\":[],\"issue\":\"8\",\"pubstatus\":\"4\",\"essn\":\"1091-6490\",\"recordstatus\":\"PubMed - indexed for MEDLINE\",\"reportnumber\":\"\",\"history\":[{\"date\":\"1991/04/15 00:00\",\"pubstatus\":\"pubmed\"},{\"date\":\"2001/03/28 10:01\",\"pubstatus\":\"medline\"},{\"date\":\"1991/04/15 00:00\",\"pubstatus\":\"entrez\"},{\"date\":\"1991/10/15 00:00\",\"pubstatus\":\"pmc-release\"}],\"epubdate\":\"\",\"sortfirstauthor\":\"Mann BJ\",\"volume\":\"88\",\"doctype\":\"citation\",\"publisherlocation\":\"\",\"lastauthor\":\"Petri WA Jr\",\"availablefromurl\":\"\",\"issn\":\"0027-8424\",\"pmcrefcount\":32,\"attributes\":[\"Has Abstract\"],\"bookname\":\"\",\"authors\":[{\"name\":\"Mann BJ\",\"clusterid\":\"\",\"authtype\":\"Author\"},{\"name\":\"Torian BE\",\"clusterid\":\"\",\"authtype\":\"Author\"},{\"name\":\"Vedvick TS\",\"clusterid\":\"\",\"authtype\":\"Author\"},{\"name\":\"Petri WA Jr\",\"clusterid\":\"\",\"authtype\":\"Author\"}]}";

        JSONObject webResponse = WebHandler.getInstance().getRecordsFromPubMedId(testId);

        assert webResponse.toString().equals(expectedResponse);
    }
}
