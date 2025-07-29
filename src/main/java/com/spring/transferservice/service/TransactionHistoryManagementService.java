package com.spring.transferservice.service;

import com.spring.transactionhistorymanagementservice.model.TransactionHistory;
import com.spring.usermanagementservice.dto.GetUserProfileRequest;
import com.spring.usermanagementservice.dto.GetUserProfileResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class TransactionHistoryManagementService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${url.saveTransactionHistory}")
    private String saveTransactionHistoryUrl;

    public void saveTransactoinHistory(TransactionHistory transactionHistory) {
        log.info("Start saveDataForUserTranhis ... ");
        log.info("saveData ... ");
        restTemplate.postForEntity(saveTransactionHistoryUrl, transactionHistory, Void.class);
    }
}
