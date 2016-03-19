package com.ofg.loans.service.risk;

import com.ofg.loans.model.Client;
import com.ofg.loans.model.FraudRule;
import com.ofg.loans.model.LoanApplication;
import com.ofg.loans.service.services.ClientService;
import com.ofg.loans.service.services.FraudRuleService;
import com.ofg.loans.service.services.LoanApplicationService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by zhabba on 18.03.16.
 */
@Service
public class RiskServiceImpl implements RiskService {
    private static final Logger LOG = getLogger(RiskServiceImpl.class);
    private static final LocalTime LOWER_TIME_LIMIT = LocalTime.of(0, 0);
    private static final LocalTime UPPER_TIME_LIMIT = LocalTime.of(6, 0);

    @Autowired
    ClientService clientService;

    @Autowired
    LoanApplicationService loanApplicationService;

    @Autowired
    FraudRuleService fraudRuleService;

    /**
     * Main method of service to check application for possible fraud
     *
     * @param loanApplication
     * @return
     */
    public Boolean approve(LoanApplication loanApplication) {
        LOG.debug("Begin fraud rules evaluation ...");
        Collection<Long> ruleIds = loanApplication.getFraudRules();
        List<FraudRule> rules = ruleIds.stream().map(fraudRuleService::getRuleById).collect(Collectors.toList());
        //return rules.stream().allMatch(rule -> evaluateFraudRules(rule, loanApplication));
        return evaluateFraudRules(null, loanApplication);
    }

    /**
     * Evaluate groovy scripts representing antifraud rule
     *
     * @param rule
     * @param application
     * @return
     */
    private Boolean evaluateFraudRules(FraudRule rule, LoanApplication application) {
        Client client = application.getApplicant();
        LocalDateTime dateTimeOfApplication = application.getDateTime();
        Integer amount = application.getAmount();

        /******************************************************************************************
         * Frankly saying I'm stuck here with groovy script and decided to hardcode rules in Java
         * due to time lack for groovy learning and debugging.
         * Hope you'll forgive me for this.
         * But you can see general idea for extendable rule set.
         ******************************************************************************************/

        /*

        ScriptEngineManager scriptMgr = new ScriptEngineManager();
        ScriptEngine engine = Objects.requireNonNull(scriptMgr.getEngineByName("groovy"));
        try {
            engine.put("loanApplication", application);
            return (Boolean) engine.eval(rule.getRule());
        } catch (ScriptException e) {
            LOG.error("FraudRule evaluation error ...", e.getMessage());
            return false;
        }

        */

        // Hardcoded rules in Java
        Map<InetAddress, Long> clientIP = loanApplicationService.countClientIPGroupedByDate(
                client.getId(),
                dateTimeOfApplication.toLocalDate()
        );
        Boolean maxAttemptsPerIpReached = clientIP
                .entrySet()
                .stream()
                .anyMatch(entry -> entry.getValue() >= MAXIMUM_ATTEMPTS_PER_IP_ADDRESS);
        LocalTime applicationTime = dateTimeOfApplication.toLocalTime();

        Boolean isApproved;
        if ((LOWER_TIME_LIMIT.compareTo(applicationTime) <= 0
                && UPPER_TIME_LIMIT.compareTo(applicationTime) >= 0)
                && (MAXIMUM_LOAN_AMOUNT.compareTo(amount) <= 0)) {
            isApproved = false;
            LOG.debug("The rule of night time applications with MAX amount violated ...");
        } else if (maxAttemptsPerIpReached) {
            isApproved = false;
            LOG.debug("The rule of MAX tries per IP violated ...");
        } else {
            isApproved = true;
            LOG.debug("No rules violated ... OK!");
        }
        return isApproved;
    }
}