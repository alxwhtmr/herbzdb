package com.github.alxwhtmr.herbzdb.gui;

import com.github.alxwhtmr.herbzdb.Constants;
import com.github.alxwhtmr.herbzdb.Utils;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created on 22.01.2015.
 */
public class ShowAllWindow {
    private HerbzDbGui parentGui = null;

    public ShowAllWindow(HerbzDbGui gui) {
        parentGui = gui;
        Stage stage = new Stage();
        FlowPane mainPane = new FlowPane();
        mainPane.setId("show_all");
        mainPane.setMinSize(Constants.Gui.Forms.SHOW_ALL_FORM_WIDTH, Constants.Gui.Forms.SHOW_ALL_FORM_HEIGHT);
        mainPane.setVgap(10);
        mainPane.setOrientation(Orientation.VERTICAL);
        mainPane.setPadding(new Insets(10, 10, 0, 10));


        ArrayList<String> allItems = new ArrayList<String>();
        try {
            allItems = parentGui.getDataManager().getTitlesFromDb();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setMinSize(Constants.Gui.Forms.SHOW_ALL_TEXTAREA_WIDTH, Constants.Gui.Forms.SHOW_ALL_TEXTAREA_HEIGHT);
        textArea.setMaxSize(Constants.Gui.Forms.SHOW_ALL_TEXTAREA_WIDTH, Constants.Gui.Forms.SHOW_ALL_TEXTAREA_HEIGHT);
        for (String item : allItems) {
            textArea.appendText(item + "\n");
        }
        textArea.setId("show_all");
        mainPane.getChildren().add(textArea);


        Scene scene = new Scene(mainPane);
        Utils.setStyle(scene, Constants.Files.CSS_FILE);
        stage.setTitle("Show all");
        stage.setScene(scene);
        stage.show();
        textArea.setScrollTop(0);
    }
}
