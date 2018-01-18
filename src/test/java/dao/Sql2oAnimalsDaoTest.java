package dao;

import models.Animals;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.time.LocalDateTime;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;

/**
 * Created by Guest on 1/17/18.
 */
public class Sql2oAnimalsDaoTest {

    private Sql2oAnimalsDao animalsDao; //ignore me for now
    private Connection conn; //must be sql2o class conn

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        animalsDao = new Sql2oAnimalsDao(sql2o); //ignore me for now

        //keep connection open through entire test so it does not get erased.
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void animalObjectIsCreatedCorrectly() throws Exception {
        Animals animals = setupNewAnimal();
        assertEquals(true, animals instanceof Animals);
    }
    @Test
    public void addingAnimalSetsId() throws Exception {
        Animals animals = new Animals("Mocha", "Male", "Dog", "Shepherd", "01/17/2018");
        int originalAnimalsId = animals.getId();
        animalsDao.add(animals);
        assertNotEquals(originalAnimalsId, animals.getId());
    }
    @Test
    public void updateChangesAnimalBreed() throws Exception {
        String initialBreed = "Golden";
        Animals animals = new Animals("Name", "Gender", "Animal" , initialBreed, "01/17/2018");
        animalsDao.add(animals);

        animalsDao.update(animals.getId(), "Mocha", "Male", "Dog", "Shepherd", "01/17/2018");
        Animals updatedAnimal = animalsDao.findById(animals.getId());
        assertNotEquals(initialBreed, updatedAnimal.getBreed());
    }
    @Test
    public void deleteAnimalById() throws Exception {
        Animals animals = setupNewAnimal();
        animalsDao.add(animals);
        animalsDao.deleteById(animals.getId());
        assertEquals(0, animalsDao.getAll().size());
    }
    @Test
    public void clearAllClearsAll() throws Exception {
        Animals animals = setupNewAnimal();
        Animals otherAnimals = new Animals("Mocha", "Male", "Dog", "Shepherd", "01/17/2018");
        animalsDao.add(animals);
        animalsDao.add(otherAnimals);
        int daoSize = animalsDao.getAll().size();
        animalsDao.clearAllAnimals();
        assertTrue(daoSize > 0 && daoSize > animalsDao.getAll().size()); //this is a little overcomplicated, but illustrates well how we might use `assertTrue` in a different way.
    }

    public Animals setupNewAnimal(){
        return new Animals("Mocha", "Male", "Dog", "Shepherd", "01/17/2018");
    }
}