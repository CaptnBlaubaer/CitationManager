module de.apaschold.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires PDFViewerFX;
    requires com.google.gson;
    requires org.json;
    requires java.sql;
    requires org.seleniumhq.selenium.api;
    requires org.seleniumhq.selenium.edge_driver;
    requires org.seleniumhq.selenium.firefox_driver;
    requires java.desktop;
    requires org.seleniumhq.selenium.support;


    opens de.apaschold.demo to javafx.fxml;
    exports de.apaschold.demo;
    exports de.apaschold.demo.gui;
    opens de.apaschold.demo.gui to javafx.fxml;
    exports de.apaschold.demo.gui.addnewarticleview;
    opens de.apaschold.demo.gui.addnewarticleview to javafx.fxml;
    exports de.apaschold.demo.additionals;
    opens de.apaschold.demo.additionals to javafx.fxml;
    exports de.apaschold.demo.gui.mainview;
    opens de.apaschold.demo.gui.mainview to javafx.fxml;
}