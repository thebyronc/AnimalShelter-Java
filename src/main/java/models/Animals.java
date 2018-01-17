package models;

import java.time.LocalDateTime;

/**
 * Created by Guest on 1/17/18.
 */
public class Animals {
    private String name;
    private String gender;
    private String type;
    private String breed;
    private int id;
    private String dateSubmitted;

    public Animals (String name, String gender, String type, String breed, String dateSubmitted) {
        this.name = name;
        this.gender = gender;
        this.type = type;
        this.breed = breed;
        this.dateSubmitted = dateSubmitted;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public String getGender(){
        return this.gender;
    }
    public String getType(){
        return this.type;
    }
    public String getBreed(){
        return this.breed;
    }
    public String getDate(){
        return this.dateSubmitted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Animals animals = (Animals) o;

        if (id != animals.id) return false;
        if (!name.equals(animals.name)) return false;
        if (!gender.equals(animals.gender)) return false;
        if (!type.equals(animals.type)) return false;
        return breed.equals(animals.breed);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + gender.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + breed.hashCode();
        result = 31 * result + id;
        return result;
    }
}
