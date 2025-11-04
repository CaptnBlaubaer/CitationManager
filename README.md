**Citation Management System v0.1**
==============================

_Project Description_

The Citation Manager application is used to manage citations for scientific publications written in LaTeX. 
It is written in Java and possesses basic functionalities to add/update/delete citations to an existing library, whereby the data is stored in a sqlite file. 

_What it does?_

It is possible to create a new libraries, open old libraries, and delete libraries. 
Publications can be added by creating a new entry manually, whereby different formats (journal publication, book, book chapter, patent, thesis, unpublished work) are possible,
or by importing citation in bibtex-format for on or more entries, form e.g. google scholar.

The library data is stored in a sqlite database file and can be exported to the bibtex format .bib to use it in LaTeX documents or export it to other systems e.g. Endnote or Citavi. 

The entries are listed in a table view, with main parameters: title, author, journal, year. 
If selected the detailed informations are shown in a adjacent pane and can be modified and saved.
If available a .pdf document can be attached to a citation, which is copied in a folder for the respective library.
Via PdfDisplayer package (com.github.Dansoftowner) the attached .pdf files can be viewed in the application.

The application is able to update existing journal publications meta data from the PubMed database, whereby it's possible to choose between all infromations or between selected informations.
If a DOI is deposited for a journal publication, it is further possible to download the .pdf automatically from the publisher if the user has the respective rights. 
Therefore, it uses Selenium in headless mode with Firefox Driver and several work arounds to circumvent bot tracking like Cloudflare non the publishers site.
In v0.1 it is only possible to access data from Nature, ACS and RSC. 

_Further Ideas_

Next step will be to add a filter function for the entries by title, author, publisher and year.
Further, the list of publishers shall be extended.
Pretty up the GUI. 
