package com.github.alxwhtmr.herbzdb.gui;

import com.github.alxwhtmr.herbzdb.Constants;
import com.github.alxwhtmr.herbzdb.Utils;
import com.github.alxwhtmr.herbzdb.gui.HerbzDbGui;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;

import java.sql.SQLException;

/**
 * Created on 22.01.2015.
 */
public class ItemListComboBox<String> extends ComboBox<String> {
    private HerbzDbGui parentGui = null;

    public ItemListComboBox(HerbzDbGui gui) {
        this.setMinHeight(Constants.Gui.COMBOBOX_HEIGHT);
        parentGui = gui;
        this.setVisibleRowCount(5);
    }

    {
        setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    parentGui.getTextArea().setText(parentGui.getDataManager().getSubstDescription(parentGui.getItemListComboBox().getValue()));
                    parentGui.getTfSearch().setText(parentGui.getItemListComboBox().getValue());
                } catch (SQLException e) {
                    Utils.logErr(e);
                }
            }
        });
    }

}
