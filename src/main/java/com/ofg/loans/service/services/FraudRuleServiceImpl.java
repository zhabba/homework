package com.ofg.loans.service.services;

import com.ofg.loans.model.FraudRule;
import com.ofg.loans.repository.FraudRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhabba on 18.03.16.
 */
@Service
public class FraudRuleServiceImpl implements FraudRuleService {
    @Autowired
    private FraudRuleRepository repository;

    @Override
    public FraudRule saveRule(FraudRule rule) {
        return repository.save(rule);
    }

    @Override
    public FraudRule getRuleById(Long id) {
        return repository.get(id);
    }

    @Override
    public FraudRule updateRule(FraudRule rule) {
        return repository.update(rule.getId(),rule);
    }

    @Override
    public FraudRule deleteRule(Long id) {
        return repository.delete(id);
    }
}
