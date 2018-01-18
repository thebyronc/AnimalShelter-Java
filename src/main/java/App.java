import dao.Sql2oAnimalsDao;
import models.Animals;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.staticFileLocation;

/**
 * Created by Guest on 1/17/18.
 */
public class App {
    public static void main(String[] args) {
        staticFileLocation("/public");
        String connectionString = "jdbc:h2:~/animallist.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        Sql2oAnimalsDao animalsDao = new Sql2oAnimalsDao(sql2o);

        //get: show all tasks in all categories and show all categories
        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Animals> animals = animalsDao.getAll();
            model.put("animals", animals);
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        //get: delete all tasks
        get("/animals/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            animalsDao.clearAllAnimals();
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());

        //get: show new task form
        get("/animals/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "animal-form.hbs");
        }, new HandlebarsTemplateEngine());

        //post: process new task form
        post("/animals/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            String name = req.queryParams("animalName");
            String gender = req.queryParams("animalGender");
            String type = req.queryParams("animalType");
            String breed = req.queryParams("animalBreed");
            String dateSubmitted = req.queryParams("dateSubmitted");
            Animals newAnimal = new Animals(name, gender, type, breed, dateSubmitted); //ignore the hardcoded categoryId
            animalsDao.add(newAnimal);
            model.put("animals", newAnimal);
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());

        //get: show a form to update a task
        get("/animals/:id/update", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfAnimalToEdit = Integer.parseInt(req.params("id"));
            Animals editAnimal = animalsDao.findById(idOfAnimalToEdit);
            model.put("editAnimal", editAnimal);
            return new ModelAndView(model, "animal-form.hbs");
        }, new HandlebarsTemplateEngine());

        //task: process a form to update a task
        post("/animals/:id/update", (req, res) -> { //URL to make new task on POST route
            Map<String, Object> model = new HashMap<>();
            String name = req.queryParams("animalName");
            String gender = req.queryParams("animalGender");
            String type = req.queryParams("animalType");
            String breed = req.queryParams("animalBreed");
            String dateSubmitted = req.queryParams("dateSubmitted");

            int idOfAnimalToEdit = Integer.parseInt(req.params("id"));
            Animals editAnimal = animalsDao.findById(idOfAnimalToEdit);
            animalsDao.update(idOfAnimalToEdit, name, gender, type, breed, dateSubmitted);
            return new ModelAndView(model, "update.hbs");
        }, new HandlebarsTemplateEngine());

        //get: show an individual task that is nested in a category
        get("/animals/:animal_id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfAnimalToFind = Integer.parseInt(req.params("animal_id"));
            Animals foundAnimal = animalsDao.findById(idOfAnimalToFind);
            model.put("animals", foundAnimal);
            return new ModelAndView(model, "animals-detail.hbs");
        }, new HandlebarsTemplateEngine());

        //get: delete an individual task
        get("/animals/:animal_id/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfAnimalToDelete = Integer.parseInt(req.params(":animal_id"));
            Animals deleteAnimals = animalsDao.findById(idOfAnimalToDelete);
            animalsDao.deleteById(idOfAnimalToDelete);
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());
    }
}
