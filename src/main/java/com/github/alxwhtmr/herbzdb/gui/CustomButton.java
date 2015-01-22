package com.github.alxwhtmr.herbzdb.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

/**
 * Created on 21.01.2015.
 */
public class CustomButton extends Button {


    private String name = null;

    public CustomButton(String btName, String style) {
        super(btName);
        name = btName;
        setId(style);
    }

    public CustomButton(String btName) {
        super(btName);
        name = btName;
    }

    {
        setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println(name + " pushed");
            }
        });
    }

    public String getName() {
        return name;
    }
}
