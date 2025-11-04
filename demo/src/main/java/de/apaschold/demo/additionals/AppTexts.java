package de.apaschold.demo.additionals;

public class AppTexts {
    //0. constants
    public static final String PLACEHOLDER = " - ";
    public static final int NUMBER_PLACEHOLDER = 0;
    public static final String REGEX_REPLACE_DB_FILENAME = "\\\\[a-zA-Z0-9-_]+\\.db";
    public static final String LIBRARY_FILE_FORMAT = ".db";
    public static final String BIBTEX_FILE_FORMAT = ".bib";
    public static final String PDF_FOLDER_EXTENSION = "-pdfs\\";
    public static final String HTTPS_FOR_DOI = "https://doi.org/";

    public static final String SQLITE_TABLE_NAME_ALL_CITATIONS = "all_citations";

    //General CSV Format
    //CitationType;Title;Authors;Journal/Publisher;Year;DOI/URL;Attached files;Journal abbreviation;Volume;Issue;Pages;Book title;Editor
    public static final String CSV_STRING_TEMPLATE = "%d;%s;%s;%s;%s;%d;%s;%s;%s;%d;%d;%s;%s;%s";

    public static final String JOURNAL_ARTICLE_BIB_TEX_EXPORT_TEMPLATE = "@article{%s,\n" +
            "\tauthor={%s},\n" +
            "\ttitle={%s},\n" +
            "\tjournal={%s},\n" +
            "\tvolume={%d},\n" +
            "\tnumber={%d},\n" +
            "\tpages={%s},\n" +
            "\tDOI={%s},\n" +
            "\tyear={%d},\n" +
            "\ttype={Journal Article}\n}";
    public static final String BOOK_BIB_TEX_EXPORT_TEMPLATE = "@book{%s,\n" +
            "\tauthor={%s},\n" +
            "\ttitle={%s},\n" +
            "\tpublisher={%s},\n" +
            "\tDOI={%s},\n" +
            "\tyear={%d},\n" +
            "\ttype={Book}\n}";
    public static final String BOOK_SECTION_BIB_TEX_EXPORT_TEMPLATE = "@inbook{%s,\n" +
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
    public static final String PATENT_BIB_TEX_EXPORT_TEMPLATE = "@misc{%s,\n" +
            "\tauthor={%s},\n" +
            "\ttitle={%s},\n" +
            "\turl={%s},\n" +
            "\tyear={%d},\n" +
            "\ttype={Patent}\n}";
    public static final String THESIS_BIB_TEX_EXPORT_TEMPLATE = "@phdthesis{%s,\n" +
            "\tauthor={%s},\n" +
            "\ttitle={%s},\n" +
            "\tDOI={%s},\n" +
            "\tyear={%d},\n" +
            "\ttype={Thesis}\n}";
    public static final String UNPUBLISHED_BIB_TEX_EXPORT_TEMPLATE = "@phdthesis{%s,\n" +
            "\tauthor={%s},\n" +
            "\ttitle={%s},\n" +
            "\tyear={%d},\n" +
            "\ttype={Unpublished Work}\n}";


    public static final String TABLE_VIEW_PLACEHOLDER = "Active Library could not been found.";

    public static final String ERROR_CONNECTING_TO_DATABASE = "Failed connecting to the database :";
    //1. attributes

    //2. constructors
    private AppTexts(){}
}
