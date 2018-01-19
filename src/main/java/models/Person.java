package models;

import java.util.Objects;

public class Person {
    private String name;
    private String number;
    private String type;
    private String breed;
    private int id;

    public Person(String name, String number, String type, String breed) {
        this.name = name;
        this.number = number;
        this.type = type;
        this.breed = breed;
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
    public String getNumber(){
        return this.number;
    }
    public String getType(){
        return this.type;
    }
    public String getBreed(){
        return this.breed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(name, person.name) &&
                Objects.equals(number, person.number) &&
                Objects.equals(type, person.type) &&
                Objects.equals(breed, person.breed);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, number, type, breed);
    }
}
