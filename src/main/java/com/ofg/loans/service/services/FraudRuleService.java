package com.ofg.loans.service.services;

import com.ofg.loans.model.FraudRule;

/**
 * Created by zhabba on 18.03.16.
 */
public interface FraudRuleService {
    FraudRule saveRule(FraudRule rule);
    FraudRule getRuleById(Long id);
    FraudRule updateRule(FraudRule rule);
    FraudRule deleteRule(Long id);
}
