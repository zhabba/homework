package com.ofg.loans.repository.impl;

import com.ofg.loans.model.LoanApplication;
import com.ofg.loans.repository.LoanApplicationRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhabba on 18.03.16.
 */
@Repository
public class LoanApplicationRepositoryInmemoryImpl implements LoanApplicationRepository {
    private static final Map<Long, LoanApplication> storage = new ConcurrentHashMap<Long, LoanApplication>();

    @Override
    public LoanApplication save(LoanApplication entity) {
        return storage.put(entity.getId(), entity);
    }

    @Override
    public LoanApplication get(Long id) {
        return storage.getOrDefault(id, null);
    }

    @Override
    public LoanApplication update(Long id, LoanApplication entity) {
        return storage.put(id, entity);
    }

    @Override
    public LoanApplication delete(Long id) {
        return storage.remove(id);
    }

    @Override
    public Collection<LoanApplication> getAll() {
        return storage.values();
    }
}
