package com.spring.transferservice.controller;

import com.spring.transferservice.dto.TransferRequest;
import com.spring.transferservice.dto.TransferRequestDto;
import com.spring.transferservice.service.MidtransTransferService;
import com.spring.transferservice.service.TransferService;
import com.spring.transferservice.service.WebhookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class TransferServiceController {

    @Autowired
    private TransferService transferService;

    @Autowired
    private WebhookService webhookService;

    @PostMapping("/transfer-midtrans")
    public ResponseEntity<?> transfer(@RequestBody TransferRequestDto request) {
        return ResponseEntity.ok(transferService.transfer(request));
    }

    @PostMapping("/webhook-callback")
    public ResponseEntity<?> webhookCallback(@RequestBody Map<String, Object> payload) {
        return ResponseEntity.ok(webhookService.handleWebhook(payload));
    }
}
