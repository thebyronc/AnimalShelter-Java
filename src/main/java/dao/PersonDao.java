package dao;

import models.Person;

import java.util.List;

public interface PersonDao {
    //create
    void add (Person person);

    //read
    List<Person> getAll();
    Person findById(int id);

    //update
    void update(int id, String name, String number, String type, String breed);

    //delete
    void deleteById(int id);
    void clearAllAnimals();
}
