module timberman {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.media;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens start.window to javafx.fxml;
    exports start.window;

    opens result.window to javafx.fxml;
    exports result.window;

}