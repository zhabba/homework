package com.ofg.loans.repository.impl;

import com.ofg.loans.model.FraudRule;
import com.ofg.loans.repository.FraudRuleRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhabba on 18.03.16.
 */
@Repository
public class FraudRuleRepositoryInmemoryImpl implements FraudRuleRepository {
    private static final Map<Long, FraudRule> storage = new ConcurrentHashMap<Long, FraudRule>();

    @Override
    public FraudRule save(FraudRule entity) {
        return storage.put(entity.getId(), entity);
    }

    @Override
    public FraudRule get(Long id) {
        return storage.getOrDefault(id, null);
    }

    @Override
    public FraudRule update(Long id, FraudRule entity) {
        return storage.put(id, entity);
    }

    @Override
    public FraudRule delete(Long id) {
        return storage.remove(id);
    }

    @Override
    public Collection<FraudRule> getAll() {
        return storage.values();
    }
}
