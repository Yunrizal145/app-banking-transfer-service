package com.spring.transferservice.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class WebhookService {


    public ResponseEntity<String> handleWebhook(Map<String, Object> webhookData) {
        String orderId = webhookData.get("order_id").toString();
        String transactionStatus = webhookData.get("transaction_status").toString();

//        Optional<Transaction> trxOpt = transactionRepository.findByOrderId(orderId);
//        if (trxOpt.isPresent()) {
//            Transaction trx = trxOpt.get();
//
//            if (transactionStatus.equals("settlement")) {
//                trx.setStatus("SUCCESS");
//                // Update saldo internal pengguna (jika kirim sesama)
//            } else if (transactionStatus.equals("cancel") || transactionStatus.equals("expire")) {
//                trx.setStatus("FAILED");
//            }
//            transactionRepository.save(trx);
//        }

        return ResponseEntity.ok("Webhook received");
    }

}
