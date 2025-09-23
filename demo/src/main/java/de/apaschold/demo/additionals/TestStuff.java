package de.apaschold.demo.additionals;

import de.apaschold.demo.logic.filehandling.SeleniumWebHandlerHeadless;

public class TestStuff {

    private static final String ACS_PAGE = "https://doi.org/10.1021/acs.bioconjchem.4c00188";
    private static final String WILEY_PAGE = "https://onlinelibrary.wiley.com/doi/10.1002/anie.202512346";
    private static final String NATURE_PAGE = "https://doi.org/10.1038/s41586-025-09495-w";
    private static final String RSC_PAGE = "https://doi.org/10.1039/D5CC03181H";

    public static void main(String[] args) {

        SeleniumWebHandlerHeadless.getInstance().downloadPdfFrom(RSC_PAGE);
    }
}
