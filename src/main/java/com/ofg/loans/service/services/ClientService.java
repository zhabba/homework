package com.ofg.loans.service.services;

import com.ofg.loans.model.Client;

/**
 * Created by zhabba on 18.03.16.
 */

public interface ClientService {
    Client save(Client client);
    Client getClientById(Long id);
    Client updateClient(Client client);
    Client deleteClient(Long id);
}
