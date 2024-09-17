package main.java.dao;
import main.java.entities.Person;
import java.util.List;

public abstract class PersonDao {
    public abstract List<Person> getAllPersons();
    public abstract Person getPersonById(int personId);
    public abstract void savePerson(Person person);
    public abstract void updatePerson(Person person);
    public abstract void deletePerson(int personId);
}
