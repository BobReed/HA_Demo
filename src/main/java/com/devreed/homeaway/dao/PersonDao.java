package com.devreed.homeaway.dao;

import com.devreed.homeaway.Util.Encryption;
import com.devreed.homeaway.core.Person;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by rreed on 5/24/2014.
 */
public class PersonDao implements Dao {

    private static final Logger logger = Logger.getLogger(PersonDao.class);
    private Connection conn;
    private static final String GET_NAMES_QUERY = "SELECT a.idPerson, a.firstName, a.lastName FROM homeaway.people as a";

    public PersonDao(Connection conn){
        this.conn = conn;
    }

    /********/
    /* READ */
    /********/
    public List<Person> getById(UUID personId) throws SQLException {
        //todo  stored procedures are outside scope of demo
        List<Person> people = new ArrayList<Person>();

        String GET_ALL_PERSON_INFO = "SELECT a.idPerson, a.firstName, a.lastName, pos.positionname, f.factoidTopic, f.factoidDesc " +
                "FROM people as a " +
                "LEFT JOIN position as pos " +
                " ON a.idPerson = pos.idPos " +
                "   JOIN factoid as f" +
                "   ON a.idPerson = f.idFactoid";

        try {
            PreparedStatement ps = conn.prepareStatement(
                        (personId != null) ? GET_ALL_PERSON_INFO + " where a.idPerson = ? " : GET_ALL_PERSON_INFO);

            if(personId != null) {
                ps.setString(1, personId.toString());
            }

            ResultSet rs = ps.executeQuery();
            try {
                while (rs.next()) {
                    Person person = new Person();
                    person.setId(UUID.fromString(rs.getString("idPerson")));
                    person.setFirstName(rs.getString("firstName"));
                    person.setLastName(rs.getString("lastName"));
                    person.setTitle(rs.getString("positionName"));
                    person.setFactoid(rs.getString("factoidTopic"));
                    person.setFactoidDesc(rs.getString("factoidDesc"));

                    people.add(person);
                }
            } finally {
                rs.close();
            }
        } catch(SQLException e){
            logger.error("SQL exception encountered while querying person. " + e.getMessage());
            throw e;
        }

        return people;
    }

    /**********/
    /* DELETE */
    /**********/
    public void delete(UUID personId) throws SQLException{
        String deleteQuery = "DELETE FROM  people where idPerson = ?;" +
                "        DELETE FROM  position where idPos = ?;" +
                "        DELETE FROM factoid where idFactoid = ?";
        try{
            PreparedStatement ps = conn.prepareStatement(deleteQuery);
            ps.setString(1, personId.toString());
            ps.setString(2, personId.toString());
            ps.setString(3, personId.toString());

            ps.executeUpdate();
        }catch(SQLException e){
            logger.error("Delete failed: " + e.getMessage());
            throw e;
        }
    }

    /**********/
    /* INSERT */
    /**********/
    public void save(Person person) throws SQLException{
        try {
            String insertStmt = "REPLACE INTO people VALUES (?,?,?);" +
                    "REPLACE INTO factoid VALUES (?,?,?);" +
                    "REPLACE INTO position VALUES (?,?);";

            PreparedStatement ps = conn.prepareStatement(insertStmt);
            //people table
            ps.setString(1, person.getId().toString());
            ps.setString(2, person.getFirstName());
            ps.setString(3, person.getLastName());
            //factoid table
            ps.setString(4, person.getId().toString());
            ps.setString(5, person.getFactoid());
            ps.setString(6, person.getFactoidDesc());
            //position table
            ps.setString(7, person.getId().toString());
            ps.setString(8, person.getTitle());

            ps.executeUpdate();
        }catch(SQLException e){
            logger.error("Insert failed: " + e.getMessage());
            throw e;
        }
    }
}
