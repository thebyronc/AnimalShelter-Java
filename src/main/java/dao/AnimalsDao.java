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
    void update(int id, String name, String gender, String type, String breed, String dateSubmitted);
    //update
    Animals findById(int id);
    //delete
}
