package com.spring.transferservice.service;

import com.spring.transactionhistorymanagementservice.constant.TransactionStatus;
import com.spring.transactionhistorymanagementservice.dto.GetTransactionByTransactionIdRequest;
import com.spring.transactionhistorymanagementservice.model.TransactionHistory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class WebhookService {

    @Autowired
    private TransactionHistoryManagementService transactionHistoryManagementService;


    public ResponseEntity<String> handleWebhook(Map<String, Object> payload) {
        String transactionStatus = (String) payload.get("transaction_status");
        String transactionId = (String) payload.get("transaction_id");
        Double grossAmount = Double.valueOf(payload.get("gross_amount").toString());
        log.info("transactionStatus : {}", transactionStatus);
        log.info("transactionId : {}", transactionId);
        log.info("grossAmount : {}", grossAmount);

        TransactionHistory trxOpt = transactionHistoryManagementService.getTransactionHistoryByTransactionId(GetTransactionByTransactionIdRequest.builder()
                        .transactionId(transactionId)
                .build());
        log.info("data transactionHistory : {}", trxOpt);

        if (Objects.nonNull(trxOpt)) {
            TransactionHistory trx = trxOpt;

            if ("settlement".equals(transactionStatus)) {
                trx.setTransactionStatus(TransactionStatus.SUCCESS);
            } else if ("expire".equals(transactionStatus)) {
                trx.setTransactionStatus(TransactionStatus.FAILED);
            } else if ("cancel".equals(transactionStatus)) {
                trx.setTransactionStatus(TransactionStatus.FAILED);
            }
            transactionHistoryManagementService.updateTransactionStatus(GetTransactionByTransactionIdRequest.builder()
                            .transactionId(trx.getTransactionId())
                            .status(trx.getTransactionStatus())
                    .build());
        }

        return ResponseEntity.ok("Webhook received");
    }

}
