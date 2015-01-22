package com.github.alxwhtmr.herbzdb.gui;

import com.github.alxwhtmr.herbzdb.Constants;
import com.github.alxwhtmr.herbzdb.DataManager;
import com.github.alxwhtmr.herbzdb.Utils;
import com.github.alxwhtmr.herbzdb.gui.CustomButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Created on 21.01.2015.
 */
public class NewSubstanceWindow {
    private static boolean isOpened = false;
    private DataManager dataManager = null;
    private  TextField titleTextField = null;
    TextArea descriptionTextArea = null;

    public NewSubstanceWindow(DataManager dataManager) {
        this.dataManager = dataManager;
        createAndShowGui();
        Utils.log(dataManager.getDBConnection());
    }

    private void createAndShowGui() {
        final Stage stage = new Stage();

        BorderPane mainPane = new BorderPane();
        mainPane.setMinWidth(Constants.Gui.Forms.NEW_SUBSTANCE_FORM_WIDTH);
        mainPane.setMinHeight(Constants.Gui.Forms.NEW_SUBSTANCE_FORM_HEIGHT);

        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);

        GridPane titlePane = new GridPane();
        Label titleLabel = new Label("Title");
        titleLabel.setId("new_substance");
        titleTextField = new TextField();
        titleTextField.setMinWidth(50);
        titlePane.add(titleLabel, 0, 0);
        titlePane.add(titleTextField, 0, 1);

        GridPane descriptionPane = new GridPane();
        Label descriptionLabel = new Label("Description");
        descriptionLabel.setId("new_substance");
        descriptionTextArea = new TextArea();
        descriptionTextArea.setMinWidth(Constants.Gui.Forms.NEW_SUBSTANCE_DESCRIPTION_WIDTH);
        descriptionTextArea.setMinHeight(Constants.Gui.Forms.NEW_SUBSTANCE_DESCRIPTION_HEIGHT);
        descriptionPane.add(descriptionLabel, 0, 0);
        descriptionPane.add(descriptionTextArea, 0, 1);

        gridPane.add(titlePane, 0, 0);
        gridPane.add(descriptionPane, 0, 1);

        gridPane.setAlignment(Pos.CENTER);

        String bottomButtonsNames[] = {
                Constants.Gui.Buttons.SAVE_SUBSTANCE,
                Constants.Gui.Buttons.CANCEL
        };
        GridPane bottomButtons = Utils.createGridPaneWithButtons(bottomButtonsNames, "glass-grey", Pos.BASELINE_CENTER);
        for (Node n : bottomButtons.getChildren()) {
            CustomButton b = (CustomButton) n;
            if (b.getName().equalsIgnoreCase(Constants.Gui.Buttons.CANCEL)) {
                b.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        isOpened = false;
                        stage.close();
                    }
                });
            } else if (b.getName().equalsIgnoreCase(Constants.Gui.Buttons.SAVE_SUBSTANCE)) {
                b.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        String titleData = titleTextField.getText();
                        String descriptionData = descriptionTextArea.getText();
                        dataManager.addData(titleData, descriptionData);
                        Utils.logInfo("Added new record: \'" + titleData + "\'");
                        isOpened = false;
                        stage.close();
                    }
                });
            }
        }


        mainPane.setTop(gridPane);
        mainPane.setBottom(bottomButtons);

        Scene scene = new Scene(mainPane);
        stage.setTitle("New Substance");
        stage.setScene(scene);
        Utils.setStyle(scene, Constants.Files.CSS_FILE);

        stage.setOnShown(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                isOpened = true;
            }
        });
        if (isOpened == false) {
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } else {
            System.out.println(this + " already opened");
        }
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                System.out.println("Stage is closing");
                isOpened = false;
            }
        });

    }


}