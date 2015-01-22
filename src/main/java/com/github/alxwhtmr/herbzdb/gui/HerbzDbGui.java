package com.github.alxwhtmr.herbzdb.gui;


/**
 * Created on 12.01.2015.
 */
import com.github.alxwhtmr.herbzdb.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.controlsfx.dialog.Dialogs;

import java.sql.*;
import java.util.ArrayList;

public class HerbzDbGui extends Application {
    private DataManager dataManager = null;
    private boolean connected = false;
    private GridPane searchAndList = null;
    private TextArea textArea = null;
    private TextField tfSearch = null;
    private ItemListComboBox<String> itemList = null;
    private HerbzDbGui self = null;


    public HerbzDbGui() throws SQLException, ClassNotFoundException {
        self = this;
        itemList = new ItemListComboBox<String>(this);
        dataManager = new DataManager(this);
        searchAndList = new GridPane();
        textArea = new TextArea();
        connected = dataManager.isConnectionEstablished();
    }

    public DataManager getDataManager() {
        return  dataManager;
    }

    public TextArea getTextArea() {
        return textArea;
    }

    public TextField getTfSearch() {
        return  tfSearch;
    }

    public ItemListComboBox<String> getItemListComboBox() {
        return itemList;
    }

    public void setItemListComboBox(ItemListComboBox<String> list) {
        itemList = list;
    }

    public GridPane getSearchAndList() {
        return searchAndList;
    }

    private void createAndShowGui(final Stage stage) {
        BorderPane mainPane = new BorderPane();
        String topButtons[] = {
                Constants.Gui.Buttons.SHOW_ALL
        };
        GridPane top = Utils.createGridPaneWithButtons(topButtons, "bevel-grey", Pos.BASELINE_LEFT);
        for (Node n : top.getChildren()) {
            CustomButton b = (CustomButton) n;
            if (b.getName().equalsIgnoreCase(Constants.Gui.Buttons.SHOW_ALL)) {
                b.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        new ShowAllWindow(self);
                    }
                });
            }
        }

        mainPane.setTop(top);


        GridPane center = new GridPane();
        center.setAlignment(Pos.TOP_CENTER);
        center.setPadding(new Insets(0, 10, 0, 10));
        center.setVgap(30);
        searchAndList.setAlignment(Pos.CENTER);
        searchAndList.setHgap(50);
        tfSearch = new TextField();
        tfSearch.setId("textField");
        tfSearch.setMinWidth(50);

        ArrayList<String> items = new ArrayList<String>();
        try {
            items = dataManager.getTitlesFromDb();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        GridPane textAreaPane = new GridPane();
        textArea = new TextArea();
        textArea.setMinWidth(Constants.Gui.Forms.MAIN_FORM_TEXT_AREA_WIDTH);
        textArea.setMinHeight(Constants.Gui.Forms.MAIN_FORM_TEXT_AREA_HEIGHT);
        textAreaPane.add(textArea, 0, 0);


        itemList.getItems().addAll(items);
        searchAndList.add(tfSearch, 0, 0);
        searchAndList.add(itemList, 1, 0);

        GridPane statusPane = new GridPane();
        statusPane.setAlignment(Pos.CENTER);
        Label statusLabel = new Label();
        statusLabel.setId("status");
        if (connected) {
            statusLabel.setText(Constants.Gui.Labels.CONNECTED);
        }
        statusPane.add(statusLabel, 0, 0);

        center.add(statusPane, 0, 0);
        center.add(searchAndList, 0, 1);
        center.add(textAreaPane, 0, 2);

        mainPane.setCenter(center);

        String bottomButtons[] = {
                Constants.Gui.Buttons.NEW_SUBSTANCE,
                Constants.Gui.Buttons.EDIT_SUBSTANCE,
                Constants.Gui.Buttons.DELETE_SUBSTANCE
        };
        GridPane bottom = Utils.createGridPaneWithButtons(bottomButtons, "turquoise", Pos.CENTER);
        for (Node n : bottom.getChildren()) {
            CustomButton b = (CustomButton) n;
            if (b.getName().equalsIgnoreCase(Constants.Gui.Buttons.NEW_SUBSTANCE)) {
                b.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        new NewSubstanceWindow(dataManager);
                    }
                });

            } else if (b.getName().equalsIgnoreCase(Constants.Gui.Buttons.DELETE_SUBSTANCE)) {
                b.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if (itemList.getValue() != null && itemList.getValue() != "") {
                            org.controlsfx.control.action.Action response = Dialogs.create()
                                    .owner(stage)
                                    .title("Confirm delete")
                                    .masthead("Are you sure?")
                                    .message("Record \"" + itemList.getValue() + "\" will be deleted!")
                                    .showConfirm();

                            if (response == org.controlsfx.dialog.Dialog.ACTION_YES) {
                                String item = itemList.getValue();
                                dataManager.deleteData(item);
                            } else {
                                Utils.logInfo("Delete canceled");
                            }
                        } else {
                            System.out.println("Nothing to delete");
                        }

                    }
                });
            } else if (b.getName().equalsIgnoreCase(Constants.Gui.Buttons.EDIT_SUBSTANCE)) {

                b.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if (itemList.getValue() != null && itemList.getValue() != "") {
                            dataManager.editData(itemList.getValue(), tfSearch.getText(), textArea.getText());
                        } else {
                            Utils.logInfo("Nothing to edit");
                        }
                    }
                });
            }
        }

        mainPane.setBottom(bottom);


        Scene scene = new Scene(mainPane, 600, 300);
        stage.setTitle("DB client");
        stage.setScene(scene);
        stage.setMinHeight(Constants.Gui.Forms.MAIN_FORM_HEIGHT);

        Utils.setStyle(scene, Constants.Files.CSS_FILE);

        stage.show();
    }

    @Override
    public void start(Stage primaryStage) throws SQLException, ClassNotFoundException {
        createAndShowGui(primaryStage);
    }

    public static void main(String[] args) {
        Application.launch();
    }
}
