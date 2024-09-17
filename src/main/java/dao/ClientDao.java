package main.java.dao;
import main.java.entities.Client;
import java.util.List;

public interface ClientDao {
    public  List<Client> getAllClients();
    public  Client getClientById(int clientId);
    public  void saveClient(Client client);
    public  void updateClient(Client client);
    public  void deleteClient(int clientId);
}

