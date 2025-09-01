package de.apaschold.demo.additionals;

public class AppTexts {
    //0. constants
    public static final String PLACEHOLDER = " - ";
    public static final int NUMBER_PLACEHOLDER = 0;
    public static final String REGEX_REPLACE_CML_FILENAME = "\\\\[a-zA-Z0-9-]+\\.cml";
    public static final String LIBRARY_FILE_FORMAT = ".cml";
    public static final String BIBTEX_FILE_FORMAT = ".cml";
    public static final String FOLDER_EXTENSION = "-pdfs";


    public static final String JOURNAL_ARTICLE_BIB_TEX_EXPORT_PROMPT = "@article{%s,\n" +
            "\tauthor={%s},\n" +
            "\ttitle={%s},\n" +
            "\tjournal={%s},\n" +
            "\tvolume={%d},\n" +
            "\tnumber={%d},\n" +
            "\tpages={%s},\n" +
            "\tDOI={%s},\n" +
            "\tyear={%d},\n" +
            "\ttype={Journal Article}\n}";
    public static final String BOOK_BIB_TEX_EXPORT_PROMPT = "@book{%s,\n" +
            "\tauthor={%s},\n" +
            "\ttitle={%s},\n" +
            "\tpublisher={%s},\n" +
            "\tDOI={%s},\n" +
            "\tyear={%d},\n" +
            "\ttype={Book}\n}";
    public static final String BOOK_SECTION_BIB_TEX_EXPORT_PROMPT = "@inbook{%s,\n" +
            "\tauthor={%s},\n" +
            "\ttitle={%s},\n" +
            "\tbooktitel={%s},\n" +
            "\teditor={%s},\n" +
            "\tpublisher={%s},\n" +
            "\tvolume={%d},\n" +
            "\tpages={%s},\n" +
            "\tDOI={%s},\n" +
            "\tyear={%d},\n" +
            "\ttype={Book Section}\n}";
    public static final String PATENT_BIB_TEX_EXPORT_PROMPT = "@misc{%s,\n" +
            "\tauthor={%s},\n" +
            "\ttitle={%s},\n" +
            "\turl={%s},\n" +
            "\tyear={%d},\n" +
            "\ttype={Patent}\n}";
    public static final String THESIS_BIB_TEX_EXPORT_PROMPT = "@phdthesis{%s,\n" +
            "\tauthor={%s},\n" +
            "\ttitle={%s},\n" +
            "\tDOI={%s},\n" +
            "\tyear={%d},\n" +
            "\ttype={Thesis}\n}";
    public static final String UNPUBLISHED_BIB_TEX_EXPORT_PROMPT = "@phdthesis{%s,\n" +
            "\tauthor={%s},\n" +
            "\ttitle={%s},\n" +
            "\tyear={%d},\n" +
            "\ttype={Unpublished Work}\n}";


    public static final String TABLE_VIEW_PLACEHOLDER = "Active Library could not been found.";

    public static final String ALERT_EMPTY_LIBRARY_TITLE = "Library is empty";
    public static final String ALERT_EMPTY_LIBRARY_HEADER = "The active library is empty.";
    public static final String ALERT_INVALID_FILENAME_TITLE = "Invalid filename";
    public static final String ALERT_INVALID_FILENAME_HEADER = "Filename can't contain whitespace character and can't be empty";
    public static final String ALERT_FILENAME_EXISTS_TITLE = "Filename already exists";
    public static final String ALERT_FILENAME_EXISTS_HEADER = "Filename already exists in this directory";
    public static final String INFORMATION_NO_FILE_CHOSEN_TITLE = "No file chosen";
    public static final String INFORMATION_NO_FILE_CHOSEN_HEADER = "There was no file chosen";
    public static final String CONFIRMATION_DELETE_ARTICLE_TITLE = "Delete selected article";
    public static final String CONFIRMATION_DELETE_ARTICLE_CONTENT = "Do you want to delete selected article?\nOK to confirm";
    //1. attributes

    //2. constructors
    private AppTexts(){}
}
