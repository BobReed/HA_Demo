package com.devreed.homeaway.util;

import com.devreed.homeaway.Util.JsonTranslator;
import com.devreed.homeaway.core.Person;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

import java.util.UUID;

/**
 * Created by rreed on 5/26/2014.
 */
public class JsonTranslatorTest {

    public Person person;

    @Before
    public void setup(){
        person = new Person();
        person.setFirstName("Bob");
        person.setLastName("Reed");
        person.setFactoid("What Bob likes to do in his spare time:");
        person.setFactoidDesc("Bob likes to work on his Toyota Supra and race it at Cecil County Dragway");
    }

    @Test
    public void testPersonTranslate(){
        UUID uuid = UUID.randomUUID();
        person.setId(uuid);

        String str = JsonTranslator.toJson(person);
        assertTrue(str.contains( "\"firstName\":\"Bob\"" ));
        assertTrue(str.contains( "\"lastName\":\"Reed\"" ));
        assertTrue(str.contains( "\"factoid\":\"What Bob likes to do in his spare time:\"" ));
        assertTrue(str.contains( "\"factoidDesc\":\"Bob likes to work on his Toyota Supra and race it at Cecil County Dragway\"" ));
    }
}
