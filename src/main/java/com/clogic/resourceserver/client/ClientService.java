package com.clogic.resourceserver.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    public Client getClientFromClientId(String clientId) {
        return clientRepository.findByClientId(clientId);
    }

    public Client getClientFromId(Integer id) {
        return clientRepository.findById(id);
    }

    public void saveClient(Client client){
        clientRepository.save(client);
    }

}
