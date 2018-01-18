package dao;
import models.Animals;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

/**
 * Created by Guest on 1/17/18.
 */
public class Sql2oAnimalsDao implements AnimalsDao {

    private final Sql2o sql2o;

    public Sql2oAnimalsDao(Sql2o sql2o){
        this.sql2o = sql2o; //making the sql2o object available everywhere so we can call methods in it
    }

    @Override
    public void add(Animals animals) {
        String sql = "INSERT INTO animals (name, gender, type, breed, dateSubmitted) VALUES (:name, :gender, :type, :breed, :dateSubmitted)"; //raw sql
        try(Connection con = sql2o.open()){ //try to open a connection
            int id = (int) con.createQuery(sql) //make a new variable
                    .addParameter("name", animals.getName())
                    .addParameter("gender", animals.getGender())
                    .addParameter("type", animals.getType())
                    .addParameter("breed", animals.getBreed())
                    .addParameter("dateSubmitted", animals.getDate())
                    .addColumnMapping("NAME", "name")
                    .addColumnMapping("GENDER", "gender")
                    .addColumnMapping("TYPE", "type")
                    .addColumnMapping("BREED", "breed")
                    .addColumnMapping("DATESUBMITTED", "dateSubmitted")
                    .executeUpdate() //run it all
                    .getKey(); //int id is now the row number (row “key”) of db
            animals.setId(id); //update object to set id now from database
        } catch (Sql2oException ex) {
            System.out.println(ex); //oops we have an error!
        }
    }

    @Override
    public List<Animals> getAll(){
        try(Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM animals") //raw sql
                    .executeAndFetch(Animals.class); //fetch a list
        }
    }

    @Override
    public Animals findById(int id){
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM animals WHERE id = :id")
                    .addParameter("id", id) //key/value pair, key must match above
                    .executeAndFetchFirst(Animals.class); //fetch an individual item
        }
    }

    @Override
    public void update(int id, String name, String gender, String type, String breed, String dateSubmitted) {
        String sql = "UPDATE animals SET (name, gender, type, breed, dateSubmitted) = (:name, :gender, :type, :breed, :dateSubmitted) WHERE id=:id"; //raw sql
        try(Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("name", name)
                    .addParameter("gender", gender)
                    .addParameter("type", type)
                    .addParameter("breed", breed)
                    .addParameter("dateSubmitted", dateSubmitted)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "Delete from animals WHERE id=:id";
        try(Connection con = sql2o.open()) {
            con.createQuery(sql).executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }


}
