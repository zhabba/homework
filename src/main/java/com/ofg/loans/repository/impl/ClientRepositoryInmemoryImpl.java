package com.ofg.loans.repository.impl;

import com.ofg.loans.model.Client;
import com.ofg.loans.repository.ClientRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhabba on 18.03.16.
 */
@Repository
public class ClientRepositoryInmemoryImpl implements ClientRepository {
    private static final Map<Long, Client> storage = new ConcurrentHashMap<Long, Client>();

    @Override
    public Client save(Client entity) {
        return storage.put(entity.getId(), entity);
    }

    @Override
    public Client get(Long id) {
        return storage.getOrDefault(id, null);
    }

    @Override
    public Client update(Long id, Client entity) {
        return storage.put(id, entity);
    }

    @Override
    public Client delete(Long id) {
        return storage.remove(id);
    }

    @Override
    public Collection<Client> getAll() {
        return storage.values();
    }
}
