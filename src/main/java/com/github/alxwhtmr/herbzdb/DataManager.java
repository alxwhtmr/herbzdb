package com.github.alxwhtmr.herbzdb;

import com.github.alxwhtmr.herbzdb.gui.HerbzDbGui;
import com.github.alxwhtmr.herbzdb.gui.ItemListComboBox;
import javafx.scene.layout.GridPane;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created on 21.01.2015.
 */
public class DataManager {
    private DBConnection dbConnection = null;
    private String host = null;
    String db = null;
    String table = null;
    String id = null;
    String title = null;
    String description = null;
    ItemListComboBox<String> itemList = null;
    HerbzDbGui parentGui;

    public DataManager(HerbzDbGui gui) {
        db = Constants.DB.DB_NAME;
        table = Constants.DB.Table.TABLE_NAME;
        id = Constants.DB.Table.ID;
        title = Constants.DB.Table.SUBST_TITLE;
        description = Constants.DB.Table.SUBST_DESCR;
        parentGui = gui;
        itemList = gui.getItemListComboBox();

        if (Constants.DB.CREATE_NEW == true) {
            host = Constants.DB.HOST_PREFFIX + Constants.DB.HOST;
            dbConnection = new DBConnection(
                host, Constants.DB.ROOT, Constants.DB.ROOT_PWD
            );
            if (Constants.DB.DROP == true) {
                dropDB(Constants.DB.DB_NAME);
            }
            createDB(Constants.DB.DB_NAME);
        }
        dbConnection = new DBConnection(
                Constants.DB.HOST_PREFFIX + Constants.DB.HOST + Constants.DB.DB_NAME,
                Constants.DB.USER,
                Constants.DB.PWD
        );
    }

    public DBConnection getDBConnection() {
        return dbConnection;
    }

    public boolean isConnectionEstablished() {
        return dbConnection.isEstablished();
    }

    public void dropDB(String dbName) {
        Utils.log("DataManager.dropDB", dbName);
        Statement stmt = null;

        try {
            stmt = dbConnection.getConnection().createStatement();
            String sql = "DROP DATABASE `"+dbName+"`";
            stmt.executeUpdate(sql);
            System.out.println("Database " + dbName + " dropped");
        } catch (SQLException e) {
            Utils.logErr(e);
        }
    }

    public void createDB(String dbName) {
        Utils.log("DataManager.createDB", dbName);
        Statement stmt = null;

        try {
            stmt = dbConnection.getConnection().createStatement();
            String createDatabase = "CREATE DATABASE " + dbName;

            String createTable = "CREATE TABLE `" +
                    dbName+"`.`"+table+"`(\n" +
                    "  `"+id+"` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `" +title+ "` VARCHAR(45) NULL,\n" +
                    "  `" +description+ "` VARCHAR(500) NULL,\n" +
                    "  PRIMARY KEY (`"+id+"`),\n" +
                    "  UNIQUE INDEX `"+id+"_UNIQUE` (`"+id+"` ASC),\n" +
                    "  UNIQUE INDEX `"+title+"_UNIQUE` (`"+title+"` ASC));";

            stmt.executeUpdate(createDatabase);
            stmt.executeUpdate(createTable);

            for (int i = 1; i < 30; i++) {
                String fillTable = "INSERT INTO `" + dbName + "`.`" + table + "` (`" + title + "`, `" + description + "`) VALUES ('" +
                        title + i + "', '" +
                        description + Math.random()*i + "');";
                stmt.executeUpdate(fillTable);
            }
            System.out.println("Database " + dbName + " created");
            System.out.println("Table " + table + " created");
        } catch (SQLException e) {
            Utils.logErr(e);
            return;
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if(stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if(dbConnection.getConnection() != null)
                    dbConnection.getConnection().close();
            } catch(SQLException se) {
                se.printStackTrace();
            }//end finally try
        }

    }

    public void addData(String titleData, String descriptionData) {
        Utils.log("DataManager.addData", title + ":" + titleData);
        Statement stmt = null;
        GridPane searchAndList = parentGui.getSearchAndList();

        try {
            stmt = dbConnection.getConnection().createStatement();
            String sql = "INSERT INTO `"+db+"`.`"+table+"` " +
                    "(`"+title+"`, `"+description+"`) VALUES " +
                    "('"+titleData+"', '"+descriptionData+"');";
            stmt.executeUpdate(sql);
            itemList.getItems().add(titleData);
            String[] items = itemList.getItems().toString().replaceAll("\\[|\\]| ", "").split(",");
            searchAndList.getChildren().remove(itemList);
            ItemListComboBox<String> newComboBox = new ItemListComboBox<String>(parentGui);
            parentGui.setItemListComboBox(newComboBox);
            newComboBox.getItems().addAll(items);
            searchAndList.add(newComboBox, 1, 0);

        } catch (SQLException e) {
            Utils.logErr(e);
        }
    }

