module timberman {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens start.window to javafx.fxml;
    exports start.window;

    opens options.window to javafx.fxml;
    exports options.window;

}