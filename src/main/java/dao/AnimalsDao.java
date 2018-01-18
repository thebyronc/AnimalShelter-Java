package dao;

import models.Animals;

import java.util.List;

/**
 * Created by Guest on 1/17/18.
 */
public interface AnimalsDao {

    //create
    void add (Animals animals);

    //read
    List<Animals> getAll();
    Animals findById(int id);

    //update
    void update(int id, String name, String gender, String type, String breed, String dateSubmitted);

    //delete
    void deleteById(int id);
    void clearAllAnimals();

}
