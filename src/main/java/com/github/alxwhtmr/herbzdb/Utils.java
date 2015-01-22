package com.github.alxwhtmr.herbzdb;

import com.github.alxwhtmr.herbzdb.gui.CustomButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;

import java.io.File;
import java.net.URI;
import java.util.Date;

/**
 * Created on 21.01.2015.
 */
public class Utils {
    public static void log(Object o) {
        if (Constants.LOGGING == true) {
            System.out.println("[LOG] " + new Date() + ": " + o);
        }
    }

    public static void log(String title, Object o) {
        if (Constants.LOGGING == true) {
            System.out.println("[LOG] " + new Date() + ": " + title + ": " + o);
        }
    }

    public static void logErr(Object o) {
        System.out.println("[ERROR] " + new Date() + ": " + o);
    }

    public static void logInfo(Object o) {
        System.out.println("[INFO] " + new Date() + ": " + o);
    }

    public static void setStyle(Scene scene, String css) {
        URI uri = null;
        File fname = new File(css);
        uri = fname.toURI();
        scene.getStylesheets().add(uri.toString());
    }

    public static CustomButton[] createRowOfButtons(String[] btns, String style) {
        CustomButton[] buttons = new CustomButton[btns.length];
        for (int i = 0; i < btns.length; i++) {
            if (style != null) {
                buttons[i] = new CustomButton(btns[i], style);
            } else {
                buttons[i] = new CustomButton(btns[i]);
            }
        }
        return buttons;
    }

    public static GridPane createGridPaneWithButtons(String[] buttons, String style, Pos alignment) {
        CustomButton[] buttonsObj = createRowOfButtons(buttons, style);
        GridPane grid = new GridPane();
        grid.setAlignment(alignment);
        grid.setPadding(new Insets(11, 12, 20, 14));
        grid.setHgap(10);
        grid.addRow(0, buttonsObj);
        return grid;
    }
}
