import java.util.ArrayList;

public class AllClients {
    ArrayList<Client> clients;
    public AllClients() {
        clients = new ArrayList<>();
    }
    public boolean addClient(Client client) {
        for (Client c : clients) {
            if(client.equals(c)||c.getAFM().equals(client.getAFM())) {               return false;
            }
        }
        clients.add(client);
        return true;
    }
    public Client searchClientByAFM(String AFM) {
        for (Client c : clients) {
            if(c.getAFM().equals(AFM)) {
                return c;
            }
        }
        return null;
    }
    public ArrayList<Client> searchByName(String firstName, String lastName) {
        ArrayList<Client> clientsByName = new ArrayList<>();
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
