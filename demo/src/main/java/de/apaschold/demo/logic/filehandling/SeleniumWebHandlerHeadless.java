package de.apaschold.demo.logic.filehandling;

import de.apaschold.demo.additionals.AppTexts;
import de.apaschold.demo.gui.GuiController;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * <h2>SeleniumWebHandlerHeadless</h2>
 * <li>Singleton class that manages Selenium WebDriver in headless mode.</li>
 * <li>Handles downloading of PDF files from various journal websites.</li>
 */
//IMPORTANT: Wiley can't be handled  as it uses an own pdf viewer in the browser

public class SeleniumWebHandlerHeadless {
    //0. constants for Selenium pdf download
    private static final String FIREFOX_DRIVER_PATH = System.getProperty("user.dir") + "\\geckodriver.exe";
    private static final String START_PAGE = "https://www.google.com";

    private static final String USER_AGENT_OPERA = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/135.0.0.0 Safari/537.36 OPR/120.0.0.0";
    private static final String USER_AGENT_FIREFOX = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:142.0) Gecko/20100101 Firefox/142.0";
    private static final String USER_AGENT_EDGE = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36 Edg/140.0.0.0";

    private static final String[] USER_AGENTS = {USER_AGENT_OPERA, USER_AGENT_FIREFOX, USER_AGENT_EDGE};


    //1. attributes
    private WebDriver driver;
    private String elementDownloadLink;
    private static SeleniumWebHandlerHeadless instance;
    private String downloadPath;

    //2. constructors
    private SeleniumWebHandlerHeadless(){
    }

    //3. getInstance method
    public static synchronized SeleniumWebHandlerHeadless getInstance(){
        if(instance == null){
            instance = new SeleniumWebHandlerHeadless();
        }

        return instance;
    }

    //4. setters and getters
    /**
     * <h2>setDownloadPath</h2>
     * <li>Sets the download path for PDFs based on the active library file path.</li>
     */
    private void setDownloadPath() {
        this.downloadPath = GuiController.getInstance().getActiveLibraryFilePath().replace(AppTexts.LIBRARY_FILE_FORMAT, AppTexts.PDF_FOLDER_EXTENSION);
    }

    //4. methods for Browser Control
    /**
     * <h2>downloadPdfFrom</h2>
     * <li>Downloads a PDF from the given {@link de.apaschold.demo.model.JournalArticle} URL using Selenium WebDriver in headless mode.</li>
     * <li>Starts WebDriver and navigates to {@link de.apaschold.demo.model.JournalArticle} URL</li>
     * <li>Based on publisher uses different procedure</li>
     *
     * @param articleURL the URL of the article to download the PDF from
     */

    public void downloadPdfFrom(String articleURL) throws TimeoutException {

        setDownloadPath();

        startWebDriver();

        this.driver.get(articleURL);

        articleURL = this.driver.getCurrentUrl();

        if (articleURL.contains("nature.com")){
            downloadPdfFromNature();
        } else{
            getPdfDownloadLink(articleURL);
            getPdf();
        }
    }

    /** <h2>startWebDriver</h2>
     * <li>Initializes and starts the Selenium WebDriver in headless mode.</li>
     * <li>Sets timeouts and maximizes the browser window.</li>
     * <li>Mimics user action</li></li>
     */
    private void startWebDriver() {
        this.driver = createFirefoxDriver();

        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        this.driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));

        this.driver.get(START_PAGE);
        this.driver.manage().window().maximize();

        mouseMovement();
    }

    /** <h2>downloadPdfFromNature</h2>
     * <li>Handles PDF download specifically for Nature articles.</li>
     * <li>Finds the PDF link on the page and navigates to it to trigger the download.</li>
     */
    private void downloadPdfFromNature() {
        WebElement element = this.driver.findElement(By.xpath("//a[contains(@href,'pdf')]"));

        String pdfLink = element.getAttribute("href");

        try {
            this.driver.get(pdfLink);
        } catch (TimeoutException e){
            System.out.println("Timeout occured");
        }

        this.driver.close();
    }

    /** <h2>getPdfDownloadLink</h2>
     * <li>Finds the PDF download link on the article page based on the publisher's website structure.</li>
     * <li>Download link is stored in class variable</li>
     * <li>Thread.sleep used to wait that page is loaded</li>
     * <li>Closes first driver</li>
     * <li>Supports RSC and ACS.</li>
     *
     * @param articleURL the URL of the article page
     */
    public void getPdfDownloadLink(String articleURL) {
        String pdfElementXpath = "";

        try {
            if (articleURL.contains("rsc.org")){
                pdfElementXpath = "//a[contains(text(),'Download this article')]";
            } else if (articleURL.contains("acs.org")){
                pdfElementXpath = "//a[contains(@title,'PDF')]";
            } /*else if (articleURL.contains("wiley.com")){
                pdfElementXpath = "//a[contains(@title,'PDF')]";
            }*/

            try{
                Thread.sleep(200);
            } catch (InterruptedException e){
                System.err.println(e.getMessage());
            }

            WebElement element = driver.findElement(By.xpath(pdfElementXpath));

            this.elementDownloadLink = element.getAttribute("href");

        } catch (NoSuchElementException e){
            System.err.print("Could not find download link by xpath: " + pdfElementXpath);
        }  catch (InvalidArgumentException e) {
            System.err.println("Invalid URL: " + articleURL);
        }

        this.driver.close();
    }

    /** <h2>getPdf</h2>
     * <li>2nd part of the download process</li>
     * <li>Uses the stored PDF download link to navigate to the PDF and trigger the download.</li>
     * <li>Starts WebDriver a 2nd time to avoid Bot tracking test</li>
     * <li>Closes the WebDriver after download.</li>
     */
    private void getPdf(){
        try {
            startWebDriver();

            this.driver.get(this.elementDownloadLink);

        } catch (TimeoutException e){
            System.out.println("Timeout occured");
        }
        catch (InvalidArgumentException e){
            System.err.println("Could not access pdf! " + this.elementDownloadLink);
        }

        this.driver.close();
    }

    /** <h2>mouseMovement</h2>
     * <li>Mimics human mouse movement to avoid bot detection.</li>
     * <li>Moves the mouse in a predefined pattern.</li>
     */
    private void mouseMovement(){
        List<int []> coordinates = new ArrayList<>();

        coordinates.add(new int[] {0, 0});
        coordinates.add(new int[]{0, 20});
        coordinates.add(new int[]{20, 30});
        coordinates.add(new int []{40, 0});
        coordinates.add(new int[]{60, 30});
        coordinates.add(new int[]{80, 20});
        coordinates.add(new int[]{80, 0});

        WebElement firstDiv = driver.findElement(By.xpath("//div"));

        Actions actions = new Actions(driver);
        actions.moveToElement(firstDiv).build().perform();

        for(int[] coordinate : coordinates){
            actions.moveByOffset(coordinate[0],coordinate[1])
                    .pause(Duration.ofMillis(100))
                    .build()
                    .perform();
        }
    }

    /** <h2>createFirefoxDriver</h2>
     * <li>Creates and configures a Firefox WebDriver instance for headless operation.</li>
     * <li>Sets download preferences to automatically save PDFs to the specified download path.</li>
     * <li>Randomizes the user agent to mimic different browsers.</li>
     *
     * @return a configured WebDriver instance
     */
    private WebDriver createFirefoxDriver(){
        System.setProperty("webdriver.gecko.driver", FIREFOX_DRIVER_PATH);

        FirefoxProfile firefoxProfile = new FirefoxProfile();

        firefoxProfile.setPreference("browser.download.folderList", 2);
        firefoxProfile.setPreference("browser.download.manager.showWhenStarting", false);
        firefoxProfile.setPreference("browser.download.alwaysOpenPanel", false);
        firefoxProfile.setPreference("dom.disable_open_during_load", false);
        firefoxProfile.setPreference("browser.download.dir", downloadPath);
        firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf");
        firefoxProfile.setPreference("pdfjs.disabled", true);

        FirefoxOptions options = new FirefoxOptions();
        options.addPreference("general.useragent.override", chooseRandomUserAgent());
        options.addArguments("-headless");
        options.setProfile(firefoxProfile);

        return new FirefoxDriver(options);
    }

    /** <h2>chooseRandomUserAgent</h2>
     * <li>Selects a random user agent string from a predefined list.</li>
     *
     * @return a random user agent string
     */
    private String chooseRandomUserAgent(){
        int randomIndex = new Random().nextInt(USER_AGENTS.length);

        return USER_AGENTS[randomIndex];
    }
}
