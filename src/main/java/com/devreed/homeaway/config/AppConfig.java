package com.devreed.homeaway.config;


import com.devreed.homeaway.core.PersonService;
import com.devreed.homeaway.dao.DbConnector;
import com.devreed.homeaway.dao.PersonDao;
import com.devreed.homeaway.rest.RestResource;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by rreed on 5/24/2014.
 */
@Configuration
public class AppConfig {

    protected static final Logger logger = Logger.getLogger(AppConfig.class);

    @Bean
    public Properties appProperties(){
        Properties properties = null;
        logger.info("loading properties file: app.properties");
        ClassPathResource resource = new ClassPathResource("/app.properties");

        try{
            properties = PropertiesLoaderUtils.loadProperties(resource);
        } catch (IOException e) {
            logger.error("Failed to load properties file: " + e.toString());
            e.printStackTrace();
        }

        return properties;
    }

    @Bean
    public Connection dbConnection() throws IOException, SQLException, ClassNotFoundException {
        return DbConnector.openConnection(appProperties());
    }

    @Bean
    public PersonService personService() throws IOException, SQLException, ClassNotFoundException {
        logger.info("instantiating rest resources");
        return new PersonService( new PersonDao(dbConnection()) );
    }

    @Bean
    public RestResource restResource() throws IOException, SQLException, ClassNotFoundException {
        logger.info("instantiating rest resources");
        return new RestResource(personService());
    }
}
