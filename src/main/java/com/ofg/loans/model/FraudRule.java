package com.ofg.loans.model;

/**
 * Created by zhabba on 18.03.16.
 */
public class FraudRule extends BaseEntity {
    private String rule;
    private String ruleName;
    private String ruleDescription;

    public FraudRule() {
    }

    public FraudRule(String rule, String ruleName, String ruleDescription) {
        this.rule = rule;
        this.ruleName = ruleName;
        this.ruleDescription = ruleDescription;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getRuleDescription() {
        return ruleDescription;
    }

    public void setRuleDescription(String ruleDescription) {
        this.ruleDescription = ruleDescription;
    }

    @Override
    public String toString() {
        return "FraudRule{" +
                "id=" + id +
                ", rule='" + rule + '\'' +
                ", ruleName='" + ruleName + '\'' +
                ", ruleDescription='" + ruleDescription + '\'' +
                '}';
    }
}
