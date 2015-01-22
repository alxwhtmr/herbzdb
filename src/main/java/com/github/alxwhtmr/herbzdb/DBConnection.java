package com.github.alxwhtmr.herbzdb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created on 21.01.2015.
 */
public class DBConnection {
    private boolean connected = false;
    private String host = null;
    private String user = null;
    private String pwd = null;
    private String driver = null;
    Connection connection = null;

    public DBConnection(String host, String user, String pwd) {
        try {
            this.host = host;
            this.user = user;
            this.pwd = pwd;
            driver = Constants.DB.DRIVER;
            Class.forName(driver);
            connection = DriverManager.getConnection(host, user, pwd);
            connected = true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public String toString() {
        return "Connection: " + user + "@" + host;
    }

    public boolean isEstablished() {
        if (host != null && user != null && pwd != null && connected != false) {
            return true;
        } else {
            return false;
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
