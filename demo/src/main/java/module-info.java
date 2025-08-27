module de.apaschold.demo {
    requires javafx.controls;
    requires javafx.fxml;


    opens de.apaschold.demo to javafx.fxml;
    exports de.apaschold.demo;
    exports de.apaschold.demo.gui;
    opens de.apaschold.demo.gui to javafx.fxml;
    exports de.apaschold.demo.gui.mainview;
    opens de.apaschold.demo.gui.mainview to javafx.fxml;
    exports de.apaschold.demo.gui.addnewarticleview;
    opens de.apaschold.demo.gui.addnewarticleview to javafx.fxml;
}