package com.devreed.homeaway.dao;

import com.devreed.homeaway.Util.Encryption;
import org.apache.log4j.Logger;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by rreed on 5/24/2014.
 */
public class DbConnector {

    private static final Logger logger = Logger.getLogger(DbConnector.class);

    public static java.sql.Connection openConnection(Properties properties) throws SQLException, ClassNotFoundException {
        String userName = properties.getProperty("app.dao.userName")  == null ? "" :  properties.getProperty("app.dao.userName");
        String daoHost = properties.getProperty("app.dao.host")  == null ? "" : properties.getProperty("app.dao.host");
        String daoPath = properties.getProperty("app.dao.path")  == null ? "" : properties.getProperty("app.dao.path");
        String password = properties.getProperty("app.dao.password") == null ? "" : properties.getProperty("app.dao.password");
        String daoPort = properties.getProperty("app.dao.port") == null ? "" : properties.getProperty("app.dao.port");
        String decryptedPassword = password.equalsIgnoreCase("") ? "" : Encryption.decrypt(password);

        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://" + daoHost + ":" + daoPort + "/" + daoPath + "?allowMultiQueries=true", userName, decryptedPassword);
        } catch(SQLException e ){
            logger.error("Unable to establish a connection! Host: " + daoHost + ", port: " + daoPort + ", Path: " + daoPath + " User: " + userName + " Message: " + e.getMessage());
            logger.debug("Stack: " + e.getStackTrace().toString());
            throw e;
        } catch (ClassNotFoundException e){
            logger.error("com.mysql.jdbc.Driver not found");
            throw e;
        }
    }
}
