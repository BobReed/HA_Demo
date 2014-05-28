package com.devreed.homeaway.dao;

import com.devreed.homeaway.core.Person;

import java.util.List;
import java.util.UUID;

/**
 * Created by rreed on 5/24/2014.
 */
public interface Dao {
    public List<Person> getById(UUID personId) throws Exception;
    public void save(Person person) throws Exception;
    public void delete(UUID personId) throws Exception;
}
