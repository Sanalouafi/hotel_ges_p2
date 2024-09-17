package main.java.dao;
import main.java.entities.Client;
import java.util.List;

public abstract class ClientDao {
    public abstract List<Client> getAllClients();
    public abstract Client getClientById(int clientId);
    public abstract void saveClient(Client client);
    public abstract void updateClient(Client client);
    public abstract void deleteClient(int clientId);
}