    public void deleteData(String data) {
        Utils.log("DataManager.deleteData", data);
        Statement stmt = null;
        ItemListComboBox<String> itemList = parentGui.getItemListComboBox();
        GridPane searchAndList = parentGui.getSearchAndList();

        if (itemList.getValue() != "") {
            try {
                stmt = dbConnection.getConnection().createStatement();
                String sql = "DELETE FROM `" + db + "`.`" + table + "` WHERE `" + title + "`='" + data + "';\n";
                stmt.executeUpdate(sql);
                itemList.getItems().remove(data);
                String[] items = itemList.getItems().toString().replaceAll("\\[|\\]| ", "").split(",");
                searchAndList.getChildren().remove(itemList);
                ItemListComboBox<String> newComboBox = new ItemListComboBox<String>(parentGui);
                parentGui.setItemListComboBox(newComboBox);
                newComboBox.getItems().addAll(items);
                searchAndList.add(newComboBox, 1, 0);

                Utils.logInfo(data + " deleted");
            } catch (SQLException e) {
                Utils.logErr(e);
            }
        } else {
            System.out.println("Nothing to delete");
        }
    }

    public void editData(String titleData, String newTitleData, String newDescrData) {
        Utils.log("DataManager.editData", titleData);
        Statement stmt = null;
        ItemListComboBox<String> itemList = parentGui.getItemListComboBox();
        newDescrData = newDescrData.replaceAll("\'", "\\`");

        GridPane searchAndList = parentGui.getSearchAndList();

        try {
            stmt = dbConnection.getConnection().createStatement();
            String sql = "UPDATE `"+db+"`.`"+table+"` " +
                    "SET `"+title+"`='"+newTitleData+"', " +
                    "`"+description+"`='"+newDescrData+"' " +
                    "WHERE `"+title+"`='"+titleData+"';\n";
            Utils.log(sql);
            stmt.executeUpdate(sql);
            itemList.setValue(newTitleData);


            String[] items = itemList.getItems().toString().replaceAll("\\[|\\]| ", "").replaceAll(titleData, newTitleData).split(",");
            searchAndList.getChildren().remove(itemList);
            ItemListComboBox<String> newComboBox = new ItemListComboBox<String>(parentGui);
            parentGui.setItemListComboBox(newComboBox);
            newComboBox.getItems().addAll(items);
            searchAndList.add(newComboBox, 1, 0);

            Utils.logInfo("Record \'" + titleData + "\' edited");
        } catch (SQLException e) {
            Utils.logErr(e);
        }
    }

    public ArrayList<String> getTitlesFromDb() throws SQLException, ClassNotFoundException {
        ArrayList<String> data = new ArrayList<String>();

        Statement statement = dbConnection.getConnection().createStatement();

        ResultSet resultSet = statement.executeQuery("select " +
                Constants.DB.Table.SUBST_TITLE +
                ", " + Constants.DB.Table.SUBST_DESCR +
                " from " + Constants.DB.Table.TABLE_NAME);

        while (resultSet.next()) {
            data.add(resultSet.getString(1));
        }
        return data;
    }


    public String getSubstDescription(String substance) throws SQLException {
        String descr = null;

        Statement statement = dbConnection.getConnection().createStatement();

        ResultSet resultSet = statement.executeQuery("select " +
                Constants.DB.Table.SUBST_DESCR +
                " from " +
                Constants.DB.Table.TABLE_NAME +
                " where " +
                Constants.DB.Table.SUBST_TITLE + "=\'" + substance + "\'");

        if (resultSet.next()) {
            StringBuffer buf = new StringBuffer();
            String unedited = resultSet.getString(1);
            int counter = 0;
            for (int i = 0 ; i < unedited.length(); i++) {
                if (counter != Constants.Gui.Forms.LETTERS_IN_TEXT_ROW) {
                    buf.append(unedited.charAt(i));
                } else {
                    counter = 0;
                    while (unedited.charAt(i) != ' ') {
                        buf.append(unedited.charAt(i++));
                    }
                    buf.append("\n");
                    continue;
                }
                counter++;
            }
            descr = buf.toString();
        }
        return descr;
    }
}
