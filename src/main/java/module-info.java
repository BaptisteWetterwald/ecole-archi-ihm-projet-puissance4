module ensisa.connect4 {
    requires javafx.controls;
    requires javafx.fxml;


    opens ensisa.connect4 to javafx.fxml;
    exports ensisa.connect4;
    exports ensisa.connect4.controller;
    opens ensisa.connect4.controller to javafx.fxml;
    exports ensisa.connect4.view;
    opens ensisa.connect4.view to javafx.fxml;
}