package api.services;

import api.model.Client;

import java.util.HashSet;
import java.util.Objects;


public class AllClients {
    HashSet<Client> clients;
    public AllClients() {
        clients = new HashSet<>();
    }
    public boolean addClient(Client client) {
        return clients.add(client);
    }
    public Client searchClientByAFM(String AFM) {
        for (Client c : clients) {
            if(c.getAFM().equals(AFM)) {
                return c;
            }
        }
        return null;
    }
    public HashSet<Client> searchByName(String firstName, String lastName) {
        HashSet<Client> clientsByName = new HashSet<>();
        for (Client c : clients) {
            if(firstName.equals(c.getFirst_name()) && lastName.equals(c.getLast_name())) {
                clientsByName.add(c);
            }
        }
        return clientsByName;
    }
    public Client searchClientByPhone(String phone) {
        for (Client c : clients) {
            if(c.getPhone().equals(phone)) {
                return c;
            }
        }
        return null;
    }

}
