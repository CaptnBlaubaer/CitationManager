package de.apaschold.demo;

import de.apaschold.demo.logic.filehandling.SeleniumWebHandlerHeadless;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class SeleniumWebHandlerHeadlessTest {

    //IMPORTANT: Sometimes the tests fail because the page is loading too slowly, then just re-run the tests!
    //Additionally, sometimes the Download is too slow, then the test will get stuck, due to a unhandled pop-up

    private static final String ACS_PAGE = "https://doi.org/10.1021/acs.bioconjchem.4c00188";
    private static final String ACS_PDF = "paschold-et-al-2024-photocontrolled-reversible-amyloid-fibril-formation-of-parathyroid-hormone-derived-peptides.pdf";

    private static final String WILEY_PAGE = "https://onlinelibrary.wiley.com/doi/10.1002/anie.202512346";

    private static final String NATURE_PAGE = "https://doi.org/10.1038/s41586-025-09495-w";
    private static final String NATURE_PDF = "s41586-025-09495-w.pdf";

    private static final String RSC_PAGE = "https://doi.org/10.1039/D5CC03181H";
    private static final String RSC_PDF = "D5CC03181H.pdf";

    private static final List<File> CREATED_TEST_FILES = new ArrayList<>();

    @Test
    void testNaturePdfDownload() {
        SeleniumWebHandlerHeadless.getInstance().downloadPdfFrom(NATURE_PAGE);

        File file = new File(System.getProperty("user.dir") + "\\"+ NATURE_PDF);

        assert file.exists();

        CREATED_TEST_FILES.add(file);
    }

    @Test
    void testRscPdfDownload() {
        // IMPORTANT: This test only works if VPN is enabled and connected to university network, article is not open Access!

        SeleniumWebHandlerHeadless.getInstance().downloadPdfFrom(RSC_PAGE);

        File file = new File(System.getProperty("user.dir") + "\\" + RSC_PDF);

        assert file.exists();

        CREATED_TEST_FILES.add(file);
    }

    @Test
    void testAcsPdfDownload() {
        // IMPORTANT: This test only works if VPN is enabled and connected to university network, article is not open Access!

        SeleniumWebHandlerHeadless.getInstance().downloadPdfFrom(ACS_PAGE);

        File file = new File(System.getProperty("user.dir") + "\\" + ACS_PDF);

        assert file.exists();

        CREATED_TEST_FILES.add(file);
    }


    @AfterAll
    static void removeTestFiles() {
        for (File file : CREATED_TEST_FILES) {

            if (file.exists()) {
                try {
                    Files.delete(file.toPath());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
