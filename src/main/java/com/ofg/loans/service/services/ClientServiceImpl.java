package com.ofg.loans.service.services;

import com.ofg.loans.model.Client;
import com.ofg.loans.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhabba on 18.03.16.
 */
@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientRepository repository;

    @Override
    public Client save(Client client) {
        return repository.save(client);
    }

    @Override
    public Client getClientById(Long id) {
        return repository.get(id);
    }

    @Override
    public Client updateClient(Client client) {
        return repository.update(client.getId(),client);
    }

    @Override
    public Client deleteClient(Long id) {
        return repository.delete(id);
    }
}
