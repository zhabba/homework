package com.ofg.loans.service.risk;

import com.ofg.loans.model.Client;
import com.ofg.loans.model.LoanApplication;
import com.ofg.loans.service.services.ClientService;
import com.ofg.loans.service.services.ClientServiceImpl;
import com.ofg.loans.service.services.LoanApplicationService;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Please add test cases for your risk service implementation.
 */
public class RiskServiceImplTest {
    private static final Integer MAXIMUM_ATTEMPTS_PER_IP_ADDRESS = 3;

    private static List<InetAddress> ips = new ArrayList<>();
    private static List<Client> clients = new ArrayList<>();
    private static ConfigurableApplicationContext appCtx;
    private static ClientService clientService;
    private static LoanApplicationService loanApplicationService;
    private static RiskService riskService;
    private static int counter = 0;

    @Rule
    public RepeatRule repeatRule = new RepeatRule();

    @BeforeClass
    public static void init() throws UnknownHostException {
        //add some clients
        clients.add(new Client("Adam", "Cubota", null));
        clients.add(new Client("Mmedja", "Mwangi", null));
        clients.add(new Client("Aiden", "Pople", null));
        clients.add(new Client("Sam", "Johns", null));
        clients.add(new Client("Laura", "Palmer", null));

        // add some IPs
        ips.add(InetAddress.getByName("192.168.0.1"));
        ips.add(InetAddress.getByName("192.168.0.2"));
        ips.add(InetAddress.getByName("192.168.0.3"));
        ips.add(InetAddress.getByName("192.168.0.4"));
        ips.add(InetAddress.getByName("192.168.0.5"));

        // init spring context
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        clientService = appCtx.getBean(ClientServiceImpl.class);
        loanApplicationService = appCtx.getBean(LoanApplicationService.class);
        riskService = appCtx.getBean(RiskServiceImpl.class);
    }

    @AfterClass
    public static void destroy() {
        appCtx.close();
    }

    @Test
    public void violatePeriodAndMaxAmount() {
        Client client = clients.get(0);
        client.setCurrentIP(ips.get(0));
        clientService.save(client);

        LoanApplication loanApplication = new LoanApplication(
                client,
                LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 1)),
                100,
                0L,
                1L
        );
        loanApplicationService.saveApplication(loanApplication);

        Boolean approved = riskService.approve(loanApplication);

        assertFalse(approved);
    }

    @Test
    public void approvedInRestrictedPeriodAndAmountBelowMax() {
        Client client = clients.get(1);
        client.setCurrentIP(ips.get(1));
        clientService.save(client);

        LoanApplication loanApplication = new LoanApplication(
                client,
                LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 1)),
                10,
                0L,
                1L
        );
        loanApplicationService.saveApplication(loanApplication);

        Boolean approved = riskService.approve(loanApplication);

        assertTrue(approved);
    }

    @Test
    public void approvedWhenAppliedWithMaxAmountInNotRestrictedPeriod() {
        Client client = clients.get(2);
        client.setCurrentIP(ips.get(2));
        clientService.save(client);

        LoanApplication loanApplication = new LoanApplication(
                client,
                LocalDateTime.of(LocalDate.now(), LocalTime.of(6, 1)),
                100,
                0L,
                1L
        );
        loanApplicationService.saveApplication(loanApplication);

        Boolean approved = riskService.approve(loanApplication);

        assertTrue(approved);
    }

    @Test
    @Repeat(times=5)
    public void violateMaxTriesPerIP() {
        Client client = clients.get(3);
        client.setCurrentIP(ips.get(3));
        clientService.save(client);

        LoanApplication loanApplication = new LoanApplication(
                client,
                LocalDateTime.of(LocalDate.now(), LocalTime.now()),
                10,
                0L,
                1L
        );
        loanApplicationService.saveApplication(loanApplication);

        Boolean approved = riskService.approve(loanApplication);
        if (++counter < MAXIMUM_ATTEMPTS_PER_IP_ADDRESS) {
            assertTrue(approved);
        } else {
            assertFalse(approved);
        }
        if (counter >= 5) {
            counter = 0;
        }
    }

    @Test
    @Repeat(times=5)
    public void approvedWhenAppliedFromUniqueIPs() {
        Client client = clients.get(4);
        client.setCurrentIP(ips.get(counter++));
        clientService.save(client);

        LoanApplication loanApplication = new LoanApplication(
                client,
                LocalDateTime.of(LocalDate.now(), LocalTime.now()),
                10,
                0L,
                1L
        );
        loanApplicationService.saveApplication(loanApplication);

        Boolean approved = riskService.approve(loanApplication);

        assertTrue(approved);
    }
}