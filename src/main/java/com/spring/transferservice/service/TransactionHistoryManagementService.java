package com.spring.transferservice.service;

import com.spring.transactionhistorymanagementservice.dto.GetTransactionByTransactionIdRequest;
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

    @Value("${url.getTransactionHistoryByTransactionId}")
    private String getTransactionByTransactionIdUrl;

    @Value("${url.updateStatusTransaction}")
    private String updateTransactionStatusUrl;

    public void saveTransactoinHistory(TransactionHistory transactionHistory) {
        log.info("Start saveDataForUserTranhis ... ");
        log.info("saveData ... ");
        restTemplate.postForEntity(saveTransactionHistoryUrl, transactionHistory, Void.class);
    }

    public void updateTransactionStatus(GetTransactionByTransactionIdRequest request) {
        log.info("Start saveDataForUserTranhis ... ");
        log.info("saveData ... ");
        restTemplate.postForEntity(saveTransactionHistoryUrl, request, Void.class);
    }

    public TransactionHistory getTransactionHistoryByTransactionId(GetTransactionByTransactionIdRequest request) {
        log.info("Start getTransactionByTransactionId ... ");
        log.info("getTransactionByTransactionId req : {} ", request);

        ResponseEntity<TransactionHistory> transactionHistoryResponseEntity = restTemplate.postForEntity(getTransactionByTransactionIdUrl, request, TransactionHistory.class);
        return transactionHistoryResponseEntity.getBody();
    }
}
