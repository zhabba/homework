package com.ofg.loans.service.services;

import com.ofg.loans.model.LoanApplication;
import com.ofg.loans.repository.LoanApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.time.LocalDate;
import java.util.Map;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

/**
 * Created by zhabba on 18.03.16.
 */
@Service
public class LoanApplicationServiceImpl implements LoanApplicationService {
    @Autowired
    private LoanApplicationRepository repository;

    @Override
    public LoanApplication saveApplication(LoanApplication application) {
        return repository.save(application);
    }

    @Override
    public LoanApplication getApplicationById(Long id) {
        return repository.get(id);
    }

    @Override
    public LoanApplication updateApplication(LoanApplication application) {
        return repository.update(application.getId(),application);
    }

    @Override
    public LoanApplication deleteApplication(Long id) {
        return repository.delete(id);
    }

    /**
     * Selects client's applications on given date and groups them by IP and counts IPs
     *
     * @param clientId
     * @param date
     * @return
     */
    @Override
    public Map<InetAddress, Long> countClientIPGroupedByDate(Long clientId, LocalDate date) {
        return repository
                .getAll()
                .stream()
                .filter(app -> date.isEqual(app.getDateTime().toLocalDate()))
                .filter(app -> clientId.equals(app.getApplicant().getId()))
                .collect(groupingBy(LoanApplication::getClientApplicationIP, counting()));
    }
}