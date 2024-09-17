package main.java.dao;
import main.java.entities.Person;
import java.util.List;

public interface PersonDao {
    public  List<Person> getAllPersons();
    public  Person getPersonById(int personId);
    public  void savePerson(Person person);
    public  void updatePerson(Person person);
    public  void deletePerson(int personId);
}
