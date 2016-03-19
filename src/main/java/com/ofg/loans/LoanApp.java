package com.ofg.loans;

import com.ofg.loans.model.Client;
import com.ofg.loans.model.FraudRule;
import com.ofg.loans.model.LoanApplication;
import com.ofg.loans.service.risk.RiskService;
import com.ofg.loans.service.risk.RiskServiceImpl;
import com.ofg.loans.service.services.*;
import org.slf4j.Logger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by zhabba on 18.03.16.
 */
public class LoanApp {
    private static final Logger LOG = getLogger(LoanApp.class);
    private static List<InetAddress> ips = new ArrayList<>();
    private static List<Client> clients = new ArrayList<>();
    private static List<FraudRule> fraudRules = new ArrayList<>();

    // Just for demo purposes prefill datasets
    static {
        // Fraud rules
        fraudRules.add(
                new FraudRule(
                    "",
                    "PeriodAmount",
                    "An attempt to take a loan made between 00:00 to 6:00 AM with maximum possible amount."
                )
        );
        fraudRules.add(
                new FraudRule(
                        "",
                        "MaxTriesFromIpReached",
                        "The client has reached the maximum number of loan applications (i.e. 3) per day from a single IP."
                )
        );

        // some IPs
        try {
            ips.add(InetAddress.getByName("192.168.0.1"));
            ips.add(InetAddress.getByName("192.168.0.2"));
            ips.add(InetAddress.getByName("192.168.0.3"));
            ips.add(InetAddress.getByName("192.168.0.4"));
            ips.add(InetAddress.getByName("192.168.0.5"));
            ips.add(InetAddress.getByName("192.168.0.6"));
            ips.add(InetAddress.getByName("192.168.0.7"));
            ips.add(InetAddress.getByName("192.168.0.8"));
            ips.add(InetAddress.getByName("192.168.0.9"));
            ips.add(InetAddress.getByName("192.168.0.10"));

            LOG.debug("Pre fill IPs set");
        } catch (UnknownHostException e) {
            LOG.error("Pre fill IPs failed", e.getMessage());
        }

        // some clients
        clients.add(new Client("Sam", "Johns", null));
        clients.add(new Client("Adam", "Cubota", null));
        clients.add(new Client("Mmedja", "Mwangi", null));
        clients.add(new Client("Aiden", "Pople", null));

        LOG.debug("Pre fill Clients set");
    }

    public static void main(String[] args) {
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            ClientService clientService = appCtx.getBean(ClientServiceImpl.class);
            LoanApplicationService loanApplicationService = appCtx.getBean(LoanApplicationService.class);
            RiskService riskService = appCtx.getBean(RiskServiceImpl.class);

            FraudRuleService fraudRuleService = appCtx.getBean(FraudRuleServiceImpl.class);
            fraudRules.stream().forEach(fraudRuleService::saveRule);

            // imitate application process
            Client client = clients.get(0);
            client.setCurrentIP(ips.get(0));

            clientService.save(client);

            LoanApplication loanApplication = new LoanApplication(
                    client,
                    LocalDateTime.now(),
                    10,
                    fraudRules.get(0).getId(),
                    fraudRules.get(1).getId()
            );
            loanApplicationService.saveApplication(loanApplication);

            // imitate application process
            client = clients.get(0);
            client.setCurrentIP(ips.get(1));

            clientService.save(client);

            loanApplication = new LoanApplication(
                    client,
                    LocalDateTime.now(),
                    10,
                    fraudRules.get(0).getId(),
                    fraudRules.get(1).getId()
            );
            loanApplicationService.saveApplication(loanApplication);

            // imitate application process
            client = clients.get(0);
            client.setCurrentIP(ips.get(2));

            clientService.save(client);

            loanApplication = new LoanApplication(
                    client,
                    LocalDateTime.now(),
                    10,
                    fraudRules.get(0).getId(),
                    fraudRules.get(1).getId()
            );
            loanApplicationService.saveApplication(loanApplication);

            Boolean isFraud = riskService.approve(loanApplication);

            System.out.println("Application fraud check result: " + isFraud);
        }
    }
}