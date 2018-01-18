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
        post("/animals/new", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            String name = request.queryParams("animalName");
            String gender = request.queryParams("animalGender");
            String type = request.queryParams("animalType");
            String breed = request.queryParams("animalBreed");
            String dateSubmitted = request.queryParams("dateSubmitted");
            Animals newAnimal = new Animals(name, gender, type, breed, dateSubmitted); //ignore the hardcoded categoryId
            animalsDao.add(newAnimal);
            model.put("animals", newAnimal);
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());

        //get: show a form to update a task
        get("/tasks/update", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfTaskToEdit = Integer.parseInt(req.params("id"));
            Animals editAnimals = animalsDao.findById(idOfTaskToEdit);
            model.put("editAnimals", editAnimals);
            return new ModelAndView(model, "task-form.hbs");
        }, new HandlebarsTemplateEngine());

        //post: process a form to update a task
        post("/tasks/update", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            String newContent = req.queryParams("description");
            int idOfAnimalToEdit = Integer.parseInt(req.queryParams("id"));
            Animals editAnimals = animalsDao.findById(idOfAnimalToEdit);
            animalsDao.update(idOfAnimalToEdit, newContent, 1); //ignore the hardcoded categoryId for now.
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());

        //get: delete an individual task
        get("categories/:category_id/tasks/:task_id/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfAnimalToDelete = Integer.parseInt(req.params("task_id"));
            Animals deleteAnimals = animalsDao.findById(idOfAnimalToDelete);
            animalsDao.deleteById(idOfAnimalToDelete);
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());
    }
}
