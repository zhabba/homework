package com.ofg.loans.service.services;

import com.ofg.loans.model.LoanApplication;

import java.net.InetAddress;
import java.time.LocalDate;
import java.util.Map;

/**
 * Created by zhabba on 18.03.16.
 */
public interface LoanApplicationService {
    LoanApplication saveApplication(LoanApplication application);
    LoanApplication getApplicationById(Long id);
    LoanApplication updateApplication(LoanApplication application);
    LoanApplication deleteApplication(Long id);
    Map<InetAddress, Long> countClientIPGroupedByDate(Long clientId, LocalDate date);
}
