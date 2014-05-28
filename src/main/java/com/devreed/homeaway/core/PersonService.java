package com.devreed.homeaway.core;

import com.devreed.homeaway.Util.JsonTranslator;
import com.devreed.homeaway.dao.Dao;
import com.devreed.homeaway.dao.PersonDao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by rreed on 5/24/2014.
 */

public class PersonService {

    private Dao dao;

    public PersonService(Dao dao){
        this.dao = dao;
    }

    public List<Person> getPerson(UUID uuid) throws Exception{
        List<Person> people = dao.getById(uuid);
        // any other service logic would go here.  Otherwise refactor to return dao.getById
        return people;
    }

    public UUID createPerson(String personStr) throws Exception {
        Person person = createPersonFromInput(personStr);
        dao.save(person);

        return person.getId();
    }

    public void deletePerson(UUID personId) throws Exception {
        dao.delete(personId);
    }

    private Person createPersonFromInput(String personStr){
        Map<String, String> attributeMap = JsonTranslator.readString(personStr);

        Person person = new Person();
        person.setId( UUID.randomUUID() );
        person.setFirstName( attributeMap.get("firstName") );
        person.setLastName(attributeMap.get("lastName"));
        person.setTitle(attributeMap.get("title"));
        person.setFactoid(attributeMap.get("factoid"));
        person.setFactoidDesc(attributeMap.get("factoidDesc"));

        return person;
    }
}
