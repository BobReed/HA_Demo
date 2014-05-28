package com.devreed.homeaway.com.devreed.homeaway.core;

import com.devreed.homeaway.config.AppConfig;
import com.devreed.homeaway.core.Person;
import com.devreed.homeaway.core.PersonService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

/**
 * Created by rreed on 5/26/2014.
 */

//These tests are dependant on the DB state.  They were mostly used to test logic.  Assertions will fail if
//# of records changes in the DB.  Tests are commented out, but left here for demo purposes
public class PersonServiceTest {

    ApplicationContext context;
    PersonService service;

    @Before
    public void setup(){
        context = new AnnotationConfigApplicationContext(AppConfig.class);
        service = (PersonService) context.getBean("personService");
    }

    /*
        Valid Test Id's
        65b48bbc-e35d-461d-a44f-45095a895c1c
        a2076b47-5b14-4c43-85fa-a42ccb680769
        ebe5e24d-39b4-460d-b2d7-4bfc7531933c
     */

    //@Test
    public void testPersonService(){
        try {
            List<Person> people;
            Person person;
            people = service.getPerson(UUID.fromString("65b48bbc-e35d-461d-a44f-45095a895c1c"));

            person = people.get(0);
            assertEquals(person.getFirstName(), "Carl");
            assertEquals(person.getLastName(), "Shepherd");

            people = service.getPerson(UUID.fromString("a2076b47-5b14-4c43-85fa-a42ccb680769"));
            person = people.get(0);
            assertEquals(person.getFirstName(), "Brian");
            assertEquals(person.getLastName(), "Sharples");

            people = service.getPerson(UUID.fromString("ebe5e24d-39b4-460d-b2d7-4bfc7531933c"));
            person = people.get(0);
            assertEquals(person.getFirstName(), "Brent");
            assertEquals(person.getLastName(), "Bellm");
        } catch(Exception e){
            fail();
        }

    }

    //@Test
    public void testGetAllPeople(){
        try {
            List<Person> people;
            Person person;
            people = service.getPerson(null);
            assertEquals(people.size(), 3);
        } catch(Exception e){
            fail();
        }
    }

    //@Test
    public void testCreateAndDeletePerson(){
        UUID personId = null;
        String personJson = "    {\n" +
                "        \"factoidDesc\": \"Bob likes to work on his Toyota Supra and race it at Cecil County Dragway\",\n" +
                "        \"title\": \"Newest Employee at HomeAway?\",\n" +
                "        \"lastName\": \"Reed\",\n" +
                "        \"firstName\": \"Bob\",\n" +
                "        \"factoid\": \"What Bob likes to do in his spare time:\"\n" +
                "    }";
        try {

            personId = service.createPerson(personJson);
            assertNotNull(personId);
        } catch(Exception e){
            fail();
        }

        try {
            service.deletePerson(personId);
        }catch(Exception e){
            fail();
        }
    }
}
