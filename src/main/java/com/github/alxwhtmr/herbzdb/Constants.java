package com.github.alxwhtmr.herbzdb;

/**
 * Created on 15.01.2015.
 */
public class Constants {
    public static final boolean LOGGING = true;

    public class DB {
        public static final boolean CREATE_NEW = true;
        public static final boolean DROP = true;


        public static final String ROOT = "root";
        public static final String ROOT_PWD = "admin";
        public static final String DRIVER = "com.mysql.jdbc.Driver";
        public static final String DB_NAME = "javadb";
        public static final String HOST_PREFFIX = "jdbc:mysql://";
        public static final String HOST = "localhost/";
        public static final String USER = ROOT;
        public static final String PWD = ROOT_PWD;



        public class Table {
            public static final String TABLE_NAME = "substance";
            public static final String ID = "id" + TABLE_NAME;
            public static final String SUBST_TITLE = "substance";
            public static final String SUBST_DESCR = "description";
        }


    }

    public class Gui {
        public static final int COMBOBOX_HEIGHT = 33;

        public class Forms {
            public static final int MAIN_FORM_TEXT_AREA_WIDTH = 300;
            public static final int LETTERS_IN_TEXT_ROW = MAIN_FORM_TEXT_AREA_WIDTH / 5;
            public static final int MAIN_FORM_TEXT_AREA_HEIGHT = 300;
            public static final int MAIN_FORM_HEIGHT = MAIN_FORM_TEXT_AREA_HEIGHT * 2;

            public static final int SHOW_ALL_FORM_WIDTH = 300;
            public static final int SHOW_ALL_FORM_HEIGHT = 420;
            public static final int SHOW_ALL_TEXTAREA_WIDTH = SHOW_ALL_FORM_WIDTH;
            public static final int SHOW_ALL_TEXTAREA_HEIGHT = SHOW_ALL_FORM_HEIGHT - 20;

            public static final int NEW_SUBSTANCE_DESCRIPTION_WIDTH = 100;
            public static final int NEW_SUBSTANCE_DESCRIPTION_HEIGHT = 100;
            public static final double NEW_SUBSTANCE_FORM_WIDTH = NEW_SUBSTANCE_DESCRIPTION_WIDTH * 5.5;
            public static final int NEW_SUBSTANCE_FORM_HEIGHT = NEW_SUBSTANCE_DESCRIPTION_HEIGHT * 2;

            public static final int STATUS_CONFIRMED = 1;
            public static final int STATUS_UNCONFIRMED = 0;
        }

        public class Labels {
            public static final String CONNECTED = "#Connected as " + DB.USER + "@" + DB.HOST + DB.DB_NAME;
            public static final String NOT_CONNECTED = "#Not connected";
        }

        public class Buttons {
            public static final String EDIT_CONNECTION = "Edit connection";
            public static final String SHOW_ALL = "Show all";
            public static final String HELP = "Help";
            public static final String NEW_SUBSTANCE = "New";
            public static final String EDIT_SUBSTANCE = "*Instant* Edit";
            public static final String DELETE_SUBSTANCE = "Delete";

            public static final String SAVE_SUBSTANCE = "Save";
            public static final String CANCEL = "Cancel";
            public static final String OK = "Ok";
        }


    }
    public class Files {
        public static final String CSS_FILE = "resources/Style.css";
    }


}
